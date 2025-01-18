package agh.ics.oop.model.maps;

import agh.ics.oop.model.Vector2d;
import agh.ics.oop.model.elements.Grass;
import agh.ics.oop.model.util.RandomPositionGenerator;

import java.util.*;

public class CorpseGlobe extends GlobeMap{

    private List<Vector2d> corpses = new ArrayList<>();
    private List<Integer> decayTimers = new ArrayList<>();
    private List<Vector2d> neighbours = List.of(new Vector2d(1, 0), new Vector2d(-1, 0),
            new Vector2d(0, 1), new Vector2d(0, -1));
    private final int decayTime;

    public CorpseGlobe(int givenId, int givenWidth, int givenHeight, int givenGrowthFactor, int givenDecayTime,int givenEnergy) {
        super(givenId, givenWidth, givenHeight, givenGrowthFactor,givenEnergy);
        decayTime = givenDecayTime;
    }

    private void removeDecayedCorpses(){
        for (int i = 0; i < corpses.size(); i++) {
            if(decayTimers.get(i) == 0){
                corpses.remove(i);
                decayTimers.remove(i);
                i--;
            }
        }
    }

    @Override
    public void grow() {
        List<Vector2d> corpseNeighbours = new ArrayList<>();
        for (Vector2d corpse : corpses) {
            for (Vector2d neighbour : neighbours) {
                corpseNeighbours.add(corpse.add(neighbour));
            }
        }
        Collections.shuffle(corpseNeighbours);
        Collection<Vector2d> excluded = grasses.keySet();
        excluded.addAll(corpseNeighbours);
        RandomPositionGenerator steppeGenerator = new RandomPositionGenerator(bounds, growthFactor, excluded, List.of());
        Iterator corpseIterator = corpseNeighbours.iterator();
        Iterator steppeIterator = steppeGenerator.iterator();
        for (int i = 0; i < growthFactor; i++) {
            int choice = randomizer.nextInt(99);
            if (choice < 20 && steppeIterator.hasNext()) {
                Vector2d position = (Vector2d) steppeIterator.next();
                grasses.put(position, new Grass(position));
                notifyListeners(String.format("Grass grows at %s", position));
            } else if (corpseIterator.hasNext()) {
                Vector2d position = (Vector2d) corpseIterator.next();
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
