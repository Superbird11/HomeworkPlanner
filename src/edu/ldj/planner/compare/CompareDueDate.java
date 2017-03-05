package edu.ldj.planner.compare;

import java.util.Comparator;
import edu.ldj.planner.task.*;

/**
 * A Comparator for comparing Tasks by their due dates
 * @author Louis Jacobowitz
 */
public class CompareDueDate< T extends Task > implements Comparator<T> {

	/**
	 * Compares the two given tasks based on their date
	 * @param task1 to compare
	 * @param task2 to compare
	 * @return >1 if task2 comes sooner than task1
	 */
	@Override
	public int compare(T task1, T task2) {
		EasyDate due1 = task1.getDueDate();
		EasyDate due2 = task2.getDueDate();
		if ( due2.getYear() > due1.getYear() )
			return -1;
		if ( due2.getYear() < due1.getYear() )
			return 1;
		if ( due2.getMonth() > due1.getMonth() )
			return -1;
		if ( due2.getMonth() < due1.getMonth() )
			return 1;
		if ( due2.getDay() > due1.getDay() )
			return -1;
		if ( due2.getDay() < due1.getDay() )
			return 1;
		return 0;
	}

	/**
	 * Compares the given task with the given EasyDate
	 * @param task to compare
	 * @param date to compare with
	 * @return >1 if the given date comes sooner than task1's due date
	 */
	@SuppressWarnings("unchecked")
	public int compareDate( T task, EasyDate date ) {
		return compare( task, (T) new Task( 0, date, 0.0, "", "", "" ) );
	}
}
