package org.integratedmodelling.klab.hub.customProperties.services;

import java.util.List;
import java.util.Optional;

import org.integratedmodelling.klab.hub.customProperties.commands.NewCustomProperty;
import org.integratedmodelling.klab.hub.customProperties.dto.CustomProperty;
import org.integratedmodelling.klab.hub.customProperties.enums.CustomPropertyType;
import org.integratedmodelling.klab.hub.repository.CustomPropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomPropertyServicesImpl implements CustomPropertyService {

    CustomPropertyRepository customPropertyRepository;

    @Autowired
    public CustomPropertyServicesImpl(CustomPropertyRepository customPropertyRepository) {
        super();
        this.customPropertyRepository = customPropertyRepository;
    }

    @Override
    public List<CustomProperty> getAllCustomProperties() {
        return customPropertyRepository.findAll();
    }

    @Override
    public List<CustomProperty> getCustomPropertiesByType(CustomPropertyType customPropertiesType) throws Exception {
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
    public CustomProperty createNewCustomProperties(CustomProperty customProperties) throws Exception {
        CustomProperty customPropertiesCreated = null;
        try {
            customPropertiesCreated = new NewCustomProperty(customProperties, customPropertyRepository).execute();
        } catch (Exception e) {
            throw new Exception("Error creating new custom Property");
        }
        return customPropertiesCreated;
    }

    @Override
    public CustomProperty createNewCustomProperties(CustomPropertyType customPropertiesType, String name)
            throws Exception {

        Optional<CustomProperty> customPropertyOptional = customPropertyRepository.findByName(name);

        CustomProperty customProperties = customPropertyOptional.isPresent()
                ? customPropertyOptional.get()
                : new CustomProperty(name);

//        switch(customPropertiesType) {
//
//        case USER:
//            customProperties.setForUser(true);
//            customProperties.
//            break;
//        case GROUP:
//            customProperties.setForGroup(true);
//            break;
//        case ALL:
//            customProperties.setForUser(true);
//            customProperties.setForGroup(true);
//            break;
//        default:
//            throw new Exception("Custom properties type isn't correct");
//        }

        return createNewCustomProperties(customProperties);
    }

}
