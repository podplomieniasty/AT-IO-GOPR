package org.atar.simulator;

import org.atar.simulator.clock.ClockParam;
import org.atar.simulator.clock.SimulationClock;
import org.atar.simulator.entities.animal.Animal;
import org.atar.simulator.entities.animal.AnimalType;
import org.atar.simulator.map.MapFile;
import org.atar.simulator.map.MapScale;
import org.atar.simulator.models.Point;
import org.atar.utils.Pair;

import java.io.File;
import java.util.ArrayList;

public class Simulation implements Runnable {

    // konkretny powód dla którego zrobiono z tego statyczne pole to to,
    // że dużo publisherów wymaga wiedzy na temat tego, czy symulacja leci
    private static boolean isRunning;
    private static ArrayList<Animal> animals = new ArrayList<Animal>();


    public Simulation() {

        MapFile map = new MapFile(new File("sample.png"),
                new Pair<>(0d, 0d),
                new Pair<>(45d, 55d),
                new Pair<>(640d,480d),
                MapScale.EXACT);

        Simulation.isRunning = true;
        SimulationClock.init();
        animals.add(new Animal(new Point(46, 8, map), AnimalType.BEAR, 0.01f, new Pair<>(0,0), new Pair<>(720,480)));
    }



    @Override
    public void run() {


        while(Simulation.isRunning) {

            //generateSampleAnimals();
            prepareNextCycle();
        }
    }

    private void prepareNextCycle() {

        /*
        * Funkcja preparująca elementy symulacji przed kolejnym cyklem zegara.
        *
        * */

        // Wypisz informacje o cyklu i zegarze, a następnie odczekaj wymagany czas
        System.out.println(SimulationClock.getClockInfo());
        SimulationClock.nextClockCycle(ClockParam.ONE_SECOND);
    }

    private void generateSampleAnimals() {



    }

    public static boolean isRunning() {
        return isRunning;
    }


}
