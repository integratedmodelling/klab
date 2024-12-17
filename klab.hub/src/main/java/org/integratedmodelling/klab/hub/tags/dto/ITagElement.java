package org.integratedmodelling.klab.hub.tags.dto;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Objects that implementing this interface can use Tags 
 * 
 * @author kristina
 *
 */
@Document(collection = "ITagElement")
public interface ITagElement {

    String getId();

}
