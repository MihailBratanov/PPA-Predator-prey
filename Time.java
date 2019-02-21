import java.util.Random;
/**
 * Time class distinguishes the time frame in the simulator.
 *
 * @author Hana Mizukami and Mihail Bratanov
 * @version 2019.02.17
 */
public class Time
{
    private int timeCount; //value to count time
    private Random rand = new Random();
    private String timeOfDay;
    /**
     * Constructor for objects of class Time
     */
    public Time()
    { 
        changeTime(0);
    }
    /**
     * Returns the time as either day or night depending on the count
     *
     * @param  stepCount  integer for the number of steps
     * @return    day or night as time reference
     */
    public void changeTime(int stepTime){
        int timeSetter = rand.nextInt((stepTime % 2) + 1); //the time count is set with a modulo 2
        if(timeSetter == 1){
            timeOfDay="day";
        } else{//when the step count is from 0 to 49, the weather is stormy{
            timeOfDay="night";
        }
    }
    /**
     * Returns the current time
     *
     * @return    day or night as time reference
     */
    public String returnTime(){
        return timeOfDay;
    }
}
