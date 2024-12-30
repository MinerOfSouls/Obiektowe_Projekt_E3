package agh.ics.oop.model;

import agh.ics.oop.model.elements.Animal;
import agh.ics.oop.model.elements.WorldElement;
import agh.ics.oop.model.maps.RectangularMap;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class RectangularMapTest {
    @Test
    void mapBoundariesAreSetCorrectlyAtStart(){
        var map = new RectangularMap(5,9,1);
        var expected = new Vector2d(4,8);
        assertEquals(expected,map.getCurrentBounds().upperRight());
    }
    @Test
    void isOccupiedReturnsTrueWhenSpaceIsOccupiedAndFalseWHenNot(){
        var map = new RectangularMap(4,4,1);
        var cat = new Animal();
        try {
            map.place(cat);
        } catch (IncorrectPositionException e){
            e.printStackTrace();
        }
        assertTrue(map.isOccupied(cat.getPosition()));
    }
    @Test
    void canMoveToReturnsFalseWhenMoveIsInvalid(){
        var map = new RectangularMap(4,4,1);
        var cat = new Animal();
        try {
            map.place(cat);
        } catch (IncorrectPositionException e){
            e.printStackTrace();
        }
        assertFalse(map.canMoveTo(cat.getPosition()));
        assertFalse(map.canMoveTo(new Vector2d(10,10)));
        assertFalse(map.canMoveTo(new Vector2d(-10,-1)));
    }
    @Test
    void canMoveToReturnsTrueForValidMoves(){
        var map = new RectangularMap(5,5,1);
        assertTrue(map.canMoveTo(new Vector2d(2,2)));
        assertTrue(map.canMoveTo(new Vector2d(3,3)));
        assertTrue(map.canMoveTo(new Vector2d(0,0)));
        assertTrue(map.canMoveTo(new Vector2d(0,4)));
    }
    @Test
    void placePlacesAnimalsInCorrectSpots(){
        var map = new RectangularMap(5,5,1);
        var cat = new Animal();
        var dog = new Animal();
        var owl = new Animal(new Vector2d(4,2));
        var bear = new Animal(new Vector2d(10,10));
        assertDoesNotThrow(()->{map.place(cat);});
        assertDoesNotThrow(()->{map.place(owl);});
        assertThrows(IncorrectPositionException.class,()->{map.place(dog);});
        assertThrows(IncorrectPositionException.class,()->{map.place(bear);});

    }
    @Test
    void objectAtReturnsCorrectObject(){
        var map = new RectangularMap(4,4,1);
        var cat = new Animal();
        try {
            map.place(cat);
        } catch (IncorrectPositionException e){
            e.printStackTrace();
        }
        assertEquals(cat,map.objectAt(new Vector2d(2,2)));
    }
    @Test
    void moveCantMoveAnimalOutOfTheMap(){
        var map = new RectangularMap(5,5,1);
        var cat = new Animal();
        try {
            map.place(cat);
        } catch (IncorrectPositionException e){
            e.printStackTrace();
        }
        for (int i = 0; i < 5; i++) {
            map.move(cat,MoveDirection.FORWARD);
        }
        assertTrue(cat.isAt(new Vector2d(2,4)));
    }
    @Test
    void moveMakesAnimalMoveAndTurn(){
        var map = new RectangularMap(4,4,1);
        var cat = new Animal();
        try {
            map.place(cat);
        } catch (IncorrectPositionException e){
            e.printStackTrace();
        }
        map.move(cat,MoveDirection.FORWARD);
        map.move(cat,MoveDirection.LEFT);
        map.move(cat,MoveDirection.FORWARD);
        map.move(cat,MoveDirection.FORWARD);
        map.move(cat,MoveDirection.RIGHT);
        map.move(cat,MoveDirection.BACKWARD);
        assertTrue(cat.isAt(new Vector2d(0,2)));
    }

    @Test
    void getElementsReturnsAllAnimals(){
        var map = new RectangularMap(4,4,1);
        var cat = new Animal(new Vector2d(0,0));
        var dog = new Animal(new Vector2d(1,1));
        var owl = new Animal(new Vector2d(2,2));
        var bear = new Animal(new Vector2d(3,3));
        var owlbear = new Animal();
        try {
            map.place(cat);
            map.place(dog);
            map.place(owl);
            map.place(bear);
        } catch (IncorrectPositionException e){
            e.printStackTrace();
        }
        Collection<WorldElement> elements = map.getElements();
        assertTrue(elements.contains(cat));
        assertTrue(elements.contains(dog));
        assertTrue(elements.contains(owl));
        assertTrue(elements.contains(bear));
        assertFalse(elements.contains(owlbear));
    }
}