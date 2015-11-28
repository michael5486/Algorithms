import edu.gwu.algtest.*;
import edu.gwu.util.*;
import edu.gwu.geometry.*;
import java.util.ArrayList;
import java.util.Collections;

public class MyAlgorithm implements MTSPAlgorithm{

	public String getName() {
		return "michael5486's implementation of MyAlgorithmMTSP";
	}

	public void setPropertyExtractor (int algID, PropertyExtractor props) {

    }

    public void printSalesmen(ArrayList<ArrayList<Integer>> arrayList) {

    	for (int i = 0; i < arrayList.size(); i++) {

    		System.out.print("Salesman " + i + "   ");
    		System.out.print(arrayList.get(i).toString());
    		System.out.print("\n");

    	}
    }

    public double calcDistance(int start, int end, Pointd[] points) {

    	Pointd point1 = points[start];
    	Pointd point2 = points[end];
    	double x1 = point1.x, y1 = point1.y, x2 = point2.x, y2 = point2.y;


    	double temp = (x2 - x1)* (x2 - x1) + (y2 - y1) * (y2 - y1);
    	double distance = Math.sqrt(temp);

    	return distance;
    }

    public int findClosestSalesman(ArrayList<ArrayList<Integer>> arrayList, int start, Pointd[] points) { //finds the closes salesman with respect to the input point

    	double minDistance = 100;
    	int minSalesman = 0;

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

    public int[] fillWithValues(int m, Pointd[] points) {

    	ArrayList<Integer> array = new ArrayList<Integer>(); 

    	for (int i = 0; i < points.length; i++) {
    		array.add(i);

    	}
    	Collections.shuffle(array);

    	int[] intArray = new int[m];
    	for (int i = 0; i < m; i++) {
    		intArray[i] = array.get(i);

    	}

    	return intArray;

    }

    public double calcArrayDistance(int[] array, Pointd[] points) {
    	
    	double distance = 0;
    	for (int i = 0; i < array.length - 1; i++) {
    		distance += calcDistance(i, i + 1, points);

    	}
    	return distance;

    }

    public ArrayList<ArrayList<Integer>> findMaxDistance(int m, Pointd[] points) {

    	//creates an array with m random points

    	int[] randomPointArray;
    	double maxDistance = 0, tempDistance;
    	int[] maxArray = null;

    	//runs this 100 times, keeping track of the one that has the greatest distance

    	for (int i = 0; i < 100; i++) {

    		randomPointArray = fillWithValues(m, points);

    		tempDistance = calcArrayDistance(randomPointArray, points);
    		if (tempDistance > maxDistance) {
    			maxDistance = tempDistance;
    			maxArray = randomPointArray;

    		}

    	}

    	//turn maxArray into new ArrayList, adds point to each salesman

    	ArrayList<ArrayList<Integer>> newArrayList = new ArrayList<ArrayList<Integer>>();
    	for (int i = 0; i < m; i++) {
    		ArrayList<Integer> temp = new ArrayList<Integer>();
    		newArrayList.add(temp);
    		newArrayList.get(i).add(maxArray[i]);
	
    	}

    	return newArrayList;

    }

    public int[][] convertToIntArray(ArrayList<ArrayList<Integer>> arrayList) { //makes an int[][] equivalent to the arrayList

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

	public int[][] computeTours (int m, Pointd[] points) {


		ArrayList<ArrayList<Integer>> salesmenArrayList = findMaxDistance(m, points);

		for (int i = 0; i < points.length; i++) {

			//initializes each salesman with 1 point, bu tries to make the points as far away as possible from one another

			//need to find the salesman with the closes point to a given point
			int salesman = findClosestSalesman(salesmenArrayList, i, points);

			if (!salesmenArrayList.get(salesman).contains(i)) {
				salesmenArrayList.get(salesman).add(i);
			}

			//printSalesmen(salesmenArrayList);

		}

		int[][] toReturn = convertToIntArray(salesmenArrayList);

		return toReturn;
	}




	public static void main(String[] args) {

	}

}