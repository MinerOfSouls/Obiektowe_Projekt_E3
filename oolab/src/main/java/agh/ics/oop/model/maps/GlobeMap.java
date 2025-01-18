package agh.ics.oop.model.maps;

import agh.ics.oop.model.*;
import agh.ics.oop.model.elements.Animal;
import agh.ics.oop.model.elements.Grass;
import agh.ics.oop.model.elements.WorldElement;
import agh.ics.oop.model.util.AnimalComparator;

import java.util.*;
import java.util.stream.Stream;

public abstract class GlobeMap implements MoveValidator {

    protected final Map<Vector2d, List<Animal>> animals = new HashMap<>();
    protected final Map<Vector2d, Grass> grasses = new HashMap<>();
    protected final List<GlobeChangeListener> listeners = new ArrayList<>();
    private final int startEnergy;
    private int time;
    private final int id;
    protected final Boundary bounds;
    protected final int growthFactor;
    protected Random randomizer = new Random();

    public GlobeMap(int givenId, int givenWidth, int givenHeight, int givenGrowthFactor,int givenStartEnergy) {
        id = givenId;
        growthFactor = givenGrowthFactor;
        bounds = new Boundary(new Vector2d(0,0),new Vector2d(givenWidth-1, givenHeight-1));
        startEnergy = givenStartEnergy;
        time=0;
    }

    public void increaseTime() {
        this.time +=1;
    }

    public boolean canMoveTo(Vector2d position) {
        return bounds.upperRight().getY() >= position.getY() && bounds.lowerLeft().getY() <= position.getY();
    }
    public void animalBreeding(){
        for (List<Animal> animalList : animals.values()) {
            if(animalList.size() >= 2){
                animalList.sort(new AnimalComparator());
                if(animalList.get(0).getEnergy() >= 0.5 * startEnergy &&
                        animalList.get(1).getEnergy() >= 0.5 * startEnergy){
                    Vector2d childPosition = animalList.get(0).getPosition();
                    Animal child = new Animal(childPosition, animalList.get(0), animalList.get(1),startEnergy,time);
                    animalList.get(0).setEnergy((int) (animalList.get(0).getEnergy() - 0.25 * startEnergy));
                    animalList.get(1).setEnergy((int) (animalList.get(1).getEnergy() - 0.25 * startEnergy));
                    animalList.get(0).increaseChilds();
                    animalList.get(1).increaseChilds();
                    try {
                        place(child);
                    } catch (IncorrectPositionException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
    public void place(Animal animal) throws IncorrectPositionException {
        if(!canMoveTo(animal.getPosition())){
            throw new IncorrectPositionException(animal.getPosition());
        }
        if(animals.containsKey(animal.getPosition())){
            animals.get(animal.getPosition()).add(animal);
        }
        else{
            animals.put(animal.getPosition(), new ArrayList<>(List.of(animal)));
        }
        notifyListeners(String.format("Animal placed at %s", animal.getPosition()));
    }

    //TODO: left and right wrapping
    public void move(Animal animal) {
        var oldPosition = animal.getPosition();
        animal.move( this);
        var newPosition = animal.getPosition();
        if(!newPosition.equals(oldPosition)){
            notifyListeners(String.format("Animal moves from %s to %s", oldPosition, newPosition));
        }
    }
    public abstract void grow();

    public abstract void removeDeadAnimals();


    public boolean isOccupied(Vector2d position) {
        return animals.containsKey(position) || grasses.containsKey(position);
    }

    public List<WorldElement> objectsAt(Vector2d position) {
        if(animals.containsKey(position)){
            return animals.get(position).stream()
                    .map(animal -> {return (WorldElement) animal;})
                    .toList();
        } else if (grasses.containsKey(position)) {
            return List.of((WorldElement) grasses.get(position));
        }
        return null;
    }

    public Collection<WorldElement> getElements() {
        Stream<WorldElement> animalsStream = animals.values().stream()
                .flatMap(Collection::stream)
                .map(animal -> {return (WorldElement) animal;});
        Stream<WorldElement> grassStream = grasses.values().stream()
                .map(grass -> {return (WorldElement) grass;});
        return Stream.concat(animalsStream, grassStream).toList();
    }

    public Boundary getCurrentBounds() {
        return bounds;
    }
    
    public int getID() {
        return id;
    }

    public void addListener(GlobeChangeListener other){
        listeners.add(other);
    }

    public void removeListener(GlobeChangeListener other){
        listeners.remove(other);
    }

    protected void notifyListeners(String message){
        for(GlobeChangeListener l: listeners){
            l.mapChanged(message);
        }
    }

}
