package org.integratedmodelling.klab.documentation;

import java.util.Map;

import org.integratedmodelling.klab.api.documentation.IDocumentationProvider;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.documentation.extensions.DocumentationExtensions;
import org.integratedmodelling.klab.documentation.extensions.DocumentationExtensions.Trigger;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;

public class DocumentationItem implements IDocumentationProvider.Item {

	private Trigger trigger = Trigger.ondemand;
	// this will never be filled in if trigger is on demand
	private String compiled = null;
	private String id;
	private String title;
	private DocumentationExtensions.Annotation type;
	IRuntimeScope scope;
	IObservable target;
	Map<?,?> definition;
	
	public DocumentationItem(DocumentationExtensions.Annotation type, Map<?,?> definition, IRuntimeScope context, IObservable target) {
		this.id = definition.containsKey("id") ? definition.get("id").toString() : null;
		this.title = definition.containsKey("title") ? definition.get("title").toString() : null;
		this.type = type;
		this.scope = context;
		this.target = target;
		this.definition = definition;
	}
	
	@Override
	public String getId() {
		return this.id;
	}

	@Override
	public String getTitle() {
		return this.title;
	}

	@Override
	public String getMarkdownContents() {

		String ret = this.compiled;
		if (ret == null) {
	
			/*
			 * if termination, we produce the text only if scheduler is null or we have finished computing a cycle.
			 */
			boolean finished = scope.getScheduler() == null || scope.getScheduler().isFinished();
			if (this.trigger == Trigger.end && !finished) {
				return "";
			}
		
			ret = DocumentationExtensions.INSTANCE.compute(type, definition, scope, target);
			
			/*
			 * save for later only if not on demand
			 */
			if (this.trigger != Trigger.ondemand) {
				this.compiled = ret;
			}
			
		}
		return ret;
	}

	public DocumentationExtensions.Annotation getType() {
		return type;
	}

}
