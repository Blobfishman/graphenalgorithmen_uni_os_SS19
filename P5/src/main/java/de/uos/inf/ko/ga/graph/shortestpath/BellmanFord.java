package de.uos.inf.ko.ga.graph.shortestpath;

import de.uos.inf.ko.ga.graph.Graph;

import java.util.List;

/**
 * Shortest-path computation with Bellman-Ford for determining the distances
 * from a vertex to all other vertices in a directed graph.
 *
 */
public class BellmanFord {

	/**
	 * Computes distances to all vertices in a graph starting at a certain vertex.
	 * The distances returned is Double.POSITIVE_INFINITY if a vertex is not
	 * reachable from the start vertex.
	 * @param graph Input graph
	 * @param start Start vertex for the computation of the distances
	 * @return Array containing the distance d[v] from the start node for each vertex v
	 */
	public static double[] shortestPaths(Graph graph, int start) {
		//throw new UnsupportedOperationException("method not implemented");

		// n ist die anzahl der Knoten im graphen
		int n = graph.getVertexCount();
		// k ist die iterationsvariable
		int k;
		// zwischenspeicher
		double tmp;
		// d[] ist das array mit den kürzesten wegen zum startknoten
		double[] d = new double[n];
		//alle distanzen auf unendlich setzen
		for(int i = 0; i < n; ++i){
			if(i == start){
				d[i] = 0;
			}else {
				d[i] = Double.POSITIVE_INFINITY;
			}
		}
		// die nachbarn vom startknoten ermitteln
		List<Integer> neighbors = graph.getSuccessors(start);

		for(Integer neighbor : neighbors){
			d[neighbor] = graph.getEdgeWeight(start, neighbor);
		}

		//for-schleife um für jede kantenanzahl kürzeste wege zu ermitteln
		for(k = 2 ; k < n; ++k){
			//für jeden knoten wird überprüft ob er mit der aktuellen Kantenanzahl erreichbar ist
			for(int j = 0; j < n;++j){
				//alle vorgängen vom aktuellen Knoten überprüfen
				neighbors = graph.getPredecessors(j);
				for(Integer neighbor : neighbors){
					//neuen weg von jedem vorgänger berechnen
					tmp = d[neighbor]+graph.getEdgeWeight(neighbor,j);
					//wenn der weg kürzer ist als der bisherige, dann alten weg überschreiben
					if(d[j] > tmp){
						d[j] = tmp;
					}
				}
			}
		}

		return d;
	}


}
