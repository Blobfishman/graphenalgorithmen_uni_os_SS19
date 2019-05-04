package de.uos.inf.ko.ga.render;

import static org.junit.Assert.fail;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import de.uos.inf.ko.ga.graph.Graph;
import de.uos.inf.ko.ga.graph.reader.GraphReader;
import de.uos.inf.ko.ga.graph.render.RenderGraph;


public class RenderGraphTest {

	private final static List<String> GRAPHS_UNDIRECTED = Arrays.asList(
			"ga_undir_01.gra",
			"ga_undir_02.gra",
			"ga_undir_03.gra",
			"ga_undir_04.gra",
			"ga_undir_05.gra"
	);
	
	private final static List<String> GRAPHS_DIRECTED = Arrays.asList(
			"ga_dir_01.gra",
			"ga_dir_02.gra",
			"ga_dir_03.gra",
			"ga_dir_04.gra",
			"ga_dir_05.gra"
	);
	
	@Test
	public void RenderUndirectedGraphsTest() {
		for (final String filename : GRAPHS_UNDIRECTED) {
			final File fileGraph = new File("src/test/resources/" + filename);
			final String filenamePng = filename.replace(".gra", ".png");
			
			try {
				final Graph graph = GraphReader.readUndirectedGraph(fileGraph);
				RenderGraph.renderGraph(graph, filenamePng);
			} catch (Exception ex) {
				fail("caught an exception while rendering undirected graphs: " + ex);
			}
		}
	}
	
	@Test
	public void RenderDirectedGraphsTest() {
		for (final String filename : GRAPHS_DIRECTED) {
			final File fileGraph = new File("src/test/resources/" + filename);
			final String filenamePng = filename.replace(".gra", ".png");
			
			try {
				final Graph graph = GraphReader.readDirectedGraph(fileGraph);
				RenderGraph.renderGraph(graph, filenamePng);
			} catch (Exception ex) {
				fail("caught an exception while rendering directed graphs: " + ex);
			}
		}
	}

}
