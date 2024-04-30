package org.integratedmodelling.klab.hub.recordedCustomProperty.services;

import java.util.List;
import java.util.Optional;

import org.integratedmodelling.klab.hub.recordedCustomProperty.commands.NewCustomProperty;
import org.integratedmodelling.klab.hub.recordedCustomProperty.dto.RecordedCustomProperty;
import org.integratedmodelling.klab.hub.recordedCustomProperty.enums.CustomPropertyType;
import org.integratedmodelling.klab.hub.repository.RecordedCustomPropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecordedCustomPropertyServicesImpl implements RecordedCustomPropertyService {

    RecordedCustomPropertyRepository customPropertyRepository;

    @Autowired
    public RecordedCustomPropertyServicesImpl(RecordedCustomPropertyRepository customPropertyRepository) {
        super();
        this.customPropertyRepository = customPropertyRepository;
    }

    @Override
    public List<RecordedCustomProperty> getAllCustomProperties() {
        return customPropertyRepository.findAll();
    }

    @Override
    public List<RecordedCustomProperty> getCustomPropertiesByType(CustomPropertyType customPropertiesType) throws Exception {
        switch(customPropertiesType) {
        case USER:
            return customPropertyRepository.findByIsForUserIsTrue();
        case GROUP:
            return customPropertyRepository.findByIsForGroupIsTrue();
        case ALL:
            return customPropertyRepository.findAll();
        default:
            throw new Exception("Custom properties type isn't correct");
        }

    }

    @Override
    public RecordedCustomProperty createNewCustomProperties(RecordedCustomProperty customProperties) throws Exception {
        RecordedCustomProperty customPropertiesCreated = null;
        try {
            customPropertiesCreated = new NewCustomProperty(customProperties, customPropertyRepository).execute();
        } catch (Exception e) {
            throw new Exception("Error creating new custom Property");
        }
        return customPropertiesCreated;
    }

    @Override
    public RecordedCustomProperty createNewCustomProperties(CustomPropertyType customPropertiesType, String name)
            throws Exception {

        Optional<RecordedCustomProperty> customPropertyOptional = customPropertyRepository.findByName(name);

        RecordedCustomProperty customProperties = customPropertyOptional.isPresent()
                ? customPropertyOptional.get()
                : new RecordedCustomProperty(name);

        switch(customPropertiesType) {

        case USER:
            customProperties.setForUser(true);
            break;
        case GROUP:
            customProperties.setForGroup(true);
            break;
        case ALL:
            customProperties.setForUser(true);
            customProperties.setForGroup(true);
            break;
        default:
            throw new Exception("Custom properties type isn't correct");
        }

        return createNewCustomProperties(customProperties);
    }

}
