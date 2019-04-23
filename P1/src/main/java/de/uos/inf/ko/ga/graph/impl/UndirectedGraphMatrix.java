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
	private double[][] matrix;

	public UndirectedGraphMatrix(){
		matrix = new double[0][0];
	}

	@Override
	public void addEdge(int start, int end) {
		addEdge(start,end,1);
	}

	@Override
	public void addEdge(int start, int end, double weight) {
		//only create edge if its not a loop
		if(start != end && !checkNotInBounds(start,end)){
			//store the weight in line for start and in the line for end
			matrix[start][end] = weight;
			matrix[end][start] = weight;
		}
	}

	@Override
	public void addVertex() {
		double[][] old = matrix.clone();
		int size = matrix.length;
		matrix = new double[size+1][size+1];
		for(int i=0; i<old.length;i++){
			for(int j=0; j<old[i].length;j++){
				matrix[i][j] = old[i][j];
			}
		}
		matrix[matrix.length -1][matrix.length -1] = Double.POSITIVE_INFINITY;

	}

	@Override
	public void addVertices(int n) {
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
		//Check if the edge exists, if not return infinity
		if(!hasEdge(start,end)) return Double.POSITIVE_INFINITY;
		//return the weight, which is stored inside the matrix
		return matrix[start][end];
	}

	@Override
	public boolean hasEdge(int start, int end) {
		if (checkNotInBounds(start, end)) return false;
		if(matrix[start][end] != Double.POSITIVE_INFINITY){
			return true;
		}
		return false;
	}

	@Override
	public void removeEdge(int start, int end) {
		matrix[start][end] = 0;
	}

	@Override
	public void removeVertex() {
		//copy the old matrix
		double[][] old = matrix.clone();
		int size = matrix.length;
		//decrease the size of every line and column by one
		matrix = new double[size-1][size-1];
		//put the old values back inside the matrix
		for(int i=0;i<old.length-1;i++){
			for(int j=0;j<old[i].length-1;j++){
				matrix[i][j] = old[i][j];
			}
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
