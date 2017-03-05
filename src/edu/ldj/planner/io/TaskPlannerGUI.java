package edu.ldj.planner.io;

import java.awt.Component;
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
import javax.swing.JFileChooser;
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
import edu.ldj.planner.compare.*;
import edu.ldj.planner.list.TaskList;
import edu.ldj.planner.task.EasyDate;
import edu.ldj.planner.task.Task;

/**
 * A manager for a GUI for the task planner program.
 * @author Louis Jacobowitz
 */
public class TaskPlannerGUI extends JFrame implements ActionListener {
	/** Serial Version ID (auto-generated) */
	private static final long serialVersionUID = 1L;
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
	private JRadioButton sortNameButton;
	private JRadioButton sortDescriptionButton;
	private JRadioButton sortDurationButton;
	private JRadioButton sortClassButton;
	private JRadioButton sortReversePriorityButton;
	private JRadioButton sortReverseDueDateButton;
	private JRadioButton sortReverseNameButton;
	private JRadioButton sortReverseDescriptionButton;
	private JRadioButton sortReverseDurationButton;
	private JRadioButton sortReverseClassButton;
	private static final String[] SORT_OPTIONS_LIST = { "Priority", "Due Date", "Name", "Description", "Time", "Class" };
	/** A filter for filtering our tasks */
	private Filter filter;
	/** A button for allowing the user to change the filter */
	private JButton filterButton;
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
		setSize( 900, 400 );
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
		JPanel sortsPanel = new JPanel();
		sortsPanel.setBorder( BorderFactory.createTitledBorder( "Sort tasks by..." ) );
		sortsPanel.setLayout( new BoxLayout( sortsPanel, BoxLayout.PAGE_AXIS ) );
		taskListPanel.setName( "Sort Tasks" );
		optionsPanel.add( sortsPanel );
		//JLabel sortLabel = new JLabel( "Sort tasks by..." );
		//optionsPanel.add( sortLabel );
		sortsPanel.add( Box.createRigidArea( new Dimension( 0, 5 ) ) );
		
		JPanel sortPriorityBox = new JPanel();
		sortPriorityBox.setLayout( new BoxLayout( sortPriorityBox, BoxLayout.LINE_AXIS ) );
		sortPriorityBox.setAlignmentX( Component.LEFT_ALIGNMENT );
		sortsPanel.add( sortPriorityBox );
		sortReversePriorityButton = new JRadioButton( "" );
		sortReversePriorityButton.setActionCommand( "Sort" );
		sortReversePriorityButton.addActionListener( this );
		sortPriorityBox.add( sortReversePriorityButton );
		sortPriorityButton = new JRadioButton( SORT_OPTIONS_LIST[ 0 ] );
		sortPriorityButton.setActionCommand( "Sort" );
		sortPriorityButton.addActionListener( this );
		sortPriorityBox.add( sortPriorityButton );
		sortPriorityButton.setSelected( true );
		
		JPanel sortDueDateBox = new JPanel();
		sortDueDateBox.setLayout( new BoxLayout( sortDueDateBox, BoxLayout.LINE_AXIS ) );
		sortDueDateBox.setAlignmentX( Component.LEFT_ALIGNMENT );
		sortsPanel.add( sortDueDateBox );
		sortReverseDueDateButton = new JRadioButton( "" );
		sortReverseDueDateButton.setActionCommand( "Sort" );
		sortReverseDueDateButton.addActionListener( this );
		sortDueDateBox.add( sortReverseDueDateButton );
		sortDueDateButton = new JRadioButton( SORT_OPTIONS_LIST[ 1 ] );
		sortDueDateButton.setActionCommand( "Sort" );
		sortDueDateButton.addActionListener( this );
		sortDueDateBox.add( sortDueDateButton );
		
		JPanel sortNameBox = new JPanel();
		sortNameBox.setLayout( new BoxLayout( sortNameBox, BoxLayout.LINE_AXIS ) );
		sortNameBox.setAlignmentX( Component.LEFT_ALIGNMENT );
		sortsPanel.add( sortNameBox );
		sortReverseNameButton = new JRadioButton( "" );
		sortReverseNameButton.setActionCommand( "Sort" );
		sortReverseNameButton.addActionListener( this );		
		sortNameBox.add( sortReverseNameButton );
		sortNameButton = new JRadioButton( SORT_OPTIONS_LIST[ 2 ] );
		sortNameButton.setActionCommand( "Sort" );
		sortNameButton.addActionListener( this );
		sortNameBox.add( sortNameButton );
		
		JPanel sortDescriptionBox = new JPanel();
		sortDescriptionBox.setLayout( new BoxLayout( sortDescriptionBox, BoxLayout.LINE_AXIS ) );
		sortDescriptionBox.setAlignmentX( Component.LEFT_ALIGNMENT );
		sortsPanel.add( sortDescriptionBox );
		sortReverseDescriptionButton = new JRadioButton( "" );
		sortReverseDescriptionButton.setActionCommand( "Sort" );
		sortReverseDescriptionButton.addActionListener( this );
		sortDescriptionBox.add( sortReverseDescriptionButton );
		sortDescriptionButton = new JRadioButton( SORT_OPTIONS_LIST[ 3 ] );
		sortDescriptionButton.setActionCommand( "Sort" );
		sortDescriptionButton.addActionListener( this );
		sortDescriptionBox.add( sortDescriptionButton );
		
		JPanel sortDurationBox = new JPanel();
		sortDurationBox.setLayout( new BoxLayout( sortDurationBox, BoxLayout.LINE_AXIS ) );
		sortDurationBox.setAlignmentX( Component.LEFT_ALIGNMENT );
		sortsPanel.add( sortDurationBox );
		sortReverseDurationButton = new JRadioButton( "" );
		sortReverseDurationButton.setActionCommand( "Sort" );
		sortReverseDurationButton.addActionListener( this );
		sortDurationBox.add( sortReverseDurationButton );
		sortDurationButton = new JRadioButton( SORT_OPTIONS_LIST[ 4 ] );
		sortDurationButton.setActionCommand( "Sort" );
		sortDurationButton.addActionListener( this );
		sortDurationBox.add( sortDurationButton );
		
		JPanel sortClassBox = new JPanel();
		sortClassBox.setLayout( new BoxLayout( sortClassBox, BoxLayout.LINE_AXIS ) );
		sortClassBox.setAlignmentX( Component.LEFT_ALIGNMENT );
		sortsPanel.add( sortClassBox );
		sortReverseClassButton = new JRadioButton( "" );
		sortReverseClassButton.setActionCommand( "Sort" );
		sortReverseClassButton.addActionListener( this );
		sortClassBox.add( sortReverseClassButton );
		sortClassButton = new JRadioButton( SORT_OPTIONS_LIST[ 5 ] );
		sortClassButton.setActionCommand( "Sort" );
		sortClassButton.addActionListener( this );
		sortClassBox.add( sortClassButton );
		
		ButtonGroup sortGroup = new ButtonGroup();
		sortGroup.add( sortPriorityButton );
		sortGroup.add( sortReversePriorityButton );
		sortGroup.add( sortDueDateButton );
		sortGroup.add( sortReverseDueDateButton );
		sortGroup.add( sortNameButton );
		sortGroup.add( sortReverseNameButton );
		sortGroup.add( sortDescriptionButton );
		sortGroup.add( sortReverseDescriptionButton );
		sortGroup.add( sortDurationButton );
		sortGroup.add( sortReverseDurationButton );
		sortGroup.add( sortClassButton );
		sortGroup.add( sortReverseClassButton );
		
		filter = new Filter();
		filterButton = new JButton( "Filtering Options..." );
		filterButton.setActionCommand( "OpenFilter" );
		filterButton.addActionListener( this );
		optionsPanel.add( filterButton );
		
		optionsPanel.add( Box.createVerticalGlue() );
		
		// Do this, for our pop-up to work
		UIManager.put( "OptionPane.minimumSize", new Dimension( 600, 300 ) );
		
		sortTaskList();
		
		setVisible( true );
	}
	
	public TaskPlannerGUI( String filename ) {
		this();
		try {
			taskList = new TaskList( filename );
		} catch ( FileNotFoundException e ) {
			JOptionPane.showMessageDialog( null, "Error: Requested file \"" + filename + "\" not found. \nProceeding with default output filename, \"planner.pxt\"." );
		} catch ( InvalidFileFormatException e ) {
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
		if ( sortPriorityButton.isSelected() )
			taskList.sortTasks( new ComparePriority< Task > () );
		else if ( sortDueDateButton.isSelected() )
			taskList.sortTasks( new CompareDueDate< Task >() );
		else if ( sortNameButton.isSelected() )
			taskList.sortTasks( new CompareName< Task >() );
		else if ( sortDescriptionButton.isSelected() )
			taskList.sortTasks( new CompareDescription< Task >() );
		else if ( sortDurationButton.isSelected() )
			taskList.sortTasks( new CompareTime< Task >() );
		else if ( sortClassButton.isSelected() )
			taskList.sortTasks( new CompareClass< Task >() );
		else if ( sortReversePriorityButton.isSelected() )
			taskList.sortTasks( new CompareReversePriority< Task > () );
		else if ( sortReverseDueDateButton.isSelected() )
			taskList.sortTasks( new CompareReverseDueDate< Task >() );
		else if ( sortReverseNameButton.isSelected() )
			taskList.sortTasks( new CompareReverseName< Task >() );
		else if ( sortReverseDescriptionButton.isSelected() )
			taskList.sortTasks( new CompareReverseDescription< Task >() );
		else if ( sortReverseDurationButton.isSelected() )
			taskList.sortTasks( new CompareReverseTime< Task >() );
		else if ( sortReverseClassButton.isSelected() )
			taskList.sortTasks( new CompareReverseClass< Task >() );
		taskListList.setListData( taskList.toStringArray( filter ) );
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
		
		// DOING THINGS
		
		/**
		 * Handles initialization for all components besides the task.
		 */
		private void initialize() {
			//setBorder( BorderFactory.createBevelBorder( BevelBorder.LOWERED ) );
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
			priorityField.setMaximumSize( new Dimension( 60, 30 ) );
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
	 * A pop-up window for managing filters
	 * @author Louis Jacobowitz
	 */
	private class EditFiltersPanel extends JPanel {
		/** Serial Version ID */
		private static final long serialVersionUID = 1L;
		/** A set of fields for inputting information about the filter being updated */
		private JTextField priorityGreaterThanField;
		private JTextField priorityLessThanField;
		private JTextField nameContainsField;
		private JTextField nameNotContainsField;
		private JTextField descriptionContainsField;
		private JTextField descriptionNotContainsField;
		private JTextField durationGreaterThanField;
		private JTextField durationLessThanField;
		private JTextField classContainsField;
		private JTextField classNotContainsField;
		private JRadioButton absoluteDateButton;
		private JTextField dateAfterDayField;
		private JTextField dateAfterMonthField;
		private JTextField dateAfterYearField;
		private JTextField dateBeforeDayField;
		private JTextField dateBeforeMonthField;
		private JTextField dateBeforeYearField;
		private JRadioButton relativeDateButton;
		private JTextField dateNumAheadField;
		/** The Filter being edited */
		public Filter editedFilter;
		
		/**
		 * Constructs a pop-up window for entering a new task
		 */
		@SuppressWarnings("unused")
		public EditFiltersPanel() {
			super();
			editedFilter = new Filter();
			initialize();
		}
		
		/**
		 * Constructs this pop-up for editing an existing command.
		 * @param t - task to edit
		 */
		public EditFiltersPanel( Filter f ) {
			super();
			editedFilter = f;
			initialize();
		}
		
		/**
		 * Handles initialization for all components besides the task.
		 */
		private void initialize() {
			//setBorder( BorderFactory.createBevelBorder( BevelBorder.LOWERED ) );
			setLayout( new BoxLayout( this, BoxLayout.PAGE_AXIS ) );
			setName( "Edit Task Filters" );
			// Organize text fields.
			JPanel textPanel = new JPanel();
			textPanel.setBorder( BorderFactory.createEmptyBorder() );
			textPanel.setLayout( new BoxLayout( textPanel, BoxLayout.PAGE_AXIS ) );
			//textPanel.setAlignmentX( Component.LEFT_ALIGNMENT );
			this.add( textPanel );
			
			// Add enclosing panel for number fields, which can go side-by-side
			JPanel numbersPanel = new JPanel();
			numbersPanel.setLayout( new BoxLayout( numbersPanel, BoxLayout.LINE_AXIS ) );
			numbersPanel.setAlignmentX( Component.LEFT_ALIGNMENT );
			textPanel.add( numbersPanel );
			
			// Priority filter
			JPanel priorityPanel = new JPanel();
			priorityPanel.setBorder( BorderFactory.createTitledBorder( "Priority" ) );
			priorityPanel.setLayout( new BoxLayout( priorityPanel, BoxLayout.LINE_AXIS ) );
			priorityPanel.setAlignmentX( Component.LEFT_ALIGNMENT );
			numbersPanel.add( priorityPanel );
			JLabel priorityGreaterThanLabel = new JLabel( "At least:" );
			priorityGreaterThanField = new JTextField( editedFilter.getPriorityMin() );
			priorityGreaterThanField.setMaximumSize( new Dimension( 30, 30 ) );
			JLabel priorityLessThanLabel = new JLabel( "  At most:" );
			priorityLessThanField = new JTextField( editedFilter.getPriorityMax() );
			priorityLessThanField.setMaximumSize( new Dimension( 30, 30 ) );
			priorityPanel.add( priorityGreaterThanLabel );
			priorityPanel.add( priorityGreaterThanField );
			priorityPanel.add( priorityLessThanLabel );
			priorityPanel.add( priorityLessThanField );
			
			// Duration filter
			JPanel durationPanel = new JPanel();
			durationPanel.setBorder( BorderFactory.createTitledBorder( "Duration" ) );
			durationPanel.setLayout( new BoxLayout( durationPanel, BoxLayout.LINE_AXIS ) );
			durationPanel.setAlignmentX( Component.LEFT_ALIGNMENT );
			numbersPanel.add( durationPanel );
			JLabel durationGreaterThanLabel = new JLabel( "At least:" );
			durationGreaterThanField = new JTextField( editedFilter.getDurationMin() );
			durationGreaterThanField.setMaximumSize( new Dimension( 60, 30 ) );
			JLabel durationLessThanLabel = new JLabel( "  At most:" );
			durationLessThanField = new JTextField( editedFilter.getDurationMax() );
			durationLessThanField.setMaximumSize( new Dimension( 60, 30 ) );
			durationPanel.add( durationGreaterThanLabel );
			durationPanel.add( durationGreaterThanField );
			durationPanel.add( durationLessThanLabel );
			durationPanel.add( durationLessThanField );
			
			// Date Filter
			JPanel datePanel = new JPanel();
			datePanel.setBorder( BorderFactory.createTitledBorder( "Due Date (day/month/year)" ) );
			datePanel.setLayout( new BoxLayout( datePanel, BoxLayout.PAGE_AXIS ) );
			datePanel.setAlignmentX( Component.LEFT_ALIGNMENT );
			textPanel.add( datePanel );
			JPanel absoluteDatePanel = new JPanel();
			absoluteDatePanel.setLayout( new BoxLayout( absoluteDatePanel, BoxLayout.LINE_AXIS ) );
			absoluteDatePanel.setAlignmentX( Component.LEFT_ALIGNMENT );
			datePanel.add( absoluteDatePanel );
			absoluteDateButton = new JRadioButton( "" );
			JLabel afterLabel = new JLabel( "  Due after: " );
			EasyDate afterDate = editedFilter.getDateAfter();
			dateAfterDayField = new JTextField( afterDate == null ? "" : String.valueOf( afterDate.getDay() ) );
			dateAfterDayField.setMaximumSize( new Dimension( 30, 30 ) );
			dateAfterMonthField = new JTextField( afterDate == null ? "" : String.valueOf( afterDate.getMonth() ) );
			dateAfterMonthField.setMaximumSize( new Dimension( 30, 30 ) );
			dateAfterYearField = new JTextField( afterDate == null ? "" : String.valueOf( afterDate.getYear() ) );
			dateAfterYearField.setMaximumSize( new Dimension( 60, 30 ) );
			JLabel beforeLabel = new JLabel( "  Due before: " );
			EasyDate beforeDate = editedFilter.getDateBefore();
			dateBeforeDayField = new JTextField( beforeDate == null ? "" : String.valueOf( beforeDate.getDay() ) );
			dateBeforeDayField.setMaximumSize( new Dimension( 30, 30 ) );
			dateBeforeMonthField = new JTextField( beforeDate == null ? "" : String.valueOf( beforeDate.getMonth() ) );
			dateBeforeMonthField.setMaximumSize( new Dimension( 30, 30 ) );
			dateBeforeYearField = new JTextField( beforeDate == null ? "" : String.valueOf( beforeDate.getYear() ) );
			dateBeforeYearField.setMaximumSize( new Dimension( 60, 30 ) );
			absoluteDatePanel.add( absoluteDateButton );
			absoluteDatePanel.add( afterLabel );
			absoluteDatePanel.add( dateAfterDayField );
			absoluteDatePanel.add( dateAfterMonthField );
			absoluteDatePanel.add( dateAfterYearField );
			absoluteDatePanel.add( beforeLabel );
			absoluteDatePanel.add( dateBeforeDayField );
			absoluteDatePanel.add( dateBeforeMonthField );
			absoluteDatePanel.add( dateBeforeYearField );
			JPanel relativeDatePanel = new JPanel();
			relativeDatePanel.setLayout( new BoxLayout( relativeDatePanel, BoxLayout.LINE_AXIS ) );
			relativeDatePanel.setAlignmentX( Component.LEFT_ALIGNMENT );
			datePanel.add( relativeDatePanel );
			relativeDateButton = new JRadioButton();
			JLabel relativeFirstLabel = new JLabel( "  Due " );
			dateNumAheadField = new JTextField( editedFilter.getNumDaysAhead() );
			dateNumAheadField.setMaximumSize( new Dimension( 30, 30 ) );
			JLabel relativeSecondLabel = new JLabel( " days from the present" );
			relativeDatePanel.add( relativeDateButton );
			relativeDatePanel.add( relativeFirstLabel );
			relativeDatePanel.add( dateNumAheadField );
			relativeDatePanel.add( relativeSecondLabel );
			ButtonGroup dateButtonGroup = new ButtonGroup();
			dateButtonGroup.add( absoluteDateButton );
			dateButtonGroup.add( relativeDateButton );
			if ( editedFilter.getDateSetting() )
				absoluteDateButton.setSelected( true );
			else
				relativeDateButton.setSelected( true );
			
			// Name filter
			JPanel namePanel = new JPanel();
			namePanel.setBorder( BorderFactory.createTitledBorder( "Name" ) );
			namePanel.setLayout( new BoxLayout( namePanel, BoxLayout.LINE_AXIS ) );
			namePanel.setAlignmentX( Component.LEFT_ALIGNMENT );
			textPanel.add( namePanel );
			JLabel nameContainsLabel = new JLabel( "Contains:" );
			nameContainsField = new JTextField( editedFilter.getNameContains() );
			nameContainsField.setMaximumSize( new Dimension( 140, 30 ) );
			JLabel nameNotContainsLabel = new JLabel( "  Does not contain:" );
			nameNotContainsField = new JTextField( editedFilter.getNameNotContains() );
			nameNotContainsField.setMaximumSize( new Dimension( 140, 30 ) );
			namePanel.add( nameContainsLabel );
			namePanel.add( nameContainsField );
			namePanel.add( nameNotContainsLabel );
			namePanel.add( nameNotContainsField );
			
			// Description filter
			JPanel descriptionPanel = new JPanel();
			descriptionPanel.setBorder( BorderFactory.createTitledBorder( "Description" ) );
			descriptionPanel.setLayout( new BoxLayout( descriptionPanel, BoxLayout.LINE_AXIS ) );
			descriptionPanel.setAlignmentX( Component.LEFT_ALIGNMENT );
			textPanel.add( descriptionPanel );
			JLabel descriptionContainsLabel = new JLabel( "Contains:" );
			descriptionContainsField = new JTextField( editedFilter.getDescriptionContains() );
			descriptionContainsField.setMaximumSize( new Dimension( 140, 30 ) );
			JLabel descriptionNotContainsLabel = new JLabel( "  Does not contain:" );
			descriptionNotContainsField = new JTextField( editedFilter.getDescriptionNotContains() );
			descriptionNotContainsField.setMaximumSize( new Dimension( 140, 30 ) );
			descriptionPanel.add( descriptionContainsLabel );
			descriptionPanel.add( descriptionContainsField );
			descriptionPanel.add( descriptionNotContainsLabel );
			descriptionPanel.add( descriptionNotContainsField );
			
			// class filter
			JPanel classPanel = new JPanel();
			classPanel.setBorder( BorderFactory.createTitledBorder( "Class" ) );
			classPanel.setLayout( new BoxLayout( classPanel, BoxLayout.LINE_AXIS ) );
			classPanel.setAlignmentX( Component.LEFT_ALIGNMENT );
			textPanel.add( classPanel );
			JLabel classContainsLabel = new JLabel( "Contains:" );
			classContainsField = new JTextField( editedFilter.getClassContains() );
			classContainsField.setMaximumSize( new Dimension( 140, 30 ) );
			JLabel classNotContainsLabel = new JLabel( "  Does not contain:" );
			classNotContainsField = new JTextField( editedFilter.getClassNotContains() );
			classNotContainsField.setMaximumSize( new Dimension( 140, 30 ) );
			classPanel.add( classContainsLabel );
			classPanel.add( classContainsField );
			classPanel.add( classNotContainsLabel );
			classPanel.add( classNotContainsField );
		}
		
		/**
		 * Creates and returns a task from the given information.
		 * If invalid information is submitted, triggers a pop-up box and 
		 * returns null.
		 * @return a task compiled from the current entries
		 */
		public Filter compileFilter() {
			editedFilter.setPriorityMin( priorityGreaterThanField.getText() );
			editedFilter.setPriorityMax( priorityLessThanField.getText() );
			editedFilter.setNameContains( nameContainsField.getText() );
			editedFilter.setNameNotContains( nameNotContainsField.getText() );
			editedFilter.setDescriptionContains( descriptionContainsField.getText() );
			editedFilter.setDescriptionNotContains( descriptionNotContainsField.getText() );
			editedFilter.setDurationMin( durationGreaterThanField.getText() );
			editedFilter.setDurationMax( durationLessThanField.getText() );
			editedFilter.setClassContains( classContainsField.getText() );
			editedFilter.setClassNotContains( classNotContainsField.getText() );
			
			if ( absoluteDateButton.isSelected() ) {
				editedFilter.setDateSetting( true );
				int newDay, newMonth, newYear;
				EasyDate newDate;
				try {
					newDay = Integer.parseInt( dateAfterDayField.getText() );
					newMonth = Integer.parseInt( dateAfterMonthField.getText() );
					newYear = Integer.parseInt( dateAfterYearField.getText() );
					newDate = new EasyDate( newYear, newMonth, newDay );
					editedFilter.setDateAfter( newDate );
					
				} catch ( RuntimeException e ) {
					if ( dateAfterDayField.getText().isEmpty() && dateAfterMonthField.getText().isEmpty() && dateAfterYearField.getText().isEmpty() )
						editedFilter.setDateAfter( null );
				}
				
				try {
					newDay = Integer.parseInt( dateBeforeDayField.getText() );
					newMonth = Integer.parseInt( dateBeforeMonthField.getText() );
					newYear = Integer.parseInt( dateBeforeYearField.getText() );
					newDate = new EasyDate( newYear, newMonth, newDay );
					editedFilter.setDateBefore( newDate );
					
				} catch ( RuntimeException e ) {
					if ( dateAfterDayField.getText().isEmpty() && dateAfterMonthField.getText().isEmpty() && dateAfterYearField.getText().isEmpty() )
						editedFilter.setDateBefore( null );
				}
				
			}
			else if ( relativeDateButton.isSelected() ) {
				editedFilter.setDateSetting( false );
				editedFilter.setNumDaysAhead( dateNumAheadField.getText() );
			}
			
			return editedFilter;
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
			taskList.getTasks().remove( taskList.getFiltered( filter, idx ) );
			// We need to update the list by sorting it.
			sortTaskList();
			try {
				taskList.writeToFile( saveFileName );
			} catch ( FileNotFoundException f ) {
				JOptionPane.showMessageDialog( null, "Error: Could not save planner" );
			}
			break;
		case "AddTask":
			UIManager.put( "OptionPane.minimumSize", new Dimension( 450, 300 ) );
			
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
			Task editedTask = taskList.getFiltered( filter, selectedIndex );
			
			UIManager.put( "OptionPane.minimumSize", new Dimension( 450, 300 ) );
			
			EditTaskPanel editPanel = new EditTaskPanel( editedTask );
			Task finalTask = null;
			int adResult = JOptionPane.OK_OPTION;
			while ( finalTask == null && adResult == JOptionPane.OK_OPTION ) {
				adResult = JOptionPane.showConfirmDialog( this, editPanel, "Edit Task", JOptionPane.OK_CANCEL_OPTION );
				if ( adResult == JOptionPane.OK_OPTION ) {
					finalTask = editPanel.compileTask();
				}
			}
			if ( finalTask != null ) {
				taskList.getTasks().remove( editedTask );
				taskList.getTasks().add( finalTask );
			}
			sortTaskList();
			try {
				taskList.writeToFile( saveFileName );
			} catch ( FileNotFoundException f ) {
				JOptionPane.showMessageDialog( null, "Error: Could not save planner" );
			}
			break;
		case "OpenFilter":
			UIManager.put( "OptionPane.minimumSize", new Dimension( 600, 300 ) );
			
			EditFiltersPanel filterPanel = new EditFiltersPanel( filter );
			Filter finalFilter = null;
			int ares = JOptionPane.OK_OPTION;
			while( finalFilter == null && ares == JOptionPane.OK_OPTION ) {
				ares = JOptionPane.showConfirmDialog( this, filterPanel, "Filtering Options", JOptionPane.OK_CANCEL_OPTION );
				if ( ares == JOptionPane.OK_OPTION ) {
					finalFilter = filterPanel.compileFilter();
				}
			}
			if ( finalFilter != null )
				filter = finalFilter;
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
		if ( args.length == 0 ) {
			app = new TaskPlannerGUI();
			JFileChooser fileChooser = new JFileChooser();
			int fOpt = fileChooser.showOpenDialog( app );
			
			if ( fOpt == JFileChooser.APPROVE_OPTION ) {
				String filePath = fileChooser.getSelectedFile().getPath();
				app.dispose();
				app = new TaskPlannerGUI( filePath );
			}
		}
		else
			app = new TaskPlannerGUI( args[ 0 ] );
	}

}
