package org.atar.simulator.map;

import org.atar.utils.Pair;

import java.io.File;
import java.util.Objects;
import java.util.Random;

/*
    MapFile to klasa przechowująca wszystkie dane odnośnie mapy (na obecną chwilę).
    W jej skład wchodzi plik, skala i inne pierdoły.
    Na daną chwilę w jej skład wchodzą dane klasy z poprzedniego projektu:
    - RasterMap
 */
public class MapFile {

    private File mapFile;
    private String mapName;
    private Pair<Double, Double> coordsTopLeft; // koordynaty (X,Y), (lat, lon)
    private Pair<Double, Double> coordsBottomRight;
    private Pair<Double, Double> canvasWidthHeight;
    private Pair<Double, Double> realCanvasWidthHeight;
    private Pair<Double, Double> coordsConversionFactors;
    private final MapScale scale;

    public MapFile(File _mapFile, Pair<Double, Double> _topLeft, Pair<Double, Double> _botRight,
                   Pair<Double, Double> _canvasWidthHeight, MapScale _scale) {
        this.mapFile            = _mapFile;
        this.coordsTopLeft      = _topLeft;
        this.coordsBottomRight  = _botRight;
        this.canvasWidthHeight  = _canvasWidthHeight;
        this.scale              = _scale;

        calculateCoordsConversionFactors();
        calculateRealCanvasSize();
        extractMapName();

    }

    // -------- CLASS CONFIG -------- //
    private void calculateCoordsConversionFactors() {

        // 03.11.2023 16:16 - oblicza współczynnik skalowania koordynatów, ale po jakiego [redacted] to potrzebne?
        // 03.11.2023 16:54 - chyba przy konwersji na rzeczywiste wartości położenia, albo na odwrót

        double lat = Math.abs(this.coordsTopLeft.getFirst() - this.coordsBottomRight.getFirst())    * this.scale.value();
        double lon = Math.abs(this.coordsTopLeft.getSecond() - this.coordsBottomRight.getSecond())  * this.scale.value();

        double latConversionFactor = (this.canvasWidthHeight.getSecond() / lat) * this.scale.value();
        double lonConversionFactor = (this.canvasWidthHeight.getFirst() / lon)  * this.scale.value();

        this.coordsConversionFactors = new Pair<>(latConversionFactor, lonConversionFactor);
    }
    private void calculateRealCanvasSize() {

        double accuracy = 0.0d;

        // 03.11.2023 16:47 - https://www.youtube.com/watch?v=qzR5Y1Tm-4U&ab_channel=skyhawk
        //                  jakaś matematyka do wyliczenia rozmiaru kanwy na podstawie specyficznie dobranych parametrów pary

        Pair<Double, Double> yPairCoordinates = new Pair<>(this.coordsBottomRight.getFirst(), this.coordsTopLeft.getSecond());
        Pair<Double, Double> xPairCoordinates = new Pair<>(this.coordsTopLeft.getFirst(), this.coordsBottomRight.getSecond());

        double canvasY = MapFile.calculateRealDistance(accuracy, this.coordsTopLeft, yPairCoordinates);
        double canvasX = MapFile.calculateRealDistance(accuracy, this.coordsTopLeft, xPairCoordinates);

        this.realCanvasWidthHeight = new Pair<>(canvasX, canvasY);
    }
    private void extractMapName() {
        String mapName;
        if(Objects.isNull(mapFile)) {
            this.mapName = "untitled";
            return;
        }
        mapName = this.mapFile.getName();
        this.mapName = mapName.substring(0, mapName.lastIndexOf('.'));
    }


    // -------- RETURNABLES -------- //
    public Pair<Double, Double> convertCoordsToReal(Pair<Double, Double> coords) {

        // konwertuje przekazany parametr na wartości odpowiadające mapie w odpowiedniej skali

        double coordsLat = coords.getFirst() / this.coordsConversionFactors.getFirst();
        double coordsLon = coords.getSecond() / this.coordsConversionFactors.getSecond();

        double realLat = this.coordsTopLeft.getFirst() - coordsLat;
        double realLon = this.coordsTopLeft.getSecond() - coordsLon;

        return new Pair<>(realLat, realLon);
    }
    public Pair<Double, Double> convertCoordsFromReal(Pair<Double, Double> coords) {

        // konwertuje przekazany parametr na wartości odpowiadające symulowanej mapie w odpowiedniej skali
        double simX = coords.getFirst() - this.coordsTopLeft.getFirst();
        double simY = coords.getSecond() - this.coordsTopLeft.getSecond();

        simX = Math.abs(simX) * this.coordsConversionFactors.getFirst();
        simY = Math.abs(simY) * this.coordsConversionFactors.getSecond();

        return new Pair<>(simX, simY);
    }
    public static double calculateRealDistance(double accuracy, Pair<Double, Double> xLatLon, Pair<Double, Double> yLatLon) {

        // 03.11.2023 16:27 - szczerze nie obchodzi mnie jak to działa

        double inacc, min;
        Random rng = new Random();
        final int radius = 6371; // promień Ziemi

        if(accuracy > 0.0000f) {
            min = -accuracy;
            inacc = min + rng.nextDouble() * (accuracy - min);
        } else {
            inacc = 0.0d;
        }

        double lat = Math.toRadians(yLatLon.getFirst() - xLatLon.getFirst());
        double lon = Math.toRadians(yLatLon.getSecond() - xLatLon.getSecond());

        double xLatAsRads = Math.toRadians(xLatLon.getFirst());
        double yLatAsRads = Math.toRadians(yLatLon.getFirst());

        // zaawansowana matma tutaj
        double a_1 = Math.sin(lat / 2) * Math.sin(lat / 2);
        double a_2 = Math.cos(xLatAsRads) * Math.cos(yLatAsRads) * Math.sin(lon / 2) * Math.sin(lon / 2);
        double a = a_1 + a_2;

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = radius * c * 1000; // konwersja na metry

        /*
         *   03.11.2023 16:39 - nie wiem CZEMU tu to jest, może ktoś chciał uwzględniać
         *   zmienną wysokość, jeśli tak chciał to mu się absolutnie nie udało i zostaje tu to dla potomnych
         *
         * */
        double height = 0.0d;

        distance = distance * distance + height * height;
        return Math.sqrt(distance) + inacc;
    }


    // -------- SETTERS & GETTERS -------- //
    public File getMapFile() {return this.mapFile; }
    public String getMapName() { return this.mapName; }
    public Pair<Double, Double> getCanvasWidthHeight() { return canvasWidthHeight; }
}
