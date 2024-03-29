import java.util.List;
import java.util.Random;

/**
 * A simple model of a badger.
 * Badgers age, move, breed, and die.
 * 
 * @author David J. Barnes Michael Kölling Hana Mizukami and Mihail Bratanov
 * @version 2017.02.18 (3)
 */
public class Badger extends Herbivore
{
    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();
    
    // The age to which a badger can live.
    private static final int MAX_AGE = 20;
    // The food value of a single flower. In effect, this is the
    // number of steps a badger can go before it has to eat again.
    private static final int FLOWER_FOOD_VALUE = 8;
    // The likelihood of a badger breeding.
    private static final double BREEDING_PROBABILITY = 1.12;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 5;
    // The age at which a badger can start to breed.
    private static final int BREEDING_AGE = 5;
    //The likelihood of a badger being sick and dying
    private static final int DISEASE_FATALITY = 3;
    
    // The badger's age.
    private int age;
    // The badger's food level, which is increased by eating flowers.
    private int foodLevelFlower;
    
    /**
     * Create a new badger. A badger may be created with age
     * zero (a new born) or with a random age.
     * 
     * @param randomAge If true, the badger will have a random age.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Badger(boolean randomAge, Field field, Location location)
    {
        super(field, location);
        age = 0;
        if(randomAge) {
            age = rand.nextInt(MAX_AGE);
        }
    }
    
    /**
     * Return age of a badger
     * @return age of a badger.
     */
    protected int getAge(){
        return age;
    }
    
    /**
     * Return maximum age of a badger
     * @return maximum age of a badger.
     */
    protected int getMaxAge(){
        return MAX_AGE;
    }
    
    /**
     * Return food value of a flower to a badger
     * @return food value of a flower to a badger.
     */
    protected int getFlowerFoodValue(){
        return FLOWER_FOOD_VALUE;
    }
    
    /**
     * Return food level of a flower to a badger
     * @return food level of a flower to a badger.
     */
    protected int getFlowerFoodLevel(){
        return foodLevelFlower;
    }
    
    /**
     * Return breeding probability of a badger
     * @return breeding probability of a badger.
     */
    protected double getBreedingProbability(){
        return BREEDING_PROBABILITY;
    }
    
    /**
     * Return maximum litter size of a badger
     * @return maximum litter size of a badger.
     */
    protected int getMaxLitterSize(){
        return MAX_LITTER_SIZE;
    }
    
    /**
     * Return breeding age of a badger
     * @return breeding age of a badger.
     */
    protected int getBreedingAge(){
        return BREEDING_AGE;
    }
    
    /**
     * Return the fatality rate of a badger
     * @return the fatality rate of a badger.
     */
    protected int getDiseaseFatality(){
        return DISEASE_FATALITY;
    }
    
    /**
     * Check whether or not this rabbit is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newBadger A list to return newly born badgers.
     */
    protected void giveBirth(List<Animal> newBadgers)
    {
        // New badgers are born into adjacent locations.
        // Get a list of adjacent free locations.
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int births = breed();
        
        for(int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            Badger young = new Badger(false, field, loc);
            newBadgers.add(young);
        }
    }
    
    /**
     * This is what the badger does most of the time - it runs 
     * around. Sometimes it will breed or die of old age.
     * @param newBadgers A list to return newly born badgers.
     */
    public void act(List<Animal> newBadgers, String timeText)
    {
        incrementAge();
        if(isAlive()) {
            giveBirth(newBadgers);            
            // Try to move into a free location.
            Location newLocation = getField().freeAdjacentLocation(getLocation());
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
