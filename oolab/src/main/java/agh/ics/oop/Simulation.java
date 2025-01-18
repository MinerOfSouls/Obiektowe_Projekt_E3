package agh.ics.oop;
import agh.ics.oop.model.*;
import agh.ics.oop.model.elements.Animal;
import agh.ics.oop.model.maps.WorldMap;

import java.util.ArrayList;
import java.util.List;

public class Simulation implements Runnable {
    private List<Animal> animals = new ArrayList<>();
    private List<MoveDirection> moves;
    private WorldMap map;

    public Simulation(List<Vector2d> startingPositions,List<MoveDirection> commands, WorldMap givenMap){
        this.moves=commands;
        this.map = givenMap;
        for(Vector2d position : startingPositions){
            try {
                var new_animal = new Animal(position);
                map.place(new_animal);
                this.animals.add(new_animal);
            } catch (IncorrectPositionException e){
                e.printStackTrace();
            }
        }
    }
    public void run(){
        int current=0;
        try {
            while(true) {
                map.increaseTime();
                map.move(animals.get(current));
                current = (current + 1) % animals.size();
                if(current== animals.size()-1){
                    map.animalBreeding();
                }
                Thread.sleep(500);
            }
        } catch (InterruptedException e){
            //ignored
        }
    }


}
