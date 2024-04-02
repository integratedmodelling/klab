package org.integratedmodelling.klab.hub.agreements.services;

import java.util.Collection;

import org.integratedmodelling.klab.hub.agreements.dto.Agreement;
import org.integratedmodelling.klab.hub.agreements.dto.AgreementEntry;
import org.springframework.stereotype.Service;

@Service
public interface UserAgreementService {

    abstract Collection<AgreementEntry> getAgreementsFromUser(String username);

    abstract void revokeAgreement(String username, String agreementId);

    abstract void updateAgreement(String username, Agreement agreement);
}
