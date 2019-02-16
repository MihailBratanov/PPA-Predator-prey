import java.util.List;
import java.util.Random;
import java.util.Iterator;
/**
 * A simple model of a rabbit.
 * Rabbits age, move, breed, and die.
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 2016.02.29 (2)
 */
public abstract class Herbivore extends Animal
{
    // The age at which a rabbit can start to breed.
    //private  int BREEDING_AGE;
    // The age to which a rabbit can live.
    //private int MAX_AGE;
    // The likelihood of a rabbit breeding.
    //private  double BREEDING_PROBABILITY;
    // The maximum number of births.
    //private int MAX_LITTER_SIZE;
    // A shared random number generator to control breeding.
    private  Random rand = Randomizer.getRandom();
    // The rabbit's age.
    //private int age;
    
    /**
     * Create a new rabbit. A rabbit may be created with age
     * zero (a new born) or with a random age.
     * 
     * @param randomAge If true, the rabbit will have a random age.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Herbivore(Field field, Location location)
    {
        super(field, location);
    }
    /**
     * Check whether or not this rabbit is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newRabbits A list to return newly born rabbits.
     */
    abstract protected double getBreedingProbability();
    /**
     * Check whether or not this rabbit is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newRabbits A list to return newly born rabbits.
     */
    abstract protected int getMaxLitterSize();
    /**
     * Check whether or not this rabbit is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newRabbits A list to return newly born rabbits.
     */
    abstract protected int getAge();
    /**
     * Check whether or not this rabbit is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newRabbits A list to return newly born rabbits.
     */
    abstract protected int getMaxAge();
    /**
     * Check whether or not this rabbit is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newRabbits A list to return newly born rabbits.
     */
    abstract protected int getBreedingAge();
    /**
     * Check whether or not this rabbit is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newRabbits A list to return newly born rabbits.
     */
    abstract protected void giveBirth(List<Animal> newHerbivores);
      /**
     * Check whether or not this rabbit is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newRabbits A list to return newly born rabbits.
     */
    abstract protected int getDiseaseFatality();
    /**
     * This is what the rabbit does most of the time - it runs 
     * around. Sometimes it will breed or die of old age.
     * @param newRabbits A list to return newly born rabbits.
     */
    public void act(List<Animal> newHerbivores)
    {
        while(getTimeAnimal().checkTime().equals("day")){
        incrementAge();
        
        if(isAlive()) {
              if(getDisease()==true){
                  spreadDisease();
                  
                  int fatalityLevel=rand.nextInt(getDiseaseFatality());
                  if (fatalityLevel==3){
                      
                      setDead();
                     
                    }
                }
            giveBirth(newHerbivores);     
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
    /**
     * Increase the age.
     * This could result in the rabbit's death.
     */
    protected void incrementAge()
    {
        int age = getAge();
        age++;
        if(age > getMaxAge()) {
           setDead();
        }
    }  
              /**
     * Check whether or not this rabbit is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newRabbits A list to return newly born rabbits.
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
     * Generate a number representing the number of births,
     * if it can breed.
     * @return The number of births (may be zero).
     */
    protected int breed()
    {
        int births = 0;
        if(canBreed() && rand.nextDouble() <= getBreedingProbability()) {
            births = rand.nextInt(getMaxLitterSize()) + 1;
        }
        return births;
    }
    /**
     * A rabbit can breed if it has reached the breeding age.
     * @return true if the rabbit can breed, false otherwise.
     */
    protected boolean canBreed()
    {
        return getAge() >= getBreedingAge();
    }
}
