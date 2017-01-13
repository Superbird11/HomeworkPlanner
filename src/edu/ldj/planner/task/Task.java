package edu.ldj.planner.task;

import java.util.StringTokenizer;

/**
 * A class representing a homework assignment that needs to be completed.
 * @author Louis Jacobowitz
 */
public class Task {
	/** The priority of this task */
	private int priority;
	/** The date on which this task is due */
	private EasyDate dueDate;
	/** The amount of time this task is estimated to take, in hours */
	private double timeEstimate; 
	/** The name of this task */
	private String taskName;
	/** The details of this task */
	private String taskDetails;
	/** The class for which this task is */
	private String taskClass;
	
	/**
	 * Constructs a new Task with default values:
	 * 0 priority, 0 time, and today's date.
	 */
	public Task() {
		super();
		setPriority( 0 );
		setDueDate( new EasyDate() );
		setTimeEstimate( 0 );
		setTaskName( "" );
		setTaskDetails( "" );
		setTaskClass( "" );
	}
	
	/**
	 * Creates a new Task with the given parameters
	 * @param priority - The priority of this task
	 * @param dueDate - The date when this task will be due
	 * @param timeEstimate - The amount of time this task is estimated to take, in hours
	 * @param taskName - The name of this task
	 * @param taskDetails - The details of this task
	 * @param taskClass - The class this task pertains to
	 */
	public Task(int priority, EasyDate dueDate, double timeEstimate, String taskName, String taskDetails, String taskClass) {
		super();
		setPriority( priority );
		setDueDate( dueDate );
		setTimeEstimate( timeEstimate );
		setTaskName( taskName );
		setTaskDetails( taskDetails );
		setTaskClass( taskClass );
	}

	/**
	 * Returns the priority of this task
	 * @return the priority of this task
	 */
	public int getPriority() {
		return priority;
	}
	
	/**
	 * Sets the priority of this task
	 * @param priority to set
	 */
	public void setPriority( int priority ) {
		this.priority = priority;
	}
	
	/**
	 * Returns the due date of this task
	 * @return due date of this task
	 */
	public EasyDate getDueDate() {
		return dueDate;
	}
	
	/**
	 * Sets the due date of this task
	 * @param dueDate to set
	 */
	public void setDueDate(EasyDate dueDate) {
		this.dueDate = dueDate;
	}
	
	/**
	 * Returns the time estimate, in hours of this task
	 * @return time estimate of this task
	 */
	public double getTimeEstimate() {
		return timeEstimate;
	}
	
	/**
	 * Sets the time estimate of this task
	 * @param time estimate to set
	 */
	public void setTimeEstimate(double timeEstimate) {
		this.timeEstimate = timeEstimate;
	}
	
	/**
	 * Sets the name of this task
	 * @param name to set
	 */
	public String getTaskName() {
		return taskName;
	}
	
	/**
	 * Sets the name of this task
	 * @param name to set
	 */
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	
	/**
	 * Sets the details of this task
	 * @param details to set
	 */
	public String getTaskDetails() {
		return taskDetails;
	}
	
	/**
	 * Sets the details of this task
	 * @param details to set
	 */
	public void setTaskDetails(String taskDetails) {
		this.taskDetails = taskDetails.replace("\t", " ").replace("\n", " ");
	}
	
	/**
	 * Sets the class of this task
	 * @param class to set
	 */
	public String getTaskClass() {
		return taskClass;
	}
	
	/**
	 * Sets the class of this task
	 * @param class to set
	 */
	public void setTaskClass(String taskClass) {
		this.taskClass = taskClass;
	}
	
	/**
	 * Prints this task in the following manner:
	 * 		Priority	Date(12)	Name(20)	Details...(60)	Duration( 5 )	Class( 20 )
	 */
	public String toString() {
		String newName = taskName;
		if( newName.length() > 12 ) {
			newName = newName.substring( 0, 9 ) + "...";
		}
		String newDetails = taskDetails;
		if( newDetails.length() > 30 ) {
			newDetails = newDetails.substring( 0, 27 ) + "...";
		}
		String newClassName = taskClass;
		if( newClassName.length() > 12 ) {
			newClassName = newClassName.substring( 0, 9 ) + "...";
		}
		String doubleString = String.valueOf( timeEstimate );
		if( doubleString.length() > 5 ) {
			doubleString = doubleString.substring( 0, 5 );
		}
		newDetails = newDetails.replace( "\n", " " );
		return String.format( "%8d | %11s | %12s | %30s | %5s | %12s", priority, dueDate.toString(), newName, newDetails, doubleString, newClassName );
	}
	
	/**
	 * Returns a String for printing this Task to a file, in the following order, tab-separated:
	 * 1. (int) The priority of this task
	 * 2. (int) The year of this task's date
	 * 3. (int) The month of this task's date
	 * 4. (int) The day of this task's date
	 * 5. (int) The number of tokens in this task's name, starting from the first non-whitespace character
	 * 6. (string) The task's name, in full
	 * 7. (int) The number of tokens in this task's description, starting from the first non-whitespace character
	 * 8. (string) The task's description, in full
	 * 9. (double) The duration of this class
	 * 10. (int) The number of tokens in this task's class, starting from the first non-whitespace character
	 * 11. (string) the task's class
	 * 12. A newline character
	 * @return
	 */
	public String toFile() {
		return String.format( "%d\t%d\t%d\t%d\t%d\t%s\t%d\t%s\t%f\t%d\t%s\n",
				/* 1 */ priority,
				/* 2 */ dueDate.getYear(),
				/* 3 */ dueDate.getMonth(),
				/* 4 */ dueDate.getDay(),
				/* 5 */ ( new StringTokenizer( taskName.trim(), " " ) ).countTokens(),
				/* 6 */ taskName.trim(),
				/* 7 */ ( new StringTokenizer( taskDetails.trim(), " " ) ).countTokens(),
				/* 8 */ taskDetails.trim(),
				/* 9 */ timeEstimate,
				/* 10 */ ( new StringTokenizer( taskClass.trim(), " " ) ).countTokens(),
				/* 11 */ taskClass.trim() );
	}
}
