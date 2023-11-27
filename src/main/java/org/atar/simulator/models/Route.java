package org.atar.simulator.models;

import java.util.ArrayList;

/*
* Klasa reprezentująca trasę górską. Trasy mogą być oznaczone kolorami oraz
* określają konkretny poziom trudności trasy.
* */
public class Route {

    private static final String[] DEFAULT_COLORS = { "Black", "Brown", "CadetBlue", "Chocolate", "CornflowerBlue",
            "DarkGreen", "DarkKhaki", "DarkSlateBlue", "GoldenRod", "Grey", "IndianRed", "MediumOrchid" };

    private ArrayList<RoutePoint> points;
    private int number;
    private int difficulty;
    private String color;
    private boolean isEntrance;
    //private transient Pair<Crossing, Crossing> endpoints;
}