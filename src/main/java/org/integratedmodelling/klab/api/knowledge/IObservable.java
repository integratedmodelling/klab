package org.integratedmodelling.klab.api.knowledge;

import org.integratedmodelling.klab.api.data.mediation.ICurrency;
import org.integratedmodelling.klab.api.data.mediation.INumericRange;
import org.integratedmodelling.klab.api.data.mediation.IUnit;

/**
 * An observable is a declared concept with additional observation semantics, including classifying concepts
 * for discretizations, units, currencies or ranges. The declaration of non-quality concepts will always be
 * the same as their type.
 * 
 * @author ferdinando.villa
 *
 */
public interface IObservable extends IConcept {

    /**
     * Observables always have a name, which is unique in the context of a model where they are used.
     * 
     * @return the formal name of this observable
     */
    String getName();

    /**
     * Return the untransformed concept, which is identical to the type returned by {@link #getType()} unless
     * a "by" (and possibly a "down to") predicate was specified.
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
     * If the observable was defined with an inline value (e.g. '10 as Concept'), report
     * the POD value here. 
     * @return
     */
    Object getValue();

}
