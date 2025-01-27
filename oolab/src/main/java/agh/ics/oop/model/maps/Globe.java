package agh.ics.oop.model.maps;

import agh.ics.oop.model.IncorrectPositionException;
import agh.ics.oop.model.MoveDirection;
import agh.ics.oop.model.MoveValidator;
import agh.ics.oop.model.Vector2d;
import agh.ics.oop.model.elements.Animal;
import agh.ics.oop.model.elements.WorldElement;

import java.util.Collection;
import java.util.List;

public interface Globe extends MoveValidator { // czy ten interfejs coś wnosi, jak jest klasa abstrakcyjna?
    public boolean canMoveTo(Vector2d position);

    public void place(Animal animal) throws IncorrectPositionException;

    public void move(Animal animal);

    public void grow(int amount);

    public void removeDeadAnimals();

    public boolean isOccupied(Vector2d position);

    public List<WorldElement> objectsAt(Vector2d position);

    public Collection<WorldElement> getElements();

    public Boundary getCurrentBounds();

    public int getID();
}
