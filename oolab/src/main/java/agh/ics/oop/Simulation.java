package agh.ics.oop;
import agh.ics.oop.model.elements.Animal;
import agh.ics.oop.model.maps.AbstractGlobeMap;

import java.util.ArrayList;
import java.util.List;

public class Simulation implements Runnable {
    private List<Animal> animals;
    private AbstractGlobeMap map;
    private boolean runSetting = true;
    private List<SimulationListener> listeners = new ArrayList<>();

    int growthFactor;

    public Simulation(AbstractGlobeMap globeMap, int givenGrowthFactor){
        map = globeMap;
        animals = globeMap.getAnimals();
        growthFactor = givenGrowthFactor;
    }

    public synchronized void run(){
        int current=0;
        try {
            while(true) {
                if(runSetting){
                    map.decreaseEnergy(animals.get(current));
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
                        notifyListeners();
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

    public void addListener(SimulationListener listener){
        listeners.add(listener);
    }

    public void removeListener(SimulationListener listener){
        listeners.remove(listener);
    }

    private void notifyListeners(){
        for(SimulationListener listener: listeners){
            listener.dayAdvance(this);
        }
    }
}
