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
    private Random rand = new Random();
    private int timeToChangeWeather;

    /**
     * Constructor for objects of class Weather
     */
    public Weather()
    {
        changeWeather(0);
    }

    /**
     * An example of a method - replace this comment with your own
     *
     * @param  timeToChange  when the weather changes
     * @param  weather String values for the weather
     * @return    the weather description
     */
    public void changeWeather(int steps)
    {
        //the interval for each weather change is determined by 4
        
            timeToChangeWeather = rand.nextInt((steps % 4) + 1); 
            if(timeToChangeWeather == 0){
                currentWeather="stormy";
            } //when the step count is from 0 to 49, the weather is stormy
            else if(timeToChangeWeather == 1){
                currentWeather="dry";
            } //when the step count is from 50 to 99, the weather is dry
            else if(timeToChangeWeather == 2){
                currentWeather="clear";
            } //when the step count is from 100 to 149, the weather is sunny
            else{
                currentWeather="rainy"; //otherwise, the weather is rainy
            }
        
    }   

    /**
     * Returns the current weather
     *
     * @return    current weather
     */
    public String getWeatherText(){
        return currentWeather;
    }
}
