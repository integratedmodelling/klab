package org.integratedmodelling.klab.documentation.extensions;

import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.IAnnotation;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;

public class GraphDocumentationExtension {
	
	private IAnnotation definition;
	private IRuntimeScope scope;
	private IObservable target;

	public GraphDocumentationExtension(IAnnotation definition, IObservable target, IRuntimeScope scope) {
		this.definition = definition;
		this.scope = scope;
		this.target = target;
	}
	
	public String compile() {
		return "**GRAPH HERE: EXTENSION IS UNIMPLEMENTED**";
	}

}
