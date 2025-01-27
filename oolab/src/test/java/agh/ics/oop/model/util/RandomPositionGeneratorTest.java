package agh.ics.oop.model.util;

import agh.ics.oop.model.Vector2d;
import agh.ics.oop.model.maps.Boundary;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RandomPositionGeneratorTest {

    private Boundary testRegionBounds = new Boundary(new Vector2d(0, 0), new Vector2d(4, 4));

    @Test
    void randomPositionsNotInExcludedRegions() {
        Boundary excludedRegion = new Boundary(new Vector2d(1, 1), new Vector2d(2, 2));
        RandomPositionGenerator generator = new RandomPositionGenerator(testRegionBounds, 25, List.of(),
                List.of(excludedRegion));
        for (Vector2d position : generator) {
            assertFalse(position.follows(excludedRegion.lowerLeft()) && position.precedes(excludedRegion.upperRight()));
        }
    }

    @Test
    void randomPositionsNotInExcludedPositions() {
        List<Vector2d> excludedPositions = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            excludedPositions.add(new Vector2d(1, i));
            excludedPositions.add(new Vector2d(4, i));
        }
        RandomPositionGenerator generator = new RandomPositionGenerator(testRegionBounds, 25, excludedPositions, null);

        for (Vector2d position : generator) {
            assertFalse(excludedPositions.contains(position));
        }
    }

}