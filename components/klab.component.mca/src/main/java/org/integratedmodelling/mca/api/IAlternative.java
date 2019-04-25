package org.integratedmodelling.mca.api;

import org.integratedmodelling.klab.api.observations.IDirectObservation;

public interface IAlternative {

    IDirectObservation getSubject();

    /**
     * If the alternative is not distributed, this will return the subject ID, otherwise some progressive
     * string made from it.
     * 
     * @return ID of alternative
     */
    String getId();

    /**
     * Value of passed criterion. Must be consistently scaled across any alternative set.
     * 
     * @param criterion
     * @return
     */
    double getValue(ICriterion criterion);

    /**
     * Available after ranking. Higher score means higher concordance with observer's priorities.
     * 
     * @return
     */
    double getScore();
    
    /**
     * This should return false if there is any unknown criterion or any constraints over
     * the criteria values aren't met.
     * 
     * @return true if OK
     */
    boolean isValid();

}
