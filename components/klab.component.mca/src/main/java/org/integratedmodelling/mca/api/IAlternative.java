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

	double getValue(ICriterion criterion);

}
