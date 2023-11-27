package org.atar.simulator.models;

import org.atar.simulator.map.MapFile;

/*
* Rozszerzenie klasy Point o, em, pole point? Szczerze nie wiem jaki jest cel tej klasy
* Możliwe, że będzie ona do wyrzucenia, bo jedyne użycie jakie mi przychodzi do głowy widząc ją,
* to powiązanie jej z którąś ścieżką
* */
public class RoutePoint extends Point {

    private int point; // wskaźnik do którego punktu należy????

    // -------- CONSTRUCTORS -------- //
    public RoutePoint(RoutePoint p) {
        super(p);
        this.point = p.getPoint();
    }
    public RoutePoint(double _lat, double _lon, int _point, MapFile _mapFile) {
        super(_lat, _lon, _mapFile);
        this.point = _point;
    }
    public RoutePoint(int _x, int _y, int _point, MapFile _mapFile) {
        super(_x, _y, _mapFile);
        this.point = _point;
    }
    public RoutePoint(double _lat, double _lon, MapFile _mapFile) {
        super(_lat, _lon, _mapFile);
    }

    // -------- SETTERS & GETTERS -------- //
    public int getPoint() { return point; }
    public Point getAsBasicPoint() { return this; }
}
