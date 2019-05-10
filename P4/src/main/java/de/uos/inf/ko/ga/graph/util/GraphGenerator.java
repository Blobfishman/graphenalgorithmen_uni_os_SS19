package de.uos.inf.ko.ga.graph.util;

import java.util.Random;

import de.uos.inf.ko.ga.graph.Graph;

public class GraphGenerator {

	/**
	 * Generates a random graph. A given number of vertices is added to the graph
	 * and each possible edge is added with probability p. The edge weights are
	 * distributed uniformly in [0, 1].
	 * 
	 * @param graph  Empty graph
	 * @param n      Number of vertices to be added to the graph
	 * @param random Pseudo random number generator
	 * @param p      Probability of existence of an edge
	 */
	public static void generateRandomGraph(Graph graph, int n, Random random, double p) {
		assert (graph != null);

		graph.addVertices(n);

		if (graph.isDirected()) {
			for (int u = 0; u < n; ++u) {
				for (int v = 0; v < n; ++v) {
					if (u != v) {
						if (random.nextDouble() <= p) {
							graph.addEdge(u, v, random.nextDouble());
						}
					}
				}
			}
		} else {
			for (int u = 0; u < n; ++u) {
				for (int v = u + 1; v < n; ++v) {
					if (random.nextDouble() <= p) {
						graph.addEdge(u, v, random.nextDouble());
					}
				}
			}
		}
	}
}
