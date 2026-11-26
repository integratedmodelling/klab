package klab.commons.customProperties;

import java.io.IOException;
import java.util.Map;
import java.util.function.Function;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import klab.commons.customProperties.auth.AuthType;


public enum CustomPropertyKey {
    GEOSERVER_KEYS("GEOSERVER_KEYS", new TypeReference<Map<String, AuthType>>() {}),
    S3_KEYS("S3_KEYS", new TypeReference<Map<String, AuthType>>() {}),
    AZURE_KEYS("AZURE_KEYS", new TypeReference<Map<String, AuthType>>() {});

    private final String key;
    private final TypeReference<?> typeReference;
    private final Function<String, Object> deserializer;

    CustomPropertyKey(String key, TypeReference<?> typeReference) {
        this(key, typeReference, null);
    }

    CustomPropertyKey(String key, TypeReference<?> typeReference, Function<String, Object> deserializer) {
        this.key = key;
        this.typeReference = typeReference;
        this.deserializer = deserializer;
    }

    public String getKey() {
        return key;
    }

    public TypeReference<?> getTypeReference() {
        return typeReference != null ? typeReference : new TypeReference<String>() {};
    }

    public boolean hasCustomDeserializer() {
        return deserializer != null;
    }

    public Object deserialize(String value, ObjectMapper objectMapper) throws IOException {
        if (deserializer != null) {
            return deserializer.apply(value);
        }
        return objectMapper.readValue(value, getTypeReference());
    }

    public static CustomPropertyKey fromKey(String key) {
        for (CustomPropertyKey cpKey : values()) {
            if (cpKey.getKey().equalsIgnoreCase(key)) {
                return cpKey;
            }
        }
        return null;
    }
}