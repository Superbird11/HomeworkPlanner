package edu.ldj.planner.list;

import edu.ldj.planner.compare.Filter;
import edu.ldj.planner.io.InvalidFileFormatException;
import edu.ldj.planner.task.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.Scanner;


/**
 * A class for handling a collection of tasks.
 * @author Louis Jacobowitz
 */
public class TaskList {
	/** The list of tasks to be performed */
	ArrayList< Task > tasks;
	
	/** 
	 * Constructs a new, empty task list
	 */
	public TaskList() {
		tasks = new ArrayList< Task >();
	}
	
	/**
	 * Constructs a new TaskList from a file that contains tasks.
	 * @throws FileNotFoundException
	 * @throws InvalidFileFormatException 
	 */
	public TaskList( String filename ) throws FileNotFoundException, InvalidFileFormatException {
		this();
		int lineNo = 0;
		try ( Scanner scanner = new Scanner( new File( filename ) ) ) {
			while( scanner.hasNextLine() ) {
				String nextLine = scanner.nextLine();
				try( Scanner lineScanner = new Scanner( nextLine ) ) {
					int priority = lineScanner.nextInt();
					int year = lineScanner.nextInt();
					int month = lineScanner.nextInt();
					int day = lineScanner.nextInt();
					int nameLength = lineScanner.nextInt();
					String name = "";
					for( int i = 0; i < nameLength; i++ )
						name = name.concat( " " ).concat( lineScanner.next() );
					name = name.trim();
					int detailsLength = lineScanner.nextInt();
					String details = "";
					for( int i = 0; i < detailsLength; i++ )
						details = details.concat( " " ).concat( lineScanner.next() );
					details = details.trim();
					double timeEstimate = lineScanner.nextDouble();
					int classLength = lineScanner.nextInt();
					String className = "";
					for( int i = 0; i < classLength; i++ )
						className = className.concat( " " ).concat( lineScanner.next() );
					className = className.trim();
					// that's all inputs from this line
					EasyDate date = new EasyDate( year, month, day );
					Task newTask = new Task( priority, date, timeEstimate, name, details, className );
					tasks.add( newTask );
					
				} catch ( NoSuchElementException e ) {
					throw new InvalidFileFormatException( "Invalid token sequence on line #" + lineNo );
				} catch ( IllegalArgumentException e ) {
					throw new IllegalArgumentException( "Invalid date on line #" + lineNo + ":\n\t" + e.getMessage() );
				}
				lineNo++;
			}
		} catch ( FileNotFoundException e ) {
			throw e;
		}
	}
	
	/**
	 * Writes the current TaskList to the given filename
	 * @param filename to write to
	 * @throws FileNotFoundException if the file is not found
	 */
	public void writeToFile( String filename ) throws FileNotFoundException {
		try ( PrintWriter writer = new PrintWriter( filename, "UTF-8" ) ) {
			for( Task t : tasks )
				writer.print( t.toFile() );
		} catch ( UnsupportedEncodingException e ) {
			// this won't happen
			e.printStackTrace();
		} catch ( FileNotFoundException e ) {
			// This is here to catch and close the writer, then escalate the exception to the client
			throw e;
		}
		
	}
	
	/**
	 * Sorts this TaskList's array by the given comparator
	 * @param c - comparator to use (metric by which to sort)
	 */
	public void sortTasks( Comparator< Task > c ) {
		tasks.sort( c );
	}
	
	/**
	 * Returns this TaskList's array of tasks, for modification elsewhere.
	 * @return this TaskList's array of tasks, by reference
	 */
	public ArrayList< Task > getTasks() {
		return tasks;
	}
	
	/**
	 * Removes the task at a certain index, after the filter has been applied
	 * @param filter to apply
	 * @param idx to remove after applying the filter
	 */
	public void removeFiltered( Filter filter, int idx ) {
		int ct = 0, i = 0;
		for ( i = 0; i < tasks.size() && ct < idx; i++ ) {
			if ( filter.meetsFilter( tasks.get( i ) ) )
				ct++;
		}
		tasks.remove( i );
	}
	
	/**
	 * Returns the task at a certain index, after the filter has been applied
	 * @param filter to apply
	 * @param idx to get after applying the filter
	 * @return the requested task from the list
	 */
	public Task getFiltered( Filter filter, int idx ) {
		int ct = -1, i = 0;
		for ( i = 0; i < tasks.size() && ct < idx; i++ ) {
			if ( filter.meetsFilter( tasks.get( i ) ) ) {
				ct++;
			}
			if ( ct == idx )
				break;
		}
		return tasks.get( i );
	}
	
	/**
	 * Returns the String representations of every task in the task list
	 * @return all Tasks's string representations concatenated together
	 */
	public String toString() {
		String ret = "";
		for( Task t : tasks ) {
			ret = ret.concat( t.toString() + "\n" );
		}
		return ret;
	}
	
	/**
	 * Returns this TaskList as an array of Strings, instead of as a single String
	 * @param filter that all returned Strings must fit
	 * @return an array of all Tasks individually outputted as Strings
	 */
	public String[] toStringArray( Filter filter ) {
		ArrayList< String > ret = new ArrayList< String >();
		ret.add( String.format( "%8s | %11s | %12s | %30s | %5s | %12s", "Priority", "Due Date", "Name", "Description", "Time", "Class" ) );
		ret.add( "---------+-------------+--------------+--------------------------------+-------+-------------" );
		for ( Task t : tasks ) {
			if ( filter.meetsFilter( t ) )
				ret.add( t.toString() );
		}
		return ret.toArray( new String[ ret.size() ] );
	}
}
