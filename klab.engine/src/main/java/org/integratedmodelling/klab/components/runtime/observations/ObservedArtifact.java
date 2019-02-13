package org.integratedmodelling.klab.components.runtime.observations;

import java.util.HashSet;
import java.util.Set;

import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.provenance.IProvenance;
import org.integratedmodelling.klab.data.Metadata;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeContext;
import org.integratedmodelling.klab.provenance.Artifact;
import org.integratedmodelling.klab.utils.NameGenerator;

/**
 * Simple abstract observation data class for storage components. Just
 * implements the basic iterator functions and access to simple final fields.
 * 
 * @author Ferd
 *
 */
public abstract class ObservedArtifact extends Artifact implements IArtifact {

	private IGeometry geometry;
	private IRuntimeContext runtimeContext;
	private IMetadata metadata = new Metadata();
	private String token = "o" + NameGenerator.shortUUID();
	private boolean markedForDeletion;

	protected ObservedArtifact() {
	}
	
	public ObservedArtifact(IGeometry geometry, IRuntimeContext context) {
		this.geometry = geometry;
		this.runtimeContext = context;
	}

	public String getId() {
		return token;
	}
	
	
	// TODO REMOVE
	Set<String> ids = new HashSet<>();
	public void chain(IArtifact data) {
		super.chain(data);
		ids.add(((ObservedArtifact)data).token);
	}


	@Override
	public IGeometry getGeometry() {
		return geometry;
	}

	public void setGeometry(IGeometry geometry) {
		this.geometry = geometry;
	}

	
	@Override
	public IMetadata getMetadata() {
		return metadata;
	}

	public IRuntimeContext getRuntimeContext() {
		return this.runtimeContext;
	}

	public ObservedArtifact getParent() {
		return null;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((token == null) ? 0 : token.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ObservedArtifact other = (ObservedArtifact) obj;
		if (token == null) {
			if (other.token != null)
				return false;
		} else if (!token.equals(other.token))
			return false;
		return true;
	}

	@Override
	public IProvenance getProvenance() {
		return getRuntimeContext().getProvenance();
	}

	protected void setMetadata(IMetadata metadata) {
		this.metadata = metadata;
	}

	/**
	 * If this returns true, the observation is discarded after creation and not inserted in
	 * the context. Used by filtering contextualizers.
	 * 
	 * @return
	 */
	public boolean isMarkedForDeletion() {
		return markedForDeletion;
	}

	/**
	 * Used by filtering contextualizers to mark observations that won't make it to the
	 * context.
	 * 
	 * @param delete
	 */
	public void setMarkedForDeletion(boolean delete) {
		this.markedForDeletion = delete;
	}

}
