import java.util.*;
/**
 * Write a description of class Flower here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Flower extends Plant
{
    //properties of a plant
    private  Random rand = Randomizer.getRandom();
    // The age to which a badger can live.
    private static final int MAX_AGE = 5;
    // instance variables - replace the example below with your own
    private boolean isEaten;
    private int plantAge;
    /**
     * Constructor for objects of class Flower
     */
    public Flower(boolean isEaten, boolean randomPlantAge, Field field, Location location)
    {
        super(field,location);
        isEaten = false;
        plantAge = 0;
        if(randomPlantAge) {
            plantAge = rand.nextInt(MAX_AGE);
        }
    }
    
    /**
     * Return age of a badger
     * @return age of a badger.
     */
    protected int getAge(){
        return plantAge;
    }
    
    /**
     * Return maximum age of a badger
     * @return maximum age of a badger.
     */
    protected int getMaxAge(){
        return MAX_AGE;
    }
    
    /**
     * Grow flowers
     */
     public void reGrow(List<Plant> newFlowers, String nameOfWeather){
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        incrementAge();
        int FLOWER_BIRTH_RATE = 5;
        int FLOWER_DEATH_RATE = 10;
        if (nameOfWeather == "rainy"){
            for(int b = 0; b < FLOWER_BIRTH_RATE && free.size() > 0; b++) {
                Location loc = free.remove(0);
                Flower flower = new Flower(false, false, field, loc);
                newFlowers.add(flower);
            }
        } 
    }
}
    
   