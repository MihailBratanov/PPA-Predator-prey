import java.util.*;
import java.util.Random;
/**
 * Write a description of class Weather here.
 *
 * @Hana Mizukami (Hana)
 * @version (a version number or a date)
 */
public class Weather
{
    // instance variables - replace the example below with your own
   
    //private int weatherIndex;
   
    private String currentWeather;
    private int stepCount;
  // private Simulator simulator;
    /**
     * Constructor for objects of class Weather
     */
    public Weather()
    {
        // initialise instance variables
      currentWeather=determineWeather(stepCount);
        
    }
    /**
     * An example of a method - replace this comment with your own
     *
     * @param  y  a sample parameter for a method
     * @return    the sum of x and y
     */
    public String determineWeather(int steps)
    {
        String weather;
        int timeToChange=steps%200;
        while(timeToChange>=0 && timeToChange<=49){
        //weatherIndex = rand.nextInt();
       // currentWeather = weatherChanges.get(weatherIndex);
       return weather="stormy";
        
    }
     while(timeToChange>=50 && timeToChange<=99){
        return weather="dry";
    }
      while(timeToChange>=100 && timeToChange<=149){
        return weather="sunny";
    }
   
    return weather="rainy";
    }
    public void setWeather(int step)
    {
        
     stepCount=step;   
    }
    public String checkWeather(){
        return currentWeather;
    }
}
