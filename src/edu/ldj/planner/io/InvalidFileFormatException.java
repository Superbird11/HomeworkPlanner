package edu.ldj.planner.io;

import java.io.IOException;

/**
 * A class for representing exceptions wherein the given file is in the wrong format, and thus unreadable.
 * @author Louis Jacobowitz
 */
public class InvalidFileFormatException extends IOException {

	/**
	 * a serial version ID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * A default constructor
	 */
	public InvalidFileFormatException() {
		super();
	}
	
	/** 
	 * A constructor when being initialized with a String 
	 * @param s - message
	 */
	public InvalidFileFormatException( String s ) {
		super( s );
	}
}
