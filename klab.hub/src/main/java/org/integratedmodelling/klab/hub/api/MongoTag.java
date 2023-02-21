package org.integratedmodelling.klab.hub.api;

import java.util.regex.Pattern;

import javax.validation.constraints.NotNull;

import org.integratedmodelling.klab.rest.HubNotificationMessage;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Document(collection = "Tags")
@TypeAlias("MongoTag")
public class MongoTag {

    @Id
    @NotNull(message = "Name field cannot be null or blank")
    @Indexed(unique = true)
    private String name;

    @Enumerated(EnumType.STRING)
    private HubNotificationMessage.Type type = HubNotificationMessage.Type.INFO;

    public MongoTag() {
    }

    public void setType(HubNotificationMessage.Type type) {
        this.type = type;
    }

    private boolean isValidName(String name) {
        if (name.isEmpty()) {
            return false;
        }
        return Pattern.compile("([a-zA-Z0-9])+(-([a-zA-Z0-9])+)*")
                .matcher(name).matches();
    }

    public void setName(String name) {
        if(!isValidName(name)) {
            throw new IllegalArgumentException("Tag has not a valid format");
        }
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public HubNotificationMessage.Type getType() {
        return type;
    }

}
