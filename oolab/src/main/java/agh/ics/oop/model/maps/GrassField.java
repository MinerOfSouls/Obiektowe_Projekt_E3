package agh.ics.oop.model.maps;

import agh.ics.oop.model.elements.Grass;
import agh.ics.oop.model.Vector2d;
import agh.ics.oop.model.elements.WorldElement;
import agh.ics.oop.model.util.RandomPositionGenerator;

import java.util.*;

import java.lang.Math;

public class GrassField extends AbstractWorldMap {
    private final Map<Vector2d, Grass> grasses = new HashMap<>();
    private int time;

    public GrassField(int grassAmount, int givenID){
        super(givenID);
        int limit = (int) Math.sqrt(grassAmount*10) + 1;
        RandomPositionGenerator randomPositionGenerator = new RandomPositionGenerator(new Boundary(new Vector2d(0,0),
                new Vector2d(limit-1, limit-1)), grassAmount, Set.of(), List.of());
        for(Vector2d grassPosition : randomPositionGenerator) {
            grasses.put(grassPosition, new Grass(grassPosition));
        }
        time=0;

    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return (super.isOccupied(position) || grasses.containsKey(position));
    }

    @Override
    public WorldElement objectAt(Vector2d position) {
        WorldElement element = super.objectAt(position);
        if(element == null && isOccupied(position)){
            element = grasses.get(position);
        }
        return element;
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return !super.isOccupied(position);
    }

    @Override
    public Collection<WorldElement> getElements(){
        Collection<WorldElement> elements = super.getElements();
        elements.addAll(grasses.values());
        return elements;
    }

    @Override
    public Boundary getCurrentBounds() {
        Vector2d bottomBoundary = null;
        Vector2d topBoundary = null;
        for ( WorldElement element : this.getElements()){
            Vector2d position = element.getPosition();
            if(bottomBoundary!=null){bottomBoundary=bottomBoundary.lowerLeft(position);
            } else{bottomBoundary=position;}
            if(topBoundary!=null){topBoundary=topBoundary.upperRight(position);
            } else{topBoundary=position;}
        }
        return new Boundary(bottomBoundary,topBoundary);
    }
    public void increaseTime() {
        this.time +=1;
    }
}
