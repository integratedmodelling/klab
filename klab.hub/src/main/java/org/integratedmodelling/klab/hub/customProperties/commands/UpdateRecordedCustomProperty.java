package org.integratedmodelling.klab.hub.customProperties.commands;

import org.integratedmodelling.klab.hub.customProperties.dto.CustomProperty;
import org.integratedmodelling.klab.hub.repository.CustomPropertyRepository;

public class UpdateRecordedCustomProperty implements CustomPropertyCommand {

    private CustomProperty customProperty;
    private CustomPropertyRepository customPropertyRepository;

    public UpdateRecordedCustomProperty(CustomProperty customProperty,
            CustomPropertyRepository customPropertyRepository) {
        this.customProperty = customProperty;
        this.customPropertyRepository = customPropertyRepository;
    }

    @Override
    public CustomProperty execute() {
        customPropertyRepository.save(customProperty);
        return customProperty;
    }
}
