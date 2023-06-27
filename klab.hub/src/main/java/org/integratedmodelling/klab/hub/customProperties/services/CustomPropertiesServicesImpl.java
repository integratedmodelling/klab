package org.integratedmodelling.klab.hub.customProperties.services;

import java.util.List;

import org.integratedmodelling.klab.hub.api.CustomProperties;
import org.integratedmodelling.klab.hub.customProperties.commands.NewCustomProperty;
import org.integratedmodelling.klab.hub.customProperties.enums.CustomPropertiesType;
import org.integratedmodelling.klab.hub.repository.CustomPropertiesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Optional;

@Service
public class CustomPropertiesServicesImpl implements CustomPropertiesServices {

    CustomPropertiesRepository customPropertiesRepository;

    @Autowired
    public CustomPropertiesServicesImpl(CustomPropertiesRepository customPropertiesRepository) {
        super();
        this.customPropertiesRepository = customPropertiesRepository;
    }

    @Override
    public List<CustomProperties> getAllCustomProperties() {
        return customPropertiesRepository.findAll();
    }

    @Override
    public List<CustomProperties> getCustomPropertiesByType(CustomPropertiesType customPropertiesType) throws Exception {
        switch(customPropertiesType) {
        case USER:
            return customPropertiesRepository.findByIsForUserIsTrue();
        case GROUP:
            return customPropertiesRepository.findByIsForGroupIsTrue();
        case ALL:
            return customPropertiesRepository.findAll();
        default:
            throw new Exception("Custom properties type isn't correct");
        }

    }

    @Override
    public CustomProperties createNewCustomProperties(CustomProperties customProperties) throws Exception {
        CustomProperties customPropertiesCreated = null;
        try {
            customPropertiesCreated = new NewCustomProperty(customProperties, customPropertiesRepository).execute();
        } catch (Exception e) {
            throw new Exception("Error creating new custom Property");
        }
        return customPropertiesCreated;
    }

    @Override
    public CustomProperties createNewCustomProperties(CustomPropertiesType customPropertiesType, String name) throws Exception {
        
        Optional<CustomProperties> customPropertyOptional = customPropertiesRepository.findByName(name);
        
        CustomProperties customProperties = customPropertyOptional.isPresent() ? customPropertyOptional.get() :  new CustomProperties(name);
        
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
