package agh.ics.oop.model.util;

import agh.ics.oop.model.Vector2d;
import agh.ics.oop.model.maps.Boundary;

import java.util.*;

public class RandomPositionGenerator implements Iterable<Vector2d>{
    private List<Vector2d> positions = new ArrayList<>();
    private int amount;

    public RandomPositionGenerator(Boundary coveredRegion, int amountGiven,  Collection<Vector2d> excludedLocations,
        List<Boundary> excludedRegions){
        amount=amountGiven;
        for (int i = coveredRegion.lowerLeft().getX(); i <= coveredRegion.upperRight().getX(); i++) {
            for (int j = coveredRegion.lowerLeft().getY(); j <= coveredRegion.upperRight().getY(); j++) {
                var vector = new Vector2d(i,j);
                if(checkVectorAvailability(vector, excludedLocations, excludedRegions)){
                    positions.add(vector);
                }
            }
        }
    }

    private boolean checkVectorAvailability(Vector2d vector, Collection<Vector2d> excludedLocations, List<Boundary> excludedRegions){
        if(excludedLocations.contains(vector)){
            return false;
        }
        if(excludedRegions == null){
            return true;
        }
        for(Boundary bounds: excludedRegions){
            if(vector.follows(bounds.lowerLeft()) && vector.precedes(bounds.upperRight())){
                return false;
            }
        }
        return true;
    }

    @Override
    public Iterator iterator() {
        return new RandomPositionIterator(positions,amount);
    }

    private class RandomPositionIterator implements Iterator<Vector2d> {
        int iteratorEnd;
        int counter = 0;
        List<Vector2d> positions;
        Random randomizer = new Random();

        public RandomPositionIterator(List<Vector2d> positionsToRandomise, int outputAmount){
            iteratorEnd=Math.min(outputAmount, positionsToRandomise.size());
            positions=positionsToRandomise;
        }

        @Override
        public boolean hasNext() {
            return counter<iteratorEnd && !positions.isEmpty();
        }

        @Override
        public Vector2d next() {
            int index = randomizer.nextInt(positions.size());
            Vector2d output = positions.get(index);
            positions.remove(index);
            if(positions.isEmpty()){
                return output;
            }
            positions.add(index, positions.getLast());
            positions.removeLast();
            counter=counter+1;
            return output;
        }
    }
}
