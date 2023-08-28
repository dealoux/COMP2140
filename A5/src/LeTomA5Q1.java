/*** LeTomA4Q1
 *
 * COMP 2140 SECTION D01*
 * ASSIGNMENT   Assignment 5, question 1
 * @author      Tom Le, 7871324
 * @version     July 27th 2020
 *
 * PURPOSE: This program simulates a Hospital Emergency Room using a priority queue */

import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.File;

// the Main class
public class LeTomA5Q1 {
    private static EmergencyRoom room = new EmergencyRoom();

    // Main method
    public static void main(String[] args) throws FileNotFoundException {
        room.simulating();
        System.out.println("Program has executed successfully");
    }// end of main method
} // end of LeTomA4Q1(main) class


// The Patient Class
class Patient{
    private int number;
    private int urgency;
    private int treatmentTime;


    // default constructor
    public Patient(int num, int urgen, int time) {
        number = num;
        urgency = urgen;
        treatmentTime = time;
    }

    // Accessors
    public int getNumber() {
        return number;
    }

    public int getUrgency() {
        return urgency;
    }

    public int getTime() {
        return treatmentTime;
    }


    // Setters
    public void setNumber(int num) {
        number = num;
    }

    public void setUrgency(int urgen) {
        urgency = urgen;
    }

    public void setTime(int time) {
        treatmentTime = time;
    }


    // toString method
    public String toString(String extra){
        return "Patient " + number + extra + " with urgency = " + urgency + " and treatment time = " + treatmentTime;
    }
} // end of Node class



// The PriorityQueue class
class PriorityQueue{
    // Queue variables
    private Patient[] queue;
    private int size;

    // default constructor
    public PriorityQueue( int arrSize ) {
        queue = new Patient[arrSize];
        size = 0;
    }

    /** Private helper methods */
    // This method swaps the 2 elements of the given arr
    private void swap(Patient arr[], int index1, int index2){
        Patient temp = arr[index1];
        arr[index1] = arr[index2];
        arr[index2] = temp;
    } // end of swap(...) helper method

    // This method double the length of the given queue
    private Patient[] enlarge(Patient[] queue){
        Patient[] newQueue = new Patient[queue.length*2];

        for(int i=0; i<queue.length; i++){
            newQueue[0] = queue[0];
        }

        // the queue is now enlarged
        return newQueue;
    }

    // This method sift the elements in the queue up inorder of the urgency
    private void siftUp (int index){
        int i = index;

        while( i>0 && queue[(i-1)/2].getUrgency() < queue[index].getUrgency()){
            swap(queue, i, (i-1)/2);
            i = (i-1)/2;
        }
    } // end of siftUp(...) helper method

    // This method returns the index of the larger (based on urgency) child of the given index, or -1 if neither child exist
    private int largestChild(int index){
        int left = index*2+1;
        int right = index*2+2;
        int result = -1;

        // check if only the right index is valid
        if(left > size && right < size){
            result = right;
        }
        // else if only the left index is valid
        else if(left < size && right > size){
            result = left;
        }
        // else if both are valid then compare them
        else if(left < size && right < size){
            if(queue[left].getUrgency() >= queue[right].getUrgency()){
                result = left;
            }
            else{
                result = right;
            }
        }

        return result;
    }

    // This method sift the elements in the queue up inorder of the urgency
    private void siftDown (int index){
        int childIndex = largestChild(index);

        while( index < size && childIndex != -1 && queue[childIndex].getUrgency() > queue[index].getUrgency()){
            swap(queue, index, childIndex);
            index = childIndex;
            childIndex = largestChild(index);
        }
    } // end of siftUp(...) helper method

    /** Public methods */
    // This method enqueues a new train car to the train end of the train and updates the sizes accordingly
    public void insert(int num, int arrivalTime, int urgency, int time){
        // check if the array need to be enlarge
        if(size >= queue.length){
            queue = enlarge(queue);
        }

        Patient newPatient = new Patient(num, urgency, time);
        queue[size] = newPatient;
        siftUp( size );
        size++;

        System.out.println(newPatient.toString(" arrived at time = " + arrivalTime));
    } // end of insert(...) method

    // This method removes and returns the first item of the queue
    public Patient deleteMax(){
        Patient result = queue[0];

        // if the queue is empty, throw an exception
        if(isEmpty()){
            throw new RuntimeException("The queue is empty");
        }
        // else if the queue only has one item
        else{
            queue[0] = queue[size-1];
            size--;
            siftDown(0);
        }
        // else the queue has more than one item

        return result;
    } // end of deleteMax() method

    // This method returns the top item of the queue
    public Patient peek(){
        return queue[0];
    } // end of peek() method

    // This method returns the queue size
    public int getSize(){
        return size;
    }

    // This method returns true if the queue is empty or false otherwise
    public boolean isEmpty(){
        return size == 0;
    } // end of isEmpty() method

    // This method returns a String of all of the items inside the queue
    public String toString(){
        String result = "";

        for(int i=0; i<size; i++){
            result += queue[i].toString();
        }

        return result;
    } // end of toString() method
} // end of PriorityQueue class



// The EmergencyRoom class
class EmergencyRoom{
    private int clock;
    private PriorityQueue queue;

    public EmergencyRoom(){
        clock = 0;
        queue = new PriorityQueue(24);
    }

    // This method simulates the emergency room base on the given input
    public void simulating() throws FileNotFoundException {
        // Prompt user for an input file
        String path;
        Scanner userinput = new Scanner(System.in);
        System.out.println( "\nEnter the input file name (.txt files only): " );
        path = userinput.nextLine();
        File fileName = new File(path); // input file
        Scanner scanner = new Scanner(fileName);

        System.out.println("\nProcessing file " + fileName + "\n");

        int num = 0;
        // While the input file still has next line
        while (!queue.isEmpty() || scanner.hasNextLine()){

            // check if there's any new patient
            if(scanner.hasNextLine()){
                String[] token = scanner.nextLine().split(" ");
                int arrivalTime = Integer.parseInt(token[0]);
                int urgency = Integer.parseInt(token[1]);
                int treatmentTime = Integer.parseInt(token[2]);

                // treat any remaining patient base on the timeline
                while(clock <= arrivalTime && !queue.isEmpty()){
                    Patient currPatient = queue.deleteMax();
                    System.out.println("Doctor is available at time = " + clock);
                    System.out.println(currPatient.toString(" in for treatment at time = " + clock));
                    clock += currPatient.getTime();
                }

                // enqueue the patient base on their arrival time
                num++;
                // display the doctor available status if so
                if(clock <= arrivalTime){
                    System.out.println("Doctor is available at time = " + clock);
                    clock = arrivalTime;
                }
                queue.insert(num, arrivalTime, urgency, treatmentTime);
            }

            // else there's no new patient, proceed to treat the rest of the enqueued patients
            else{
                Patient currPatient = queue.deleteMax();
                System.out.println("Doctor is available at time = " + clock);
                System.out.println(currPatient.toString(" in for treatment at time = " + clock));
                clock += currPatient.getTime();
            }
        } // end of while the input file still has next line
    }// end of simulating() method

} // end of EmergencyRoom class



