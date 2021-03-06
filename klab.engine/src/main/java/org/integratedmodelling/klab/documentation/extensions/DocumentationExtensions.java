package org.integratedmodelling.klab.documentation.extensions;

import java.util.Map;

import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.IAnnotation;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
import org.integratedmodelling.klab.exceptions.KlabValidationException;

public enum DocumentationExtensions {

	INSTANCE;

	/**
	 * Extension annotations that specify items to be computed and inserted in
	 * documentation when specified. The enum names correspond to annotations
	 * supported by the engine, which must all admit parameters <code>id</code> and
	 * <code>when</code>, the latter specifying one of the triggers below.
	 * 
	 * @author Ferd
	 *
	 */
	public enum Annotation {
		graph, table;
	}

	/**
	 * Possible values of the 'when' parameter in the admitted annotations.
	 * 
	 * @author Ferd
	 *
	 */
	public enum Trigger {
		start, ondemand, end
	}

	/**
	 * Call to see if the annotation is fine to call in the passed scope and when.
	 * If this function returns null, the annotation is not recognized as a report
	 * extension or it does not apply to the scope (e.g. requires temporal
	 * descriptions and the scope is not temporal). If parameters are invalid, throw
	 * a KlabValidationException.
	 * 
	 * @param definition
	 * @param scope
	 */
	public Annotation validate(IAnnotation definition, IRuntimeScope scope) {

		boolean ok = false;
		Annotation ret = null;
		for (Annotation a : Annotation.values()) {
			if (a.name().equals(definition.getName())) {
				ret = a;
				break;
			}
		}

		if (ret != null) {
			Trigger trigger = Trigger.ondemand;
			String when = definition.get("when", String.class);
			if (when != null) {
				try {
					trigger = Trigger.valueOf(when);
				} catch (IllegalArgumentException e) {
					throw new KlabValidationException(e);
				}
			}

			/*
			 * TODO more validation vs. context
			 */
			return ret;
		}
		return null;
	}

	public String compute(Annotation type, Map<?,?> definition, IRuntimeScope scope, IObservable target) {
		switch (type) {
		case table:
			return new TableDocumentationExtension(definition, target, scope).compile();
		case graph:
			return new GraphDocumentationExtension(definition, target, scope).compile();
		}
		return "**UNIMPLEMENTED DOCUMENTATION EXTENSION " + type + "**";
	}

}
