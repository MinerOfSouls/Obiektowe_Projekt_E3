package agh.ics.oop.model;

import agh.ics.oop.model.elements.Animal;
import agh.ics.oop.model.elements.Grass;
import agh.ics.oop.model.elements.WorldElement;
import agh.ics.oop.model.maps.GrassField;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GrassFieldTest {

    @Test
    void constructorCratesCorrectNumberOfGrasses(){
        GrassField fieldA = new GrassField(6,1);
        GrassField fieldB = new GrassField(40,2);
        assertEquals(6, fieldA.getElements().size());
        assertEquals(40, fieldB.getElements().size());
    }

    @Test
    void getElementsReturnsAnimalsAndGrasses(){
        GrassField field = new GrassField(1,1);
        Animal cat = new Animal();
        try {
            field.place(cat);
        } catch (IncorrectPositionException e){
            e.printStackTrace();
        }
        assertTrue(field.getElements().contains(cat));
        assertTrue(field.getElements().stream().anyMatch(g -> g instanceof Grass));
    }

    @Test
    void animalsMoveOverGrass(){
        GrassField field = new GrassField(1,1);
        Vector2d grassLocation = field.getElements().stream().toList().get(0).getPosition();
        Animal cat = new Animal(grassLocation.subtract(new Vector2d(0,1)));
        try {
            field.place(cat);
        } catch (IncorrectPositionException e){
            e.printStackTrace();
        }
        field.move(cat,MoveDirection.FORWARD);
        field.move(cat,MoveDirection.FORWARD);
        assertTrue(cat.isAt(grassLocation.add(new Vector2d(0,1))));
    }

    @Test
    void grassIsContainedInCorrectArea(){
        GrassField field = new GrassField(10,1);
        Vector2d lowerLeft = new Vector2d(0,0);
        Vector2d upperRight = new Vector2d(10,10);
        for(WorldElement grass : field.getElements()){
            assertTrue(grass.getPosition().precedes(upperRight));
            assertTrue(grass.getPosition().follows(lowerLeft));
        }
    }
}