import edu.gwu.algtest.*;
import edu.gwu.debug.*;
import edu.gwu.util.*;


public class SelectionSort implements SortingAlgorithm {



	public int[] createSortIndex(java.lang.Comparable[] data) {
		return null;
	}

	public int[] createSortIndex(int[] data) {
		return null;
	}

	public void sortInPlace(java.lang.Comparable[] data) {

	}

	public void sortInPlace(int[] data) {

      int i, j, index, temp;
      int n = data.length;
      for (i = 0; i < n - 1; i++) {
            index = i;
            for (j = i + 1; j < n; j++)
                  if (data[j] < data[index]) //this finds the greatest number with each iteration of the for loop
                        index = j;
       /*The following code swaps the number found by the previous loop with 
        another number in the array. It eventually put the highest number found
        in the for loop into it's correct location in the array*/ 
            
            if (index != i) {
                  temp = data[i]; 
                  data[i] = data[index];
                  data[index] = temp;
            }

          //The following code prints out the sorted array after each iteration of the outside for loop. It was used in debugging purposes, to see where/how my sorting code went wrong.

        	/*System.out.println("Sorted Array, pass: " + i);
    		  for (int k = 0; k < 10; k++) {
    			System.out.println(data[k]);
    		}*/
      	}
	}
	

	public String getName() {
		return "michael5486's implementation of SelectionSort";
	}

	public void setPropertyExtractor (int algID, PropertyExtractor props) {
  }

public static void main(String[] args) {

    	/*This code was used to test my sort method. It creates an array filled with 10 random numbers from 0 to 100 and calls the sortInPlace method.

    	SelectionSort sort = new SelectionSort();
    	int[] testArray = new int[10];
    	for(int i = 0; i < 10; i++) {
    		testArray[i] = (int)(Math.random() * 100);
    	}
    	for (int i = 0; i < 10; i++) {
    		System.out.println(testArray[i]);
    	}
    	sort.sortInPlace(testArray);*/	
    }
}