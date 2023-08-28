/*** LeTomA1Q2
 *
 * COMP 2140 SECTION D01*
 * ASSIGNMENT   Assignment 1, question 2
 * @author      Tom Le, 7871324
 * @version     May 22 2020
 *
 * PURPOSE: this program recursively solve a sudoku problem*/

import java.io.FileNotFoundException;
import java.util.Scanner;
import java.lang.Math;
import java.io.File;

// the Main class
public class LeTomA1Q2 {
    private static Sudoku sudoku;

    /** private methods */
    // This method initialized the sudoku board with the provided String Array and then print out the board
    private static void initSudoku(String[] arr){
        double size = Math.sqrt(arr.length);
        if((int) size > 1 && (int) size == size) { // checks if the board is valid by verifying if the square root result is an integer
            int index = 0;
            sudoku = new Sudoku((int) size, (int)size);
            // fills the board with the input
            for(int i = 0; i< (int) size; i++){
                for(int j = 0; j< (int) size; j++){
                    sudoku.fillCell(i, j, arr[index++]);
                }
            }
        }
        else // else the board is invalid
            sudoku = new Sudoku(9, 9); // default empty board

        System.out.println("The original board is:\n" + sudoku.toString()); // prints out the original board
    } // end of initSudoku(...) method

    // This method handles the input file, privately called by the main method
    private static void readInput() throws FileNotFoundException {
        // Prompt user for an input file
        String path;
        Scanner userinput = new Scanner(System.in);
        System.out.println( "\nEnter the input file name (.txt files only): " );
        path = userinput.nextLine();
        File fileName = new File(path); // input file
        Scanner scanner = new Scanner(fileName);

        System.out.println("\nProcessing file " + fileName + "\n");

        while(scanner.hasNextLine()){
            System.out.println("New Board Input");
            String[] input = scanner.nextLine().split(" "); // split the argument by the space between them
            initSudoku(input);

            if(sudoku.solve()) // if the provided board is solvable
                System.out.println("The solution is:\n" + sudoku.toString());
            else // else its unsolvable
                System.out.println("The provided board is unsolvable\n" + sudoku.toString());
        }// end of while loop
    }// end of readInput() method

    // Main method
    public static void main(String[] args) throws FileNotFoundException {
        readInput();
        System.out.println("Program has executed successfully");
    }// end of main method
} // end of LeTomA1Q2(main) class

// The Sudoku class
class Sudoku{
    private int rows = 9;
    private int cols = 9;
    private int[][] sudoku; // default sudoku board is 9x9

    public Sudoku(int rows, int cols){ // default constructor
        if(rows > 1 && cols > 1){
            this.rows = rows;
            this.cols = cols;
        }
        sudoku = new int[this.rows][this.cols];
    }

    /** private methods */
    // This method returns true if the number is already exist in the row, false otherwise, privately called by the solve() method
    private boolean isInRow(int row, int num){
        for(int i = 0; i<cols; i++){ // for loop traverse through the whole row
            if(num == sudoku[row][i])
                    return true;
        }
        return false;
    } // end of isInRow(...) method

    // This method returns true if the number is already exist in the column, false otherwise, privately called by the solve() method
    private boolean isInCol(int col, int num){
        for(int i = 0; i<rows; i++){ // for loop traverse through the whole column
            if(num == sudoku[i][col])
                return true;
        }
        return false;
    } // end of isInCol(...) method

    // This method returns true if the number is already exist in the sub-grid, false otherwise, privately called by the solve() method
    private boolean isInSubGrid(int row, int col, int num){
        int subGridLenght = (int)Math.floor(Math.sqrt(rows));
        int subRow = row - row % subGridLenght;
        int subCol = col - col % subGridLenght;
        for(int i = subRow; i < subRow + subGridLenght; i++){ // 1st loop traverse through all the sub-rows
            for(int j = subCol; j < subCol + subGridLenght; j++){ // 2nd loop traverse through all the sub-columns
                if(num == sudoku[i][j])
                    return true;
            }// end of 2nd for loop (sub-columns traverse)
        }// end of 1st for loop (sub-rows traverse)
        return false;
    } // end of isInSubGrid(...) method

    // This method checks if the String is numeric, return true if so, return false if not, privately called by the fillCell(...) method
    private static boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    } // end of isNumeric(...) method

    /** public methods */
    // This method fills the cell with the num value
    public void fillCell(int row, int col, String num){
        if(isNumeric(num))
            sudoku[row][col] = Integer.parseInt(num);
    } // end of fillCell(...) method

    // This method is the sudoku solver
    public boolean solve(){
        for(int row = 0; row<rows; row++){ // 1st loop traverse through all the rows
            for(int col = 0; col<cols; col++){ // 2nd loop traverse through all the columns
                // checks if the cell is empty (equals 0), if so attempt to solve it
                if(sudoku[row][col] == 0){
                    for(int num=1; num<=sudoku.length; num++){ // try each number from 1 to the maximum number of grid size
                        // try filling in the number if satisfy all of the checks
                        if(!isInRow(row, num) && !isInCol(col, num) && !isInSubGrid(row, col, num)){
                            sudoku[row][col] = num;
                            // check if the sudoku can be solved with the current filling by recursively calling the solve(...) method
                            if(solve())
                                return true; // the sudoku is solvable
                            else
                                sudoku[row][col] = 0; // backtracking as the current filling will reach an dead-end
                        }
                    } // end of for loop trying each number from 1 to grid size
                    return false; // the sudoku is unsolvable
                } // end of if the cell is empty
            } // end of 2nd for loop (columns traverse)
        } // end of 1st for loop (rows traverse)
        return true; // solved as there's no empty cell left
    } // end of the solve(...) method

    // This method returns a String as the sudoku board
    public String toString(){
        String sudokuDisplay = "";
        for(int i=0; i<rows; i++){ // 1st loop traverse through all the rows
            for(int j=0; j<cols; j++){ // 2nd loop traverse through all the columns
                if(sudoku[i][j] == 0)
                    sudokuDisplay += "- "; // display a dash as an empty cell
                else
                    sudokuDisplay += sudoku[i][j] + " "; // display a the number inside cell
            } // end of 2nd for loop (columns traverse)
            sudokuDisplay += "\n"; // new line for each row
        } // end of 1st for loop (rows traverse)
        return sudokuDisplay;
    } // end of toString() method
} // end of Sudoku class

/** Regarding the questions
 * 1) I chose 2D array instead of 1D array because it would be easier to check the all the element in each rows with a nested for loops.
 *    My program should be flexible, which require Math.sqrt() so for element comparison and for ease of conversion sake, int is the better data type choice than char
 *
 * 2) I haven't tried any other approaches yet but the current approach seems to be the most efficient as it's very straight forward, it goes through every empty cell,
 *    tries each number from 1 to the gridSize, checks all the conditions then fills the cell with the appropriate number or backtracking and keeps recursively solving */

