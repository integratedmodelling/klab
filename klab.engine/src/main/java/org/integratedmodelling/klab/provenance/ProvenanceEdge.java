package org.integratedmodelling.klab.provenance;

import org.integratedmodelling.klab.api.provenance.IAssociation;
import org.jgrapht.graph.DefaultEdge;

public class ProvenanceEdge extends DefaultEdge implements IAssociation {

	private static final long serialVersionUID = 1013844289682153561L;

	private long timestamp = System.currentTimeMillis();
	private Type type;
	
	@Override
	public long getTimestamp() {
		return timestamp;
	}

	@Override
	public Type getType() {
		return type;
	}
}
