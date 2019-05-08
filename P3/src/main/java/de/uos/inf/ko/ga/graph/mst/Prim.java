package de.uos.inf.ko.ga.graph.mst;

import de.uos.inf.ko.ga.graph.Graph;
import de.uos.inf.ko.ga.graph.impl.UndirectedGraphList;

import java.util.HashSet;
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
		HashSet<Integer> Vertices = new HashSet<Integer>();

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
		Vertices = deIsolateSet(Vertices,graph);

		//adds the first Element from our Vertice into our Graph Set
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
		assert(graph != null);
		assert(!graph.isDirected());

		final Graph mst = new UndirectedGraphList();

		/**
		 * A small class for our nodes, which are going into our three.
		 */

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

		/**
		 * A small class for our heap
		 */

		 class MinHeap {
			private node[] Heap;
			private int size;
			private int maxsize;
			private node begin = new node(Integer.MIN_VALUE,Integer.MIN_VALUE,Double.MIN_VALUE);

			private static final int FRONT = 1;

			public MinHeap(int maxsize)
			{
				this.maxsize = maxsize;
				this.size = 0;
				Heap = new node[this.maxsize + 1];
				Heap[0] = begin;
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
				if (size >= maxsize) {
					return;
				}
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


		//create a heap for the graph
		MinHeap heap = new MinHeap(graph.getVertexCount());

		//fill the heap with big edges, so we are not going to have NullpointerExceptions
		node maxEdge = new node(100000,100000,100000);
		for(int g = 0; g < graph.getVertexCount() +1; g++)
		{
			heap.Heap[g] = maxEdge;
		}

		//flag for the nodes
		boolean alreadyIn = false;

		//a generatetd node for our edges and the next edge which is going into our graph
		node edge;
		node nextEdge;

		//our Set with Vertices that are not included yet
		HashSet<Integer> Vertices = new HashSet<Integer>();

		for (int i = 0; i < graph.getVertexCount(); i++)
		{
			Vertices.add(i);
		}

		Set<Integer> InGraph = new HashSet<>();
		mst.addVertices(graph.getVertexCount());  //Fügt alle Knoten des Graphen in Spannbaum ein

		Vertices = deIsolateSet(Vertices,graph);

		for(Integer m : Vertices)
		{
			InGraph.add(m);
			Vertices.remove(m);
			break;
		}

		while(Vertices.size() != 0)
		{
			for(Integer i: InGraph)
			{
				for(Integer j: Vertices)
				{
					if(graph.hasEdge(i,j))
					{
							alreadyIn = false;
							//es wird eine Kante mit Start, Ende und dem Gewicht erzeugt
							edge = new node(i,j,graph.getEdgeWeight(i,j));

							//es wird geprüft ob sich eine Kante zum Knoten schon im Heap befindet
							for(int m = 1; m < graph.getVertexCount() ; m++)
							{
								if (heap.Heap[m] != null)
								{
									if (heap.Heap[m].end == edge.end)
									{
										// Wenn ja so wird ein boolean auf true gesetzt
										// und es wird geprüft ob diese kleiner ist
										alreadyIn = true;

										if (heap.Heap[m].weight > edge.weight)
										{
											//Sollte sie kleiner sein so wird diese ersetzt und der Baum neu sortiert
											heap.Heap[m] = edge;
											heap.minHeapify(1);
										}
									}
								}
							}
							//Sollte sich die Kante noch nicht im Heap befinden, so wird diese einfach eingefügt
							if (alreadyIn == false)
							{
								heap.insert(edge);
							}
						}
					}

				}

			//Nachdem nun alle Kanten eingefügt werden, wird das erste Element gelöscht und in unseren Graphen eingefügt
			nextEdge = heap.remove();
			if ((nextEdge.end != 100000) && (nextEdge.start != 100000) && (nextEdge.weight != 100000))
			{
				mst.addEdge(nextEdge.start,nextEdge.end,nextEdge.weight);
				InGraph.add(nextEdge.end);
				Vertices.remove(nextEdge.end);
			}
		}

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
		for(int o = 0; o < Vertices.size() + 1; o++)
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
