package de.uos.inf.ko.ga.reader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;

import org.junit.Test;

import de.uos.inf.ko.ga.graph.Graph;
import de.uos.inf.ko.ga.graph.reader.GraphReader;

public class GraphReaderTest {

	@Test
	public void DirectedGraphTest() {
		Graph graph = null;

		try {
			graph = GraphReader.readDirectedGraph(new File("src/test/resources/reader_test_directed.gra"));
		} catch (Exception e) {
			fail("caught an exception while reading a directed graph; is the test data available?");
		}
		
		assertNotNull(graph);
		assertTrue(graph.isDirected());

		/* test outgoing edges of vertex 0 */
		assertEquals(0.0, graph.getEdgeWeight(0, 1), 0.0001);
		assertFalse(graph.hasEdge(0, 2));
		assertFalse(graph.hasEdge(0, 3));
		assertEquals(1.0, graph.getEdgeWeight(0, 4), 0.0001);
		assertFalse(graph.hasEdge(0, 5));

		/* test outgoing edges of vertex 1 */
		assertEquals(1.0, graph.getEdgeWeight(1, 0), 0.0001);
		assertEquals(-2.0, graph.getEdgeWeight(1, 2), 0.0001);
		assertEquals(1.0, graph.getEdgeWeight(1, 3), 0.0001);
		assertFalse(graph.hasEdge(1, 4));
		assertFalse(graph.hasEdge(1, 5));

		/* test outgoing edges of vertex 2 */
		assertFalse(graph.hasEdge(2, 1));
		assertFalse(graph.hasEdge(2, 2));
		assertFalse(graph.hasEdge(2, 3));
		assertEquals(1.0, graph.getEdgeWeight(2, 4), 0.0001);
		assertFalse(graph.hasEdge(2, 5));

		/* test outgoing edges of vertex 3 */
		assertEquals(1.0, graph.getEdgeWeight(3, 0), 0.0001);
		assertFalse(graph.hasEdge(3, 1));
		assertEquals(1.0, graph.getEdgeWeight(3, 2), 0.0001);
		assertEquals(1.0, graph.getEdgeWeight(3, 4), 0.0001);
		assertFalse(graph.hasEdge(3, 5));

		/* test outgoing edges of vertex 4 */
		assertFalse(graph.hasEdge(4, 0));
		assertFalse(graph.hasEdge(4, 1));
		assertFalse(graph.hasEdge(4, 2));
		assertFalse(graph.hasEdge(4, 3));
		assertEquals(1.0, graph.getEdgeWeight(4, 5), 0.0001);

		/* test outgoing edges of vertex 5 */
		assertEquals(1.0, graph.getEdgeWeight(5, 0), 0.0001);
		assertFalse(graph.hasEdge(5, 1));
		assertFalse(graph.hasEdge(5, 2));
		assertFalse(graph.hasEdge(5, 3));
		assertFalse(graph.hasEdge(5, 4));
	}
	
	@Test
	public void UndirectedGraphTest() {
		Graph graph = null;

		try {
			graph = GraphReader.readUndirectedGraph(new File("src/test/resources/reader_test_undirected.gra"));
		} catch (Exception e) {
			fail("caught an exception while reading an undirected graph; is the test data available?");
		}
		
		assertNotNull(graph);
		assertFalse(graph.isDirected());
		
		/* test outgoing edges of vertex 0 */
		assertEquals(1.0, graph.getEdgeWeight(0, 1), 0.0001);
		assertFalse(graph.hasEdge(0, 2));
		assertFalse(graph.hasEdge(0, 3));
		assertEquals(1.0, graph.getEdgeWeight(0, 4), 0.0001);
		assertEquals(1.0, graph.getEdgeWeight(0, 5), 0.0001);

		/* test outgoing edges of vertex 1 */
		assertEquals(1.0, graph.getEdgeWeight(1, 0), 0.0001);
		assertFalse(graph.hasEdge(1, 2));
		assertEquals(1.0, graph.getEdgeWeight(1, 3), 0.0001);
		assertFalse(graph.hasEdge(1, 4));
		assertFalse(graph.hasEdge(1, 5));

		/* test outgoing edges of vertex 2 */
		assertFalse(graph.hasEdge(2, 1));
		assertFalse(graph.hasEdge(2, 2));
		assertEquals(1.0, graph.getEdgeWeight(2, 3), 0.0001);
		assertFalse(graph.hasEdge(2, 4));
		assertFalse(graph.hasEdge(2, 5));

		/* test outgoing edges of vertex 3 */
		assertFalse(graph.hasEdge(3, 0));
		assertEquals(1.0, graph.getEdgeWeight(3, 1), 0.0001);
		assertEquals(1.0, graph.getEdgeWeight(3, 2), 0.0001);
		assertEquals(1.0, graph.getEdgeWeight(3, 4), 0.0001);
		assertFalse(graph.hasEdge(3, 5));

		/* test outgoing edges of vertex 4 */
		assertEquals(1.0, graph.getEdgeWeight(4, 0), 0.0001);
		assertFalse(graph.hasEdge(4, 1));
		assertFalse(graph.hasEdge(4, 2));
		assertEquals(1.0, graph.getEdgeWeight(4, 3), 0.0001);
		assertFalse(graph.hasEdge(4, 5));

		/* test outgoing edges of vertex 5 */
		assertEquals(1.0, graph.getEdgeWeight(5, 0), 0.0001);
		assertFalse(graph.hasEdge(5, 1));
		assertFalse(graph.hasEdge(5, 2));
		assertFalse(graph.hasEdge(5, 3));
		assertFalse(graph.hasEdge(5, 4));
	}

}
