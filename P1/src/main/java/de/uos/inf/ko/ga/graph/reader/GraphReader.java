package de.uos.inf.ko.ga.graph.reader;

import java.io.*;
import java.util.List;

import de.uos.inf.ko.ga.graph.Graph;
import de.uos.inf.ko.ga.graph.impl.DirectedGraphList;
import de.uos.inf.ko.ga.graph.impl.DirectedGraphMatrix;
import de.uos.inf.ko.ga.graph.impl.UndirectedGraphList;
import de.uos.inf.ko.ga.graph.impl.UndirectedGraphMatrix;

public class GraphReader {

	/**
	 * Reads a directed graph from a file.
	 * @param f File to be read
	 * @return Directed graph
	 */
	public static Graph readDirectedGraph(File f) {
		DirectedGraphList graph = new DirectedGraphList();

		try (BufferedReader reader = new BufferedReader(new FileReader(f))) {

			reader.lines().forEach(line -> {
				line = line.trim();

				if (line.charAt(0) == 'n') { //add vertices based on first line
					line = line.replace("n", "");
					line = line.trim();
					graph.addVertices(Integer.parseInt(line));
				} else if (line.contains("#")) {
					//ignore comment
				} else  { // get matrix
					String[] stringList = line.split(" : ");

					//Syntax check
					if(stringList.length != 2)
						return;

					// index of node
					int node = Integer.parseInt(stringList[0]);

					//edge for this note
					stringList = stringList[1].split(" ");

					//Syntax check
					if(stringList.length != graph.getVertexCount())
						return;

					//add edge
					for (int i=0; i<stringList.length; i++) {
						if (!stringList[i].contains("x")) {
							int weight = Integer.parseInt(stringList[i]);
							if(weight !=1)
								graph.addEdge(node, i, weight);
							else
								graph.addEdge(node, i);
						}
					}
				}
			});

		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Can't find file: " + f.toString());
		}

		return graph;
	}

	/**
	 * Reads an undirected graph from a file.
	 * @param f File to be read
	 * @return Undirected graph
	 */
	public static Graph readUndirectedGraph(File f) {
		UndirectedGraphList graph = new UndirectedGraphList();

		try (BufferedReader reader = new BufferedReader(new FileReader(f))) {

			reader.lines().forEach(line -> {
				line = line.trim();

				if (line.charAt(0) == 'n') { //add vertices based on first line
					line = line.replace("n", "");
					line = line.trim();
					graph.addVertices(Integer.parseInt(line));
				} else if (line.contains("#")) {
					//ignore comment
				} else  { // get matrix
					String[] stringList = line.split(" : ");

					//Syntax check
					if(stringList.length != 2)
						return;

					// index of node
					int node = Integer.parseInt(stringList[0]);

					//edge for this note
					stringList = stringList[1].split(" ");

					//Syntax check
					if(stringList.length != graph.getVertexCount())
						return;

					//add edge
					for (int i=0; i<stringList.length; i++) {
						if (!stringList[i].contains("x")) {
							int weight = Integer.parseInt(stringList[i]);
							if(weight !=1)
								graph.addEdge(node, i, weight);
							else
								graph.addEdge(node, i);
						}
					}
				}
			});

		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Can't find file: " + f.toString());
		}

		return graph;
	}

}
