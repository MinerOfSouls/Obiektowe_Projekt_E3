package agh.ics.oop.presenter;

import agh.ics.oop.Simulation;
import agh.ics.oop.model.GlobeChangeListener;
import agh.ics.oop.model.maps.AbstractGlobeMap;
import agh.ics.oop.model.maps.Boundary;
import agh.ics.oop.model.maps.EquatorGlobe;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.layout.*;

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
                
            }
        }
    }

    private void drawMap(AbstractGlobeMap globe){
        clearGrid();
    }

    @Override
    public void mapChanged(AbstractGlobeMap globe, String message) {
        Platform.runLater(() -> {drawMap(globe);});
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
