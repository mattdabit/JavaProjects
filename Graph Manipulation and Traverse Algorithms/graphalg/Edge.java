package graphalg;

class Edge {
	protected Object vertex1;
	protected Object vertex2;
	protected int weight;

	/**
	 * Constructs an edge object from the inputted vertices and weight.
	 * @param v1 is an Object that represents the first vertex of the edge.
	 * @param v2 is an Object that represents the second vertex of the edge.
	 * @param w is an integer that represents the weight of the edge
	*/
	public Edge(Object v1, Object v2, int w) {
		vertex1 = v1;
		vertex2 = v2;
		weight = w;
	}
}