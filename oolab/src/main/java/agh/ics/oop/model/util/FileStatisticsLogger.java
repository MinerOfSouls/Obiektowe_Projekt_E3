package agh.ics.oop.model.util;

import agh.ics.oop.Simulation;
import agh.ics.oop.SimulationListener;
import agh.ics.oop.model.GlobeChangeListener;
import agh.ics.oop.model.maps.AbstractGlobeMap;
import agh.ics.oop.model.maps.Globe;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class FileStatisticsLogger implements SimulationListener {
    public void dayAdvance(Simulation simulation) {
        AbstractGlobeMap map = simulation.getMap();
        File file = new File(String.format("globe_stats_%d", map.getID()));
        if (!file.exists()) {
            create_csv(file);
        }
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(
                    String.format(
                            "%d, %d, %d, %d, %s, %d, %d, %d\n",
                            map.getTime(), map.getAnimals().size(), map.getGrassLocations().size(),
                            (map.getCurrentBounds().upperRight().getY() + 1) * (map.getCurrentBounds().upperRight().getY() + 1) - map.getGrassLocations().size(),
                            map.getTopGenome().toString(), map.getAverageEnergy(),
                            map.getAverageLifespan(), map.getAverageChildrenAmount()
                    ));
        } catch (IOException e) {
            //ignored
        }
    }

    private void create_csv(File file) {
        try {
            file.createNewFile();
        } catch (IOException e) {
            //ignored // czy to dobry wybór?
        }
        try (FileWriter writer = new FileWriter(file)) {
            writer.write("day, animal_amount, plant_amount, free_space_amount, dominant_genome, average_energy_level, average_lifespan, average_child_amount\n");
        } catch (IOException e) {
            //ignored
        }
    }
}
