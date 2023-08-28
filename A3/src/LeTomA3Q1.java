/*** LeTomA3Q1
 *
 * COMP 2140 SECTION D01*
 * ASSIGNMENT   Assignment 3, question 1
 * @author      Tom Le, 7871324
 * @version     June 24th 2020
 *
 * PURPOSE: This program will try to solve a maze with two approaches, a stack and a queue */

import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.File;

enum squareType{
    START,
    FINISH,
    PATH,
    WALL;
}

// the Main class
public class LeTomA3Q1 {
    private static Maze maze;

    /** private methods */
    // This method handles the input file, privately called by the main method
    private static void processInput() throws FileNotFoundException {
        // Prompt user for an input file
        String path;
        Scanner userinput = new Scanner(System.in);
        System.out.println( "\nEnter the input file name (.txt files only): " );
        path = userinput.nextLine();
        File fileName = new File(path); // input file
        Scanner scanner = new Scanner(fileName);

        System.out.println("\nProcessing file " + fileName + "\n");

        // Maze initialization
        String input = scanner.nextLine();
        String token[] = input.split(" ");
        maze = new Maze(Integer.parseInt(token[0]), Integer.parseInt(token[1]));

        int r = 0;
        // While the input file still has next line
        while (scanner.hasNextLine()){
            input = scanner.nextLine();
            for(int c=0; c<input.length(); c++){
                maze.insert(r, c, Character.toString(input.charAt(c)));
            }
            r++;
        } // end of while the input file still has next line

        System.out.println("The initial maze is:");
        maze.print();
        maze.stackSearch();
        maze.mazeReset();
        //System.out.println("The reset maze is:");
        //maze.print();
        maze.queueSearch();
    }// end of readInput() method

    // Main method
    public static void main(String[] args) throws FileNotFoundException {
        processInput();
        System.out.println("Program has executed successfully");
    }// end of main method
} // end of LeTomA2Q2(main) class



// The Position class
class Position{
    private int row;
    private int column;
    private squareType typeOfSquare;
    private boolean isVisited;
    private boolean isPath; // a value to determine if the position is part of the path from start to finish
    private Position prev;

    // default constructor
    public Position(int row, int column, squareType typeOfSquare){
        this.row = row;
        this.column = column;
        this.typeOfSquare = typeOfSquare;
        isVisited = false;
        isPath = false;
        prev = null;
    }

    // Accessors
    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public squareType getTypeOfSquare() {
        return typeOfSquare;
    }

    public boolean isVisited() {
        return isVisited;
    }

    public Position getPrev() {
        return prev;
    }

    // Setters
    public void setVisited(boolean visited) {
        isVisited = visited;
    }

    public void setPath(boolean value) {
        isPath = value;
    }

    public void setPrev(Position prev) {
        this.prev = prev;
    }

    /** Public methods */
    // This method returns the symbol of the position as a string
    public String getSymbol(){
        String symbol = "";

        switch (typeOfSquare){
            case START:
                symbol = "S";
                break;

            case PATH:
                if(isPath){
                    symbol = "*";
                }
                else{
                    symbol = ".";
                }
                break;

            case WALL:
                symbol = "#";
                break;

            case FINISH:
                symbol = "F";
                break;
        } // end of switch case

        return symbol;
    } // end of getSymbol() method

    // This method returns a string with the coordinate of the position
    public String getCoordinate(){
        return "("+ row + ", "+ column +")";
    } // end of getCoordinate() method

    // This method returns a string of both the coordinate and symbol of the position
    public String toString(){
        return "Type " + getSymbol() + " at " + getCoordinate();
    } // end of toString() method
} // end of Position class



// The Queue class
class Queue{
    // The Node Class
    class Node{
        private Position pos;
        private Node next;
        private Node prev;

        // default constructor
        public Node(Position pos, Node next, Node prev) {
            this.pos = pos;
            this.next = next;
            this.prev = prev;
        }

        // Accessors
        public Position getPosition() {
            return pos;
        }

        public Node getNext() {
            return next;
        }

        public Node getPrev() {
            return prev;
        }

        // Setters
        public void setNext(Node next) {
            this.next = next;
        }

        public void setPrev(Node prev) {
            this.prev = prev;
        }
    } // end of Node class

    // Queue variables
    private Node head;
    private Node tail;
    private int size;

    // default constructor
    public Queue() {
        head = tail = null;
        this.size = 0;
    }

    /** Public methods */
    // This method enqueues a new train car to the train end of the train and updates the sizes accordingly
    public void enqueue(Position pos){
        // append the new node to the end of the list(train)
        Node newNode = new Node(pos, null, null);

        // if the queue is empty, insert at head
        if(isEmpty()){
            head = tail = newNode;
        }
        // else the queue is not empty
        else{
            tail.setNext(newNode);
            newNode.setPrev(tail);
            tail = newNode;
        }
        size++;
    } // end of append(...) method

    // This method removes and returns the first item of the queue
    public Position dequeue(){
        Node result;

        // if the queue is empty, throw an exception
        if(isEmpty()){
            throw new RuntimeException("The queue is empty");
        }
        // else if the queue only has one item
        else if(head == tail){
            tail = null;
            result = head;
            head = head.getNext();
        }
        // else the queue has more than one item
        else{
            result = head;
            head = head.getNext();
            head.setPrev(null);
        }
        size--;
        return result.getPosition();
    } // end of dequeue() method

    // This method returns the top item of the queue
    public Position top(){
        return head.getPosition();
    } // end of top() method

    // This method returns true if the queue is empty or false otherwise
    public boolean isEmpty(){
        return size == 0;
    } // end of isEmpty() method

    // This method returns a String of all of the items inside the queue
    public String toString(){
        String result = "";
        Node curr = head;
        while(curr != null){
            result += curr.getPosition().toString() + "\n";
            curr = curr.getNext();
        }
        return result;
    } // end of toString() method
} // end of Queue class



// The Stack class
class Stack{
    private Position[] stack;
    private int size;

    // default constructor
    public Stack(int length){
        stack = new Position[length];
        size = 0;
    }

    /** Public methods */
    // This method pushes the new position into the stack
    public void push(Position newPos){
        if(size >= stack.length){
            throw new RuntimeException("The stack is full");
        }
        stack[size] = newPos;
        size++;
    } // end of push(...) method

    // This method removes and returns the top item of the stack
    public Position pop(){
        if(isEmpty()){
            throw new RuntimeException("The stack is empty");
        }
        size--;
        return stack[size];
    } // end of pop() method

    // This method returns the top item of the stack
    public Position top(){
        Position top = pop();
        push(top);
        return top;
    } // end of top() method

    // This method returns true if the stack is empty or false otherwise
    public boolean isEmpty(){
        return size == 0;
    } // end of isEmpty() method

    // This method returns a String of all of the items inside the stack
    public String toString(){
        String result = "";
        for(int i=0; i<size; i++){
            result += stack[i].toString() + "\n";
        }
        return result;
    } // end of toString() method
} // end of Stack class



// The Maze class
class Maze{
    private Position[][] maze;
    private Stack stack;
    private Queue queue;
    public Position start; // a variable to store the start position
    public Position finish; // a variable to store the finish position

    // default constructor
    public Maze(int row, int col){
        maze = new Position[row][col];
        stack = new Stack(row+col);
        queue = new Queue();
        start = finish = null;
    }

    /** Private methods */
    // This method returns true if the position is a path and is not visited
    private boolean posCheck(Position pos){
        boolean result = false;

        if(pos != null){
            if(pos.getTypeOfSquare() == squareType.PATH || pos.getTypeOfSquare() == squareType.FINISH){
                if(!pos.isVisited()){
                    result = true;
                }
            }
        }

        return result;
    } // end of posCheck(...) helper method

    // This method prints out the path result if found
    private void pathResult(Position pos, String ADT){
        String path = "";

        // while loop goes through the positions from finish to start
        while(pos != null){
            // format the result
            path = pos.getCoordinate() + " " + path;
            pos.setPath(true);
            // get the previous position
            pos = pos.getPrev();
        }

        System.out.println("The path found using the " + ADT + " is");
        print(); // print the maze
        System.out.println("Path from start to finish: " + path);
    } // end of pathResult helper method

    /** Public methods */
    // This method inserts a new Position into the array based on the given input
    public void insert(int row, int col, String squareSymbol){
        squareType typeOfSquare = null;
        switch (squareSymbol){
            case "S" -> typeOfSquare = squareType.START;
            case "." -> typeOfSquare = squareType.PATH;
            case "#" -> typeOfSquare = squareType.WALL;
            case "F" -> typeOfSquare = squareType.FINISH;
        }
        Position newPos = new Position(row, col, typeOfSquare);
        maze[row][col] = newPos;
        if(squareSymbol.equalsIgnoreCase("s")){
            start = maze[row][col];
        }
        else if(squareSymbol.equalsIgnoreCase("f")){
            finish = maze[row][col];
        }
    } // end of insert(...) method

    // This method searches the maze for a path using stack, returns the finish Position if found or null otherwise
    public void stackSearch(){
        Position curr;
        start.setVisited(true);
        stack.push(start);

        while(!stack.isEmpty()){
            curr = stack.pop();
            // check if the curr position is the finish position
            if(curr == finish) {
                pathResult(curr, "stack");
                return;
            }
            // else keep searching
            else{
                Position neighbour;
                // 1st neighbour if exist
                neighbour = maze[curr.getRow()-1][curr.getColumn()];
                if(posCheck(neighbour)){
                    neighbour.setVisited(true);
                    neighbour.setPrev(curr);
                    stack.push(neighbour);
                }

                // 2nd neighbour if exist
                neighbour = maze[curr.getRow()+1][curr.getColumn()];
                if(posCheck(neighbour)){
                    neighbour.setVisited(true);
                    neighbour.setPrev(curr);
                    stack.push(neighbour);
                }

                // 3rd neighbour if exist
                neighbour = maze[curr.getRow()][curr.getColumn()-1];
                if(posCheck(neighbour)){
                    neighbour.setVisited(true);
                    neighbour.setPrev(curr);
                    stack.push(neighbour);
                }

                // 4th neighbour if exist
                neighbour = maze[curr.getRow()][curr.getColumn()+1];
                if(posCheck(neighbour)){
                    neighbour.setVisited(true);
                    neighbour.setPrev(curr);
                    stack.push(neighbour);
                }
            } // end of else keep searching
        } // end of while loop

        System.out.println("No path is found using the stack");
    } // end of stackSearch() method

    // This method search the queue for a path
    public void queueSearch(){
        Position curr;
        start.setVisited(true);
        queue.enqueue(start);

        while(!queue.isEmpty()){
            curr = queue.dequeue();
            // check if the curr position is the finish position
            if(curr == finish) {
                pathResult(curr, "queue");
                return;
            }
            // else keep searching
            else{
                Position neighbour;
                // 1st neighbour if exist
                neighbour = maze[curr.getRow()-1][curr.getColumn()];
                if(posCheck(neighbour)){
                    neighbour.setVisited(true);
                    neighbour.setPrev(curr);
                    queue.enqueue(neighbour);
                }

                // 2nd neighbour if exist
                neighbour = maze[curr.getRow()+1][curr.getColumn()];
                if(posCheck(neighbour)){
                    neighbour.setVisited(true);
                    neighbour.setPrev(curr);
                    queue.enqueue(neighbour);
                }

                // 3rd neighbour if exist
                neighbour = maze[curr.getRow()][curr.getColumn()-1];
                if(posCheck(neighbour)){
                    neighbour.setVisited(true);
                    neighbour.setPrev(curr);
                    queue.enqueue(neighbour);
                }

                // 4th neighbour if exist
                neighbour = maze[curr.getRow()][curr.getColumn()+1];
                if(posCheck(neighbour)){
                    neighbour.setVisited(true);
                    neighbour.setPrev(curr);
                    queue.enqueue(neighbour);
                }
            } // end of else keep searching
        } // end of while loop

        System.out.println("No path is found using the queue");
    } // end of queueSearch() method

    // This method resets the maze to its original state
    public void mazeReset(){
        for(int r=0; r<maze.length; r++){ // 1st loop goes through the maze rows
            for(int c=0; c<maze[r].length; c++){ // 2nd loop goes through the each row columns
                // reset the position visit status to false if visited
                if(maze[r][c].isVisited()){
                    maze[r][c].setVisited(false);
                    maze[r][c].setPath(false);
                    maze[r][c].setPrev(null);
                }
            } // end of 2nd for loop
        } // end of 1st for loop
    } // end of mazeReset() method

    // This method prints out all the elements of the maze
    public void print(){
        for(int r=0; r<maze.length; r++){// 1st loop goes through all the maze rows
            String row = "";
            for(int c=0; c<maze[r].length; c++){ // 2nd for loop goes through each row columns
                row += maze[r][c].getSymbol();
            } // end of 2nd for loop
            System.out.println(row);
        } // end of 1st for loop
    } // end of print() method
} // end of Maze class

