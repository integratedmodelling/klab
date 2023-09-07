package org.integratedmodelling.klab.hub.users.services;

import java.util.Collection;

import org.integratedmodelling.klab.hub.users.requests.UserCustomPropertyRequest;
import org.integratedmodelling.klab.rest.CustomProperty;
import org.springframework.stereotype.Service;

@Service
public interface UserCustomPropertyService {
    public Collection<CustomProperty> getAllUserCustomProperties(String username);
    public void addUserCustomProperties(UserCustomPropertyRequest request);
    public void deleteUserCustomCustomProperties(UserCustomPropertyRequest request);
}
