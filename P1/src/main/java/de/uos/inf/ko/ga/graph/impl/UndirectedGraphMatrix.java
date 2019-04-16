package de.uos.inf.ko.ga.graph.impl;

import java.util.ArrayList;
import java.util.List;

import de.uos.inf.ko.ga.graph.Graph;
import de.uos.inf.ko.ga.graph.util.NoVertexExcept;

/**
 * Implementation of a undirected graph with a matrix representation of the edges.
 *
 */
public class UndirectedGraphMatrix implements Graph {

	private boolean weighted = false;
	private  double[][] mat = new double[1][];

	@Override
	public void addEdge(int start, int end) {
		addEdge(start,end,1);
	}

	@Override
	public void addEdge(int start, int end, double weight) {
		if(weight != 0) weighted = true;
		mat[start][end] = weight;
	}

	@Override
	public void addVertex() {
		double[][] new_mat = new double[mat.length+1][mat.length+1];
		for(int i = 0; i < mat.length; i++){
			System.arraycopy(mat[i], 0, new_mat[i], 0, mat.length);
		}
		mat = new_mat;
	}

	@Override
	public void addVertices(int n) {
		double[][] new_mat = new double[mat.length+n][mat.length+n];
		for(int i = 0; i < mat.length; i++){
			System.arraycopy(mat[i], 0, new_mat[i], 0, mat.length);
		}
		mat = new_mat;
	}

	@Override
	public List<Integer> getNeighbors(int v) {
		List<Integer> l = new ArrayList<>();
		if(checkBounds(v,v)){
			for(int i = 0; i < mat.length;i++){
				if(mat[v][i] >0 ){
					l.add(i);
				}
			}
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
		return mat.length;
	}

	@Override
	public double getEdgeWeight(int start, int end) {
		if(checkBounds(start,end))
		{
			return (mat[start][end]> 0) ? mat[start][end] : Double.POSITIVE_INFINITY ;
		}
		return Double.POSITIVE_INFINITY;
	}

	@Override
	public boolean hasEdge(int start, int end) {
		if(checkBounds(start,end))
		{
			return mat[start][end] > 0;
		}
		return false;
	}

	@Override
	public void removeEdge(int start, int end) {
		if(checkBounds(start,end))
		{
			mat[start][end] = 0;
		}
	}

	@Override
	public void removeVertex() {
		double[][] new_mat = new double[mat.length-1][mat.length-1];
		for(int i = 0; i < mat.length; i++){
			System.arraycopy(mat[i], 0, new_mat[i], 0, mat.length);
		}
		mat = new_mat;
	}

	@Override
	public boolean isWeighted() {
		return weighted;
	}

	@Override
	public boolean isDirected() {
		return false;
	}

	private boolean checkBounds(int start, int end){
		if(start > mat.length || end > mat.length)
		{
			System.err.println("Es gibt keinen Vertex mit:" + start +" oder " + end);
//			throw new NoVertexExcept("Es gibt keinen Vertex mit:" + start +" oder " + end);
			return false;
		}
		return true;
	}

	public void print(){
		for (double[] doubles : mat) {
			for (int j = 0; j < mat.length; j++) {
				System.out.printf("%.3f ", doubles[j]);
			}
			System.out.println();
		}
	}


}
