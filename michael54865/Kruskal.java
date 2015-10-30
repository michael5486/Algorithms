import edu.gwu.algtest.*;
import edu.gwu.debug.*;
import edu.gwu.util.*;
import java.util.ArrayList;
import java.lang.Math;


public class Kruskal implements SpanningTreeAlgorithm {

	//global variable that maintains vertice and edge information
	//ArrayList<LinkedList<GraphEdge>> verticeNeighbors; //java wouldn't let me create an array of Object type
	double[][] adjMatrix; //adjacency Matrix
	double[][] mst; // the minimum spanning tree
	ArrayList<GraphEdge> arrayListEdges;
	int [] setArray;
	int numVertices;



	//must implement following methods

	public String getName() {
		return "michael5486's implementation of Kruskal's algorithm";
	}

	public void setPropertyExtractor (int algID, PropertyExtractor props) {
	}


	public void initialize(int vertices) {

		System.out.println("Initializing " + vertices + " vertices");
		//adds vertices to  graph
		numVertices = vertices;
		adjMatrix = new double[vertices][vertices];
		arrayListEdges = new ArrayList<GraphEdge>();

	}

	public void insertUndirectedEdge(int startVertex, int endVertex, double weight)  { //this is used in my own testing of Kruskal's algorithm
		GraphEdge newEdge = new GraphEdge(startVertex, endVertex, weight);
		GraphEdge newEdge2 = new GraphEdge(endVertex, startVertex, weight);
		int i;
		adjMatrix[startVertex][endVertex] = weight;
		adjMatrix[endVertex][startVertex] = weight;
		if (arrayListEdges.isEmpty()) {
			arrayListEdges.add(newEdge); //adds the initial edge to the arrayList
		}
		else { //this inserts the edges into the matrix into the correct position into the array, so they are already sorted
			for (i = 0; i < arrayListEdges.size(); i++) {
				if (newEdge.weight < arrayListEdges.get(i).weight) {
					break;
				}
			}
			arrayListEdges.add(i, newEdge);
		}
	}


	public double[][] minimumSpanningTree(double[][] matrix) { //calculates the minimum spanning tree of input matrix
		mst = new double[numVertices][numVertices];
		this.printGraph(matrix);
		for (int i = 0; i < numVertices; i++) {
			for (int j = 0; j < numVertices; j++) {
				mst[i][j] = 0;				
				if (matrix[i][j] != 0) { //adds edge to arrayListEdges to be used by Kruskal's algorithm
					GraphEdge newEdge = new GraphEdge(i, j, matrix[i][j]);
					int x;
					if (arrayListEdges.isEmpty()) {
						arrayListEdges.add(newEdge); //adds the initial edge to the arrayList
					}
					else { //this inserts the edges into the matrix into the correct position into the array, so they kept sorted
						for (x = 0; x < arrayListEdges.size(); x++) {
							if (newEdge.weight < arrayListEdges.get(x).weight) {
								break;
							}
						}
						arrayListEdges.add(x, newEdge);
					}
				}
			}
		}
		setArray = new int[numVertices]; //this array represnets the different sets the vertices can be in

		for (int i = 0; i < numVertices; i++) { //places each vertex in its own set
			setArray[i] = i;
		}

		while (!arrayListEdges.isEmpty()) {
			GraphEdge temp = arrayListEdges.remove(0); //removes and sets temp equal to the cheapest edge
			int i = temp.startVertex;
			int j =temp.endVertex;
			if (setArray[i] != setArray[j]) { //if vertex i and j are in different sets then the union of the two is calculated and added to the minimum spanning tree
				this.union(i, j);
				mst[i][j] = mst[j][i] = matrix[i][j];
			}

		}
		System.out.println("mst: ");			
		this.printGraph(mst);
		return mst;
	}

	public GraphVertex[] minimumSpanningTree(GraphVertex[] adjList) {
		System.out.println("minimumSpanningTree adjList");
		return null;

	}

	public void union (int i, int j) {
		int iset = setArray[i];
		int jset = setArray[j];
		int targetSet; //result of the union
		if (iset < jset) { //makes the union set the smallest number of the two sets
			targetSet = iset;
		}
		else {
			targetSet = jset;
		}
		for (int k = 0; k < numVertices; k++) { //this is the actual union operation
			if (setArray[k] == iset || setArray[k] == jset) {
				setArray[k] = targetSet;
			}
		}

	}

	public double getTreeWeight() {
		System.out.println("getTreeWeight");
		double sumWeight = 0;
		for (int i = 0; i < numVertices; i++) {
			for (int j = 0; j < numVertices; j++) {
				if (mst[i][j] != 0) {
					sumWeight += mst[i][j];
				}
			}
		}	
		return sumWeight / 2; //sum is of all edges in the matrix, but the matrix stores every undirected edge twice. Therefore, I divide the result by 2
	}

	public void printGraph(double[][] matrix) { //prints the graph and its edges
		if (numVertices == 0) {
			System.out.println("Graph is empty");
			return;
		}
		else {

			System.out.println("Graph:");
			System.out.print("     ");
			for (int i = 0; i < numVertices; i++) {
				System.out.print("  " + i + "     ");
			}		
			System.out.print("\n");	
			for (int i = 0; i < numVertices; i++) {
				System.out.print(i + "    ");
				for (int j = 0; j < numVertices; j++) {
					if (matrix[i][j] == 0) {
						System.out.print("[     ] ");
					}
					else {
						System.out.print("[ " + matrix[i][j] + " ] ");
					}
				}
				System.out.print("\n");
			}
		}
	}

	public static void generateRandomPoints(int totalVertices) {
		double X[] = new double[totalVertices];
		double Y[] = new double[totalVertices];
		double weight;

		for (int i = 0; i < totalVertices; i++) {
			X[i] = UniformRandom.uniform();
			Y[i] = UniformRandom.uniform();
		}
		for (int i = 0; i < totalVertices; i++) {
			for (int j = 0; j < totalVertices; j++) {
				weight = Math.sqrt(  (X[i] - X[j] * (X[i] - X[j])) +  ((Y[i] - Y[j] * Y[i] - Y[j]))   ); //distance formula
				this.insertUndirectedEdge(i, j, weight);
			}
		}

		this.printGraph(this.adjMatrix);


	}

	public static void main(String[] args) {

		Kruskal test = new Kruskal();
		/*test.initialize(9);
		test.insertUndirectedEdge(0, 1, 1);
		test.insertUndirectedEdge(0,1,1);
		test.insertUndirectedEdge(1,2,5);
		test.insertUndirectedEdge(3,4,3);
		test.insertUndirectedEdge(4,5,4);
		test.insertUndirectedEdge(6,7,6);
		test.insertUndirectedEdge(7,8,2);
		test.insertUndirectedEdge(0,3,2);
		test.insertUndirectedEdge(1,4,4);
		test.insertUndirectedEdge(2,5,6);
		test.insertUndirectedEdge(3,6,5);
		test.insertUndirectedEdge(4,7,3);
		test.insertUndirectedEdge(5,8,1);
		System.out.println(test.arrayListEdges.toString());

		//test.printGraph(test.adjMatrix);

		//System.out.println("minimumSpanningTree: ");
		test.minimumSpanningTree(test.adjMatrix);
		System.out.println("mst weight: " + test.getTreeWeight());*/
		test.generateRandomPoints(10);



	}
		

}