package edu.ldj.planner.compare;

import java.util.Comparator;
import edu.ldj.planner.task.*;

/**
 * A Comparator for comparing tasks by their class
 * @author Louis Jacobowitz
 */
public class CompareClass< T extends Task > implements Comparator<T> {

	/**
	 * Returns a lexigraphic comparison of task1 and task2's class names
	 * @param task1 to compare
	 * @param task2 to compare
	 * @return >1 if task1's class name is lexigraphically greater than task2
	 */
	public int compare( T task1, T task2 ) {
		return task2.getTaskClass().compareTo( task1.getTaskClass() );
	}
}
