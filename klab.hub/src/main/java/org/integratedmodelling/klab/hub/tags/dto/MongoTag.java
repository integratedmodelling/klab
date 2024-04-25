package org.integratedmodelling.klab.hub.tags.dto;

import java.time.Period;
import java.util.regex.Pattern;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
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

    private Period timeToExpiration;

    private String tagElementId;

    private ITagElement iTagElement;

    private Boolean visible;

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

}
