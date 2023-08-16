package org.integratedmodelling.klab.hub.commands;

import org.integratedmodelling.klab.hub.api.Agreement;
import org.integratedmodelling.klab.hub.repository.AgreementRepository;

public class CreateAgreement implements AgreementCommand {

    private Agreement agreement;
    private AgreementRepository agreementRepository;
    
    public CreateAgreement(Agreement agreement,
            AgreementRepository agreementRepository) {
            this.agreement = agreement;
            this.agreementRepository = agreementRepository;
    }

    @Override
    public Agreement execute() {
        agreementRepository.save(agreement);      
        return agreement;
    }
}
