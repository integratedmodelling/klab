package org.integratedmodelling.klab;

import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.services.IResourceService;
import org.integratedmodelling.klab.exceptions.KlabUnauthorizedUrnException;
import org.integratedmodelling.klab.exceptions.KlabUnknownUrnException;

/**
 * Static functions related to the management and resolution of URNs.
 * 
 * @author ferdinando.villa
 *
 */
public enum Resources implements IResourceService {
    INSTANCE;

    @Override
    public IResource resolve(String urn) throws KlabUnknownUrnException, KlabUnauthorizedUrnException {
        return null;
    }

    /**
     * Non-API Return a full URN that is resolved to a {@link IResource} wrapping a literal or describing
     * contents of locally available resources, such as from a file.
     * 
     * @param inlineResource
     *            a POD value, filename or function call with POD value.
     * @return
     */
    public String getLocalUrn(Object inlineResource) {
        return null;
    }
    
}
