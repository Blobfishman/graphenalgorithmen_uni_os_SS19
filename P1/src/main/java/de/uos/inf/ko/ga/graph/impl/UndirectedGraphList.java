package de.uos.inf.ko.ga.graph.impl;

import java.util.*;

import de.uos.inf.ko.ga.graph.Graph;
import de.uos.inf.ko.ga.graph.util.Pair;

/**
 * Implementation of an undirected graph with a list representation of the edges.
 *
 */
public class UndirectedGraphList implements Graph {
	private boolean weighted = false;
	private ArrayList<ArrayList<Pair<Integer,Double>>> lists = new ArrayList<>();

	@Override
	public void addEdge(int start, int end) {
		addEdge(start,end,1);
	}

	@Override
	public void addEdge(int start, int end, double weight) {
		if(checkBounds(start,end)){
			if(weight != 1) weighted = true;
			lists.get(start).add(new Pair<>(end,weight));
			lists.get(end).add(new Pair<>(start,weight));
		}
	}

	@Override
	public void addVertex() {
		lists.add(lists.size(), new ArrayList<>());
	}

	@Override
	public void addVertices(int n) {
		for(int i = 0; i < n; i++){
			lists.add(lists.size(), new ArrayList<>());
		}
	}

	@Override
	public List<Integer> getNeighbors(int v) {
		List<Integer> l = new ArrayList<>();
		if(checkBounds(v,v)){
			for(Pair<Integer,Double> e: lists.get(v)){
				l.add(e.getFirst());
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
		return lists.size();
	}

	@Override
	public double getEdgeWeight(int start, int end) {
		if(checkBounds(start,end)){
			for(Pair<Integer,Double> e: lists.get(start)){
				if(e.getFirst() == end) return  e.getSecond();
			}
		}
		return Double.POSITIVE_INFINITY;
	}

	@Override
	public boolean hasEdge(int start, int end) {
		if(!checkBounds(start,end)) return false;
		Pair<Integer,Double> tmp;
		for (Pair<Integer, Double> integerDoublePair : lists.get(start)) {
			tmp = integerDoublePair;
			if (tmp != null && tmp.getFirst() == end) return true;
		}
		return false;
	}

	@Override
	public void removeEdge(int start, int end) {
		if(checkBounds(start,end)) {
			lists.get(start).removeIf(tmp -> tmp.getFirst() == end);
			lists.get(end).removeIf(tmp -> tmp.getFirst() == start);
		}
	}

	@Override
	public void removeVertex() {
		lists.remove(lists.size()-1);
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

	}

	private boolean checkBounds(int start, int end){
		if(start > lists.size() || end > lists.size())
		{
			System.err.println("Es gibt keinen Vertex mit:" + start +" oder " + end);
			//throw new NoVertexExcept("Es gibt keinen Vertex mit:" + start +" oder " + end);
			return false;
		}
		return true;
	}
}
