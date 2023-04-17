package org.integratedmodelling.klab.hub.users.controllers.criteria;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.integratedmodelling.klab.auth.Role;
import org.integratedmodelling.klab.hub.api.User.AccountStatus;
import org.springframework.data.domain.Pageable;

public class UserProfileCriteria {
    public Optional<Pageable> pagination = Optional.empty();
    public List<String> groupsCriteria = new ArrayList<String>();
    public List<Role> rolesCriteria = new ArrayList<Role>();
    public List<AccountStatus> accountStatusCriteria = new ArrayList<AccountStatus>();
}
