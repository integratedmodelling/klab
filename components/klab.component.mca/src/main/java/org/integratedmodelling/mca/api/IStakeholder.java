package org.integratedmodelling.mca.api;

import java.util.List;

import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.exceptions.KlabException;

public interface IStakeholder {
	
    IDirectObservation getSubject();

    /**
     * Return all alternatives retained, making sure everything is initialized.
     * @return alternatives at transition
     * HMM not sure
     */
    List<IAlternative> getAlternatives(ILocator locator);

    void rankAlternatives(ILocator transition) throws KlabException;

    boolean canValue(IAlternative alternative);

}
