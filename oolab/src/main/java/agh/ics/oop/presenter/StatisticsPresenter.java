package agh.ics.oop.presenter;

import agh.ics.oop.model.GlobeChangeListener;
import agh.ics.oop.model.elements.Animal;
import agh.ics.oop.model.maps.AbstractGlobeMap;
import agh.ics.oop.model.maps.Globe;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Box;

import java.awt.*;
import java.beans.EventHandler;

public class StatisticsPresenter implements GlobeChangeListener {

    public VBox animalStats;
    public AnimalPresenter animalStatsController;
    public VBox stats;
    public Label animalAmountLabel;
    public Label grassAmountLabel;
    public Label freeSpaceLabel;
    public VBox popularGenotypesVbox;
    public Label averageEnergyLevel;
    public Label averageLifeSpanLabel;
    public Label averageChildAmountLabel;
    public Button stopTrackingButton;

    public VBox animalTrackingVBox;

    public void initialize(){
        stopTrackingButton.setVisible(false);
        stopTrackingButton.setManaged(false);
    }

    private void updateStatistics(AbstractGlobeMap map){

    }

    @Override
    public void mapChanged(AbstractGlobeMap globeMap, String message) {
        Platform.runLater(() -> {updateStatistics(globeMap);});
    }

    public void trackAnimal(Animal animal){
        animalStatsController.setTrackedAnimal(animal);
        animalTrackingVBox.getChildren().removeFirst();
        animalTrackingVBox.getChildren().add(animalStats);
        stopTrackingButton.setVisible(true);
        stopTrackingButton.setManaged(true);
    }


    public void stopTracking(ActionEvent actionEvent) {
        animalStatsController.stopTracking();
        animalTrackingVBox.getChildren().removeFirst();
        stopTrackingButton.setVisible(false);
        stopTrackingButton.setManaged(false);
    }
}
