import java.util.List;
import java.util.Random;

/**
 * A simple model of a squirrel.
 * Squirrels age, move, breed, and die.
 * 
 * @author David J. Barnes Michael Kölling Hana Mizukami and Mihail Bratanov
 * @version 2017.02.18 (3)
 */
public class Squirrel extends Herbivore
{
    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();
    
    // The age to which a squirrel can live.
    private static final int MAX_AGE = 49;
    // The food value of a single flower. In effect, this is the
    // number of steps a squirrel can go before it has to eat again.
    private static final int FLOWER_FOOD_VALUE = 9;
    // The likelihood of a squirrel breeding.
    private static final double BREEDING_PROBABILITY = 0.48;
    // The maximum litter size of a squirrel.
    private static final int MAX_LITTER_SIZE = 4;
    // The age at which a squirrel can start to breed.
    private static final int BREEDING_AGE = 5;
    // The likelihood of a squirrel dying from the disease 
    private static final int DISEASE_FATALITY = 3;
    
    // The squirrel's age.
    private int age;
    // The squirrel's food level, which is increased by eating flowers.
    private int foodLevelFlower;
    
    /**
     * Create a new squirrel. A squirrel may be created with age
     * zero (a new born) or with a random age.
     * 
     * @param randomAge If true, the rabbit will have a random age.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Squirrel(boolean randomAge, Field field, Location location)
    {
        super(field, location);
        age = 0;
        if(randomAge) {
            age = rand.nextInt(MAX_AGE);
        }
    }
    
    /**
     * Return age of a squirrel
     * @return age of a squirrel.
     */
    protected int getAge(){
        return age;
    }
    
    /**
     * Return maximum age of a squirrel
     * @return maximum age of a squirrel.
     */
    protected int getMaxAge(){
        return MAX_AGE;
    }
    
    /**
     * Return food value of a flower to a squirrel
     * @return food value of a flower to a squirrel.
     */
    protected int getFlowerFoodValue(){
        return FLOWER_FOOD_VALUE;
    }
    
    /**
     * Return food level of a flower to a squirrel
     * @return food level of a flower to a squirrel.
     */
    protected int getFlowerFoodLevel(){
        return foodLevelFlower;
    }
    
    /**
     * Return breeding probability of a squirrel
     * @return breeding probability of a squirrel.
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
     * Return breeding age of a squirrel
     * @return breeding age of a squirrel.
     */
    protected int getBreedingAge(){
        return BREEDING_AGE;
    }
    
    /**
     * Return disease fatality of a squirrel
     * @return disease fatality of a squirrel.
     */
    protected int getDiseaseFatality(){
        return DISEASE_FATALITY;
    }
    
    /**
     * Check whether or not this rabbit is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newSquirrels A list to return newly born squirrels.
     */
    public void giveBirth(List<Animal> newSquirrels)
    {
        // New squirrels are born into adjacent locations.
        // Get a list of adjacent free locations.
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int births = breed();
        for(int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            Squirrel young = new Squirrel(false, field, loc);
            newSquirrels.add(young);
        }
    }
    
    /**
     * This is what the squirrel does most of the time - it runs 
     * around. Sometimes it will breed or die of old age.
     * @param newSquirrels A list to return newly born squirrels.
     */
    public void act(List<Animal> newSquirrels, String timeText)
    {
        incrementAge();
        if(isAlive()) {
            giveBirth(newSquirrels);            
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
