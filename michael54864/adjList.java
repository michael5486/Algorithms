import edu.gwu.algtest.*;
import edu.gwu.debug.*;
import edu.gwu.util.*;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.HashSet;


public class UndirectedDepthFirstAdjList implements UndirectedGraphSearchAlgorithm {

	//global variable that maintains vertice and edge information
	ArrayList<LinkedList<GraphEdge>> verticeNeighbors; //java wouldn't let me create an array of Object type
	int[] visitOrder;
	int[] completionOrder;
	int[] componentLabel;
	int visitCount;
	int completionCount;
	int currentComponentLabel;
	int numVertices;


	public GraphEdge[] articulationEdges() {
		return null;
	}

	public int[] articulationVertices() {
		return null;
	}
	public int[] breadthFirstVisitOrder() {
		return null;
	}
	public boolean existsCycle() {
		return false;
	}
	public boolean existsOddCycle() {
		return false;
	}

	//must implement following methods

	public String getName() {
		return "michael5486's implementation of UndirectedDepthFirstAdjList";
	}

	public void setPropertyExtractor (int algID, PropertyExtractor props) {
	}


	public void initialize(int vertices, boolean isWeighted) {
		
		System.out.println("Initializing " + vertices + " vertices");
		//adds vertices to  graph
		numVertices = vertices;
		verticeNeighbors = new ArrayList<LinkedList<GraphEdge>>();
		LinkedList<GraphEdge> tempList;
		for (int i = 0; i < numVertices; i++) {
			tempList = new LinkedList<GraphEdge>();
			verticeNeighbors.add(tempList);
		}
	}

	public void insertUndirectedEdge(int startVertex, int endVertex, double weight) { //inserts a new edge to the graph
		//System.out.println("startVertex: " + startVertex + " endVertex: " + endVertex + " weight: " + weight);
		GraphEdge newEdge = new GraphEdge(startVertex, endVertex, weight);	
		GraphEdge newEdge2 = new GraphEdge(endVertex, startVertex, weight); //edge is undirected, so it must go both ways
		verticeNeighbors.get(startVertex).add(newEdge);
		verticeNeighbors.get(endVertex).add(newEdge2);

	}

	public void adjListDFS() {
		visitOrder = new int[numVertices];
		completionOrder = new int[numVertices];
		componentLabel = new int[numVertices];
		for (int i = 0; i < numVertices; i++) { //initializes visitOrder and completionOrder array
			visitOrder[i] = -1;
			completionOrder[i] = -1;
		}
		visitCount = -1; //initialize visitCount
		completionCount = -1; //initalize completionCount
		currentComponentLabel = -1; //initialize componentLabel
		//this.printGraph();
		for (int i = 0; i < numVertices; i++) {
			if (visitOrder[i] < 0) {
				currentComponentLabel = currentComponentLabel + 1;
				recursiveDFS(i); //ensures DFS is called on all components of the graph
			}
		}
	}

	public void recursiveDFS(int vertex) {

		visitCount = visitCount + 1;
		//System.out.println("visitCount: " + visitCount);
		visitOrder[vertex] = visitCount; //marks vertex as visited
		//System.out.println("Visited: " + vertex);
		componentLabel[vertex] = currentComponentLabel;

		//Looks for unvisited neighbors
		for (int i = 0; i < verticeNeighbors.get(vertex).size(); i++) {
			 //runs if linkedList of neighbors isnt empty and endVertex doesn't equal startVertex
			int endV = verticeNeighbors.get(vertex).get(i).endVertex;
			if (visitOrder[endV] < 0 &&  endV != vertex) {
				//if unvisited then visit recursively
				//System.out.println("startVertex: " + vertex + " endVertex: " + endV); 
				recursiveDFS(endV);
			}
		}
		//System.out.println("Completed: " + vertex);
		completionCount = completionCount + 1;
		completionOrder[vertex] = completionCount;

	}

	public int[] depthFirstVisitOrder() { //runs DFS to find the visitOrder
		adjListDFS();
		return visitOrder;
	}

	public int[] depthFirstCompletionOrder() { //runs DFS to find the completionOrder
		adjListDFS();
		return completionOrder;
	}

	public int[] componentLabels() {
		adjListDFS();
		return componentLabel;
	}

	public int numConnectedComponents() { //runs DFS to find the number of connected components
		adjListDFS();
		//need to find the number of unique numbers in an array. HashSet only keeps track of unique elements
		HashSet<Integer> uniqueComponents = new HashSet<Integer>();
		for (int i = 0; i < numVertices; i++) {
			uniqueComponents.add(componentLabel[i]);
		}
		//System.out.println("numConnectedComponents: " + uniqueComponents.size());
		return uniqueComponents.size(); //size of HashSet is the number of unique components
	}
	public void printGraph() { //prints the graph and its edges
		if (numVertices == 0) {
			System.out.println("Graph is empty");
			return;
		}
		System.out.println("Graph: ");
		for (int i = 0; i < verticeNeighbors.size(); i++) {
			System.out.print("    Vertice " + i + ": ");
			for (int j = 0; j < verticeNeighbors.get(i).size(); j++) {
				System.out.print(verticeNeighbors.get(i).get(j).endVertex + " ");

			}
			System.out.print("\n");
		}
	}

	public static void main(String[] args) {
		
		UndirectedDepthFirstAdjList test = new UndirectedDepthFirstAdjList();
		test.initialize(2, false);
		test.insertUndirectedEdge(0, 1, 1);
		test.insertUndirectedEdge(1, 0, 1);

	/*	test.insertUndirectedEdge(0, 1, 1);
		test.insertUndirectedEdge(0, 2, 1);
		test.insertUndirectedEdge(1, 2, 1);
		test.insertUndirectedEdge(2, 3, 1);
		test.insertUndirectedEdge(3, 4, 1);
		test.insertUndirectedEdge(3, 6, 1);		
		test.insertUndirectedEdge(4, 6, 1);
		test.insertUndirectedEdge(2, 5, 1);
		test.insertUndirectedEdge(5, 7, 1);*/


		//GraphEdge edge = new GraphEdge(1, 2, 3);
		//System.out.println(edge.toString());


		test.printGraph();

		//test.adjListDFS();
		int[] array = test.depthFirstVisitOrder();
		int[] array2 = test.depthFirstCompletionOrder();
		int[] array3 = test.componentLabels();


		for (int i = 0; i < test.numVertices; i++) {
			System.out.println("Visited" + i + ": " + array[i]);
		}
		for (int i = 0; i < test.numVertices; i++) {
			System.out.println("Completed" + i + ": " + array2[i]);
		}
		for (int i = 0; i < test.numVertices; i++) {
			System.out.println("Component" + i + ": " + array3[i]);
		}
		System.out.println("numConnectedComponents: " + test.numConnectedComponents());

	}


}