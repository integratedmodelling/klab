package org.integratedmodelling.klab.hub.agreements.services;

import org.integratedmodelling.klab.hub.api.Agreement;
import org.springframework.stereotype.Service;

@Service
public abstract interface AgreementService {
    
    abstract Agreement getAgreement(String id);

}
