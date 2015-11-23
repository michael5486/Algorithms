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


    public int[] convertListToArray(ArrayList<Integer> list) {

    	int[] tempArray = new int[list.size()];
    	for (int i = 0; i < list.size(); i++) {
    		tempArray[i] = list.get(i);
    	}
    	return tempArray;


    }

	public int[][] computeTours (int m, Pointd[] points) {

		//this algorithm will divide the # of points by the # of salesmen
		//it will split up the remainders

		//int[][] salesmanArray = new int[m][0];
		//ArrayList<Integer>[] salesmanArrayList = new ArrayList<Integer>()[m];
		System.out.println("points.length: "  + points.length);
		System.out.println("m: " + m);

		System.out.println("points: ");
		for (int i = 0; i < points.length; i++) {
			System.out.println(i + ":    " + points[i].toString());

		}

		
		//for (int i = 0; i < points.length; i++) {

		//	int[i % 2][points]
		//}

		int[] countArray = new int[m]; //makes an array to find the sizes for each salesman
		for (int i = 0; i < m; i++) {
			countArray[i] = 0; //initializes each element to 0

		}
		for (int j = 0; j < points.length; j++) {
			countArray[j % m]++;

		}
		for (int k = 0; k < m; k++) {
			System.out.println("Path length for salesman[" + i + "] " + countArray[k]);
		}

		//now the arrays have the right sizes

		int[][] salesmanArray = new int[m][m];
		for (int i = 0; i < m; i++) {
			salesmanArray[i] = new int[countArray[i]]; //initializes each row in salesMan array to the correct size
		}

		int rowLoc = 0, columnLoc = 0, rowMax = m, columnMax = countArray[0];
		int countArrayLoc = 0;
		
		for (int i = 0; i < points.length; i++) {

			System.out.println(rowLoc + ", " + columnLoc);
			salesmanArray[rowLoc][columnLoc] = i;

			columnLoc++;

			if (columnLoc == columnMax) {
				if (countArrayLoc < m - 1) {
					countArrayLoc++;
					columnMax = countArray[countArrayLoc];
					System.out.println(" hit");
					columnLoc = 0;
					rowLoc++;
				}
			}
			if (rowLoc == rowMax) {
				System.out.println("finished");
				break;
				
			}


		}

		/*while (rowLoc < rowMax) {

			while (columnLoc < columnMax) {


			}


		}*/



		/*int columnLoc = 0, rowLoc = 0;

		for (int i = 0; i < points.length; i++) {

			for (int j = i)


		}*/



		



		return salesmanArray;
	}




	public static void main(String[] args) {




	}

}