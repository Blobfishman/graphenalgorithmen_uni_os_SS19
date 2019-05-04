package de.uos.inf.ko.ga.mst;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.junit.Test;

import de.uos.inf.ko.ga.graph.Graph;
import de.uos.inf.ko.ga.graph.impl.UndirectedGraphMatrix;
import de.uos.inf.ko.ga.graph.mst.Prim;
import de.uos.inf.ko.ga.graph.reader.GraphReader;
import de.uos.inf.ko.ga.graph.render.RenderGraph;
import de.uos.inf.ko.ga.graph.util.GraphGenerator;

public class PrimTest {

	private static final int GRAPH_NUM_INSTANCES = 10;
	private static final List<Integer> GRAPH_SIZES = Arrays.asList(
			5,
			10,
			20,
			50,
			100,
			200,
			500,
			1000,
			2000
	);

	private static final List<String> GRAPHS_UNDIRECTED = Arrays.asList(
			"prim_01.gra",
			"prim_02.gra",
			"prim_03.gra"
	);

	/**
	 * Runs Prim with a list implementation on a set of small instances and renders the minimum spanning trees.
	 */
	@Test
	public void testPrimListOnGraphsFromResources() {
		for (final String filename : GRAPHS_UNDIRECTED) {
			final File fileGraph = new File("src/test/resources/" + filename);
			final String filenamePng = filename.replace(".gra", ".png");
			
			try {
				final Graph graph = GraphReader.readUndirectedGraph(fileGraph);
				assertNotNull(graph);

				final Graph mst = Prim.minimumSpanningTreeList(graph);
				assertNotNull(mst);

				RenderGraph.renderGraph(graph, filenamePng);
				RenderGraph.renderGraph(mst, filenamePng.replace(".png", ".mst.list.png"));
			} catch (Exception ex) {
				fail("caught an exception while computing a minimum spanning tree: " + ex);
			}
		}
	}

	/**
	 * Runs Prim with a heap implementation on a set of small instances and renders the minimum spanning trees.
	 */
	@Test
	public void testPrimHeapOnGraphsFromResources() {
		for (final String filename : GRAPHS_UNDIRECTED) {
			final File fileGraph = new File("src/test/resources/" + filename);
			final String filenamePng = filename.replace(".gra", ".png");
			
			try {
				final Graph graph = GraphReader.readUndirectedGraph(fileGraph);
				assertNotNull(graph);

				final Graph mst = Prim.minimumSpanningTreeHeap(graph);
				assertNotNull(mst);

				RenderGraph.renderGraph(graph, filenamePng);
				RenderGraph.renderGraph(mst, filenamePng.replace(".png", ".mst.heap.png"));
			} catch (Exception ex) {
				fail("caught an exception while computing a minimum spanning tree: " + ex);
			}
		}
	}

	/**
	 * Generates sets of random graphs and determines the running times of Prim on these instances. 
	 */
	@Test
	public void testPrimListAgainstPrimHeap() {
		final Random random = new Random(); // set seed to obtain deterministic results

		for (int n : GRAPH_SIZES) {
			long timePrimList = 0;
			long timePrimHeap = 0;

			for (int instance = 0; instance < GRAPH_NUM_INSTANCES; ++instance) {
				/* generate a random graph */
				final Graph graph = new UndirectedGraphMatrix();
				GraphGenerator.generateRandomGraph(graph, n, random, 0.25);

				/* construct minimum spanning trees with both List and Heap implementations */
				final long timeStartList = System.currentTimeMillis();
				final Graph mstList = Prim.minimumSpanningTreeList(graph);
				final long timeEndList = System.currentTimeMillis();
				final Graph mstHeap = Prim.minimumSpanningTreeHeap(graph);
				final long timeEndHeap = System.currentTimeMillis();
				
				timePrimList += (timeEndList - timeStartList);
				timePrimHeap += (timeEndHeap - timeEndList);
				
				/* test whether the weights of the trees are equal (this does not actually mean that both graphs are spanning trees) */
				assertEquals(getGraphWeight(mstList), getGraphWeight(mstHeap), 0.001);
			}

			System.out.println("n = " + n + ", time list = " + (timePrimList * 0.001) + "s, time heap = " + (timePrimHeap * 0.001));
		}
	}
	
	/**
	 * Computes the weight of all edges of a graph.
	 * @param graph Input graph
	 * @return weight of all edges of the graph
	 */
	private static double getGraphWeight(Graph graph) {
		double totalWeight = 0.0;

		for (int u = 0; u < graph.getVertexCount(); ++u) {
			for (int v : graph.getSuccessors(u)) {
				totalWeight += graph.getEdgeWeight(u, v);
			}
		}

		/* if the graph is undirected, then we counted all edge weights twice */
		return graph.isDirected() ? totalWeight : (0.5 * totalWeight);
	}
}
