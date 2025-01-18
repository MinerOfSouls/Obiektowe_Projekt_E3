package agh.ics.oop.presenter;

import agh.ics.oop.model.elements.Animal;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;

public class AnimalPresenter {

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

    public void stopTracking(){
        trackedAnimal = null;
    }

}
