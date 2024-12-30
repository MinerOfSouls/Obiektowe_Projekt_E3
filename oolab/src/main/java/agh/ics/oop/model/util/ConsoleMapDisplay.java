package agh.ics.oop.model.util;

import agh.ics.oop.model.MapChangeListener;
import agh.ics.oop.model.maps.WorldMap;

public class ConsoleMapDisplay implements MapChangeListener {
    private int changesCount = 0;
    @Override
    public void mapChanged(WorldMap worldMap, String message) {
        synchronized (System.out) {
            changesCount += 1;
            System.out.printf("ID mapy: %d %n", worldMap.getID());
            System.out.println(message);
            System.out.println(worldMap.toString());
            System.out.printf("Liczba zmian: %d %n", changesCount);
        }
    }
}
