import java.util.Random;
import java.util.List;
/**
 * Plant class takes into acount of what plants do as associated with herbivores.
 *
 * @author Hana Mizukami and Mihail Bratanov
 * @version 2019.02.17
 */
public class Plant
{
    //properties of a plant
    private  Random rand = Randomizer.getRandom();
    //allocate a field
    private Field plantField;
    //allocate plant location
    private Location plantLocation;
    // whether or not the plan is eaten by herbivores
    private boolean eaten;
    /**
     * Constructor for objects of class Plant
     */
    private Plant(Field field, Location location)
    {
        this.plantField = field;
        setLocation(location);
        rand = new Random();
        eaten = false; //not eaten 
    }

    /**
     * Check whether the plant is eaten or not.
     * @return true if the plant is eaten.
     */
    public boolean isEaten()
    {
        return eaten;
    }
    
    /**
     * Place the plant at the new location in the given field.
     * @param newLocation The plant's new location.
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
     * Return the location plant is planted
     * 
     * @return location of the plant
     */
    public Location getLocation()
    {
        return plantLocation;
    }
    
    /**
     * Return the field where the plant is at
     * @return the field of the plant.
     */
    public Field getField()
    {
        return plantField;
    }
    
    /**
     * Grow the plants after the herbivores eat the plants
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