package org.integratedmodelling.klab.hub.customProperties.services;

import java.util.List;

import org.integratedmodelling.klab.hub.customProperties.dto.CustomProperty;
import org.integratedmodelling.klab.hub.customProperties.enums.CustomPropertyType;
import org.springframework.stereotype.Service;

@Service
public abstract interface CustomPropertyService {

    public List<CustomProperty> getAllCustomProperties();
    public List<CustomProperty> getCustomPropertiesByType(CustomPropertyType customPropertiesType) throws Exception;
    public CustomProperty createNewCustomProperties(CustomProperty customProperties) throws Exception;
    public CustomProperty createNewCustomProperties(CustomPropertyType customPropertiesType, String name) throws Exception;

}
