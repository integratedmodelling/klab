package org.integratedmodelling.klab.hub.customProperties.commands;

import org.integratedmodelling.klab.hub.customProperties.dto.CustomProperty;
import org.integratedmodelling.klab.hub.repository.CustomPropertyRepository;

public class NewCustomProperty implements CustomPropertyCommand {

    private CustomProperty customProperty;
    private CustomPropertyRepository customPropertyRepository;

    public NewCustomProperty(CustomProperty customProperty,
            CustomPropertyRepository customPropertiesRepository) {
        this.customProperty = customProperty;
        this.customPropertyRepository = customPropertiesRepository;
    }

    @Override
    public CustomProperty execute() {
        customPropertyRepository.save(customProperty);
        return customProperty;
    }
}
