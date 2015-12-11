import java.util.*;

public class michael5486Algorithm implements CameraPlacementAlgorithm {

    // Algorithm should solve and return list of Camera instances
    // as camera locations and orientations.

    double T = 400;
    ArrayList<Pointd> validPoints;


    public double cost(CameraPlacementProblem problem, ArrayList<Camera> cameraList) {
    	
    	//calculates the cost of each state, aka total covered blocks/numCameras
    	CameraPlacementResult camResult = new CameraPlacementResult ();
		camResult.cover = CameraGeometry.findCover(problem.getMaxX(),
			     problem.getMaxY(),
			     problem.getCameraRange(),
			     cameraList,
			     problem.getExteriorWalls(),
			     problem.getInteriorWalls(),
			     false,
			     true);

		double total = problem.getMaxX()*problem.getMaxY();
		double numCovered = 0;
		double numNotCovered = 0;
		double ratio = 0;
		double numCameras = cameraList.size();

		for (int i = 0; i < problem.getMaxX(); i++) {
	    	for (int j = 0; j < problem.getMaxY(); j++) {
				if (camResult.cover[i][j] == 1) {
		   	 		numCovered++;
				}
	   		}
		}

		if (numCameras == 0) {
			return 0;
		}
		ratio = numCovered / numCameras;

		//System.out.println("cost: " + ratio);
		return ratio;
    }

    public ArrayList<Pointd> validPoints(CameraPlacementProblem problem) {

    	ArrayList<Pointd> points = new ArrayList<Pointd>();

    	for (int x = 0; x < problem.getMaxX(); x++) {
    		for (int y = 0; y < problem.getMaxY(); y++ ) {
	    		Pointd newPoint = new Pointd(x, y);

    			int wallType = CameraGeometry.findWallType (newPoint,
				    problem.getExteriorWalls(),
				    problem.getInteriorWalls(),
				    problem.getMaxX(),
				    problem.getMaxY());
	    			//System.out.println("wallType " + x + ", " + y  + ": " + wallType);	

	    		if (wallType >= 0) { //point is a valid location for a camera
	    			points.add(newPoint);
	    		}
    		}
    	}

    	return points;

    }

    public ArrayList<Camera> randomNextState(CameraPlacementProblem problem, ArrayList<Camera> cameraList) {
    	//randomly decides to add or swap a new camera 
    	ArrayList<Camera> newCamList = new ArrayList<Camera>(cameraList);
    	int u = (int)(Math.random() * 10);
    	//System.out.println("u: " + u);
    	

    	if (u < 5) { //add
			if (!validPoints.isEmpty()) {
    			//adds a new camera
    			//selects a random point from the list of valid points
    			System.out.println("ADD.............");
    			int num = (int)(Math.random() * validPoints.size() - 1);
    			Pointd randomPoint = validPoints.get(num);
    			//System.out.println("removing validPoint " + validPoints.remove(num).toString()); //must remove point so another cameras are placed on it
    			//validPoints.remove(num);

    			//selects a random angle from 0-359
    			int angle = (int) (Math.random() * 359);
    			Camera camera = new Camera(randomPoint, angle, true);
    			//System.out.println("adding cam " + randomPoint.toString());    			
    			newCamList.add(camera);
    		}
    	}
    	else { //swap

    		if (!newCamList.isEmpty()) {
    			System.out.println("SWAP..........");    			
    			//changes the points of an existing camera by removing and adding a new one
    			int num = (int)(Math.random() * newCamList.size() - 1);
    			int tempX = (int)newCamList.get(num).location.getx();
    			int tempY = (int)newCamList.get(num).location.gety();
    			Pointd tempPoint = new Pointd(tempX, tempY);
    			newCamList.remove(num);
    			//System.out.println("removing cam " + newCamList.remove(num).toString()); //must remove point so another cameras are placed on it
    			validPoints.add(tempPoint);
    			//System.out.println("adding validPoint " + tempPoint.toString());
    		}

			if (!validPoints.isEmpty()) {
    			//adds a new camera
    			//selects a random point from the list of valid points
    			int num = (int)(Math.random() * validPoints.size() - 1);
    			
    			Pointd randomPoint = validPoints.get(num);
    			//System.out.println("removing validPoint " + randomPoint.toString());    			
    			validPoints.remove(num); //must remove point so no other cameras are placed on it

    			//selects a random angle from 0-359
    			int angle = (int) (Math.random() * 359);
    			Camera camera = new Camera(randomPoint, angle, true);
    			newCamList.add(camera);
    			//System.out.println("adding camera " + camera.toString());
    		}
    	}

    	System.out.println("CamList: " + newCamList.toString());
    	return newCamList;

    }

    public boolean expCoinFlip(CameraPlacementProblem problem, ArrayList<Camera> s, ArrayList<Camera> sPrime) {

    	double difference = cost(problem, s) - cost(problem, sPrime);
    	System.out.println("difference: " + difference);
    	int u = (int)(Math.round(Math.random()));
    	System.out.println("T: " + T);
    	double p = Math.exp((difference * -1) / T);
    	System.out.println("p = " + p);
    	if (u < p) {
    		return true;
    	}
    	else {
    		return false;
    	}



    }

    public ArrayList<Camera> camSimulatedAnnealing(CameraPlacementProblem problem) {

    	ArrayList<Camera> s = new ArrayList<Camera>();
    	validPoints = validPoints(problem);

    	double max = cost(problem, s);
    	ArrayList<Camera> maxState = s;

    	for (int i = 0; i < 1000; i++) {

    		ArrayList<Camera> sPrime = randomNextState(problem, s);

    		if (cost(problem, sPrime) > cost(problem, s)) {
    			System.out.println("Jumping to new state...");
    			System.out.println("cost(s') = " + cost(problem, sPrime));
    			System.out.println("cost(s) = " + cost(problem, s));
    			s = sPrime;

    			if (cost(problem, sPrime) > max) {
    				max = cost(problem, sPrime);
    				maxState = sPrime;
    			}
    		}
    		/*else if (expCoinFlip(problem, s, sPrime)) {
    			//System.out.println("Shouldn't add anything...");
    			System.out.println("Jumping to worse state...");
    		}
    		else {
    			System.out.println("Decreasing temp...");
    			T = T * .95;
    		}*/

    	}

    	System.out.println("maxState: " + maxState.toString());
    	return maxState;

    }

    public ArrayList<Camera> solve (CameraPlacementProblem problem) {
		
		System.out.print("Interior walls: " + problem.getInteriorWalls().toString() + "\n");
		System.out.print("Exterior walls: " + problem.getExteriorWalls().toString() + "\n");
		System.out.println("Camera Range: " + problem.getCameraRange());


		// INSERT YOUR CODE HERE

    return camSimulatedAnnealing(problem);

    }
}
