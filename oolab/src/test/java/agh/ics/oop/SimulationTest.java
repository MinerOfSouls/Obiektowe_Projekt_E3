package agh.ics.oop;

import agh.ics.oop.model.MoveDirection;
import agh.ics.oop.model.maps.RectangularMap;
import agh.ics.oop.model.Vector2d;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SimulationTest {

    @Test
    void placedAnimalsInCorrectPositionsOnMap(){
        RectangularMap map = new RectangularMap(5,5,1);
        List<Vector2d> animalPositions = List.of(new Vector2d(2,2), new Vector2d(3,4),new Vector2d(0,0), new Vector2d(4,1));
        List<MoveDirection> notAplicable = List.of(MoveDirection.FORWARD);
        Simulation simulation = new Simulation(animalPositions,notAplicable,map);
        for (int i = 0; i < 4; i++) {
            assertEquals(animalPositions.get(i), map.objectAt(animalPositions.get(i)).getPosition());
        }
    }
    @Test
    void runMovesAnimalsToCorrectPositions(){
        String[] commands = {"f", "b", "r", "l", "f", "f", "r", "r", "f", "f", "f", "f", "f", "f", "f", "f"};
        List<MoveDirection> directions = OptionsParser.parseMoveDirections(commands);
        List<Vector2d> positions = List.of(new Vector2d(2,2), new Vector2d(3,4));
        RectangularMap map = new RectangularMap(4,4,1);
        Simulation simulation = new Simulation(positions, directions, map);
        simulation.run();
        var a = new Vector2d(2,0);
        var b = new Vector2d(3,4);
        assertTrue(map.isOccupied(a));
        assertTrue(map.isOccupied(a));
    }

}