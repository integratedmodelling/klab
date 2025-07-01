package org.integratedmodelling.klab.hub.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.integratedmodelling.klab.hub.customProperties.dto.CustomProperty;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomPropertyRepository extends ResourceRepository<CustomProperty, String> {

    List<CustomProperty> findByIsForUserIsTrue();
    List<CustomProperty> findByIsForGroupIsTrue();
    Optional<CustomProperty> findByName(String name);
    List<CustomProperty> findAllByNameIn(Collection<String> names);
}
