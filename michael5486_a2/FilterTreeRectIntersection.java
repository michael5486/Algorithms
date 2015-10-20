import edu.gwu.algtest.*;
import edu.gwu.util.*;
import edu.gwu.geometry.*;
import java.util.*;

public class FilterTreeRectIntersection implements RectangleSetIntersectionAlgorithm {

    public FilterTreeNode root; //global variables for my methods
    public IntPair[] intersected; 
    public LinkedList<IntPair> tempIntersection; 
    public FilterTreeNode newNode;  
    public int count;
    
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
    		return false; //returns false if two rectangles do not intersect
    	}
    	else {
    		return true; //returns true is two rectangles do intersect
    	}

    }

    public int pointInQuad(FilterTreeNode node, IntPoint point) {
        int midX = node.midX, midY = node.midY;

        if (point.x > midX && point.y > midY) {
            return 0; //point in quad1
        }
        if (point.x < midX && point.y > midY) {
            return 1; //point in quad2
        }
        if (point.x < midX && point.y < midY) {
            return 2; //point in quad3
        }
        if (point.x > midX && point.y < midY) {
            return 3; //point in quad4
        }
        else {
            return -1; //point is on a bisector, this should never happen though
        }
    }


    public IntPair[] findIntersections(IntRectangle[] rectSet1, IntRectangle[] rectSet2) {
       
        //Places data rectangles in rectSet1 into tree
        this.makeFilterTree(rectSet1);

        //Scans rectangles in second set and test possible intersections against tree.
        LinkedList intersectionSet = filterTreeSearch(rectSet2);
        if (intersectionSet.size() == 0) {
            return null;
        }
        else {
            intersected = new IntPair[intersectionSet.size()];
            for (int i = 0; i < intersected.length; i++) {
                intersected[i] = (IntPair)tempIntersection.get(i);
            }

            //tried debugging to see what's wrong in test 5
            //for (int k = 0; k < intersected.length; k++) { 
              //  System.out.println(intersected[k].toString());
            //}
            /*System.out.println("Tree: ");
            this.printTree(this.root);
            System.out.println("Set: ");
            this.printSet(rectSet2);*/

            tempIntersection = null; //clears gloval variable intersected
            return intersected; 
        }
    }


    public LinkedList<IntPair> filterTreeSearch(IntRectangle[] rectSet) {
            tempIntersection = new LinkedList<IntPair>(); //resets tempIntersection

            for (int i = 0; i < rectSet.length; i++) {
                this.recursiveFilterTreeSearch(this.root, rectSet[i]);
            }
            return tempIntersection; //returns a LinkedList of all intersecting IntPairs
        
    }

    public void recursiveFilterTreeSearch(FilterTreeNode node, IntRectangle rect) {

        if (!node.rectList.isEmpty())  { //checks if rect instersects with all rectangles that are in the current node's rectList
            
            for (int i = 0; i < node.rectList.size(); i++) {
                if (this.testIntersection((IntRectangle)node.rectList.get(i), rect)) {
                    IntRectangle rectangle = (IntRectangle)node.rectList.get(i);
                    IntPair tempPair = new IntPair(rectangle.ID, rect.ID);
                    tempIntersection.add(tempPair); //adds all intersections to global variable tempIntersection
                }
            }
        }
        
            IntPoint TL = rect.topLeft, BR = rect.bottomRight;

            /*This code reduces the amount of recursion. In the naive way,
            every possible rectangle combination between sets is tested. In this new way,
            some quadrants at each node are eliminated and not recursively searched*/

            //rectangle lies in quad2 and 3
            if (this.pointInQuad(node, TL) == 2 && this.pointInQuad(node, BR) == 3) {
                if (node.quadrants[2] != null)
                    recursiveFilterTreeSearch(node.quadrants[2], rect);
                if (node.quadrants[3] != null)
                    recursiveFilterTreeSearch(node.quadrants[3], rect);
            }
            //rectangle lies in quad1 and 2
            else if (this.pointInQuad(node, TL) == 1 && this.pointInQuad(node, BR) == 2) {
                if (node.quadrants[1] != null)
                    recursiveFilterTreeSearch(node.quadrants[1], rect);
                if (node.quadrants[2] != null)                
                    recursiveFilterTreeSearch(node.quadrants[2], rect);
            }
            //rectangle lies in quad1 and 0            
            else if (this.pointInQuad(node, TL) == 1 && this.pointInQuad(node, BR) == 0) {
                if (node.quadrants[1] != null)                
                    recursiveFilterTreeSearch(node.quadrants[1], rect);
                if (node.quadrants[0] != null)
                    recursiveFilterTreeSearch(node.quadrants[0], rect);
            }  
            //rectangle lies in quad0 and 3            
            else if (this.pointInQuad(node, TL) == 0 && this.pointInQuad(node, BR) == 3) {
                if (node.quadrants[0] != null)                
                    recursiveFilterTreeSearch(node.quadrants[0], rect);
                if (node.quadrants[3] != null)
                    recursiveFilterTreeSearch(node.quadrants[3], rect);
            }                      
            else { //rectangle spans all 4 quadrants, must search in each one
                if (node.quadrants[0] != null)
                    recursiveFilterTreeSearch(node.quadrants[0], rect);
                if (node.quadrants[1] != null)
                    recursiveFilterTreeSearch(node.quadrants[1], rect);
                if (node.quadrants[2] != null)
                    recursiveFilterTreeSearch(node.quadrants[2], rect);                
                if (node.quadrants[3] != null)
                    recursiveFilterTreeSearch(node.quadrants[3], rect);
            } 
    }


    public void makeFilterTree(IntRectangle[] rectSet) { //creates a new tree

        int maxX = 0, minX = 0, maxY = 0, minY = 0, x1, x2, y1, y2;

        for (int j = 0; j < rectSet.length; j++) { //finds the max and min coodinates so the level 1 node can be defined
            x1 = rectSet[j].topLeft.x;
            y1 = rectSet[j].topLeft.y;
            x2 = rectSet[j].bottomRight.x;
            y2 = rectSet[j].bottomRight.y;       

            if (x2 > maxX) {
                maxX = x2;
            }
            if (x1 < minX) {
                minX = x1;
            }
            if (y1 > maxY) {
                maxY = y1;
            }
            if (y2 < minY) {
                minY = y2;
            }

        }

        FilterTreeNode newNode = new FilterTreeNode(minX, maxX, minY, maxY, 1);
        this.root = newNode; //adds root node
        //System.out.println("Added root");

        for (int i = 0; i < rectSet.length; i++) { //inserts all rectangles in rectSet[1] into the tree
            recursiveInsertRect(this.root, rectSet[i]);
        }
    }

    public void recursiveInsertRect(FilterTreeNode node, IntRectangle rect) {        

        if (this.findQuadrant(node, rect) == -1) { //check if rect bisects node
            node.rectList.add(rect);
            return;
        }
        else { //must continue into one of the quadrants
            int quadrant = this.findQuadrant(node, rect);

            if (quadrant == 3) { //the contructor switches quadrants 1 and 3, so they must be switched back
                quadrant = 1;
            }
            else if (quadrant == 1) {
             quadrant = 3;
            }

            if (node.quadrants[quadrant] == null) { //if the quadrant is null, make a newNode that quadrant points to
                newNode = new FilterTreeNode(quadrant, node);
                node.quadrants[quadrant] = newNode;
                recursiveInsertRect(newNode, rect);

            }
            else { //quadrant is not null, therefore recursively call the node
                recursiveInsertRect(node.quadrants[quadrant], rect);
            }
        }
    }

    public int findQuadrant(FilterTreeNode node, IntRectangle rect1) { //returns the quadrant number if a rectangle lies completely in that quadrant
        int x1, x2, y1, y2, midX, midY;
        midX = node.midX;
        midY = node.midY;
        x1 = rect1.topLeft.x;
        y1 = rect1.topLeft.y;
        x2 = rect1.bottomRight.x;
        y2 = rect1.bottomRight.y;
        if (x1 > midX && y1 > midY && x2 > midX && y2>midY) {
            return 0; //rect in quadrant 0
        }
        else if (x1 < midX && y1 > midY && x2 < midX && y2 > midY) {
            return 1; //rect in quadrant 1
        }
        else if (x1 < midX && y1 < midY && x2 < midX && y2 < midY) {
            return 2; //rect in quadrant 2
        }
        else if (x1 > midX && y1 < midY && x2 > midX && y2 < midY) {
            return 3; //rect in quadrent 3
        }
        else {
            return -1; //rect bisects node
        }

    }

    public static void printSet(IntRectangle[] rectSet) { //prints the rectangles in each set
    	System.out.println("New Set: ");
    	for (int i = 0; i < rectSet.length; i++) {
    		System.out.println(rectSet[i].toString());
    	}
    }

    public void printTree(FilterTreeNode node) { //recursively prints the full tree

        if (!node.rectList.isEmpty()) 
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
    	IntRectangle[] set1 = new IntRectangle[5];
    	IntRectangle r1 = new IntRectangle(89, 57, 91, 37);
    	IntRectangle r2 = new IntRectangle(12, 80, 45, 66);
    	IntRectangle r3 = new IntRectangle(28, 40, 39, 29);
        IntRectangle r4 = new IntRectangle(18, 70, 32, 60);
        IntRectangle r5 = new IntRectangle(79, 94, 82, 90);

    	set1[0] = r1;
   		set1[1] = r2;
    	set1[2] = r3;
        set1[3] = r4;
        set1[4] = r5;

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

        System.out.println("Set 1 Tree: ");
        test.makeFilterTree(set1);
        test.printTree(test.root);

        System.out.println("Set 2 Tree: ");
        test.makeFilterTree(set2);
        test.printTree(test.root);


        //test code

        //IntPair[] testArray = test.findIntersections(set1, set2);
        /*System.out.println("findIntersections: ");
        for (int i = 0; i < testArray.length; i++) {
            System.out.println(testArray[i]);
        }

        FilterTreeNode base = new FilterTreeNode(0, 100, 0, 100, 1);
        IntPoint testPoint = new IntPoint(51, 49);
        System.out.println(test.pointInQuad(base, testPoint));*/




        //FilterTreeNode newNode = new FilterTreeNode(0, 100, 0, 100, 1);
        //System.out.println(test.findQuadrant(newNode, r6));

	    //test.printSet(set2);

	    /*IntPair[] pairs = test.findIntersections(set1, set2);

	    for (int i = 0; i < pairs.length; i++) {
	    	System.out.println(pairs[i]);
	    }*/
	}
}