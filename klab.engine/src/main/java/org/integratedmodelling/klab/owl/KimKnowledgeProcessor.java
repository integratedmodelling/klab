package org.integratedmodelling.klab.owl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nullable;

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
import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.Currencies;
import org.integratedmodelling.klab.Observables;
import org.integratedmodelling.klab.Reasoner;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.Traits;
import org.integratedmodelling.klab.Types;
import org.integratedmodelling.klab.Units;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.model.INamespace;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.api.knowledge.IObservable.Builder;
import org.integratedmodelling.klab.engine.resources.CoreOntology;
import org.integratedmodelling.klab.engine.resources.CoreOntology.NS;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.kim.KimNotifier.ErrorNotifyingMonitor;
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
						monitor.error("Core concept " + concept.getUpperConceptDefined() + " unknown", concept);
					}
				} else {
					parent = Resources.INSTANCE.getUpperOntology().getCoreType(concept.getType());
				}

				if (parent != null) {
					ns.addAxiom(Axiom.SubClass(parent.getUrn(), ret.getName()));
				}
			}

			if (ret != null) {
				ns.addAxiom(Axiom.AnnotationAssertion(ret.getName(), NS.BASE_DECLARATION, "true"));
				createProperties(ret, ns);
				ns.define();
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
				namespace.addAxiom(Axiom.SubClass(expr.getUrn(), mainId));
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
			IConcept trait = declare(inherited, monitor);
			if (trait == null) {
				monitor.error("inherited " + inherited.getName() + " does not identify known concepts", inherited);
				return null;
			}
			try {
				Traits.INSTANCE.addTrait(main, trait);
			} catch (KlabValidationException e) {
				monitor.error(e, inherited);
			}
		}

		for (ApplicableConcept link : concept.getSubjectsLinked()) {
			if (link.getOriginalObservable() == null && link.getSource() != null) {
				// relationship source->target
				Observables.INSTANCE.defineRelationship(main, declare(link.getSource(), monitor),
						declare(link.getTarget(), monitor));
			} else {
				// TODO
			}
		}

		if (kimObject != null) {
			kimObject.set(main);
		}

		return main;
	}

	public @Nullable Observable declare(final IKimObservable concept, final IMonitor monitor) {

		if (concept.getNonSemanticType() != null) {
			Concept nsmain = OWL.INSTANCE.getNonsemanticPeer(concept.getModelReference(), concept.getNonSemanticType());
			Observable observable = new Observable(nsmain);
			observable.setModelReference(concept.getModelReference());
			observable.setName(concept.getFormalName());
			return observable;
		}

		Concept main = declareInternal(concept.getMain(), monitor);

		if (main == null) {
			return null;
		}

		Concept observable = main;

		Observable ret = new Observable(observable);

		String declaration = concept.getDefinition();

		if (concept.getUnit() != null) {
			try {
				ret.setUnit(Units.INSTANCE.getUnit(concept.getUnit()));
				declaration += " in " + ret.getUnit();
			} catch (Exception e) {
				monitor.error(e, concept);
			}
		}

		if (concept.getCurrency() != null) {
			ret.setCurrency(Currencies.INSTANCE.getCurrency(concept.getUnit()));
			declaration += " in " + ret.getCurrency();
		}

		if (concept.getValue() != null) {
			ret.setValue(concept.getValue());
		}

		if (concept.getRange() != null) {
			ret.setRange(concept.getRange());
		}

		for (IKimConcept role : concept.getAssignedRoles()) {
			ret.getAssignedRoles().add(declareInternal(role, monitor));
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
		if (ret.getUnit() == null) {
			ret.setUnit(Units.INSTANCE.getDefaultUnitFor(observable));
			if (ret.getUnit() != null) {
				declaration += " in " + ret.getUnit();
			}
		}

		ret.setDeclaration(declaration);

		if (concept.getClassifier() != null || concept.getAggregator() != null) {

			IKimConcept modifier = concept.getAggregator() == null ? concept.getClassifier() : concept.getAggregator();

			Concept by = declareInternal(modifier, monitor);
			declaration += " by " + by;

			if (concept.getAggregator() != null) {
				ret.setAggregator(by);
			} else {

				Concept downTo = null;

				if (concept.getDownTo() != null) {
					downTo = declareInternal(concept.getDownTo(), monitor);
					declaration += " down to " + by;
				}

				IConcept classifiedType = Types.INSTANCE.getTypeByTrait(ret, by, downTo,
						(Ontology) (Configuration.INSTANCE.useCommonOntology() ? Reasoner.INSTANCE.getOntology()
								: null));

				ret.setObservable((Concept) classifiedType);
				ret.setClassifier(by);
				// force re-creation of name
				ret.setName(null);
				ret.setDownTo(downTo);
				ret.setDeclaration(classifiedType.getDefinition());
			}
		}

		return ret;
	}

	public @Nullable IConcept declare(final IKimConcept concept, final IMonitor monitor) {
		return declareInternal(concept, monitor);
	}

	private @Nullable Concept declareInternal(final IKimConcept concept, final IMonitor monitor) {

		Concept main = null;

		if (concept.getObservable() != null) {
			main = declareInternal(concept.getObservable(), monitor);
		} else if (concept.getName() != null) {
			main = Concepts.INSTANCE.getConcept(concept.getName());
		}

		if (main == null) {
			return null;
		}

		Builder builder = new ObservableBuilder(main,
				(Ontology) (Configuration.INSTANCE.useCommonOntology() ? Reasoner.INSTANCE.getOntology() : null))
						.withDeclaration(concept, monitor);

		/*
		 * transformations first
		 */

		if (concept.getInherent() != null) {
			IConcept c = declareInternal(concept.getInherent(), monitor);
			if (c != null) {
				builder.of(c);
			}
		}
		if (concept.getContext() != null) {
			IConcept c = declareInternal(concept.getContext(), monitor);
			if (c != null) {
				builder.within(c);
			}
		}
		if (concept.getCompresent() != null) {
			IConcept c = declareInternal(concept.getCompresent(), monitor);
			if (c != null) {
				builder.with(c);
			}
		}
		if (concept.getCausant() != null) {
			IConcept c = declareInternal(concept.getCausant(), monitor);
			if (c != null) {
				builder.from(c);
			}
		}
		if (concept.getCaused() != null) {
			IConcept c = declareInternal(concept.getCaused(), monitor);
			if (c != null) {
				builder.to(c);
			}
		}
		if (concept.getMotivation() != null) {
			IConcept c = declareInternal(concept.getMotivation(), monitor);
			if (c != null) {
				builder.withGoal(c);
			}
		}
		if (concept.getCooccurrent() != null) {
			IConcept c = declareInternal(concept.getCooccurrent(), monitor);
			if (c != null) {
				builder.withCooccurrent(c);
			}
		}
		if (concept.getAdjacent() != null) {
			IConcept c = declareInternal(concept.getAdjacent(), monitor);
			if (c != null) {
				builder.withAdjacent(c);
			}
		}

		for (IKimConcept c : concept.getTraits()) {
			IConcept trait = declareInternal(c, monitor);
			if (trait != null) {
				builder.withTrait(trait);
			}
		}

		for (IKimConcept c : concept.getRoles()) {
			IConcept role = declareInternal(c, monitor);
			if (role != null) {
				builder.withRole(role);
			}
		}

		// semantic operator goes last as it builds the operand and resets all
		// predicates
		if (concept.getObservationType() != null) {
			IConcept other = null;
			if (concept.getComparisonConcept() != null) {
				other = declareInternal(concept.getComparisonConcept(), monitor);
			}
			try {
				builder.as(concept.getObservationType(), other == null ? (IConcept[]) null : new IConcept[] { other });
			} catch (KlabValidationException e) {
				monitor.error(e, concept);
			}
		}

		Concept ret = null;
		try {

			ret = (Concept) builder.build();

			/*
			 * handle unions and intersections
			 */
			if (concept.getOperands().size() > 0) {
				List<IConcept> concepts = new ArrayList<>();
				concepts.add(ret);
				for (IKimConcept op : concept.getOperands()) {
					concepts.add(declareInternal(op, monitor));
				}
				ret = concept.getExpressionType() == Expression.INTERSECTION
						? OWL.INSTANCE.getIntersection(concepts, ret.getOntology(),
								concept.getOperands().get(0).getType())
						: OWL.INSTANCE.getUnion(concepts, ret.getOntology(), concept.getOperands().get(0).getType());
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
			ret = (Concept) Traits.INSTANCE.makeNegation(ret);
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
