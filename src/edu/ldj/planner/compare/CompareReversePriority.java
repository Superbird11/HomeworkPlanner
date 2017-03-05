package edu.ldj.planner.compare;

import java.util.Comparator;
import edu.ldj.planner.task.*;

/**
 * A comparator for comparing tasks by priority
 * @author Louis Jacobowitz
 */
public class CompareReversePriority< T extends Task > implements Comparator< T > {

	/**
	 * Compares the two given tasks based on their priority
	 * @param task1 to compare
	 * @param task2 to compare
	 * @return >1 if task1 is lower priority than task2 (that is, its priority is further away from 0)
	 */
	@Override
	public int compare(T task1, T task2) {
		return -1 * ( task2.getPriority() - task1.getPriority() );
	}

}
