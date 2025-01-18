package agh.ics.oop;
import agh.ics.oop.model.*;
import agh.ics.oop.model.util.ConsoleMapDisplay;

import java.util.ArrayList;
import java.util.List;

public class World {
    private static void run(MoveDirection[] directions){
        for(MoveDirection where: directions) {
            switch (where) {
                case MoveDirection.FORWARD -> System.out.println("Zwierzak idzie do przodu");
                case MoveDirection.BACKWARD -> System.out.println("Zwierzak idzie do tyłu");
                case MoveDirection.RIGHT -> System.out.println("Zwierzak skręca w prawo");
                case MoveDirection.LEFT -> System.out.println("Zwierzak skręca w lewo");
            }
        }
    }

    public static void main(String[] args) {
        List<MoveDirection> directions = OptionsParser.parseMoveDirections(args);
        List<Vector2d> positions = List.of(new Vector2d(2,2), new Vector2d(3,4));

        MapChangeListener display = new ConsoleMapDisplay();

        ArrayList<Simulation> simulations = new ArrayList<>();

        for (int i = 0; i < 200; i++) {
            GrassField field = new GrassField(10,i);
            field.registerListener(display);
            Simulation simulation = new Simulation(positions, directions,field);
            simulations.add(simulation);
        }

        SimulationEngine engine = new SimulationEngine(simulations);
        engine.runAsyncInThreadPool();
        try {
            engine.awaitSimulationsEnd();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("System zakończył działanie");
    }
}
