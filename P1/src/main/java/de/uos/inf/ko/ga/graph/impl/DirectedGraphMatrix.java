package de.uos.inf.ko.ga.graph.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.DeflaterOutputStream;

import de.uos.inf.ko.ga.graph.Graph;

/**
 * Implementation of a directed graph with a matrix representation of the edges.
 *
 */
public class DirectedGraphMatrix implements Graph {

	private boolean weighted = false;
	private  double[][] mat = new double[0][1];

	@Override
	public void addEdge(int start, int end) {
		addEdge(start, end, 0);
	}

	@Override
	public void addEdge(int start, int end, double weight) {
		if (!checkBounds(start, end)) return;
		if (weight != 0) weighted = true;
		mat[start][end] = weight;
	}

	@Override
	public void addVertex() {
		addVertices(1);
	}

	@Override
	public void addVertices(int n) {
		double[][] new_mat = new double[mat.length+n][mat.length+n];
		for(int i = 0; i < mat.length; i++){
			System.arraycopy(mat[i], 0, new_mat[i], 0, mat.length);
			Arrays.fill(new_mat[i], mat.length, new_mat.length-1, Double.POSITIVE_INFINITY);
		}
		for (int i = mat.length; i<new_mat.length; i++){
			Arrays.fill(new_mat[i], Double.POSITIVE_INFINITY);
		}
		mat = new_mat;
	}

	@Override
	public List<Integer> getNeighbors(int v) {
		List<Integer> list = getPredecessors(v);
		list.addAll(getSuccessors(v));

		for (int i=0; i<list.size(); i++) {
			if(list.lastIndexOf(list.get(i)) != list.indexOf(list.get(i))) {
				list.remove(list.lastIndexOf(list.get(i)));
			}
		}

		return list;
	}

	@Override
	public List<Integer> getPredecessors(int v) {
		List<Integer> l = new ArrayList<>();
		if(checkBounds(v,v)){
			for(int i = 0; i < mat.length;i++){
				if(mat[i][v] != Double.POSITIVE_INFINITY ){
					l.add(i);
				}
			}
		}
		return l;
	}

	@Override
	public List<Integer> getSuccessors(int v) {
		List<Integer> l = new ArrayList<>();
		if(checkBounds(v,v)){
			for(int i = 0; i < mat.length;i++){
				if(mat[v][i] != Double.POSITIVE_INFINITY ){
					l.add(i);
				}
			}
		}
		return l;
	}

	@Override
	public int getVertexCount() {
		return mat.length;
	}

	@Override
	public double getEdgeWeight(int start, int end) {
		if(checkBounds(start,end))
		{
			return mat[start][end];
		}
		return Double.POSITIVE_INFINITY;
	}

	@Override
	public boolean hasEdge(int start, int end) {
		if(checkBounds(start,end))
		{
			return mat[start][end] != Double.POSITIVE_INFINITY;
		}
		return false;
	}

	@Override
	public void removeEdge(int start, int end) {
		if(checkBounds(start,end))
		{
			mat[start][end] = Double.POSITIVE_INFINITY;
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
		return true;
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
