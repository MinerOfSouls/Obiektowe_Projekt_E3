package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SimulationEngine {
    private List<Simulation> simulations;
    private List<Thread> threads = new ArrayList<>();
    private ExecutorService pool = Executors.newFixedThreadPool(4);;

    public SimulationEngine(List<Simulation> givenSimulations){
        simulations = givenSimulations;
    }
    public void runSync(){
        for(Simulation simulation: simulations){
            simulation.run();
        }
    }

    public void runAsync(){
        for (Simulation simulation: simulations){
            threads.add(new Thread(simulation));
        }
        for (Thread thread: threads){
            thread.start();
        }
    }

    public void runAsyncInThreadPool(){
        for (Simulation simulation: simulations){
            pool.submit(simulation);
        }
    }

    public void awaitSimulationsEnd() throws InterruptedException{
        pool.shutdown();
        if(!pool.awaitTermination(10, TimeUnit.SECONDS)) {
            pool.shutdownNow();
        }
        pool.shutdownNow();
        for (Thread thread : threads) {
            thread.join();
        }
    }
}
