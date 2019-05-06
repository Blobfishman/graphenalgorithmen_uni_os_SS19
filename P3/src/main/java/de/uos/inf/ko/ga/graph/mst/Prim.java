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
		HashSet<Integer> Vertices = new HashSet<Integer>();
		for (int i = 0; i < graph.getVertexCount(); i++)
		{
			Vertices.add(i);
		}
		Set<Integer> InGraph = new HashSet<>();
		mst.addVertices(graph.getVertexCount());  //Fügt alle Knoten des Graphen in Spannbaum ein
		int start;
		int end;
		double weight;

		Vertices = deIsolateSet(Vertices,graph);

		InGraph.add(0);
		Vertices.remove(0);

		while (Vertices.size() != 0) {
			start = 1000;
			end = 1000;
			weight = 1000;
			for (Integer j : InGraph)
			{
				for (Integer k : Vertices)
				{
					if (graph.hasEdge(j, k))
					{
						if (graph.getEdgeWeight(j, k) < weight)
						{
							start = j;
							end = k;
							weight = graph.getEdgeWeight(j, k);
						}
					}
				}
			}

			mst.addEdge(start, end, weight);
			InGraph.add(end);
			Vertices.remove(end);
		}

		return mst;
	}

	public static HashSet<Integer> deIsolateSet (HashSet<Integer> Vertices, Graph graph)
	{
		boolean isolated;
		for(int o = 0; o < Vertices.size(); o++)
		{
			isolated = true;

			for(int i = 0; i < graph.getVertexCount(); i++)
			{
				if ((graph.hasEdge(o,i)) || (graph.hasEdge(i,o)))
				{
					isolated = false;
				}
			}

			if (isolated == true)
			{
				Vertices.remove(o);
			}

		}

		return Vertices;
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
