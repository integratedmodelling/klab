package klab.commons.customProperties.utils;

import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import org.integratedmodelling.klab.rest.CustomPropertyRest;
import org.integratedmodelling.klab.rest.Group;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import klab.commons.customProperties.CustomPropertyKey;
import klab.commons.utils.JsonUtils;

/**
 * Utility class for handling deserialization of custom properties in groups.
 * <p>
 * This class centralizes the logic of converting raw JSON strings into
 * concrete Java objects based on the {@link CustomPropertyKey}.
 */

public class CustomPropertyUtils {
    
    private static final Logger LOGGER = Logger.getLogger(CustomPropertyUtils.class.getName());

    private final static ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    /**
     * Creates a new instance of {@link CustomPropertyUtils}.
     *
     * @param objectMapper the Jackson {@link ObjectMapper} used for deserialization
     */
    public CustomPropertyUtils() {
    }

    /**
     * Iterates through all groups and deserializes their custom properties.
     * Each property value is converted to its corresponding Java object.
     *
     * @param groups the set of groups containing custom properties
     */
    public static void deserializeCustomProperties(Set<? extends Group> groups) {
        deserialize(groups);
    }

    /**
     * Static helper method that deserializes custom properties using the given {@link ObjectMapper}.
     *
     * @param groups        the set of groups containing custom properties
     * @param objectMapper  the Jackson {@link ObjectMapper} used for deserialization
     */
    public static void deserialize(Set<? extends Group> groups) {
        for (Group g : groups) {
            for (Map.Entry<String, CustomPropertyRest> entry : g.getCustomPropertyMap().entrySet()) {
                CustomPropertyRest customPropertyRest = entry.getValue();

                CustomPropertyKey propertyKey = CustomPropertyKey.fromKey(entry.getKey());
                if (propertyKey != null) {
                    try {
                        Object valueObject = propertyKey.deserialize(customPropertyRest.getValue(), objectMapper);
                        customPropertyRest.setValueObject(valueObject);
                    } catch (Exception e) {
                        // a wrong property must not prevent the authentication: it is ignored
                        // (the value is not logged, as it can contain secrets)
                        LOGGER.warning("Custom property '" + entry.getKey() + "' of group '" + g.getId()
                                + "' cannot be read and is ignored");
                    }
                } else if (JsonUtils.isValidJson(customPropertyRest.getValue())) {
                    try {
                        customPropertyRest.setValueObject(objectMapper.readTree(customPropertyRest.getValue()));
                    } catch (Exception e) {
                        throw new RuntimeException("Error parsing value as JsonNode", e);
                    }
                }
            }
        }
    }
}

