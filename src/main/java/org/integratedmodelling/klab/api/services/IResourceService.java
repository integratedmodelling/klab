package org.integratedmodelling.klab.api.services;

import java.io.File;

import org.integratedmodelling.kim.api.IKimFunctionCall;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.model.IKimObject;
import org.integratedmodelling.klab.api.resolution.IResolvable;
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

    /**
     * Retrieve a model object identified through a URN - either an observer or a
     * model, local or remote, in the latter case triggering any necessary synchronization with
     * the network.
     * 
     * @param urn
     * @return the model object corresponding to the urn, or null if not found.
     */
    IKimObject getModelObject(String urn);

    /**
     * Retrieve a resolvable object identified by a URN, promoting any resource that is not
     * directly resolvable to the correspondent resolvable when possible. 
     * 
     * @param urn
     * @return a resolvable resource, or null if nothing can be found.
     */
    IResolvable getResolvableResource(String urn);

}
