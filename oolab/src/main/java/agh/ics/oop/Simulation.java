package agh.ics.oop;
import agh.ics.oop.model.elements.Animal;
import agh.ics.oop.model.maps.AbstractGlobeMap;

import java.util.List;

public class Simulation implements Runnable {
    private List<Animal> animals;
    private AbstractGlobeMap map;
    private boolean runSetting = true;

    int growthFactor;

    public Simulation(AbstractGlobeMap globeMap, int givenGrowthFactor){
        map = globeMap;
        animals = globeMap.getAnimals();
        growthFactor = givenGrowthFactor;
    }

    public synchronized void run() {
        int current = 0;
        try {
            while (true) {
                if (runSetting) {

                    animals = map.getAnimals();


                    if (animals.isEmpty()) {
                        continue;
                    }


                    if (current < animals.size()) {
                        map.decreaseEnergy(animals.get(current));
                        map.move(animals.get(current));
                        map.eatIfPossible(animals.get(current));
                    }


                    current = current + 1;


                    if (current == animals.size()) {
                        map.increaseTime();
                        map.animalBreeding();
                        map.grow(growthFactor);
                        map.removeDeadAnimals();
                        animals = map.getAnimals();
                        current = 0;
                    }

                    Thread.sleep(500);
                } else {
                    wait();
                }
            }
        } catch (InterruptedException e) {

        }
    }

    public synchronized void start(){
        runSetting = true;
        notify();
    }

    public void stop(){
        runSetting = false;
    }

    public AbstractGlobeMap getMap() {
        return map;
    }

    public boolean getState(){
        return runSetting;
    }
}
