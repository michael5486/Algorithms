import java.util.*;

public class michael5486Algorithm implements CameraPlacementAlgorithm {

    // Algorithm should solve and return list of Camera instances
    // as camera locations and orientations.

    public double cost(CameraPlacementProblem problem, ArrayList<Camera> cameraList) {
    	
    	//calculates the cost of each state, aka the cover ratio
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
		double percentCovered = 0;
		double numCovered = 0;
		double numNotCovered = 0;

		for (int i = 0; i < problem.getMaxX(); i++) {
	    	for (int j = 0; j < problem.getMaxY(); j++) {
				if (camResult.cover[i][j] == 1) {
		   	 		numCovered++;
				}
				else if (camResult.cover[i][j] == 0) {
		    		numNotCovered++;
				}
	   	 }
		}

		percentCovered = 100.0*(double) numCovered/(double)total;

		return percentCovered;

    }

    public ArrayList<Camera> solve (CameraPlacementProblem problem) {
		
		System.out.print("Interior walls: " + problem.getInteriorWalls().toString() + "\n");
		System.out.print("Exterior walls: " + problem.getExteriorWalls().toString() + "\n");
		System.out.printf("Camera Range: %d", problem.getCameraRange());


		// INSERT YOUR CODE HERE
   		ArrayList<Camera> cameraList = new ArrayList<>();

		// Place cameras at the four corners, but only show the first two
		// (just to demonstrate that you can selectively view cameras).
		Camera camera = new Camera( new Pointd(4,1), 45, true );
		cameraList.add(camera);
		//camera = new Camera( new Pointd(problem.getMaxX()-1, problem.getMaxY()-1), 225, true );
		//cameraList.add(camera);*/

	
		CameraPlacementResult result = CameraPlacement.evaluatePlacement (problem, cameraList);

		if (result.numIllegalPlacements > 0) {
			// Something went wrong.
			return null;
		}

		System.out.printf("Cost: %d/%\n", cost(problem, cameraList));


    return cameraList;

    }
}
