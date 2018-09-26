package klab.component.klab.mca.api;

import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.observations.IDirectObservation;

public interface IAlternative {

    boolean isDistributed();

    double getValueOf(IConcept k, int offset, IDirectObservation offsetContext);

    boolean hasCriterion(IConcept observable);

    IDirectObservation getSubject();

    /**
     * If the alternative is not distributed, this will return the subject ID, otherwise some progressive
     * string made from it.
     * 
     * @return ID of alternative
     */
    String getId();

}
