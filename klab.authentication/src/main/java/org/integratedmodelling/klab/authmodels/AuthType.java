package org.integratedmodelling.klab.authmodels;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * Base class for authentication types.
 * Jackson will use the 'type' property to determine the concrete subclass during deserialization.
 */
@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME, // uses a field with the type name
    include = JsonTypeInfo.As.PROPERTY, // the field is included as a property
    property = "type" // this is the field that indicates the type
)
@JsonSubTypes({
    @JsonSubTypes.Type(value = BearerAuth.class, name = "bearer"),
    @JsonSubTypes.Type(value = BasicAuth.class, name = "basic")
})
public abstract class AuthType {
    // common to all (optional)
}