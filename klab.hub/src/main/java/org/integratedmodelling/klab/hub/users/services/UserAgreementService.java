package org.integratedmodelling.klab.hub.users.services;

import java.util.Collection;

import org.integratedmodelling.klab.hub.api.Agreement;
import org.springframework.stereotype.Service;

@Service
public interface UserAgreementService {

    abstract Collection<Agreement> getAgreementsFromUser(String username);

    abstract void revokeAgreementFromUser(String username, String agreementId);

}
