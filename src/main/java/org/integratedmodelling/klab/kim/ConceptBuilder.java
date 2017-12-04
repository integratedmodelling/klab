package org.integratedmodelling.klab.kim;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

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

	public @Nullable IConcept build(IKimConceptStatement concept, INamespace namespace, IMonitor monitor) {

		if (concept.isMacro()) {
			return null;
		}
		
		Namespace ns = (Namespace) namespace;
		try {
			
			IConcept ret = buildInternal(concept, ns, monitor);
			if (concept.getParents().isEmpty()) {
				IConcept parent = null;
				if (concept.getUpperConceptDefined() != null) {
					parent = Concepts.INSTANCE.getConcept(concept.getUpperConceptDefined());
					if (parent == null) {
						monitor.error("Core concept " + concept.getUpperConceptDefined() + " unknown", concept);
					}
				} else {
					parent = Workspaces.INSTANCE.getUpperOntology().getCoreType(concept.getType());
				}

				if (parent != null) {
					ns.addAxiom(Axiom.SubClass(ret.getLocalName(), parent.getUrn()));
					ns.define();
				}
			}
			return ret;
			
		} catch (Throwable e) {
			monitor.error(e);
		}
		return null;
	}

	private @Nullable IConcept buildInternal(IKimConceptStatement concept, Namespace namespace, IMonitor monitor) {

		IConcept main = null;
		String mainId = concept.getName();

		System.out.println("BUILT CONCEPT " + mainId);

		namespace.addAxiom(Axiom.ClassAssertion(mainId, concept.getType()));

		/*
		 * basic attributes subjective deniable internal uni/bidirectional
		 * (relationship)
		 */
		if (concept.isAbstract()) {
			namespace.addAxiom(Axiom.AnnotationAssertion(mainId, CoreOntology.NS.IS_ABSTRACT, "true"));
		}

		namespace.define();
		main = namespace.getOntology().getConcept(mainId);

		for (ParentConcept parent : concept.getParents()) {

			List<IConcept> concepts = new ArrayList<>();
			for (IKimConcept pdecl : parent.getConcepts()) {
			    IConcept declared = declare(pdecl, monitor);
			    if (declared == null) {
			        monitor.error("parent declaration " + pdecl + " does not identify known concepts", pdecl);
			        return null;
			    }
				concepts.add(declared);
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
				try {
					IConcept childConcept = buildInternal((IKimConceptStatement) child, namespace, monitor);
					namespace.addAxiom(Axiom.SubClass(mainId, childConcept.getLocalName()));
					namespace.define();
				} catch (Throwable e) {
					monitor.error(e);
				}
			}
		}

		return main;
	}

	public @Nullable IConcept declare(IKimConcept concept, IMonitor monitor) {

		IConcept main = null;

		if (concept.getName() != null) {

		}

		return main;
	}
}
