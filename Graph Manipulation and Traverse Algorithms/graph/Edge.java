package graph;
import list.*;

class Edge {

	protected Vertex vertex1;
	protected Vertex vertex2;
	protected DListNode vertex1Node;
	protected DListNode vertex2Node;
	protected int weight;

	/**
	 * Constructs an edge object with the given vertices and weights.
	 * @param v1 is a Vertex object that represents the first vertex of the edge.
	 * @param v2 is a Vertex object that represents the second vertex of the edge.
	 * @param w is an integer that represents the weight of the edge.
	*/
	public Edge(Vertex v1, Vertex v2, int w) {
		vertex1 = v1;
		vertex2 = v2;
		weight = w;
	}

	/**
	 * weight returns the weight of "this" edge formed by the two vertices.
	 * @return an integer representing the weight of "this" edge.
	*/
	public int weight() {
		return weight;
	}
}