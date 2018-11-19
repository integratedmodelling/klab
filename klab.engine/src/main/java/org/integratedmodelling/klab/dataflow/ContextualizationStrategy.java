package org.integratedmodelling.klab.dataflow;

import java.util.List;

import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

/**
 * A tree of dataflows, possibly connected through internal links. Root nodes are arranged chronologically
 * and correspond to user-generated observations. Child nodes result from the resolution of instantiated
 * countables.
 * <p>
 * Each context keeps one of these and adds to it as the user makes new observations. Dataflows are added 
 * after resolution, independent of whether the contextualization after it is successful.
 * 
 * @author Ferd
 *
 */
public class ContextualizationStrategy extends DefaultDirectedGraph<Dataflow, DefaultEdge>{
	
	public ContextualizationStrategy() {
		super(DefaultEdge.class);
	}

	private static final long serialVersionUID = 1L;

	List<Dataflow> getRootNodes() {
		return null;
	}
	
}
