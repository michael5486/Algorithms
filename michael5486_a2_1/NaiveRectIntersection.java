import edu.gwu.algtest.*;
import edu.gwu.util.*;
import edu.gwu.geometry.*;
import java.util.*;

public class NaiveRectIntersection implements RectangleSetIntersectionAlgorithm {

	public String getName() {
		return "michael5486's implementation of NaiveRectIntersection Algorithm";
	}

	public void setPropertyExtractor (int algID, PropertyExtractor props) {

    }

    public boolean testIntersection(IntRectangle rect1, IntRectangle rect2) { //tests is two rectangles intersect 

    	int x1, x2, x3, x4, y1, y2, y3, y4;
    	x1 = rect1.topLeft.x;
    	y1 = rect1.topLeft.y;
    	x2 = rect1.bottomRight.x;
    	y2 = rect1.bottomRight.y;   	
    	x3 = rect2.topLeft.x;
    	y3 = rect2.topLeft.y;
    	x4 = rect2.bottomRight.x;
    	y4 = rect2.bottomRight.y;
    	/*System.out.println("Intersection: " + rect1.ID + " " + rect2.ID);
    	System.out.println("x4: " + x4 + " < x1: " + x1);
    	System.out.println("x2: " + x2 + " < x3: " + x3);
    	System.out.println("y1: " + y1 + " < y4: " + y4);
    	System.out.println("y3: " + y3 + " < y2: " + y2);*/

    	if  (x4 < x1 || x2 < x3 || y1 < y4 || y3 < y2) {
    		return false;
    	}
    	else {
    		return true;
    	}

    }

    public IntPair[] findIntersections(IntRectangle[] rectSet1, IntRectangle[] rectSet2) {

    	IntPair[] intersected = new IntPair[rectSet1.length * rectSet2.length];
    	int count = 0;
    	for (int i = 0; i < rectSet1.length; i++) {
    		for (int j = 0; j < rectSet2.length; j++) {
    			//test every combination of rectangles in the sets
    			if (this.testIntersection(rectSet1[i], rectSet2[j])) { //2 rectangles intersect
    			//	System.out.println("Intersection: " + rectSet1[i].ID + " " + rectSet2[j].ID);
    				IntPair ip = new IntPair(rectSet1[i].ID, rectSet2[j].ID);
    				intersected[count] = ip;
    				count++;

    			}
    		}
    	}

    	if (count == 0) { //no intersections found
    		return null;
    	}
    	else { //done looking for intersections, putting IntPairs into smaller array
    		IntPair[] finalIntersected = new IntPair[count];
    		for (int i = 0; i < count; i++) {
    			finalIntersected[i] = intersected[i];
    		}
    		return finalIntersected;
    	}
    }

    public static void printSet(IntRectangle[] rectSet) { //prints Rectangles in each set
    	System.out.println("New Set: ");
    	for (int i = 0; i < rectSet.length; i++) {
    		System.out.println(rectSet[i].toString());
    	}
    }

    public static void main(String[] args) { //code used to test my methods

    	NaiveRectIntersection test = new NaiveRectIntersection();
    
    	//set 1
    	IntRectangle[] set1 = new IntRectangle[3];
    	IntRectangle r1 = new IntRectangle(5, 80, 20, 70);
    	IntRectangle r2 = new IntRectangle(20, 60, 40, 50);
    	IntRectangle r3 = new IntRectangle(20, 30, 40, 20);
    	set1[0] = r1;
   		set1[1] = r2;
    	set1[2] = r3;
    	//set 2
    	IntRectangle[] set2 = new IntRectangle[4];
    	IntRectangle R4 = new IntRectangle(70, 80, 80, 70);
	    IntRectangle R5 = new IntRectangle(30, 55, 60, 25);
    	IntRectangle R6 = new IntRectangle(50, 30, 65, 20);
    	IntRectangle R7 = new IntRectangle(70, 30, 80, 20);
    	set2[0] = R4;
	    set2[1] = R5;
	    set2[2] = R6;
	    set2[3] = R7;

	    test.printSet(set1);
	    test.printSet(set2);

	    IntPair[] pairs = test.findIntersections(set1, set2);

	    for (int i = 0; i < pairs.length; i++) {
	    	System.out.println(pairs[i]);
	    }
	}
}