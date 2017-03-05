# Homework Planner Instructions

This Homework Planner is simple to use. Included in this repository are the program files, which can be loaded into an IDE or otherwise manipulated, as well as a compiled runnable JAR that can be executed on the command line to run the program. Additionally, included is an OS X .app file for the application, as well as a .zip file containing a Windows executable (that hopefully is functional). These can also be used to run the Homework Planner depending on your platform.

## Usage instructions

Upon reaching the main screen, several options are available. They should be fairly self-explanatory: first, use the ```Add New Task...``` button to add a task, and fill in the fields in the consequent pop-up window as appropriate. The ```Remove``` and ```Edit``` buttons can be used when a task has been added to the list on the left of the application, by selecting the item to perform the operation on. 

The fields of a task include:
- ***Priority***: A custom metric of how important the task is.
- ***Due Date***: A date determining when the task needs to be completed by.
- ***Name***: A simple designation of what each task is.
- ***Description***: A more detailed description of the task, for convenience's sake.
- ***Time***: An estimate of how long a task will take to complete, in hours. This is included for organization and prioritization based on how much time you have available.
- ***Class***: A designation of which class (or some other purpose) each task is for. This is useful for grouping tasks by their overall purpose or which project or class they are part of.

There are also twelve sorting options, that sort the list to the left by the listed fields. Tasks can be sorted by any field, and the two radio buttons next to each field represent the two directions in which they can be sorted. In general, the right radio button sorts the tasks in order, and the left radio button sorts them in reverse order, based on the respective criteria. The list is re-sorted after every operation.

Additionally, a filter can be applied to the list of tasks to temporarily remove tasks from view that aren't immediately relevant. To edit the current filter, click the ```Filtering Options...``` button, and simply input the desired filter into any of the text boxes in the resulting pop-up window. Any field left blank will be ignored by the filter, so to remove a filter simply delete it from its text box and leave the text box blank.

## Saving Data

The Homework Planner saves data automatically after every Add, Remove, and Edit operation. The file it saves to is decided at startup via a file chooser; by default, if a file is not chosen, a ```planner.pxt``` file is created in the same directory as the program. When the Homework Planner loads, it will generally start from scratch and overwrite a planner file that already exists in the directory.

However, if the Homework Planner is run as a .jar file with an argument, it will use that argument as a file from which to load data, and to which to consequently save the data. Start with a blank file and include its path as the Homework Planner's argument when launching the program, and the Homework Planner will save to that file. When the Homework Planner is again run with the same argument, progress from the last session will have been saved, and will be recovered.