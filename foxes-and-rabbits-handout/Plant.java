import java.util.Random;
import java.util.List;
/**
 * Write a description of class Plant here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Plant
{
    // instance variables - replace the example below with your own
    private  Random rand = Randomizer.getRandom();
    
    private Field plantField;
    
    private Location plantLocation;
    
    private boolean eaten;
    /**
     * Constructor for objects of class Plant
     */
    private Plant(Field field, Location location)
    {
        // initialise instance variables
        this.plantField = field;
        setLocation(location);
        rand = new Random();
        eaten = false;
    }

    /**
     * Check whether the animal is alive or not.
     * @return true if the animal is still alive.
     */
    public boolean isEaten()
    {
        return eaten;
    }
    /**
     * Place the animal at the new location in the given field.
     * @param newLocation The animal's new location.
     */
    protected void setLocation(Location newLocation)
    {
        if(plantLocation != null) {
            plantField.clear(plantLocation);
        }
        plantLocation = newLocation;
        plantField.place(this, newLocation);
    }
    /**
     * Place the animal at the new location in the given field.
     * @param newLocation The animal's new location.
     */
    public Location getLocation()
    {
        return plantLocation;
    }
    /**
     * Place the animal at the new location in the given field.
     * @param newLocation The animal's new location.
     */
    public Field getField()
    {
        return plantField;
    }
    /**
     * 
     */
    public void reGrow(List<Plant> newPlants){
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        if(free.size() > 0) {
            Location loc = free.remove(0);
            Plant newPlant = new Plant(field, loc);
            newPlants.add(newPlant);
        }
    }
}
