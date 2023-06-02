package org.insa.graphs.algorithm.shortestpath;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import org.insa.graphs.algorithm.AbstractSolution.Status;
import org.insa.graphs.algorithm.utils.*;
import org.insa.graphs.model.*;

public class DijkstraAlgorithm extends ShortestPathAlgorithm {

    public DijkstraAlgorithm(ShortestPathData data) {
        super(data);
        this.nbSommetsVisites = 0;
    }
    
    protected int nbSommetsVisites;
	protected int nbSommets;
	
	/* Crée et retourne le Label correspondant au Node */
	protected Label newLabel(Node node, ShortestPathData data) {
		return new Label(node);
	}

    @Override
    protected ShortestPathSolution doRun() {
    	boolean fin = false;
		ShortestPathData data = getInputData();
		Graph graph = data.getGraph();
		int tailleGraphe = graph.size();

		ShortestPathSolution solution = null;

		Label tabLabels[] = new Label [tailleGraphe];

		BinaryHeap<Label> tas = new BinaryHeap<Label>();

		Arc[] predecessorArcs = new Arc[tailleGraphe];

		Label deb = newLabel(data.getOrigin(), data);
		tabLabels[deb.getNode().getId()] = deb;
		tas.insert(deb);
		deb.setInTas();
		deb.setCost(0);
		
		notifyOriginProcessed(data.getOrigin());

		while(!tas.isEmpty() && !fin){ 
			Label current= tas.deleteMin();
			notifyNodeMarked(current.getNode());
			current.setMark();
			if (current.getNode() == data.getDestination()) {
				fin = true;
			}
			Iterator<Arc> arc = current.getNode().iterator();;
			while (arc.hasNext()) {
				Arc arcIter = arc.next();

				if (!data.isAllowed(arcIter)) {
					continue;
				}

				Node successeur = arcIter.getDestination();

				Label successeurLabel = tabLabels[successeur.getId()];
				
				if (successeurLabel == null) {
					successeurLabel = newLabel(successeur, data);
					tabLabels[successeurLabel.getNode().getId()] = successeurLabel;
					notifyNodeReached(successeur);
				}
				
				if (!successeurLabel.getMark()) {
					if((successeurLabel.getTotalCost()>(current.getCost()+data.getCost(arcIter)
						+(successeurLabel.getTotalCost()-successeurLabel.getCost()))) 
						|| (successeurLabel.getCost()==Float.POSITIVE_INFINITY)){
						successeurLabel.setCost(current.getCost()+(float)data.getCost(arcIter));
						successeurLabel.setFather(current.getNode());
						if(successeurLabel.getInTas()) {
							tas.remove(successeurLabel);
						}
						else {
							successeurLabel.setInTas();
						}
						tas.insert(successeurLabel);
						predecessorArcs[arcIter.getDestination().getId()] = arcIter;
					}
				}
			}
		
		}
		if (predecessorArcs[data.getDestination().getId()] == null) {
			solution = new ShortestPathSolution(data, Status.INFEASIBLE);
		} else {
			ArrayList<Arc> arcs = new ArrayList<>();
			Arc arc = predecessorArcs[data.getDestination().getId()];

			while (arc != null) {
				arcs.add(arc);
				arc = predecessorArcs[arc.getOrigin().getId()];
			}
			Collections.reverse(arcs);
			solution = new ShortestPathSolution(data, Status.OPTIMAL, new Path(graph, arcs));
		}	
        return solution;
    }

}
