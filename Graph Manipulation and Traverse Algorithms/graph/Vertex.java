package graph;
import list.*;

class Vertex {

	protected DList adjacencyList;
	protected DListNode node;
	protected int degree;
	protected Object appVertex;

	/**
	 * Constructs a vertex object using the original inputted vertex from the application.
	 * @param originalVertex is an Object that represents the original vertex that was
	 * inputted by the application.
	*/
	public Vertex(Object originalVertex) {
		adjacencyList = new DList();
		degree = 0;
		appVertex = originalVertex;
	}

	/**
	 * degree returns the degree of "this" vertex where the degree is the
	 * number of vetices that "this" vertex forms an edge with.
	 * @return an integer value representing the degree of "this" vertex.
	*/
	public int degree() {
		return adjacencyList.length();
	}
}