package agh.ics.oop.model.maps;

import agh.ics.oop.model.Vector2d;

public class RectangularMap extends AbstractWorldMap {

    Boundary mapBounds;
    private int time;

    public RectangularMap(int givenWidth, int givenHeight, int givenID){
        super(givenID);
        mapBounds = new Boundary(new Vector2d(0,0),new Vector2d(givenWidth-1, givenHeight-1));
        time=0;
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return (!super.isOccupied(position) && position.follows(mapBounds.lowerLeft()) && position.precedes(mapBounds.upperRight()));
    }

    @Override
    public Boundary getCurrentBounds() {
        return mapBounds;
    }
    public void increaseTime(){
        time++;
    }
}
