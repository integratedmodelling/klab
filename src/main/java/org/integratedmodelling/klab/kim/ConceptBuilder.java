package org.integratedmodelling.klab.kim;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import org.integratedmodelling.kim.api.IKimConcept;
import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.kim.api.IKimConceptStatement;
import org.integratedmodelling.kim.api.IKimScope;
import org.integratedmodelling.kim.model.KimConceptStatement;
import org.integratedmodelling.kim.model.KimConceptStatement.ParentConcept;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.model.INamespace;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.model.Namespace;
import org.integratedmodelling.klab.owl.Axiom;

public enum ConceptBuilder {

	INSTANCE;

	public IConcept build(IKimConceptStatement concept, INamespace namespace, IMonitor monitor) {
	    Namespace ns = (Namespace)namespace;
		IConcept ret = buildInternal(concept, ns, monitor);
		if (concept.getParents().isEmpty()) {
		    IConcept parent = getCoreType(concept.getType());
		    ns.addAxiom(Axiom.SubClass(ret.getLocalName(), parent.getUrn()));
		    ns.define();
		}
		return ret;
	}

	private IConcept buildInternal(IKimConceptStatement concept, Namespace namespace, IMonitor monitor) {

		IConcept main = null;
		String mainId = null;
		
		/*
		 * main type
		 */
		if (concept.getName() != null) {
		    if (concept.getName().equals(KimConceptStatement.ROOT_DOMAIN_NAME)) {
		        mainId = "Domain";
		    } else {
		        mainId = concept.getName();
		    }
		}
		
		namespace.addAxiom(Axiom.ClassAssertion(mainId, concept.getType()));

		
		namespace.define();
		main = namespace.getOntology().getConcept(mainId);
		
		for (ParentConcept parent : concept.getParents()) {
		    List<IConcept> concepts = new ArrayList<>();
		    for (IKimConcept pdecl : parent.getConcepts()) {
		        concepts.add(declare(pdecl, monitor));
		    }
		    if (concepts.size() == 1) {
		        namespace.addAxiom(Axiom.SubClass(concepts.get(0).getUrn(), mainId));
		        namespace.define();
		    } else {
		        // TODO create union/intersection/negation and set it as parent
		    }
		}
		
		for (IKimScope child : concept.getChildren()) {
		    if (child instanceof IKimConceptStatement) {
		        IConcept childConcept = buildInternal((IKimConceptStatement)child, namespace, monitor);
		        namespace.addAxiom(Axiom.SubClass(mainId, childConcept.getLocalName()));
		        namespace.define();
		    }
		}

		return main;
	}

	public IConcept declare(IKimConcept concept, IMonitor monitor) {

		IConcept main = null;

		if (concept.getName() != null) {

		}

		return main;
	}

	public IConcept getCoreType(EnumSet<Type> type) {
		return null;
	}
}
