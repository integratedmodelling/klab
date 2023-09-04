package org.integratedmodelling.klab.hub.customProperties.commands;

import org.integratedmodelling.klab.hub.api.RecordedCustomProperty;
import org.integratedmodelling.klab.hub.repository.RecordedCustomPropertyRepository;

public class UpdateRecordedCustomProperty implements RecordedCustomPropertyCommand {

    private RecordedCustomProperty customProperty;
    private RecordedCustomPropertyRepository customPropertyRepository;

    public UpdateRecordedCustomProperty(RecordedCustomProperty customProperty,
            RecordedCustomPropertyRepository customPropertyRepository) {
        this.customProperty = customProperty;
        this.customPropertyRepository = customPropertyRepository;
    }

    @Override
    public RecordedCustomProperty execute() {
        customPropertyRepository.save(customProperty);
        return customProperty;
    }
}
