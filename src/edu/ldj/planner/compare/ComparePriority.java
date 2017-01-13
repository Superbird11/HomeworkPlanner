package edu.ldj.planner.compare;

import java.util.Comparator;
import edu.ldj.planner.task.*;

/**
 * A comparator for comparing tasks by priority
 * @author Louis Jacobowitz
 */
public class ComparePriority< T extends Task > implements Comparator< T > {

	/**
	 * Compares the two given tasks based on their priority
	 * @param task1 to compare
	 * @param task2 to compare
	 * @return >1 if task1 is higher priority than task2 (that is, its priority is closer to 0)
	 */
	@Override
	public int compare(T task1, T task2) {
		return task2.getPriority() - task1.getPriority();
	}

}
