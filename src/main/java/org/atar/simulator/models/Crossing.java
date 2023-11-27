package org.atar.simulator.models;

import org.atar.utils.Pair;

import java.util.LinkedList;
import java.util.List;

/*
* Klasa reprezentująca aktualne przejście turysty, tj. czy idzie w dół aktualnej ścieżki czy w górę.
* */
public class Crossing {

    private static int pointCount = 0;
    private int id;
    private RoutePoint location;
    private List<Pair<Route, Boolean>> associatedRoutesWithDirections;

    public Crossing(RoutePoint _location) {
    //Animal
        this.id = Crossing.pointCount++;
        this.location = _location;
        this.associatedRoutesWithDirections = new LinkedList<>();
    }
    public void addRoute(Route _route, boolean natDirection) {
        associatedRoutesWithDirections.add(new Pair<>(_route, natDirection));
    }


}
