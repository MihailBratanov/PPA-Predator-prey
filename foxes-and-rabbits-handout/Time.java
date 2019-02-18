
/**
 * Time class distinguishes the time frame in the simulator.
 *
 * @author Hana Mizukami and Mihail Bratanov
 * @version 2019.02.17
 */
public class Time
{
    private int timeCount; //value to count time
    private String currentTime; //String value for current time
    /**
     * Constructor for objects of class Time
     */
    public Time()
    { 
        currentTime = determineTime(timeCount); //initialize current time
    }

    /**
     * Returns the time as either day or night depending on the count
     *
     * @param  stepCount  integer for the number of steps
     * @return    day or night as time reference
     */
    public String determineTime(int stepCount)
    {
        //timeOfDay indicates what time frame it is at
        String timeOfDay;
        int timeSetter = stepCount % 50; //the time count is set with a modulo 50
        while(timeSetter <= 49 && timeSetter >= 25){
            return timeOfDay="day";
        } //when the remainder is from 25 to 49, it is "day"
        return timeOfDay="night"; //otherwise, "night"
    }
    
    /**
     * Sets the time input as timeCount
     *
     * @param  time  the number time counts
     */
    public void setTime(int time){
        timeCount = time;
    }
    
    /**
     * Returns the current time
     *
     * @return    day or night as time reference
     */
    public String checkTime(){
        return currentTime;
    }
}
