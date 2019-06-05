package de.uos.inf.ko.ga.graph.shortestpath;

import de.uos.inf.ko.ga.graph.Graph;

/**
 * Shortest-path computation with Floyd for determining the distances between
 * all pairs of vertices in a directed graph.
 *
 */
public class Floyd {

	/**
	 * Computes distances between all pairs of vertices.
	 * If a vertex is not reachable from another vertex, the corresponding entry
	 * of the distance matrix is Double.POSITIVE_INFINITY.
	 * @param graph Input graph
	 * @return Matrix of dimension n times n with entry d[i][j] being the distance from vertex i to vertex j
	 */
	public static double[][] shortestPaths(Graph graph) {
		//throw new UnsupportedOperationException("method not implemented");
		// anzahl der knoten im graphen
		int n = graph.getVertexCount();
		//Matrix für die Gewichtung der kürzesten Wege
		double[][] d = new double[n][n];
		//Matrix für die kürzesten Wege
		int[][] pred = new int[n][n];

		//erste Iteration, wobei alle Kanten in die Matrix eingetragen werden
		for(int i = 0; i < n; ++i){
			for(int j = 0; j < n; ++j) {
				// da EdgeWeight() bei einem graphen ohne selbstschleifen immer Infinity zurückgeben würde wird der Wert
				// für die Kante die wieder auf den Knoten selbst zeigt manuell auf 0 gesetzt
				if (i == j) {
					d[i][j] = 0;
				} else {
					d[i][j] = graph.getEdgeWeight(i, j);
					pred[i][j] = i;
				}
			}
		}

		// Es wird für jedes Knotenpaar überprüft, ob man einen kürzeren Weg über den Knoten k hat, dadurch wächst die
		// mögliche Kantenanzahl eines weges und ein Spannbaum entsteht
		for(int k = 0; k < n; ++k){
			for(int i = 0; i < n; ++i){
				for(int j = 0; j < n; ++j){
					if((d[i][k]+d[k][j]) < d[i][j]){
						d[i][j] = d[i][k]+d[k][j];
						pred[i][j] = pred[k][j];
					}
				}
			}
		}

		return d;
	}
}
