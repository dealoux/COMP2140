import java.util.*;
import java.io.*;

/*** LeTomA2Q1
 *
 * COMP 2140 SECTION D01*
 * ASSIGNMENT   Assignment 2, question 1
 * @author      Tom Le, 7871324, the template author
 * @version     June 12th 2020
 *
 * PURPOSE: This program calculates the average time to sort of the 7 different sorting algorithms */

public class LeTomA2Q1 {

    // Control the testing
    private static final int ARRAY_SIZE = 10000;
    private static final int SAMPLE_SIZE = 300; // The number of trials in each experiment.

    // Control the randomness
    private static final int NUM_SWAPS = ARRAY_SIZE / 2;
    private static Random generator = new Random( System.nanoTime() );

    // Control the base cases for hybrid quick sort:
    private static final int BREAKPOINT = 50;

    // Controls which sort is tried.
    private static final int INSERTION_SORT = 0;
    private static final int BUBBLE_SORT = 1;
    private static final int SELECTION_SORT = 2;
    private static final int MERGE_SORT = 3;
    private static final int QUICK_SORT = 4;
    private static final int HYBRID_QUICK_SORT = 5;
    private static final int SHELL_SORT = 6;

/*********** main and the method it calls *************************/

    /*******************************************************************
     * main
     *
     * Purpose: Print out "bookend" messages and call the method that
     *          does all the testing of the sorting algorithms.
     *
     ******************************************************************/
    public static void main( String[] args ) {
		System.out.println( "\n\nCOMP 2140 A2Q1 Sorting Test --- Summer 2020\n" );

		testSorts();

		System.out.println( "\nProcessing ends normally\n" );
    } // end main


    /*******************************************************************
     * testSorts
     *
     * Purpose: Run each sorting algorithm SAMPLE_SIZE times,
     *          on an array of size ARRAY_SIZE with NUM_SWAPS
     *          random swaps performed on it.
     *          Compute the arithmetic mean of the timings for each sorting algorithm.
     *
     *          Print the results.
     *
     ******************************************************************/
    private static void testSorts() {

		// Arrays used in timing experiments (create these arrays once)
		int[] array = new int[ARRAY_SIZE]; // array to be sorted
		long[] sortTime = new long[ SAMPLE_SIZE ]; // store timings for multiple runs
	                                           // of a single sorting method
		// Fill array to be sorted with the numbers 0 to ARRAY_SIZE.
		// (The numbers will be randomly swapped before each sort.)
		fillArray( array );

		// Now run the experiments on all the sorts
		System.out.println("Array size: " + ARRAY_SIZE + "\nNumber of swaps: " + NUM_SWAPS);
		System.out.println("Number of trials of each sort: " + SAMPLE_SIZE );

		// Stats for each run
		System.out.println("\nInsertion sort mean: "
			   + tryOneSort( array, sortTime, INSERTION_SORT )
			   + " ns" );
		System.out.println("Bubble sort mean: "
			   + tryOneSort( array, sortTime, BUBBLE_SORT )
			   + " ns" );
		System.out.println("Selection sort mean: "
			   + tryOneSort( array, sortTime, SELECTION_SORT )
			   + " ns" );
		System.out.println("Merge sort mean: "
			   + tryOneSort( array, sortTime, MERGE_SORT )
			   + " ns" );
		System.out.println("Quick sort mean: "
			   + tryOneSort( array, sortTime, QUICK_SORT )
			   + " ns" );
		System.out.println("Hybrid quick sort mean: "
			   + tryOneSort( array, sortTime, HYBRID_QUICK_SORT )
			   + " ns" );
		System.out.println("Shell sort mean: "
			   + tryOneSort( array, sortTime, SHELL_SORT )
			   + " ns" );

    } // end testSorts

/*********** methods called by testSorts *************************/

    /*******************************************************************
     * tryOneSort:
     *
     * Purpose: Get an average run time for a sorting algorithm.
     *
     * Methodology: Run the chosen sorting algorithm SAMPLE_SIZE times,
     *          on an array of size ARRAY_SIZE with NUM_SWAPS
     *          random swaps performed on it.
     *          Return the arithmetic mean of the timings.
     *
     ******************************************************************/
    private static double tryOneSort( int[] array, long[] sortTime, int whichSort ) {

		long start, stop, elapsedTime;  // Time how long each sort takes.

		start = stop = 0; // because the compiler complains that they might
		                  // not have been initialized inside the for-loop

		for ( int i = 0; i < SAMPLE_SIZE; i++ ) {
		    randomizeArray( array, NUM_SWAPS );
		    if ( whichSort == INSERTION_SORT ) {
				start = System.nanoTime();
				insertionSort( array );
				stop = System.nanoTime();
				checkArray(array, "Insertion sort");
		    } else if ( whichSort == BUBBLE_SORT ) {
				start = System.nanoTime();
				bubbleSort( array );
				stop = System.nanoTime();
				checkArray(array, "Bubble sort");
		    } else if ( whichSort == SELECTION_SORT ) {
				start = System.nanoTime();
				selectionSort( array );
				stop = System.nanoTime();
				checkArray(array, "Selection sort");
		    } else if ( whichSort == MERGE_SORT ) {
				start = System.nanoTime();
				mergeSort( array );
				stop = System.nanoTime();
				checkArray(array, "Merge sort");
		    } else if ( whichSort == QUICK_SORT ) {
				start = System.nanoTime();
				quickSort( array );
				stop = System.nanoTime();
				checkArray(array, "Quick sort");
		    } else if ( whichSort == HYBRID_QUICK_SORT ) {
				start = System.nanoTime();
				hybridQuickSort( array );
				stop = System.nanoTime();
				checkArray(array, "Hybrid quick sort");
		    } else if ( whichSort == SHELL_SORT ) {
				start = System.nanoTime();
				shellSort( array );
				stop = System.nanoTime();
				checkArray(array, "Shell sort");
		    }
		    elapsedTime = stop - start;
		    sortTime[i] = elapsedTime;
		} // end for

		return arithmeticMean( sortTime );
    } // end tryOneSort


/********** Add sort methods here ********************/
	/** Insertion sort algorithm */
	// This method uses insertionSort algorithm to sort an array from the provided start position to the end position
	private static void insertionSort( int[] array, int start, int end){
		int i;
		int j;

		for (i = start; i < end; ++i) {
			j = i;
			while (j > 0 && array[j] < array[j-1]) {
				// Swap array[j] and array[j - 1] items
				swap(array, j, j-1);
				--j;
			} // end of while loop
		} // end of for loop
	} // end of private insertionSort(...) helper method

	// Insertion sort main driver method
	public static void insertionSort(int[] array){
		insertionSort(array, 0, array.length);
	} // end of public insertionSort(...) main driver method


	/** Bubble sort algorithm */
	// Bubble sort main driver method
	public static void bubbleSort(int[] array){
		int i;
		int j;
		for (i = 0; i < array.length-1; i++) { // 1st for loop
			for (j = 0; j < array.length-i-1; j++) { // 2nd for loop
				// Swap array[j] & array[j+1] items if the condition is satisfied
				if (array[j] > array[j+1]) {
					swap(array, j, j+1);
				}
			} // end of 2nd for loop
		} // end of 1st for loop
	} // end of bubbleSort(...) main driver method


	/** Selection sort algorithm */
	// Selection short main driver method
	public static void selectionSort(int[] array){
		int i;
		int index;
		for (i = 0; i < array.length-1; ++i) {
			index = findMin(array, i, array.length);
			swap(array, i, index);
		}
	} // end of selectionSort(...) main driver method

	// This method find the index of the minimum value within the start and end position of an array
	private static int findMin( int[] array, int start, int end ){
		int minIndex = start;
		int i;
		for(i = minIndex+1; i < end; i++){
			if(array[minIndex] > array[i]){
				minIndex = i;
			}
		}
		return minIndex;
	} // end of findMin(...) helper method

	/** Recursive merge sort algorithm */
	// Merge sort main driver method
	public static void mergeSort(int[] array){
		int start = 0;
		int end = array.length-1;
		int[] temp = new int[array.length];
		mergeSort(array, temp, start, end);
	} // end of mergeSort(...) main driver method

	// This method recursively do the merge sort on the array from position start to end-1
	private static void mergeSort(int[] array, int[] temp, int start, int end){
		int mid;
		if(start == end-1){
			if(array[start] > array[end]){
				swap(array, start, end);
			}
		}
		else if(start < end){
			mid = (start + end)/2;
			mergeSort(array, temp, start, mid);
			mergeSort(array, temp, mid+1, end);

			merge(array, temp, start, mid, end);
		}
	} // end of private mergeSort(...) helper method

	// This method merges the two sorted sub-lists (defined by start, mid and end indices) into 1 sorted list using the temp array
	private static void merge(int[] array, int[] temp, int start, int mid, int end){
		int mergedSize = end - start + 1;                // Size of merged partition
		int mergePos = 0;                          // Position to insert merged number
		int leftPos = 0;                           // Position of elements in left partition
		int rightPos = 0;                          // Position of elements in right partition
		temp = new int[mergedSize];

		leftPos = start;                           // Initialize left partition position
		rightPos = mid + 1;                      // Initialize right partition position

		// Add smallest element from left or right partition to merged numbers
		while (leftPos <= mid && rightPos <= end) {
			if (array[leftPos] <= array[rightPos]) {
				temp[mergePos] = array[leftPos];
				++leftPos;
			}
			else {
				temp[mergePos] = array[rightPos];
				++rightPos;

			}
			++mergePos;
		}

		// If left partition is not empty, add remaining elements to merged numbers
		while (leftPos <= mid) {
			temp[mergePos] = array[leftPos];
			++leftPos;
			++mergePos;
		}

		// If right partition is not empty, add remaining elements to merged numbers
		while (rightPos <= end) {
			temp[mergePos] = array[rightPos];
			++rightPos;
			++mergePos;
		}

		// Copy merge number back to numbers
		for (mergePos = 0; mergePos < mergedSize; ++mergePos) {
			array[start + mergePos] = temp[mergePos];
		}
	} // end of private merge(...) helper method


	/** Recursive quick sort algorithm */
	// Quick sort main driver method
	public static void quickSort(int[] array){
		quickSort(array, 0, array.length);
	} // end of quickSort(...) main driver method

	// This method is the recursive quick sort helper method
	private static void quickSort(int[] array, int start, int end){
		int pivotPosn;

		if(2 == end-start){
			if(array[start] > array[start+1]){
				swap(array, start, start+1);
			}
		}
		else if(2 < end-start){
			medianOf3(array, start, end);
			pivotPosn = partition(array, start, end);
			quickSort(array, start, pivotPosn);
			quickSort(array, pivotPosn+1, end);
		}
	} // end of quickSort(...) helper method

	// This method chooses a pivot from the items in position start to end-1 using the median-of-three method,
	// and swap the chosen pivot into the position start of the array
	private static void medianOf3(int[] array, int start, int end){
		int mid = (start+end-1)/2;
		if(array[end-1] < array[start]){
			swap(array, start, end-1);
		}
		if(array[mid] < array[start]){
			swap(array, start, mid);
		}
		if(array[end-1] < array[mid]){
			swap(array, mid, end-1);
		}
	} // end of medianOf3(...) helper method

	// This method partitions the items in positions start to end-1 with the chosen pivot, returns the final position of the pivot after the partition
	private static int partition(int[] array, int start, int end){
		int bigStart = start+1;
		int pivot = array[start];
		for(int curr=start+1; curr<end; curr++){
			if(array[curr] < pivot){
				swap(array, bigStart, curr);
				bigStart++;
			}
		}
		swap(array, start, bigStart-1);
		return bigStart-1;
	} // end of partition(...) helper method

	/** Recursive hybrid quick sort algorithm */
	// Hybrid quick sort main driver method
	public static void hybridQuickSort(int[] array){
		hybridQuickSort(array, 0, array.length);
	} // end of hybridQuickSort(...) main driver method

	// This method recursively sort the array from position start to end-1 using the both the insertion sort algorithm and the quick sort algorithm
	private static void hybridQuickSort(int[] array, int start, int end ){
		int pivotPosn;

		if(end-start < BREAKPOINT){
			insertionSort(array, start, end);
		}
		else{
			medianOf3(array, start, end);
			pivotPosn = partition(array, start, end);
			hybridQuickSort(array, start, pivotPosn);
			hybridQuickSort(array, pivotPosn+1, end);
		}
	} // end of private hybridQuickSort(...) helper method

	/** Shell sort algorithm using Knuth's sequence for gap */
	// Shell sort main driver method
	public static void shellSort(int[] array){
		int h = 1;
		int temp;
		while(h <= array.length){
			h = h*3+1; // Knuth's sequence
		}

		while(h>0){ // 1st while loop
			for(int outer=h; outer<array.length; outer++){
				temp = array[outer];
				int inner = outer;
				while (inner > h-1 && array[inner-h] >= temp){ // 2nd while loop
					array[inner] = array[inner-h];
					inner -= h;
				} // end of 2nd while loop
				array[inner] = temp;
			} // end for loop
			h = (h-1)/3; // Knuth's sequence
		} // end of 1st while loop
	} // end of shellSort(...) main driver method

/****************** Other miscellaneous methods ********************/

    /*******************************************************************
     * swap
     *
     * Purpose: Swap the items stored in positions i and j in array.
     *
     ******************************************************************/
    private static void swap( int[] array, int i, int j ) {
		int temp = array[ i ];
		array[ i ] = array[ j ];
		array[ j ] = temp;
    } // end swap


    /*******************************************************************
     * isSorted
     *
     * Purpose: Return true if the input array is sorted into
     *          ascending order; return false otherwise.
     *
     * Idea: If every item is <= to the item immediately after it,
     *       then the whole list is sorted.
     *
     ******************************************************************/
    public static boolean isSorted( int[] array ) {
		boolean sorted = true;

		// Loop through all adjacent pairs in the
		// array and check if they are in proper order.
		// Stops at first problem found.
		for ( int i = 1; sorted && (i < array.length); i++ )
		    sorted = array[i-1] <=  array[i];
		return sorted;
    } // end method isSorted

    /*******************************************************************
     * checkArray
     *
     * Purpose: Print an error message if array is not
     *          correctly sorted into ascending order.
     *          (If the array is correctly sorted, checkArray does nothing.)
     *
     ******************************************************************/
    private static void checkArray(int[] array, String sortType) {
		if ( !isSorted( array ) )
		    System.out.println( sortType + " DID NOT SORT CORRECTLY *** ERROR!!" );
    }

    /*******************************************************************
     * fillArray
     *
     * Purpose: Fills the given array with the numbers 0 to array.length-1.
     *
     ******************************************************************/
    public static void fillArray( int[] array ) {

		for ( int i = 0; i < array.length; i++ ) {
		    array[i] = i;
		} // end for

    } // end fillArray

    /*******************************************************************
     * randomizeArray
     *
     * Purpose: Does numberOfSwaps swaps of randomly-chosen positions
     *          in the given array.
     *
     ******************************************************************/
    public static void randomizeArray( int[] array, int numberOfSwaps ) {
		for ( int count = 0; count < numberOfSwaps; count++ ) {
		    int i = generator.nextInt( array.length );
		    int j = generator.nextInt( array.length );
		    swap( array, i, j );
		}
    } // end randomizeArray


    /*******************************************************************
     * arithmeticMean
     *
     * Purpose: Compute the average of long values.
     *          To avoid long overflow, use type double in the computation.
     *
     ******************************************************************/
    public static double arithmeticMean(long data[]) {
		double sum = 0;
		for (int i = 0; i < data.length; i++)
		    sum += (double)data[i];
		return sum / (double)data.length;
    } // end arithmeticMean

} // end class LeTomA2Q1

/** Output
 *
 * COMP 2140 A2Q1 Sorting Test --- Summer 2020
 *
 * Array size: 10000
 * Number of swaps: 5000
 * Number of trials of each sort: 300
 *
 * Insertion sort mean: 8860249.666666666 ns
 * Bubble sort mean: 7.448656966666667E7 ns
 * Selection sort mean: 1.9604145666666668E7 ns
 * Merge sort mean: 759670.6666666666 ns
 * Quick sort mean: 452965.3333333333 ns
 * Hybrid quick sort mean: 368903.3333333333 ns
 * Shell sort mean: 645580.6666666666 ns
 *
 * Processing ends normally
 *
 *
 * Process finished with exit code 0
 *
 */

/** Report
 *
 *  1. Yes the insertion was faster than the selection sort by a large margin as seen in the above output. Even though they are both linear sorting algorithms,
 *  the reason insertion sort was faster was that every time a new item was added, it would be placed in its correct position. This resulted in the time require
 *  to sort the next newly added items to be much less than other two linear sorting algorithms.
 *
 *  2. Yes the quick sort was than the insertion sort as seen in the above output. The is due to the quick sort being a much more complex algorithm with an average
 *  case runtime complexity of O(N*logN) compare to insertion sort's O(N^2). If the method for choosing pivot is effective such as the median-of-three method, the
 *  worst case runtime complexity for quick sort can be avoided, thus making it to be more effective in most cases than the insertion sort.
 *
 *  3. Yes the hybrid quick sort was even faster than the quick sort as seen in the above output. This was due to the algorithm being the "upgraded" version of the quick
 *  sort algorithm by brilliantly making use of a BREAKPOINT variable to determine when the data was small enough (in this case less than 50 items) to use the less complex
 *  insertion sort to sort it rather than sorting it normally using quick sort.
 *
 *  4. I would recommend the following sorting algorithm to others: both the hybrid/normal quick sort, and the merge sort. As seen in the output, and given their average
 *  case runtime complexity of O(N*logN), their performance were relatively fast and effective. The shell sort using Knuth's sequence might also be suggested given its
 *  performance, according to the output, was faster than the merge sort.
 *
 *  5. I would warn others against using either the selection sort or the bubble sort or even the insertion short algorithm if they are trying to sort a large amount of
 *  data. The problem is that while they are less complex than other algorithms, they have a high average case runtime complexity of O(N^2), thus making them very
 *  ineffective for sorting large amount of data.
 *
 */
