package org.integratedmodelling.klab.hub.users.services;

import java.util.List;

import org.integratedmodelling.klab.hub.api.Tag;
import org.integratedmodelling.klab.rest.HubNotificationMessage;
import org.springframework.stereotype.Service;

@Service
public interface UserTagService {

    public void createNewTag(String username, Tag tag);

    public List<Tag> getTagsOfUser(String username);

    public List<Tag> getTagsOfUserWithType(String username, HubNotificationMessage.Type type);

    public List<Tag> getUnsentTagsOfUser(String username);

    public List<Tag> getUnsentTagsOfUserWithType(String username, HubNotificationMessage.Type type);

}
