package Animals;

import Field.Field;
import Field.Location;

import java.util.ArrayList;
import java.util.List;

public class bear {
    private static int BREEDING_AGE = 4;
    private static int MAX_AGE = 80;
    // The likelihood of a fox breeding.
    private static double BREEDING_PROBABILITY = 0.8;
    // The maximum number of births
    private static int MAX_LITTER_SIZE = 4;
    // The food value of a single rabbit. In effect, this is the
    // number of steps a fox can go before it has to eat again.
    private static int WOLF_FOOD_VALUE = 10;

    private int age;

    private boolean alive;

    private Location location;

    private int foodLevel;

    public bear(boolean startWithRandomAge) {
        age = 0;
        alive = true;
        if (startWithRandomAge) {
            age = (int) (Math.random() * MAX_AGE);
            foodLevel = (int) (Math.random() * WOLF_FOOD_VALUE);
        } else {
            // leave age at 0
            foodLevel = WOLF_FOOD_VALUE;
        }
    }

    public void hunt(Field currentField, Field updatedField, ArrayList<bear> babybearstorage) {
        incrementAge();
        incrementHunger();
        if (alive) {

            int births = breed();
            for (int b = 0; b < births; b++) {
                bear newbear = new bear(false);
                newbear.setFoodLevel(this.foodLevel);
                babybearstorage.add(newbear);
                Location loc = updatedField.randomAdjacentLocation(location);
                newbear.setLocation(loc);
                updatedField.put(newbear, loc);
            }
            // Move towards the source of food if found.
            Location newLocation = findFood(currentField, location);
            if (newLocation == null) { // no food found - move randomly
                newLocation = updatedField.freeAdjacentLocation(location);
            }
            if (newLocation != null) {
                setLocation(newLocation);
                updatedField.put(this, newLocation);
            } else {
                // can neither move nor stay - overcrowding - all locations
                // taken
                alive = false;
            }
        }
    }

    private void incrementHunger() {
        foodLevel--;
        if (foodLevel <= 0) {
            alive = false;
        }
    }

    private void incrementAge() {
        age++;
        if (age > MAX_AGE) {
            alive = false;
        }
    }
    private int breed() {
        int numBirths = 0;
        if (canBreed() && Math.random() <= BREEDING_PROBABILITY) {
            numBirths = (int)(Math.random()*MAX_LITTER_SIZE) + 1;
        }
        return numBirths;
    }

    private boolean canBreed() {
        return age >= BREEDING_AGE;
    }
    public boolean isAlive() {
        return alive;
    }

    /**
     * Set the animal's location.
     *
     * @param row
     *            The vertical coordinate of the location.
     * @param col
     *            The horizontal coordinate of the location.
     */
    public void setLocation(int row, int col) {
        this.location = new Location(row, col);
    }

    /**
     * Set the fox's location.
     *
     * @param location
     *            The fox's location.
     */
    public void setLocation(Location location) {
        this.location = location;
    }

    public void setFoodLevel(int fl) {
        this.foodLevel = fl;
    }

    private Location findFood(Field field, Location location) {
        List<Location> adjacentLocations = field.adjacentLocations(location);

        for (Location where : adjacentLocations) {
            Object animal = field.getObjectAt(where);
            if (animal instanceof Fox) {
                Fox fox = (Fox) animal;
                if (fox.isAlive()) {
                    fox.setEaten();
                    foodLevel = WOLF_FOOD_VALUE;
                    return where;
                }
            }
        }

        return null;
    }

    public void remove(int i) {
        alive = false;
    }
}


