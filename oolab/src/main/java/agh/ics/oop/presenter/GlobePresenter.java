package agh.ics.oop.presenter;

import agh.ics.oop.Simulation;
import agh.ics.oop.SimulationEngine;
import agh.ics.oop.model.GlobeChangeListener;
import agh.ics.oop.model.Vector2d;
import agh.ics.oop.model.elements.Animal;
import agh.ics.oop.model.elements.WorldElement;
import agh.ics.oop.model.maps.AbstractGlobeMap;
import agh.ics.oop.model.maps.Boundary;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

import java.util.*;

public class GlobePresenter implements GlobeChangeListener {
    public BorderPane content;
    public VBox statistics;

    public StatisticsPresenter statisticsController;
    public GridPane globe;
    public Button highlightGenomeButton;
    public Button preferedSpacesButton;
    public Button startButton;
    public Button stopButton;
    public Button runButton;

    private Simulation simulation;
    private SimulationEngine engine;

    private Map<EventHandler<ActionEvent>, Animal> animals = new HashMap<>();

    private int rowHeight = 30;
    private int collumnWidth = 30;

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
                    statisticsController.trackAnimal(animals.get(this));
                }
            };
            animals.put(action, animal);
            animalButton.setOnAction(action);
            globe.add(animalButton, animal.getPosition().getX(), lowerZero - animal.getPosition().getY());
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
    }

    public void stopButtonClicked(ActionEvent actionEvent) {
        highlightGenomeButton.visibleProperty().setValue(true);
        highlightGenomeButton.managedProperty().setValue(true);
        preferedSpacesButton.visibleProperty().setValue(true);
        preferedSpacesButton.managedProperty().setValue(true);
        stopButton.setDisable(true);
        startButton.setDisable(false);
    }

    public void setSimulation(Simulation simulation) {
        this.simulation = simulation;
        simulation.getMap().addListener(statisticsController);
        statisticsController.mapChanged(simulation.getMap(), "");
    }

    public void highlightDominantGenome(ActionEvent actionEvent) {
    }

    public void highlightPreferredSpaces(ActionEvent actionEvent) {
        List<Vector2d> spaces = simulation.getMap().getPreferredSpaces();
        int lowerZero = simulation.getMap().getCurrentBounds().upperRight().getX();
        for (Vector2d position: spaces){
            Rectangle highlight = new Rectangle(collumnWidth, rowHeight);
            highlight.setFill(Color.web("rgba(42, 215, 239, 0.46)"));
            globe.add(highlight, position.getX(), lowerZero - position.getY());
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
