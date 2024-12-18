package org.integratedmodelling.klab.hub.tags.services;

import java.util.ArrayList;
import java.util.List;

import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.hub.repository.MongoTagRepository;
import org.integratedmodelling.klab.hub.repository.TagNotificationRepository;
import org.integratedmodelling.klab.hub.tags.dto.ITagElement;
import org.integratedmodelling.klab.hub.tags.dto.MongoTag;
import org.integratedmodelling.klab.hub.tags.dto.TagNotification;
import org.integratedmodelling.klab.hub.tags.enums.ITagElementEnum;
import org.integratedmodelling.klab.hub.tags.enums.TagNameEnum;
import org.integratedmodelling.klab.hub.users.dto.User;
import org.integratedmodelling.klab.hub.users.services.UserService;
import org.integratedmodelling.klab.rest.HubNotificationMessage;
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

    @Autowired
    private UserService userService;

    /**
     * Create TagNotification
     *  
     * @param iTagElementEnum {@link ITagElementEnum} Element for which the notification is. 
     * @param id Id of element, empty if iTagElementEnum is ALL
     * @param tagNotifactionType {@link HubNotificationMessage.Type} Type of notification
     * @param visible Visible or not
     * @param tagName  To use with frontend predefined titles and messages use {@link TagNameEnum} otherwise free string
     * @param title Title of notification if tagName is not a {@link TagNameEnum}
     * @param message Message of notification if tagName is not a {@link TagNameEnum}
     * @return
     */
    public TagNotification createTagNotification(ITagElementEnum iTagElementEnum, String id,
            HubNotificationMessage.Type tagNotifactionType, Boolean visible, String tagName, String title, String message) {

        /* Create Mongo Tag */
        MongoTag tag = new MongoTag();

        tag.setName(tagName);
        tag.setVisible(visible);

        setITagElementById(tag, iTagElementEnum, id);

        tag = mongoTagRepository.save(tag);

        /* Create TagNotification */
        TagNotification tagNotification = new TagNotification();

        tagNotification.setTag(tag);
        tagNotification.setType(tagNotifactionType);

        tagNotification.setTitle(title);
        tagNotification.setMessage(message);

        try {
            tagNotificationRepository.save(tagNotification);
        } catch (Exception e) {
            throw new KlabException("Error saving tag notification.", e);
        }

        return tagNotification;

    }

    private void setITagElementById(MongoTag tag, ITagElementEnum iTagElementEnum, String id) {
        if (id.isEmpty() || iTagElementEnum == null) {
            return;
        }

        tag.setTagElementId(!id.isEmpty() ? id : null);
        switch(iTagElementEnum) {
        case USER:
            tag.setITagElement(userService.getUserById(id));
            break;
        case GROUP:
            break;
        }

    }

    public TagNotification createWarningUserTagNotification(User user, String tagName, Boolean visible, String title,
            String message) {
        MongoTag tag = new MongoTag();
        tag.setTagElementId(user.getId().isEmpty() ? null : user.getId());
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
            listMongoTags.addAll(mongoTagRepository.findAllByTagElementIdIsNull());
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
