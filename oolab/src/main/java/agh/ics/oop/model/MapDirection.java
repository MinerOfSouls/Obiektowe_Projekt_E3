package agh.ics.oop.model;

import java.util.Map;

public enum MapDirection {
    NORTH,
    SOUTH,
    WEST,
    EAST;
    public String toString() {
        switch (this){
            case NORTH -> {return "^";}
            case SOUTH -> {return "v";}
            case EAST -> {return ">";}
            case WEST -> {return "<";}
        }
        return null;
    }
    public MapDirection previous() {
        switch (this) {
            case NORTH -> {return WEST;}
            case WEST -> {return SOUTH;}
            case SOUTH -> {return EAST;}
            case EAST -> {return NORTH;}
        }
        return null;
    }
    public MapDirection next() {
        switch (this) {
            case NORTH -> {return EAST;}
            case WEST -> {return NORTH;}
            case SOUTH -> {return WEST;}
            case EAST -> {return SOUTH;}
        }
        return null;
    }
    public Vector2d toUnitVector() {
        switch (this) {
            case NORTH -> {return new Vector2d(0, 1);}
            case WEST -> {return new Vector2d(-1, 0);}
            case SOUTH -> {return new Vector2d(0, -1);}
            case EAST -> {return new Vector2d(1, 0);}
        }
        return null;
    }


}
