package edu.ldj.planner.task;

import java.util.Calendar;

/**
 * A simple format for storing a date
 * @author Louis Jacobowitz
 */
public class EasyDate {
	/** This date's year */
	private int year;
	/** This date's month */
	private int month;
	/** This date's day */
	private int day;
	
	/**
	 * Constructs an EasyDate based on today's date.
	 */
	public EasyDate() {
		Calendar today = Calendar.getInstance();
		this.year = today.get( Calendar.YEAR );
		this.month = today.get( Calendar.MONTH ) + 1; // is 0-indexed, so since our months are 1-indexed we need to add 1
		this.day = today.get( Calendar.DAY_OF_MONTH );
	}
	
	/**
	 * Constructs an easy date using the given year, month, and day
	 * @param year
	 * @param month
	 * @param day
	 */
	public EasyDate( int year, int month, int day ) {
		super();
		setYear( year );
		setMonth( month );
		setDay( day );
	}

	/**
	 * Returns the year
	 * @return year
	 */
	public int getYear() {
		return year;
	}
	
	/**
	 * Sets the year
	 * @param year to set
	 */
	public void setYear( int year ) {
		this.year = year;
	}
	
	/**
	 * Returns the month
	 * @return month
	 */
	public int getMonth() {
		return month;
	}
	
	/**
	 * Sets the month
	 * @param month to set
	 */
	public void setMonth( int month ) {
		if( month < 1 || month > 12 )
			throw new IllegalArgumentException( "Months range from 1 to 12" );
		this.month = month;
	}
	
	/**
	 * Returns the number of days in this month
	 * @return the number of days in this month
	 */
	public int getMonthDays() {
		switch( month ) {
		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12:
			return 31;
		case 4:
		case 6:
		case 9:
		case 11:
			return 30;
		case 2:
			if ( year % 4 == 0 )
				return 29;
			return 28;
		default:
			return 31;
		}
	}
	
	/**
	 * Returns the day
	 * @return day
	 */
	public int getDay() {
		return day;
	}
	
	/**
	 * Sets the day
	 * @param day to set
	 */
	public void setDay( int day ) {
		if( day < 1 || day > getMonthDays() )
			throw new IllegalArgumentException( "There are only so many days in a month" );
		this.day = day;
	}
	
	/**
	 * Adds the specified number of days to this EasyDate, changing it accordingly.
	 * @param num - number of days to add
	 */
	public void addDays( int num ) {
		for( int i = 0; i < num; i++ ) {
			day++;
			if ( day > getMonthDays() ) {
				day = 0;
				month++;
			}
			if ( month > 12 ) {
				month = 1;
				year++;
			}
		}
	}
	
	/**
	 * Returns the date as a String in the format:
	 * 		Month/Day/Year
	 */
	public String toString() {
		return "" + month + "/" + day + "/" + year;
	}
	
}
