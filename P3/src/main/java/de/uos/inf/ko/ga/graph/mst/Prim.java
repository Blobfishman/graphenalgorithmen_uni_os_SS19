package de.uos.inf.ko.ga.graph.mst;

import de.uos.inf.ko.ga.graph.Graph;
import de.uos.inf.ko.ga.graph.impl.UndirectedGraphList;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class Prim {

	/**
	 * Computes a minimum spanning tree of an undirected graph using Prim's algorithm.
	 * @param graph Input graph
	 * @return Minimum spanning tree of the input graph
	 */
	public static Graph minimumSpanningTreeList(Graph graph) {
		assert(graph != null);
		assert(!graph.isDirected());

		final Graph mst = new UndirectedGraphList();
		Set<Integer> Vertices = new HashSet<Integer>();
		mst.addVertices(graph.getVertexCount());  //Fügt alle Knoten des Graphen in Spannbaum ein
		int vertex = 0;       //Nummer des aktuellen Knotens
		int neighbor = 0;     //Nummer des Nachbarn
		int next = 0;         //Nummer des nächsten Knotens der eingefügt wird

		while (Vertices.size() < graph.getVertexCount())
		{
			Vertices.add(vertex);
			List<Integer> neighbors = graph.getNeighbors(vertex);
			Iterator iterator = neighbors.iterator();
			double j = 100000;

			while(iterator.hasNext())
			{
				neighbor = (int) iterator.next();
				if(!Vertices.contains(neighbor))
				{
					if (graph.getEdgeWeight(vertex,neighbor) < j)
					{
						j = graph.getEdgeWeight(vertex,neighbor);
						next = neighbor;
					}
				}
			}

			mst.addEdge(vertex,next,j);
			vertex = next;

		}

		return mst;
	}

	/**
	 * Computes a minimum spanning tree of an undirected graph using Prim's algorithm.
	 * @param graph Input graph
	 * @return Minimum spanning tree of the input graph
	 */
	public static Graph minimumSpanningTreeHeap(Graph graph) {
		assert(graph != null);
		assert(!graph.isDirected());

		final Graph mst = new UndirectedGraphList();

		/* TODO: implement Prim's algorithm */

		return mst;
	}
}
