package org.integratedmodelling.klab.api.knowledge;

import java.util.List;
import org.integratedmodelling.kim.utils.Range;
import org.integratedmodelling.klab.api.data.mediation.ICurrency;
import org.integratedmodelling.klab.api.data.mediation.INumericRange;
import org.integratedmodelling.klab.api.data.mediation.IUnit;
import org.integratedmodelling.klab.api.resolution.IResolvable;

/**
 * An observable is a declared concept with additional observation semantics, including classifying
 * concepts for discretizations, units, currencies or ranges. The declaration of non-quality
 * concepts will always be the same as their type.
 * 
 * @author ferdinando.villa
 *
 */
public interface IObservable extends IConcept, IResolvable {

  /**
   * A classification of the observation activity that can produce an observation of this
   * observable.
   * 
   * @author ferdinando.villa
   *
   */
  public enum ObservationType {
    /**
     * The observation that produces a countable object
     */
    INSTANTIATION,
    /**
     * The observation that produces a configuration
     */
    DETECTION,
    /**
     * The observation that produces a dynamic account
     */
    SIMULATION,
    /**
     * The observation that produces a numeric quality
     */
    QUANTIFICATION,
    /**
     * The observation that produces a categorical quality
     */
    CLASSIFICATION,
    /**
     * The observation that produces a boolean quality (presence/absence)
     */
    VERIFICATION
  }

  /**
   * Each observable must be able to quickly assess the type of the observation that will produce an
   * IObservation of it. This is also used to instantiate the storage for states.
   * 
   * @return the necessary observation type
   */
  ObservationType getObservationType();

  /**
   * Observables always have a name, which is unique in the context of a model where they are used,
   * and can be used within a model to refer to the observation made of it. The name can be
   * explicitly set using the 'named' k.IM clause, and is always a simple lowercase identifier.
   * 
   * @return the formal name of this observable
   */
  String getLocalName();

  /**
   * Return the untransformed concept, which will be identical to the type returned by
   * {@link #getType()} unless a "by" (and possibly a "down to") predicate was specified.
   * 
   * @return the declared concept before any reclassification
   */
  IConcept getMain();

  /**
   * 
   * @return the normalized 'down to' limiter concept if any was specified.
   */
  IConcept getDownTo();

  /**
   * 
   * @return the 'by' classifier concept, if any was specified.
   */
  IConcept getBy();

  /**
   * 
   * @return the numeric range, if any was specified.
   */
  INumericRange getRange();

  /**
   * 
   * @return the unit, if any was specified.
   */
  IUnit getUnit();

  /**
   * 
   * @return the currency, if any was specified.
   */
  ICurrency getCurrency();

  /**
   * If the observable was defined with an inline value (e.g. '10 as Concept'), report the POD value
   * here.
   * 
   * @return the inline value (a POD; a distribution, {@link Range} or {@link List} are also
   *         possible, but so far there are no situations in which this happens.)
   */
  Object getValue();

  /**
   * If true, observer produces an extensive value over the passed extent, one that varies with the
   * extents of computation. A true return value will cause different aggregation than the default
   * averaging when mediating to different scales.
   * 
   * @param c the extent concept selecting a particular extent
   * 
   * @return true if the value of the quality this represents is extensive in the extent concept
   *         passed
   */
  boolean isExtensive(IConcept c);

  /**
   * A generic observable expects to be resolved extensively - i.e., all the subtypes, leaving the
   * base type last if the subtypes don't provide full coverage. This subsumes the abstract nature
   * of the observable concept, but may also be true in dependency observables, which may explicitly
   * ask to be generic even if not abstract ('any' modifier).
   * 
   * @return true if generic
   */
  boolean isGeneric();
  
  /**
   * True if the observable was declared optional. This can only happen in model dependencies and
   * for the observables of acknowledged subjects.
   * 
   * @return optional status
   */
  boolean isOptional();

}
