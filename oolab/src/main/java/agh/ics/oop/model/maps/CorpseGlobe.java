package agh.ics.oop.model.maps;

import agh.ics.oop.model.Vector2d;
import agh.ics.oop.model.elements.Animal;
import agh.ics.oop.model.elements.Grass;
import agh.ics.oop.model.util.RandomPositionGenerator;

import java.util.*;

public class CorpseGlobe extends AbstractGlobeMap {

    private List<Vector2d> corpses = new ArrayList<>();
    private List<Vector2d> neighbours = List.of(new Vector2d(1, 0), new Vector2d(-1, 0),
            new Vector2d(0, 1), new Vector2d(0, -1), new Vector2d(0,0));
    private final int decayTime;

    public CorpseGlobe(int givenId, int givenWidth, int givenHeight, int startingPlantAmount,
                               int givenDecayTime,
                       int givenBreadingEnergy,int givenParentBreadingEnergyLoose,
                       int givenMinimalMutations, int givenMaximalMutations,
                       boolean givenNextGenomeVariant,int givenFoodEnergy) {
        super(givenId, givenWidth, givenHeight, givenBreadingEnergy,
                givenParentBreadingEnergyLoose, givenMinimalMutations, givenMaximalMutations, givenNextGenomeVariant, givenFoodEnergy);
        decayTime = givenDecayTime;
        grow(startingPlantAmount);
    }

    @Override
    public void grow(int amount) {
        List<Vector2d> corpseNeighbours = getPreferredSpaces();
        Collections.shuffle(corpseNeighbours);
        Collection<Vector2d> excluded = new HashSet<>(grasses.keySet());
        excluded.addAll(corpseNeighbours);


        RandomPositionGenerator steppeGenerator = new RandomPositionGenerator(bounds, amount, excluded, null);
        Iterator corpseIterator = corpseNeighbours.iterator();
        Iterator steppeIterator = steppeGenerator.iterator();
        for (int i = 0; i < amount; i++) {
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


    @Override
    public List<Vector2d> getPreferredSpaces() {
        List<Vector2d> corpseNeighbours = new ArrayList<>();
        for (Animal corpse : deadAnimals) {
            if(time - corpse.getDeathTime() < decayTime)
                for (Vector2d neighbour : neighbours) {
                    corpseNeighbours.add(corpse.getPosition().add(neighbour));
                }
        }
        return corpseNeighbours;
    }
}
