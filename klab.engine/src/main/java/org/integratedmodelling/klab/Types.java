package org.integratedmodelling.klab;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.klab.api.data.classification.IClassification;
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
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.owl.OWL;
import org.integratedmodelling.klab.owl.ObservableBuilder;

/**
 * Methods to deal with classified types.
 * <p>
 * The following classified types can exist:
 * <p>
 * <ul>
 * Straight classifications: the classified type is a class and it's classified into its subclasses.
 * In data annotations, this produces as many models as traits exposed by the class, with
 * classifiers that extract the trait from the subclasses. Class must be abstract, subtypes may be
 * but that will trigger further resolution.
 * <li>Straight trait classification: classify one trait into its subtraits. This will be
 * represented by observability of that trait and imply the presence of any direct observable it is
 * inherent of.
 * <li>Classification of direct non-trait and resolves to observability (accepting only the deniable
 * observability expressed as the concept itself with/out 'no') without 'by' clause.
 * <li>Classification of direct non-trait 'by' trait. Creates trait contextualized 'of' (direct
 * thing - must be compatible) 'within' context and observes its observability. A further
 * contextualized 'by x of z' should be admitted - say by Temperature Level of Stream' that reverses
 * the inherency and makes is a Level of (Temperature within Stream) within (context).
 * <li>Classification of quality 'by' trait. If quantity by ordering, a 'use discretization' error
 * will ensue unless classify is discretize, in which case it will resolve to a categorized ranking
 * rather than a class. Otherwise
 * <li>Classification of
 * </ul>
 * <p>
 * All classifications contextualize universals as qualities, so they are subject to the same rules
 * of all qualities: they must have an inherent object and if that is not the context of
 * contextualization, a within statement.
 * 
 * @author Ferd
 *
 */
public enum Types implements ITypeService {

  INSTANCE;

  /**
   * Return a (flat) list of all children up to the passed level of detail, using the model object
   * (stated) hierarchy and keeping the order of declaration (depth- first if more levels are
   * involved). Allows abstract concepts in the result - if only concrete ones are desires, use
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
   * If current is a child of base at passed level, return it; otherwise return the parent at the
   * passed level, or null if the concept is unrelated or higher than level. Uses the model object
   * (stated) hierarchy.
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
   * Get the level of detail corresponding to the passed key in the DECLARED hierarchy of baseType -
   * i.e. using the model objects declared in k.IM. Key can be the concept fully qualified name or
   * its ID alone, matched case-insensitive. Semantics alone does not suffice: the concepts must be
   * arranged in a declaration hierarchy for the levels to be attributed. Also only works with trait
   * and class types, as this is only relevant to classifications.
   * 
   * @param baseType
   * @param key
   * @return detail level
   */
  public int getDetailLevel(IConcept baseType, String key) {

    /*
     * go through children using the model object hierarchy. Will only find the ones declared in a
     * hierarchy.
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
   * Return a (flat) list of all CONCRETE children up to the passed level of detail, using the model
   * object (stated) hierarchy and keeping the order of declaration (depth- first if more levels are
   * involved).
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
   * Get the level of detail of current in the DECLARED hierarchy of base - i.e. using the model
   * objects declared in k.IM. Only works with trait and class types, as this is only relevant to
   * classifications.
   * 
   * @param base
   * @param current
   * @return detail level of current within base
   */
  public int getDetailLevel(IConcept base, IConcept current) {
    return getDetailLevel(base, current.toString());
  }

  /**
   * Return the appropriate classification type for a stated 'classify' observer, with optional 'by'
   * clause. Any error throw an exception.
   * 
   * @param original
   * @return
   * @throws KlabValidationException
   */
  public IConcept getClassificationType(IConcept original) throws KlabValidationException {

    String problem = null;
    IConcept by = null; // TODO needs to be extracted from original
                        // observable.

    if (original.is(Type.CLASS)) {
      if (original.isAbstract() || Concepts.INSTANCE.isBaseDeclaration(original)) {
        return original;
      } else {
        problem = "classified type cannot be specific";
      }
    } else if (original.is(Type.TRAIT)) {
      if (by == null) {
        return ObservableBuilder.makeType(original, true);
      } else {
        // TODO
      }
    } else if (original.is(Type.DIRECT_OBSERVABLE)) {
      if (by == null) {
        return ObservableBuilder.makeType(ObservableBuilder.makeObservability(original, true),
            true);
      } else {

      }

    } else if (original.is(Type.QUALITY)) {

    }
    throw new KlabValidationException("concept " + original + " can not be classified"
        + (problem == null ? "" : (": " + problem)));
  }

  /**
   * Return the type we should use in a classifier that classifies the first one (returned
   * {@link #getClassificationType(IConcept, IConcept)} into the stated classified concept. If the
   * type is not adequate for that throw a validation exception explaining why.
   * 
   * @param classifier
   * @param classified
   * @return
   * @throws KlabValidationException
   */
  public IConcept getClassifiedType(IConcept classifier, IConcept classified)
      throws KlabValidationException {
    return null;
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

  private void findAtLevel(IKimObject mo, List<IConcept> ret, int level, int current,
      boolean filterAbstract) {

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
   * Produces the quality observable that will carry the contextualizable meaning of one or more
   * universals. Concept must exist but it may not be a type yet.
   * 
   * @param type
   * 
   * @param exposedTraits
   * @return
   */
  public void setExposedTraits(IConcept type, Collection<IConcept> exposedTraits) {
    OWL.INSTANCE.restrictSome(type, Concepts.p(NS.EXPOSES_TRAIT_PROPERTY), LogicalConnector.UNION,
        exposedTraits);
  }

  /**
   * True if trait is exposedTrait, or if trait is a valid value for it - e.g. is part of the
   * restricted closure of a "down to" specification.
   * 
   * @param trait
   * @param exposedTrait
   * @return
   */
  public boolean isExposedAs(IKnowledge trait, IConcept exposedTrait) {
    if (trait.is(exposedTrait)) {
      return true;
    }
    for (IConcept c : OWL.INSTANCE.getRestrictedClasses(exposedTrait,
        Concepts.p(NS.LIMITED_BY_PROPERTY))) {
      if (trait.is(c)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Create a classification based on the encodings stored as metadata in the concept hierarchy.
   * 
   * @param rootClass
   * @param metadataEncodingProperty
   * @return classification
   * @throws KlabValidationException
   */
  public IClassification createClassificationFromMetadata(IConcept rootClass,
      String metadataEncodingProperty, String metadataFormat) throws KlabValidationException {

    Classification ret = Classification.create(rootClass);

    for (IKnowledge c : ret.getConcept().getSemanticClosure()) {

      IMetadata m = c.getMetadata();
      Object o = m.get(metadataEncodingProperty);

      if (metadataFormat != null
          && (o != null && !(o instanceof Double && Double.isNaN((Double) o)))) {
        switch (metadataFormat) {
          case "text":
            o = o.toString();
          case "integer":
            o = Integer.parseInt(o.toString());
          default:
            throw new KlabValidationException(
                "cannot interpret value " + o + " as " + metadataFormat);
        }
      }

      if (o != null) {
        ret.addClassifier(Classifier.create(o), (IConcept) c);
      }
    }

    return ret;
  }

}
