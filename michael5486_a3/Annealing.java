import edu.gwu.algtest.*;
import edu.gwu.util.*;
import edu.gwu.geometry.*;

import java.util.ArrayList;

public class Annealing implements MTSPAlgorithm{

	double T;

	public String getName() {
		return "michael5486's implementation of AnnealingMTSP";
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

    public ArrayList<ArrayList<Integer>> randomNextState(ArrayList<ArrayList<Integer>> arrayList) { //randomly removes a point from one salesman and adds it to another


    	int salesman1 = (int)(Math.random() * arrayList.size()); //will create a random number from 0 to m
		int salesman2 = (int)(Math.random() * arrayList.size());    	
    	
    	int pointLoc1 = (int)(Math.random() * arrayList.get(salesman1).size());
    	//System.out.printf("salesman1: %d salesman2: %d pointLoc1: %d\n", salesman1, salesman2, pointLoc1);

    	if (arrayList.get(salesman1).isEmpty()) {
    		return arrayList; //salesman1 is empty, cannot swap
    	}
    	int point1 = arrayList.get(salesman1).get(pointLoc1);
    	

    	//System.out.println("point1: " + point1);


    	arrayList.get(salesman1).remove(pointLoc1); //removes randomly selected point from salesman1
    	arrayList.get(salesman2).add(point1); //adds point from salesman1 to salesman2*/

    	return arrayList;

    }

    public double calcCostOfTour(ArrayList<Integer> arrayList, Pointd[] points) {

    	double sum = 0;

    	for (int i = 0; i < arrayList.size() - 1; i++) {
    		sum += calcDistance(i, i+1, points);
    	}
    	return sum;

    }

    public double cost(ArrayList<ArrayList<Integer>> arrayList, Pointd[] points) {

    	double sum = 0;

    	for (int i = 0; i < arrayList.size(); i++) {
    		sum += calcCostOfTour(arrayList.get(i), points);
    	}
    	return sum;
    }

    public double calcDistance(int start, int end, Pointd[] points) {

    	Pointd point1 = points[start];
    	Pointd point2 = points[end];
    	double x1 = point1.x, y1 = point1.y, x2 = point2.x, y2 = point2.y;


    	double temp = (x2 - x1)* (x2 - x1) + (y2 - y1) * (y2 - y1);
    	double distance = Math.sqrt(temp);

    	return distance;
    }    

    public ArrayList<ArrayList<Integer>> TSPSimulatedAnnealing(ArrayList<ArrayList<Integer>> arrayList, Pointd[] points) {

    	ArrayList<ArrayList<Integer>> s = arrayList;

    	double min = cost(arrayList, points);
    	ArrayList<ArrayList<Integer>> minTour = s;

    	T = 1;

    	for (int i = 0; i < 10000; i++) {

    		ArrayList<ArrayList<Integer>> sPrime = randomNextState(s);

    		if (cost(sPrime, points) < cost(s, points)) {
    			s = sPrime;

    			if (cost(sPrime, points) < min) {
    				min = cost(sPrime, points);
    				minTour = sPrime;

    			}

    		}
    		else if (expCoinFlip(s, sPrime, points)) {
    			//Jump to sPrime even if it's worse
    			//System.out.println("Jumping to new state");
    			s = sPrime;

    		}
    		else {

    			//System.out.println("Not jumping to new state");
    		}

    		//Decrease temperature
    		T = T - 0.00001;


    	}

    	return minTour;


    }

    public boolean expCoinFlip(ArrayList<ArrayList<Integer>> s, ArrayList<ArrayList<Integer>> sPrime, Pointd[] points) {

    	double p = Math.exp( -(cost(sPrime, points) - cost(s, points)) / T);
    	//System.out.println("P: " + p);
    	//System.out.println("T: " + T);

    	double u = UniformRandom.uniform(0, 1);
    	if (u < p) {
    		return true;
    	}
    	else {
    		return false;
    	}

    }

	public int[][] computeTours (int m, Pointd[] points) {

		ArrayList<ArrayList<Integer>> salesmenArrayList = new ArrayList<ArrayList<Integer>>();

		/*System.out.println("num of salesmen: " + m);
		System.out.println("points: ");
		for (int i = 0; i < points.length; i++) {
			System.out.println(i + ":    " + points[i].toString());

		}*/

		for (int i = 0; i < m; i++) { //for each salesman, create a linkedList
			ArrayList<Integer> temp = new ArrayList<Integer>();
			salesmenArrayList.add(temp);
			//System.out.println("Added salesman " + i);
		}

		for (int i = 0; i < points.length; i++) {
			salesmenArrayList.get(0).add(i);

		}

		//System.out.println("cost: " + cost(salesmenArrayList, points));
		
		TSPSimulatedAnnealing(salesmenArrayList, points);

		int[][] toReturn = convertToIntArray(salesmenArrayList);

		//printSalesmen(salesmenArrayList);

		return toReturn;

	}

	public static void main(String[] args) {




	}

}