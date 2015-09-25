import edu.gwu.algtest.*;
import edu.gwu.debug.*;
import edu.gwu.util.*;

public class InsertionSort implements SortingAlgorithm {

	public int[] createSortIndex(java.lang.Comparable[] data) {
		return null;
	}

	public int[] createSortIndex(int[] data) {
		return null;
	}

	public void sortInPlace(java.lang.Comparable[] data) {

	}

	public void sortInPlace(int[] data) {
		
		//The first for loop iterates through the entire array, storing each number in the variable "temp"
		for (int i = 1; i < data.length; i++) {
			int temp = data[i];
			int j;

		/*The second for loop takes each number from the first for loop
		and keeps swapping the number with the previous numbers. It will run
		until temp is swapped into a location where it is larger than the previous numbers and 
		smaller than the following numbers in the array.*/
			for (j = i - 1; j >= 0 && temp < data[j]; j--) {
				data[j + 1] = data[j];
			}
			data[j + 1] = temp;
			
		//This code prints out the sorted array after each iteration of the outside for loop. It was used in debugging purposes, to see where/how my sorting code went wrong.

		/*System.out.println("Sorted Array, pass: " + i);
    	for (int k = 0; k < 10; k++) {
    		System.out.println(data[k]);
    	}*/
		}
	}

	/*public String getName() {
		return "michael5486's implementation of InsertionSort";
	}

	public void setPropertyExtractor (int algID, PropertyExtractor props) {

    }

	public java.lang.Comparable successor(Comparable key) {
    	return null;
    }*/


    public static void main(String[] args) {

       	/*This code was used to test my sort method. It creates an array filled with 10 random numbers from 0 to 100 and calls the sortInPlace method.
	
    	InsertionSort sort = new InsertionSort();
    	int[] testArray = new int[10];
    	for(int i = 0; i < 10; i++) {
    		testArray[i] = (int)(Math.random() * 100);
    	}
    	for (int i = 0; i < 10; i++) {
    		System.out.println(testArray[i]);
    	}
    	sort.sortInPlace(testArray)*/
    }
}