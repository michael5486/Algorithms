import edu.gwu.algtest.*;
import edu.gwu.util.*;
import edu.gwu.geometry.*;
import java.util.*;

public class FilterTreeRectIntersection implements RectangleSetIntersectionAlgorithm {

    public FilterTreeNode root;
    
    public FilterTreeRectIntersection() {
        this.root = null;
    }

    public FilterTreeRectIntersection(FilterTreeNode node) {
        this.root = node;
    }

	public String getName() {
		return "michael5486's implementation of FilterTreeRectIntersection Algorithm";
	}

	public void setPropertyExtractor (int algID, PropertyExtractor props) {

    }

    public boolean testIntersection(IntRectangle rect1, IntRectangle rect2) {

    	int x1, x2, x3, x4, y1, y2, y3, y4;
    	x1 = rect1.topLeft.x;
    	y1 = rect1.topLeft.y;
    	x2 = rect1.bottomRight.x;
    	y2 = rect1.bottomRight.y;   	
    	x3 = rect2.topLeft.x;
    	y3 = rect2.topLeft.y;
    	x4 = rect2.bottomRight.x;
    	y4 = rect2.bottomRight.y;

    	if  (x4 < x1 || x2 < x3 || y1 < y4 || y3 < y2) {
    		return false;
    	}
    	else {
    		return true;
    	}

    }

    public boolean testRectBisectIntersection(FilterTreeNode node, IntRectangle rect1) {
        int x1, x2, y1, y2, midX, midY;
        midX = node.midX;
        midY = node.midY;
        x1 = rect1.topLeft.x;
        y1 = rect1.topLeft.y;
        x2 = rect1.bottomRight.x;
        y2 = rect1.bottomRight.y; 
       // System.out.println(x1+ " " + x2 + " " + y1 + " " + y2);
        if ((x1 < midX && x2 > midX) || (y2 < midY && y1 > midY)) { //Rect intersects bisection of node
            return true;
        }
        else { //Rect doesn't intersect node bisection
            return false;
        }

    }



    public IntPair[] findIntersections(IntRectangle[] rectSet1, IntRectangle[] rectSet2) {
        int numIntersections = 0;
        //First, place data rectangles in rectSet1 into tree
        makeFilterTree(rectSet1);
        //Now scan rectangles in second set and query against tree.
        for (int i = 0; i < rectSet2.length; i++) {
            LinkedList intersectionSet = filterTreeSearch(rectSet2[i]);
            if (!intersectionSet.isEmpty()) {
                numIntersections += intersectionSet.size();
                //Place intersections in intersectionSet into list of intersections
            }


        }
        //return all intersections
        return null;
    }

    public void makeFilterTree(IntRectangle[] rectSet) {

        FilterTreeNode newNode = new FilterTreeNode(0, 100, 0, 100, 1);
        this.root = newNode;
        System.out.println("Added root");

        for (int i = 0; i < rectSet.length; i++) {
            recursiveInsertRect(this.root, rectSet[i]);
        }
    }

    public void recursiveInsertRect(FilterTreeNode node, IntRectangle rect) {
       // System.out.println("midx : " + node.midX + "midy: " + node.midY);
       // System.out.print(this.testRectBisectIntersection(node, rect));
        if (this.testRectBisectIntersection(node, rect)) { //rect intersects node
            node.rectList.add(rect);
            System.out.println("Added " + rect.toString() + " to " + node.level);

        }
        else {

            FilterTreeNode quad0 = new FilterTreeNode(node.midX, 100, node.midX, 100, node.level + 1);
            FilterTreeNode quad1 = new FilterTreeNode(0, node.midX, node.midY, 100, node.level + 1);
            FilterTreeNode quad2 = new FilterTreeNode(0, node.midX, 0, node.midY, node.level + 1);
            FilterTreeNode quad3 = new FilterTreeNode(node.midX, 100, 0, node.midY, node.level + 1);

            if (this.testRectBisectIntersection(quad0, rect)) { //
                node.quadrants[0] = quad0;
                recursiveInsertRect(quad0, rect);

            } 
            else if (this.testRectBisectIntersection(quad1, rect)) {
                node.quadrants[1] = quad1;
                recursiveInsertRect(quad1, rect);
            }
            else if (this.testRectBisectIntersection(quad2, rect)) {
                node.quadrants[2] = quad2;
                recursiveInsertRect(quad2, rect);

            }
            else if (this.testRectBisectIntersection(quad3, rect)) {
                node.quadrants[3] = quad3;
                recursiveInsertRect(quad3, rect);
            }
            else {
                System.out.println("Code is getting here");
                if (this.rectInQuadrant(quad0, rect)) { //go to quadrant0
                    System.out.println("to quad0");                   
                    recursiveInsertRect(quad0, rect);
                }
                else if (this.rectInQuadrant(quad1, rect)) { //go to quadrant1
                    System.out.println("to quad1");                    
                    recursiveInsertRect(quad1, rect);
                }
                else if (this.rectInQuadrant(quad2, rect)) { //go to quadrant2
                    System.out.println("to quad2");                    
                    recursiveInsertRect(quad2, rect);
                }
                else { //go to quadrant 3
                    System.out.println("to quad3");                    
                    recursiveInsertRect(quad3, rect);
                }
            }   
        }
    }

    public boolean rectInQuadrant(FilterTreeNode node, IntRectangle rect1) {
        int x1, x2, x3, x4, y1, y2, y3, y4;
        x1 = rect1.topLeft.x;
        y1 = rect1.topLeft.y;
        x2 = rect1.bottomRight.x;
        y2 = rect1.bottomRight.y;
       /* System.out.println("node.leftX " + node.leftX+ "  > x1 " + x1);
        System.out.println("node.topY " + node.topY+ "  > y1 " + y1);
        System.out.println("x2 " + x2+ "  < node.rightX " + node.rightX);
        System.out.println("node.botY " + node.botY+ "  < y2 " + y2);*/
        System.out.println((node.leftX < x1)+" "+(node.topY > y1)+" "+(x2 < node.rightX)+" "+(node.botY < y2));

        if (node.leftX < x1 && node.topY > y1 && x2 < node.rightX && node.botY < y2) {
            return true;
        }
        else {
            return false;
        }

    }

    public LinkedList filterTreeSearch(IntRectangle rectSet) {

        return null;
    }

    public static void printSet(IntRectangle[] rectSet) { //prints Rectangles in each set
    	System.out.println("New Set: ");
    	for (int i = 0; i < rectSet.length; i++) {
    		System.out.println(rectSet[i].toString());
    	}
    }

    public void printTree(FilterTreeNode node) {

        System.out.println("Level " + node.level + ": "+ node.rectList);
        for (int i = 0; i < 4; i++) {
            if (node.quadrants[i] != null) {
                printTree(node.quadrants[i]);
            }

        }

    }

    public static void main(String[] args) { 

    	FilterTreeRectIntersection test = new FilterTreeRectIntersection();
    
    	//set 1
    	IntRectangle[] set1 = new IntRectangle[6];
    	IntRectangle r1 = new IntRectangle(89, 57, 91, 37);
    	IntRectangle r2 = new IntRectangle(12, 80, 45, 66);
    	IntRectangle r3 = new IntRectangle(28, 40, 39, 29);
        IntRectangle r4 = new IntRectangle(18, 70, 32, 60);
        IntRectangle r5 = new IntRectangle(79, 94, 82, 90);
        IntRectangle r6 = new IntRectangle(1, 5, 3, 7);


    	set1[0] = r1;
   		set1[1] = r2;
    	set1[2] = r3;
        set1[3] = r4;
        set1[4] = r5;
        set1[5] = r6;

    	/*//set 2
    	IntRectangle[] set2 = new IntRectangle[4];
    	IntRectangle R4 = new IntRectangle(70, 80, 80, 70);
	    IntRectangle R5 = new IntRectangle(30, 55, 60, 25);
    	IntRectangle R6 = new IntRectangle(50, 30, 65, 20);
    	IntRectangle R7 = new IntRectangle(70, 30, 80, 20);
    	set2[0] = R4;
	    set2[1] = R5;
	    set2[2] = R6;
	    set2[3] = R7;*/

	    test.printSet(set1);
        test.makeFilterTree(set1);
        test.printTree(test.root);
	    //test.printSet(set2);

	    /*IntPair[] pairs = test.findIntersections(set1, set2);

	    for (int i = 0; i < pairs.length; i++) {
	    	System.out.println(pairs[i]);
	    }*/
	}
}