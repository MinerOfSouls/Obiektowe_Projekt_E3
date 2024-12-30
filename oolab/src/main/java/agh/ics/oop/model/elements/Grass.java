package agh.ics.oop.model.elements;

import agh.ics.oop.model.Vector2d;

public class Grass implements WorldElement {
    private Vector2d position;

    public Grass(Vector2d givenPosition){
        position=givenPosition;
    }

    public String toString(){
        return "*";
    }

    @Override
    public boolean isAt(Vector2d other) {
        return position.equals(other);
    }

    @Override
    public Vector2d getPosition() {
        return position;
    }

}
