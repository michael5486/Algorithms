import edu.gwu.algtest.*;
import edu.gwu.util.*;
import edu.gwu.geometry.*;
import java.util.ArrayList;

public class Greedy implements MTSPAlgorithm{

	public String getName() {
		return "michael5486's implementation of GreedyMTSP";
	}

	public void setPropertyExtractor (int algID, PropertyExtractor props) {

    }

    public int isThereAnEmptySalesman(ArrayList<ArrayList<Integer>> arrayList) { //determines if there is a salesman without any assigned points

    	for (int i = 0; i < arrayList.size(); i++) {
    		if (arrayList.get(i).isEmpty()) {
    			return i;
    		}
    	}

    	return -1;
    	
    }

    public void printSalesmen(ArrayList<ArrayList<Integer>> arrayList) { //prints the salesmen and assigned points

    	for (int i = 0; i < arrayList.size(); i++) {

    		System.out.print("Salesman " + i + "   ");
    		System.out.print(arrayList.get(i).toString());
    		System.out.print("\n");

    	}
    }

    public double calcDistance(int start, int end, Pointd[] points) { //calculates the distance between two points

    	Pointd point1 = points[start];
    	Pointd point2 = points[end];
    	double x1 = point1.x, y1 = point1.y, x2 = point2.x, y2 = point2.y;

    	double temp = (x2 - x1)* (x2 - x1) + (y2 - y1) * (y2 - y1);
    	double distance = Math.sqrt(temp);
    	//System.out.println("Distance " + start + ", " + end + ": " + distance);

    	return distance;
    }

    public int findClosestSalesman(ArrayList<ArrayList<Integer>> arrayList, int start, Pointd[] points) { //finds the closest salesman to an inputted point

    	double minDistance = 100; //start with a number greater than the expected minDistance
    	int minSalesman = 0; //the number of the closest salesman

    	for (int i = 0; i < arrayList.size(); i++) {

    		for (int j = 0; j < arrayList.get(i).size(); j++) { //iterates through every point for every salesman
    			double distance = calcDistance(start, arrayList.get(i).get(j), points);

    			if (distance < minDistance) {
    				//System.out.println("Found new min distance: " + start + ", " + arrayList.get(i).get(j));
    				minDistance = distance;
    				minSalesman = i;
    			}

    		}

    	}

    	//System.out.println("minDistance: " + minDistance + " minSalesman: " + minSalesman);
    	return minSalesman;

    }

    public int[][] convertToIntArray(ArrayList<ArrayList<Integer>> arrayList) { //converts the arrayList with all salesmen/point data into an int[][]

    	int[][] array = new int[arrayList.size()][];
    	for (int i = 0; i < arrayList.size(); i++) {
    		array[i] = new int[arrayList.get(i).size()];
    	}

    	for (int i = 0; i < arrayList.size(); i++) {

    		for (int j = 0; j < arrayList.get(i).size(); j++) {
    			array[i][j] = arrayList.get(i).get(j);

    		}
    	}

    	return array;

    }


    //creates an ArrayList of ArrayListsLists that store the points allocated to each salesman
    //if a salesman has no points allocated, then give him one
    //for each point after that, iterate through every salesman's points, finding the one with the closest point, because it means his travel time to that point will be the shortest
    //when that is all done, convert the ArrayLists into an int array to return in computeTours
	public int[][] computeTours (int m, Pointd[] points) {

		ArrayList<ArrayList<Integer>> salesmenArrayList = new ArrayList<ArrayList<Integer>>();

		for (int i = 0; i < m; i++) { //for each salesman, create a linkedList
			ArrayList<Integer> temp = new ArrayList<Integer>();
			salesmenArrayList.add(temp);
		}

		for (int i = 0; i < points.length; i++) {

			int num = isThereAnEmptySalesman(salesmenArrayList);
			if (num != -1) { //adds a point to each salesman
				//System.out.println("Empty salesman #" + num);
				salesmenArrayList.get(num).add(i);
			}
			else { //need to find the salesman with the closes point to a given point
				int salesman = findClosestSalesman(salesmenArrayList, i, points);
				//System.out.println("adding to salesman " + salesman);
				salesmenArrayList.get(salesman).add(i);

			}

		}

		int[][] toReturn = convertToIntArray(salesmenArrayList);

		//printSalesmen(salesmenArrayList);

		return toReturn;
	}

	public static void main(String[] args) {

	}

}