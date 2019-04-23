package de.uos.inf.ko.ga.graph.impl;

import java.util.ArrayList;
import java.util.List;

import de.uos.inf.ko.ga.graph.Graph;

/**
 * Implementation of a directed graph with a matrix representation of the edges.
 *
 */
public class DirectedGraphMatrix implements Graph {

	//matrix representation of the edges
	public double[][] matrix;

	/**
	 * Constructor, which initializes the matrix
	 *
	 */
	public DirectedGraphMatrix(){
		matrix = new double[0][0];
	}

	public void addEdge(int start, int end) {
		//only create edge if its not a loop
		if(start != end){
			//call the eddEdge method which has the weight as an additional parameter
			this.addEdge(start, end, 1);
		}

	}


	public void addEdge(int start, int end, double weight) {
		//only create edge if its not a loop
		if(start != end){
			//store the weight at the proper place
			matrix[start][end] = weight;
		}

	}


	public void addVertex() {
		//copy the old matrix
		double[][] old = matrix.clone();
		int size = matrix.length;
		//enlarge the matrix by one
		matrix = new double[size+1][size+1];
		//put the old values back inside the matrix
		for(int i=0; i<old.length;i++){
			for(int j=0; j<old[i].length;j++){
				matrix[i][j] = old[i][j];
			}
		}

	}


	public void addVertices(int n) {
		//call the addVertex() method n-times
		for(int i=0;i<n;i++){
			addVertex();
		}
	}


	public List<Integer> getNeighbors(int v) {
		//check if the vertex exists
		if(matrix.length < v+1) throw new IllegalArgumentException();
		//create list, which is returned in the end and store all predecessors in it
		List<Integer> ret = new ArrayList<Integer>();
		ret.addAll(getPredecessors(v));
		//create list and store all successors in it
		List<Integer> suc = new ArrayList<Integer>();
		suc = getSuccessors(v);
		//check which successors need to be added, to prevent duplicates
		for(int i=0;i<suc.size();i++){
			if(!ret.contains(suc.get(i))){
				ret.add(suc.get(i));
			}
		}

		return ret;
	}


	public List<Integer> getPredecessors(int v) {
		//check if the vertex exists
		if(matrix.length < v+1) throw new IllegalArgumentException();
		//create list, which is returned in the end
		List<Integer> ret = new ArrayList<Integer>();
		//go throw the column
		for(int i=0;i<matrix.length;i++){
			//look for any existing edges
			if(matrix[i][v] != 0){
				ret.add(i);
			}
		}
		return ret;
	}


	public List<Integer> getSuccessors(int v) {
		//check if the vertex exists
		if(matrix.length < v+1) throw new IllegalArgumentException();
		//Create list, which is returned in the end
		List<Integer> ret = new ArrayList<Integer>();
		//go throw the line
		for(int i=0; i<matrix[v].length;i++){
			//look for any existing edges
			if(matrix[v][i] != 0){
				ret.add(i);
			}

		}
		return ret;
	}


	public int getVertexCount() {
		return matrix.length;
	}


	public double getEdgeWeight(int start, int end) {
		//Check if the edge exists, if not return infinity
		if(!hasEdge(start,end)) return Double.POSITIVE_INFINITY;
		//return the weight, which is stored inside the matrix
		return matrix[start][end];
	}


	public boolean hasEdge(int start, int end) {
		//if start or end vertex does not exist, return false
		if(start > getVertexCount()-1 || end > getVertexCount()-1) return false;
		//look at the specific place inside the matrix and check if edge exists
		if(matrix[start][end] != 0){
			return true;
		}
		return false;
	}


	public void removeEdge(int start, int end) {
		matrix[start][end] = 0;
	}


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


	public boolean isWeighted() {
		boolean flag = false;
		//go throw the matrix and check if one value is not 1
		for(int i=0;i<matrix.length;i++){
			for(int j=0;j<matrix[i].length;j++){
				if(matrix[i][j] != 0 && matrix[i][j] != 1) flag = true;
			}
		}

		return flag;
	}


	public boolean isDirected() {
		return true;
	}

	public void print(){
		for (double[] doubles : matrix) {
			for (int j = 0; j < matrix.length; j++) {
				System.out.printf("%.3f ", doubles[j]);
			}
			System.out.println();
		}
	}
}
