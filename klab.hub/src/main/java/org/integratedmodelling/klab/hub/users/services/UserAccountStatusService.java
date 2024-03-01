package org.integratedmodelling.klab.hub.users.services;

import java.util.List;

import org.integratedmodelling.klab.hub.users.dto.User.AccountStatus;
import org.springframework.stereotype.Service;

@Service
public interface UserAccountStatusService {

    abstract List<String> getUsersByStatus(AccountStatus accountStatus);
}
