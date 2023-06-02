package org.insa.graphs.algorithm.shortestpath;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import org.insa.graphs.algorithm.AbstractSolution.Status;
import org.insa.graphs.algorithm.utils.*;
import org.insa.graphs.model.*;

public class AStarAlgorithm extends DijkstraAlgorithm {
    public AStarAlgorithm(ShortestPathData data) {
        super(data);
    }
	protected LabelStar newLabel(Node node, ShortestPathData data) {
		return new LabelStar(node,data);
	}
}
