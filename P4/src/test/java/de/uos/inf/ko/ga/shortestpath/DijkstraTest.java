package de.uos.inf.ko.ga.shortestpath;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import de.uos.inf.ko.ga.graph.render.RenderGraph;
import org.junit.Test;

import de.uos.inf.ko.ga.graph.Graph;
import de.uos.inf.ko.ga.graph.impl.DirectedGraphList;
import de.uos.inf.ko.ga.graph.impl.UndirectedGraphList;
import de.uos.inf.ko.ga.graph.reader.GraphReader;
import de.uos.inf.ko.ga.graph.shortestpath.Dijkstra;

public class DijkstraTest {

	private static final List<String> GRAPHS = Arrays.asList(
			"dijkstra_01.gra",
			"dijkstra_02.gra",
			"dijkstra_03.gra"
	);
	
	@Test
	public void testSimpleUndirectedGraph() {
		final Graph graph = new UndirectedGraphList();
		graph.addVertices(5);
		
		graph.addEdge(0, 1, 5.0);
		graph.addEdge(0, 3, 1.0);
		graph.addEdge(0, 4, 4.0);
		graph.addEdge(1, 2, 4.0);
		graph.addEdge(2, 3, 3.0);
		graph.addEdge(2, 4, 3.0);

		final double[] dist = Dijkstra.shortestPaths(graph, 0);
		assertNotNull(dist);

		final Graph shortestPaths = Dijkstra.shortestPathGraphFromDistances(graph, 0, dist);
		assertNotNull(shortestPaths);
		assertTrue(shortestPaths.isDirected());

		assertTrue(shortestPaths.hasEdge(0, 1));
		assertTrue(shortestPaths.hasEdge(0, 3));
		assertTrue(shortestPaths.hasEdge(0, 4));
		assertTrue(shortestPaths.hasEdge(3, 2));

		assertEquals(5.0, shortestPaths.getEdgeWeight(0, 1), 0.001);
		assertEquals(1.0, shortestPaths.getEdgeWeight(0, 3), 0.001);
		assertEquals(4.0, shortestPaths.getEdgeWeight(0, 4), 0.001);
		assertEquals(3.0, shortestPaths.getEdgeWeight(3, 2), 0.001);
	}
	
	@Test
	public void testDirectedGraphWithNegativeEdgeWeights() {
		final Graph graph = new DirectedGraphList();
		graph.addVertices(5);
		
		graph.addEdge(0, 1, 2.0);
		graph.addEdge(0, 3, 3.0);
		graph.addEdge(1, 2, 1.0);
		graph.addEdge(2, 3, 2.0);
		graph.addEdge(3, 1, -2.0);

		final double[] dist = Dijkstra.shortestPaths(graph, 0);
		assertNotNull(dist);
		
		final Graph shortestPaths = Dijkstra.shortestPathGraphFromDistances(graph, 0, dist);
		assertNotNull(shortestPaths);


		assertTrue(shortestPaths.hasEdge(0, 1));
		assertTrue(shortestPaths.hasEdge(0, 3));
		assertTrue(shortestPaths.hasEdge(1, 2));
	}

	/**
	 * Runs Prim with a list implementation on a set of small instances
	 * and outputs the distances from the starting vertex to all other vertices.
	 */
	@Test
	public void testPrimListOnGraphsFromResources() {
		for (final String filename : GRAPHS) {
			final File fileGraph = new File("src/test/resources/" + filename);
			
			try {
				final Graph graph = GraphReader.readDirectedGraph(fileGraph);
				assertNotNull(graph);

				double[] dist = Dijkstra.shortestPaths(graph, 0);

				//print dist
				System.out.println("Graph: " + filename);
				for (int i=0; i<dist.length; i++) {
					System.out.println("Node: " + i + " dist: " + dist[i]);
				}

				RenderGraph.renderGraph(graph, filename.replace(".gra", ".png"));
				Graph shortestGraph =  Dijkstra.shortestPathGraphFromDistances(graph, 0, dist);
				RenderGraph.renderGraph(shortestGraph, "shortest" + filename.replace(".gra", ".png"));

			} catch (Exception ex) {
				fail("caught an exception while computing shortest paths with Dijkstra: " + ex);
			}
		}
	}
}
