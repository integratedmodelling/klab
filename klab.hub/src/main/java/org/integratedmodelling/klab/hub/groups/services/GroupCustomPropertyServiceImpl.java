package org.integratedmodelling.klab.hub.groups.services;

import java.util.List;

import org.integratedmodelling.klab.hub.groups.dto.MongoGroup;
import org.integratedmodelling.klab.hub.repository.MongoGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupCustomPropertyServiceImpl implements GroupCustomPropertyService {
    
    @Autowired
    private MongoGroupRepository repository;
    

    @Override
    public List<MongoGroup> getGroupsWithCustomProperty(String key, String value) {
        return repository.findByCustomProperties_KeyAndCustomProperties_Value(key, value);
    }

    @Override
    public List<MongoGroup> getGroupsWithCustomProperty(String key) {
        // TODO Auto-generated method stub
        return null;
    }

}
