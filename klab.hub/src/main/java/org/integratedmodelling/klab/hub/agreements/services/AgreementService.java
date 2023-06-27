package org.integratedmodelling.klab.hub.agreements.services;

import java.util.Date;
import java.util.Set;

import org.integratedmodelling.klab.hub.api.Agreement;
import org.integratedmodelling.klab.hub.enums.AgreementLevel;
import org.integratedmodelling.klab.hub.enums.AgreementType;
import org.springframework.stereotype.Service;

@Service
public abstract interface AgreementService {
    
    abstract Agreement getAgreement(String id);

    Agreement createAgreement(AgreementType agreementType, AgreementLevel agreementLevel);

    Set<Agreement> updateAgreementValidDate(Set<Agreement> agreements, Date validDate);

    Agreement updateAgreement(Agreement agreement);
    

}
