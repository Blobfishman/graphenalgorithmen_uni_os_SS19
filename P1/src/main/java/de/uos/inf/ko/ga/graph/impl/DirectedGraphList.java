package de.uos.inf.ko.ga.graph.impl;

import java.util.ArrayList;
import java.util.List;

import de.uos.inf.ko.ga.graph.Graph;
import de.uos.inf.ko.ga.graph.util.Pair;

/**
 * Implementation of a directed graph with a list representation of the edges.
 *
 */
public class DirectedGraphList implements Graph {

	private boolean weighted = false;
	private ArrayList<ArrayList<Pair<Integer,Double>>> lists = new ArrayList<>();

	@Override
	public void addEdge(int start, int end) {
		if(checkBounds(start,end)){
			lists.get(start).add(new Pair<>(end,1.0));
		}
	}

	@Override
	public void addEdge(int start, int end, double weight) {
		if(checkBounds(start,end)){
			if(weight != 0) weighted = true;
			lists.get(start).add(new Pair<>(end,weight));
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
		List<Integer> list = new ArrayList<>();

		for (int i=0; i< lists.size(); i++) {
			for (Pair<Integer, Double> l: lists.get(i)) {
				if (v == l.getFirst())
					list.add(i);
			}
		}

		return list;
	}

	@Override
	public List<Integer> getSuccessors(int v) {
		List<Integer> l = new ArrayList<>();
		if(checkBounds(v,v)){
			for(Pair<Integer,Double> e: lists.get(v)){
				l.add(e.getFirst());
			}
		}
		return l;
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
		return true;
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
