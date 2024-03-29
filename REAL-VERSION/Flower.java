import java.util.*;
/**
 * Write a description of class Flower here.
 *
 * @author Hana Mizukami and Mihail Bratanov
 * @version 2019/02/22
 */
public class Flower extends Plant
{
    //properties of a flower
    private  Random rand = Randomizer.getRandom();
    // The age to which a flower can live.
    private static final int MAX_AGE = 30;
    //Whether it is eaten or not
    private boolean isEaten;
    //the age of the flower
    private int plantAge;
    /**
     * Constructor for objects of class Flower
     * 
     * @param isEaten whether it is eaten
     * @param randomPlantAge whether given a random age or not
     * @param field where the flower is located 
     * @param location where the flower is located
     */
    public Flower(boolean isEaten, boolean randomPlantAge, Field field, Location location)
    {
        super(field,location);
        isEaten = false; //not eaten
        plantAge = 0;
        if(randomPlantAge) {
            plantAge = rand.nextInt(MAX_AGE);
        }
    }

    /**
     * Return age of a flower
     * @return age of a flower.
     */
    protected int getAge(){
        return plantAge;
    }

    /**
     * Return maximum age of a flower
     * @return maximum age of a flower.
     */
    protected int getMaxAge(){
        return MAX_AGE;
    }

    /**
     * Grow flowers
     * 
     * @param newFlowers a list of new flowers
     * @param nameOfWeather the weather type
     */
    public void reGrow(List<Plant> newFlowers, String nameOfWeather){
        //get a location
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());

        incrementAge();
        //check whether it is rainy or stormy
        if ((free.size() > 0 && nameOfWeather == "rainy")||(free.size() > 0 && nameOfWeather == "stormy")){
            setDead();
            
            Location loc = free.remove(0);
            Flower flower = new Flower(false, false, field, loc);
            newFlowers.add(flower);   
        } 
    }
}

