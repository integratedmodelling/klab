package org.integratedmodelling.klab.hub.groups.services;

import java.util.List;

import org.integratedmodelling.klab.hub.groups.dto.MongoGroup;
import org.springframework.stereotype.Service;

@Service
public interface GroupCustomPropertyService {

    List<MongoGroup> getGroupsWithCustomProperty(String key, String value);
    List<MongoGroup> getGroupsWithCustomProperty(String key);
}
