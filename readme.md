# Homework Planner Instructions

This Homework Planner is simple to use. Included in this repository are the program files, which can be loaded into an IDE or otherwise manipulated, as well as a compiled executable JAR that also runs the program. To run the program, simply execute the JAR file.

## Usage instructions

Upon reaching the main screen, several options are available. They should be fairly self-explanatory: first, use the ```Add New Task...``` button to add a task, and fill in the fields in the consequent pop-up window as appropriate. The ```Remove``` and ```Edit``` buttons can be used when a task has been added to the list on the left of the application, by selecting the item to perform the operation on.

There are also four sorting options, that sort the list to the left by the listed fields. Whichever radio button is highlighted is the one that is currently the sorting criteria. The list is re-sorted after every operation.

The fields of a task include:
- ***Priority***: A custom metric of how important the task is. Higher numbers appear first in the list.
- ***Due Date***: A date determining when the task needs to be completed by. Earlier dates appear first in the list.
- ***Name***: A simple designation of what each task is.
- ***Description***: A more detailed description of the task, for convenience's sake.
- ***Time***: An estimate of how long a task will take to complete, in hours. This is included for organization and prioritization based on how much time you have available.
- ***Class***: A designation of which class (or some other purpose) each task is for. The sort function for classes is lexicographic, and serves mostly to group tasks of the same class together for easier viewing.

## Saving Data

The Homework Planner saves data automatically after every Add, Remove, and Edit operation. By default, this is saved to a ```planner.pxt``` file in the same directory as the program. When the Homework Planner loads, it will generally start from scratch and overwrite a planner file that already exists in the directory.

However, if the Homework Planner is run with an argument, it will use that argument as a file from which to load data, and to which to consequently save the data. Start with a blank file and include its path as the Homework Planner's argument when launching the program, and the Homework Planner will save to that file. When the Homework Planner is again run with the same argument, progress from the last session will have been saved, and will be recovered.