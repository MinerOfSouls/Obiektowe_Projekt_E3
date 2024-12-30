package agh.ics.oop;

import agh.ics.oop.model.MoveDirection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class OptionsParser {
    public static List<MoveDirection> parseMoveDirections(String[] commands) {
        List<MoveDirection> directions = new ArrayList<MoveDirection>();
        int element=0;
        for (String command : commands) {
            switch (command) {
                case "f" -> directions.add(MoveDirection.FORWARD);
                case "b" -> directions.add(MoveDirection.BACKWARD);
                case "l" -> directions.add(MoveDirection.LEFT);
                case "r" -> directions.add(MoveDirection.RIGHT);
                default -> throw new IllegalArgumentException(command + " is not legal move specification");
            }
        }
        return directions;
    }
}
