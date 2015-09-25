import edu.gwu.algtest.*;
import edu.gwu.debug.*;
import edu.gwu.util.*;


public class MergeSortOpt implements SortingAlgorithm {



	public int[] createSortIndex(java.lang.Comparable[] data) {
		return null;
	}

	public int[] createSortIndex(int[] data) {
		return null;
	}

int min(int a, int b) {
  if (a < b) {
    return a;
  }
  else {
    return b;
  }

}



private void InsertionSort(int[] data, int left, int end) {
    //This is the code from my InsertionSort from Exercise 1. THe start and end have been modified to work when implemented in MergeSort
    for (int i = left + 1; i < end; i++) {
      int temp = data[i];
      int j;
      for (j = i - 1; j >= 0 && temp < data[j]; j--) {
        data[j + 1] = data[j];
      }
      data[j + 1] = temp;
    }
  }

  private void InsertionSort(java.lang.Comparable[] data, int left, int end) {
    
    //This is the code from my InsertionSort from Exercise 1. THe start and end have been modified to work when implemented in MergeSort
    for (int i = left + 1; i < end; i++) {
      java.lang.Comparable temp = data[i];
      int j;
      for (j = i - 1; j >= 0 && temp.compareTo(data[j]) < 0; j--) {
        //System.out.println("temp: " + temp + " data[j]: " + data[j]);
        data[j + 1] = data[j];
      }
      data[j + 1] = temp;
    }
  }


private void Merge(java.lang.Comparable[] A, int iLeft, int iRight, int iEnd, java.lang.Comparable[] B) {

  int i0 = iLeft;
  int i1 = iRight;
  int j;
  /*if (iEnd - iLeft < 20) { //implement small-size cutoff implementation
    //For groups less than 20, use insertionsort
  //  System.out.println("Using Insertion Sort!!!!!!!");
    InsertionSort(A, iLeft, iEnd);
  }*/

  // While there are elements in the left or right groups
  for (j = iLeft; j < iEnd; j++)
    {
      // If the left run head exists and is less than or equal to the right group's pointer
      if (i0 < iRight && (i1 >= iEnd || A[i0].compareTo(A[i1]) <= 0))
        {
          B[j] = A[i0];
          i0 = i0 + 1;
        }
      else
        {
          B[j] = A[i1];
          i1 = i1 + 1;
        }
    }
}

private void Merge(int[] A, int iLeft, int iRight, int iEnd, int[] B) {

  int i0 = iLeft;
  int i1 = iRight;
  int j;

  /*if (iEnd - iLeft < 20) { //implement small-size cutoff implementation
    //For groups less than 20, use insertionsort
    InsertionSort(A, iLeft, iEnd);
  }*/

  /* While there are elements in the left or right runs */
  for (j = iLeft; j < iEnd; j++)
    {
      /* If left run head exists and is <= existing right run head */
      if (i0 < iRight && (i1 >= iEnd || A[i0] <= A[i1]))
        {
          B[j] = A[i0];
          i0 = i0 + 1;
        }
      else
        {
          B[j] = A[i1];
          i1 = i1 + 1;
        }
    }
}

	public void sortInPlace(java.lang.Comparable[] data) {

    java.lang.Comparable [] B = new java.lang.Comparable[data.length];
    int width, n = data.length, i;
    for (width = 1; width < n; width = 2 * width) {
      for (i = 0; i < n; i = i + 2 * width) {
        Merge(data, i, min(i + width, n), min(i + 2*width, n), B);
      }

     //two arrays should be successfully merged. This loop sets data equal to sortedArray
      for (int j = 0; j < data.length; j++) {
       data[j] = B[j];
      }

    }
      print(data);
      print(B);
	}

	public void sortInPlace(int[] data) {

    int [] B = new int[data.length];
    int width, n = data.length, i;
    for (width = 1; width < n; width = 2 * width) {
      for (i = 0; i < n; i = i + 2 * width) {


        Merge (data, i, min(i + width, n), min(i + 2*width, n), B);
      }

     //two arrays should be successfully merged. This loop sets data equal to sortedArray
      for (int j = 0; j < data.length; j++) {
       data[j] = B[j];
      }
    }
	}
	

	public String getName() {
		return "michael5486's non-recursive implementation of the optimized MergeSort";
	}

	public void setPropertyExtractor (int algID, PropertyExtractor props) {
  
  }

  public void print(int[] array) {
    System.out.print("\nArray: ");
    for (int i = 0; i < array.length; i++) {
      System.out.print(" " + array[i] + " ");
    }
    System.out.println("");
  }

    public void print(java.lang.Comparable[] array) {
    System.out.print("\nArray: ");
    for (int i = 0; i < array.length; i++) {
      System.out.print(" " + array[i] + " ");
    }
    System.out.println("");
  }


public static void main(String[] args) {

    	//This code was used to test my sort method. It creates an array filled with 10 random numbers from 0 to 100 and calls the sortInPlace method.
      MergeSort sort = new MergeSort();
      int[] testArray = new int[10];
      int[] testArray2 = new int[100];
      for(int i = 0; i < testArray.length; i++) {
        testArray[i] = (int)(Math.random() * 100);
      }
      for(int i = 0; i < testArray2.length; i++) {
        testArray2[i] = (int)(Math.random() * 100);
      }
      System.out.println("testArray");
      sort.print(testArray);
      sort.sortInPlace(testArray);
      sort.print(testArray);
      System.out.println("testArray2");
      sort.print(testArray2);
      sort.sortInPlace(testArray2);
      sort.print(testArray2);
  }
}