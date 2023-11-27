package org.atar.simulator.models;

//import lombok.extern.log4j.Log4j2;
import org.atar.simulator.map.MapFile;
import org.atar.utils.Pair;

import java.util.Objects;
import java.util.Random;

/*
* Klasa reprezentująca punkt na mapie o położeniu (X,Y) z odpowiadającą terenowi
* (powiązanemu z przekazaną mapą) szerokością i wysokością geograficzną.
* Szacuje, czy (lat,lon) nie są zbyt bliskie zeru - istnieje prawdopodobieństwo,
* że wartości są źle podane.
* */

//@Log4j2
public class Point {

    private double xCoord, yCoord;  // położenie na mapie
    private double lat, lon;    // rzeczywiste położenie
    private final double ZERO_THRESHOLD = 0.00001; // do sprawdzenia wartości zerowej przy koordynatach

    // -------- CONSTRUCTORS -------- //
    public Point(double _lat, double _lon, MapFile _mapFile) {

        // 12.11.2023 15:50 -- na podstawie przekazanej mapy konwertuje szerokość i wysokość geo
        //                      do położenia (X,Y) używając konwersji z klasy mapy.
        this.lat = _lat;
        this.lon = _lon;
        if(_mapFile != null) {
            Pair<Double,Double> coordsXY = _mapFile.convertCoordsFromReal(new Pair<>(_lat, _lon));
            this.xCoord = coordsXY.getFirst();
            this.yCoord = coordsXY.getSecond();
            checkZeroThreshold();
        } else {
            System.out.println("[POINT] Unable to initialize point -- MapFile is null");
        }
    }
    public Point(int _x, int _y, MapFile _mapFile) {

        // 12.11.2023 15:58 -- na podstawie przekazanej mapy konwertuje x i y do
        //                      szerokości i wysokości geograficznej używając konwersji z klasy mapy.
        this.xCoord = _x;
        this.yCoord = _y;
        if(_mapFile != null) {
            Pair<Double, Double> coordsLatLon = _mapFile.convertCoordsToReal(new Pair<>((double)_x, (double)_y));
            this.lat = coordsLatLon.getFirst();
            this.lon = coordsLatLon.getSecond();
            checkZeroThreshold();
        } else {
            System.out.println("[POINT] Unable to initialize point -- MapFile is null");
        }
    }
    public Point(Point _point) {
        this.xCoord = _point.xCoord;
        this.yCoord = _point.yCoord;
        this.lat = _point.lat;
        this.lon = _point.lon;
    }

    // -------- STATIC FUNCTIONS -------- //
    /*
    * W starej klasie Point istniała opcja na utworzenie punktu używając funkcji
    * Point.fromLatLon(lat, lon, map), która zwracała nowy objekt Point do użycia.
    * Generowała dokładnie to samo co zwykły konstruktor z parametrami double double,
    * więc jej nie uwzględniamy.
    *
    * */
    public double distance(Point destination) {

        // zwraca "naiwną" odległość pomiędzy dwoma punktami
        return Point.naiveDistance(this, destination, 0.0d);
    }
    public double accurateDistance(Point destination) {

        // zwraca w miarę przybliżoną odległość używając klasy MapFile i jej metody na obliczanie
        return Point.accurateDistance(this, destination, 0.0d);
    }
    public static double naiveDistance(Point a, Point b, double inaccuracy) {

        // zwraca "naiwną" odległość pomiędzy dwoma punktami

        double rand;
        if(inaccuracy > 0.0000d) {
            Random rng = new Random();
            double min = -inaccuracy;
            rand = min + rng.nextDouble() * (inaccuracy - min);
        } else {
            rand = 0.0d;
        }
        double xSquared = a.xCoord - b.getxCoord();
        xSquared = xSquared * xSquared;
        double ySquared = a.yCoord - b.getyCoord();
        ySquared = ySquared * ySquared;

        return Math.sqrt(xSquared + ySquared + rand);
    }
    public static double accurateDistance(Point a, Point b, double inaccuracy) {
        return MapFile.calculateRealDistance(inaccuracy, new Pair<>(a.getLat(), b.getLat()), new Pair<>(b.getLat(), b.getLon()));
    }


    // -------- CONFIGURATION -------- //
    private void checkZeroThreshold() throws IllegalArgumentException {

        // sprawdzenie czy coś się nie porąbało z koordynatami
        // jeśli są zbliżone do zera (x,y) i (lat,lon) to coś prawdopodobnie jest zjebane

        boolean areXYZeros      = (Math.abs(this.xCoord) < this.ZERO_THRESHOLD) && (Math.abs(this.yCoord) < this.ZERO_THRESHOLD);
        boolean areLatLonZeros  = (Math.abs(this.lat) < this.ZERO_THRESHOLD) && (Math.abs(this.lon) < this.ZERO_THRESHOLD);
        if(areXYZeros && areLatLonZeros) throw new IllegalArgumentException("[POINT] (X,Y) coordinates and pair (lat, lon) are near zero!");
    }
    @Override
    public String toString() { return String.format("Point {%s, %s (%d, %d)}", this.lat, this.lon, (int)this.xCoord, (int)this.yCoord); }
    @Override
    public boolean equals(Object obj) {
        if(Objects.isNull(obj))     return false;
        if(this == obj)             return true;
        if(!(obj instanceof Point)) return false;
        Point o = (Point)obj;
        return Double.compare(this.xCoord, o.getxCoord()) == 0 && Double.compare(this.yCoord, o.getyCoord()) == 0;
    }

    // -------- SETTERS & GETTERS  -------- //

    public double getLat() { return this.lat; }
    public double getLon() { return this.lon; }
    public double getxCoord() { return this.xCoord; }
    public double getyCoord() { return this.yCoord; }

}
