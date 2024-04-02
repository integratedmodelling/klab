package org.integratedmodelling.klab.hub.recordedCustomProperty.commands;

import org.integratedmodelling.klab.hub.recordedCustomProperty.dto.RecordedCustomProperty;
import org.integratedmodelling.klab.hub.repository.RecordedCustomPropertyRepository;

public class NewCustomProperty implements RecordedCustomPropertyCommand {

    private RecordedCustomProperty customProperty;
    private RecordedCustomPropertyRepository customPropertyRepository;

    public NewCustomProperty(RecordedCustomProperty customProperty,
            RecordedCustomPropertyRepository customPropertiesRepository) {
        this.customProperty = customProperty;
        this.customPropertyRepository = customPropertiesRepository;
    }

    @Override
    public RecordedCustomProperty execute() {
        customPropertyRepository.save(customProperty);
        return customProperty;
    }
}
