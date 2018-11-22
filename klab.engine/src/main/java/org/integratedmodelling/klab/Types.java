package org.integratedmodelling.klab;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.kim.model.Kim;
import org.integratedmodelling.kim.model.KimConcept;
import org.integratedmodelling.klab.api.data.classification.IClassification;
import org.integratedmodelling.klab.api.knowledge.IAxiom;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IKnowledge;
import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.api.model.IConceptDefinition;
import org.integratedmodelling.klab.api.model.IKimObject;
import org.integratedmodelling.klab.api.model.INamespace;
import org.integratedmodelling.klab.api.services.ITypeService;
import org.integratedmodelling.klab.common.LogicalConnector;
import org.integratedmodelling.klab.data.classification.Classification;
import org.integratedmodelling.klab.data.classification.Classifier;
import org.integratedmodelling.klab.engine.resources.CoreOntology.NS;
import org.integratedmodelling.klab.exceptions.KlabUnimplementedException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.owl.Axiom;
import org.integratedmodelling.klab.owl.Concept;
import org.integratedmodelling.klab.owl.OWL;
import org.integratedmodelling.klab.owl.Observable;
import org.integratedmodelling.klab.owl.ObservableBuilder;
import org.integratedmodelling.klab.owl.Ontology;

/**
 * Methods to deal with classified types.
 * <p>
 * The following classified types can exist:
 * <p>
 * <ul>
 * Straight classifications: the classified type is a class and it's classified
 * into its subclasses. In data annotations, this produces as many models as
 * traits exposed by the class, with classifiers that extract the trait from the
 * subclasses. Class must be abstract, subtypes may be but that will trigger
 * further resolution.
 * <li>Straight trait classification: classify one trait into its subtraits.
 * This will be represented by observability of that trait and imply the
 * presence of any direct observable it is inherent of.
 * <li>Classification of direct non-trait and resolves to observability
 * (accepting only the deniable observability expressed as the concept itself
 * with/out 'no') without 'by' clause.
 * <li>Classification of direct non-trait 'by' trait. Creates trait
 * contextualized 'of' (direct thing - must be compatible) 'within' context and
 * observes its observability. A further contextualized 'by x of z' should be
 * admitted - say by Temperature Level of Stream' that reverses the inherency
 * and makes is a Level of (Temperature within Stream) within (context).
 * <li>Classification of quality 'by' trait. If quantity by ordering, a 'use
 * discretization' error will ensue unless classify is discretize, in which case
 * it will resolve to a categorized ranking rather than a class. Otherwise
 * <li>Classification of
 * </ul>
 * <p>
 * All classifications contextualize universals as qualities, so they are
 * subject to the same rules of all qualities: they must have an inherent object
 * and if that is not the context of contextualization, a within statement.
 * 
 * @author Ferd
 *
 */
public enum Types implements ITypeService {

	INSTANCE;

	/**
	 * Return a (flat) list of all children up to the passed level of detail, using
	 * the model object (stated) hierarchy and keeping the order of declaration
	 * (depth- first if more levels are involved). Allows abstract concepts in the
	 * result - if only concrete ones are desires, use
	 * {@link #getConcreteChildrenAtLevel} instead.
	 * 
	 * @param baseType
	 * @param level
	 * @return all children at level
	 */
	public List<IConcept> getChildrenAtLevel(IConcept baseType, int level) {

		List<IConcept> ret = new ArrayList<>();

		INamespace ns = Namespaces.INSTANCE.getNamespace(baseType.getNamespace());
		if (ns == null) {
			return ret;
		}

		IKimObject mo = ns.getObject(baseType.getName());
		if (mo == null) {
			return ret;
		}

		findAtLevel(mo, ret, level, 0, false);

		return ret;
	}

	/**
	 * If current is a child of base at passed level, return it; otherwise return
	 * the parent at the passed level, or null if the concept is unrelated or higher
	 * than level. Uses the model object (stated) hierarchy.
	 * 
	 * @param base
	 * @param current
	 * @param level
	 * @return parent at level
	 */
	public IConcept getParentAtLevel(IConcept base, IConcept current, int level) {
		IConcept ret = null;
		if (current.is(base)) {
			int l = getDetailLevel(base, current);
			if (l == level) {
				return current;
			}
			if (l > level) {
				for (IConcept c : getChildrenAtLevel(base, level)) {
					if (current.is(c)) {
						return c;
					}
				}
			}
		}
		return ret;
	}

	/**
	 * Get the level of detail corresponding to the passed key in the DECLARED
	 * hierarchy of baseType - i.e. using the model objects declared in k.IM. Key
	 * can be the concept fully qualified name or its ID alone, matched
	 * case-insensitive. Semantics alone does not suffice: the concepts must be
	 * arranged in a declaration hierarchy for the levels to be attributed. Also
	 * only works with trait and class types, as this is only relevant to
	 * classifications.
	 * 
	 * @param baseType
	 * @param key
	 * @return detail level
	 */
	public int getDetailLevel(IConcept baseType, String key) {

		/*
		 * go through children using the model object hierarchy. Will only find the ones
		 * declared in a hierarchy.
		 */
		INamespace ns = Namespaces.INSTANCE.getNamespace(baseType.getNamespace());
		if (ns == null) {
			return -1;
		}

		IKimObject mo = ns.getObject(baseType.getName());
		if (mo == null) {
			return -1;
		}

		return findLevel(mo, key, 0);
	}

	/**
	 * Return a (flat) list of all CONCRETE children up to the passed level of
	 * detail, using the model object (stated) hierarchy and keeping the order of
	 * declaration (depth- first if more levels are involved).
	 * 
	 * @param baseType
	 * @param level
	 * @return concrete children at level
	 */
	public List<IConcept> getConcreteChildrenAtLevel(IConcept baseType, int level) {

		List<IConcept> ret = new ArrayList<>();

		INamespace ns = Namespaces.INSTANCE.getNamespace(baseType.getNamespace());
		if (ns == null) {
			return ret;
		}

		IKimObject mo = ns.getObject(baseType.getName());
		if (mo == null) {
			return ret;
		}

		findAtLevel(mo, ret, level, 0, true);

		return ret;
	}

	/**
	 * Get the level of detail of current in the DECLARED hierarchy of base - i.e.
	 * using the model objects declared in k.IM. Only works with trait and class
	 * types, as this is only relevant to classifications.
	 * 
	 * @param base
	 * @param current
	 * @return detail level of current within base
	 */
	public int getDetailLevel(IConcept base, IConcept current) {
		return getDetailLevel(base, current.toString());
	}

	@Override
    public IConcept getByType(IConcept concept) {
        Collection<IConcept> cls = OWL.INSTANCE
                .getRestrictedClasses((IConcept) concept, Concepts.p(NS.REPRESENTED_BY_PROPERTY));
        return cls.isEmpty() ? null : cls.iterator().next();
    }
	
//	/**
//	 * Return the appropriate classification type for a stated 'classify' observer,
//	 * with optional 'by' clause. Any error throw an exception.
//	 * 
//	 * @param original
//	 * @return the classification type
//	 * @throws KlabValidationException
//	 */
//	public IConcept getClassificationType(IConcept original) throws KlabValidationException {
//
//		String problem = null;
//		IConcept by = null; // TODO needs to be extracted from original
//							// observable.
//
//		if (original.is(Type.CLASS)) {
//			if (original.isAbstract() || Concepts.INSTANCE.isBaseDeclaration(original)) {
//				return original;
//			} else {
//				problem = "classified type cannot be specific";
//			}
//		} else if (original.is(Type.TRAIT)) {
//			if (by == null) {
//				return ObservableBuilder.makeType(original, true);
//			} else {
//				throw new KlabUnimplementedException();
//			}
//		} else if (original.is(Type.DIRECT_OBSERVABLE)) {
//			if (by == null) {
//				return ObservableBuilder.makeType(ObservableBuilder.makeObservability(original, true), true);
//			} else {
//				throw new KlabUnimplementedException();
//			}
//
//		} else if (original.is(Type.QUALITY)) {
//			throw new KlabUnimplementedException();
//		}
//		throw new KlabValidationException(
//				"concept " + original + " can not be classified" + (problem == null ? "" : (": " + problem)));
//	}

//	/**
//	 * Return the type we should use in a classifier that classifies the first one
//	 * (returned {@link #getClassificationType(IConcept)} into the stated classified
//	 * concept. If the type is not adequate for that throw a validation exception
//	 * explaining why.
//	 * 
//	 * @param classifier
//	 * @return the classified type
//	 * @throws KlabValidationException
//	 */
//	public IConcept getClassifiedType(IConcept classifier, IConcept classified) throws KlabValidationException {
//		return null;
//	}

	private static int findLevel(IKimObject mo, String key, int level) {

		if (mo == null || mo.getName() == null) {
			return -1;
		}

		if (mo.getName().equals(key) || mo.getId().equalsIgnoreCase(key)) {
			return level;
		}

		for (IKimObject o : mo.getChildren()) {
			if (o instanceof IConceptDefinition) {
				int l = findLevel(o, key, level + 1);
				if (l > 0) {
					return l;
				}
			}
		}

		return -1;
	}

	private void findAtLevel(IKimObject mo, List<IConcept> ret, int level, int current, boolean filterAbstract) {

		IConcept k = Concepts.c(mo.getName());
		if (!filterAbstract || !k.isAbstract()) {
			ret.add(k);
		}
		if (level < 0 || level < current) {
			for (IKimObject o : mo.getChildren()) {
				if (o instanceof IConceptDefinition) {
					findAtLevel(o, ret, level, current + 1, filterAbstract);
				}
			}
		}
	}

	public Collection<IConcept> getExposedTraits(IConcept cls) {
		return OWL.INSTANCE.getRestrictedClasses(cls, Concepts.p(NS.EXPOSES_TRAIT_PROPERTY));
	}

	public boolean isDelegate(IConcept concept) {
		return concept.getMetadata().get(NS.IS_TYPE_DELEGATE) != null;
	}

	/**
	 * Produces the quality observable that will carry the contextualizable meaning
	 * of one or more universals. Concept must exist but it may not be a type yet.
	 * 
	 * @param type
	 * @param exposedTraits
	 */
	public void setExposedTraits(IConcept type, Collection<IConcept> exposedTraits) {
		OWL.INSTANCE.restrictSome(type, Concepts.p(NS.EXPOSES_TRAIT_PROPERTY), LogicalConnector.UNION, exposedTraits);
	}

	/**
	 * True if trait is exposedTrait, or if trait is a valid value for it - e.g. is
	 * part of the restricted closure of a "down to" specification.
	 * 
	 * @param trait
	 * @param exposedTrait
	 * @return true if the trait is exposed as asked
	 */
	public boolean isExposedAs(IKnowledge trait, IConcept exposedTrait) {
		if (trait.is(exposedTrait)) {
			return true;
		}
		for (IConcept c : OWL.INSTANCE.getRestrictedClasses(exposedTrait, Concepts.p(NS.LIMITED_BY_PROPERTY))) {
			if (trait.is(c)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Create a classification based on the encodings stored as metadata in the
	 * concept hierarchy.
	 * 
	 * TODO this may be called multiple times, so it should cache the result.
	 * 
	 * @param rootClass
	 * @param metadataEncodingProperty
	 * @return classification
	 * @throws KlabValidationException
	 */
	public IClassification createClassificationFromMetadata(IConcept rootClass, String metadataEncodingProperty) throws KlabValidationException {

		Classification ret = Classification.create(rootClass);

		for (IKnowledge c : ret.getConcept().getSemanticClosure()) {

			IMetadata m = c.getMetadata();
			Object o = m.get(metadataEncodingProperty);

			if (o != null && !(o instanceof Double && Double.isNaN((Double) o))) {
				ret.addClassifier(Classifier.create(o), (IConcept) c);
			}
		}
		
		ret.initialize();

		return ret;
	}

//    /**
//     * Produce a type that exposes a single passed trait.
//     * 
//     * @param trait
//     * @return
//     */
//    private IConcept makeTypeFor(IConcept trait) {
//
//        IConcept ret = trait.getOntology().getConcept(traitID);
//
//        if (ret == null) {
//
//            List<IAxiom> axioms = new ArrayList<>();
//            axioms.add(Axiom.ClassAssertion(traitID));
//            axioms.add(Axiom.SubClass(NS.TYPE, traitID));
//            axioms.add(Axiom.AnnotationAssertion(traitID, NS.CONCEPT_DEFINITION_PROPERTY, Qualities.TYPE
//                    .name().toLowerCase() + " " + ((Concept) trait).getAssertedDefinition()));
//            axioms.add(Axiom.AnnotationAssertion(traitID, IMetadata.DC_LABEL, trait.getLocalName()));
//            // type of x are base declarations.
//            axioms.add(Axiom.AnnotationAssertion(traitID, NS.BASE_DECLARATION, "true"));
//            axioms.add(Axiom.AnnotationAssertion(traitID, NS.IS_TYPE_DELEGATE, "true"));
//            trait.getOntology().define(axioms);
//            ret = trait.getOntology().getConcept(traitID);
//            OWL.restrictSome(ret, KLAB.p(NS.EXPOSES_TRAIT_PROPERTY), trait);
//
//            /*
//             * types inherit the context from their trait
//             */
//            IConcept context = getContextType(trait);
//            if (context != null) {
//                OWL.restrictSome(ret, KLAB.p(NS.HAS_CONTEXT_PROPERTY), context);
//            }
//        }
//
//        return ret;
//    }
	
	public IConcept getTypeByTrait(Observable observable, Concept by, Concept downTo, Ontology ontology) {

		String id = observable.getName();
		String definition = observable.getType().getDefinition();
		Set<IConcept> allowedDetail = new HashSet<>();

		if (!by.is(Type.TRAIT)) {
			throw new KlabValidationException(by + ": the concept in a 'by' clause must be a base abstract trait");
		}

		/*
		 * TODO trait must be a base trait and abstract.
		 */
		if (!Concepts.INSTANCE.isBaseDeclaration(by) || !by.isAbstract()) {
			throw new KlabValidationException(by + 
					": traits used in a 'by' clause must be abstract and declared at root level");
		}

		id += "By" + ObservableBuilder.getCleanId(by);
		definition += " by " + by.getDefinition();

		if (downTo != null) {
			allowedDetail.addAll(Types.INSTANCE.getChildrenAtLevel(by, Types.INSTANCE.getDetailLevel(by, downTo)));
			id += "DownTo" + ObservableBuilder.getCleanId(downTo);
			definition += " down to " + downTo.getDefinition();
		}

		IConcept ret = ontology.getConcept(id);

		if (ret == null) {
			
			/*
			 * CHECK - this produces a trait, not a class. Do we still need that
			 * distinction?
			 */
			Set<Type> type = Kim.INSTANCE.getType("class");
			if (observable.isAbstract()) {
				type.add(Type.ABSTRACT);
			}

			List<IAxiom> axioms = new ArrayList<>();
			// FIXME this needs to use IDs and labels like the rest
			axioms.add(Axiom.ClassAssertion(id, type));
			axioms.add(Axiom.SubClass(NS.CORE_TYPE, id));
			axioms.add(Axiom.AnnotationAssertion(id, NS.BASE_DECLARATION, "true"));
	        axioms.add(Axiom.AnnotationAssertion(id, NS.DISPLAY_LABEL_PROPERTY, id));
	        axioms.add(Axiom.AnnotationAssertion(id, NS.CONCEPT_DEFINITION_PROPERTY, definition));
	        axioms.add(Axiom.AnnotationAssertion(id, "rdfs:label", id));
			ontology.define(axioms);
			ret = ontology.getConcept(id);
			
			OWL.INSTANCE.restrictSome(ret, Concepts.p(NS.REPRESENTED_BY_PROPERTY), by);
			if (downTo != null) {
				OWL.INSTANCE.restrictSome(ret, Concepts.p(NS.LIMITED_BY_PROPERTY), LogicalConnector.UNION, allowedDetail);
			}
			Observables.INSTANCE.copyContext(observable.getType(), ret);
		}

		return ret;
	}

}
