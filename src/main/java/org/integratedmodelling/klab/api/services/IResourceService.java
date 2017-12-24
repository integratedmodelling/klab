package org.integratedmodelling.klab.api.services;

import java.io.File;

import org.integratedmodelling.kim.api.IKimFunctionCall;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.exceptions.KlabUnauthorizedUrnException;
import org.integratedmodelling.klab.exceptions.KlabUnknownUrnException;

public interface IResourceService {

    /**
     * Resolve the passed URN to a resource. 
     * @param urn the 
     * @return a resource
     * @throws KlabUnknownUrnException
     * @throws KlabUnauthorizedUrnException
     */
    IResource getResource(String urn) throws KlabUnknownUrnException, KlabUnauthorizedUrnException;

    /**
     * 
     * @param file
     * @return
     */
	IResource getLocalFileResource(File file);

	/**
	 * 
	 * @param function
	 * @return
	 */
	IResource getComputedResource(IKimFunctionCall function);

	/**
	 * 
	 * @param inlineResource
	 * @return
	 */
	IResource getLiteralResource(Object inlineResource);
    
}
