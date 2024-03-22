package org.integratedmodelling.klab.hub.tags.services;

import java.util.List;

import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.hub.repository.MongoTagRepository;
import org.integratedmodelling.klab.hub.repository.TagNotificationRepository;
import org.integratedmodelling.klab.hub.tags.dto.MongoTag;
import org.integratedmodelling.klab.hub.tags.dto.TagNotification;
import org.integratedmodelling.klab.hub.users.dto.User;
import org.integratedmodelling.klab.rest.HubNotificationMessage.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import scala.PartialFunction.OrElse;

@Service
public class TagNotificationService {

    private static final Logger logger = LoggerFactory.getLogger(TagNotificationService.class);

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    private TagNotificationRepository tagNotificationRepository;

    @Autowired
    private MongoTagRepository mongoTagRepository;

    public TagNotification createWarningUserTagNotification(User user, String tagName, Boolean visible, String title,
            String message) {
        MongoTag tag = new MongoTag();
        tag.setITagElement(user);
        tag.setName(tagName);
        tag.setVisible(visible);

        tag = mongoTagRepository.save(tag);
        TagNotification tagNotification = new TagNotification();

        tagNotification.setTag(tag);
        tagNotification.setType(Type.WARNING);

        try {
            tagNotificationRepository.save(tagNotification);
        } catch (Exception e) {
            throw new KlabException("Error saving tag notification.", e);
        }

        return tagNotification;
    }

    /**
     * Find 
     * @param username
     * @return
     */
    public List<MongoTag> findTagByUsername(String username) {
        return mongoTagRepository.findAllByUsernameOrUsernameIsNull(username);
    }

    /**
     * Get tagNotification list by username, also included null username tag notifications 
     * @param username
     * @return
     */
    public List<TagNotification> getUserTagNotifications(String username) {
        List<TagNotification> listTagNotifications = null;
        List<MongoTag> listMongoTags = null;
        try {
            listMongoTags = mongoTagRepository.findAllByUsernameOrUsernameIsNull(username);
            listTagNotifications = tagNotificationRepository.findAllByTagIn(listMongoTags);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new KlabException(e.getMessage(), e);
        }
        return listTagNotifications;
    }

    /**
     * Delete tagNotification and mongoTag by ragNotification id.
     * 
     * @param id
     * @return
     */
    public TagNotification deleteTagNotification(String id) {
        TagNotification tagNotification = tagNotificationRepository.findById(id)
                .orElseThrow(() -> new KlabException("TagNotification doesn't exist"));
        try {
            mongoTagRepository.delete(tagNotification.getTag());
            tagNotificationRepository.delete(tagNotification);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new KlabException("Error deleting data in mongo.", e);
        }
        return tagNotification;
    }

}
