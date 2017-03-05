package edu.ldj.planner.compare;

import java.util.Comparator;
import edu.ldj.planner.task.*;

/**
 * A Comparator for comparing tasks by their class, in reverse alphabetical order
 * @author Louis Jacobowitz
 */
public class CompareReverseClass< T extends Task > implements Comparator<T> {

	/**
	 * Returns a lexigraphic comparison of task1 and task2's class names
	 * @param task1 to compare
	 * @param task2 to compare
	 * @return >1 if task2's class name is lexigraphically less than task1
	 */
	public int compare( T task1, T task2 ) {
		return -1 * ( task1.getTaskClass().compareTo( task2.getTaskClass() ) );
	}
}
