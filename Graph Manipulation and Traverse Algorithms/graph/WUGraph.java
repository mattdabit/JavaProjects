/* WUGraph.java */
package graph;
import dict.*;
import list.*;

/**
 * The WUGraph class represents a weighted, undirected graph.  Self-edges are
 * permitted.
 */

public class WUGraph {
  protected HashTableChained vertexTable;
  protected HashTableChained edgeTable;
  protected DList vertexList;


  /**
   * WUGraph() constructs a graph having no vertices or edges.
   *
   * Running time:  O(1).
   */
  public WUGraph() {
    vertexTable = new HashTableChained();
    edgeTable = new HashTableChained();
    vertexList = new DList();
  }

  /**
   * vertexCount() returns the number of vertices in the graph.
   *
   * Running time:  O(1).
   */
  public int vertexCount(){
    return vertexTable.size();
  }

  /**
   * edgeCount() returns the total number of edges in the graph.
   *
   * Running time:  O(1).
   */
  public int edgeCount(){
    return edgeTable.size();
  }

  /**
   * getVertices() returns an array containing all the objects that serve
   * as vertices of the graph.  The array's length is exactly equal to the
   * number of vertices.  If the graph has no vertices, the array has length
   * zero.
   *
   * (NOTE:  Do not return any internal data structure you use to represent
   * vertices!  Return only the same objects that were provided by the
   * calling application in calls to addVertex().)
   *
   * Running time:  O(|V|).
   */
  public Object[] getVertices() {
    Object[] verticesArray = new Object[vertexList.length()];
    try {
      DListNode temp = (DListNode) vertexList.front();
      int c = 0;
      while (temp.item() != null) {
        verticesArray[c] = temp.item();
        temp = (DListNode) temp.next();
        c++;
      }
    } catch (InvalidNodeException e) {}
    return verticesArray;
  }

  /**
   * addVertex() adds a vertex (with no incident edges) to the graph.
   * The vertex's "name" is the object provided as the parameter "vertex".
   * If this object is already a vertex of the graph, the graph is unchanged.
   *
   * Running time:  O(1).
   */
  public void addVertex(Object vertex) {
    if (!isVertex(vertex)) {
      Vertex v = new Vertex(vertex);
      vertexTable.insert(vertex, v);
      vertexList.insertBack(vertex);
      v.node = (DListNode) vertexList.back();
    }
  }

  /**
   * removeVertex() removes a vertex from the graph.  All edges incident on the
   * deleted vertex are removed as well.  If the parameter "vertex" does not
   * represent a vertex of the graph, the graph is unchanged.
   *
   * Running time:  O(d), where d is the degree of "vertex".
   */
  public void removeVertex(Object vertex) {
    Entry entry = vertexTable.find(vertex);
    if (entry != null) {
      try {
        DList adjacencyList = ((Vertex) entry.value()).adjacencyList;
        if (adjacencyList.length()>0) {
          DListNode temp = (DListNode) adjacencyList.front();
          while (temp.item() != null) {
            DListNode v = temp;
            temp = (DListNode) temp.next();
            removeEdge(vertex, ((Vertex) v.item()).appVertex);
          }
        }
        ((Vertex) entry.value()).node.remove();
      } catch (InvalidNodeException e) {

      } finally {
        vertexTable.remove(vertex);
      }
    }
  }

  /**
   * isVertex() returns true if the parameter "vertex" represents a vertex of
   * the graph.
   *
   * Running time:  O(1).
   */
  public boolean isVertex(Object vertex) {
    Entry entry = vertexTable.find(vertex);
    if (entry == null) {
      return false;
    }
    return true;
  }

  /**
   * degree() returns the degree of a vertex.  Self-edges add only one to the
   * degree of a vertex.  If the parameter "vertex" doesn't represent a vertex
   * of the graph, zero is returned.
   *
   * Running time:  O(1).
   */
  public int degree(Object vertex) {
    if (isVertex(vertex)) {
      Entry entry = vertexTable.find(vertex);
      return ((Vertex) entry.value()).degree();
    }
    return 0;
  }

  /**
   * getNeighbors() returns a new Neighbors object referencing two arrays.  The
   * Neighbors.neighborList array contains each object that is connected to the
   * input object by an edge.  The Neighbors.weightList array contains the
   * weights of the corresponding edges.  The length of both arrays is equal to
   * the number of edges incident on the input vertex.  If the vertex has
   * degree zero, or if the parameter "vertex" does not represent a vertex of
   * the graph, null is returned (instead of a Neighbors object).
   *
   * The returned Neighbors object, and the two arrays, are both newly created.
   * No previously existing Neighbors object or array is changed.
   *
   * (NOTE:  In the neighborList array, do not return any internal data
   * structure you use to represent vertices!  Return only the same objects
   * that were provided by the calling application in calls to addVertex().)
   *
   * Running time:  O(d), where d is the degree of "vertex".
   */
  public Neighbors getNeighbors(Object vertex) {
    Entry entry = vertexTable.find(vertex);
    if (entry == null || degree(vertex) == 0) {
      return null;
    } else {
      Neighbors neighbors = new Neighbors();
      try {
        DList aList = ((Vertex) entry.value()).adjacencyList;
        neighbors.neighborList = new Object[aList.length()];
        neighbors.weightList = new int[aList.length()];
        DListNode temp = (DListNode) aList.front();
        int c = 0;
        while (temp.item()!=null) {
          neighbors.neighborList[c] = ((Vertex) temp.item()).appVertex;
          neighbors.weightList[c] = weight(vertex, ((Vertex) temp.item()).appVertex);
          temp = (DListNode) temp.next();
          c++;
        }
      } catch (InvalidNodeException e) {}
      return neighbors;
    }
  }

  /**
   * addEdge() adds an edge (u, v) to the graph.  If either of the parameters
   * u and v does not represent a vertex of the graph, the graph is unchanged.
   * The edge is assigned a weight of "weight".  If the graph already contains
   * edge (u, v), the weight is updated to reflect the new value.  Self-edges
   * (where u == v) are allowed.
   *
   * Running time:  O(1).
   */
  public void addEdge(Object u, Object v, int weight) {
    if (!isEdge(u,v)) {
      VertexPair pair = new VertexPair(u,v);
      Entry v1 = vertexTable.find(u);
      Entry v2 = vertexTable.find(v);
      if (v1 != null && v2 != null) {
        Edge edge = new Edge((Vertex) v1.value(),(Vertex) v2.value(),weight);
        edgeTable.insert(pair,edge);
        ((Vertex) v1.value()).adjacencyList.insertBack((Vertex) v2.value());
        edge.vertex2Node = (DListNode) ((Vertex) v1.value()).adjacencyList.back();
        if (v1!=v2) {
          ((Vertex) v2.value()).adjacencyList.insertBack((Vertex) v1.value());
        }
        edge.vertex1Node = (DListNode) ((Vertex) v2.value()).adjacencyList.back();
      }
    } else {
      Edge e = (Edge) edgeTable.find(new VertexPair(u,v)).value();
      e.weight = weight;
    }
  }

  /**
   * removeEdge() removes an edge (u, v) from the graph.  If either of the
   * parameters u and v does not represent a vertex of the graph, the graph
   * is unchanged.  If (u, v) is not an edge of the graph, the graph is
   * unchanged.
   *
   * Running time:  O(1).
   */
  public void removeEdge(Object u, Object v) {
    try {
      Entry edgeEntry = edgeTable.find(new VertexPair(u,v));
      if (edgeEntry!=null) {
        Edge e = (Edge) edgeEntry.value();
        if (u==v) {
          if (e.vertex1Node != null) {
            e.vertex1Node.remove();
          } else {
            e.vertex2Node.remove();
          }
        } else {
          e.vertex1Node.remove();
          e.vertex2Node.remove();
        }
        edgeTable.remove(new VertexPair(u,v));
      }
    } catch (InvalidNodeException e) {}
  }

  /**
   * isEdge() returns true if (u, v) is an edge of the graph.  Returns false
   * if (u, v) is not an edge (including the case where either of the
   * parameters u and v does not represent a vertex of the graph).
   *
   * Running time:  O(1).
   */
  public boolean isEdge(Object u, Object v) {
    VertexPair pair = new VertexPair(u,v);
    if (edgeTable.find(pair) == null) {
      return false;
    } else {
      return true;
    }
  }

  /**
   * weight() returns the weight of (u, v).  Returns zero if (u, v) is not
   * an edge (including the case where either of the parameters u and v does
   * not represent a vertex of the graph).
   *
   * (NOTE:  A well-behaved application should try to avoid calling this
   * method for an edge that is not in the graph, and should certainly not
   * treat the result as if it actually represents an edge with weight zero.
   * However, some sort of default response is necessary for missing edges,
   * so we return zero.  An exception would be more appropriate, but also more
   * annoying.)
   *
   * Running time:  O(1).
   */
  public int weight(Object u, Object v) {
    VertexPair pair = new VertexPair(u,v);
    Entry entry = edgeTable.find(pair);
    if (entry == null) {
      return 0;
    }
    return ((Edge) entry.value()).weight();
  }

}
