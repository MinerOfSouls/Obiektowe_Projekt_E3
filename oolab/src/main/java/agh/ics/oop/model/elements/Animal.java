package agh.ics.oop.model.elements;

import agh.ics.oop.model.MapDirection;
import agh.ics.oop.model.MoveDirection;
import agh.ics.oop.model.MoveValidator;
import agh.ics.oop.model.Vector2d;

public class Animal implements WorldElement {
    private MapDirection facing = MapDirection.NORTH;
    private Vector2d position;

    public Animal() {
        position = new Vector2d(2, 2);
    }
    public Animal(Vector2d given_position) {
        position = given_position;
    }

    @Override
    public String toString() {
        return String.format("%s", facing);
    }

    public boolean isAt(Vector2d other_position) {
        return this.position.equals(other_position);
    }

    public Vector2d getPosition(){
        return position;
    }

    public MapDirection getFacing(){
        return facing;
    }

    public void move(MoveDirection direction, MoveValidator movementMap){
        switch (direction) {
            case MoveDirection.RIGHT -> facing = facing.next();
            case MoveDirection.LEFT -> facing = facing.previous();
            case MoveDirection.FORWARD -> {
                var next_position = position.add(facing.toUnitVector());
                if (movementMap.canMoveTo(next_position)) {
                    position = next_position;
                }
            }
            case MoveDirection.BACKWARD -> {
                var next_position = position.subtract(facing.toUnitVector());
                if (movementMap.canMoveTo(next_position)) {
                    position = next_position;
                }
            }
        }
    }


}