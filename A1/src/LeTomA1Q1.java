/*** LeTomA1Q1
 *
 * COMP 2140 SECTION D01*
 * ASSIGNMENT   Assignment 1, question 1
 * @author      Tom Le, 7871324
 * @version     May 14 2020
 *
 * PURPOSE: this program acts as a library for books*/

import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.File;

public class LeTomA1Q1 {
    private static Library library = new Library();

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

        while(scanner.hasNextLine()){ // while loop go through each line of the input file
            // format the input
            String[] input = scanner.nextLine().split(",", 3); // split the argument by the commas, limit to 3 to prevent accidentally splitting tittles that includes commas
            String[] prefixSplit = input[0].split(" ");

            switch (prefixSplit[0].toLowerCase()){ // switch case for prefix
                case "add":
                    library.addBook(prefixSplit[1], input[1], input[2]);
                    break;

                case "searcha":
                    System.out.println("Book(s) by " + prefixSplit[1] + ":\n" + library.listByAuthor(prefixSplit[1]));
                    break;

                case "searcht":
                    String tittle = "";
                    for(int i=1; i<prefixSplit.length; i++)
                        tittle += " " + prefixSplit[i];
                    System.out.println("Book(s) tittled" + tittle + ":\n" + library.listByTittle(tittle));
                    break;

                case "getbook":
                    String book = prefixSplit[1] + ", " + input[1] + ", " + input[2] + "\n";
                    if(library.loanBook(prefixSplit[1], input[1], input[2])){
                        System.out.println("Book loaned:\n" + book);
                    }
                    else{
                        System.out.println("Book is either unavailable for loan or doesn't exist in library:\n" + book);
                    }
                    break;

                case "returnbook":
                    book = prefixSplit[1] + ", " + input[1] + ", " + input[2] + "\n";
                    if(library.returnBook(prefixSplit[1], input[1], input[2])){
                        System.out.println("Book returned:\n" + book);
                    }
                    else{
                        System.out.println("Book is either already returned or doesn't exist in library:\n" + book);
                    }
                    break;
            } // end of switch case
        } // end of while loop
    } // end of readInput() method

    // Main method
    public static void main(String[] args) throws FileNotFoundException {
        readInput();
        //System.out.println("List of book(s) in the library:\n" + library.toString()); // print out the whole library
        System.out.println("Program has executed successfully");
    } // end of main method
} // end of LeTomA1Q1(main) class

class Book{
    private String authorLN = "Unknown";
    private String authorFN = " Unknown";
    private String tittle = " Unknown";
    private boolean loaned;

    public Book(String lastName, String firstName, String bookTittle) {  // default constructor
        if(!lastName.trim().equals(""))
            authorLN = lastName;

        if(!firstName.trim().equals(""))
            authorFN = firstName;

        if(!bookTittle.trim().equals(""))
            tittle = bookTittle;

        loaned = false;
    }

    // accessors
    public String getAuthorLN() {
        return authorLN;
    }

    public String getAuthorFN() {
        return authorFN;
    }

    public String getTittle() {
        return tittle;
    }

    public boolean isLoaned() {
        return loaned;
    }

    // setters
    public void setLoaningStatus(boolean status) {
        loaned = status;
    }

    public String toString(){ // toString method
        return authorLN + "," + authorFN + "," + tittle + "\n";
    }
} // end of Book class

class Library{
    private Book[] library;

    public Library(){ // default constructor
        library = new Book[0];
    }

    /** private helper methods */

    // this method return an array of index(es) of the book(s) in the library if matches all the conditions, if not return the empty array. Is privately called by loanBook(...) & returnBook(...) methods
    private int[] getIndexes(String lastName, String firstName, String tittle){
        int[] indexes = new int[0]; // array of index(es) default to empty

        for(int i=0; i<library.length; i++){
            // checks all the conditions
            if(library[i].getAuthorLN().trim().equals(lastName.trim()) && library[i].getAuthorFN().trim().equals(firstName.trim()) && library[i].getTittle().trim().equals(tittle.trim())) {
                int[] temp = new int[indexes.length+1]; // temporary array for appending purposes
                // array appending algorithm
                if(temp.length == 1)
                    temp[0] = i;
                else{
                    // array deep copy
                    for(int j=0; j<indexes.length; j++)
                        temp[j] = indexes[j];
                    temp[temp.length-1] = i;
                }
                indexes = temp;
            }
        }
        return indexes;
    } // end of getIndexes(...) method

    // this method sort the library alphabetically using bubble sort algorithm, privately called by addBook(...) method
    private void sortLibrary(){
        Book temp; // a temporary variable for swapping

        // bubble sort algorithm
        for(int i=0; i<library.length; i++){
            for(int j=i+1; j<library.length; j++){
                if(library[i].getAuthorLN().trim().compareToIgnoreCase(library[j].getAuthorLN().trim()) > 0){
                    // swap the books position
                    temp = library[i];
                    library[i] = library[j];
                    library[j] = temp;
                }
                else if (library[i].getAuthorLN().trim().equalsIgnoreCase(library[j].getAuthorLN().trim())){
                    // author's first name check
                    if(library[i].getAuthorFN().trim().compareToIgnoreCase(library[j].getAuthorFN().trim()) > 0){
                        // swap the books position
                        temp = library[i];
                        library[i] = library[j];
                        library[j] = temp;
                    }
                    else if(library[i].getAuthorFN().trim().equalsIgnoreCase(library[j].getAuthorFN().trim())){
                        // book tittle check
                        if(library[i].getTittle().trim().compareToIgnoreCase(library[j].getTittle().trim()) > 0){
                            // swap the books position
                            temp = library[i];
                            library[i] = library[j];
                            library[j] = temp;
                        }
                    }
                }
            } // end of for (int j...) loop
        } // end of for (int i...) loop
    } // end of sortLibrary() method

    /** public methods */

    public void addBook(String lastName, String firstName, String bookTittle){
        // construct the new book
        Book newBook = new Book(lastName, firstName, bookTittle);

        Book[] temp = new Book[library.length+1]; // temporary array for appending purposes
        // array appending algorithm
        if(temp.length == 1)
            temp[0] = newBook;
        else{
            // array deep copy
            for(int i=0; i<library.length; i++)
                temp[i] = library[i];
            temp[temp.length-1] = newBook;
        }
        library = temp;

        // sort the library
        if(library.length <= 1) return;
        sortLibrary();
    } // end of addBook(...) method

    public String listByAuthor(String lastName){
        String list = "";
        for (Book book : library) {
            if (book.getAuthorLN().trim().equalsIgnoreCase(lastName.trim()))
                list += book.toString();
        }
        return list;
    } // end of listByAuthor(...) method

    public String listByTittle(String tittle){
        String list = "";
        for (Book book : library) {
            if (book.getTittle().trim().equalsIgnoreCase(tittle.trim()))
                list += book.toString();
        }
        return list;
    } // end of listByTittle(...) method

    public boolean loanBook(String lastName, String firstName, String tittle){
        int[] indexes = getIndexes(lastName, firstName, tittle); // get the book's index in the library
        int curr = 0;

        // return false if the book does not exist in the library or is already being loaned
        if(indexes.length == 0) return false;

        while (curr < indexes.length) {
            if(!library[indexes[curr]].isLoaned()){ // checks if there's any book(s) available for loan with the given parameters
                // update the loaning status
                library[indexes[curr]].setLoaningStatus(true);
                return true;
            }
            curr++;
        } // end of while loop
        return false;
    } // end of loanBook(...) method

    public boolean returnBook(String lastName, String firstName, String tittle){
        int[] indexes = getIndexes(lastName, firstName, tittle); // get the book's index in the library
        int curr = 0;

        // return false if the book does not exist in the library or is already being loaned
        if(indexes.length == 0) return false;

        while (curr < indexes.length) {
            if(library[indexes[curr]].isLoaned()){ // checks if there's any book(s) available for loan with the given parameters
                // update the loaning status
                library[indexes[curr]].setLoaningStatus(false);
                return true;
            }
            curr++;
        } // end of while loop
        return false;
    } // end of returnBook(...) method

    // This method returns a String as a list of all the books inside the library
    public String toString(){
        String booklist = "";
        for(int i=0; i<library.length; i++)
            booklist += library[i].toString();
        return booklist;
    } // end of toString() method
} // end of Library class
