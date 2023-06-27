package org.integratedmodelling.klab.hub.customProperties.services;

import java.util.List;

import org.integratedmodelling.klab.hub.api.CustomProperties;
import org.integratedmodelling.klab.hub.customProperties.enums.CustomPropertiesType;
import org.springframework.stereotype.Service;

@Service
public abstract interface CustomPropertiesServices {
    
    public List<CustomProperties> getAllCustomProperties();
    public List<CustomProperties> getCustomPropertiesByType(CustomPropertiesType customPropertiesType) throws Exception;
    public CustomProperties createNewCustomProperties(CustomProperties customProperties) throws Exception;
    public CustomProperties createNewCustomProperties(CustomPropertiesType customPropertiesType, String name) throws Exception;

}
