package org.integratedmodelling.klab.provenance;

import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.provenance.IAssociation;
import org.jgrapht.graph.DefaultEdge;

public class ProvenanceEdge extends DefaultEdge implements IAssociation {

	private static final long serialVersionUID = 1013844289682153561L;

	private long timestamp = System.currentTimeMillis();
	private Type type;
	private IScale scale;
	
	public ProvenanceEdge(IScale scale2) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public long getTimestamp() {
		return timestamp;
	}

	@Override
	public Type getType() {
		return type;
	}
	
	public IScale getScale() {
		return scale;
	}

	public void merge(IScale scale2) {
		// TODO Auto-generated method stub
		
	}
}
