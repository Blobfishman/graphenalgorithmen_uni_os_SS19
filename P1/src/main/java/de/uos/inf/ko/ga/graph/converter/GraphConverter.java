package de.uos.inf.ko.ga.graph.converter;

import de.uos.inf.ko.ga.graph.Graph;
import de.uos.inf.ko.ga.graph.impl.DirectedGraphList;
import de.uos.inf.ko.ga.graph.impl.DirectedGraphMatrix;
import de.uos.inf.ko.ga.graph.impl.UndirectedGraphList;
import de.uos.inf.ko.ga.graph.impl.UndirectedGraphMatrix;

import java.util.List;

/**
 * Methods for converting between list and matrix representations of graphs.
 */
public class GraphConverter {

	/**
	 * Constructs a graph represented by adjacency lists.
	 * Outputs an instance of DirectedGraphList if the input graph is directed, and outputs an instance of UndirectedGraphList otherwise. 
	 * The weights of the edges of the new graph are the same as the ones of the input graph.
	 * @param graph Graph to be converted
	 * @return graph Graph represented by adjacency list
	 */
	public static Graph toList(Graph graph) {
		if (graph.isDirected()) {
			/* TODO: implement me! */
			return new DirectedGraphList();
		} else {
			Graph g = new UndirectedGraphList();
			return rebuildUndirected(graph,g);
		}
	}

	/**
	 * Constructs a graph represented by an adjacency matrix.
	 * Outputs an instance of DirectedGraphMatrix if the input graph is directed, and outputs an instance of UndirectedGraphMatrix otherwise.
	 * The weights of the edges of the new graph are the same as the ones of the input graph. 
	 * @param graph Graph to be converted
	 * @return graph Graph represented by an adjacency matrix
	 */
	public static Graph toMatrix(Graph graph) {
		if (graph.isDirected()) {
			/* TODO: implement me! */
			return new DirectedGraphMatrix();
		} else {
			Graph g = new UndirectedGraphMatrix();
			return rebuildUndirected(graph,g);
		}
	}

	private static Graph rebuildUndirected(Graph ori, Graph dest){
		dest.addVertices(ori.getVertexCount());
		List<Integer> l;
		for(int i = 0; i< ori.getVertexCount(); i++){
			l = ori.getNeighbors(i);
			for(Integer e: l){
				double weight;
				if(ori.getEdgeWeight(i,e) < Double.POSITIVE_INFINITY){
					weight = ori.getEdgeWeight(i,e);
				}
				else{
					weight = 0;
				}
				dest.addEdge(i,e,weight);
			}
			l.clear();
		}
		return dest;

	}

}
