/* Kruskal.java */

package graphalg;

import graph.*;
import set.*;
import dict.*;

/**
 * The Kruskal class contains the method minSpanTree(), which implements
 * Kruskal's algorithm for computing a minimum spanning tree of a graph.
 */

public class Kruskal {

  /**
   * minSpanTree() returns a WUGraph that represents the minimum spanning tree
   * of the WUGraph g.  The original WUGraph g is NOT changed.
   *
   * @param g The weighted, undirected graph whose MST we want to compute.
   * @return A newly constructed WUGraph representing the MST of g.
   */
  public static WUGraph minSpanTree(WUGraph g){
  WUGraph mst = new WUGraph();
  Object[] vertices = g.getVertices();
  HashTableChained vertexTable = new HashTableChained();
  Edge[] edges = new Edge[g.edgeCount()];
  int k = 0;
  int c = 0;
  for (int x = 0; x<vertices.length; x++) {
    Neighbors neighbors = g.getNeighbors(vertices[x]);
    vertexTable.insert(vertices[x],k);
    k++;
    for (int i = 0; i<neighbors.neighborList.length; i++) {
      if (vertexTable.find(neighbors.neighborList[i]) != null) {
        edges[c] = new Edge(vertices[x], neighbors.neighborList[i], neighbors.weightList[i]);
        c++;
      }
    }
  }
  quicksort(edges, 0, edges.length-1);
  DisjointSets treeVertices = new DisjointSets(vertices.length);
  for (int i = 0; i < edges.length; i++){
    Edge edge = edges[i];
    if (edge.vertex1 != edge.vertex2){
      int v1 = (int) vertexTable.find(edge.vertex1).value();
      int v2 = (int) vertexTable.find(edge.vertex2).value();
      int root1 = treeVertices.find(v1);
      int root2 = treeVertices.find(v2);
      if (root1!=root2) {
        if ((root1 == v1) && (root2 == v2)) {
          mst.addVertex(edge.vertex1);
          mst.addVertex(edge.vertex2);
          mst.addEdge(edge.vertex1, edge.vertex2, edge.weight);
          treeVertices.union(v1, v2);
        } else if (root1 == v1) {
          mst.addVertex(edge.vertex1);
          mst.addEdge(edge.vertex1, edge.vertex2, edge.weight);
          treeVertices.union(v1, root2);
        } else if (root2 == v2) {
          mst.addVertex(edge.vertex2);
          mst.addEdge(edge.vertex1, edge.vertex2, edge.weight);
          treeVertices.union(root1, v2);
        }
      }
    }
  }
  return mst;
}

  /**
    * quickSort sorts an array of weights in place from least to greatest in order to have
    * a sorted array of edges for minSpanTree().
    * @param a The array of weights
    * @int low is the lowest index of the array we wish to sort
    * @int high is the highest index of the array we wish to sort
  */
  private static void quicksort(Edge[] a, int low, int high) {
    if (low < high) {
      int pivotIndex = low + ((int) ((double) (high-low)*(Math.random())));
      Edge pivot = a[pivotIndex];
      a[pivotIndex] = a[high];
      a[high] = pivot;
      int i = low - 1;
      int j = high;
      do {
        do { i++; } while (a[i].weight < pivot.weight);
        do { j--; } while ((a[j].weight > pivot.weight) && (j > low));
        if (i < j) {
          Edge e = a[i];
          a[i] = a[j];
          a[j] = e;
        }
      } while (i < j);
      a[high] = a[i];
      a[i] = pivot;
      quicksort(a, low, i - 1);
      quicksort(a, i + 1, high);
    }
  }
}