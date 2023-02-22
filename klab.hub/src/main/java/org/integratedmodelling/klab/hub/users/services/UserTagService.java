package org.integratedmodelling.klab.hub.users.services;

import java.util.List;

import org.integratedmodelling.klab.hub.api.MongoTag;
import org.integratedmodelling.klab.hub.api.TagEntry;
import org.integratedmodelling.klab.rest.HubNotificationMessage;
import org.springframework.stereotype.Service;

/**
 * Service to make CRUD operations of MongoTags and EntryTags.
 */
@Service
public interface UserTagService {

    /**
     * Assigns a MongoTag to a user as a new TagEntry.
     * Searches for the MongoTag at the database and the user before assigning it to avoid duplications.
     * Tags that already exist are not assigned twice to the same user. 
     * @param username of the user to be tagged
     * @param tag to add.
     * @return true if the tag has been assigned to the user.
     */
    public boolean assignTagToUser(String username, MongoTag tag);

    /**
     * Gets all the EntryTags of a User.
     * @param username of the User.
     * @return List of EntryTags.
     */
    public List<TagEntry> getTagsOfUser(String username);

    /**
     * Gets EntryTags of a User that have the type passed as a parameter.
     * @param username of the User.
     * @param type of the tags.
     * @return A list of EntryTags that have the type passed as a parameter.
     */
    public List<TagEntry> getTagsOfUserWithType(String username, HubNotificationMessage.Type type);

    /**
     * Gets EntryTags of a User that have not been sent.
     * @param username of the User.
     * @return A list of EntryTags that have not been sent to the User.
     */
    public List<TagEntry> getUnsentTagsOfUser(String username);

    /**
     * Gets EntryTags of a User that have not been sent and are of the type passed as a parameter.
     * @param username of the User.
     * @param type of the tags.
     * @return A list of EntryTags that have not been sent to the User and have the type passed as a parameter.
     */
    public List<TagEntry> getUnsentTagsOfUserWithType(String username, HubNotificationMessage.Type type);

    /**
     * Gets all the MongoTags.
     * @return A list of all the MongoTags.
     */
    public List<MongoTag> getAllTags();

    /**
     * Gets all the MongoTags with the requested type.
     * @param type of the tags.
     * @return A list of all the MongoTags with the requested type.
     */
    public List<MongoTag> getAllTagsWithType(HubNotificationMessage.Type type);

    /**
     * Inserts a new MongoTag.
     * @param tag to insert.
     * @throws MongoWriteExceptionHandler if the tag already exists.
     */
    public void insertTag(MongoTag tag);

    /**
     * Inserts or updates a MongoTag.
     * @param tag to insert or update.
     */
    public void insertOrUpdateTag(MongoTag tag);

}
