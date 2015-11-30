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

    public ArrayList<ArrayList<Integer>> randomNextState(ArrayList<ArrayList<Integer>> arrayList) { //removes a point from a randomly selected salesman and gives it to another salesman

    	int salesman1 = (int)(Math.random() * arrayList.size()); //selects a random salesman to remove point from
		int salesman2 = (int)(Math.random() * arrayList.size()); //selects a random salesman to allocate point to	
    	
    	int pointLoc1 = (int)(Math.random() * arrayList.get(salesman1).size());

    	if (arrayList.get(salesman1).isEmpty()) {
    		return arrayList; //salesman1 is empty, cannot swap
    	}

    	int point1 = arrayList.get(salesman1).get(pointLoc1);

    	arrayList.get(salesman1).remove(pointLoc1); //removes randomly selected point from salesman1
    	arrayList.get(salesman2).add(point1); //adds point from salesman1 to salesman2

    	return arrayList;

    }

    public double calcCostOfTour(ArrayList<Integer> arrayList, Pointd[] points) { //calculates the cost of each salesman's tour

    	double sum = 0;

    	for (int i = 0; i < arrayList.size() - 1; i++) {
    		sum += calcDistance(i, i+1, points);
    	}
    	return sum;

    }

    public double cost(ArrayList<ArrayList<Integer>> arrayList, Pointd[] points) { //calculates the cost of each state by returning the longest individual salesman's tour

    	double maxCost = 0;

    	for (int i = 0; i < arrayList.size(); i++) {
    		double temp = calcCostOfTour(arrayList.get(i), points);
            if (temp > maxCost) {
                maxCost = temp;
            }
    	}
    	return maxCost;
    }

    public double calcDistance(int start, int end, Pointd[] points) {

    	Pointd point1 = points[start];
    	Pointd point2 = points[end];
    	double x1 = point1.x, y1 = point1.y, x2 = point2.x, y2 = point2.y;

    	double temp = (x2 - x1)* (x2 - x1) + (y2 - y1) * (y2 - y1);
    	double distance = Math.sqrt(temp);

    	return distance;
    }    

    public ArrayList<ArrayList<Integer>> TSPSimulatedAnnealing(ArrayList<ArrayList<Integer>> arrayList, Pointd[] points) { //allocates points to each salesman with simulated annealing

    	ArrayList<ArrayList<Integer>> s = arrayList;

    	double min = cost(arrayList, points);
    	ArrayList<ArrayList<Integer>> minTour = s;

    	T = 1; //initial value for T

    	for (int i = 0; i < 10000; i++) { //iterates a large amount of times

    		ArrayList<ArrayList<Integer>> sPrime = randomNextState(s); //s' is a new random state

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
    		/*else {
    			System.out.println("Not jumping to new state");
    		}*/

    		//Decreases temperature
    		T = T - .1;

    		//printSalesmen(arrayList);

    	}

    	return minTour;

    }

    public boolean expCoinFlip(ArrayList<ArrayList<Integer>> s, ArrayList<ArrayList<Integer>> sPrime, Pointd[] points) { //calculates the chance that we jump to the new state, even if the cost is worse

        double costSPrime = cost(sPrime, points);
        double costS = cost(s, points);
        //System.out.println("cost(sPrime): " + costSPrime + "cost(s): " + costS);
        
        double difference = costSPrime - costS;
      //  System.out.println("difference: " + difference);

        double p = Math.exp(-1 * difference);
    	//System.out.println("p: " + p);
        p = p / T;

    	double u = UniformRandom.uniform(0, 1);

    	if (u < p) {
    		return true;
    	}
    	else {
            //this should return false more as time goes on
    		return false;
        }

    }

	public int[][] computeTours (int m, Pointd[] points) {

		ArrayList<ArrayList<Integer>> salesmenArrayList = new ArrayList<ArrayList<Integer>>();

		/*for (int i = 0; i < points.length; i++) {
			System.out.println(points[i].toString());

		}*/

		for (int i = 0; i < m; i++) { //for each salesman, create a linkedList
			ArrayList<Integer> temp = new ArrayList<Integer>();
			salesmenArrayList.add(temp);
		}

		for (int i = 0; i < points.length; i++) {
			salesmenArrayList.get(0).add(i);

		}
		
		TSPSimulatedAnnealing(salesmenArrayList, points);

		int[][] toReturn = convertToIntArray(salesmenArrayList);

		//lsprintSalesmen(salesmenArrayList);

		return toReturn;

	}

	public static void main(String[] args) {


	}

}