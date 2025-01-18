package agh.ics.oop.model.elements;

import agh.ics.oop.model.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
public class Animal implements WorldElement {
    private MapDirection facing;
    private Vector2d position;
    private List<Integer> genome = new ArrayList<>();
    private List<Animal> childrens = new ArrayList<>();
    private int minimalMutations ;
    private int maximalMutations ;
    public int currentGenomeIndex;
    private int energy ;
    private int bornTime;
    private int childs;

    public Animal() {
        position = new Vector2d(2, 2);
    }

    public Animal(Vector2d given_position) {
        position = given_position;
    }

    public Animal(Vector2d given_position, List<Integer> genome,int time, int energy) {
        childs=0;
        bornTime=time;
        this.energy=energy;
        position = given_position;
        this.genome = genome;
    }

    public Animal(Vector2d given_position, Animal parent1, Animal parent2,
                  int given_energy,int time, int minimalMutations, int maximalMutations) {
        position = given_position;
        this.minimalMutations = minimalMutations;
        this.maximalMutations = maximalMutations;
        this.energy=given_energy;
        genome = combineGenomes(parent1, parent2);
        MapDirection randomDirection = MapDirection.NORTH;
        int randomNum= new Random().nextInt(genome.size());
        for (int i = 0; i < randomNum; i++) {
            randomDirection = randomDirection.next();
        }
        currentGenomeIndex=randomNum;
        bornTime=time;

    }
    public void addChild(Animal child){
        childrens.add(child);
    }
    public void increaseChilds(){
        childs++;
    }
    public int getBornTime(){
        return bornTime;
    }
    public int getNumberOfChildrens(){
        return childs;
    }
    @Override
    public String toString() {
        return String.format("%s", facing);
    }

    public boolean isAt(Vector2d other_position) {
        return this.position.equals(other_position);
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int newEnergy) {
        energy = newEnergy;
    }

    public Vector2d getPosition() {
        return position;
    }

    public MapDirection getFacing() {
        return facing;
    }

    public void move(MoveValidator movementMap) {
        for (int i = 0; i < genome.get(currentGenomeIndex); i++) {
            facing = facing.next();
        }

        var next_position = position.add(facing.toUnitVector());
        if (movementMap.canMoveTo(next_position)) {
            position = next_position;
        }
        int random = new Random().nextInt(100);
        if(random<=80) {
            currentGenomeIndex = (currentGenomeIndex + 1) % genome.size();
        }
        else{
            currentGenomeIndex = new Random().nextInt(genome.size());
        }

    }
    public List<Integer> getGenome() {
        return genome;
    }
    private List<Integer> combineGenomes(Animal parent1, Animal parent2) {
        List<Integer> genome1 = parent1.getGenome();
        List<Integer> genome2 = parent2.getGenome();
        List<Integer> childGenome = new ArrayList<>();

        int energy1 = parent1.getEnergy();
        int energy2 = parent2.getEnergy();
        int totalEnergy = energy1 + energy2;


        double ratio1 = (double) energy1 / totalEnergy;
        double ratio2 = (double) energy2 / totalEnergy;


        int splitPoint = (int) (genome1.size() * ratio1);
        boolean takeRightSide = new Random().nextBoolean();

        if (takeRightSide) {
            childGenome.addAll(genome1.subList(0, splitPoint));
            childGenome.addAll(genome2.subList(splitPoint, genome2.size()));
        } else {
            childGenome.addAll(genome2.subList(0, splitPoint));
            childGenome.addAll(genome1.subList(splitPoint, genome1.size()));
        }
        int mutations = new Random().nextInt(maximalMutations - minimalMutations) + minimalMutations;
        for (int i = 0; i < mutations; i++) {
            int mutationIndex = new Random().nextInt(childGenome.size());
            int mutationValue = new Random().nextInt(8);
            childGenome.set(mutationIndex, mutationValue);
        }

        return childGenome;
    }
}
