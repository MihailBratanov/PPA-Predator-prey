import java.util.*;
/**
 * Write a description of class Flower here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Flower extends Plant
{
    // instance variables - replace the example below with your own
    private boolean isEaten;

    /**
     * Constructor for objects of class Flower
     */
    public Flower(boolean isEaten, Field field, Location location)
    {
        super(field,location);
        // initialise instance variables
        isEaten=isEaten();
    }
  /**
     * Grow flowers
     */
     public void reGrow(List<Plant> newFlowers){
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
         
        
       if( free.size() > 0 && canGrow()==true) {
            Location loc = free.remove(0);
            Flower flower = new Flower(false,field, loc);
            newFlowers.add(flower);
        }
       
    }
}
    
   