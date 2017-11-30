package org.integratedmodelling.klab.kim;

import java.util.EnumSet;

import org.integratedmodelling.kim.api.IKimConcept;
import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.kim.api.IKimConceptStatement;
import org.integratedmodelling.kim.api.IKimScope;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.model.INamespace;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;

public enum ConceptBuilder {

	INSTANCE;

	public IConcept build(IKimConceptStatement concept, INamespace namespace, IMonitor monitor) {
		return build(concept, namespace, getCoreType(concept.getType()), monitor);
	}

	public IConcept build(IKimConceptStatement concept, INamespace namespace, IConcept parent, IMonitor monitor) {

		IConcept main = null;

		/*
		 * main type
		 */
		if (concept.getName() != null) {

		}

		/*
		 * parent: ignore the passed parent if one is given
		 */
		
		for (IKimScope child : concept.getChildren()) {
		    if (child instanceof IKimConceptStatement) {
		        
		    }
		}

		return null;
	}

	public IConcept declare(IKimConcept concept, IMonitor monitor) {

		IConcept main = null;

		if (concept.getName() != null) {

		}

		return null;
	}

	public IConcept getCoreType(EnumSet<Type> type) {
		return null;
	}
}
