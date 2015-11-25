import edu.gwu.algtest.*;
import edu.gwu.util.*;
import edu.gwu.geometry.*;
import java.util.ArrayList;

public class Naive implements MTSPAlgorithm{


	public String getName() {
		return "michael5486's implementation of NaiveMTSP";
	}

	public void setPropertyExtractor (int algID, PropertyExtractor props) {

    }


    public void printSalesmanArray(int[][] array) {


    	for (int i = 0; i < array.length; i++) {

    		System.out.print("   ");
    		for (int j = 0; j < array[i].length; j++) {
    			System.out.print(array[i][j] + ", ");
    		}
    		System.out.print("\n");

    	}
    }

    //Naive algorithm divides the points as evenly as possible among the salesmen

	public int[][] computeTours (int m, Pointd[] points) {

		//System.out.println("points.length: "  + points.length);
		//System.out.println("m: " + m);

		System.out.println("points: ");
		/*for (int i = 0; i < points.length; i++) {
			System.out.println(i + ":    " + points[i].toString());

		}*/

		int[] countArray = new int[m]; //makes an array to find the sizes for each salesman
		for (int i = 0; i < m; i++) {
			countArray[i] = 0; //initializes each element to 0

		}
		for (int j = 0; j < points.length; j++) {
			countArray[j % m]++;

		}
		/*for (int k = 0; k < m; k++) {
			System.out.println("Path length for salesman[" + k + "]  " + countArray[k]);
		}*/

		//now the arrays have the right sizes

		int[][] salesmanArray = new int[m][m];
		for (int i = 0; i < m; i++) {
			salesmanArray[i] = new int[countArray[i]]; //initializes each row in salesMan array to the correct size
		}

		int rowLoc = 0, columnLoc = 0, rowMax = m, columnMax = countArray[0];
		int countArrayLoc = 0;
		
		for (int i = 0; i < points.length; i++) {

			//System.out.println(rowLoc + ", " + columnLoc);
			salesmanArray[rowLoc][columnLoc] = i;
			columnLoc++;

			if (columnLoc == columnMax) {
				if (countArrayLoc < m - 1) {
					countArrayLoc++;
					columnMax = countArray[countArrayLoc];
					//System.out.println(" hit");
					columnLoc = 0;
					rowLoc++;
				}
			}
			/*if (rowLoc == rowMax) {
				System.out.println("finished");
				break;
				
			}*/

		}

		printSalesmanArray(salesmanArray);
		return salesmanArray;
	}




	public static void main(String[] args) {

	}

}