package org.integratedmodelling.klab.hub.users.services;

import java.util.List;
import java.util.Optional;

import org.integratedmodelling.klab.hub.tags.dto.MongoTag;
import org.integratedmodelling.klab.hub.tags.dto.TagEntry;
import org.integratedmodelling.klab.hub.tags.dto.TagNotification;
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
     * Gets EntryTags of a User that have not been sent.
     * @param username of the User.
     * @return A list of EntryTags that have not been sent to the User.
     */
    public List<TagEntry> getUnsentTagsOfUser(String username);

    /**
     * Gets all the MongoTags.
     * @return A list of all the MongoTags.
     */
    public List<MongoTag> getAllTags();

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

    /**
     * Gets a tag by the tag name.
     * @param name of the tag.
     * @return The requested MongoTag as Optional.
     */
    public Optional<MongoTag> getTagByName(String name);

    /**
     * Inserts or updates a TagNotification.
     * @param tagNotification to insert or update.
     */
    public void insertOrUpdateTagNotification(TagNotification tagNotification);

    /**
     * Gets all the TagNotifications.
     * @return A list of all the TagNotifications
     */
    public List<TagNotification> getAllTagNotifications();

    /**
     * Gets notification associated to a tag.
     * @param tag MongoTag of which we request the notification.
     * @return The requested TagNotification as Optional.
     */
    public Optional<TagNotification> getTagNotificationsByTag(MongoTag tag);

    /**
     * Gets a list of the TagNotifications associated to a user.
     * @param username of the User.
     * @return A list with the TagNotifications associated to a user.
     */
    public List<TagNotification> getTagNotificationsByUser(String username);

}
