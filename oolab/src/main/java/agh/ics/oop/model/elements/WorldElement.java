package agh.ics.oop.model.elements;

import agh.ics.oop.model.Vector2d;

public interface WorldElement {
    String toString();

    boolean isAt(Vector2d other);

    Vector2d getPosition();
}
