import java.util.List;
import java.util.Iterator;
import java.util.Random;

/**
 * A simple model of a fox.
 * Foxes age, move, eat rabbits, and die.
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 2016.02.29 (2)
 */
public class Wolf extends Predator
{
    // Characteristics shared by all foxes (class variables).
    
    // The age at which a fox can start to breed.
    private static final int BREEDING_AGE =5;
    //A random number representing the gender. Even numbers are female and odd numbers are male.
    
    // The age to which a fox can live.
    private static final int MAX_AGE = 80;
    // The likelihood of a fox breeding.
    private static final double BREEDING_PROBABILITY = 0.50;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 8;
    // The food value of a single rabbit. In effect, this is the
    // number of steps a fox can go before it has to eat again.
    private static final int BADGER_FOOD_VALUE = 4;
    private static final int HEDGEHOG_FOOD_VALUE = 3;
    private static final int SQUIRREL_FOOD_VALUE = 0;
    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();
    //boolean gender;
    // Individual characteristics (instance fields).
    // The fox's age.
    private int age;
    // The fox's food level, which is increased by eating rabbits.
    private int foodLevelBadger;
    private int foodLevelHedgehog;
    private int foodLevelSquirrel;
    /**
     * Create a fox. A fox can be created as a new born (age zero
     * and not hungry) or with a random age and food level.
     * 
     * @param randomAge If true, the fox will have random age and hunger level.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Wolf(boolean randomAge, Field field, Location location)
    {
        super(field, location);
        if(randomAge) {
            age = rand.nextInt(MAX_AGE);
            foodLevelBadger = rand.nextInt(BADGER_FOOD_VALUE);
            foodLevelHedgehog=rand.nextInt(HEDGEHOG_FOOD_VALUE);
        }
        else {
            age = 0;
            foodLevelBadger = BADGER_FOOD_VALUE;
            foodLevelHedgehog = HEDGEHOG_FOOD_VALUE;
        }
        getGender();
    }
  
    /**
     * Check whether or not this rabbit is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newRabbits A list to return newly born rabbits.
     */
    protected int getBadgerFoodValue(){
        return BADGER_FOOD_VALUE;
    }
    /**
     * Check whether or not this rabbit is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newRabbits A list to return newly born rabbits.
     */
    protected int getHedgehogFoodValue(){
        return HEDGEHOG_FOOD_VALUE;
    }
      /**
     * Check whether or not this rabbit is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newRabbits A list to return newly born rabbits.
     */
    protected int getSquirrelFoodValue(){
        return SQUIRREL_FOOD_VALUE;
    }
    /**
     * Check whether or not this rabbit is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newRabbits A list to return newly born rabbits.
     */
    protected int getSquirrelFoodLevel(){
        return foodLevelSquirrel;
    }
    /**
     * Check whether or not this rabbit is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newRabbits A list to return newly born rabbits.
     */
    protected int getBadgerFoodLevel(){
        return foodLevelBadger;
    }
    /**
     * Check whether or not this rabbit is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newRabbits A list to return newly born rabbits.
     */
    protected int getHedgehogFoodLevel(){
        return foodLevelHedgehog;
    }
    /**
     * Check whether or not this rabbit is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newRabbits A list to return newly born rabbits.
     */
    protected int getBreedingAge(){
        return BREEDING_AGE;
    }
    /**
     * Check whether or not this rabbit is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newRabbits A list to return newly born rabbits.
     */
    protected double getBreedingProbability(){
        return BREEDING_PROBABILITY;
    }
    /**
     * Check whether or not this rabbit is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newRabbits A list to return newly born rabbits.
     */
    protected int getAge(){
        return age;
    }
    /**
     * Check whether or not this rabbit is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newRabbits A list to return newly born rabbits.
     */
    protected int getMaxAge(){
        return MAX_AGE;
    }
    /**
     * Check whether or not this rabbit is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newRabbits A list to return newly born rabbits.
     */
    protected int getMaxLitterSize(){
        return MAX_LITTER_SIZE;
    }
    /**
     * This is what the fox does most of the time: it hunts for
     * rabbits. In the process, it might breed, die of hunger,
     * or die of old age.
     * @param field The field currently occupied.
     * @param newFoxes A list to return newly born foxes.
     */
    public void act(List<Animal> newWolves)
    {
        incrementAge();
        incrementHunger();
        if(isAlive()) {
                       
            // Move towards a source of food if found.
            Location newLocation = findFood();
            Location newPartnerLocation=findPartner();
            if(newLocation == null) { 
                // No food found - try to move to a free location.
                newLocation = getField().freeAdjacentLocation(getLocation());
            }
                if(newLocation != null) { 
                // No food found - try to move to a free location.
                 setLocation(newLocation);
            }
            // See if it was possible to move.
            if(newPartnerLocation != null) {
            
                giveBirth(newWolves);
                
                
            }
            else {
                // Overcrowding.
                setDead();
            }
        }
    }    

    /**
     * Check whether or not this fox is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newFoxes A list to return newly born foxes.
     */
     public Location findPartner(){
        Field field = getField();
        List<Location> adjacent = field.adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        while(it.hasNext()) {
            Location where = it.next();
            Object animal = field.getObjectAt(where);
            if(animal instanceof Wolf ){
                Wolf wolf=(Wolf) animal;
                if(wolf.isAlive() && getFemaleOrMale()!=wolf.getFemaleOrMale()){
                    
               // canBreed();
               return where;
            }
            }
    
}
return null;
}

 /**
     * Check whether or not this fox is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newFoxes A list to return newly born foxes.
     */
    public void giveBirth(List<Animal> newWolves)
    {
        // New foxes are born into adjacent locations.
        // Get a list of adjacent free locations.
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int births = breed();
        for(int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            Wolf young = new Wolf(false, field, loc);
            newWolves.add(young);
        }
    }
}