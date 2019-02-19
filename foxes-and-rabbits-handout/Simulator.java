import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.awt.Color;

/**
 * A simple predator-prey simulator, based on a rectangular field
 * containing rabbits and foxes.
 * 
 * @author Hana Mizukami and Mihail 
 * @version 2019.02.17
 */
public class Simulator
{
    // Constants representing configuration information for the simulation.
    // The default width for the grid.
    private static final int DEFAULT_WIDTH = 120;
    // The default depth of the grid.
    private static final int DEFAULT_DEPTH = 80;
    // The probability that a snake will be created in any given grid position.
    private static final double SNAKE_CREATION_PROBABILITY = 0.03;
    // The probability that a badger will be created in any given grid position.
    private static final double BADGER_CREATION_PROBABILITY = 0.04; 
    // The probability that a squirrel will be created in any given grid position.
    private static final double SQUIRREL_CREATION_PROBABILITY = 0.05; 
    // The probability that a hedgehog will be created in any given grid position.
    private static final double HEDGEHOG_CREATION_PROBABILITY = 0.06;
    // The probability that a wof will be created in any given grid position.
    private static final double WOLF_CREATION_PROBABILITY = 0.04; 
    //The probability that a plant will be created in any given grid position.
    private static final double PLANT_CREATION_PROBABILITY= 0.08;
    // List of animals in the field.
    private List<Animal> animals;
    private List <Plant> plants;
    // The current state of the field.
    private Field field;
    private Field plantField;
    // The current step of the simulation.
    private int step;
    // A graphical view of the simulation.
    private SimulatorView view;
    private Time time=new Time();
    private Weather weather=new Weather();
    /**
     * Construct a simulation field with default size.
     */
    public Simulator()
    {
        this(DEFAULT_DEPTH, DEFAULT_WIDTH);
    }
    
    /**
     * Create a simulation field with the given size.
     * @param depth Depth of the field. Must be greater than zero.
     * @param width Width of the field. Must be greater than zero.
     */
    public Simulator(int depth, int width)
    {
        if(width <= 0 || depth <= 0) {
            System.out.println("The dimensions must be greater than zero.");
            System.out.println("Using default values.");
            depth = DEFAULT_DEPTH;
            width = DEFAULT_WIDTH;
        }
        
        animals = new ArrayList<>();
        plants =new ArrayList<>();
        field = new Field(depth, width);
        plantField=new Field(depth,width);

        // Create a view of the state of each location in the field.
        view = new SimulatorView(depth, width);
        view.setColor(Badger.class, Color.ORANGE);
        view.setColor(Snake.class, Color.BLUE);
        view.setColor(Squirrel.class,Color.RED);
        view.setColor(Hedgehog.class,Color.YELLOW);
        view.setColor(Wolf.class,Color.BLACK);
        view.setColor(Flower.class,Color.GREEN);
        
        
        // Setup a valid starting point.
        reset();
    }
    
    /**
     * Run the simulation from its current state for a reasonably long period,
     * (4000 steps).
     */
    public void runLongSimulation()
    {
        simulate(4000);
    }
    
    /**
     * Run the simulation from its current state for the given number of steps.
     * Stop before the given number of steps if it ceases to be viable.
     * @param numSteps The number of steps to run for.
     */
    public void simulate(int numSteps)
    {
        for(int step = 1; step <= numSteps && view.isViable(field); step++) {
            simulateOneStep();
             delay(60);   // uncomment this to run more slowly
        }
    }
    
    /**
     * Run the simulation from its current state for a single step.
     * Iterate over the whole field updating the state of each
     * fox and rabbit.
     */
    public void simulateOneStep()
    {
        step++;
        // Provide space for newborn animals.
        List<Animal> newAnimals = new ArrayList<>();  
        List<Plant> newPlants=new ArrayList<>();
        // Let all rabbits act.
        for(Iterator<Animal> it = animals.iterator(); it.hasNext(); ) {
            Animal animal = it.next();
            animal.act(newAnimals);
            if(! animal.isAlive()) {
                it.remove();
            }
        }
         for(Iterator<Plant> it = plants.iterator(); it.hasNext(); ) {
            Plant plant = it.next();
            plant.reGrow(newPlants);
            if(plant.isEaten()) {
                it.remove();
            }
        }
               
        // Add the newly born foxes and rabbits to the main lists.
        plants.addAll(newPlants);
        animals.addAll(newAnimals);
        time.setTime(step);
        weather.setWeather(step);
        view.showStatus(step, field, plantField);
    }
    /**
     * Gets the current step
     */
    public int getStep(){
        return step;
    }
    /**
     * Reset the simulation to a starting position.
     */
    public void reset()
    {
        step = 0;
        animals.clear();
        plants.clear();
        populate();
        
        // Show the starting state in the view.
        view.showStatus(step, field, plantField);
    }
    
    /**
     * Randomly populate the field with foxes and rabbits.
     */
    private void populate()
    {
        Random rand = Randomizer.getRandom();
        field.clear();
        for(int row = 0; row < field.getDepth(); row++) {
            for(int col = 0; col < field.getWidth(); col++) {
                if(rand.nextDouble() <= SNAKE_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Snake snake = new Snake(true, field, location);
                    animals.add(snake);
                }
                else if(rand.nextDouble() <= BADGER_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Badger badger = new Badger(true, field, location);
                    animals.add(badger);
                }
                else if(rand.nextDouble() <= SQUIRREL_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Squirrel squirrel = new Squirrel(true, field, location);
                    animals.add(squirrel);
                }
                else if(rand.nextDouble() <= HEDGEHOG_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Hedgehog hedgehog = new Hedgehog(true, field, location);
                    animals.add(hedgehog);
            }
               else if(rand.nextDouble() <= WOLF_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Wolf wolf = new Wolf(true, field, location);
                    animals.add(wolf);
            }
             
        }
        
        }
        plantField.clear();
        for(int row = 0; row < plantField.getDepth(); row++) {
            for(int col = 0; col < plantField.getWidth(); col++) {
                if(rand.nextDouble() <= PLANT_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Flower flower = new Flower(false, field, location);
                    plants.add(flower);
            }
        }
    }
}
    /**
     * Pause for a given time.
     * @param millisec  The time to pause for, in milliseconds
     */
    private void delay(int millisec)
    {
        try {
            Thread.sleep(millisec);
        }
        catch (InterruptedException ie) {
            // wake up
        }
    }
}
