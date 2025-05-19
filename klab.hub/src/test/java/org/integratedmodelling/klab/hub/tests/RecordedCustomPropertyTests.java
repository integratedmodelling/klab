package org.integratedmodelling.klab.hub.tests;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.integratedmodelling.klab.hub.customProperties.dto.CustomProperty;
import org.integratedmodelling.klab.hub.repository.CustomPropertyRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@DataMongoTest
@ExtendWith(SpringExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
public class RecordedCustomPropertyTests {
    @Autowired CustomPropertyRepository repository;

    List<String> exisingNames = List.of("test", "prueba", "froga");

    @BeforeAll
    public void addRecordedCustomProperties() {
        for (String name : exisingNames) {
            CustomProperty cp = new CustomProperty(name);
            repository.insert(cp);
        }
    }

    @Test
    public void getAllRecordedCustomPropertiesWithName() {
        List<CustomProperty> ret = repository.findAllByNameIn(exisingNames);

        assertTrue(ret.size() == exisingNames.size());
    }

}
