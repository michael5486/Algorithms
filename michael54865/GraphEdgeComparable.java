import edu.gwu.algtest.*;
import edu.gwu.debug.*;
import edu.gwu.util.*;

public class GraphEdgeComparable extends GraphEdge {

	public int startVertex, endVertex;
	public double weight;

	public GraphEdgeComparable(int sVertex, int eVertex, double w) {
		startVertex = sVertex;
		endVertex = eVertex;
		weight = w;

	}

	public int compareTo(GraphEdgeComparable compareEdge) {

		int compareQuantity = (int)compareEdge.weight;
		return (int)this.weight - compareQuantity;
	}


} 