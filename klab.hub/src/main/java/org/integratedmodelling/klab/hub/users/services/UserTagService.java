package org.integratedmodelling.klab.hub.users.services;

import java.util.List;

import org.integratedmodelling.klab.hub.api.MongoTag;
import org.integratedmodelling.klab.hub.api.TagEntry;
import org.integratedmodelling.klab.rest.HubNotificationMessage;
import org.springframework.stereotype.Service;

@Service
public interface UserTagService {

    public void assignTagToUser(String username, MongoTag tag);

    public List<TagEntry> getTagsOfUser(String username);

    public List<TagEntry> getTagsOfUserWithType(String username, HubNotificationMessage.Type type);

    public List<TagEntry> getUnsentTagsOfUser(String username);

    public List<TagEntry> getUnsentTagsOfUserWithType(String username, HubNotificationMessage.Type type);

}
