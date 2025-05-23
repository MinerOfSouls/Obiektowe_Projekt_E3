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
import java.util.List;

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
    public Label LivingAndDeadAnimalsLabel;

    public VBox animalTrackingVBox;

    public void initialize(){
        stopTrackingButton.setVisible(false);
        stopTrackingButton.setManaged(false);
        animalTrackingVBox.getChildren().add(animalStats);
        animalStats.setManaged(false);
        animalStats.setVisible(false);
    }

    private void updateStatistics(AbstractGlobeMap map){
        animalAmountLabel.setText(String.format("Ilosc zwierzat: %d", map.getAnimals().size()));
        grassAmountLabel.setText(String.format("Ilosc trawy: %d", map.getGrassLocations().size()));
        freeSpaceLabel.setText(String.format("Ilosc wolnego miejsca: %d", (map.getCurrentBounds().upperRight().getY()+1)*(map.getCurrentBounds().upperRight().getY()+1) - map.getGrassLocations().size()));
        popularGenotypesVbox.getChildren().clear();
        for (List<Integer> genome: map.getTop5Genomes()){
            popularGenotypesVbox.getChildren().add(new Label(genome.toString()));
        }
        averageEnergyLevel.setText(String.format("Srednia energia: %d", map.getAverageEnergy()));
        averageChildAmountLabel.setText(String.format("Srednia liczba dzieci: %d", map.getAverageChildrenAmount()));
        averageLifeSpanLabel.setText(String.format("Srednia dlugolc zycia: %d", map.getAverageLifespan()));
        LivingAndDeadAnimalsLabel.setText(String .format("wszystkie zwierzeta od poczatku symulacji %d", map.getLivingAndDeadAnimalsAmount() ));
    }

    @Override
    public void mapChanged(AbstractGlobeMap globeMap, String message) {
        Platform.runLater(() -> {updateStatistics(globeMap);
            animalStatsController.updateTrackedAnimal(globeMap);});
    }

    public void trackAnimal(Animal animal){
        animalStatsController.clearAnimalStats();  // Czyszczenie poprzednich danych

        // Ustawianie nowego zwierzęcia do śledzenia
        animalStatsController.setTrackedAnimal(animal);

        // Uaktualnienie UI dla nowego zwierzęcia
        // Można przekazać null, jeśli chcesz, żeby domyślnie nie wywoływać mapy

        // Włączamy UI dla statystyk zwierzęcia
        animalStats.setManaged(true);
        animalStats.setVisible(true);
        stopTrackingButton.setVisible(true);
        stopTrackingButton.setManaged(true);
    }


    public void stopTracking(ActionEvent actionEvent) {
        animalStatsController.stopTracking();
        animalStats.setManaged(false);
        animalStats.setVisible(false);
        stopTrackingButton.setVisible(false);
        stopTrackingButton.setManaged(false);
    }
}
