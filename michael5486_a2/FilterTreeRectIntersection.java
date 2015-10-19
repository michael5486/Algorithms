import edu.gwu.algtest.*;
import edu.gwu.util.*;
import edu.gwu.geometry.*;
import java.util.*;

public class FilterTreeRectIntersection implements RectangleSetIntersectionAlgorithm {

    public FilterTreeNode root;
    public IntPair[] intersected; 
    public LinkedList<IntPair> tempIntersection;   
    
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

    public IntPair[] findIntersections(IntRectangle[] rectSet1, IntRectangle[] rectSet2) {
       
        int numIntersections = 0;
        //First, place data rectangles in rectSet1 into tree
        this.makeFilterTree(rectSet1);
        //Now scan rectangles in second set and query against tree.
        //need to compare rectangles from rectSet2 to the ones in the tree


            LinkedList intersectionSet = filterTreeSearch(rectSet2);
            if (intersectionSet.size() == 0) {
                return null;
            }
            else {
              //  System.out.println("IntersectionSet: " + intersectionSet);
                intersected = new IntPair[intersectionSet.size()];
                for (int i = 0; i < intersected.length; i++) {
                    intersected[i] = (IntPair)tempIntersection.get(i);
                }
            tempIntersection = null;
            return intersected;
        }
    }


    public LinkedList<IntPair> filterTreeSearch(IntRectangle[] rectSet) {
            tempIntersection = new LinkedList<IntPair>();

            for (int i = 0; i < rectSet.length; i++) {
                this.recursiveFilterTreeSearch(this.root, rectSet[i]);
            }
            //System.out.println(tempIntersection);

            return tempIntersection;
        
    }

    public void recursiveFilterTreeSearch(FilterTreeNode node, IntRectangle rect) {

        //LinkedList<IntRectangle> temp = new LinkedList<IntRectangle>();
        if (!node.rectList.isEmpty())  { //checks if rectangle is instersected by rectangles in the filter tree
            for (int i = 0; i < node.rectList.size(); i++) {
                if (this.testIntersection((IntRectangle)node.rectList.get(i), rect)) {
                    //return (IntRectangle)node.rectList.get(i); //intersecting rectangle found, return that rectangle
                   // System.out.println("Intersection at: " + (IntRectangle)node.rectList.get(i) + " and " + rect);
                    IntRectangle rectangle = (IntRectangle)node.rectList.get(i);
                    IntPair tempPair = new IntPair(rectangle.ID, rect.ID);
                    tempIntersection.add(tempPair);
                }
            }
        }
        for (int i = 0; i < 4; i++) { //if not in the root node, then it recursively continues to all the non-null quadrants
            if (node.quadrants[i] != null) {
                recursiveFilterTreeSearch(node.quadrants[i], rect);
            }
        }
       // System.out.println("Temp: " + temp);
        //return temp; //if no intersections are found, returns null
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
        System.out.println(this.findQuadrant(node, rect));
        if (this.findQuadrant(node, rect) == -1) { //rect bisects node
            node.rectList.add(rect);

          //  System.out.println("Added " + rect.toString() + " to " + node.level);
            return;

        }
        
            int quadrant = this.findQuadrant(node, rect);
            int realQuadrant = quadrant;
            if (quadrant == 3) {
                realQuadrant = 1;
            }
            else if (quadrant == 1) {
                realQuadrant = 3;
            }

            if (node.quadrants[quadrant] == null) {
                FilterTreeNode newNode = new FilterTreeNode(quadrant, node);
                node.quadrants[realQuadrant] = newNode;
                recursiveInsertRect(newNode, rect);
            }
            else {
                recursiveInsertRect(node.quadrants[quadrant], rect);
            }
        }

    }

    public int findQuadrant(FilterTreeNode node, IntRectangle rect1) {
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

    public static void printSet(IntRectangle[] rectSet) { //prints Rectangles in each set
    	System.out.println("New Set: ");
    	for (int i = 0; i < rectSet.length; i++) {
    		System.out.println(rectSet[i].toString());
    	}
    }

    public void printTree(FilterTreeNode node) {

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
    	IntRectangle[] set1 = new IntRectangle[3];
    	/*IntRectangle r1 = new IntRectangle(89, 57, 91, 37);
    	IntRectangle r2 = new IntRectangle(12, 80, 45, 66);
    	IntRectangle r3 = new IntRectangle(28, 40, 39, 29);
        IntRectangle r4 = new IntRectangle(18, 70, 32, 60);
        IntRectangle r5 = new IntRectangle(79, 94, 82, 90);
        //IntRectangle r6 = new IntRectangle(1, 1, 1, 1);*/

        IntRectangle r1 = new IntRectangle(5, 80, 20, 70);
        IntRectangle r2 = new IntRectangle(20, 60, 40, 50);
        IntRectangle r3 = new IntRectangle(20, 30, 40, 20);


    	set1[0] = r1;
   		set1[1] = r2;
    	set1[2] = r3;
      //  set1[3] = r4;
       // set1[4] = r5;
       // set1[5] = r6;

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
        //test.makeFilterTree(set1);
        IntPair[] testArray = test.findIntersections(set1, set2);
        test.printTree(test.root);
        System.out.println("findIntersections: ");
        for (int i = 0; i < testArray.length; i++) {
            System.out.println(testArray[i]);
        }



        //FilterTreeNode newNode = new FilterTreeNode(0, 100, 0, 100, 1);
        //System.out.println(test.findQuadrant(newNode, r6));

	    //test.printSet(set2);

	    /*IntPair[] pairs = test.findIntersections(set1, set2);

	    for (int i = 0; i < pairs.length; i++) {
	    	System.out.println(pairs[i]);
	    }*/
	}
}