package org.atar;

import org.atar.simulator.Simulation;
import org.atar.supportsys.broker.Publisher;
import org.atar.supportsys.broker.PublisherType;


public class Main {

    public static void main(String[] args) {


        Simulation sim = new Simulation();
        sim.run();
    }
}