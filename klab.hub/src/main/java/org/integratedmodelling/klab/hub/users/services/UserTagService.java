package org.integratedmodelling.klab.hub.users.services;

import java.util.List;

import org.integratedmodelling.klab.hub.api.MongoTag;
import org.integratedmodelling.klab.hub.api.TagEntry;
import org.integratedmodelling.klab.rest.HubNotificationMessage;
import org.springframework.stereotype.Service;

@Service
public interface UserTagService {

    /**
     * Assigns a MongoTag to a user as a new TagEntry.
     * Searches for the MongoTag at the database and the user before assigning it to avoid duplications.
     * Tags that already exist are not assigned twice. 
     * @param username
     * @param tag
     * @return true if the tag has been assigned to the user
     */
    public boolean assignTagToUser(String username, MongoTag tag);

    public List<TagEntry> getTagsOfUser(String username);

    public List<TagEntry> getTagsOfUserWithType(String username, HubNotificationMessage.Type type);

    public List<TagEntry> getUnsentTagsOfUser(String username);

    public List<TagEntry> getUnsentTagsOfUserWithType(String username, HubNotificationMessage.Type type);

    public List<MongoTag> getAllTags();

    public List<MongoTag> getAllTagsWithType(HubNotificationMessage.Type type);

    public void insertTag(MongoTag tag);

    public void insertOrUpdateTag(MongoTag tag);

}
