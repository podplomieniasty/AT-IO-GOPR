package org.atar.simulator.entities.animal;

import org.atar.simulator.map.MapFile;
import org.atar.simulator.models.Point;
import org.atar.utils.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AnimalManager {

    public List<Animal> animals = new ArrayList<>();
    private Random rng = new Random();
    private float directionChangeTimer = 49f;
    private float directionChangeFrequency = 50f;
    private final float directionChangeStep = 0.3f;
    private MapFile usedMap = null;

    public synchronized void generateRandomAnimals(MapFile map, int amount) {

        animals.clear();
        this.usedMap = map;
        for(int i = 0; i < amount; i++) {

            // generowanie losowych koordynatów w obrębie mapy
            Pair<Double, Double> mapWidthHeight = map.getCanvasWidthHeight();
            Pair<Integer, Integer> newCoords = new Pair<>(this.rng.nextInt(mapWidthHeight.getFirst().intValue() - 40) + 20,
                                                        this.rng.nextInt(mapWidthHeight.getSecond().intValue() - 40) + 20);
            this.animals.add(new Animal(
                                 new Point(newCoords.getFirst(), newCoords.getSecond(), map),
                                 AnimalType.BEAR,    // na teraz niech to będą tylko niedźwiedzie
                                 0.01d,              // velocity
                                 new Pair<>(Math.max(10, newCoords.getFirst()),
                                            Math.max(10, newCoords.getSecond())),
                                 new Pair<>(Math.min(mapWidthHeight.getFirst().intValue() - 10, newCoords.getFirst() + 350),
                                            Math.min(mapWidthHeight.getSecond().intValue() - 10, newCoords.getSecond() + 350))));
        }
    }

    public Point calculateAnimalsNextPoint(Animal animal, long delta) {

        this.directionChangeTimer += this.directionChangeStep;
        if(this.directionChangeTimer >= this.directionChangeFrequency) {
            this.directionChangeTimer -= this.directionChangeFrequency;
            animal.changeDirection(this.rng);
        }

        animal.changeVelocity(this.rng);
        double finalVelocity = animal.getVelocity();

        Pair<Integer, Integer> animalsDirection = animal.getDirection();
        double componentVelocity = (animalsDirection.getFirst() * animalsDirection.getSecond() == 0 ?
                                    finalVelocity : finalVelocity / Math.sqrt(2));
        Pair<Double, Double> diff = new Pair<>(delta * animalsDirection.getFirst() * componentVelocity / 1000d,
                                                   delta * animalsDirection.getSecond() * componentVelocity / 1000d);
        // LET'S GET FUCKIING NASTY
        double x = diff.getFirst() + animal.getLocation().getxCoord() >= animal.getMaxCoordinates().getFirst() ||
                   diff.getFirst() + animal.getLocation().getxCoord() <= animal.getMinCoordinates().getFirst()
                    ? animal.getLocation().getxCoord() - diff.getFirst()
                    : animal.getLocation().getxCoord() + diff.getFirst();
        double y = diff.getSecond() + animal.getLocation().getyCoord() >= animal.getMaxCoordinates().getSecond() ||
                   diff.getSecond() + animal.getLocation().getyCoord() <= animal.getMinCoordinates().getSecond()
                    ? animal.getLocation().getyCoord() - diff.getSecond()
                    : animal.getLocation().getyCoord() + diff.getSecond();

        if(x > animal.getMaxCoordinates().getFirst()) {
            animal.changeDirection(this.rng);
            x = animal.getMaxCoordinates().getFirst();
        } else if(x < animal.getMinCoordinates().getFirst()) {
            animal.changeDirection(this.rng);
            x = animal.getMinCoordinates().getFirst();
        }

        if(y > animal.getMaxCoordinates().getSecond()) {
            animal.changeDirection(this.rng);
            y = animal.getMaxCoordinates().getSecond();
        } else if(y < animal.getMinCoordinates().getSecond()) {
            animal.changeDirection(this.rng);
            y = animal.getMinCoordinates().getSecond();
        }

        return new Point(x, y, this.usedMap);
    }

}
