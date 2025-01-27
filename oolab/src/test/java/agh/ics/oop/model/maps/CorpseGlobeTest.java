package agh.ics.oop.model.maps;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CorpseGlobeTest {

    @Test
    void corpseGrowGrowsCorrectGrassAmount() {
        CorpseGlobe map = new CorpseGlobe(0, 10, 10, 0, 10, 1, 1, 0, 1, false, 1);
        map.grow(10);
        assertEquals(10, map.getGrassLocations().size());
    }

}