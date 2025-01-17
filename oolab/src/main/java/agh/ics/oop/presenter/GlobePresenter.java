package agh.ics.oop.presenter;

import agh.ics.oop.Simulation;
import agh.ics.oop.model.GlobeChangeListener;
import agh.ics.oop.model.maps.Globe;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

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

    private void drawMap(){

    }

    @Override
    public void mapChanged(Globe globe, String message) {
        Platform.runLater(this::drawMap);
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
