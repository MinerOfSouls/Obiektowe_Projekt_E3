package agh.ics.oop.model;

import agh.ics.oop.model.elements.Animal;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AnimalTest {
    @Test
    void isAtReturnsTrueWhenATCorrectCoordinatesAndFalseWhenNot(){
        var cat = new Animal();
        assertTrue(cat.isAt(new Vector2d(2,2)));
        assertFalse(cat.isAt(new Vector2d(1,1)));
    }
    @Test
    void OrientationIsNorthAtStart(){
        var cat = new Animal();
        assertEquals(MapDirection.NORTH,cat.getFacing());
    }
    @Test
    void AnimalPositionIsSetCorrectlyAtStart(){
        var position = new Vector2d(1,1);
        var cat = new Animal(position);
        assertTrue(cat.isAt(position));
    }
    @Test
    void toStringReturnsCorrectString(){
        var cat = new Animal();
        String teststring = "^";
        assertEquals(teststring,cat.toString());
    }


}