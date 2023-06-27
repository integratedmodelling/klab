package org.integratedmodelling.klab.hub.commands;

import org.integratedmodelling.klab.hub.api.Agreement;
import org.integratedmodelling.klab.hub.repository.AgreementRepository;

public class UpdateAgreement implements AgreementCommand {

    private Agreement agreement;
    private AgreementRepository agreementRepository;
    
    public UpdateAgreement(Agreement agreement,
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
