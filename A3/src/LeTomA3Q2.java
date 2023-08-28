//==============================================================
// LeTomA3Q2.java
//
// COMP 2140 SUMMER 2020 D01
// ASSIGNMENT 3 QUESTION 2
// PROVIDED TEMPLATE
//
// PURPOSE: Compare the performace of three dictionaries (ordered
//          array, open addressing hash table, separate chaining
//          hash table).
//==============================================================

import java.io.*;
import java.util.*;
import java.lang.Math;

//==========================================================
// LeTomA3Q2 class (main) --- MODIFY TO INCLUDE YOUR NAME BUT OTHERWISE DO NOT CHANGE THIS CLASS
//
// PURPOSE: Compare performance of three dictionaries. Given
//          a text file, time how long it takes to fill each
//          dictionary, one word at a time. Time how long it
//          takes to search each dictionary for a given set
//          of words.
//==========================================================

public class LeTomA3Q2 {

    public static void main(String[] args){

		String inputFile = "GreatExpectations.txt";
		String searchFile = "A3Q2TestWords.txt";

		DictionaryOrdered allWordsOrdered; //complete dictionary, using an ordered array
		DictionaryOpen allWordsOpen; //complete dictionary, using open addressing
		DictionaryChain allWordsChain; //complete dictionary, using separate chaining

		long startTime, endTime, elapsedTime;

		//Fill the dictionaries
		System.out.println("\nFilling ordered array dictionary...");
		startTime = System.nanoTime();
		allWordsOrdered = new DictionaryOrdered(100);
		buildOrdered(allWordsOrdered, inputFile);
		endTime = System.nanoTime();
		elapsedTime = endTime - startTime;
		System.out.println("The time to fill the ordered array dictionary with " + allWordsOrdered.getSize() + " words was: " + elapsedTime + " ns.");

		System.out.println("\nFilling open addressing dictionary...");
		allWordsOpen = new DictionaryOpen(100);
		startTime = System.nanoTime();
		buildOpen(allWordsOpen, inputFile);
		endTime = System.nanoTime();
		elapsedTime = endTime - startTime;
		System.out.println("The time to fill the open addressing dictionary with " + allWordsOpen.getSize() + " words was: " + elapsedTime + " ns.");

		System.out.println("\nFilling separate chaining dictionary...");
		allWordsChain = new DictionaryChain(100);
		startTime = System.nanoTime();
		buildChain(allWordsChain, inputFile);
		endTime = System.nanoTime();
		elapsedTime = endTime - startTime;
		System.out.println("The time to fill the open addressing dictionary with " + allWordsChain.getSize() + " words was: " + elapsedTime + " ns.");

		//Search the dictionaries
		System.out.println("\nSearching ordered array dictionary...");
		startTime = System.nanoTime();
		searchOrdered(allWordsOrdered, searchFile);
		endTime = System.nanoTime();
		elapsedTime = endTime - startTime;
		System.out.println("The time to search the ordered array dictionary was: " + elapsedTime + " ns.");

		System.out.println("\nSearching open addressing dictionary...");
		startTime = System.nanoTime();
		searchOpen(allWordsOpen, searchFile);
		endTime = System.nanoTime();
		elapsedTime = endTime - startTime;
		System.out.println("The time to search the open addressing dictionary was: " + elapsedTime + " ns.");

		System.out.println("\nSearching separate chaining dictionary...");
		startTime = System.nanoTime();
		searchChain(allWordsChain, searchFile);
		endTime = System.nanoTime();
		elapsedTime = endTime - startTime;
		System.out.println("The time to search the separate chaining dictionary was: " + elapsedTime + " ns.");

		System.out.println("\nEnd of Processing.");
    }//end main

    //==========================================================
    // buildOrdered
    //
    // PURPOSE: Fill the given dictionary with the given text.
    //
    // PARAMETERS:
    //   allWordsOrdered - the dictionary to be filled
    //   inputFile - the file containing words to add to dictionary
    //
    // RETURNS:
    //   none
    //==========================================================
    static void buildOrdered(DictionaryOrdered allWordsOrdered, String inputFile){
		String temp;
		String[] words;

		try{
			BufferedReader buff = new BufferedReader(new FileReader(new File(inputFile)));

			temp = buff.readLine();
		    while(temp != null){
				words = temp.split("[.,;: --\"?]");

				for (int i=0; i<words.length; i++)
			      allWordsOrdered.insert(words[i]);

				temp = buff.readLine();
	    	}

		}//end try
		catch (IOException e){
		    System.out.println("File I/O Error: " + inputFile);
		}

    }//end buildOrdered

    //==========================================================
    // buildOpen
    //
    // PURPOSE: Fill the given dictionary with the given text.
    //
    // PARAMETERS:
    //   allWordsOpen - the dictionary to be filled
    //   inputFile - the file containing words to add to dictionary
    //
    // RETURNS:
    //   none
    //==========================================================
    static void buildOpen(DictionaryOpen allWordsOpen, String inputFile){
		String temp;
		String[] words;

		try{
			BufferedReader buff = new BufferedReader(new FileReader(new File(inputFile)));

			temp = buff.readLine();
		    while(temp != null){
				words = temp.split("[.,;: --\"?]");

				for (int i=0; i<words.length; i++)
			      allWordsOpen.insert(words[i]);

				temp = buff.readLine();
		    }

		}//end try
		catch (IOException e){
		    System.out.println("File I/O Error: " + inputFile);
		}

    }//end buildOpen

    //==========================================================
    // buildChain
    //
    // PURPOSE: Fill the given dictionary with the given text.
    //
    // PARAMETERS:
    //   allWordsChain - the dictionary to be filled
    //   inputFile - the file containing words to add to dictionary
    //
    // RETURNS:
    //   none
    //==========================================================
    static void buildChain(DictionaryChain allWordsChain, String inputFile){
		String temp;
		String[] words;

		try{
			BufferedReader buff = new BufferedReader(new FileReader(new File(inputFile)));

			temp = buff.readLine();
		    while(temp != null){
				words = temp.split("[.,;: --\"?]");

				for (int i=0; i<words.length; i++)
			      allWordsChain.insert(words[i]);

				temp = buff.readLine();
		    }

		}//end try
		catch (IOException e){
		    System.out.println("File I/O Error: " + inputFile);
		}

    }//end buildChain

    //==========================================================
    // searchOrdered
    //
    // PURPOSE: Search the given dictionary for the given words.
    //
    // PARAMETERS:
    //   allWordsOrdered - the dictionary to be searched
    //   searchFile - the file containing words to look for
    //
    // RETURNS:
    //   none (prints number of words found)
    //==========================================================
    static void searchOrdered(DictionaryOrdered allWordsOrdered, String searchFile){
		String temp;
		int numFound=0;
		int numMissing=0;

		try{

		    Scanner scnner = new Scanner(new File(searchFile));
		    while(scnner.hasNext()){

				temp = scnner.next();
				if (allWordsOrdered.search(temp))
				    numFound++;
				else
				    numMissing++;
		    }
		    System.out.println("Number of words found = " + numFound + ". Number of words not found = " + numMissing + ".");

		}//end try
		catch (IOException e){
		    System.out.println("File I/O Error: " + searchFile);
		}

    }//end searchOrdered

    //==========================================================
    // searchOpen
    //
    // PURPOSE: Search the given dictionary for the given words.
    //
    // PARAMETERS:
    //   allWordsOpen - the dictionary to be searched
    //   searchFile - the file containing words to look for
    //
    // RETURNS:
    //   none (prints number of words found)
    //==========================================================
    static void searchOpen(DictionaryOpen allWordsOpen, String searchFile){
		String temp;
		int numFound=0;
		int numMissing=0;

		try{

		    Scanner scnner = new Scanner(new File(searchFile));
		    while(scnner.hasNext()){

				temp = scnner.next();
				if (allWordsOpen.search(temp))
				    numFound++;
				else
				    numMissing++;
		    }
		    System.out.println("Number of words found = " + numFound + ". Number of words not found = " + numMissing + ".");

		}//end try
		catch (IOException e){
		    System.out.println("File I/O Error: " + searchFile);
		}

    }//end searchOpen

    //==========================================================
    // searchChain
    //
    // PURPOSE: Search the given dictionary for the given words.
    //
    // PARAMETERS:
    //   allWordsChain - the dictionary to be searched
    //   searchFile - the file containing words to look for
    //
    // RETURNS:
    //   none (prints number of words found)
    //==========================================================
    static void searchChain(DictionaryChain allWordsChain, String searchFile){
		String temp;
		int numFound=0;
		int numMissing=0;

		try{

		    Scanner scnner = new Scanner(new File(searchFile));
		    while(scnner.hasNext()){

				temp = scnner.next();
				if (allWordsChain.search(temp))
				    numFound++;
				else
				    numMissing++;
		    }
		    System.out.println("Number of words found = " + numFound + ". Number of words not found = " + numMissing + ".");

		}//end try
		catch (IOException e){
		    System.out.println("File I/O Error: " + searchFile);
		}

    }//end searchChain

}//end class LeTomA3Q2

//==============================================================
// DictionaryOrdered class
//
// PURPOSE: Store a list of words, in an ordered array.
//
// PUBLIC METHODS: - constructor: public Dictionary(int size)
//                 - public int getSize() - return the current number of words
//                 - public void insert(String newWord) - insert new word in list
//                 - public boolean search(String wordToFind) - search for
//                   given word and return true if found
// FOR TESTING: public void print() - print contents of dictionary
//==============================================================
class DictionaryOrdered{
	private int size;
	private String[] arr;

	// default constructor
	public DictionaryOrdered(int size){
		this.size = 0;
		arr = new String[size];
	}

	// Accessors
	public int getSize(){
		return size;
	}

	/** Private methods */
	// This method doubles the size of the array, privately called by insert(...) method
	private void doubleSize(){
		String[] newArr = new String[arr.length*2];
		// deep copy the original array into the new array
		for(int i=0; i<arr.length; i++){
			newArr[i] = arr[i];
		}
		// the original array is now double sized
		arr = newArr;
	} // end of doubleSize() helper method

	/** Public methods */
	// This method inserts the given word into the dictionary if unique
	public void insert(String newWord){
		if(newWord.length() < 1){
			return;
		}
		newWord = newWord.toLowerCase();
		// checks if the newWord has not yet existed in the dictionary
		if(!search(newWord)){
			// add the newWord to the dictionary and increase the dictionary size
			arr[size] = newWord;
			size++;

			// insertion sort algorithm
			for (int i = 0; i < size; ++i) {
				int j = i;
				while (j > 0 && arr[j].compareToIgnoreCase(arr[j-1]) < 0) {
					String temp = arr[j];
					arr[j] = arr[j-1];
					arr[j-1] = temp;
					--j;
				} // end of while loop
			} // end of for loop

			// double the array size if needed
			if(size >= arr.length){
				doubleSize();
			}
		} // end of if the newWord has not yet existed in the dictionary
	} // end of insert(...) method

	// This method searches the dictionary for the given word, returns true if found or false otherwise
	public boolean search(String wordToFind){
		boolean result = false;
		// linear binary search
		for(int i=0; i<size; i++){
			if(arr[i].equalsIgnoreCase(wordToFind)){
				result = true;
			}
		}
		return result;
	} // end of search(...) method

	// This method prints all the words inside of the dictionary
	public void print(){
		for(int i=0; i<size; i++){
			System.out.println(arr[i]);
		}
	} // end of print() methods

}//end class DictionaryOrdered


//==============================================================
// DictionaryOpen class
//
// PURPOSE: Store a list of words, in a hash table using open addressing.
//
// PUBLIC METHODS: - constructor: public Dictionary(int size)
//                 - public int getSize() - return the current number of words
//                 - public void insert(String newWord) - insert new word in list
//                 - public boolean search(String wordToFind) - search for
//                   given word and return true if found
// FOR TESTING: public void print() - print contents of dictionary
//
//==============================================================
class DictionaryOpen{
	private int size;
	private String[] table;

	// default constructor
	public DictionaryOpen(int size){
		this.size = 0;
		while(!isPrime(size)){
			size++;
		}
		table = new String[size];
	}

	// Accessors
	public int getSize(){
		return size;
	}

	/** Private methods */
	// This method returns true if the given num is a prime, false otherwise
	private boolean isPrime(int num) {
		boolean result = true;

		// check if num is greater than 1
		if(num > 1){
			for (int i = 2; i*i <= num; i++) {
				// check if num can be divided by i, num is not prime if so
				if (num % i == 0) {
					result = false;
				}
			} // end of for loop
		}
		// else num is not prime
		else{
			result = false;
		}

		return result;
	} // end of isPrime(...) helper method

	// This method enlarges the hash table
	private void enlarge(){
		int newLength = table.length*2;
		while(!isPrime(newLength)){
			newLength++;
		}
		String[] newTable = new String[newLength];

		for(int i=0; i<table.length; i++){
			if(table[i] != null){
				hashInsert(newTable, table[i]);
			}
		}

		// the table is now enlarged
		table = newTable;
	} // end of enlarge() helper method

	// This method checks if the given char is a letter
	private boolean isLetter(char c){
		boolean result = true;

		if((int) c < 97 || (int) c > 122){
			result = false;
		}

		return result;
	} // end of isLetter(...) helper method

	// This method calculates and returns the hash value of the string using Horner's method
	private int hashCode(String str, int length){
		int a = 27;
		int result = 0;

		for(int i=0; i<str.length()-1; i++){
			if(isLetter(str.charAt(i))){
				result = Math.abs((result + ((int) str.charAt(i) - 96))*a) % length;
			}
		} // end of for loop
		if(isLetter(str.charAt(str.length()-1))){
			result = Math.abs(result+ ((int) str.charAt(str.length()-1) - 96))% length;
		}

		return result;
	} // end of hashCode(...) helper method

	// This method calculates and returns the step size
	private int stepSize(String str){
		int c = 41;
		int sumOfChars = 0;
		int result = 0;

		// calculate the sum of characters
		for(int i=0; i<str.length()-1; i++){
			if(isLetter(str.charAt(i))){
				sumOfChars += (int) str.charAt(i) - 96;
			}
		} // end of for loop

		result = c - (sumOfChars % c);

		return result;
	} // end of stepSize(...) helper method

	// This method resolves any collision and inserts the newWord into the given array
	private void hashInsert(String[] array, String newWord){
		int index = hashCode(newWord, array.length);

		// recalculate the index if a collision occurs
		while(array[index] != null){
			index = (index + stepSize(newWord)) % array.length;
		}

		// insert the index into the table
		array[index] = newWord;
	} // end of hashInsert(...) helper method

	/** Public methods */
	// This method inserts the given word into the table if unique
	public void insert(String newWord){
		if(newWord.length() < 1){
			return;
		}
		newWord = newWord.toLowerCase();
		// checks if the newWord has not yet existed in the dictionary
		if(!search(newWord)){
			hashInsert(table, newWord);

			// increase the dictionary size and enlarge the hash table if needed
			size++;
			if(size >= .6*table.length){
				enlarge();
			}
		} // end of if the newWord has not yet existed in the dictionary
	} // end of insert(...) method

	// This method searches the dictionary for the given word, returns true if found or false otherwise
	public boolean search(String wordToFind){
		wordToFind = wordToFind.toLowerCase();
		int index = hashCode(wordToFind, table.length);
		int stepSize = stepSize(wordToFind);
		int i=0;

		while(!wordToFind.equalsIgnoreCase(table[(index + i * stepSize)%table.length])){
			if (table[(index + i * stepSize)%table.length] == null) {
				return false;
			}
			i++;
		}

		return true;
	} // end of search(...) method

	// This method prints all the elements of the table
	public void print(){
		for(int i=0; i<table.length; i++){
			System.out.println(table[i]);
		}
	} // end of print() methods

}//end class DictionaryOpen


//==============================================================
// DictionaryChain class
//
// PURPOSE: Store a list of words, in a hash table using separate
//          chaining. Words are converted to lowercase as inserted.
//
// PUBLIC METHODS: - constructor: public Dictionary(int size)
//                 - public int getSize() - return the current number of words
//                 - public void insert(String newWord) - insert new word in list
//                 - public boolean search(String wordToFind) - search for
//                   given word and return true if found
// FOR TESTING: public void print() - print contents of dictionary
//
//==============================================================
class DictionaryChain{
	private int size;
	private LinkedList[] table;

	// default constructor
	public DictionaryChain(int size){
		this.size = 0;
		while(!isPrime(size)){
			size++;
		}
		table = new LinkedList[size];
	}

	// Accessors
	public int getSize(){
		return size;
	}

	/** Private methods */
	// This method returns true if the given num is a prime, false otherwise
	private boolean isPrime(int num) {
		boolean result = true;

		// check if num is greater than 1
		if(num > 1){
			for (int i = 2; i*i <= num; i++) {
				// check if num can be divided by i, num is not prime if so
				if (num % i == 0) {
					result = false;
				}
			} // end of for loop
		}
		// else num is not prime
		else{
			result = false;
		}

		return result;
	} // end of isPrime(...) helper method

	// This method enlarges the hash table
	private void enlarge(){
		int newLength = table.length*2;
		while(!isPrime(newLength)){
			newLength++;
		}
		LinkedList[] newTable = new LinkedList[newLength];

		for(int i=0; i<table.length; i++){
			if(table[i] != null){
				while (table[i].getSize() > 0){
					hashInsert(newTable, table[i].pop());
				}
			}
		}

		// the table is now enlarged
		table = newTable;
	} // end of enlarge() helper method

	// This method checks if the given char is a letter
	private boolean isLetter(char c){
		boolean result = true;

		if((int) c < 97 || (int) c > 122){
			result = false;
		}

		return result;
	} // end of isLetter(...) helper method

	// This method calculates and returns the hash value of the string using Horner's method
	private int hashCode(String str, int length){
		int a = 27;
		int result = 0;

		for(int i=0; i<str.length()-1; i++){
			if(isLetter(str.charAt(i))){
				result = Math.abs((result + ((int) str.charAt(i) - 96))*a) % length;
			}
		} // end of for loop
		if(isLetter(str.charAt(str.length()-1))){
			result = Math.abs(result+ ((int) str.charAt(str.length()-1) - 96))% length;
		}

		return result;
	} // end of hashCode(...) helper method

	// This method resolves any collision and inserts the newWord into the given array
	private void hashInsert(LinkedList[] array, String newWord){
		int index = hashCode(newWord, array.length);
		if(array[index] == null){
			array[index] = new LinkedList();
		}
		array[index].add(newWord);
	} // end of hashInsert(...) helper method

	/** Public methods */
	// This method inserts the given word into the dictionary if unique
	public void insert(String newWord){
		if(newWord.length() < 1){
			return;
		}
		newWord = newWord.toLowerCase();
		// checks if the newWord has not yet existed in the dictionary
		if(!search(newWord)){
			hashInsert(table, newWord);

			// increase the dictionary size and double the array size if needed
			size++;
			if(size >= 2*table.length){
				enlarge();
			}
		} // end of if the newWord has not yet existed in the dictionary
	} // end of insert(...) method

	// This method searches the dictionary for the given word, returns true if found or false otherwise
	public boolean search(String wordToFind){
		wordToFind = wordToFind.toLowerCase();
		int index = hashCode(wordToFind, table.length);
		if(table[index] == null){
			table[index] = new LinkedList();
		}
		return table[index].search(wordToFind);
	} // end of search(...) method

	// This method prints all the elements of the dictionary
	public void print(){
		for(int i=0; i<table.length; i++){
			System.out.println("Bucket " + i + " contents");
			if(table[i] != null){
				System.out.println(table[i].toString());
			}
		}
	} // end of print() methods

}//end class DictionaryChain

// LinkedList class
class LinkedList{
	// Node class
	class Node{
		private String data;
		private Node next;

		// default constructor
		public Node(String str, Node next){
			data = str;
			this.next = next;
		}

		// Accessors
		public Node getNext() {
			return next;
		}

		public String getData() {
			return data;
		}

		// Setters
		public void setNext(Node next) {
			this.next = next;
		}
	} // end of Node class

	// linked list variables
	private Node top;
	private int size;

	// default constructor
	public LinkedList(){
		top = null;
		size = 0;
	}

	// Accessors
	public int getSize() {
		return size;
	}

	/** Public methods */
	// This method adds the new string into the linked list
	public void add(String value){
		Node newNode = new Node(value, top);
		top = newNode;
		size++;
	} // end of add(...) method

	// This method returns the top data and removes it from the list
	public String pop(){
		String result = "";
		// check if the linked list is not null
		if(size > 0){
			result = top.getData();
			top = top.getNext();
			size--;
		}
		return result;
	} // end of pop() method

	// This method returns true if the given value is found inside the list or false otherwise
	public boolean search(String value){
		Node curr = top;
		while (curr != null){
			if(curr.getData().equalsIgnoreCase(value)){
				return true;
			}
			curr = curr.getNext();
		}
		return false;
	} // end of remove(...) method

	// This method returns a String of all the elements inside the list
	public String toString(){
		String result = "";
		Node curr = top;
		while (curr != null){
			result += curr.getData() + "\n";
			curr = curr.getNext();
		}

		return result;
	} // end of toString() method
} // end of LinkedList class




