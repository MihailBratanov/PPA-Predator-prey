import java.util.List;
import java.util.Random;
import java.util.Iterator;
/**
 * A simple model of a herbivore.
 * Rabbits age, move, breed, and die.
 * 
 * @author David J. Barnes Michael Kölling Hana Mizukami and Mihail Bratanov
 * @version 2019.02.18 (3)
 */
public abstract class Herbivore extends Animal
{
    private  Random rand = Randomizer.getRandom();
    /**
     * Create a new herbivore. 
     * 
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Herbivore(Field field, Location location)
    {
        super(field, location);
    }
    /**
     * Return the breeding probability of a herbivore
     * @return the breeding probability of a herbivore.
     */
    abstract protected double getBreedingProbability();
    
    /**
     * Return the maximum litter size of a herbivore
     * @return the maximum litter size of a herbivore.
     */
    abstract protected int getMaxLitterSize();
    
    /**
     * Return the age of a herbivore
     * @return the age of a herbivore.
     */
    abstract protected int getAge();
    
    /**
     * Return the maximum age of a herbivore
     * @return the maximum age of a herbivore.
     */
    abstract protected int getMaxAge();
    
    /**
     * Return the breeding age of a herbivore
     * @return the breeding age of a herbivore.
     */
    abstract protected int getBreedingAge();
    
    /**
     * Create a list of herbivores
     * @param newHerbivores A list to return newly born herbivores.
     */
    abstract protected void giveBirth(List<Animal> newHerbivores);
    
    /**
     * Return the fatality rate of a herbivore
     * @return the fatality rate of a herbivore.
     */
    abstract protected int getDiseaseFatality();
    
    /**
     * Return the food value of a herbivore for flower
     * @return the food value of a herbivore for flower
     */
    abstract protected int getFlowerFoodValue();
    
    /**
     * Return the food level of a herbivore for flower
     * @return the food level of a herbivore for flower
     */
    abstract protected int getFlowerFoodLevel();
    
    /**
     * This is what the herbivore does most of the time - it runs 
     * around. Sometimes it will breed or die of old age.
     * @param newHerbivores A list to return newly born herbivores.
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
                Location newLocation = findFood();
               if(newLocation == null) { 
                   // No food found - try to move to a free location.
                   newLocation = getField().freeAdjacentLocation(getLocation());
                }
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
     * Make this predator more hungry. This could result in the predator's death.
     */
    protected void incrementHunger()
    {
        int f = getFlowerFoodLevel();
        
        f--;
       
      
        if(f<=0 ){
            setDead();
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
     * Spread sickness among herbivores
     */
    protected void spreadDisease(){
        Field field = getField();
        List<Location> adjacent = field.adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        while(it.hasNext()) {
            Location where = it.next();
            Object animal = field.getObjectAt(where);
            if(animal instanceof Animal){
                Animal animal1=(Animal) animal;
                if (animal1 instanceof Animal && sickOrNot()==true){
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
     * A herbivore can breed if it has reached the breeding age.
     * @return true if the herbivore can breed, false otherwise.
     */
    protected boolean canBreed()
    {
        return getAge() >= getBreedingAge();
    }

    /**
     * Look for plants adjacent to the current location.
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
            Object plant = field.getObjectAt(where);
            if(plant instanceof Flower) {
                Flower flower = (Flower) plant;
                if(!flower.isEaten()) { 
                    flower.setDead();
                    int b = getFlowerFoodValue();
                    return where;
                }
            }
        }//if the adjacent animal was a flower, flower dies and the food value is set as the location is allocated
            return null; //no location
        }
    
    
}



    

