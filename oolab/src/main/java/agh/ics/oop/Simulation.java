package agh.ics.oop;
import agh.ics.oop.model.elements.Animal;
import agh.ics.oop.model.maps.AbstractGlobeMap;

import java.util.List;

public class Simulation implements Runnable {
    private List<Animal> animals;
    private AbstractGlobeMap map;
    private boolean runSetting = true;

    private boolean simulationOpen = true;

    int growthFactor;

    public Simulation(AbstractGlobeMap globeMap, int givenGrowthFactor){
        map = globeMap;
        animals = globeMap.getAnimals();
        growthFactor = givenGrowthFactor;
    }

    public synchronized void run(){
        int current=0;
        try {
            while(simulationOpen) {
                //TODO:ADD STOP LOGIC
                if(runSetting){
                    map.move(animals.get(current));
                    map.eatIfPossible(animals.get(current));
                    current = current+1;
                    if(current== animals.size()-1){
                        map.increaseTime();
                        map.animalBreeding();
                        animals = map.getAnimals();
                        map.grow(growthFactor);
                        map.removeDeadAnimals();
                        current = 0;
                    }
                    Thread.sleep(500);
                } else {
                    wait();
                }
            }
        } catch (InterruptedException e){
            //ignored
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

    public void end(){

    }
}
