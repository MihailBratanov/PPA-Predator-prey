
/**
 * Write a description of class Time here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Time
{
    // instance variables - replace the example below with your own
    
    private int timeCount;
    private String currentTime;
    /**
     * Constructor for objects of class Time
     */
    public Time()
    { 
      // initialise instance variables
     
      
      currentTime=determineTime(timeCount);
     
       
    }

    /**
     * An example of a method - replace this comment with your own
     *
     * @param  y  a sample parameter for a method
     * @return    the sum of x and y
     */
    public String determineTime(int stepCount)
    {
        // put your code here
        String timeOfDay;
        int timeSetter=stepCount%50;
        while(timeSetter<=49 && timeSetter>=25){
            return timeOfDay="day";
        }
       
        return timeOfDay="night";
    }
    
    public void setTime(int time){
        
        timeCount = time;
        
        }
        public String checkTime(){
            return currentTime;
        }
}
