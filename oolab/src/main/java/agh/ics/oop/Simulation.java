package agh.ics.oop;
import agh.ics.oop.model.elements.Animal;
import agh.ics.oop.model.maps.AbstractGlobeMap;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.util.List;

public class Simulation implements Runnable {
    private List<Animal> animals;
    private AbstractGlobeMap map;

    int growthFactor;

    public Simulation(AbstractGlobeMap globeMap, int givenGrowthFactor){
        map = globeMap;
        animals = globeMap.getAnimals();
        growthFactor = givenGrowthFactor;
    }

    public void run(){
        int current=0;
        try {
            while(true) {
                //TODO:ADD STOP LOGIC
                if(true){
                    map.move(animals.get(current));
                    map.eatIfPossible(animals.get(current));
                    current = current+1;
                    if(current== animals.size()-1){
                        map.increaseTime();
                        map.animalBreeding();
                        animals = map.getAnimals();
                        map.grow(growthFactor);
                        current = 0;
                    }
                }
                Thread.sleep(500);
            }
        } catch (InterruptedException e){
            //ignored
        }
    }

    public AbstractGlobeMap getMap() {
        return map;
    }
}
