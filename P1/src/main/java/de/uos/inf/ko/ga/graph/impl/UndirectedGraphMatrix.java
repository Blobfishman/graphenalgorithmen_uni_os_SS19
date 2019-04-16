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

	private final int SIZE = 16;
	private int counter = 0;
	private boolean weighted = false;
	private  double[][] mat = new double[SIZE][SIZE];

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
		counter++;
		checkAndFixSize();
	}

	@Override
	public void addVertices(int n) {
		counter += n;
		checkAndFixSize();
	}

	@Override
	public List<Integer> getNeighbors(int v) {
		List<Integer> l = new ArrayList<Integer>();
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
		return counter;
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
		counter --;
	}

	@Override
	public boolean isWeighted() {
		return weighted;
	}

	@Override
	public boolean isDirected() {
		return false;
	}

	/**
	 * If array has not enouth space double the size and copy all old data
	 * to the new array
	 */
	private  void checkAndFixSize(){
		if(counter > SIZE){
			double[][] new_mat = new double[2*mat.length][2* mat.length];
			for(int i = 0; i < mat.length; i++){
				for(int j = 0; j < mat.length; j++){
					new_mat[i][j] = mat[i][j];
				}
			}
			mat = new_mat;
		}
	}

	private boolean checkBounds(int start, int end){
		if(start > counter || end > counter)
		{
			System.err.println("Es gibt keinen Vertex mit:" + start +" oder " + end);
			//throw new NoVertexExcept("Es gibt keinen Vertex mit:" + start +" oder " + end);
			return false;
		}
		return true;
	}

	public void print(){
		for(int i = 0; i < mat.length; i++){
			for(int j = 0; j < mat.length; j++){
				System.out.printf("%.3f ", mat[i][j]);
			}
			System.out.println();
		}
	}


}
