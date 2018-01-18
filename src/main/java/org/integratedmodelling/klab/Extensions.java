package org.integratedmodelling.klab;

import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.integratedmodelling.kdl.api.IKdlDataflow;
import org.integratedmodelling.klab.api.extensions.Component;
import org.integratedmodelling.klab.api.extensions.IPrototype;
import org.integratedmodelling.klab.api.extensions.Prototype;
import org.integratedmodelling.klab.api.extensions.ResourceAdapter;
import org.integratedmodelling.klab.api.extensions.component.IComponent;
import org.integratedmodelling.klab.api.services.IExtensionService;
import org.integratedmodelling.klab.exceptions.KlabRuntimeException;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;

public enum Extensions implements IExtensionService {
    
    INSTANCE;

    Map<String, IComponent> components = new HashMap<>();
    
    public void registerPrototype(Prototype annotation, Class<?> cls) {
        
        // TODO Auto-generated method stub
        /*
         * Class must be a 'callable' object - contextualizer etc
         */
    }
    
    @Override
    public Collection<IComponent> getComponents() {
        return components.values();
    }
    
    @Override
    public IComponent getComponent(String componentId) {
        return components.get(componentId);
    }
    
    
    @Override
    public IPrototype getServicePrototype(String service) {
        return null;
    }

    public IComponent registerComponent(Component annotation, Class<?> cls) {

        IComponent ret = null;
        
        /*
         * TODO store knowledge for later processing
         */
        
        /*
         * TODO ingest all .kdl files in the component's path
         */
        for (String kdl : new Reflections(cls.getPackageName(), new ResourcesScanner())
                .getResources(Pattern.compile(".*\\.kdl"))) {
            try (InputStream input = cls.getClassLoader().getResourceAsStream(kdl)) {
                declareServices(ret, Dataflows.INSTANCE.declare(input));
            } catch (Exception e) {
                throw new KlabRuntimeException(e);
            }
        }

        return ret;
        
    }

	private void declareServices(IComponent component, IKdlDataflow declaration) {
        // TODO Auto-generated method stub
        
    }

    public void registerResourceAdapter(ResourceAdapter annotation, Class<?> cls) {
		// TODO Auto-generated method stub
		/*
		 * class must be a IResourceAdapter
		 */
	}
    
    

}
