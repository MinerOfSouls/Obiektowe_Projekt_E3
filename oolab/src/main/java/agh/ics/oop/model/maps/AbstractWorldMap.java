package agh.ics.oop.model.maps;

import agh.ics.oop.model.*;
import agh.ics.oop.model.elements.Animal;
import agh.ics.oop.model.elements.WorldElement;
import agh.ics.oop.model.util.MapVisualizer;

import java.util.*;


public abstract class AbstractWorldMap implements WorldMap {
    protected final Map<Vector2d, Animal> animals = new HashMap<>();
    protected MapVisualizer visualisation = new MapVisualizer(this);
    protected List<MapChangeListener> listeners = new ArrayList<>();
    private final int ID;

    protected AbstractWorldMap(int id) {
        ID = id;
    }

    @Override
    public void place(Animal animal) throws IncorrectPositionException {
        if (canMoveTo(animal.getPosition())) {
            this.animals.put(animal.getPosition(), animal);
            notifyListeners(String.format("Animal placed at %s",animal.getPosition()));
        }
        else {
            throw new IncorrectPositionException(animal.getPosition());
        }
    }

    @Override
    public void move(Animal animal ) {
        animals.remove(animal.getPosition());
        var oldPosition = animal.getPosition();
        var oldFacing = animal.getFacing();
        animal.move(this);
        animals.put(animal.getPosition(),animal);
        var newPosition = animal.getPosition();
        var newFacing = animal.getFacing();
        if(!newPosition.equals(oldPosition)){
            notifyListeners(String.format("Animal moved from %s to %s", oldPosition, newPosition));
        } else if (!oldFacing.equals(newFacing)) {
            notifyListeners(String.format("Animal at %s turned from %s to %s",oldPosition,oldFacing,newFacing));
        }
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return animals.containsKey(position);
    }

    @Override
    public WorldElement objectAt(Vector2d position) {
        if(!isOccupied(position)){
            return null;
        }
        return animals.get(position);
    }

    @Override
    public abstract boolean canMoveTo(Vector2d position);

    @Override
    public Collection<WorldElement> getElements(){
        return new ArrayList<>(animals.values());
    }

    public abstract Boundary getCurrentBounds();

    public String toString(){
        Boundary limits = getCurrentBounds();
        return visualisation.draw(limits.lowerLeft(),limits.upperRight());
    }

    public void registerListener(MapChangeListener newListener){
        listeners.add(newListener);
    }

    public void deregisterListener(MapChangeListener listener){
        listeners.remove(listener);
    }

    private void notifyListeners(String message){
        for(MapChangeListener listener : listeners){
            listener.mapChanged(this,message);
        }
    }

    public int getID(){
        return ID;
    }

}
