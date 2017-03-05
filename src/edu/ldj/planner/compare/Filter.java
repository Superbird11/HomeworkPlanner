package edu.ldj.planner.compare;

import edu.ldj.planner.task.EasyDate;
import edu.ldj.planner.task.Task;

/**
 * A class representing a list of filters that can be applied to a Task list
 * @author Louis Jacobowitz
 */
public class Filter {
	/** Filter criteria */
	private Integer priorityMin;
	private Integer priorityMax;
	private String nameContains;
	private String nameNotContains;
	private String descriptionContains;
	private String descriptionNotContains;
	private String classContains;
	private String classNotContains;
	private Double durationMin;
	private Double durationMax;
	
	/** Date filter criteria */
	private Integer numDaysAhead;
	private EasyDate dateAfter;
	private EasyDate dateBefore;
	private boolean dateSetting;
	
	/**
	 * Initializes a new Filter, with all fields as null
	 */
	public Filter() {
		priorityMin = null;
		priorityMax = null;
		nameContains = null;
		nameNotContains = null;
		descriptionContains = null;
		descriptionNotContains = null;
		classContains = null;
		classNotContains = null;
		durationMin = null;
		durationMax = null;
		numDaysAhead = null;
		dateAfter = null;
		dateBefore = null;
		dateSetting = true;
	}
	
	/**
	 * Using this Filter's variables, determines whether the given task fits within the filter.
	 * Conditions in the Filter that are null are not checked.
	 * @param task to check
	 * @return true if the task meets all this filter's conditions
	 */
	public boolean meetsFilter( Task t ) {
		boolean success = true;
		if ( priorityMin != null && !( t.getPriority() >= priorityMin.intValue() ) )
			success = false;
		if ( priorityMax != null && !( t.getPriority() <= priorityMax.intValue() ) )
			success = false;
		if ( durationMin != null && !( t.getTimeEstimate() >= durationMin.doubleValue() ) )
			success = false;
		if ( durationMax != null && !( t.getTimeEstimate() <= durationMax.intValue() ) )
			success = false;
		if ( nameContains != null && !( t.getTaskName().contains( nameContains ) ) )
			success = false;
		if ( nameNotContains != null && ( t.getTaskName().contains( nameNotContains ) ) )
			success = false;
		if ( descriptionContains != null && !( t.getTaskDetails().contains( descriptionContains ) ) )
			success = false;
		if ( descriptionNotContains != null && ( t.getTaskDetails().contains( descriptionNotContains ) ) )
			success = false;
		if ( classContains != null && !( t.getTaskClass().contains( classContains ) ) )
			success = false;
		if ( classNotContains != null && ( t.getTaskClass().contains( classNotContains ) ) )
			success = false;
		if ( dateSetting ) {
			CompareDueDate<Task> cdd = new CompareDueDate<Task>();
			if ( dateAfter != null && !( cdd.compareDate( t, dateAfter ) >= 0 ) )
				success = false;
			if ( dateBefore != null && !( cdd.compareDate( t, dateBefore ) <= 0 ) )
				success = false;
		}
		else {
			if ( numDaysAhead != null ) {
				EasyDate dateAhead = new EasyDate();
				dateAhead.addDays( numDaysAhead.intValue() );
				if ( ( new CompareDueDate<Task>() ).compareDate( t, dateAhead ) > 0 )
					success = false;
			}
		}
		
		return success;
	}
	
	// All setters and getters below. 
	// They differ from standard setter/getter behavior but are standard format here.
	
	/**
	 * Sets the number of days ahead for this filter, based on a String.
	 * If the String is empty or null, sets it to null.
	 * If the input value is an invalid conversion, keeps the current value.
	 * @param input value
	 */
	public void setNumDaysAhead( String input ) {
		if ( input == null || input.isEmpty() ) {
			numDaysAhead = null;
		}
		else {
			try { numDaysAhead = new Integer( input ); } finally {;}
		}
	}
	
	/**
	 * Sets the date after for this filter, based on an EasyDate.
	 * @param input value
	 */
	public void setDateAfter( EasyDate input ) {
		dateAfter = input;
	}
	
	/**
	 * Sets the date before for this filter, based on an EasyDate.
	 * @param input value
	 */
	public void setDateBefore( EasyDate input ) {
		dateBefore = input;
	}
	
	/**
	 * Sets the date setting - true for after/before, false for days ahead
	 * @param input
	 */
	public void setDateSetting( boolean input ) {
		dateSetting = input;
	}
	
	/**
	 * Sets the minimum priority for this filter, based on a String.
	 * If the String is empty or null, sets it to null.
	 * If the input value is an invalid conversion, keeps the current value.
	 * @param input value
	 */
	public void setPriorityMin( String input ) {
		if ( input == null || input.isEmpty() ) {
			priorityMin = null;
		}
		else {
			try { priorityMin = new Integer( input ); } catch ( NumberFormatException e ) {;}
		}
	}
	
	/**
	 * Sets the maximum priority for this filter, based on a String.
	 * If the String is empty or null, sets it to null.
	 * If the input value is an invalid conversion, keeps the current value.
	 * @param input value
	 */
	public void setPriorityMax( String input ) {
		if ( input == null || input.isEmpty() ) {
			priorityMax = null;
		}
		else {
			try { priorityMax = new Integer( input ); } catch ( NumberFormatException e ) {;}
		}
	}
	
	/**
	 * Sets the maximum duration for this filter, based on a String.
	 * If the String is empty or null, sets it to null.
	 * If the input value is an invalid conversion, keeps the current value.
	 * @param input value
	 */
	public void setDurationMax( String input ) {
		if ( input == null || input.isEmpty() ) {
			durationMax = null;
		}
		else {
			try { durationMax = new Double( input ); } catch ( NumberFormatException e ) {;}
		}
	}
	
	/**
	 * Sets the minimum duration for this filter, based on a String.
	 * If the String is empty or null, sets it to null.
	 * If the input value is an invalid conversion, keeps the current value.
	 * @param input value
	 */
	public void setDurationMin( String input ) {
		if ( input == null || input.isEmpty() ) {
			durationMin = null;
		}
		else {
			try { durationMin = new Double( input ); } catch ( NumberFormatException e ) {;}
		}
	}
	
	/**
	 * Sets the "name contains" field for this filter, based on a String.
	 * If the String is empty or null, sets it to null.
	 * @param input value
	 */
	public void setNameContains( String input ) {
		if ( input == null || input.isEmpty() ) {
			nameContains = null;
		}
		else {
			nameContains = input;
		}
	}
	
	/**
	 * Sets the "name does not contain" field for this filter, based on a String.
	 * If the String is empty or null, sets it to null.
	 * @param input value
	 */
	public void setNameNotContains( String input ) {
		if ( input == null || input.isEmpty() ) {
			nameNotContains = null;
		}
		else {
			nameNotContains = input;
		}
	}
	
	/**
	 * Sets the "description contains" field for this filter, based on a String.
	 * If the String is empty or null, sets it to null.
	 * @param input value
	 */
	public void setDescriptionContains( String input ) {
		if ( input == null || input.isEmpty() ) {
			descriptionContains = null;
		}
		else {
			descriptionContains = input;
		}
	}
	
	/**
	 * Sets the "description does not contain" field for this filter, based on a String.
	 * If the String is empty or null, sets it to null.
	 * @param input value
	 */
	public void setDescriptionNotContains( String input ) {
		if ( input == null || input.isEmpty() ) {
			descriptionNotContains = null;
		}
		else {
			descriptionNotContains = input;
		}
	}
	
	/**
	 * Sets the "class name contains" field for this filter, based on a String.
	 * If the String is empty or null, sets it to null.
	 * @param input value
	 */
	public void setClassContains( String input ) {
		if ( input == null || input.isEmpty() ) {
			classContains = null;
		}
		else {
			classContains = input;
		}
	}
	
	/**
	 * Sets the "class name does not contain" field for this filter, based on a String.
	 * If the String is empty or null, sets it to null.
	 * @param input value
	 */
	public void setClassNotContains( String input ) {
		if ( input == null || input.isEmpty() ) {
			classNotContains = null;
		}
		else {
			classNotContains = input;
		}
	}
	
	/**
	 * Returns the value of numDaysAhead as a String, or an empty String if it's null
	 * @return the value of numDaysAhead as a String, or an empty String if it's null
	 */
	public String getNumDaysAhead() {
		if ( numDaysAhead == null )
			return "";
		return numDaysAhead.toString();
	}
	
	/**
	 * Returns the value of dateAfter as an EasyDate. Can return null.
	 * @return the value of dateAfter as an EasyDate
	 */
	public EasyDate getDateAfter() {
		return dateAfter;
	}
	
	/**
	 * Returns the value of dateBefore as an EasyDate. Can return null.
	 * @return the value of dateBefore as an EasyDate
	 */
	public EasyDate getDateBefore() {
		return dateBefore;
	}
	
	/**
	 * Returns true if using after/before, or false if using days from today
	 * @return the value of dateSetting
	 */
	public boolean getDateSetting() {
		return dateSetting;
	}
	
	/**
	 * Returns the value of priorityMin as a String, or an empty String if it's null
	 * @return the value of priorityMin as a String, or an empty String if it's null
	 */
	public String getPriorityMin() {
		if ( priorityMin == null )
			return "";
		return priorityMin.toString();
	}
	
	/**
	 * Returns the value of priorityMax as a String, or an empty String if it's null
	 * @return the value of priorityMax as a String, or an empty String if it's null
	 */
	public String getPriorityMax() {
		if ( priorityMax == null )
			return "";
		return priorityMax.toString();
	}
	
	/**
	 * Returns the value of durationMin as a String, or an empty String if it's null
	 * @return the value of durationMin as a String, or an empty String if it's null
	 */
	public String getDurationMin() {
		if ( durationMin == null )
			return "";
		return durationMin.toString();
	}
	
	/**
	 * Returns the value of durationMax as a String, or an empty String if it's null
	 * @return the value of durationMax as a String, or an empty String if it's null
	 */
	public String getDurationMax() {
		if ( durationMax == null )
			return "";
		return durationMax.toString();
	}
	
	/**
	 * Returns the value of nameContains, or an empty String if it's null
	 * @return the value of nameContains as a String, or an empty String if it's null
	 */
	public String getNameContains() {
		if ( nameContains == null )
			return "";
		return nameContains;
	}
	
	/**
	 * Returns the value of nameNotContains, or an empty String if it's null
	 * @return the value of nameNotContains as a String, or an empty String if it's null
	 */
	public String getNameNotContains() {
		if ( nameNotContains == null )
			return "";
		return nameNotContains;
	}
	
	/**
	 * Returns the value of descriptionContains, or an empty String if it's null
	 * @return the value of descriptionContains as a String, or an empty String if it's null
	 */
	public String getDescriptionContains() {
		if ( descriptionContains == null )
			return "";
		return descriptionContains;
	}
	
	/**
	 * Returns the value of descriptionNotContains, or an empty String if it's null
	 * @return the value of descriptionNotContains as a String, or an empty String if it's null
	 */
	public String getDescriptionNotContains() {
		if ( descriptionNotContains == null )
			return "";
		return descriptionNotContains;
	}
	
	/**
	 * Returns the value of classContains, or an empty String if it's null
	 * @return the value of classContains as a String, or an empty String if it's null
	 */
	public String getClassContains() {
		if ( classContains == null )
			return "";
		return classContains;
	}
	
	/**
	 * Returns the value of classNotContains, or an empty String if it's null
	 * @return the value of classNotContains as a String, or an empty String if it's null
	 */
	public String getClassNotContains() {
		if ( classNotContains == null )
			return "";
		return classNotContains;
	}
}
