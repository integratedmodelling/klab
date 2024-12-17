package org.integratedmodelling.klab.hub.tags.services;

import java.util.ArrayList;
import java.util.List;

import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.hub.repository.MongoTagRepository;
import org.integratedmodelling.klab.hub.repository.TagNotificationRepository;
import org.integratedmodelling.klab.hub.tags.dto.ITagElement;
import org.integratedmodelling.klab.hub.tags.dto.MongoTag;
import org.integratedmodelling.klab.hub.tags.dto.TagNotification;
import org.integratedmodelling.klab.hub.tags.enums.TagNameEnum;
import org.integratedmodelling.klab.hub.users.dto.User;
import org.integratedmodelling.klab.rest.HubNotificationMessage.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

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
        tag.setTagElementId(user.getId());
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
     * Get tagNotification list by username, also included null username tag notifications 
     * @param user
     * @return
     */
    public List<TagNotification> getUserTagNotifications(ITagElement iTagElement) {
        List<TagNotification> listTagNotifications = null;
        List<MongoTag> listMongoTags = null;
        try {
            listMongoTags = mongoTagRepository.findAllByTagElementId(iTagElement.getId());
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

    /**
     * Delete tagNotifications by user and type
     * @param user
     * @param tagNameEnum
     */
    public void deleteTagNotification(User user, TagNameEnum tagNameEnum) {
        List<MongoTag> tagList = mongoTagRepository.findAllByTagElementId(user.getId());
        List<TagNotification> tagNotificacionList = tagNotificationRepository.findAllByTagIn(tagList);
        List<TagNotification> filteredList = new ArrayList<>();
        if (!tagNotificacionList.isEmpty()) {
            filteredList = tagNotificacionList.stream().filter(tg -> tg.getTag().getName().equals(tagNameEnum.toString()))
                    .toList();
        }

        if (!filteredList.isEmpty()) {
            deleteTagNotifications(filteredList);
        }

    }

    /**
     * Delete list of tagNotifications
     * @param tagNotificationList
     */
    private void deleteTagNotifications(List<TagNotification> tagNotificationList) {
        mongoTagRepository.deleteAll(tagNotificationList.stream().map(fl -> fl.getTag()).toList());
        tagNotificationRepository.deleteAll(tagNotificationList);

    }

}
