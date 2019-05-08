package de.uos.inf.ko.ga.graph.mst;

import de.uos.inf.ko.ga.graph.Graph;
import de.uos.inf.ko.ga.graph.impl.UndirectedGraphList;

import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
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

		//Set for the vertices, which are not added yet
		HashSet<Integer> Vertices = new HashSet<>();

		//fill the Set with the Vertices from our graph
		for (int i = 0; i < graph.getVertexCount(); i++)
		{
			Vertices.add(i);
		}

		//Set for our Vertices already in the graph
		Set<Integer> InGraph = new HashSet<>();

		//Add all Vertices from our old Graph to the new one
		mst.addVertices(graph.getVertexCount());

		//Some Variables
		int start = 100000;
		int end = 100000;
		double weight = 100000;

		//deletes isolated Vertices from our Set
		deIsolateSet(Vertices,graph);

		//adds the first Element from our Vertices into our Graph Set
		//(because the Element 0 could be isolated we search for the )
		for(Integer m : Vertices)
		{
			InGraph.add(m);
			Vertices.remove(m);
			break;
		}


		while (Vertices.size() != 0)
		{
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

			if((start < 100000) && (end < 1000000) && (weight < 100000) )
			{
				mst.addEdge(start, end, weight);
				InGraph.add(end);
				Vertices.remove(end);
			}

			// if the graph is not related, we add a node from the other connected component
			else
			{
				for (Integer z: Vertices)
				{
					if(Vertices.contains(z))
					{
						InGraph.add(z);
						Vertices.remove(z);
					}
					break;
				}
			}

			start = 100000;
			end = 100000;
			weight = 100000;

		}

		return mst;
	}



	/**
	 * Computes a minimum spanning tree of an undirected graph using Prim's algorithm.
	 * @param graph Input graph
	 * @return Minimum spanning tree of the input graph
	 */
	public static Graph minimumSpanningTreeHeap(Graph graph) {
		assert (graph != null);
		assert (!graph.isDirected());

		final Graph mst = new UndirectedGraphList();

		mst.addVertices(graph.getVertexCount());

		HashSet<Integer> visited = new HashSet<>();

		PriorityQueue<Node> heap = new PriorityQueue<>();

		double[] d = new double[graph.getVertexCount()];
		int[] pred = new int[graph.getVertexCount()];

		for (int i=0; i<d.length; i++) {
			d[i]= Double.MAX_VALUE;
		}

		//find first unisolated node to prevent unwanted behaviour
		boolean notFound = true;
		int startIndex = 0;
		for (; startIndex<graph.getVertexCount() && notFound; startIndex++) {
			notFound = graph.getNeighbors(startIndex).isEmpty();
			visited.add(startIndex);
		}

		//add all neighbors of the start node to heap
		List<Integer> l = graph.getNeighbors(startIndex);
		for (int i : l) {
			Node node = new Node(startIndex, i, graph.getEdgeWeight(startIndex, i));
			heap.add(node);
			d[i] = graph.getEdgeWeight(startIndex, i);
			pred[i] = startIndex;
		}

		//do prim
		while (visited.size() != graph.getVertexCount()) {
			//get node with minimal weight
			Node node = heap.poll();
			if (node != null) {
				mst.addEdge(pred[node.end], node.end, node.weight);
				visited.add(node.end);

				//go through neighbors and pick edge
				List<Integer> neighbors = graph.getNeighbors(node.end);
				for (int neighbor : neighbors) {
					if(!visited.contains(neighbor) && graph.getEdgeWeight(node.end, neighbor) < d[neighbor]) {
						d[neighbor] = graph.getEdgeWeight(node.end, neighbor);
						pred[neighbor] = node.end;
						Node newNode = new Node(node.end, neighbor, graph.getEdgeWeight(node.end, neighbor));
						heap.add(newNode);
					}
				}
			}
			else{
				//If no node exist find new startIndex
				//find unisolated node to prevent infinite loop

				notFound = true;
				for (; startIndex<graph.getVertexCount() && notFound ; startIndex++) {
					if (!visited.contains(startIndex)) {
						notFound = graph.getNeighbors(startIndex).isEmpty();
						visited.add(startIndex);
					}
				}


				//add all neighbors of the new start node to heap
				l = graph.getNeighbors(startIndex);
				for (int i : l) {
					Node n = new Node(startIndex, i, graph.getEdgeWeight(startIndex, i));
					heap.add(n);
				}
			}
		}


		return mst;
	}

	/**
	 * Deletes all the isolated Vertices from our Set, to prevent unpredictable behaviour
	 * @param Vertices a Set with all the vertices
	 * @param graph input graph
	 */

	private static void deIsolateSet(HashSet<Integer> Vertices, Graph graph)
	{
		boolean isolated;
		for(int k = 0; k < Vertices.size() + 1; k++)
		{
			isolated = true;

			for(int i = 0; i < graph.getVertexCount(); i++)
			{
				if ((graph.hasEdge(k,i)) || (graph.hasEdge(i,k)))
				{
					isolated = false;
				}
			}

			if (isolated)
			{
				Vertices.remove(k);
			}

		}

	}


	private static class Node implements Comparable{

		double weight;
		int start;
		int end;

		Node(int start, int end, double edgeWeight) {
			this.weight = edgeWeight;
			this.start = start;
			this.end = end;
		}

		@Override
		public int compareTo(Object o) {
			return Double.compare(weight, ((Node) o).weight);
		}
	}
}
