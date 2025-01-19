package agh.ics.oop.presenter;

import agh.ics.oop.Simulation;
import agh.ics.oop.SimulationEngine;
import agh.ics.oop.model.GlobeChangeListener;
import agh.ics.oop.model.Vector2d;
import agh.ics.oop.model.elements.Animal;
import agh.ics.oop.model.maps.AbstractGlobeMap;
import agh.ics.oop.model.maps.Boundary;
import javafx.application.Platform;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.Node;

import java.util.*;

public class GlobePresenter implements GlobeChangeListener {
    public BorderPane content;
    public VBox statistics;

    public StatisticsPresenter statisticsController;
    public GridPane globe;
    public ToggleButton highlightGenomeButton;
    public ToggleButton preferedSpacesButton;
    public Button startButton;
    public Button stopButton;
    public Button runButton;

    private Simulation simulation;
    private SimulationEngine engine;

    private Map<EventHandler<ActionEvent>, Animal> animals = new HashMap<>();
    private List<Button> animalButtons = new ArrayList<>();

    private int rowHeight = 30;
    private int collumnWidth = 30;

    private final Paint preferredSpaceHighlight = Color.web("rgba(42, 215, 239, 0.46)");
    private final Paint dominantGenomeHighlight = Color.web("rgba(227, 2, 2, 0.42)");
    private final Paint energyBarColor = Color.web("rgba(255, 67, 67, 1)");

    public void initialize(){
        content.setLeft(statistics);
        highlightGenomeButton.visibleProperty().setValue(false);
        highlightGenomeButton.managedProperty().setValue(false);
        preferedSpacesButton.visibleProperty().setValue(false);
        preferedSpacesButton.managedProperty().setValue(false);

        startButton.visibleProperty().setValue(false);
        startButton.managedProperty().setValue(false);
        stopButton.visibleProperty().setValue(false);
        stopButton.managedProperty().setValue(true);
    }

    private void drawGrid(Boundary bounds){
        for (int i = bounds.lowerLeft().getX(); i < bounds.upperRight().getX()+2; i++) {
            globe.getColumnConstraints().add(new ColumnConstraints(collumnWidth));
        }
        for (int i = bounds.lowerLeft().getY(); i < bounds.upperRight().getY()+1; i++) {
            globe.getRowConstraints().add(new RowConstraints(rowHeight));
        }
    }

    private void drawGround(AbstractGlobeMap map){
        Boundary bounds = map.getCurrentBounds();
        for (int i = 0; i < bounds.upperRight().getX()+1; i++) {
            for (int j = 0; j < bounds.upperRight().getY()+1; j++) {
                ImageView ground = new ImageView(SimulationPresenter.groundTexture);
                ground.setFitWidth(collumnWidth);
                ground.setFitHeight(rowHeight);
                globe.add(ground, i, j);
            }
        }
    }

    private void drawGrass(AbstractGlobeMap map){
        Boundary bounds = map.getCurrentBounds();
        int lowerZero = bounds.upperRight().getX();
        for(Vector2d location : map.getGrassLocations()){
            ImageView grass = new ImageView(SimulationPresenter.grassTexture);
            grass.setFitHeight(rowHeight);
            grass.setFitWidth(collumnWidth);
            globe.add(grass, location.getX(), lowerZero - location.getY());
        }
    }

    private void drawAnimals(AbstractGlobeMap map){
        animalButtons.clear();
        Boundary bounds = map.getCurrentBounds();
        int lowerZero = bounds.upperRight().getX();
        for(Animal animal : map.getTopAnimals()){
            ImageView animalTexture = new ImageView(animal.getTexture());
            animalTexture.setFitWidth(collumnWidth);
            animalTexture.setFitHeight(rowHeight);
            Button animalButton = new Button("", animalTexture);
            animalButton.setPadding(Insets.EMPTY);
            animalButton.setMaxWidth(collumnWidth);
            animalButton.setMaxHeight(rowHeight);
            animalButton.setBackground(null);
            EventHandler<ActionEvent> action = new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    if (simulation.getState()){
                        return;
                    }
                    statisticsController.trackAnimal(animals.get(this));
                }
            };
            animals.put(action, animal);
            animalButtons.add(animalButton);
            animalButton.setOnAction(action);
            globe.add(animalButton, animal.getPosition().getX(), lowerZero - animal.getPosition().getY());

            SimpleDoubleProperty energyPercentage = new SimpleDoubleProperty(Math.min(1.0,
                    (double) animal.getEnergy() /simulation.getMap().getMinBreedingEnergy()));

            Rectangle energyBar = new Rectangle((double) rowHeight /10, collumnWidth);
        }
    }

    private void drawMap(AbstractGlobeMap map){
        clearGrid();
        drawGrid(map.getCurrentBounds());
        drawGround(map);
        drawGrass(map);
        drawAnimals(map);
    }

    @Override
    public void mapChanged(AbstractGlobeMap map, String message) {
        Platform.runLater(() -> {drawMap(map);});
    }

    private void clearGrid() {
        globe.getChildren().clear();
        globe.getColumnConstraints().clear();
        globe.getRowConstraints().clear();
    }

    public void startButtonClicked(ActionEvent actionEvent) {
        highlightGenomeButton.visibleProperty().setValue(false);
        highlightGenomeButton.managedProperty().setValue(false);
        preferedSpacesButton.visibleProperty().setValue(false);
        preferedSpacesButton.managedProperty().setValue(false);
        startButton.setDisable(true);
        stopButton.setDisable(false);
        simulation.start();
    }

    public void stopButtonClicked(ActionEvent actionEvent) {
        highlightGenomeButton.visibleProperty().setValue(true);
        highlightGenomeButton.managedProperty().setValue(true);
        preferedSpacesButton.visibleProperty().setValue(true);
        preferedSpacesButton.managedProperty().setValue(true);
        stopButton.setDisable(true);
        startButton.setDisable(false);
        simulation.stop();
    }

    public void setSimulation(Simulation simulation) {
        this.simulation = simulation;
        simulation.getMap().addListener(statisticsController);
        statisticsController.mapChanged(simulation.getMap(), "");
        Boundary bounds = simulation.getMap().getCurrentBounds();
        rowHeight = Math.min(500/bounds.upperRight().getX(),500/bounds.upperRight().getY());
        collumnWidth = rowHeight;
    }

    public void highlightDominantGenome(ActionEvent actionEvent) {
        if(highlightGenomeButton.isSelected()){
            List<Vector2d> positions = simulation.getMap().getDominantGenomeLocations();
            int lowerZero = simulation.getMap().getCurrentBounds().upperRight().getX();
            for (Vector2d position: positions){
                Rectangle highlight = new Rectangle(collumnWidth, rowHeight);
                highlight.setFill(dominantGenomeHighlight);
                globe.add(highlight, position.getX(), lowerZero - position.getY());
            }
        }
        else {
            globe.getChildren().removeIf((Node node) -> {
                if(node.getClass() == Rectangle.class){
                    Rectangle highlight = (Rectangle) node;
                    if(highlight.getFill() == dominantGenomeHighlight){
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            });
        }
    }

    public void highlightPreferredSpaces(ActionEvent actionEvent) {
        if(preferedSpacesButton.isSelected()){
            List<Vector2d> spaces = simulation.getMap().getPreferredSpaces();
            int lowerZero = simulation.getMap().getCurrentBounds().upperRight().getX();
            for (Vector2d position: spaces){
                Rectangle highlight = new Rectangle(collumnWidth, rowHeight);
                highlight.setFill(preferredSpaceHighlight);
                globe.add(highlight, position.getX(), lowerZero - position.getY());
            }
        }
        else{
            globe.getChildren().removeIf((Node node) -> {
                if(node.getClass() == Rectangle.class){
                    Rectangle highlight = (Rectangle) node;
                    if(highlight.getFill() == preferredSpaceHighlight){
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            });
        }
    }

    public void runSimulation(ActionEvent actionEvent) {
        runButton.visibleProperty().setValue(false);
        runButton.managedProperty().setValue(false);

        stopButton.visibleProperty().setValue(true);
        stopButton.managedProperty().setValue(true);
        startButton.managedProperty().setValue(true);
        startButton.visibleProperty().setValue(true);
        startButton.setDisable(true);

        engine = new SimulationEngine(List.of(simulation));
        engine.runAsync();
    }
}
