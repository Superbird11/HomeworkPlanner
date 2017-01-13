package edu.ldj.planner.io;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;

import edu.ldj.planner.compare.*;
import edu.ldj.planner.list.TaskList;
import edu.ldj.planner.task.EasyDate;
import edu.ldj.planner.task.Task;

/**
 * A manager for a GUI for the task planner program.
 * @author Louis Jacobowitz
 */
public class TaskPlannerGUI extends JFrame implements ActionListener {
	/** The fundamental list of tasks */
	private TaskList taskList;
	/** The name of this application */
	public static final String APP_NAME = "Task Planner";
	/** The GUI element of the task list */
	private JList<String> taskListList;
	/** A series of self-explanatory buttons */
	private JButton addTaskButton;
	private JButton editTaskButton;
	private JButton removeTaskButton;
	/** A set of radio buttons for sorting options */
	private JRadioButton sortPriorityButton;
	private JRadioButton sortDueDateButton;
	private JRadioButton sortDurationButton;
	private JRadioButton sortClassButton;
	private static final String[] SORT_OPTIONS_LIST = { "Priority", "Due Date", "Duration", "Class" };
	/** The file to save to */
	private String saveFileName;

	public TaskPlannerGUI() {
		taskList = new TaskList();
		saveFileName = "planner.pxt";
		// First, make the x button actually close the program
		addWindowListener(new WindowAdapter () {
			public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
		});
		// Initialize the window
		Container c = getContentPane();
		setTitle( APP_NAME );
		setSize( 900, 250 );
		setResizable( true );
		// Set a layout for the window
		c.setLayout( new BoxLayout( c, BoxLayout.LINE_AXIS ) );
		// Populate the window
		// First, create the panel where we'll put our list of tasks
		JPanel taskListPanel = new JPanel(); 
		taskListPanel.setBorder( BorderFactory.createTitledBorder( "Task List" ) );
		taskListPanel.setLayout( new BoxLayout( taskListPanel, BoxLayout.PAGE_AXIS ) );
		taskListPanel.setName( "Task List" );
		c.add( taskListPanel );
		// Next, create said list of tasks
		taskListList = new JList<String>();
		taskListList.setSelectionMode( ListSelectionModel.SINGLE_INTERVAL_SELECTION );
		taskListList.setLayoutOrientation( JList.VERTICAL );
		taskListList.setFont( new Font( Font.MONOSPACED, Font.PLAIN, 12 ) );
		taskListList.setVisibleRowCount( -1 );
		// and put it in a scroll pane
		JScrollPane taskListPane = new JScrollPane( taskListList );
		taskListPanel.add( taskListPane );
		// Now, create the left side, a series of buttons that do things.
		JPanel optionsPanel = new JPanel();
		optionsPanel.setBorder( BorderFactory.createTitledBorder( "Options" ) );
		optionsPanel.setLayout( new BoxLayout( optionsPanel, BoxLayout.PAGE_AXIS ) );
		optionsPanel.setName( "Options" );
		c.add( optionsPanel );
		// Create these buttons, and add them to the options panel
		addTaskButton = new JButton( "Add New Task..." );
		addTaskButton.setActionCommand( "AddTask" );
		addTaskButton.addActionListener( this );
		removeTaskButton = new JButton( "Remove Selected Task" );
		removeTaskButton.setActionCommand( "RemoveTask" );
		removeTaskButton.addActionListener( this );
		editTaskButton = new JButton( "Edit Selected Task" );
		editTaskButton.setActionCommand( "EditTask" );
		editTaskButton.addActionListener( this );
		optionsPanel.add( addTaskButton );
		optionsPanel.add( removeTaskButton );
		optionsPanel.add( editTaskButton );
		// Create a set of radio buttons for sorting purposes
		JLabel sortLabel = new JLabel( "Sort tasks by..." );
		optionsPanel.add( sortLabel );
		optionsPanel.add( Box.createRigidArea( new Dimension( 0, 5 ) ) );
		sortPriorityButton = new JRadioButton( SORT_OPTIONS_LIST[ 0 ] );
		sortPriorityButton.setActionCommand( "Sort" );
		sortPriorityButton.addActionListener( this );
		optionsPanel.add( sortPriorityButton );
		sortPriorityButton.setSelected( true );
		sortDueDateButton = new JRadioButton( SORT_OPTIONS_LIST[ 1 ] );
		sortDueDateButton.setActionCommand( "Sort" );
		sortDueDateButton.addActionListener( this );
		optionsPanel.add( sortDueDateButton );
		sortDurationButton = new JRadioButton( SORT_OPTIONS_LIST[ 2 ] );
		sortDurationButton.setActionCommand( "Sort" );
		sortDurationButton.addActionListener( this );
		optionsPanel.add( sortDurationButton );
		sortClassButton = new JRadioButton( SORT_OPTIONS_LIST[ 3 ] );
		sortClassButton.setActionCommand( "Sort" );
		sortClassButton.addActionListener( this );
		optionsPanel.add( sortClassButton );
		ButtonGroup sortGroup = new ButtonGroup();
		sortGroup.add( sortPriorityButton );
		sortGroup.add( sortDueDateButton );
		sortGroup.add( sortDurationButton );
		sortGroup.add( sortClassButton );
		optionsPanel.add( Box.createVerticalGlue() );
		
		// Do this, for our pop-up to work
		UIManager.put( "OptionPane.minimumSize", new Dimension( 500, 300 ) );
		
		sortTaskList();
		
		setVisible( true );
	}
	
	public TaskPlannerGUI( String filename ) {
		this();
		try {
			taskList = new TaskList( filename );
		} catch ( FileNotFoundException e ) {
			JOptionPane.showMessageDialog( null, "Error: Requested file \"" + filename + "\" not found. \nProceeding with default output filename, \"planner.pxt\"." );
		} catch (InvalidFileFormatException e) {
			JOptionPane.showMessageDialog( null, "Error: Requested file \"" + filename + "\" was in the incorrect format:\n" + e.getMessage() + "\nProceeding with default output filename, \"planner.pxt\"." );
		}
		saveFileName = filename;
		sortTaskList();
	}
	
	/**
	 * Instructs the Task List to sort itself, 
	 * based on the currently-selected ordering system
	 */
	public void sortTaskList() {
		if ( sortPriorityButton.isSelected() ) {
			ComparePriority< Task > nn = new ComparePriority< Task >();
			taskList.sortTasks( nn );
		}
		else if ( sortDueDateButton.isSelected() )
			taskList.sortTasks( new CompareDueDate< Task >() );
		else if ( sortDurationButton.isSelected() )
			taskList.sortTasks( new CompareTime< Task >() );
		else if ( sortClassButton.isSelected() )
			taskList.sortTasks( new CompareClass< Task >() );
		taskListList.setListData( taskList.toStringArray() );
	}
	
	/**
	 * A pop-up window for editing tasks
	 * @author Louis Jacobowitz
	 */
	private class EditTaskPanel extends JPanel {
		/** Serial Version ID */
		private static final long serialVersionUID = 1L;
		/** A set of fields for inputting information about the task being updated */
		private JTextField priorityField;
		private JTextField nameField;
		private JTextArea descriptionField;
		private JTextField classField;
		private JTextField durationField;
		private JTextField dateDayField;
		private JTextField dateMonthField;
		private JTextField dateYearField;
		/** The Task being edited */
		public Task editedTask;
		
		/**
		 * Constructs a pop-up window for entering a new task
		 */
		public EditTaskPanel() {
			super();
			editedTask = new Task();
			initialize();
		}
		
		/**
		 * Constructs this pop-up for editing an existing command.
		 * @param t - task to edit
		 */
		public EditTaskPanel( Task t ) {
			super();
			editedTask = t;
			initialize();
		}
		
		/**
		 * Handles initialization for all components besides the task.
		 */
		private void initialize() {
			setBorder( BorderFactory.createBevelBorder( BevelBorder.LOWERED ) );
			setLayout( new BoxLayout( this, BoxLayout.PAGE_AXIS ) );
			setName( "Edit Task Information" );
			// Organize text fields.
			JPanel textPanel = new JPanel();
			textPanel.setBorder( BorderFactory.createEmptyBorder() );
			textPanel.setLayout( new BoxLayout( textPanel, BoxLayout.LINE_AXIS ) );
			this.add( textPanel );
			// First, name
			JPanel namePanel = new JPanel();
			namePanel.setBorder( BorderFactory.createTitledBorder( "Task Name:" ) );
			namePanel.setLayout( new BoxLayout( namePanel, BoxLayout.LINE_AXIS ) );
			nameField = new JTextField( editedTask.getTaskName() );
			nameField.setMaximumSize( new Dimension( 140, 30 ) );
			namePanel.add( nameField );
			textPanel.add( namePanel );
			// Then, next to it, class
			JPanel classPanel = new JPanel();
			classPanel.setBorder( BorderFactory.createTitledBorder( "Class Name:" ) );
			classPanel.setLayout( new BoxLayout( classPanel, BoxLayout.LINE_AXIS ) );
			classField = new JTextField( editedTask.getTaskClass() );
			classField.setMaximumSize( new Dimension( 140, 30 ) );
			classPanel.add( classField );
			textPanel.add( classPanel );
			// Then, description
			JPanel descriptionPanel = new JPanel();
			descriptionPanel.setBorder( BorderFactory.createTitledBorder( "Description / Details:" ) );
			descriptionPanel.setLayout( new BoxLayout( descriptionPanel, BoxLayout.PAGE_AXIS ) );
			descriptionField = new JTextArea( editedTask.getTaskDetails() );
			descriptionField.setMaximumSize( new Dimension( 300, 150 ) );
			descriptionField.setLineWrap( true );
			descriptionField.setEditable( true );
			descriptionPanel.add( descriptionField );
			this.add( descriptionPanel );
			// Then, priority, Duration, and Date
			JPanel numbersSubPanel = new JPanel();
			numbersSubPanel.setBorder( BorderFactory.createEmptyBorder() );
			numbersSubPanel.setLayout( new BoxLayout( numbersSubPanel, BoxLayout.LINE_AXIS ) );
			
			JPanel prioritySubPanel = new JPanel();
			prioritySubPanel.setLayout( new BoxLayout( prioritySubPanel, BoxLayout.LINE_AXIS ) );
			prioritySubPanel.setBorder( BorderFactory.createTitledBorder( "Priority" ) );
			priorityField = new JTextField( String.valueOf( editedTask.getPriority() ) );
			priorityField.setMaximumSize( new Dimension( 100, 30 ) );
			prioritySubPanel.add( priorityField );
			numbersSubPanel.add( prioritySubPanel );
			
			JPanel durationSubPanel = new JPanel();
			durationSubPanel.setLayout( new BoxLayout( durationSubPanel, BoxLayout.LINE_AXIS ) );
			durationSubPanel.setBorder( BorderFactory.createTitledBorder( "Duration (hrs)" ) );
			durationField = new JTextField( String.valueOf( editedTask.getTimeEstimate() ) );
			durationField.setMaximumSize( new Dimension( 100, 30 ) );
			durationSubPanel.add( durationField );
			numbersSubPanel.add( durationSubPanel );
			
			JPanel dateSubPanel = new JPanel();
			dateSubPanel.setLayout( new BoxLayout( dateSubPanel, BoxLayout.LINE_AXIS ) );
			dateSubPanel.setBorder( BorderFactory.createTitledBorder( "Date (d/m/y)" ) );
			dateDayField = new JTextField( String.valueOf( editedTask.getDueDate().getDay() ) );
			dateDayField.setMaximumSize( new Dimension( 30, 30 ) );
			dateMonthField = new JTextField( String.valueOf( editedTask.getDueDate().getMonth() ) );
			dateMonthField.setMaximumSize( new Dimension( 30, 30 ) );
			dateYearField = new JTextField( String.valueOf( editedTask.getDueDate().getYear() ) );
			dateYearField.setMaximumSize( new Dimension( 60, 30 ) );
			dateSubPanel.add( dateDayField );
			dateSubPanel.add( dateMonthField );
			dateSubPanel.add( dateYearField );
			numbersSubPanel.add( dateSubPanel );
			this.add( numbersSubPanel );
		}
		
		/**
		 * Creates and returns a task from the given information.
		 * If invalid information is submitted, triggers a pop-up box and 
		 * returns null.
		 * @return a task compiled from the current entries
		 */
		public Task compileTask() {
			int newPriority;
			try {
				newPriority = Integer.parseInt( priorityField.getText() );
			} catch ( NumberFormatException n ) {
				JOptionPane.showMessageDialog( null, "Error: Priority must be an integer" );
				return null;
			}
			double newDuration;
			try {
				newDuration = Double.parseDouble( durationField.getText() );
				if ( newDuration < 0 )
					throw new NumberFormatException();
			} catch ( NumberFormatException n ) {
				JOptionPane.showMessageDialog( null, "Error: Duration must be a real positive number");
				return null;
			}
			int newDay, newMonth, newYear;
			EasyDate newDate;
			try {
				newDay = Integer.parseInt( dateDayField.getText() );
				newMonth = Integer.parseInt( dateMonthField.getText() );
				newYear = Integer.parseInt( dateYearField.getText() );
				newDate = new EasyDate( newYear, newMonth, newDay );
			} catch ( NumberFormatException n ) {
				JOptionPane.showMessageDialog( null, "Error: Date must consist of integers" );
				return null;
			} catch ( IllegalArgumentException e ) {
				JOptionPane.showMessageDialog( null, e.getMessage() );
				return null;
			}
			return new Task( newPriority, newDate, newDuration, nameField.getText(), descriptionField.getText(), classField.getText() );
		}
	}
	
	/**
	 * Allows this GUI to act as an action listener, and
	 * handle button presses
	 * @param e - ActionEvent that triggered this action
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		switch ( e.getActionCommand() ) {
		case "Sort":
			sortTaskList();
			break;
		case "RemoveTask":
			int idx = taskListList.getSelectedIndex() - 2; 
			if ( idx < 0 ) {
				JOptionPane.showMessageDialog( null, "Select a task first before removing it" );
				return;
			}
			// because we have that explanatory row up in front
			taskList.getTasks().remove( idx );
			// We need to update the list by sorting it.
			sortTaskList();
			try {
				taskList.writeToFile( saveFileName );
			} catch ( FileNotFoundException f ) {
				JOptionPane.showMessageDialog( null, "Error: Could not save planner" );
			}
			break;
		case "AddTask":
			EditTaskPanel newPanel = new EditTaskPanel();
			Task newTask = null;
			int addResult = JOptionPane.OK_OPTION;
			while ( newTask == null && addResult == JOptionPane.OK_OPTION ) {
				addResult = JOptionPane.showConfirmDialog(this, newPanel, "Add New Task", JOptionPane.OK_CANCEL_OPTION);
				if ( addResult == JOptionPane.OK_OPTION ) {
					newTask = newPanel.compileTask();
				}
			}
			if ( newTask != null ) 
				taskList.getTasks().add( newTask );
			sortTaskList();
			try {
				taskList.writeToFile( saveFileName );
			} catch ( FileNotFoundException f ) {
				JOptionPane.showMessageDialog( null, "Error: Could not save planner" );
			}
			break;
		case "EditTask":
			int selectedIndex = taskListList.getSelectedIndex() - 2; 
			if ( selectedIndex < 0 ) {
				JOptionPane.showMessageDialog( null, "Select a task first before editing it" );
				return;
			}
			Task editedTask = taskList.getTasks().get( selectedIndex );
			
			EditTaskPanel editPanel = new EditTaskPanel( editedTask );
			Task finalTask = null;
			int adResult = JOptionPane.OK_OPTION;
			while ( finalTask == null && adResult == JOptionPane.OK_OPTION ) {
				adResult = JOptionPane.showConfirmDialog(this, editPanel, "Edit Task", JOptionPane.OK_CANCEL_OPTION);
				if ( adResult == JOptionPane.OK_OPTION ) {
					finalTask = editPanel.compileTask();
				}
			}
			if ( finalTask != null ) {
				taskList.getTasks().remove( selectedIndex );
				taskList.getTasks().add( finalTask );
			}
			sortTaskList();
			try {
				taskList.writeToFile( saveFileName );
			} catch ( FileNotFoundException f ) {
				JOptionPane.showMessageDialog( null, "Error: Could not save planner" );
			}
			break;
		default:
			break;
		}
		
	}
	
	/**
	 * Executes the Task Planner program, creating a GUI and starting the program.
	 * @param args - Optionally provides a single parameter, a file from which to read in.
	 */
	public static void main(String[] args) {
		TaskPlannerGUI app;
		if ( args.length == 0 )
			app = new TaskPlannerGUI();
		else
			app = new TaskPlannerGUI( args[ 0 ] );
	}

}
