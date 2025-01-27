package agh.ics.oop.model.maps;

import agh.ics.oop.model.IncorrectPositionException;
import agh.ics.oop.model.Vector2d;
import agh.ics.oop.model.elements.Animal;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EquatorGlobeTest {
    @Test
    void growGrowsCorrectGrassAmount() {
        EquatorGlobe map = new EquatorGlobe(0, 10, 10, 0, 10, 1, 1, 1, false, 1);
        map.grow(10);
        assertEquals(10, map.getGrassLocations().size());
    }

    @Test
    void deadAnimalRemoved() {
        EquatorGlobe map = new EquatorGlobe(0, 10, 10, 0, 10, 1, 1, 1, false, 1);
        try {
            map.place(new Animal(new Vector2d(2, 2), List.of(1, 2, 3, 4), 0, 0, 1, 1, false));
            map.place(new Animal(new Vector2d(2, 2), List.of(1, 2, 3, 4), 10, 0, 1, 1, false));
        } catch (IncorrectPositionException e) {
            fail();
        }
        map.removeDeadAnimals();
        assertEquals(1, map.getAnimals().size());
    }

    @Test
    void animalsBreed() {
        EquatorGlobe map = new EquatorGlobe(0, 10, 10, 0, 1, 1, 1, 2, false, 1);
        try {
            map.place(new Animal(new Vector2d(2, 2), List.of(1, 2, 3, 4), 10, 0, 1, 2, false));
            map.place(new Animal(new Vector2d(2, 2), List.of(1, 2, 3, 4), 10, 0, 1, 2, false));
        } catch (IncorrectPositionException e) {
            fail();
        }
        map.animalBreeding();
        assertEquals(3, map.getAnimals().size());
    }

    @Test
    void animalsMove() {
        EquatorGlobe map = new EquatorGlobe(0, 10, 10, 0, 1, 1, 1, 1, false, 1);
        Animal owl = new Animal(new Vector2d(2, 2), List.of(0), 10, 0, 1, 2, false);
        Animal bear = new Animal(new Vector2d(2, 2), List.of(4), 10, 0, 1, 2, false);
        try {
            map.place(owl);
            map.place(bear);
        } catch (IncorrectPositionException e) {
            fail();
        }
        map.move(owl);
        map.move(bear);
        assertEquals(new Vector2d(2, 3), owl.getPosition());
        assertEquals(new Vector2d(2, 1), bear.getPosition());
    }

    @Test
    void animalsEat() {
        EquatorGlobe map = new EquatorGlobe(0, 10, 10, 101, 1, 1, 1, 1, false, 1);
        Animal owl = new Animal(new Vector2d(2, 2), List.of(1, 2, 3, 4), 10, 0, 1, 1, false);
        try {
            map.place(owl);
        } catch (IncorrectPositionException e) {
            fail();
        }
        map.eatIfPossible(owl);
        assertFalse(map.getGrassLocations().contains(new Vector2d(2, 2)));
    }
}