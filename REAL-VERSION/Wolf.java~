import java.util.List;
import java.util.Iterator;
import java.util.Random;

/**
 * A simple model of a wolf.
 * Wolves age, move, eat badgers and hedgehogs, and die.
 * 
 * @author David J. Barnes Michael Kölling Hana Mizukami and Mihail Bratanov
 * @version 2019.02.19 (3)
 */
public class Wolf extends Predator
{
    // Characteristics shared by all wolves (class variables).
    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();
    
    // The age to which a wolf can live.
    private static final int MAX_AGE = 200;
    // The food value of a single badger. In effect, this is the
    // number of steps a wolf can go before it has to eat again.
    private static final int BADGER_FOOD_VALUE = 15;
    // The food value of a single hedgehog.
    private static final int HEDGEHOG_FOOD_VALUE = 15;
    // The food value of a single squirrel.
    private static final int SQUIRREL_FOOD_VALUE = 0;
    // The likelihood of a wolf breeding.
    private static final double BREEDING_PROBABILITY = 0.80;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 15;
    // The age at which a wolve can start to breed.
    private static final int BREEDING_AGE = 2;
    // The amount of steps animals can make before they die from the infection.
    private static final int DISEASE_FATALITY = 3;
    
    // Individual characteristics (instance fields).
    // The wolf's age.
    private int age;
    // The wolf's food level, which is increased by eating herbivore.
    private int foodLevelBadger;
    private int foodLevelHedgehog;
    private int foodLevelSquirrel;
    //whether or not the wolf is female
    private boolean isFemale;
    //whether or not the wolf is sick
    private boolean isSick;
    
    /**
     * Create a wolf. A wolf can be created as a new born (age zero
     * and not hungry) or with a random age and food level.
     * 
     * @param randomAge If true, the wolf will have random age and hunger level.
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
        getGender(); //randomize whether it is female or not
        getDisease(); //randomize whether or not the wolf is sick
    }
    
    /**
     * Return the age of a wolf
     * @return age of a wolf.
     */
    protected int getAge(){
        return age;
    }
    
    /**
     * Return maximum age of a wolf
     * @return maximum age of a wolf.
     */
    protected int getMaxAge(){
        return MAX_AGE;
    }
    
    /**
     * Return food value of a badger to a wolf
     * @return food value of a badger to a wolf.
     */
    protected int getBadgerFoodValue(){
        return BADGER_FOOD_VALUE;
    }
    
    /**
     * Return food value of a hedgehog to a wolf
     * @return food value of a hedgehog to a wolf.
     */
    protected int getHedgehogFoodValue(){
        return HEDGEHOG_FOOD_VALUE;
    }
    
    /**
     * Return food value of a squirrel to a wolf
     * @return food value of a squirrel to a wolf.
     */
    protected int getSquirrelFoodValue(){
        return SQUIRREL_FOOD_VALUE;
    }
    
    /**
     * Return food level of a squirrel to a wolf
     * @return food level of a squirrel to a wolf.
     */
    protected int getSquirrelFoodLevel(){
        return foodLevelSquirrel;
    }
    
    /**
     * Return food level of a badger to a wolf
     * @return food level of a badger to a wolf.
     */
    protected int getBadgerFoodLevel(){
        return foodLevelBadger;
    }
    
    /**
     * Return food level of a hedgehog to a wolf
     * @return food level of a hedgehog to a wolf.
     */
    protected int getHedgehogFoodLevel(){
        return foodLevelHedgehog;
    }
    
    /**
     * Return breeding probability of a wolf
     * @return breeding probability of a wolf.
     */
    protected double getBreedingProbability(){
        return BREEDING_PROBABILITY;
    }
    
    /**
     * Return maximum litter size of a wolf
     * @return maximum litter size of a wolf.
     */
    protected int getMaxLitterSize(){
        return MAX_LITTER_SIZE;
    }
    
    /**
     * Return breeding age of a wolf
     * @return breeding age of a wolf.
     */
    protected int getBreedingAge(){
        return BREEDING_AGE;
    }
    
    /**
     * Return a location to breed
     * If the partner is the opposite gender and alive, they can breed
     * @return where location where new partners meet
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
     * @param newWolves A list to return newly born wolves.
     */
    public void giveBirth(List<Animal> newWolves)
    {
        // New wolves are born into adjacent locations.
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
    
    /**
     * Spread diseases for wolves
     */
    protected void spreadDisease(){
        Field field = getField();
        List<Location> adjacent = field.adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        while(it.hasNext()) {
            Location where = it.next();
            Object animal = field.getObjectAt(where);
            if(animal instanceof Wolf && sickOrNot()==true){
                Wolf wolf=(Wolf) animal;
                getDisease();
            }
        }
    }
    
    /**
     * This is what the fox does most of the time: it hunts for
     * badgers and hedgehogs. In the process, it might breed, die of hunger,
     * or die of old age.
     * @param newWolves A list to return newly born wolves.
     */
    public void act(List<Animal> newWolves, String timeText)
    {
        incrementAge();
        incrementHunger();
      
        if(isAlive()) {
             //disease will take effect here if isSick==true;
             if(getDisease() == true){
                  spreadDisease();
                
                  int fatalityLevel = rand.nextInt(DISEASE_FATALITY);
                  if (fatalityLevel == 3){
                      setDead();
                    }
             }
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
}