package agh.ics.oop.model.util;

import agh.ics.oop.Simulation;
import agh.ics.oop.model.GlobeChangeListener;
import agh.ics.oop.model.maps.Globe;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

//TODO: make output to file when day advances in simulation

public class FileStatisticsLogger {
    public void simulationChanged(Simulation simulation, String message) {
        File file = new File(String.format("globe_stats_%d", 0));
        if(!file.exists()){
            create_csv(file);
        }
    }

    private void create_csv(File file) {
        try{
            file.createNewFile();
        } catch (IOException e) {
            //ignored
        }
        try(FileWriter writer = new FileWriter(file)){
            writer.write("");
        } catch (IOException e) {
            //ignored
        }
    }
}
