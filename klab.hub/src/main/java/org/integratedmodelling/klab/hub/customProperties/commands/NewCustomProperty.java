package org.integratedmodelling.klab.hub.customProperties.commands;

import org.integratedmodelling.klab.hub.api.CustomProperties;
import org.integratedmodelling.klab.hub.repository.CustomPropertiesRepository;

public class NewCustomProperty implements CustomPropertiesCommand{
    
    private CustomProperties customProperties;
    private CustomPropertiesRepository customPropertiesRepository;
    
    public NewCustomProperty (CustomProperties customProperties,
            CustomPropertiesRepository customPropertiesRepository) {
            this.customProperties = customProperties;
            this.customPropertiesRepository = customPropertiesRepository;
    }
    
    @Override
    public CustomProperties execute() {
        customPropertiesRepository.save(customProperties);      
        return customProperties;
    }
}
