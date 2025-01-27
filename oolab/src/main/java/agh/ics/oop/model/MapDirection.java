package agh.ics.oop.model;

import java.util.Map;

public enum MapDirection {
    NORTH,
    NORTHEAST,

    EAST,

    SOUTHEAST,
    SOUTH,

    SOUTHWEST,

    WEST,
    NORTHWEST; // po co te puste linie?

    public MapDirection next() {
        switch (this) {
            case NORTH -> {
                return NORTHEAST;
            }
            case NORTHEAST -> {
                return EAST;
            }
            case EAST -> {
                return SOUTHEAST;
            }
            case SOUTHEAST -> {
                return SOUTH;
            }
            case SOUTH -> {
                return SOUTHWEST;
            }
            case SOUTHWEST -> {
                return WEST;
            }
            case WEST -> {
                return NORTHWEST;
            }
            case NORTHWEST -> {
                return NORTH;
            }
        }
        return null;
    }

    public MapDirection previous() {
        switch (this) {
            case NORTH -> {
                return NORTHWEST;
            }
            case NORTHWEST -> {
                return WEST;
            }
            case WEST -> {
                return SOUTHWEST;
            }
            case SOUTHWEST -> {
                return SOUTH;
            }
            case SOUTH -> {
                return SOUTHEAST;
            }
            case SOUTHEAST -> {
                return EAST;
            }
            case EAST -> {
                return NORTHEAST;
            }
            case NORTHEAST -> {
                return NORTH;
            }
        }
        return null;
    }

    public Vector2d toUnitVector() {
        switch (this) {
            case NORTH -> {
                return new Vector2d(0, 1); // nowy obiekt co wywołanie?
            }
            case WEST -> {
                return new Vector2d(-1, 0);
            }
            case SOUTH -> {
                return new Vector2d(0, -1);
            }
            case EAST -> {
                return new Vector2d(1, 0);
            }
            case NORTHEAST -> {
                return new Vector2d(1, 1);
            }
            case NORTHWEST -> {
                return new Vector2d(-1, 1);
            }
            case SOUTHEAST -> {
                return new Vector2d(1, -1);
            }
            case SOUTHWEST -> {
                return new Vector2d(-1, -1);
            }

        }
        return null;
    }


}
