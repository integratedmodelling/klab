package org.integratedmodelling.klab;

import org.integratedmodelling.kim.api.IModel;
import org.integratedmodelling.kim.model.KimModel;
import org.integratedmodelling.klab.api.services.IModelService;

public enum Models implements IModelService {

    INSTANCE;

    /**
     * Build a model from its parsed, syntax-error-free representation coming
     * from the Kim validator. May add logical errors, which are reported back when this
     * is called from an intelligent editor.
     * 
     * @param model
     * @return
     */
    public IModel build(KimModel model) {
        return null;
    }
    
}
