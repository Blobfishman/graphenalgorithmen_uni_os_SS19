package de.uos.inf.ko.ga.graph.reader;

import java.io.File;

import de.uos.inf.ko.ga.graph.Graph;
import de.uos.inf.ko.ga.graph.impl.DirectedGraphMatrix;
import de.uos.inf.ko.ga.graph.impl.UndirectedGraphMatrix;

public class GraphReader {

	/**
	 * Reads a directed graph from a file.
	 * @param f File to be read
	 * @return Directed graph
	 */
	public static Graph readDirectedGraph(File f) {
		/* TODO: implement me! */
		return new DirectedGraphMatrix();
	}

	/**
	 * Reads an undirected graph from a file.
	 * @param f File to be read
	 * @return Undirected graph
	 */
	public static Graph readUndirectedGraph(File f) {
		/* TODO: implement me! */
		return new UndirectedGraphMatrix();
	}

}
