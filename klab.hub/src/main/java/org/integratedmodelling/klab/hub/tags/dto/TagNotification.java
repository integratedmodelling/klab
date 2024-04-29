package org.integratedmodelling.klab.hub.tags.dto;

import java.time.LocalDateTime;

import org.integratedmodelling.klab.rest.HubNotificationMessage.Type;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a HubNotificationMessage associated to a MongoTag.
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "NotificationTags")
@TypeAlias("NotificationTag")
public class TagNotification {

    @Id
    @Indexed(unique = true)
    private String id;

    @DBRef
    private MongoTag tag;

    @Enumerated(EnumType.STRING)
    private Type type;

    private String title;

    @CreatedBy
    private String createdBy;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedBy
    private String lastModifiedBy;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

}
