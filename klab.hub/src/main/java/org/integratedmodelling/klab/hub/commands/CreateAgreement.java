package org.integratedmodelling.klab.hub.commands;

import java.util.List;
import java.util.Set;

import org.integratedmodelling.klab.hub.api.Agreement;
import org.integratedmodelling.klab.hub.repository.AgreementRepository;

public class CreateAgreement implements AgreementCommand {

    private Set<Agreement> agreements;
    private AgreementRepository agreementRepository;
    
    public CreateAgreement(Set<Agreement> agreements,
            AgreementRepository agreementRepository) {
            this.agreements = agreements;
            this.agreementRepository = agreementRepository;
    }

    @Override
    public List<Agreement> execute() {
        return agreementRepository.saveAll(agreements);        
    }
}
