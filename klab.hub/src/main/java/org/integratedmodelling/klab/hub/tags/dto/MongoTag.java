package org.integratedmodelling.klab.hub.tags.dto;

import java.time.Period;
import java.util.regex.Pattern;

import javax.validation.constraints.NotNull;

import org.integratedmodelling.klab.hub.api.ITagElement;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "Tags")
@TypeAlias("MongoTag")
public class MongoTag {

    @Id
    @Indexed(unique = true)
    private String id;

    @NotNull(message = "Name field cannot be null or blank")
    private String name;

    @Field("Username")
    private String username;

    private Period timeToExpiration;

    private ITagElement iTagElement;

    private Boolean visible;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ITagElement getiTagElement() {
        return iTagElement;
    }

    public void setiTagElement(ITagElement iTagElement) {
        this.iTagElement = iTagElement;
    }

    public void setTimeToExpiration(Period timeToExpiration) {
        this.timeToExpiration = timeToExpiration;
    }

    private boolean isValidName(String name) {
        if (name.isEmpty()) {
            return false;
        }
        return Pattern.compile("([a-zA-Z0-9])+(-([a-zA-Z0-9])+)*").matcher(name).matches();
    }

    public void setName(String name) {
        if (!isValidName(name)) {
            throw new IllegalArgumentException("Tag has not a valid format");
        }
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public Period getTimeToExpiration() {
        return timeToExpiration;
    }

    public ITagElement getITagElement() {
        return iTagElement;
    }

    public void setITagElement(ITagElement iTagElement) {
        this.iTagElement = iTagElement;
    }

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
