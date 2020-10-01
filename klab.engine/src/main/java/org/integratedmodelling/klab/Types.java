package org.integratedmodelling.klab;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.klab.api.data.classification.IClassification;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IKnowledge;
import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.api.knowledge.ISemantic;
import org.integratedmodelling.klab.api.model.IConceptDefinition;
import org.integratedmodelling.klab.api.model.IKimObject;
import org.integratedmodelling.klab.api.model.INamespace;
import org.integratedmodelling.klab.api.services.ITypeService;
import org.integratedmodelling.klab.data.classification.Classification;
import org.integratedmodelling.klab.data.classification.Classifier;
import org.integratedmodelling.klab.exceptions.KlabValidationException;

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

	private Types() {
		Services.INSTANCE.registerService(this, ITypeService.class);
	}

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
	 * Get all concrete children for a type as a flat list.
	 * 
	 * @param baseType
	 * @return
	 */
	public List<IConcept> getConcreteChildren(IConcept baseType) {

		List<IConcept> ret = new ArrayList<>();
		getConcreteChildren(baseType, ret, false);
		return ret;
	}

	/**
	 * Get all concrete children for a type that have no further children as a flat
	 * list.
	 * 
	 * @param baseType
	 * @return
	 */
	public List<IConcept> getConcreteLeaves(IConcept baseType) {

		List<IConcept> ret = new ArrayList<>();
		getConcreteChildren(baseType, ret, true);
		return ret;
	}

	private void getConcreteChildren(IConcept baseType, List<IConcept> ret, boolean mustBeLeaf) {

		Collection<IConcept> children = baseType.getChildren();

		if (!baseType.isAbstract() && (!mustBeLeaf || children.isEmpty())) {
			ret.add(baseType);
		}
		for (IConcept child : children) {
			getConcreteChildren(child, ret, mustBeLeaf);
		}
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

	/**
	 * Create a classification for an observable, including all the possible types.
	 * Automatically demote from class to trait as needed.
	 * 
	 * @param rootClass
	 * @return
	 */
	public IClassification createClassification(ISemantic rootClass) {
		Classification ret = Classification.create(rootClass.getType());
		// TODO!
		return ret;
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
	public IClassification createClassificationFromMetadata(IConcept rootClass, String metadataEncodingProperty)
			throws KlabValidationException {

		if (rootClass.is(Type.CLASS)) {
			rootClass = Observables.INSTANCE.getDescribedType(rootClass);
		}

		Classification ret = Classification.create(rootClass);

		for (IKnowledge c : ret.getConcept().getSemanticClosure()) {

			IMetadata m = c.getMetadata();
			Object o = m.get(metadataEncodingProperty);

			if (o != null && !(o instanceof Double && Double.isNaN((Double) o))) {
				if (o instanceof List) {

					for (Object oo : ((List<?>) o)) {
						ret.addClassifier(Classifier.create(oo), (IConcept) c);
					}

				} else {
					ret.addClassifier(Classifier.create(o), (IConcept) c);
				}
			}
		}

		ret.initialize();

		return ret;
	}

//	public IConcept getCategorizingType(IConcept concept) {
//		Collection<IConcept> cls = OWL.INSTANCE.getRestrictedClasses((IConcept) concept,
//				Concepts.p(NS.INCARNATES_TRAIT_PROPERTY));
//		return cls.isEmpty() ? null : cls.iterator().next();
//	}

}
