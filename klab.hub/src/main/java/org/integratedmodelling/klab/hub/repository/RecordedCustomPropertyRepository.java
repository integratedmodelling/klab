package org.integratedmodelling.klab.hub.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.integratedmodelling.klab.hub.recordedCustomProperty.dto.RecordedCustomProperty;
import org.springframework.stereotype.Repository;

@Repository
public interface RecordedCustomPropertyRepository extends ResourceRepository<RecordedCustomProperty, String> {

    List<RecordedCustomProperty> findByIsForUserIsTrue();
    List<RecordedCustomProperty> findByIsForGroupIsTrue();
    Optional<RecordedCustomProperty> findByName(String name);
    List<RecordedCustomProperty> findAllByNameIn(Collection<String> names);

}
