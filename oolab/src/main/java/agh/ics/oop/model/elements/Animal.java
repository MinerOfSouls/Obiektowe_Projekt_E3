package agh.ics.oop.model.elements;

import agh.ics.oop.model.*;
import agh.ics.oop.presenter.MainWindowPresenter;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
public class Animal implements WorldElement {
    private MapDirection facing;
    private Vector2d position;
    private List<Integer> genome = new ArrayList<>();
    private List<Animal> childrens = new ArrayList<>();
    private boolean nextGenomeVariant;
    private int minimalMutations ;
    private int maximalMutations ;
    public int currentGenomeIndex;
    private int energy ;
    private int bornTime;
    private int plantsEaten ;

    private int childs;
    private int deadTime;

    public Animal() {
        position = new Vector2d(2, 2);
    }

    public Animal(Vector2d given_position) {
        position = given_position;
    }

    public Animal(Vector2d given_position, List<Integer> genome,
                  int given_energy,int time, int minimalMutations, int maximalMutations,
                  boolean nextGenomeVariant) {
        childs=0;
        plantsEaten=0;
        position = given_position;
        this.nextGenomeVariant=nextGenomeVariant;
        this.minimalMutations = minimalMutations;
        this.maximalMutations = maximalMutations;
        this.energy=given_energy;
        bornTime=time;
        currentGenomeIndex=0;
        this.genome = genome;
        MapDirection randomDirection = MapDirection.NORTH;
        int randomNum= new Random().nextInt(genome.size());
        for (int i = 0; i < randomNum; i++) {
            randomDirection = randomDirection.next();
        }
        facing=randomDirection;
    }

    public Animal(Vector2d given_position, Animal parent1, Animal parent2,
                  int given_energy,int time, int minimalMutations, int maximalMutations,
                  boolean nextGenomeVariant) {
        position = given_position;
        this.nextGenomeVariant=nextGenomeVariant;
        this.minimalMutations = minimalMutations;
        this.maximalMutations = maximalMutations;
        this.energy=given_energy;
        plantsEaten=0;
        genome = combineGenomes(parent1, parent2);
        MapDirection randomDirection = MapDirection.NORTH;
        int randomNum= new Random().nextInt(genome.size());
        for (int i = 0; i < randomNum; i++) {
            randomDirection = randomDirection.next();
        }
        facing=randomDirection;
        currentGenomeIndex=randomNum;
        bornTime=time;

    }
    public void setPosition(Vector2d position){
        this.position=position;
    }
    public void setDeathTime(int time){
        deadTime=time;
    }
    public int getDeathTime(){
        return deadTime;
    }

    public void increasePlantsEaten(){
        plantsEaten++;
    }
    public int getPlantsEaten(){
        return plantsEaten;
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

    @Override
    public Image getTexture() {
        return MainWindowPresenter.animalTextures.get(facing);
    }

    public MapDirection getFacing() {
        return facing;
    }

    public void move(MoveValidator movementMap) {
        for (int i = 0; i < genome.get(currentGenomeIndex); i++) {
            facing = facing.next();
        }

        var next_position = position.add(facing.toUnitVector());
        int width=movementMap.getCurrentBounds().upperRight().getX();
        int height=movementMap.getCurrentBounds().upperRight().getY();
        if(next_position.getX() > width){
            System.out.println("jestem" + position);
            if(next_position.getY() >= height){
                position = new Vector2d(0, height-1);
            }
            else if(next_position.getY() <= -1){
                position = new Vector2d(0, 0);
            }
            else{
                position = new Vector2d(0, next_position.getY());
            }

            System.out.println("teplem" + position);

        }
        else if(next_position.getX() <= -1){
            System.out.println("jestem" + position);
            if(next_position.getY() >= height){
                position = new Vector2d(width-1, height-1);
            }
            else if(next_position.getY() <= -1){
                position = new Vector2d(width-1, 0);
            }
            else{
                position = new Vector2d(width, next_position.getY());
            }

            System.out.println("teplem" + position);
        }
        else if(next_position.getY() >= height || next_position.getY() <= -1){

        }
        else {
            if (movementMap.canMoveTo(next_position)) {
                position = next_position;
            }

        }
        int random = new Random().nextInt(100);

        if (random <= 80 && nextGenomeVariant) {
            currentGenomeIndex = (currentGenomeIndex + 1) % genome.size();
        } else {
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
