package agh.ics.oop.model.util;

import agh.ics.oop.model.Vector2d;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class RandomPositionGenerator implements Iterable<Vector2d>{
    private List<Vector2d> positions = new ArrayList<>();
    private int amount;

    public RandomPositionGenerator(int width, int height, int amountGiven){
        amount=amountGiven;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                positions.add(new Vector2d(i,j));
            }
        }
    }

    @Override
    public Iterator iterator() {
        return new RandomPositionIterator(positions,amount);
    }

    private class RandomPositionIterator implements Iterator<Vector2d> {
        int iteratorEnd;
        int counter = 0;
        List<Vector2d> positions;
        Random randomiser = new Random();

        public RandomPositionIterator(List<Vector2d> positionsToRandomise, int outputAmount){
            iteratorEnd=outputAmount;
            positions=positionsToRandomise;
        }

        @Override
        public boolean hasNext() {
            return counter<iteratorEnd;
        }

        @Override
        public Vector2d next() {
            int index = randomiser.nextInt(positions.size());
            Vector2d output = positions.get(index);
            positions.remove(index);
            positions.add(index, positions.getLast());
            positions.removeLast();
            counter=counter+1;
            return output;
        }
    }
}
