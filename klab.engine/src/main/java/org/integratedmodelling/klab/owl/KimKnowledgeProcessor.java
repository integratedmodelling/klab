package org.integratedmodelling.klab.owl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import org.integratedmodelling.kim.api.IKimAnnotation;
import org.integratedmodelling.kim.api.IKimConcept;
import org.integratedmodelling.kim.api.IKimConcept.Expression;
import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.kim.api.IKimConceptStatement;
import org.integratedmodelling.kim.api.IKimConceptStatement.ApplicableConcept;
import org.integratedmodelling.kim.api.IKimObservable;
import org.integratedmodelling.kim.api.IKimScope;
import org.integratedmodelling.kim.model.KimConceptStatement;
import org.integratedmodelling.kim.model.KimConceptStatement.ParentConcept;
import org.integratedmodelling.klab.Concepts;
import org.integratedmodelling.klab.Currencies;
import org.integratedmodelling.klab.Observables;
import org.integratedmodelling.klab.Reasoner;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.Traits;
import org.integratedmodelling.klab.Types;
import org.integratedmodelling.klab.Units;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IObservable.Builder;
import org.integratedmodelling.klab.api.knowledge.IOntology;
import org.integratedmodelling.klab.api.model.INamespace;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.engine.resources.CoreOntology;
import org.integratedmodelling.klab.engine.resources.CoreOntology.NS;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.kim.KimNotifier.ErrorNotifyingMonitor;
import org.integratedmodelling.klab.model.Annotation;
import org.integratedmodelling.klab.model.ConceptStatement;
import org.integratedmodelling.klab.model.Namespace;
import org.integratedmodelling.klab.utils.CamelCase;

/**
 * A singleton that handles translation of k.IM knowledge statements to internal
 * OWL-based knowledge.
 * 
 * @author Ferd
 *
 */
public enum KimKnowledgeProcessor {

	INSTANCE;

	private Map<String, String> coreConceptPeers = new HashMap<>();

	/*
	 * Record correspondence of core concept peers to worldview concepts. Called by
	 * KimValidator for later use at namespace construction.
	 */
	public void setWorldviewPeer(String coreConcept, String worldviewConcept) {
		coreConceptPeers.put(worldviewConcept, coreConcept);
	}

	public @Nullable Concept build(final IKimConceptStatement concept, final INamespace namespace,
			final IMonitor monitor) {
		return build(concept, namespace, null, monitor);
	}

	public @Nullable Concept build(final IKimConceptStatement concept, final INamespace namespace,
			ConceptStatement kimObject, final IMonitor monitor) {

		if (concept.isMacro()) {
			return null;
		}

		Namespace ns = (Namespace) namespace;
		try {

			Concept ret = buildInternal(concept, ns, kimObject, monitor);
			if (((KimConceptStatement) concept).getParents().isEmpty()) {
				IConcept parent = null;
				if (concept.getUpperConceptDefined() != null) {
					parent = Concepts.INSTANCE.getConcept(concept.getUpperConceptDefined());
					if (parent == null) {
						monitor.error("Core concept " + concept.getUpperConceptDefined() + " is unknown", concept);
					}
				} else {
					parent = Resources.INSTANCE.getUpperOntology().getCoreType(concept.getType());
					if (coreConceptPeers.containsKey(ret.toString())) {
						// ensure that any non-trivial core inheritance is dealt with appropriately
						parent = Resources.INSTANCE.getUpperOntology().alignCoreInheritance(ret);
					}
				}

				if (parent != null) {
					ns.addAxiom(Axiom.SubClass(parent.getUrn(), ret.getName()));
				}
			}

			if (ret != null) {
				ns.addAxiom(Axiom.AnnotationAssertion(ret.getName(), NS.BASE_DECLARATION, "true"));
				createProperties(ret, ns);
				ns.define();

				Observables.INSTANCE.registerConfigurations(ret);

				if (coreConceptPeers.containsKey(ret.toString())) {
					Resources.INSTANCE.getUpperOntology().setAsCoreType(ret);
				}

			}

			return ret;

		} catch (Throwable e) {
			monitor.error(e, concept);
		}
		return null;
	}

	private @Nullable Concept buildInternal(final IKimConceptStatement concept, final Namespace namespace,
			ConceptStatement kimObject, final IMonitor monitor) {

		Concept main = null;
		String mainId = concept.getName();

		namespace.addAxiom(Axiom.ClassAssertion(mainId, concept.getType()));

		// set the k.IM definition
		namespace.addAxiom(Axiom.AnnotationAssertion(mainId, NS.CONCEPT_DEFINITION_PROPERTY,
				namespace.getName() + ":" + concept.getName()));

		/*
		 * basic attributes subjective deniable internal uni/bidirectional
		 * (relationship)
		 */
		if (concept.isAbstract()) {
			namespace.addAxiom(Axiom.AnnotationAssertion(mainId, CoreOntology.NS.IS_ABSTRACT, "true"));
		}

		namespace.define();
		main = namespace.getOntology().getConcept(mainId);

		for (ParentConcept parent : ((KimConceptStatement) concept).getParents()) {

			List<IConcept> concepts = new ArrayList<>();
			for (IKimConcept pdecl : parent.getConcepts()) {
				IConcept declared = declare(pdecl, namespace.getOntology(), monitor);
				if (declared == null) {
					monitor.error("parent declaration " + pdecl + " does not identify known concepts", pdecl);
					return null;
				}
				concepts.add(declared);
			}

			if (concepts.size() == 1) {
				if (concept.isAlias()) {
					namespace.getOntology().addDelegateConcept(mainId, namespace.getStatement(),
							(Concept) concepts.get(0));
				} else {
					namespace.addAxiom(Axiom.SubClass(concepts.get(0).getUrn(), mainId));
				}
			} else {
				IConcept expr = null;
				switch (parent.getConnector()) {
				case INTERSECTION:
					expr = OWL.INSTANCE.getIntersection(concepts, namespace.getOntology(),
							((Concept) concepts.get(0)).getTypeSet());
					break;
				case UNION:
					expr = OWL.INSTANCE.getUnion(concepts, namespace.getOntology(),
							((Concept) concepts.get(0)).getTypeSet());
					break;
				case FOLLOWS:
					expr = OWL.INSTANCE.getConsequentialityEvent(concepts, namespace.getOntology());
					break;
				default:
					// won't happen
					break;
				}
				if (concept.isAlias()) {
					namespace.getOntology().addDelegateConcept(mainId, namespace.getStatement(), (Concept) expr);
				} else {
					namespace.addAxiom(Axiom.SubClass(expr.getUrn(), mainId));
				}
			}
			namespace.define();
		}

		for (IKimScope child : concept.getChildren()) {
			if (child instanceof IKimConceptStatement) {
				try {
					ConceptStatement chobj = kimObject == null ? null
							: new ConceptStatement((IKimConceptStatement) child);
					IConcept childConcept = buildInternal((IKimConceptStatement) child, namespace, chobj,
							monitor instanceof ErrorNotifyingMonitor
									? ((ErrorNotifyingMonitor) monitor).contextualize(child)
									: monitor);
					namespace.addAxiom(Axiom.SubClass(mainId, childConcept.getName()));
					namespace.define();
					kimObject.getChildren().add(chobj);
				} catch (Throwable e) {
					monitor.error(e, child);
				}
			}
		}

		for (IKimConcept inherited : ((KimConceptStatement) concept).getTraitsInherited()) {
			IConcept trait = declare(inherited, namespace.getOntology(), monitor);
			if (trait == null) {
				monitor.error("inherited " + inherited.getName() + " does not identify known concepts", inherited);
				return null;
			}
			try {
				Traits.INSTANCE.addTrait(main, trait, namespace.getOntology());
			} catch (KlabValidationException e) {
				monitor.error(e, inherited);
			}
		}

		for (ApplicableConcept link : concept.getSubjectsLinked()) {
			if (link.getOriginalObservable() == null && link.getSource() != null) {
				// relationship source->target
				Observables.INSTANCE.defineRelationship(main,
						declare(link.getSource(), namespace.getOntology(), monitor),
						declare(link.getTarget(), namespace.getOntology(), monitor), namespace.getOntology());
			} else {
				// TODO
			}
		}

		if (kimObject != null) {
			kimObject.set(main);
		}

		return main;
	}

	public @Nullable Observable declare(final IKimObservable concept, IOntology declarationOntology,
			final IMonitor monitor) {
		if (concept.getNonSemanticType() != null) {
			Concept nsmain = OWL.INSTANCE.getNonsemanticPeer(concept.getModelReference(), concept.getNonSemanticType());
			Observable observable = new Observable(nsmain);
			observable.setModelReference(concept.getModelReference());
			observable.setName(concept.getFormalName());
			return observable;
		}

		Concept main = declareInternal(concept.getMain(), (Ontology) declarationOntology, monitor);

		if (main == null) {
			return null;
		}

		Concept observable = main;

		Observable ret = new Observable(observable);

		String declaration = concept.getDefinition();

		String unit = concept.getUnit();
		String currency = concept.getCurrency();
		// if (unit != null && unit.contains("@")) {
		// currency = unit;
		// unit = null;
		// }

		if (unit != null) {
			try {
				ret.setUnit(Units.INSTANCE.getUnit(unit));
				declaration += " in " + unit;
			} catch (Exception e) {
				monitor.error(e, concept);
			}
		}

		if (currency != null) {
			try {
				ret.setCurrency(Currencies.INSTANCE.getCurrency(currency));
				declaration += " in " + currency;
			} catch (Exception e) {
				monitor.error(e, concept);
			}
		}

		if (concept.getValue() != null) {
			Object value = concept.getValue();
			if (value instanceof IKimConcept) {
				value = Concepts.INSTANCE.declare((IKimConcept) value);
			}
			ret.setValue(concept.getValue());
		}

		if (concept.getRange() != null) {
			ret.setRange(concept.getRange());
		}

		ret.setOptional(concept.isOptional());
		ret.setGeneric(concept.isAbstractObservable());

		/*
		 * TODO redefine observable if modifiers (by) were given
		 */

		String name = concept.getFormalName();
		if (name == null) {
			name = CamelCase.toLowerCase(Concepts.INSTANCE.getDisplayName(main), '_');
		}

		ret.setName(name);

		/*
		 * set default unit if any is appropriate
		 */
		if (ret.getUnit() == null && ret.getCurrency() == null) {
			ret.setUnit(Units.INSTANCE.getDefaultUnitFor(observable));
			if (ret.getUnit() != null) {
				declaration += " in " + ret.getUnit();
			}
		}

		ret.setDeclaration(declaration);

		if (concept.getClassifier() != null) {

			IKimConcept modifier = concept.getClassifier();

			Concept by = declareInternal(modifier, (Ontology) declarationOntology, monitor);
			declaration += " by " + by;

			if (by == null) {
			    monitor.error("unknown concept in 'by' clause: " + modifier.getDefinition());
			    return null;
			}
			
			Concept downTo = null;

			if (concept.getDownTo() != null) {
				downTo = declareInternal(concept.getDownTo(), (Ontology) declarationOntology, monitor);
				declaration += " down to " + by;
			}

			ret.setClassifier(by);
			// force re-creation of name
			ret.setName(ret.getName() + "_by_" + modifier.getCodeName().replaceAll("\\-", "_"));
			ret.setDownTo(downTo);
			if (downTo != null) {
				ret.setName(ret.getName() + "_down_to_" + concept.getDownTo().getCodeName().replaceAll("\\-", "_"));
			}
		}

		for (IKimAnnotation annotation : concept.getAnnotations()) {
			ret.addAnnotation(new Annotation(annotation));
		}

		return ret;
	}

	/**
	 * 
	 * @param concept
	 * @param declarationNamespace
	 *            the namespace where derived concepts will be put if declaring them
	 *            in the original ontologies causes loss of referential integrity.
	 * @param monitor
	 * @return
	 */
	public @Nullable IConcept declare(final IKimConcept concept, IOntology declarationOntology,
			final IMonitor monitor) {
		return declareInternal(concept, (Ontology) declarationOntology, monitor);
	}

	private @Nullable Concept declareInternal(final IKimConcept concept, Ontology ontology, final IMonitor monitor) {

		Concept main = null;

		if (concept.getObservable() != null) {
			main = declareInternal(concept.getObservable(), ontology, monitor);
		} else if (concept.getName() != null) {
			main = Concepts.INSTANCE.getConcept(concept.getName());
		}

		if (main == null) {
			return null;
		}

		Builder builder = new ObservableBuilder(main, ontology).withDeclaration(concept, monitor);

		/*
		 * transformations first
		 */

		if (concept.getInherent() != null) {
			IConcept c = declareInternal(concept.getInherent(), ontology, monitor);
			if (c != null) {
				builder.of(c);
			}
		}
		if (concept.getContext() != null) {
			IConcept c = declareInternal(concept.getContext(), ontology, monitor);
			if (c != null) {
				builder.within(c);
			}
		}
		if (concept.getCompresent() != null) {
			IConcept c = declareInternal(concept.getCompresent(), ontology, monitor);
			if (c != null) {
				builder.with(c);
			}
		}
		if (concept.getCausant() != null) {
			IConcept c = declareInternal(concept.getCausant(), ontology, monitor);
			if (c != null) {
				builder.from(c);
			}
		}
		if (concept.getCaused() != null) {
			IConcept c = declareInternal(concept.getCaused(), ontology, monitor);
			if (c != null) {
				builder.to(c);
			}
		}
		if (concept.getMotivation() != null) {
			IConcept c = declareInternal(concept.getMotivation(), ontology, monitor);
			if (c != null) {
				builder.withGoal(c);
			}
		}
		if (concept.getCooccurrent() != null) {
			IConcept c = declareInternal(concept.getCooccurrent(), ontology, monitor);
			if (c != null) {
				builder.withCooccurrent(c);
			}
		}
		if (concept.getAdjacent() != null) {
			IConcept c = declareInternal(concept.getAdjacent(), ontology, monitor);
			if (c != null) {
				builder.withAdjacent(c);
			}
		}
		if (concept.getRelationshipSource() != null) {
			IConcept source = declareInternal(concept.getRelationshipSource(), ontology, monitor);
			IConcept target = declareInternal(concept.getRelationshipTarget(), ontology, monitor);
			if (source != null && target != null) {
				builder.linking(source, target);
			}

		}

		for (IKimConcept c : concept.getTraits()) {
			IConcept trait = declareInternal(c, ontology, monitor);
			if (trait != null) {
				builder.withTrait(trait);
			}
		}

		for (IKimConcept c : concept.getRoles()) {
			IConcept role = declareInternal(c, ontology, monitor);
			if (role != null) {
				builder.withRole(role);
			}
		}

		// semantic operator goes last as it builds the operand and resets all
		// predicates
		if (concept.getObservationType() != null) {
			IConcept other = null;
			if (concept.getComparisonConcept() != null) {
				other = declareInternal(concept.getComparisonConcept(), ontology, monitor);
			}
			try {
				builder.as(concept.getObservationType(), other == null ? (IConcept[]) null : new IConcept[] { other });
			} catch (KlabValidationException e) {
				monitor.error(e, concept);
			}
		}

		Concept ret = null;
		try {

			ret = (Concept) builder.buildConcept();

			/*
			 * handle unions and intersections
			 */
			if (concept.getOperands().size() > 0) {
				List<IConcept> concepts = new ArrayList<>();
				concepts.add(ret);
				for (IKimConcept op : concept.getOperands()) {
					concepts.add(declareInternal(op, ontology, monitor));
				}
				ret = concept.getExpressionType() == Expression.INTERSECTION
						? OWL.INSTANCE.getIntersection(concepts, ontology, concept.getOperands().get(0).getType())
						: OWL.INSTANCE.getUnion(concepts, ontology, concept.getOperands().get(0).getType());
			}

			// set the k.IM definition in the concept FIXME this must only happen if the
			// concept wasn't there - within build() and repeat if mods are made
			ret.getOntology().define(Collections.singletonList(
					Axiom.AnnotationAssertion(ret.getName(), NS.CONCEPT_DEFINITION_PROPERTY, concept.getDefinition())));

			// consistency check
			if (!Reasoner.INSTANCE.isSatisfiable(ret)) {
				((Concept) ret).getTypeSet().add(Type.NOTHING);
				monitor.error("the definition of this concept has logical errors and is inconsistent", concept);
			}

		} catch (Throwable e) {
			monitor.error(e, concept);
		}

		if (concept.isNegated()) {
			ret = (Concept) Traits.INSTANCE.makeNegation(ret, ontology);
		}

		return ret;
	}

	private void createProperties(IConcept ret, Namespace ns) {

		String pName = null;
		String pProp = null;
		if (ret.is(Type.ATTRIBUTE)) {
			// hasX
			pName = "has" + ret.getName();
			pProp = NS.HAS_ATTRIBUTE_PROPERTY;
		} else if (ret.is(Type.REALM)) {
			// inX
			pName = "in" + ret.getName();
			pProp = NS.HAS_REALM_PROPERTY;
		} else if (ret.is(Type.IDENTITY)) {
			// isX
			pName = "is" + ret.getName();
			pProp = NS.HAS_IDENTITY_PROPERTY;
		}
		if (pName != null) {
			ns.addAxiom(Axiom.ObjectPropertyAssertion(pName));
			ns.addAxiom(Axiom.ObjectPropertyRange(pName, ret.getName()));
			ns.addAxiom(Axiom.SubObjectProperty(pProp, pName));
			ns.addAxiom(Axiom.AnnotationAssertion(ret.getName(), NS.TRAIT_RESTRICTING_PROPERTY,
					ns.getName() + ":" + pName));
		}
	}
}
