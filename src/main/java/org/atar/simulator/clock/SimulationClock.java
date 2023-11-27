package org.atar.simulator.clock;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SimulationClock {

    private static LocalDateTime time = null;
    private static int cycleCounter = 0;
    private static boolean isInitialized = false;

    public static void init() {
        time = LocalDateTime.now();
        cycleCounter = 1;
        isInitialized = true;
    }

    public static void nextClockCycle(ClockParam _param) {

        if(!isInitialized) throw new RuntimeException("[SimulationClock] Clock wasn't initialized. Have you tried to SimulationClock.init()?");
        try {
            time = time.plusSeconds(_param.getValueAsSeconds());
            cycleCounter++;
            Thread.sleep(_param.getValue());
        } catch (InterruptedException ie) {
            System.out.println("[SimulationClock] Interrupted during nextClockCycle.");
            System.out.println(ie);
        }
    }

    public static int getCycle() { return cycleCounter; }
    public static String getClockInfo() {
        return String.format("[SimulationClock] Cycle %d [%d:%d:%d]",
                cycleCounter,
                time.getHour(),
                time.getMinute(),
                time.getSecond());
    }

    public static LocalDateTime getClockCopy() {
        // używana w publisherze przy evencie, idk czemu tak
        return LocalDateTime.from(time);
    }
}
