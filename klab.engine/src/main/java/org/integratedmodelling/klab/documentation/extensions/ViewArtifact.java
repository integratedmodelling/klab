package org.integratedmodelling.klab.documentation.extensions;

import java.io.File;

import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.knowledge.IKnowledgeView;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
import org.integratedmodelling.klab.provenance.Artifact;

public abstract class ViewArtifact extends Artifact {

	IObservation target;
	IKnowledgeView view;
	
	
	@Override
	public IGeometry getGeometry() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Type getType() {
		return Type.VOID;
	}

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IArtifact getGroupMember(int n) {
		return null;
	}

	public abstract void compute(IRuntimeScope scope);

	public abstract String compile();

	public abstract void export(File file);

}
