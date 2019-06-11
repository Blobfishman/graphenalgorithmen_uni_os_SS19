package de.uos.inf.ko.ga.tsp;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import de.uos.inf.ko.ga.graph.Graph;
import de.uos.inf.ko.ga.graph.reader.GraphReader;

public class TwoOptTest {

	private static final List<String> GRAPHS = Arrays.asList(
			"tsp_01.gra",
			"tsp_02.gra",
			"tsp_03.gra"
	);

	@Test
	public void testRunTwoOptOnTestGraphs() {
		for (final String filename : GRAPHS) {
			final File fileGraph = new File("src/test/resources/" + filename);

			try {
				final Graph graph = GraphReader.readUndirectedGraph(fileGraph);
				assertNotNull(graph);
				
				// TODO: generate 100 random TSP tours and call TwoOpt.iterativeTwoOpt() on them
				Tour[] randomTours = Tour.randomTour(graph);

				
				// TODO: output minimum, maximum, and average tour length

			} catch (Exception ex) {
				fail("caught an exception while computing TSP tours: " + ex);
			}
		}
	}
}
