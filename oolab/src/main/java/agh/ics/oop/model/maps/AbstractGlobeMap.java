package agh.ics.oop.model.maps;

import agh.ics.oop.model.*;
import agh.ics.oop.model.elements.Animal;
import agh.ics.oop.model.elements.Grass;
import agh.ics.oop.model.elements.WorldElement;
import agh.ics.oop.model.util.AnimalComparator;

import java.util.*;
import java.util.stream.Stream;

public abstract class AbstractGlobeMap implements Globe {

    protected final Map<Vector2d, List<Animal>> animals = new HashMap<>();
    protected final Map<Vector2d, Grass> grasses = new HashMap<>();
    protected final List<GlobeChangeListener> listeners = new ArrayList<>();
    protected final List<Animal> deadAnimals = new ArrayList<>();
    protected int time;
    protected final int id;
    protected final boolean nextGenomeVariant;
    protected final int breadingEnergy;
    protected final int minimalMutations;
    protected final int maximalMutations;
    protected final int foodEnergy;
    protected final int parentBreadingEnergyLoose;
    protected final Boundary bounds;
    protected Random randomizer = new Random();

    public AbstractGlobeMap(int givenId, int givenWidth,
                            int givenHeight,
                            int givenBreadingEnergy,int givenParentBreadingEnergyLoose,
                            int givenMinimalMutations, int givenMaximalMutations,
                            boolean givenNextGenomeVariant,int givenFoodEnergy) {

            id = givenId;
            nextGenomeVariant = givenNextGenomeVariant;
            minimalMutations = givenMinimalMutations;
            maximalMutations = givenMaximalMutations;
            foodEnergy = givenFoodEnergy;
            parentBreadingEnergyLoose = givenParentBreadingEnergyLoose;
            breadingEnergy = givenBreadingEnergy;
            bounds = new Boundary(new Vector2d(0, 0), new Vector2d(givenWidth - 1, givenHeight - 1));
            time = 0;
        }

        public void increaseTime () {
            this.time += 1;
        }

        public boolean canMoveTo (Vector2d position){
            return bounds.upperRight().getY() >= position.getY() && bounds.lowerLeft().getY() <= position.getY();
        }
        public void animalBreeding () {
            for (List<Animal> animalList : animals.values()) {
                if (animalList.size() >= 2) {
                    animalList.sort(new AnimalComparator());
                    if (animalList.get(0).getEnergy() >= breadingEnergy &&
                            animalList.get(1).getEnergy() >= breadingEnergy) {
                        Vector2d childPosition = animalList.get(0).getPosition();
                        Animal child = new Animal(childPosition, animalList.get(0), animalList.get(1), 2 * parentBreadingEnergyLoose, time,
                                minimalMutations, maximalMutations, nextGenomeVariant);
                        animalList.get(0).setEnergy((int) (animalList.get(0).getEnergy() - parentBreadingEnergyLoose));
                        animalList.get(1).setEnergy((int) (animalList.get(1).getEnergy() - parentBreadingEnergyLoose));
                        animalList.get(0).increaseChilds();
                        animalList.get(1).increaseChilds();
                        animalList.get(0).addChild(child);
                        animalList.get(1).addChild(child);
                        try {
                            place(child);
                        } catch (IncorrectPositionException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        public void eatIfPossible (Animal animal){
            if (grasses.containsKey(animal.getPosition())) {
                animal.setEnergy(animal.getEnergy() + foodEnergy);
                grasses.remove(animal.getPosition());
                animal.increasePlantsEaten();
            }
        }
        public void place (Animal animal) throws IncorrectPositionException {
            if (!canMoveTo(animal.getPosition())) {
                throw new IncorrectPositionException(animal.getPosition());
            }
            if (animals.containsKey(animal.getPosition())) {
                animals.get(animal.getPosition()).add(animal);
            } else {
                animals.put(animal.getPosition(), new ArrayList<>(List.of(animal)));
            }
            notifyListeners(String.format("Animal placed at %s", animal.getPosition()));
        }

        //TODO: left and right wrapping
        public void move (Animal animal){
            var oldPosition = animal.getPosition();
            animal.move(this);
            var newPosition = animal.getPosition();
            if (!newPosition.equals(oldPosition)) {
                notifyListeners(String.format("Animal moves from %s to %s", oldPosition, newPosition));
            }
        }

        public abstract void grow ( int amount);

    public void removeDeadAnimals() {
        for (List<Animal> animalList : animals.values()) {
            Iterator<Animal> iterator = animalList.iterator();
            while (iterator.hasNext()) {
                Animal animal = iterator.next();
                if (animal.getEnergy() <= 0) {
                    deadAnimals.add(animal);
                    deadAnimals.get(deadAnimals.size() - 1).setDeathTime(time);
                    iterator.remove();
                }
            }
        }
    }


        public boolean isOccupied (Vector2d position){
            return animals.containsKey(position) || grasses.containsKey(position);
        }

        public List<WorldElement> objectsAt (Vector2d position){
            if (animals.containsKey(position)) {
                return animals.get(position).stream()
                        .map(animal -> {
                            return (WorldElement) animal;
                        })
                        .toList();
            } else if (grasses.containsKey(position)) {
                return List.of((WorldElement) grasses.get(position));
            }
            return null;
        }

        public Collection<WorldElement> getElements () {
            Stream<WorldElement> animalsStream = animals.values().stream()
                    .flatMap(Collection::stream)
                    .map(animal -> {
                        return (WorldElement) animal;
                    });
            Stream<WorldElement> grassStream = grasses.values().stream()
                    .map(grass -> {
                        return (WorldElement) grass;
                    });
            return Stream.concat(animalsStream, grassStream).toList();
        }

        public Boundary getCurrentBounds () {
            return bounds;
        }

        public int getID () {
            return id;
        }

        public void addListener (GlobeChangeListener other){
            listeners.add(other);
        }

        public void removeListener (GlobeChangeListener other){
            listeners.remove(other);
        }

        protected void notifyListeners (String message){
            for (GlobeChangeListener l : listeners) {
                l.mapChanged(this, message);
            }
        }

        public Collection<Vector2d> getGrassLocations () {
            return grasses.keySet();
        }

        public Collection<Animal> getTopAnimals () {
            List<Animal> topAnimals = new ArrayList<>();
            for (List<Animal> animalList : animals.values()) {
                topAnimals.add(
                        animalList.stream().max(new AnimalComparator()).get()
                );
            }
            return topAnimals;
        }

        public List<Animal> getAnimals () {
            return animals.values().stream().flatMap(Collection::stream).toList();
        }

    public abstract List<Vector2d> getPreferredSpaces();

}
