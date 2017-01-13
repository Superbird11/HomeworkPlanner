package edu.ldj.planner.compare;

import java.util.Comparator;
import edu.ldj.planner.task.*;

/**
 * A comparator for comparing tasks by their estimated completion time
 * @author Louis Jacobowitz
 */
public class CompareTime< T extends Task > implements Comparator<T> {

	/**
	 * Compares the two given tasks based on their estimated completion time
	 * @param task1 to compare
	 * @param task2 to compare
	 * @return >1 if task1 is shorter than task2
	 */
	@Override
	public int compare( T task1, T task2 ) {
		return (int) ( 100 * ( task2.getTimeEstimate() - task1.getTimeEstimate() ) );
	}

}
