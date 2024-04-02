package org.integratedmodelling.klab.hub.tags.dto;

import java.time.LocalDateTime;
import java.time.Period;
import java.util.regex.Pattern;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
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

    @CreatedBy
    private String createdBy;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedBy
    private String lastModifiedBy;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

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
