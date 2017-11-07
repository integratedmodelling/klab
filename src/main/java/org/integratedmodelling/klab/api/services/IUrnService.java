package org.integratedmodelling.klab.api.services;

import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.exceptions.KlabUnauthorizedUrnException;
import org.integratedmodelling.klab.exceptions.KlabUnknownUrnException;

public interface IUrnService {

    /**
     * Resolve the passed URN to a resource. 
     * @param urn the 
     * @return
     * @throws KlabUnknownUrnException
     * @throws KlabUnauthorizedUrnException
     */
    IResource resolve(String urn) throws KlabUnknownUrnException, KlabUnauthorizedUrnException;
    
}
