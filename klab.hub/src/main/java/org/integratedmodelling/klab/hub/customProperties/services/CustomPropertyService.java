package org.integratedmodelling.klab.hub.customProperties.services;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.integratedmodelling.klab.hub.customProperties.dto.CustomProperty;
import org.integratedmodelling.klab.hub.customProperties.enums.CustomPropertyType;
import org.integratedmodelling.klab.rest.CustomPropertyRest;
import org.integratedmodelling.klab.rest.ICustomProperty;
import org.springframework.stereotype.Service;

@Service
public abstract interface CustomPropertyService {

    public List<CustomProperty> getAllCustomProperties();
    public List<CustomProperty> getCustomPropertiesByType(CustomPropertyType customPropertiesType) throws Exception;
    public CustomProperty createNewCustomProperties(CustomProperty customProperties) throws Exception;
    public CustomProperty createNewCustomProperties(CustomPropertyType customPropertiesType, String name) throws Exception;
    

    /**
     * Converts any implementation of ICustomProperty to CustomPropertyRest.
     * It resolves any secret placeholders in the value by creating a temporary CustomProperty,
     * then deserializes the value if applicable.
     *
     * @param customProperty any object implementing ICustomProperty
     * @return CustomPropertyRest with resolved secrets and properly deserialized value
     */
    public CustomPropertyRest convertAndResolveSecretsToRest(ICustomProperty customProperty);

    /**
     * Recursively processes a JsonNode to resolve any secret placeholders.
     * 
     * <p>For each field whose key contains the word "secret" (case-insensitive) and has a textual value,
     * the method retrieves the actual secret value from the Vault using {@code vaultService.getSecret()} 
     * and replaces the placeholder with the resolved value in the JSON structure.</p>
     *
     * <p>This method modifies the input JsonNode directly if it's an ObjectNode. Arrays and nested objects
     * are also processed recursively.</p>
     *
     * @param node the input JSON structure to scan for secret placeholders
     * @return the modified JsonNode with resolved secrets
     * @throws IOException if an error occurs while communicating with Vault
     * @throws InterruptedException if the thread is interrupted while retrieving a secret
     */
    public void processSecrets(CustomProperty customProperty) throws IOException, InterruptedException;

    /**
     * Converts a Set of ICustomProperty into a Map with keys and resolved values.
     * For each property, it applies the toCustomPropertyRest method (which resolves secrets)
     * and maps the key to the valueObject if present, otherwise to the string value.
     * 
     * @param properties List of ICustomProperty to process
     * @return Map with property keys as keys and resolved value objects or strings as values
     */
    Map<String, CustomPropertyRest> resolvePropertiesToMap(Set< ? extends ICustomProperty> properties);


}
