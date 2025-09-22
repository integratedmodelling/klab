package org.integratedmodelling.klab.hub.customProperties.services;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.integratedmodelling.klab.hub.customProperties.commands.NewCustomProperty;
import org.integratedmodelling.klab.hub.customProperties.dto.CustomProperty;
import org.integratedmodelling.klab.hub.customProperties.enums.CustomPropertyType;
import org.integratedmodelling.klab.hub.repository.CustomPropertyRepository;
import org.integratedmodelling.klab.hub.vault.VaultService;
import org.integratedmodelling.klab.rest.CustomPropertyRest;
import org.integratedmodelling.klab.rest.ICustomProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;

import klab.commons.utils.JsonUtils;

@Service
public class CustomPropertyServicesImpl implements CustomPropertyService {

    private final CustomPropertyRepository customPropertyRepository;
    private final VaultService vaultService;    

    private static final Logger logger = LoggerFactory.getLogger(CustomPropertyServicesImpl.class);    

    @Autowired
    public CustomPropertyServicesImpl(CustomPropertyRepository customPropertyRepository, VaultService vaultService) {
        super();
        this.customPropertyRepository = customPropertyRepository;
        this.vaultService = vaultService;
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
    public CustomProperty createNewCustomProperties(CustomPropertyType customPropertiesType, String name) throws Exception {

        Optional<CustomProperty> customPropertyOptional = customPropertyRepository.findByName(name);

        CustomProperty customProperties = customPropertyOptional.isPresent()
                ? customPropertyOptional.get()
                : new CustomProperty(name);

        switch(customPropertiesType) {

        case USER:
            customProperties.setForUser(true);
            customProperties.setForGroup(false);
            break;
        case GROUP:
            customProperties.setForUser(false);
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

    /**
     * Converts a Set of ICustomProperty into a Map with keys and resolved values.
     * For each property, it applies the toCustomPropertyRest method (which resolves secrets)
     * and maps the key to the valueObject if present, otherwise to the string value.
     * 
     * @param properties List of ICustomProperty to process
     * @return Map with property keys as keys and resolved value objects or strings as values
     */
    @Override
    public Map<String, CustomPropertyRest> resolvePropertiesToMap(Set< ? extends ICustomProperty> properties) {
        return properties.stream().map(p -> {
            try {
                logger.debug("Resolving secrets and converting property with key '{}'", p.getKey());
                return convertAndResolveSecretsToRest(p);
            } catch (Exception e) {
                logger.error("Error processing property with key '{}': {}", p.getKey(), e.getMessage(), e);
                return null; // skip this property
            }
        }).filter(Objects::nonNull).collect(Collectors.toMap(CustomPropertyRest::getKey,
                p -> p));
    }

    /**
     * Converts any implementation of ICustomProperty to CustomPropertyRest.
     * It resolves any secret placeholders in the value by creating a temporary CustomProperty,
     * then deserializes the value if applicable.
     *
     * @param customProperty any object implementing ICustomProperty
     * @return CustomPropertyRest with resolved secrets and properly deserialized value
     */
    @Override
    public CustomPropertyRest convertAndResolveSecretsToRest(ICustomProperty customProperty) {
        CustomPropertyRest customPropertyRest = new CustomPropertyRest();
        customPropertyRest.setKey(customProperty.getKey());
        customPropertyRest.setOnlyAdmin(customProperty.isOnlyAdmin());

        String resolvedValue = customProperty.getValue(); // fallback initial value

        // Create a temporary CustomProperty to resolve secrets
        CustomProperty temp = new CustomProperty();
        temp.setKey(customProperty.getKey());
        temp.setOnlyAdmin(customProperty.isOnlyAdmin());
        temp.setValue(customProperty.getValue());

        try {
            processSecrets(temp); // resolve secrets using existing method
            resolvedValue = temp.getValue();
            logger.debug("Resolved secret value for key '{}'", customProperty.getKey());
        } catch (IOException | InterruptedException e) {
            logger.error("Failed to resolve secret for key '{}': {}", customProperty.getKey(), e.getMessage());
        }

        // Set the resolved value to the rest object
        customPropertyRest.setValue(resolvedValue);

        // If the key matches a specific type, deserialize accordingly
//        CustomPropertyKey propertyKey = CustomPropertyKey.fromKey(customProperty.getKey());
//        if (propertyKey != null) {
//            try {
//                CustomPropertyTypeRest valueObject = (CustomPropertyTypeRest) propertyKey.deserialize(resolvedValue, objectMapper);
//                customPropertyRest.setValueObject(valueObject);
//            } catch (Exception e) {
//                logger.error("Error deserializing value for key '{}': {}", customProperty.getKey(), e.getMessage());
//                throw new RuntimeException("Error deserializing value for key: " + customProperty.getKey(), e);
//            }
//        } else if (JsonUtils.isValidJson(resolvedValue)) {
//            try {
//                customPropertyRest.setValueObject((CustomPropertyTypeRest) objectMapper.readTree(resolvedValue));
//            } catch (Exception e) {
//                logger.error("Error parsing value as JsonNode for key '{}': {}", customProperty.getKey(), e.getMessage());
//                throw new RuntimeException("Error parsing value as JsonNode", e);
//            }
//        }

        return customPropertyRest;
    }

    /**
     * Resolves secret placeholders inside a CustomProperty.
     * 
     * <p>If the property's key contains "secret", it assumes the value is a Vault path and resolves it.
     * If the value is a JSON object or array, it recursively replaces any keys containing "secret"
     * (case-insensitive) with the resolved secret values from Vault.</p>
     *
     * @param customProperty the property to process
     * @return the updated CustomProperty with resolved secrets
     * @throws IOException if an error occurs while retrieving secrets
     * @throws InterruptedException if the thread is interrupted
     */
    public void processSecrets(CustomProperty customProperty) throws IOException, InterruptedException {
        String keyLower = customProperty.getKey().toLowerCase();

        logger.debug("Processing secrets for key: {}", customProperty.getKey());

        if (keyLower.contains("secret") && customProperty.getValueAsString() != null) {
            // If the key indicates this is a direct secret
            try {
                String secretValue = vaultService.getSecret(customProperty.getValueAsString());
                logger.debug("Resolved direct secret for key: {}", customProperty.getKey());
                customProperty.setValue(secretValue);
                return;
            } catch (Exception e) {
                logger.error("Failed to resolve direct secret for key: {}", customProperty.getKey(), e);
                throw e;
            }
        }

        String valueAsString = customProperty.getValueAsString();
        if (valueAsString != null && JsonUtils.isValidJson(valueAsString)) {
            try {
                // Try to treat the value as JSON and recursively resolve nested secrets
                JsonNode node = customProperty.getValueAsJsonNode();
                JsonNode processedNode = processSecretsRecursively(node);
                customProperty.setValue(processedNode);
                logger.debug("Resolved nested secrets for key: {}", customProperty.getKey());
            } catch (Exception e) {
                logger.error("Failed to process nested secrets for key: {}", customProperty.getKey(), e);
                throw e;
            }
        } else {
            // Value is not JSON, just keep as is
            logger.debug("Value is not JSON for key: {}, leaving as string", customProperty.getKey());
        }
    }

    private JsonNode processSecretsRecursively(JsonNode node) throws IOException, InterruptedException {
        if (node.isObject()) {
            ObjectNode objectNode = (ObjectNode) node;
            Iterator<Map.Entry<String, JsonNode>> fields = objectNode.fields();
            while (fields.hasNext()) {
                Map.Entry<String, JsonNode> entry = fields.next();
                String key = entry.getKey();
                JsonNode value = entry.getValue();

                if (key.toLowerCase().contains("secret") && value.isTextual()) {
                    String secretValue = vaultService.getSecret(value.asText());
                    objectNode.set(key, new TextNode(secretValue));
                    logger.debug("Resolved nested secret for key: {}", key);
                } else if (value.isTextual()) {
                    objectNode.set(key, value);
                } else {
                    JsonNode processedValue = processSecretsRecursively(value);
                    objectNode.set(key, processedValue);
                }
            }
            return objectNode;
        }

        if (node.isArray()) {
            ArrayNode arrayNode = (ArrayNode) node;
            for (int i = 0; i < arrayNode.size(); i++) {
                JsonNode processedItem = processSecretsRecursively(arrayNode.get(i));
                arrayNode.set(i, processedItem);
            }
            return arrayNode;
        }

        // Base case: return as-is
        return node;
    }


}
