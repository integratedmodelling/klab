package klab.commons.customProperties.auth;

import org.integratedmodelling.klab.rest.CustomPropertyTypeRest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * Base class for authentication types.
 * Jackson will use the 'type' property to determine the concrete subclass during deserialization.
 * <p>
 * The fields holding secrets must contain "secret" in their name (e.g. <code>passwordSecret</code>): their
 * values are Vault paths, which the Hub replaces with the secrets before sending the groups to the engine.
 * The other fields must not contain it.
 */
@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME, // uses a field with the type name
    include = JsonTypeInfo.As.PROPERTY, // the field is included as a property
    property = "type" // this is the field that indicates the type
)
@JsonSubTypes({
    @JsonSubTypes.Type(value = BearerAuth.class, name = "bearer"),
    @JsonSubTypes.Type(value = BasicAuth.class, name = "basic"),
    @JsonSubTypes.Type(value = AwsAuth.class, name = "aws"),
    @JsonSubTypes.Type(value = HeaderAuth.class, name = "header"),
    @JsonSubTypes.Type(value = QueryParamAuth.class, name = "query")
})
@JsonIgnoreProperties(ignoreUnknown = true)
public interface AuthType extends CustomPropertyTypeRest {
}
