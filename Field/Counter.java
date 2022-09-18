package Field;

import java.awt.Color;

/**
 * Provide a counter for a participant in the simulation.
 * This includes an identifying string and a count of how
 * many participants of this type currently exist within 
 * the simulation.
 * 
 * @author David J. Barnes and Michael Kolling. Modified by David Dobervich 2007-2022
 */
public class Counter
{
    // A name for this type of simulation participant
    private String name;
		private Class animalClass;
		
    // How many of this type exist in the simulation.
    private int count;

    /**
     * Provide a name for one of the simulation types.
     * @param animalClass  the class name for an animal type, e.g. "Animals.Fox".
     * @param animalClass  the class name for an animal type, e.g. "Animals.Fox".
     */
    public Counter(Class animalClass)
    {
    		this.animalClass = animalClass;
        this.name = animalClass.getName();
        count = 0;
    }
    
    /**
     * @return The short description of this type.
     */
    public String getName()
    {
        return name;
    }

    /**
     * @return The current count for this type.
     */
    public int getCount()
    {
        return count;
    }

    /**
     * Increment the current count by one.
     */
    public void increment()
    {
        count++;
    }
    
    /**
     * Reset the current count to zero.
     */
    public void reset()
    {
        count = 0;
    }

    /**
     * @return the class name of the type of object for this counter.
     * These are used as keys in colorMap Hashmaps
     */
		public Class getClassName() {
			return animalClass;
		}
}
