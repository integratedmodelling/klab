package org.integratedmodelling.klab;

import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.services.IUrnService;
import org.integratedmodelling.klab.exceptions.KlabUnauthorizedUrnException;
import org.integratedmodelling.klab.exceptions.KlabUnknownUrnException;

/**
 * Static functions related to the management and resolution of URNs.
 * 
 * @author ferdinando.villa
 *
 */
public enum Urns implements IUrnService {
    INSTANCE;
    
    public IResource resolve(String urn) throws KlabUnknownUrnException, KlabUnauthorizedUrnException {
        return null;
    }
}
