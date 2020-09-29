package org.integratedmodelling.klab.documentation.extensions;

import java.util.Map;

import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;

@Deprecated
public class GraphDocumentationExtension {
	
	private Map<?,?> definition;
	private IRuntimeScope scope;
	private IObservable target;

	public GraphDocumentationExtension(Map<?,?> definition, IObservable target, IRuntimeScope scope) {
		this.definition = definition;
		this.scope = scope;
		this.target = target;
	}
	
	public String compile() {
		return "**GRAPH HERE: EXTENSION IS UNIMPLEMENTED**";
	}

}
