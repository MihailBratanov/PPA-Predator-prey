import java.util.List;
import java.util.Random;
/**
 * A class representing shared characteristics of animals.
 * 
 * @author David J. Barnes Michael Kölling Hana Mizukami and Mihail Bratanov
 * @version 2019.02.17 (3)
 */
public abstract class Animal
{
    // Whether the animal is alive or not.
    private boolean alive;
    // The animal's field.
    private Field field;
    // The animal's position in the field.
    private Location location;
    private Random rand;
    // Whether the animal is female or not.
    private boolean isFemale;
    // Whether the animal is sick or not.
    private boolean isSick;
    //initialize Time class
    private Time time = new Time();
    //initialize Weather class
    private Weather weather = new Weather();
    /**
     * Create a new animal at location in field.
     * 
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Animal(Field field, Location location)
    {
        alive = true; //animal is alive
        this.field = field;
        rand=new Random();
        getGender(); //returns boolean value for female or male value
        getDisease(); //returns whether the animal is sick or not
        setLocation(location);
    }
    
    /**
     * Make this animal act - that is: make it do
     * whatever it wants/needs to do.
     * @param newAnimals A list to receive newly born animals.
     */
    abstract public void act(List<Animal> newAnimals);

    /**
     * Returns whether the animal is female or not.
     * @return true if the animal is female.
     */
    protected boolean getGender()
    {
        return isFemale=rand.nextBoolean(); //randomized whether the animal is female or not
    }
    
    /**
     * Returns whether the animal is sick or not.
     * @return true if the animal is sick.
     */
    protected boolean getDisease()
    {
        return isSick=rand.nextBoolean(); //randomized whether the animal is sick or not
    }
    
    /**
     * Check whether the animal is sick or not.
     * @return true if the animal is sick.
     */
    protected boolean sickOrNot()
    {
        return isSick;
    }
    
    /**
     * Check whether the animal is female or not.
     * @return true if the animal is female.
     */
    protected boolean getFemaleOrMale(){
      return isFemale;
    }
    
    /**
     * Check whether the animal is alive or not.
     * @return true if the animal is still alive.
     */
    protected boolean isAlive()
    {
        return alive;
    }

    /**
     * Indicate that the animal is no longer alive.
     * It is removed from the field.
     */
    protected void setDead()
    {
        alive = false;
        if(location != null) {
            field.clear(location);
            location = null;
            field = null;
        }
    }
    
    /**
     * Return weather value for the animal.
     * @return weather.
     */
    protected Weather getWeatherAnimal()
    {
        return weather;
    }
    
    /**
     * Return the time.
     * @return time.
     */
    protected Time getTimeAnimal()
    {
        return time;
    }
    
    /**
     * Return the animal's location.
     * @return The animal's location.
     */
    protected Location getLocation()
    {
        return location;
    }
    
    /**
     * Place the animal at the new location in the given field.
     * @param newLocation The animal's new location.
     */
    protected void setLocation(Location newLocation)
    {
        if(location != null) {
            field.clear(location);
        }
        location = newLocation;
        field.place(this, newLocation);
    }
    
    /**
     * Return the animal's field.
     * @return The animal's field.
     */
    protected Field getField()
    {
        return field;
    }
}
