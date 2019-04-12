package de.uos.inf.ko.ga.graph;

import de.uos.inf.ko.ga.graph.impl.DirectedGraphMatrix;

public class DirectedGraphMatrixTest extends DirectedGraphTest<DirectedGraphMatrix> {

	@Override
	protected Graph createGraph() {
		return new DirectedGraphMatrix();
	}

}
