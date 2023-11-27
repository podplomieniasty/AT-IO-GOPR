package org.atar.simulator.models;

public class Detector {

    private static int detectorsCount = 0;
    private int detectorNumber;
    private Point coordinates;
    public Detector(Point _coordinates) {

        this.coordinates = _coordinates;
        this.detectorNumber = Detector.detectorsCount++;
    }

    // -------- SETTERS & GETTERS -------- //

    public int getDetectorNumber() { return this.detectorNumber; }
    public Point getCoordinates() { return this.coordinates; }
    public int getDetectorsCount() { return Detector.detectorsCount; }
}
