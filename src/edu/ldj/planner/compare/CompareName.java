package edu.ldj.planner.compare;

import java.util.Comparator;
import edu.ldj.planner.task.*;

/**
 * A Comparator for comparing tasks by their task name
 * @author Louis Jacobowitz
 */
public class CompareName< T extends Task > implements Comparator<T> {

	/**
	 * Returns a lexigraphic comparison of task1 and task2's task names
	 * @param task1 to compare
	 * @param task2 to compare
	 * @return >1 if task2's task name is lexigraphically greater than task1
	 */
	public int compare( T task1, T task2 ) {
		return task1.getTaskName().compareTo( task2.getTaskName() );
	}
}
