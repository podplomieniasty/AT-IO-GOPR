package org.atar.simulator;

import org.atar.simulator.clock.ClockParam;
import org.atar.simulator.clock.SimulationClock;
import org.atar.simulator.map.MapFile;
import org.atar.simulator.map.MapScale;
import org.atar.utils.Pair;

import java.io.File;

public class Simulation implements Runnable {

    // konkretny powód dla którego zrobiono z tego statyczne pole to to,
    // że dużo publisherów wymaga wiedzy na temat tego, czy symulacja leci
    private static boolean isRunning;

    public Simulation() {

        MapFile map = new MapFile(new File("sample.png"),
                new Pair<>(0d, 0d),
                new Pair<>(45d, 55d),
                new Pair<>(640d,480d),
                MapScale.EXACT);

        Simulation.isRunning = true;
        SimulationClock.init();
    }



    @Override
    public void run() {


        while(Simulation.isRunning) {

            /*
            * SCHEMAT DZIALANIA SYMULATORA
            *
            * Krok 1a: Opracowanie turystów i wstępna konfiguracja
            *       - położenie
            *       - czas podejmowania decyzji
            *       - czas do podjęcia decyzji
            *       - kierunek
            * Krok 1b: Opracowanie zwierząt
            *       - zwierzę musi się tylko poruszać po mapie i nie oddziałuje w innym stopniu na inne obiekty
            *       - położenie, kierunek, prędkość itp
            * Krok 2: ???
            * Krok 3: następny cykl zegara
            * */
            generateSampleAnimals();
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
