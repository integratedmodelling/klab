package org.integratedmodelling.klab.hub.users.services;

import java.util.Collection;

import org.integratedmodelling.klab.hub.api.Agreement;
import org.integratedmodelling.klab.hub.api.AgreementEntry;
import org.springframework.stereotype.Service;

@Service
public interface UserAgreementService {

    abstract Collection<AgreementEntry> getAgreementsFromUser(String username);

    abstract void revokeAgreement(String username, String agreementId);

    abstract void updateAgreement(String username, Agreement agreement);
}
