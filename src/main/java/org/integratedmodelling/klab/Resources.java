package org.integratedmodelling.klab;

import java.io.File;

import org.integratedmodelling.kim.api.IKimFunctionCall;
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
    public IResource getResource(String urn) throws KlabUnknownUrnException, KlabUnauthorizedUrnException {
        return null;
    }
    
    
    @Override
    public IResource getLocalFileResource(File file) {
    	return null;
    }
    
    @Override
    public IResource getComputedResource(IKimFunctionCall function) {
    	return null;
    }

    @Override
    public IResource getLiteralResource(Object inlineResource) {
        return null;
    }
    
}
