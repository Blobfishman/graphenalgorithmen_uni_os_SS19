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



	/**
	 * Computes a minimum spanning tree of an undirected graph using Prim's algorithm.
	 * @param graph Input graph
	 * @return Minimum spanning tree of the input graph
	 */
	public static Graph minimumSpanningTreeHeap(Graph graph) {
		assert(graph != null);
		assert(!graph.isDirected());

		final Graph mst = new UndirectedGraphList();

		class node {

			int start;
			int end;
			double weight;

			public node(int x, int y, double z)
			{
				start = x;
				end = y;
				weight = z;
			}
		}


		class MinHeap {
			private node[] Heap;
			private int size;

			private static final int FRONT = 1;

			public MinHeap(int size)
			{
				Heap = new node[size];
			}

			// Function to return the position of
			// the parent for the node currently
			// at pos
			private int parent(int pos)
			{
				return pos / 2;
			}

			// Function to return the position of the
			// left child for the node currently at pos
			private int leftChild(int pos)
			{
				return (2 * pos);
			}

			// Function to return the position of
			// the right child for the node currently
			// at pos
			private int rightChild(int pos)
			{
				return (2 * pos) + 1;
			}

			// Function that returns true if the passed
			// node is a leaf node
			private boolean isLeaf(int pos)
			{
				if (pos >= (size / 2) && pos <= size) {
					return true;
				}
				return false;
			}

			// Function to swap two nodes of the heap
			private void swap(int fpos, int spos)
			{
				node tmp;
				tmp = Heap[fpos];
				Heap[fpos] = Heap[spos];
				Heap[spos] = tmp;
			}

			// Function to heapify the node at pos
			private void minHeapify(int pos)
			{

				// If the node is a non-leaf node and greater
				// than any of its child
				if (!isLeaf(pos)) {
					if (Heap[pos].weight > Heap[leftChild(pos)].weight
							|| Heap[pos].weight > Heap[rightChild(pos)].weight) {

						// Swap with the left child and heapify
						// the left child
						if (Heap[leftChild(pos)].weight < Heap[rightChild(pos)].weight) {
							swap(pos, leftChild(pos));
							minHeapify(leftChild(pos));
						}

						// Swap with the right child and heapify
						// the right child
						else {
							swap(pos, rightChild(pos));
							minHeapify(rightChild(pos));
						}
					}
				}
			}

			// Function to insert a node into the heap
			public void insert(node element)
			{
				Heap[++size] = element;
				int current = size;

				while (Heap[current].weight < Heap[parent(current)].weight) {
					swap(current, parent(current));
					current = parent(current);
				}
			}



			// Function to build the min heap using
			// the minHeapify
			public void minHeap()
			{
				for (int pos = (size / 2); pos >= 1; pos--) {
					minHeapify(pos);
				}
			}

			// Function to remove and return the minimum
			// element from the heap
			public node remove()
			{
				node popped = Heap[FRONT];
				Heap[FRONT] = Heap[size--];
				minHeapify(FRONT);
				return popped;
			}
		}

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
		




		/* TODO: implement Prim's algorithm */

		return mst;
	}

	/**
	 * Deletes all the isolated Vertices from our Set, so there wont be any problems
	 * @param Vertices a Set with all the vertices
	 * @param graph input graph
	 * @return Vertices, but only the non isolated ones
	 */

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
}
