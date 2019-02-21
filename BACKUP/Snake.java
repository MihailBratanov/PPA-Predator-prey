import java.util.List;
import java.util.Iterator;
import java.util.Random;

/**
 * A simple model of a snake.
 * Foxes age, move, eat rabbits, and die.
 * 
 * @author David J. Barnes Michael Kölling Hana Mizukami and Mihail Bratanov
 * @version 2019.02.18 (3)
 */
public class Snake extends Predator
{
    // Characteristics shared by all snakes (class variables).
    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();
    
    // The age to which a snake can live.
    private static final int MAX_AGE = 120;
    // The food value of a single badger. In effect, this is the
    // number of steps a snake can go before it has to eat again.
    private static final int BADGER_FOOD_VALUE = 3;
    // The food value of a single squirrel.
    private static final int SQUIRREL_FOOD_VALUE = 4;
    // The food value of a single hedgehog.
    private static final int HEDGEHOG_FOOD_VALUE = 0;
    // The likelihood of a snake breeding.
    private static final double BREEDING_PROBABILITY = 0.80;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 10;
    // The age at which a snake can start to breed.
    private static final int BREEDING_AGE = 3;
    //The likelihood of dying by disease
    private static final int DISEASE_FATALITY=3;
    
    // Individual characteristics (instance fields).
    // The snake's age.
    private int age;
    // The snake's food level, which is increased by eating herbivores.
    private int foodLevelBadger;
    private int foodLevelSquirrel;
    private int foodLevelHedgehog;
    //Whether the snake is female or not
    private boolean isFemale;
    //Whether the snake is sick or not
    private boolean isSick;
    /**
     * Create a snake. A snake can be created as a new born (age zero
     * and not hungry) or with a random age and food level.
     * 
     * @param randomAge If true, the fox will have random age and hunger level.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Snake(boolean randomAge, Field field, Location location)
    {
        super(field, location);

        if(randomAge) {
            age = rand.nextInt(MAX_AGE);
            foodLevelBadger = rand.nextInt(BADGER_FOOD_VALUE);
            foodLevelSquirrel=rand.nextInt(SQUIRREL_FOOD_VALUE);
        }
        else {
           age = 0;
           foodLevelBadger = BADGER_FOOD_VALUE;
           foodLevelSquirrel = SQUIRREL_FOOD_VALUE;
        }
        getGender();
        getDisease();
    }
    
    /**
     * Return age of a snake
     * @return age of a snake.
     */
    protected int getAge(){
        return age;
    }
    
    /**
     * Return maximum age of a snake
     * @return maximum age of a snake.
     */
    protected int getMaxAge(){
        return MAX_AGE;
    }
    
    /**
     * Return food value of a squirrel to a snake
     * @return food value of a squirrel to a snake.
     */
    protected int getSquirrelFoodValue(){
        return SQUIRREL_FOOD_VALUE;
    }
    
    /**
     * Return food value of a badger to a snake
     * @return food value of a badger to a snake.
     */
    protected int getBadgerFoodValue(){
        return BADGER_FOOD_VALUE;
    }
    
    /**
     * Return food value of a hedgehog to a snake
     * @return food value of a hedgehog to a snake.
     */
    protected int getHedgehogFoodValue(){
        return HEDGEHOG_FOOD_VALUE;
    }
    
    /**
     * Return food level of a squirrel to a snake
     * @return food level of a squirrel to a snake.
     */
    protected int getSquirrelFoodLevel(){
        return foodLevelSquirrel;
    }
    
    /**
     * Return food level of a badger to a snake
     * @return food level of a badger to a snake.
     */
    protected int getBadgerFoodLevel(){
        return foodLevelBadger;
    }
    
    /**
     * Return food level of a hedgehog to a snake
     * @return food level of a hedgehog to a snake.
     */
    protected int getHedgehogFoodLevel(){
        return foodLevelHedgehog;
    }
    
    /**
     * Return breeding probability of a snake
     * @return breeding probability of a snake.
     */
    protected double getBreedingProbability(){
        return BREEDING_PROBABILITY;
    }
    
    /**
     * Return maximum litter size of a snake
     * @return maximum litter size of a snake.
     */
    protected int getMaxLitterSize(){
        return MAX_LITTER_SIZE;
    }
    
    /**
     * Return breeding age of a snake
     * @return breeding age of a snake.
     */
    protected int getBreedingAge(){
        return BREEDING_AGE;
    }
    
    /**
     * Find the partner's location under the conditions
     * @return where the partner is found
     */
     public Location findPartner(){
        Field field = getField();
        List<Location> adjacent = field.adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        while(it.hasNext()) {
            Location where = it.next();
            Object animal = field.getObjectAt(where);
            if(animal instanceof Snake ){
                Snake snake=(Snake) animal;
                if(snake.isAlive() && getFemaleOrMale()!=snake.getFemaleOrMale()){
                    // canBreed();
                    return where;
                }
            }
        }
        return null;
    }
    
    /**
     * Check whether or not this snake is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newSnakes A list to return newly born snakes.
     */
    public void giveBirth(List<Animal> newSnakes)
    {
        // New snakes are born into adjacent locations.
        // Get a list of adjacent free locations.
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int births = breed();
        for(int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            Snake young = new Snake(false, field, loc);
            newSnakes.add(young);
        }
    }
    
    /**
     * Spread disease for a snake
     */
    protected void spreadDisease(){
        Field field = getField();
        List<Location> adjacent = field.adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        while(it.hasNext()) {
            Location where = it.next();
            Object animal = field.getObjectAt(where);
            if(animal instanceof Snake){
                Snake snake=(Snake) animal;
                if (animal instanceof Snake && sickOrNot()==true){
                    getDisease();
                }
            }
        }
    }
    
    /**
     * This is what the fox does most of the time: it hunts for
     * rabbits. In the process, it might breed, die of hunger,
     * or die of old age.
     * @param field The field currently occupied.
     * @param newFoxes A list to return newly born foxes.
     */
    public void act(List<Animal> newSnakes, String timeText)
    {
        incrementAge();
        incrementHunger();
        if(isAlive()) {
            //disease will take effect here if isSick==true;
            if(getDisease()==true){
                spreadDisease();
                  
                int fatalityLevel=rand.nextInt(DISEASE_FATALITY);
                if (fatalityLevel==3){
                     setDead();
                     
                }
            }
            // Move towards a source of food if found.
            Location newLocation = findFood();
            Location partnerLocation=findPartner();
            if(newLocation == null) { 
                // No food found - try to move to a free location.
                newLocation = getField().freeAdjacentLocation(getLocation());
            }
            // See if it was possible to move.
            if(newLocation != null) {
                setLocation(newLocation);
            }
            if(partnerLocation!=null){
                giveBirth(newSnakes);   
            }
            else {
                // Overcrowding.
                setDead();
            }
        }
    }
}
