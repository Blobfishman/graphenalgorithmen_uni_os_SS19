package de.uos.inf.ko.ga.graph;

import de.uos.inf.ko.ga.graph.impl.UndirectedGraphList;
import de.uos.inf.ko.ga.graph.impl.UndirectedGraphMatrix;

public class UndirectedGraphMatrixTest extends UndirectedGraphTest<UndirectedGraphList> {

	@Override
	protected Graph createGraph() {
		return new UndirectedGraphMatrix();
	}

}
