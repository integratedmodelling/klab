package org.integratedmodelling.klab;

import org.integratedmodelling.klab.api.extensions.Component;
import org.integratedmodelling.klab.api.extensions.Prototype;
import org.integratedmodelling.klab.api.extensions.ResourceAdapter;
import org.integratedmodelling.klab.api.services.IExtensionService;

public enum Extensions implements IExtensionService {
    
    INSTANCE;

    public void registerPrototype(Prototype annotation, Class<?> cls) {
        // TODO Auto-generated method stub
        /*
         * Class must be a 'callable' object - contextualizer etc
         */
    }

    public void registerComponent(Component annotation, Class<?> cls) {
        // TODO Auto-generated method stub
    }

	public void registerResourceAdapter(ResourceAdapter annotation, Class<?> cls) {
		// TODO Auto-generated method stub
		/*
		 * class must be a IResourceAdapter
		 */
	}
    
    

}
