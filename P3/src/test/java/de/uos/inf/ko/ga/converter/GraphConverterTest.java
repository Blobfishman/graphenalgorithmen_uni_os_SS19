package de.uos.inf.ko.ga.converter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Random;

import org.junit.Test;

import de.uos.inf.ko.ga.graph.Graph;
import de.uos.inf.ko.ga.graph.converter.GraphConverter;
import de.uos.inf.ko.ga.graph.impl.DirectedGraphList;
import de.uos.inf.ko.ga.graph.impl.DirectedGraphMatrix;
import de.uos.inf.ko.ga.graph.impl.UndirectedGraphList;
import de.uos.inf.ko.ga.graph.impl.UndirectedGraphMatrix;

/**
 * Tests for the GraphConverter.
 * @author Tobias Oelschlägel
 *
 */
public class GraphConverterTest {

	private final static int MAX_NUM_VERTICES = 32;
	private final Random random = new Random();

	/**
	 * Generates a random directed graph in matrix representation and converts it to a list representation. 
	 */
	@Test
	public void testDirectedMatrixToList() {
		final Graph graph1 = new DirectedGraphMatrix();
		
		generateRandomGraph(graph1, this.random, 0.25);
		assertTrue(graph1.getVertexCount() > 0);
		
		final Graph graph2 = GraphConverter.toList(graph1);
		assertTrue(graph2 instanceof DirectedGraphList);
		assertGraphEquals(graph1, graph2);
	}
	
	/**
	 * Generates a random directed graph in list representation and converts it to a matrix representation. 
	 */
	@Test
	public void testDirectedListToMatrix() {
		final Graph graph1 = new DirectedGraphList();
		
		generateRandomGraph(graph1, this.random, 0.25);
		assertTrue(graph1.getVertexCount() > 0);
		
		final Graph graph2 = GraphConverter.toMatrix(graph1);
		assertTrue(graph2 instanceof DirectedGraphMatrix);
		assertGraphEquals(graph1, graph2);
	}
	
	/**
	 * Generates a random undirected graph in matrix representation and converts it to a list representation. 
	 */
	@Test
	public void testUndirectedMatrixToList() {
		final Graph graph1 = new UndirectedGraphMatrix();
		
		generateRandomGraph(graph1, this.random, 0.25);
		assertTrue(graph1.getVertexCount() > 0);
		
		final Graph graph2 = GraphConverter.toList(graph1);
		assertTrue(graph2 instanceof UndirectedGraphList);
		assertGraphEquals(graph1, graph2);
	}
	
	/**
	 * Generates a random undirected graph in list representation and converts it to a matrix representation. 
	 */
	@Test
	public void testUndirectedListToMatrix() {
		final Graph graph1 = new UndirectedGraphList();
		
		generateRandomGraph(graph1, this.random, 0.25);
		assertTrue(graph1.getVertexCount() > 0);
		
		final Graph graph2 = GraphConverter.toMatrix(graph1);
		assertTrue(graph2 instanceof UndirectedGraphMatrix);
		assertGraphEquals(graph1, graph2);
	}

	/**
	 * Asserts that two graphs are equal, i.e. all of their edges and edge weights are equal.
	 * @param expected Expected graph
	 * @param actual Graph to be compared to the expected graph
	 */
	private static void assertGraphEquals(Graph expected, Graph actual) {
		assertNotNull(expected);
		assertNotNull(actual);
		
		assertEquals(expected.getVertexCount(), actual.getVertexCount());
		
		final int n = expected.getVertexCount();
		
		for (int u = 0; u < n; ++u) {
			for (int v = 0; v < n; ++v) {
				assertEquals(expected.hasEdge(u, v), actual.hasEdge(u, v));
				assertEquals(expected.getEdgeWeight(u, v), actual.getEdgeWeight(u, v), 0.001);
			}
		}
	}

	/**
	 * Generates a random graph.
	 * A random number of vertices is added to the graph and each possible edge is added with probability p.
	 * The edge weights are distributed uniformly in [0, 1].
	 * @param graph Empty graph
	 * @param random Pseudo random number generator
	 * @param p Probability of existence of an edge
	 */
	private static void generateRandomGraph(Graph graph, Random random, double p) {
		assertNotNull(graph);
		
		final int n = (Math.abs(random.nextInt()) % MAX_NUM_VERTICES) + 1;
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
