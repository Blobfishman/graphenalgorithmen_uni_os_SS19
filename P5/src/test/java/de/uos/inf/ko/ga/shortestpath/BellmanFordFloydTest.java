package de.uos.inf.ko.ga.shortestpath;

import static de.uos.inf.ko.ga.graph.render.RenderGraph.renderGraph;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import de.uos.inf.ko.ga.graph.impl.DirectedGraphMatrix;
import de.uos.inf.ko.ga.graph.impl.UndirectedGraphMatrix;
import de.uos.inf.ko.ga.graph.render.RenderGraph;
import org.junit.Test;

import de.uos.inf.ko.ga.graph.Graph;
import de.uos.inf.ko.ga.graph.reader.GraphReader;
import de.uos.inf.ko.ga.graph.shortestpath.BellmanFord;
import de.uos.inf.ko.ga.graph.shortestpath.Floyd;

public class BellmanFordFloydTest {

	private static final List<String> GRAPHS = Arrays.asList(
			"floyd_01.gra",
			"floyd_02.gra",
			"floyd_03.gra"
	);

	/**
	 * Determine distances between all pairs of vertices with Bellman-Ford and Floyd
	 * and output the distances between all pairs.
	 */
	@Test
	public void testCompareBellmanFordAndFloyd() {
		for (final String filename : GRAPHS) {
			final File fileGraph = new File("src/test/resources/" + filename);

			try {
				final Graph graph = GraphReader.readDirectedGraph(fileGraph);
				assertNotNull(graph);

				// TODO: compute distances between all pairs with Bellman-Ford and Floyd, and then output the distance matrices
				double[][] bellman = bellmanFordAllPairs(graph);
				double[][] floyd = Floyd.shortestPaths(graph);
				System.out.println("Bellman-Ford matrix");
				printMatrix(bellman);
				System.out.println("Floyd Matrix");
				printMatrix(floyd);

				// TODO: test whether the values from both distance matrix are equal (use tolerance for comparing floating-point values)
				int n = graph.getVertexCount();
				for (int i = 0; i < n; i++) {
					for (int j = 0; j < n; j++) {
						if (Math.abs(bellman[i][j] - floyd[i][j]) > 0.0001) {
							throw new Exception("Bellman-Ford und Floyd Matrix sind nicht identisch" + bellman[i][j] + " " + floyd[i][j]);
						}
					}
				}

			} catch (Exception ex) {
				fail("caught an exception while computing shortest paths: " + ex);
			}
		}
	}

	/**
	 * Generate random graphs and report the running times of Bellman-Ford and Floyd.
	 */
	@Test
	public void testCompareRunningTimes() {
		final Random random = new Random();
		final int n = 100;

		long totalTimeBellmanFord = 0; // TODO: track time with System.currentTimeMillis()
		long totalTimeFloyd = 0;
		long[] timeBellmanFord = new long[10];
		long[] timeFloyd = new long[10];

		for (int inst = 0; inst < 10; ++inst) {
			// TODO: generate some random graph and report the running times of both algorithms
			Graph randomGraph = new DirectedGraphMatrix();
			randomGraph.addVertices(n);
			for(int i = 0; i < 1000; ++i) {
				randomGraph.addEdge(random.nextInt(n), random.nextInt(n), random.nextInt(10));
			}
			totalTimeBellmanFord = System.currentTimeMillis();
			double[][] bellman = bellmanFordAllPairs(randomGraph);
			totalTimeBellmanFord -= System.currentTimeMillis();
			timeBellmanFord[inst] = totalTimeBellmanFord * (-1);

			totalTimeFloyd = System.currentTimeMillis();
			double[][] floyd = Floyd.shortestPaths(randomGraph);
			totalTimeFloyd -= System.currentTimeMillis();
			timeFloyd[inst] = totalTimeFloyd;

		}


		// TODO: output some kind of report
		double mittelwert = 0;
		for (int i = 0; i < timeBellmanFord.length; ++i) {
			mittelwert += timeBellmanFord[i];
		}
		mittelwert /= timeBellmanFord.length;
		System.out.println("Bellman-Ford Mittelwert: " + mittelwert + "ms");

		for (int i = 0; i < timeFloyd.length; ++i) {
			mittelwert += timeFloyd[i];
		}
		mittelwert /= timeFloyd.length;
		System.out.println("Floyd Mittelwert: " + mittelwert + "ms");
	}

	/**
	 * Executes Bellman-Ford with each vertex as the starting vertex.
	 *
	 * @param graph Input graph
	 * @return distance matrix with distances between all pairs of vertices
	 */
	private double[][] bellmanFordAllPairs(Graph graph) {
		final int n = graph.getVertexCount();

		double d[][] = new double[n][n];

		for (int u = 0; u < n; ++u) {
			d[u] = BellmanFord.shortestPaths(graph, u);
			assertNotNull(d[u]);
		}

		return d;
	}

	private void printMatrix(double[][] matrix) {
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
					System.out.print(matrix[i][j] + " ");
			}

			System.out.println();
		}
	}

}