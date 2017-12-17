package org.integratedmodelling.klab.api.services;

import org.integratedmodelling.klab.api.model.IModel;

public interface IModelService {

    /**
     * Release all models pertaining to named namespace, both in live and
     * persistent storage.
     * 
     * @param name
     */
    void releaseNamespace(String name);

    /**
     * Store model in kbox.
     * 
     * @param model
     */
    void index(IModel model);

}
