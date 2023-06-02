package org.insa.graphs.algorithm.utils;

import org.insa.graphs.algorithm.shortestpath.ShortestPathData;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Point;
import org.insa.graphs.algorithm.AbstractInputData;

public class LabelStar extends Label implements Comparable<Label>{
	private float inf;

	public LabelStar(Node noeud, ShortestPathData data) {
		super(noeud);

		if (data.getMode() == AbstractInputData.Mode.LENGTH) {
			this.inf = (float)Point.distance(noeud.getPoint(),data.getDestination().getPoint());
		}
		else {
			int vitesse = Math.max(data.getMaximumSpeed(), data.getGraph().getGraphInformation().getMaximumSpeed());
			this.inf = (float)Point.distance(noeud.getPoint(),data.getDestination().getPoint())/(vitesse*1000.0f/3600.0f);
		}
	}

	@Override
	public float getTotalCost() {
		return this.inf*1f+this.cost*1.5f;
	}

}
