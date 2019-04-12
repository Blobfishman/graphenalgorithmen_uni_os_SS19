package de.uos.inf.ko.ga.graph;

import de.uos.inf.ko.ga.graph.impl.DirectedGraphList;

public class DirectedGraphListTest extends DirectedGraphTest<DirectedGraphList> {

	@Override
	protected Graph createGraph() {
		return new DirectedGraphList();
	}

}
