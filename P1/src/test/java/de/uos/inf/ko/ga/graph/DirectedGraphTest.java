package de.uos.inf.ko.ga.graph;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

public abstract class DirectedGraphTest<T extends Graph> {

	protected abstract Graph createGraph();

	@Test
	public void NoVerticesTest() {
		final Graph graph = this.createGraph();
		assertTrue(graph.isDirected());
		assertEquals(0, graph.getVertexCount());
	}

	@Test
	public void NoEdgesTest() {
		final int n = 128;
		final Graph graph = this.createGraph();

		/* add vertices to the graph */
		graph.addVertices(n);
		assertEquals(n, graph.getVertexCount());

		/* test whether the neighborhood of all vertices are empty */
		for (int v = 0; v < n; ++v) {
			assertTrue(graph.getNeighbors(v).isEmpty());
		}

		/* test whether any edges exist */
		for (int u = 0; u < n; ++u) {
			for (int v = 0; v < n; ++v) {
				assertFalse(graph.hasEdge(u, v));
				assertEquals(Double.POSITIVE_INFINITY, graph.getEdgeWeight(u, v), 0.0001);
			}
		}
	}

	@Test
	public void AddVertexTest() {
		final int n = 128;
		final Graph graph = this.createGraph();

		/* add a small number of vertices to the graph */
		for (int i = 0; i < n; ++i) {
			graph.addVertex();
			assertEquals(i + 1, graph.getVertexCount());
		}
	}
	
	@Test
	public void PropertyTest() {
		final Graph graph = this.createGraph();
		graph.addVertex();
		assertTrue(graph.isDirected());
		assertFalse(graph.isWeighted());
		
		graph.addVertex();
		graph.addEdge(0, 1);
		assertFalse(graph.isWeighted());
		graph.addEdge(1, 0, 2.0);
		assertTrue(graph.isWeighted());
	}

	@Test
	public void AdjacencyTest() {
		final Graph graph = this.createGraph();
		
		graph.addVertices(2);
		graph.addEdge(0, 1, 0.5);
		graph.addEdge(1, 0, -0.5);
		
		assertEquals(1, graph.getNeighbors(0).size());
		assertEquals(1, graph.getNeighbors(1).size());
	}
	
	@Test
	public void SimpleAddEdgeTest() {
		final Graph graph = this.createGraph();
		
		graph.addVertices(2);
		graph.addEdge(0, 1, 0.5);

		assertTrue(graph.isWeighted());
		assertTrue(graph.hasEdge(0, 1));
		assertFalse(graph.hasEdge(1, 0));
		
		assertEquals(1, graph.getNeighbors(0).size());
		assertEquals(1, graph.getNeighbors(1).size());

		assertEquals(1, graph.getSuccessors(0).size());
		assertEquals(0, graph.getPredecessors(0).size());
		assertEquals(0, graph.getSuccessors(1).size());
		assertEquals(1, graph.getPredecessors(1).size());
		
		assertEquals(1, graph.getSuccessors(0).get(0).intValue());
		assertEquals(0, graph.getPredecessors(1).get(0).intValue());
	}
	
	@Test
	public void SimpleRemoveEdgeTest() {
		final Graph graph = this.createGraph();
		
		/* add two vertices and two edges connecting them */
		graph.addVertices(2);
		graph.addEdge(0, 1);
		graph.addEdge(1, 0);
		
		assertTrue(graph.hasEdge(0, 1));
		assertTrue(graph.hasEdge(1, 0));
		
		assertEquals(1, graph.getNeighbors(0).size());
		assertEquals(1, graph.getNeighbors(1).size());
		assertEquals(1, graph.getPredecessors(0).size());
		assertEquals(1, graph.getSuccessors(0).size());
		assertEquals(1, graph.getPredecessors(1).size());
		assertEquals(1, graph.getSuccessors(1).size());
		
		/* remove the first edge */
		graph.removeEdge(0, 1);
		
		assertFalse(graph.hasEdge(0, 1));
		assertTrue(graph.hasEdge(1, 0));
		
		assertEquals(1, graph.getNeighbors(0).size());
		assertEquals(1, graph.getNeighbors(1).size());
		assertEquals(1, graph.getPredecessors(0).size());
		assertTrue(graph.getSuccessors(0).isEmpty());
		assertTrue(graph.getPredecessors(1).isEmpty());
		assertEquals(1, graph.getSuccessors(1).size());
		
		/* remove the second edge */
		graph.removeEdge(1, 0);
		
		assertFalse(graph.hasEdge(0, 1));
		assertFalse(graph.hasEdge(1, 0));
	}
	
	private void testPartialGraph(Graph graph) {
		/* test whether all edges exist with the correct edge weights */
		for (int u = 0; u < graph.getVertexCount(); ++u) {
			final List<Integer> neighbors = graph.getNeighbors(u);
			final List<Integer> predecessors = graph.getPredecessors(u);
			final List<Integer> successors = graph.getSuccessors(u);
			
			assertEquals(neighbors.size(), predecessors.size() + successors.size());
			assertTrue(predecessors.size() <= 2);
			assertTrue(successors.size() <= 2);

			if (u > 0) {
				assertTrue(successors.contains(u - 1));
				assertTrue(graph.hasEdge(u, u - 1));
				assertEquals(-0.5, graph.getEdgeWeight(u, u - 1), 0.0001);
			}
			
			if (u > 1) {
				assertTrue(successors.contains(u - 2));
				assertTrue(graph.hasEdge(u, u - 2));
				assertEquals(12345.9, graph.getEdgeWeight(u, u - 2), 0.0001);
			}
			
			if (u > 2) {
				assertFalse(graph.hasEdge(u, u - 3));
			}
			
			if (u + 1 < graph.getVertexCount()) {
				assertTrue(predecessors.contains(u + 1));
				assertTrue(graph.hasEdge(u + 1, u));
				assertEquals(-0.5, graph.getEdgeWeight(u + 1, u), 0.0001);
			}
			
			if (u + 2 < graph.getVertexCount()) {
				assertTrue(predecessors.contains(u + 2));
				assertTrue(graph.hasEdge(u + 2, u));
				assertEquals(12345.9, graph.getEdgeWeight(u + 2, u), 0.0001);
			}
			
			if (u + 3 < graph.getVertexCount()) {
				assertFalse(graph.hasEdge(u + 3, u));
			}
		}
	}

	@Test
	public void ComplexEdgeTest() {
		final int n = 16;
		final Graph graph = this.createGraph();
		
		/* add vertices one by one and for each new vertex 'v' add the edges (v, v - 1) if i >= 1 and (v, v - 2) if v >= 2 */
		for (int v = 0; v < n; ++v) {
			graph.addVertex();
			assertEquals(v + 1, graph.getVertexCount());
			
			if (v > 0) {
				graph.addEdge(v, v - 1, -0.5);
				assertTrue(graph.hasEdge(v, v - 1));
			}
			
			if (v > 1) {
				graph.addEdge(v, v - 2, 12345.9);
				assertTrue(graph.hasEdge(v, v - 2));
			}
			
			testPartialGraph(graph);
		}
		
		for (int v = 0; v < n; ++v) {
			graph.removeVertex();
			testPartialGraph(graph);
		}
	}
}
