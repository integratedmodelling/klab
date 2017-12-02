package org.integratedmodelling.klab.kim;

import java.util.ArrayList;
import java.util.List;

import org.integratedmodelling.kim.api.IKimConcept;
import org.integratedmodelling.kim.api.IKimConceptStatement;
import org.integratedmodelling.kim.api.IKimScope;
import org.integratedmodelling.kim.model.KimConceptStatement.ParentConcept;
import org.integratedmodelling.klab.Concepts;
import org.integratedmodelling.klab.Workspaces;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.model.INamespace;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.engine.resources.CoreOntology;
import org.integratedmodelling.klab.model.Namespace;
import org.integratedmodelling.klab.owl.Axiom;
import org.integratedmodelling.klab.owl.OWL;

public enum ConceptBuilder {

	INSTANCE;

	public IConcept build(IKimConceptStatement concept, INamespace namespace, IMonitor monitor) {
	    Namespace ns = (Namespace)namespace;
		IConcept ret = buildInternal(concept, ns, monitor);
		if (concept.getParents().isEmpty()) {
		    IConcept parent =
		            concept.getUpperConceptDefined() != null ? 
		                    Concepts.c(concept.getUpperConceptDefined()) :
		                    Workspaces.INSTANCE.getUpperOntology().getCoreType(concept.getType());
		    ns.addAxiom(Axiom.SubClass(ret.getLocalName(), parent.getUrn()));
		    ns.define();
		}
		return ret;
	}

	private IConcept buildInternal(IKimConceptStatement concept, Namespace namespace, IMonitor monitor) {

		IConcept main = null;
		String mainId = concept.getName();
		
		System.out.println("BUILT CONCEPT " + mainId);
		
		namespace.addAxiom(Axiom.ClassAssertion(mainId, concept.getType()));

		/*
		 * basic attributes
		 *    subjective
		 *    deniable
		 *    internal
		 *    uni/bidirectional (relationship)
		 */
		if (concept.isAbstract()) {
		    namespace.addAxiom(Axiom.AnnotationAssertion(mainId, CoreOntology.NS.IS_ABSTRACT, "true"));
		}
		
		namespace.define();
		main = namespace.getOntology().getConcept(mainId);
		
		for (ParentConcept parent : concept.getParents()) {
		    
		    List<IConcept> concepts = new ArrayList<>();
		    for (IKimConcept pdecl : parent.getConcepts()) {
		        concepts.add(declare(pdecl, monitor));
		    }
		    
		    if (concepts.size() == 1) {
		        namespace.addAxiom(Axiom.SubClass(concepts.get(0).getUrn(), mainId));
		    } else {
		        IConcept expr = null;
		        switch (parent.getConnector()) {
		        case INTERSECTION:
		            expr = OWL.INSTANCE.getIntersection(concepts, namespace.getOntology());
		            break;
		        case UNION:
                    expr = OWL.INSTANCE.getUnion(concepts, namespace.getOntology());
		            break;
		         default:
		             // won't happen
		             break;
		        }
                namespace.addAxiom(Axiom.SubClass(expr.getUrn(), mainId));
		    }
            namespace.define();
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
}
