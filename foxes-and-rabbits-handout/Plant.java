import java.util.Random;
import java.util.List;
/**
 * Plant class takes into acount of what plants do as associated with herbivores.
 *
 * @author Hana Mizukami and Mihail Bratanov
 * @version 2019.02.17
 */
public abstract class Plant
{
    //properties of a plant
    private  Random rand = Randomizer.getRandom();
    //allocate a field
    private Field plantField;
    //allocate plant location
    private Location plantLocation;
    // whether or not the plan is eaten by herbivores
    private boolean eaten;
    private Weather weather=new Weather();
    /**
     * Constructor for objects of class Plant
     */
    public Plant(Field field, Location plantLocation)
    {
       plantField = field;
        setLocation(plantLocation);
        rand = new Random();
        eaten = false; //not eaten 
    }

    /**
     * Check whether the plant is eaten or not.
     * @return true if the plant is eaten.
     */
    protected boolean isEaten()
    {
        return eaten;
    }
    
      /**
     * Indicate that the plant is no longer alive.
     * It is removed from the field.
     */
    protected void setDead()
    {
        eaten = true;
        if(plantLocation != null) {
            plantField.clear(plantLocation);
            plantLocation = null;
            plantField = null;
        }
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
    
    protected Location getLocation()
    {
        return plantLocation;
    }
    
    /**
     * Return the field where the plant is at
     * @return the field of the plant.
     */
    protected Field getField()
    {
        return plantField;
    }
    
    /**
     * Grow the plants after the herbivores eat the plants
     */
    abstract void reGrow(List<Plant> newPlants);
     /**
     * 
     */
    protected boolean canGrow(){
        if(weather.checkWeather().equals("rainy") && isEaten()==true){
            return true;
    }
    return false;
}
}