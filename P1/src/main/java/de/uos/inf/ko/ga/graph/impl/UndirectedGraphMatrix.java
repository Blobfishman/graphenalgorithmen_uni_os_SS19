package de.uos.inf.ko.ga.graph.impl;

import java.util.List;

import de.uos.inf.ko.ga.graph.Graph;

/**
 * Implementation of a undirected graph with a matrix representation of the edges.
 *
 */
public class UndirectedGraphMatrix implements Graph {

	@Override
	public void addEdge(int start, int end) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addEdge(int start, int end, double weight) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addVertex() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addVertices(int n) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Integer> getNeighbors(int v) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Integer> getPredecessors(int v) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Integer> getSuccessors(int v) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getVertexCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getEdgeWeight(int start, int end) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean hasEdge(int start, int end) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void removeEdge(int start, int end) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeVertex() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isWeighted() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isDirected() {
		// TODO Auto-generated method stub
		return false;
	}
}
