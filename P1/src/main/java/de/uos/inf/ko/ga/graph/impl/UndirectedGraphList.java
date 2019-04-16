package de.uos.inf.ko.ga.graph.impl;

import java.util.*;

import de.uos.inf.ko.ga.graph.Graph;
import de.uos.inf.ko.ga.graph.util.Pair;

/**
 * Implementation of an undirected graph with a list representation of the edges.
 *
 */
public class UndirectedGraphList implements Graph {

	private int counter = 0;
	private boolean weighted = false;
	private HashMap<Integer, ArrayList<Pair<Integer,Double>>> lists = new HashMap<>();

	@Override
	public void addEdge(int start, int end) {
		addEdge(start,end,0);
	}

	@Override
	public void addEdge(int start, int end, double weight) {
		if(checkBounds(start,end)){
			if(weight != 0) weighted = true;
			lists.get(start).add(new Pair<>(end,weight));
			lists.get(end).add(new Pair<>(start,weight));
		}
	}

	@Override
	public void addVertex() {
		lists.put(counter,new ArrayList<>());
		counter++;
		
	}

	@Override
	public void addVertices(int n) {
		for(int i = 0; i < n; i++){
			lists.put(counter,new ArrayList<>());
			counter++;
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
		return counter;
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
		for(Iterator<Pair<Integer,Double>> it = lists.get(start).iterator(); it.hasNext();) {
			tmp = it.next();
			if (tmp != null && tmp.getFirst() == end) return true;
		}
		return false;
	}

	@Override
	public void removeEdge(int start, int end) {
		if(checkBounds(start,end)) {
			for(Iterator<Pair<Integer,Double>> it = lists.get(start).iterator(); it.hasNext();){
				Pair<Integer,Double> tmp = it.next();
				if(tmp.getFirst() == end) it.remove();
			}
			for(Iterator<Pair<Integer,Double>> it = lists.get(end).iterator(); it.hasNext();){
				Pair<Integer,Double> tmp = it.next();
				if(tmp.getFirst() == start) it.remove();
			}
		}
	}

	@Override
	public void removeVertex() {
		lists.remove(counter -1);
		counter--;
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
		if(start > counter || end > counter)
		{
			System.err.println("Es gibt keinen Vertex mit:" + start +" oder " + end);
			//throw new NoVertexExcept("Es gibt keinen Vertex mit:" + start +" oder " + end);
			return false;
		}
		return true;
	}
}
