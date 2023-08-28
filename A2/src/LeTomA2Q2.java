/*** LeTomA2Q2
 *
 * COMP 2140 SECTION D01*
 * ASSIGNMENT   Assignment 2, question 2
 * @author      Tom Le, 7871324
 * @version     June 9th 2020
 *
 * PURPOSE: This program modelling a train using doubly linked list */

import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.File;

// the Main class
public class LeTomA2Q2 {
    private static Train train = new Train();

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

        // While the input file still has next line
        while (scanner.hasNextLine()){
            String str = scanner.nextLine();
            System.out.println("Processing command: " + str);
            String[] input = str.split(" "); // split the argument by the space between them

            // A switch case for command prefixes
            switch (input[0].toUpperCase()){
                case "PICKUP":
                    int engine = 0;
                    int cargo = 0;

                    // A for loop to go through every next line that are required by the PICKUP command
                    for(int i=0; i<Integer.parseInt(input[1]); i++){
                        // Format the input
                        String[] trainCarInput = scanner.nextLine().split(" "); // split the argument by the space between them
                        // Check if the input has a value, if not its an engine type thus assigning it the value of 0
                        if(trainCarInput.length <= 1){
                            train.append(trainCarInput[0], 0);
                            engine++;
                        }
                        // Else the input is an cargo type
                        else{
                            train.append(trainCarInput[0], Integer.parseInt(trainCarInput[1]));
                            cargo++;
                        }
                    } // end of for loop

                    System.out.println(engine + " engines and " + cargo + " cars added to train");
                    break;

                case "PRINT":
                    System.out.println(train.toString());
                    break;

                case "DROPLAST":
                    train.dropLast(Integer.parseInt(input[1]));
                    break;

                case "DROPFIRST":
                    train.dropFirst(Integer.parseInt(input[1]));
                    break;

                case "DROP":
                    train.drop(input[1], Integer.parseInt(input[2]));
                    break;
            } // end of switch case
        } // end of while the input file still has next line
    }// end of readInput() method

    // Main method
    public static void main(String[] args) throws FileNotFoundException {
        processInput();
        System.out.println("Program has executed successfully");
    }// end of main method
} // end of LeTomA2Q2(main) class

// The TrainCar class
class TrainCar{
    private String cargoType;
    private int value;

    // default constructor
    public TrainCar(String cargoType, int value){
        this.cargoType = cargoType;
        this.value = value;
    }

    // Accessors
    public String getCargoType(){
        return cargoType;
    }

    public int getValue(){
        return value;
    }
} // end of TrainCar class

// The Node Class
class Node{
    private TrainCar trainCar;
    private Node next;
    private Node prev;

    // default constructor
    public Node(TrainCar trainCar, Node next, Node prev) {
        this.trainCar = trainCar;
        this.next = next;
        this.prev = prev;
    }

    // Accessors
    public TrainCar getTrainCar() {
        return trainCar;
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

// The Train class
class Train{
    private Node head;
    private Node tail;
    private int size; // total number train of cars of the train
    private int engineCars; // total number of engine type train cars
    private int cargoCars; // total number of cargo type train cars

    // default constructor
    public Train() {
        Node engine = new Node(new TrainCar("engine", 0), null, null);
        head = engine;
        tail = engine;
        size = 0;
        engineCars = 1;
        cargoCars = 0;
    }

    /** Private methods */
    // This method counts and returns the number of a specified cargo type train car of the train, privately called by the drop(...) methods
    private int countCargoType(String cargoType){
        int specifiedCars = 0;
        Node curr = head;

        // only count the cargo cars if the train size is more than 1
        if(size > 1){
            while (curr != null){
                if(curr.getTrainCar().getCargoType().equalsIgnoreCase(cargoType)){
                    specifiedCars++;
                }
                curr = curr.getNext();
            } // end of while loop
        } // end of if the train size is more than 1
        // else there's no cargo car
        return specifiedCars;
    }// end of countCargoType() helper method

    // This method remove the curr node from the list, privately called by the 3 drop(...) methods
    private void remove(Node curr){
        Node nextNode = curr.getNext();
        Node prevNode = curr.getPrev();

        if(nextNode != null){
            nextNode.setPrev(prevNode);
        }

        if(prevNode != null){
            prevNode.setNext(nextNode);
        }

        if(curr == tail){
            tail = prevNode;
        }
    } // end of remove(...) helper method

    // This method calculates and returns the total value of the train, privately called by the toString() method
    private int totalValue(){
        int value = 0;
        Node curr = head;

        // only calculate the value if the train size is more than 1
        if(size > 1){
            while (curr != null){
                value += curr.getTrainCar().getValue();
                curr = curr.getNext();
            } // end of while loop
        } // end of if the train size is more than 1
        // else there's no cargo car thus the value is 0
        return value;
    }// end of totalValue() helper method

    // This method return a String of all the cars in the train, privately called by toString() method
    private String trainCars(){
        String result = "";
        Node curr = head;

        while (curr != null){
            result += curr.getTrainCar().getCargoType();
            if(curr != tail){
                result += " - ";
            }
            curr = curr.getNext();
        }

        return result;
    } // end of trainCars() helper method

    /** Public methods */
    // This method appends a new train car to the train end of the train and updates the sizes accordingly
    public void append(String cargoType, int value){
        // append the new node to the end of the list(train)
        Node newNode = new Node(new TrainCar(cargoType, value), null, null);
        tail.setNext(newNode);
        newNode.setPrev(tail);
        tail = newNode;

        // Update the total number of train cars accordingly
        if(cargoType.equalsIgnoreCase("engine")){
            engineCars++;
        }
        else {
            cargoCars++;
        }
        size++;
    } // end of append(...) method

    // This method searches the train for the (number) of matching train cars then remove them from the train if so
    public void drop(String cargoType, int num){
        Node curr = head;
        int cargoTypeNum = countCargoType(cargoType);
        if(num > cargoTypeNum){
            num = cargoTypeNum;
        }

        int i=0; // this value is for keeping track of if the total number of train cars that were actually removed (whether or not to stop looping)
        while(i < num){
            if(curr.getTrainCar().getCargoType().equalsIgnoreCase(cargoType)){
                remove(curr);
                cargoCars--;
                i++;
            }
            curr = curr.getNext();
        } // end of while loop
        System.out.println(num + " cars dropped from train");
    } // end of drop(...) method

    // This method removes the first (number) of trains car that are not engine type from the train
    public void dropFirst(int num){
        Node curr = head;
        if(num > cargoCars){
            num = cargoCars;
        }

        int i = 0; // this value is for keeping track of if the total number of train cars that were actually removed (whether or not to stop looping)
        while(i < num){
            if(!curr.getTrainCar().getCargoType().equalsIgnoreCase("engine")){
                remove(curr);
                cargoCars--;
                i++;
            }
            curr = curr.getNext();
        } // end of while loop
        System.out.println(num + " cars dropped from train");
    } // end of dropFirst(...) method

    // This method removes the last (number) of trains car that are not engine type from the train
    public void dropLast(int num){
        Node curr = tail;
        if(num > cargoCars){
            num = cargoCars;
        }

        int i = 0; // this value is for keeping track of if the total number of train cars that were actually removed (whether or not to stop looping)
        while (i < num){
            if(curr.getTrainCar().getCargoType() != "engine"){
                remove(curr);
                cargoCars--;
                i++;
            }
            curr = curr.getPrev();
        }// end of while loop
        System.out.println(num + " cars dropped from train");
    } // end of dropLast(...) method

    // toString() method
    public String toString(){
        return "Total number of engines: "+ engineCars +", Total number of cargo cars: " + cargoCars +
                ", Total value of cargo: $" + totalValue() + "\nThe cars on the trains are: " + trainCars();
    } // end of toString() method
} // end of Train class

