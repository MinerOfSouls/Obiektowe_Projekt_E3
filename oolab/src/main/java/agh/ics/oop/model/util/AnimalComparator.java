package agh.ics.oop.model.util;

import agh.ics.oop.model.elements.Animal;

import java.util.Comparator;
import java.util.Random;

public class AnimalComparator implements Comparator<Animal> {
    private final Random random = new Random();

    @Override
    public int compare(Animal a1, Animal a2) {
        int energyComparison = Integer.compare(a2.getEnergy(), a1.getEnergy());
        if (energyComparison != 0) {
            return energyComparison;
        }

        int ageComparison = Integer.compare(a1.getBornTime(), a2.getBornTime());
        if (ageComparison != 0) {
            return ageComparison;
        }


        int childrenComparison = Integer.compare(a2.getNumberOfChildrens(), a1.getNumberOfChildrens());
        if (childrenComparison != 0) {
            return childrenComparison;
        }

        return random.nextInt(2) * 2 - 1;
    }
}