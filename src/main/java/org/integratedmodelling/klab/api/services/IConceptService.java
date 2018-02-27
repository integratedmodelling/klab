package org.integratedmodelling.klab.api.services;

import java.util.Collection;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IProperty;
import org.integratedmodelling.klab.exceptions.KlabValidationException;

public interface IConceptService {

  IProperty getProperty(String propertyId);

  IConcept getConcept(String conceptId);

  /**
   * Build a concept from its k.IM declaration. The resulting concept will return a normalized
   * version of the k.IM code in its {@link IConcept#getDefinition()} method.
   * 
   * See {@link IObservableService#declare(String)} for the version that accepts observable
   * semantics as well.
   * 
   * @param declaration
   * @return the concept corresponding to a k.IM definition.
   * @throws KlabValidationException if the declaration contains errors or unknown concepts.
   */
  // IConcept declare(String declaration) throws KlabValidationException;

  // IConcept declare(IKimConcept observable, IMonitor monitor);

  Collection<IConcept> getLeastGeneral(Collection<IConcept> cc);

  IConcept getLeastGeneralCommonConcept(Collection<IConcept> cc);

  IConcept getLeastGeneralCommonConcept(IConcept concept1, IConcept c);

  IConcept getLeastGeneralConcept(Collection<IConcept> cc);

  /**
   * Finds a metadata field in the inheritance chain of a concept. {@link IConcept#getMetadata()}
   * only retrieves metadata for the concept it's called on.
   * 
   * @param concept
   * @param field
   * @return the metadata field or null
   */
  Object getMetadata(IConcept concept, String field);

  /**
   * Return the number of parent "generations" between from and to, or -1 if the concepts have no
   * common lineage.
   * 
   * @param from
   * @param to
   * @return 0 or a positive number if the concepts have common lineage, -1 otherwise.
   */
  int getAssertedDistance(IConcept from, IConcept to);

  /**
   * Find a common denominator and return n > 0 if c1 is more specific than c2, 0 if same, n < 0
   * otherwise. Return {@link Integer#MIN_VALUE} if the concepts have nothing in common.
   * 
   * @param c1
   * @param c2
   * @param useBaseTrait if true, compare against the base trait of both concepts instead of the
   *        least general common ancestor. Integer.MIN_VALUE is returned if the base trait does not
   *        exist or is not the same.
   * @return the result of comparing, or Integer.MIN_VALUE if concepts are not comparable.
   */
  int compareSpecificity(IConcept c1, IConcept c2, boolean useBaseTrait);

}
