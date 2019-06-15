package org.integratedmodelling.klab.resolution;

import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.resolution.IResolvable;
import org.integratedmodelling.klab.owl.Observable;

/**
 * Bean to store a previously resolved artifact when a dataflow using it is
 * built.
 * 
 * @author ferdinando.villa
 *
 */
public class ResolvedArtifact implements IResolvable {

	private IObservation artifact;
	private String artifactId;
	private Observable observable;

	public ResolvedArtifact(Observable observable, IObservation artifact, String artifactId) {
		this.artifact = artifact;
		this.artifactId = artifactId;
		this.observable = observable;
	}

	public IObservation getArtifact() {
		return artifact;
	}

	public void setArtifact(IObservation artifact) {
		this.artifact = artifact;
	}

	public String getArtifactId() {
		return artifactId;
	}

	public void setArtifactId(String artifactId) {
		this.artifactId = artifactId;
	}

	/**
	 * The observable that this artifact resolves. Will be the same but possibly
	 * with a different name.
	 * 
	 * @return
	 */
	public Observable getObservable() {
		return observable;
	}

}
