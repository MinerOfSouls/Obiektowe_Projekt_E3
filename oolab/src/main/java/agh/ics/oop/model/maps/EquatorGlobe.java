package agh.ics.oop.model.maps;

import agh.ics.oop.model.Vector2d;
import agh.ics.oop.model.elements.Grass;
import agh.ics.oop.model.util.RandomPositionGenerator;

import java.util.Iterator;
import java.util.List;

public class EquatorGlobe extends AbstractGlobeMap {

    private Boundary plentifulAreaBounds;
    private List<Boundary> steppeBounds;

    public EquatorGlobe(int givenId, int givenWidth, int givenHeight, int startingPlantAmount) {
        super(givenId, givenWidth, givenHeight, startingPlantAmount);

        plentifulAreaBounds = new Boundary(new Vector2d(0, (bounds.lowerLeft().getY()*2)/5),
                new Vector2d(bounds.upperRight().getX(), (bounds.upperRight().getY()*3)/5));
        steppeBounds = List.of(new Boundary(new Vector2d(0,0),
                        new Vector2d(bounds.upperRight().getX(),((bounds.lowerLeft().getY()*2)/5)-1)),
                        new Boundary(new Vector2d(0, (((bounds.upperRight().getY()*3)/5)+1)), bounds.upperRight()));
    }

    @Override
    public void grow(int amount) {
        RandomPositionGenerator equatorGenerator = new RandomPositionGenerator(bounds, amount, grasses.keySet(), steppeBounds);
        RandomPositionGenerator steppeGenerator = new RandomPositionGenerator(bounds, amount, grasses.keySet(), List.of(plentifulAreaBounds));
        Iterator equatorIterator = equatorGenerator.iterator();
        Iterator steppeIterator = steppeGenerator.iterator();
        for (int i = 0; i < amount; i++) {
            int choice = randomizer.nextInt(99);
            if (choice < 20 && steppeIterator.hasNext()) {
                Vector2d position = (Vector2d) steppeIterator.next();
                grasses.put(position, new Grass(position));
                notifyListeners(String.format("Grass grows at %s", position));
            } else if (equatorIterator.hasNext()) {
                Vector2d position = (Vector2d) equatorIterator.next();
                grasses.put(position, new Grass(position));
                notifyListeners(String.format("Grass grows at %s", position));
            } else if (steppeIterator.hasNext()) {
                Vector2d position = (Vector2d) steppeIterator.next();
                grasses.put(position, new Grass(position));
                notifyListeners(String.format("Grass grows at %s", position));
            } else {
                break;
            }
        }
    }

    //TODO: implement when animals added
    @Override
    public void removeDeadAnimals() {

    }
}
