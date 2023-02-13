package org.integratedmodelling.klab.hub.users.services;

import java.util.List;

import org.integratedmodelling.klab.hub.api.User.AccountStatus;

public interface UserAccountStatusService {

    abstract List<String> getUsersByStatus(AccountStatus accountStatus);
}
