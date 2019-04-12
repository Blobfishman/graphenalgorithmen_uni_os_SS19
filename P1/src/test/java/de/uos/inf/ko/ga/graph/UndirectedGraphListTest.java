package de.uos.inf.ko.ga.graph;

import de.uos.inf.ko.ga.graph.Graph;
import de.uos.inf.ko.ga.graph.impl.UndirectedGraphList;

public class UndirectedGraphListTest extends UndirectedGraphTest<UndirectedGraphList> {

	@Override
	protected Graph createGraph() {
		return new UndirectedGraphList();
	}

}
