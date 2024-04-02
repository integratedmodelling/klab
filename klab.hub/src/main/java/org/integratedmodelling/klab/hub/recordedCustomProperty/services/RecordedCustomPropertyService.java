package org.integratedmodelling.klab.hub.recordedCustomProperty.services;

import java.util.List;

import org.integratedmodelling.klab.hub.recordedCustomProperty.dto.RecordedCustomProperty;
import org.integratedmodelling.klab.hub.recordedCustomProperty.enums.CustomPropertyType;
import org.springframework.stereotype.Service;

@Service
public abstract interface RecordedCustomPropertyService {

    public List<RecordedCustomProperty> getAllCustomProperties();
    public List<RecordedCustomProperty> getCustomPropertiesByType(CustomPropertyType customPropertiesType) throws Exception;
    public RecordedCustomProperty createNewCustomProperties(RecordedCustomProperty customProperties) throws Exception;
    public RecordedCustomProperty createNewCustomProperties(CustomPropertyType customPropertiesType, String name) throws Exception;

}
