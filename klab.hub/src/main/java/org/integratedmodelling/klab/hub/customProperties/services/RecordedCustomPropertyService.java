package org.integratedmodelling.klab.hub.customProperties.services;

import java.util.List;

import org.integratedmodelling.klab.hub.api.RecordedCustomProperty;
import org.integratedmodelling.klab.hub.customProperties.enums.CustomPropertiesType;
import org.springframework.stereotype.Service;

@Service
public abstract interface RecordedCustomPropertyService {

    public List<RecordedCustomProperty> getAllCustomProperties();
    public List<RecordedCustomProperty> getCustomPropertiesByType(CustomPropertiesType customPropertiesType) throws Exception;
    public RecordedCustomProperty createNewCustomProperties(RecordedCustomProperty customProperties) throws Exception;
    public RecordedCustomProperty createNewCustomProperties(CustomPropertiesType customPropertiesType, String name) throws Exception;

}
