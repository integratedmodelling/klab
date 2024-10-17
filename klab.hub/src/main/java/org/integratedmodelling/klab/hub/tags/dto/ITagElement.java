package org.integratedmodelling.klab.hub.tags.dto;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Objects that implementing this interface can use Tags 
 * The tags can be for users, groups... This interface allow all this classes to be in the MongoTag class
 * 
 * @author kristina
 *
 */
@Document(collection = "ITagElement")
public interface ITagElement {

    String getId();

}
