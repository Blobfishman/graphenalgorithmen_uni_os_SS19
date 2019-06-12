package de.uos.inf.ko.ga.tsp;

import de.uos.inf.ko.ga.graph.Graph;

import java.util.ArrayList;
import java.util.List;

/**
 * Class representing a tour in a graph.
 * @author Tobias Oelschlägel
 *
 */
public class Tour {

	private final Graph g;
	private final int[] vertices;

	/**
	 * Initializes an empty tour
	 * @param g Graph
	 */
	public Tour(Graph g) {
		this(g, null);
	}

	/**
	 * Creates a copy of a tour.
	 * @param tour Tour to be copied
	 */
	public Tour(Tour tour) {
		this(tour.getGraph(), tour.getVertices());
	}

	/**
	 * Initializes a tour with a given order of the vertices.
	 * @param g Graph
	 * @param vertices Order of the vertices
	 */
	public Tour(Graph g, int[] vertices) {
		this.g = g;

		if (vertices != null) {
			this.vertices = new int[vertices.length];
			System.arraycopy(vertices, 0, this.vertices, 0, vertices.length);
		} else {
			this.vertices = new int[0];
		}
	}

	/**
	 * Es werden 100 random Touren an Hand des Graphen erstellt;
	 * @param g
	 * @return randomTours[];
	 */
	public static Tour[] randomTour(Graph g)
	{
		Tour[] randomTours = new Tour[100];

		for (int i = 0; i < 100; i++) {
			// Man erstellt für alle Touren eine Liste
			List<Integer> knoten = new ArrayList<>();

			for (int k = 0; k < g.getVertexCount(); k++) {
				//Man fügt alle Knoten ein
				knoten.add(k);
			}

			int[] random = new int[g.getVertexCount()];
			List<Integer> valList = new ArrayList<>();

			for (int j = 0; j < random.length; j++)
				valList.add(j);

			for (int j = 0; j < g.getVertexCount(); j++) {
				//Man erzeugt ein Array mit zufälligen Werten
				int next = (int) (Math.random() * valList.size());
				random[j] = valList.get(next);
				valList.remove(next);
			}
			// Die Tour wird dann im Array gespeichert
			randomTours[i] = new Tour(g, random);
		}

		return randomTours;
	}

	/**
	 * Computes the total costs of the tour.
	 * @return total sum of costs of edges contained in the tour
	 */
	public double getCosts() {
		double costs = 0.0;
		
		final int n = this.vertices.length;

		for (int i = 0; i < n; ++i) {
			costs += this.g.getEdgeWeight(this.vertices[i], this.vertices[(i + 1) % n]);
		}
		
		return costs;
	}

	/**
	 * Gets the order of the vertices of the tour.
	 * @return order of vertices
	 */
	public int[] getVertices() {
		return this.vertices;
	}

	/**
	 * Gets the graph.
	 * @return graph
	 */
	public Graph getGraph() {
		return this.g;
	}
	
	@Override
	public String toString() {
		final StringBuilder s = new StringBuilder();
		for (int i = 0; i < this.vertices.length; i++){
			s.append(this.vertices[i]);
			s.append(" -> ");
		}
		
		s.append(this.vertices[0]);
		s.append(", Kosten = ");
		s.append(this.getCosts());

		return s.toString();
	}
}
 