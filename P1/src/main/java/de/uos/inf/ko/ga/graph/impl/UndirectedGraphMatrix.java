package de.uos.inf.ko.ga.graph.impl;

import de.uos.inf.ko.ga.graph.Graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Implementation of a undirected graph with a matrix representation of the edges.
 * XXXXX
 * XXXX
 * XXX
 * XX
 * X
 */
public class UndirectedGraphMatrix implements Graph {

	private boolean weighted = false;
	private double[][] matrix = new double[0][0];

	@Override
	public void addEdge(int start, int end) {
		if (checkNotInBounds(start, end))
			return;
		if (start>end)
			matrix[start][end] = 1;
		else
			matrix[end][start] = 1;
	}

	@Override
	public void addEdge(int start, int end, double weight) {
		if (checkNotInBounds(start, end))
			return;
		weighted = true;
		if(start>end)
			matrix[start][end] = weight;
		else
			matrix[end][start] = weight;
	}

	@Override
	public void addVertex() {
		for (int i=0; i<matrix.length; i++) {
			matrix[i] = Arrays.copyOf(matrix[i], matrix[i].length+1);
			matrix[i][matrix[i].length-1] = Double.POSITIVE_INFINITY;
		}
		matrix = Arrays.copyOf(matrix, matrix.length+1);
		double[] a = {Double.POSITIVE_INFINITY};
		matrix[matrix.length-1] = a;
	}

	@Override
	public void addVertices(int n) {
//		double[][] new_mat = new double[matrix.length+n][];
//		for (int i=0; i<new_mat.length; i++) {
//			new_mat[i] = new double[new_mat.length-i];
//			if (i < matrix.length)
//				System.arraycopy(matrix[i], 0, new_mat[i], 0, matrix[i].length);
//		}
//		matrix = new_mat;

		for(int i=0; i<n; i++) {
			addVertex();
		}
	}

	@Override
	public List<Integer> getNeighbors(int v) {
		List<Integer> l = new ArrayList<>();
		if (v < 0 || v >= matrix.length)
			return l;
		//get all neighbors in the column of v
		for ( int i=0; i<matrix[v].length; i++) {
			if(matrix[v][i] != Double.POSITIVE_INFINITY)
				l.add(i);
		}
		//check if v is neighbors of note >v(index)
		for (int i=v+1; i<matrix.length; i++) {
			if (matrix[i].length <= v)
				continue;
			if(matrix[i][v] != Double.POSITIVE_INFINITY)
				l.add(i);
		}

		return l;
	}

	@Override
	public List<Integer> getPredecessors(int v) {
		return getNeighbors(v);
	}

	@Override
	public List<Integer> getSuccessors(int v) {
		return getNeighbors(v);
	}

	@Override
	public int getVertexCount() {
		return matrix.length;
	}

	@Override
	public double getEdgeWeight(int start, int end) {
		if (checkNotInBounds(start, end))
			return Double.POSITIVE_INFINITY;
		if(start>end)
			return matrix[start][end];
		else
			return matrix[end][start];
	}

	@Override
	public boolean hasEdge(int start, int end) {
		if (checkNotInBounds(start, end))
			return false;
		if(start>end)
			return matrix[start][end] != Double.POSITIVE_INFINITY;
		else
			return matrix[end][start] != Double.POSITIVE_INFINITY;
	}

	@Override
	public void removeEdge(int start, int end) {
		if (checkNotInBounds(start, end))
			return;
		if(start>end)
			matrix[start][end] = Double.POSITIVE_INFINITY;
		else
			matrix[end][start] = Double.POSITIVE_INFINITY;
	}

	@Override
	public void removeVertex() {
		matrix = Arrays.copyOf(matrix, matrix.length-1);
		for (int i=0; i<matrix.length; i++) {
			matrix[i] = Arrays.copyOf(matrix[i], matrix[i].length-1);
		}
	}

	@Override
	public boolean isWeighted() {
		return weighted;
	}

	@Override
	public boolean isDirected() {
		return false;
	}

	@Override
	public void print() {
		for (double[] matrix1 : matrix) {
			for (double v : matrix1) {
				System.out.print(v + " ");
			}
			System.out.println();
		}
	}

	private boolean checkNotInBounds(int start, int end) {
		//			throw new NoVertexExcept("Es gibt keinen Vertex mit:" + v );
		if (matrix.length <= start || start < 0)
			return true;
		return matrix[start].length <= end || end < 0;
	}

}
