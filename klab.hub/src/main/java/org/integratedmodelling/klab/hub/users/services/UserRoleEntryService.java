package org.integratedmodelling.klab.hub.users.services;

import java.util.List;

import org.integratedmodelling.klab.auth.Role;
import org.springframework.stereotype.Service;

@Service
public interface UserRoleEntryService {
    public List<String> getUsersWithRole(Role role);
}
