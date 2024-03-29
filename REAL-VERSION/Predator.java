import java.util.List;
import java.util.Iterator;
import java.util.Random;
import java.util.*;

/**
 * A simple model of a predator.
 * Predators age, move, eat herbivores, and die.
 * 
 * @author David J. Barnes Michael Kölling Hana Mizukami and Mihail Bratanov
 * @version 2019.02.18 (3)
 */
public abstract class Predator extends Animal
{
    // A shared random number generator to control breeding.
    private  Random rand = Randomizer.getRandom();
    /**
     * Create a predator.
     * 
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Predator(Field field, Location location)
    {
        super(field, location);
    }

    /**
     * Return the age of a predator
     * @return the age of a predator
     */
    abstract protected int getAge();

    /**
     * Return the maximum age of a predator before it dies
     * @return the maximum age of a predator
     */
    abstract protected int getMaxAge();

    /**
     * Increase the age. This could result in the predator's death.
     */
    protected void incrementAge()
    {
        int age= getAge();
        age++;

        if(age > getMaxAge()) {
            setDead();
        }
    }

    /**
     * Return the food value of a predator for badger
     * @return the food value of a predator for badger
     */
    abstract protected int getBadgerFoodValue();

    /**
     * Return the food value of a predator for squirrel
     * @return the food value of a predator for squirrel
     */
    abstract protected int getSquirrelFoodValue();

    /**
     * Return the food value of a predator for hedgehog
     * @return the food value of a predator for hedgehog
     */
    abstract protected int getHedgehogFoodValue();

    /**
     * Look for herbivores adjacent to the current location.
     * 
     * @return Where food was found, or null if it wasn't.
     */
    protected Location findFood()
    {
        Field field = getField();
        List<Location> adjacent = field.adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        while(it.hasNext()) {
            Location where = it.next();
            Object animal = field.getObjectAt(where);
            if(animal instanceof Badger) {
                Badger badger = (Badger) animal;
                if(badger.isAlive()) { 
                    badger.setDead();
                    int predatorBadgerFV = getBadgerFoodValue();
                    return where;
                }
            } //if the adjacent animal was a badger, badger dies and the food value is set as the location is allocated
            else if(animal instanceof Squirrel) {
                Squirrel squirrel = (Squirrel) animal;
                if(squirrel.isAlive()) { 
                    squirrel.setDead();
                    int predatorSquirrelFV = getSquirrelFoodValue();
                    return where;
                }
            } //if the adjacent animal was a squirrel, squirrel dies and the food value is set as the location is allocated
            else if (animal instanceof Hedgehog) {
                Hedgehog hedgehog = (Hedgehog) animal;
                if(hedgehog.isAlive()) { 
                    hedgehog.setDead();
                    int predatorHedgehogFV = getHedgehogFoodValue();
                    return where;
                }
            } //if the adjacent animal was a hedgehog, hedgehog dies and the food value is set as the location is allocated
        }
        return null; //no location
    }

    /**
     * Return the food level of a predator for badger
     * @return the food level of a predator for badger
     */
    abstract protected int getBadgerFoodLevel();

    /**
     * Return the food level of a predator for squirrel
     * @return the food level of a predator for squirrel
     */
    abstract protected int getSquirrelFoodLevel();

    /**
     * Return the food level of a predator for hedgehog
     * @return the food level of a predator for hedgehog
     */
    abstract protected int getHedgehogFoodLevel();

    /**
     * Make this predator more hungry. This could result in the predator's death.
     */
    protected void incrementHunger()
    {
        int predatorBadgerFL = getBadgerFoodLevel();
        int predatorSquirrelFL = getSquirrelFoodLevel();
        int predatorHedgehogFL = getHedgehogFoodLevel();
        predatorBadgerFL--;
        predatorSquirrelFL--;
        predatorHedgehogFL--;

        if((predatorBadgerFL <= 0 && predatorSquirrelFL<=0)||(predatorBadgerFL <= 0 && predatorHedgehogFL<=0)||(predatorHedgehogFL <= 0 && predatorSquirrelFL<=0) ){
            setDead();
        }
    }

    /**
     * Return the breeding probability of a predator
     * @return the breeding probability of a predator
     */
    abstract protected double getBreedingProbability();

    /**
     * Return the maximum litter size of a predator
     * @return the maximum litter size of a predator
     */
    abstract protected int getMaxLitterSize();

    /**
     * Return the breeding age of a predator
     * @return the breeding age of a predator
     */
    abstract protected int getBreedingAge();

    /**
     * A predator can breed if it has reached the breeding age.
     * 
     * @return whether it can breed or not
     */
    protected boolean canBreed()
    {
        return getAge() >= getBreedingAge();
    }

    /**
     * Generate a number representing the number of births,
     * if it can breed.
     * @return The number of births (may be zero).
     */
    protected int breed()
    {
        int births = 0;
        if(canBreed()  && rand.nextDouble() <= getBreedingProbability()) {
            births = rand.nextInt(getMaxLitterSize()) + 1;
        }
        return births;
    }

    /**
     * Check whether or not this predator is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newPredators A list to return newly born predators.
     */
    abstract void giveBirth(List<Animal> newPredators);

    /**
     * This is what the predator does most of the time: it hunts for
     * herbivores. In the process, it might breed, die of hunger,
     * or die of old age.
     * 
     * @param newFoxes A list to return newly born foxes.
     * @param timeText the current time
     */
    public void act(List<Animal> newPredators, String timeText)
    {
        while(timeText == "night"){ //if the time is night, it hunts and functions
            incrementAge();
            incrementHunger();
            if(isAlive()) {
                giveBirth(newPredators);            
                // Move towards a source of food if found.
                Location newLocation = findFood();
                if(newLocation == null) { 
                    // No food found - try to move to a free location.
                    newLocation = getField().freeAdjacentLocation(getLocation());
                }
                // See if it was possible to move.
                if(newLocation != null) {
                    setLocation(newLocation);
                }
                else {
                    // Overcrowding.
                    setDead();
                }
            }
        }
    }
}
