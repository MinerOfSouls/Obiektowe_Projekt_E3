package agh.ics.oop.presenter;

import agh.ics.oop.model.GlobeChangeListener;
import agh.ics.oop.model.elements.Animal;
import agh.ics.oop.model.maps.AbstractGlobeMap;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;

public class AnimalPresenter  {

    public Label trackedAnimalGenomeLabel;
    public Label trackedAnimalEnergyLabel;
    public Label trackedAnimalEatenPlantsLabel;
    public Label trackedAnimalDirectChildrenLabel;
    public Label trackedAnimalAllChildrenLabel;
    public Label trackedAnimalDaysSurviedLabel;
    public Label trackedAnimalDeathDayLabel;

    private Animal trackedAnimal;

    public void setTrackedAnimal(Animal animal){
        trackedAnimal = animal;
    }
    public void updateTrackedAnimal(AbstractGlobeMap map){
        if(trackedAnimal != null){
            trackedAnimalGenomeLabel.setText(trackedAnimal.getGenome().toString());
            trackedAnimalEnergyLabel.setText(String.format("Energia: %d", trackedAnimal.getEnergy()));
            trackedAnimalEatenPlantsLabel.setText(String.format("Zjedzone rosliny: %d", trackedAnimal.getPlantsEaten()));
            trackedAnimalDirectChildrenLabel.setText(String.format("Dzieci: %d", trackedAnimal.getNumberOfChildrens()));
            trackedAnimalAllChildrenLabel.setText(String.format("Wszystkie dzieci: %d", trackedAnimal.getNumberOfChildrens()));
            trackedAnimalDaysSurviedLabel.setText(String.format("Dni zyjacy: %d", map.getTime()-trackedAnimal.getBornTime()));
            trackedAnimalDeathDayLabel.setText(String.format("Dzien smierci: %d", trackedAnimal.getDeathTime()));
        }
    }
    public void clearAnimalStats() {
        // Resetowanie etykiet do pustych wartości
        trackedAnimalGenomeLabel.setText("");
        trackedAnimalEnergyLabel.setText("");
        trackedAnimalEatenPlantsLabel.setText("");
        trackedAnimalDirectChildrenLabel.setText("");
        trackedAnimalAllChildrenLabel.setText("");
        trackedAnimalDaysSurviedLabel.setText("");
        trackedAnimalDeathDayLabel.setText("");
    }


    public void stopTracking(){
        trackedAnimal = null;
        clearAnimalStats();
    }

}
