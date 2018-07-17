package org.integratedmodelling.klab.provenance;

import org.jgrapht.graph.DefaultEdge;

public class ProvenanceEdge extends DefaultEdge {

	private static final long serialVersionUID = 1013844289682153561L;

	enum Type {
		/**
		 * Activity wasAssociatedWith Agent;
		 *
		 * Entity wasGeneratedBy Activity wasAssociatedWith Agent [if Agent = model,
		 * then Activity = {resolution|instantiation}
		 */
		wasAssociatedWith,
		/**
		 * Entity wasAttributedTo Agent (implicit Activity, for remote provenance)
		 */
		wasAttributedTo,
		/**
		 * Entity wasGeneratedBy Activity
		 */
		wasGeneratedBy,

	}

	Type type;
}
