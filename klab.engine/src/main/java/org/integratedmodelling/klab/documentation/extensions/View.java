package org.integratedmodelling.klab.documentation.extensions;

import java.io.File;

import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.knowledge.IKnowledgeView;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
import org.integratedmodelling.klab.provenance.Artifact;
import org.integratedmodelling.klab.utils.NameGenerator;

/**
 * 
 * @author Ferd
 *
 * @param <T> the type of the final "published" view - a string for HTML, video
 *            file, etc.
 * @param <E> the type of the result computed by the view's "compute" method,
 *            which must be reentrant.
 */
public abstract class View<T, E> {

	protected IObservation target;
	protected IKnowledgeView view;
	private String id = "v" + NameGenerator.shortUUID();

	public String getId() {
		return id;
	}

	public abstract E compute(IObservation target, IRuntimeScope scope);

	public abstract T compile(E result);

	public abstract void export(File file, E result);

}
