package org.atar.simulator.entities.animal;

import org.atar.simulator.models.Point;
import org.atar.utils.Pair;

import java.util.Random;

public class Animal {

    private static int id = 0;
    private String animalId;
    private Point location;
    private AnimalType type; // typ zwierzęcia

    private Pair<Integer, Integer> minCoordinates;
    private Pair<Integer, Integer> maxCoordinates;
    private Pair<Integer, Integer> direction;
    private double velocity;

    public Animal(Point _location, AnimalType _type, double _velocity, Pair<Integer, Integer> _minCoordinates, Pair<Integer, Integer> _maxCoordinates) {

        this.location = _location;
        this.type = _type;
        this.velocity = _velocity;
        this.minCoordinates = _minCoordinates;
        this.maxCoordinates = _maxCoordinates;

        // formatuje zwierze type: enum i id: x do postaci: enum-000x
        this.animalId = String.format("%s-%04d", _type.toString().toLowerCase(), Animal.id++);
        System.out.println("[Animal] Utworzono zwierzę o ID: " + this.animalId);
    }

    public void changeDirection(Random rng) {

        int bound = 2;
        int x = rng.nextInt(bound, bound + 3) - 3; // wyniki -1, 0, 1
        int y = rng.nextInt(bound, bound + 3) - 3; // wyniki -1, 0, 1
        this.direction = new Pair<>(x, y);
    }

    public void changeVelocity(Random rng) {
        double velocityShift = rng.nextDouble() * 5 - 2.5f; // czemu tak? nie wiem, wyjebane? tak
        double velocityPrev = this.velocity;
        double newVelocity = velocityPrev + velocityShift;
        this.velocity = Math.clamp(newVelocity, 0.01d, 5.0d); // ogranicz velocity do tych wartosci
    }

    public Pair<Integer, Integer> getDirection() {
        return direction;
    }
    public void setDirection(Pair<Integer, Integer> direction) {
        this.direction = direction;
    }

    public Pair<Integer, Integer> getMinCoordinates() {
        return minCoordinates;
    }

    public void setMinCoordinates(Pair<Integer, Integer> minCoordinates) {
        this.minCoordinates = minCoordinates;
    }

    public Pair<Integer, Integer> getMaxCoordinates() {
        return maxCoordinates;
    }

    public void setMaxCoordinates(Pair<Integer, Integer> maxCoordinates) {
        this.maxCoordinates = maxCoordinates;
    }

    public double getVelocity() {
        return velocity;
    }

    public void setVelocity(double velocity) {
        this.velocity = velocity;
    }

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }
}


