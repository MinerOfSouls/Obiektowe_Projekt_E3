package agh.ics.oop.presenter;

import agh.ics.oop.Simulation;
import agh.ics.oop.model.GlobeChangeListener;
import agh.ics.oop.model.Vector2d;
import agh.ics.oop.model.elements.Animal;
import agh.ics.oop.model.elements.WorldElement;
import agh.ics.oop.model.maps.AbstractGlobeMap;
import agh.ics.oop.model.maps.Boundary;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GlobePresenter implements GlobeChangeListener {
    public BorderPane content;
    public VBox statistics;

    public StatisticsPresenter statisticsController;
    public GridPane globe;
    public Button highlightGenomeButton;
    public Button preferedSpacesButton;
    public Button startButton;
    public Button stopButton;

    private Simulation simulation;

    private Map<EventHandler<ActionEvent>, Animal> animals = new HashMap<>();

    public void initialize(){
        content.setLeft(statistics);
        highlightGenomeButton.visibleProperty().setValue(false);
        highlightGenomeButton.managedProperty().setValue(false);
        preferedSpacesButton.visibleProperty().setValue(false);
        preferedSpacesButton.managedProperty().setValue(false);
    }

    private void drawGrid(Boundary bounds){
        for (int i = bounds.lowerLeft().getX(); i < bounds.upperRight().getX()+2; i++) {
            globe.getColumnConstraints().add(new ColumnConstraints(50));
        }
        for (int i = bounds.lowerLeft().getY(); i < bounds.upperRight().getY()+1; i++) {
            globe.getRowConstraints().add(new RowConstraints(50));
        }
    }

    private void drawGround(AbstractGlobeMap map){
        Boundary bounds = map.getCurrentBounds();
        for (int i = 0; i < bounds.upperRight().getX()+1; i++) {
            for (int j = 0; j < bounds.upperRight().getY()+1; j++) {
                ImageView ground = new ImageView(SimulationPresenter.groundTexture);
                globe.add(ground, i, j);
            }
        }
    }

    private void drawGrass(AbstractGlobeMap map){
        Boundary bounds = map.getCurrentBounds();
        int lowerZero = bounds.upperRight().getX();
        for(Vector2d location : map.getGrassLocations()){
            ImageView grass = new ImageView(SimulationPresenter.grassTexture);
            globe.add(grass, location.getX(), lowerZero - location.getY());
        }
    }

    private void drawAnimals(AbstractGlobeMap map){
        Boundary bounds = map.getCurrentBounds();
        int lowerZero = bounds.upperRight().getX();
        for(Animal animal : map.getTopAnimals()){
            Button animalButton = new Button();
            EventHandler<ActionEvent> action = new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    statisticsController.trackAnimal(animals.get(this));
                }
            };
            animals.put(action, animal);
            animalButton.setBackground(
                    new Background(
                            new BackgroundImage(animal.getTexture(), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT)
                    ));
            animalButton.setOnAction(action);
            globe.add(animalButton, animal.getPosition().getX(), lowerZero - animal.getPosition().getY());
        }
    }

    private void drawMap(AbstractGlobeMap map){
        clearGrid();
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
    }

    public void highlightDominantGenome(ActionEvent actionEvent) {
    }

    public void highlightPreferredSpaces(ActionEvent actionEvent) {
    }
}
