package org.integratedmodelling.klab.hub.customProperties.dto;

import java.util.Map;

import org.integratedmodelling.klab.authmodels.AuthType;

import com.fasterxml.jackson.core.type.TypeReference;

public enum CustomPropertyKey {
    GEOSERVER_KEYS("GEOSERVER_KEYS"),
    S3_KEYS("S3_KEYS"),
    AZURE_KEYS("AZURE_KEYS");

    private final String key;

    CustomPropertyKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public TypeReference<?> getTypeReference() {
        switch (this) {
            case GEOSERVER_KEYS:
            case S3_KEYS:
            case AZURE_KEYS:
                return new TypeReference<Map<String, AuthType>>() {};
            default:
                return new TypeReference<String>() {};
        }
    }

    public static CustomPropertyKey fromKey(String key) {
        for (CustomPropertyKey cpKey : values()) {
            if (cpKey.getKey().equals(key)) {
                return cpKey;
            }
        }
        return null;
    }
}
