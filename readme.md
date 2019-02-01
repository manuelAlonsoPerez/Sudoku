## Project Description:

This project reads an unsolved Sudoku from a text file and finds all the posible solutions. After founded the solutions are shown in a GUI window.

The file to be readen has to especify the number of rows and columns of the sudoku in separate lines and the initial values (using dots for empty value) per rows also in separate lines.

The MainProgram functions with two main threads, one for creating and solving the sudoku after reading the file. And another handling the GUI visualization.

From the GUI you can navigate between the solutions or select a new file.

## Importing the program:

From the console run 

`git clone https://github.com/manuelAlonsoPerez/Sudoku.git`

## Running the program:

Just compile all the java files

`javac *.java`

And run MainProgramJava

`java MainProgram`

## Motivation

This program was a mandatory assigment for the subject INF1010 – Objektorientert programmering during the first year of my bachelor studies. It was a solo programming assignment.

The program was build in three stages in one month according to the specifications demanded.

## Status

The  project is tested and working for Sudokus up to 12x12. Since one of the demands was to use brute force to solve the sudokus bigger problems can be solved but they demand a big RAM capacity.




