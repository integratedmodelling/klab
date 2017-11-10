package org.integratedmodelling.klab;

import org.integratedmodelling.klab.api.extensions.Component;
import org.integratedmodelling.klab.api.extensions.Prototype;
import org.integratedmodelling.klab.api.services.IExtensionService;

public enum Extensions implements IExtensionService {
    
    INSTANCE;

    @Override
    public void registerPrototype(Prototype annotation, Class<?> cls) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void registerComponent(Component annotation, Class<?> cls) {
        // TODO Auto-generated method stub
        
    }
    
    

}
