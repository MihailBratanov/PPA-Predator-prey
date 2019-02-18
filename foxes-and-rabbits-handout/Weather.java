import java.util.*;
import java.util.Random;
/**
 * Weather class stores the possible weathers for each time step.
 *
 * @Hana Mizukami (Hana)
 * @version1 14/02/2019 
 */
public class Weather
{
    private String currentWeather; //String value for the current weather
    private int stepCount; //
    /**
     * Constructor for objects of class Weather
     */
    public Weather()
    {
        // call the current weather by determine method
        currentWeather=determineWeather(stepCount);
    }
    /**
     * An example of a method - replace this comment with your own
     *
     * @param  timeToChange  when the weather changes
     * @param  weather String values for the weather
     * @return    the weather description
     */
    public String determineWeather(int steps)
    {
        String weather;
        int timeToChange = steps % 200; //the interval for each weather change is determined by 200
        while(timeToChange >= 0 && timeToChange <= 49){
            return weather="stormy";
        } //when the step count is from 0 to 49, the weather is stormy
        while(timeToChange >= 50 && timeToChange <= 99){
            return weather="dry";
        } //when the step count is from 50 to 99, the weather is dry
        while(timeToChange >= 100 && timeToChange <= 149){
            return weather="sunny";
        } //when the step count is from 100 to 149, the weather is sunny
        return weather="rainy"; //otherwise, the weather is rainy
    }
    
    /**
     * Set stepCount as the number of weather changes
     *
     * @param  step  input integer of the number of steps    
     */
    public void setWeather(int step)
    {
        stepCount = step;   
    }
    
    /**
     * Returns the current weather
     *
     * @return    current weather
     */
    public String checkWeather(){
        return currentWeather;
    }
}