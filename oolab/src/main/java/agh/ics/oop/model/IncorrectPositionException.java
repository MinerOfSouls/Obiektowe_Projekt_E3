package agh.ics.oop.model;

public class IncorrectPositionException extends Exception {
    public IncorrectPositionException(Vector2d incorrectPosition) {
        super(String.format("Position %s is not correct", incorrectPosition));
    }
}
