package klab.commons.customProperties.auth;

import org.integratedmodelling.klab.rest.CustomPropertyTypeRest;

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
public interface AuthType extends CustomPropertyTypeRest {
}