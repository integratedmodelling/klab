package org.integratedmodelling.klab.hub.users.services;

import java.util.List;

import org.integratedmodelling.klab.hub.api.Tag;
import org.springframework.stereotype.Service;

@Service
public interface UserTagService {

    public void createNewTag(String username, String description);

    public List<Tag> getTagsOfUser(String username);

}
