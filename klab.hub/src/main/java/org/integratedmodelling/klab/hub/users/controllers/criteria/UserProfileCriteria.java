package org.integratedmodelling.klab.hub.users.controllers.criteria;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.integratedmodelling.klab.auth.Role;
import org.integratedmodelling.klab.hub.api.User.AccountStatus;
import org.integratedmodelling.klab.rest.Group;
import org.springframework.data.domain.Pageable;

public class UserProfileCriteria {
    public Optional<Pageable> pagination = Optional.empty();
    public List<Group> groupsCriteria = new ArrayList<Group>();
    public List<Role> rolesCriteria = new ArrayList<Role>();
    public List<AccountStatus> accountStatusCriteria = new ArrayList<AccountStatus>();
}
