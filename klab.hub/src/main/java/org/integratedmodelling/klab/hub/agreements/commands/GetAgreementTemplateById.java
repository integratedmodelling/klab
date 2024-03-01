package org.integratedmodelling.klab.hub.agreements.commands;

import org.integratedmodelling.klab.hub.api.AgreementTemplate;
import org.integratedmodelling.klab.hub.repository.AgreementTemplateRepository;

public class GetAgreementTemplateById {
    
    AgreementTemplateRepository repository;
    String id;
    public GetAgreementTemplateById(AgreementTemplateRepository repository, String id) {
        super();
        this.repository = repository;
        this.id = id;
    }
    
    public AgreementTemplate execute() {
        return repository.findById(id)
                .orElseThrow(() -> new NullPointerException(id + " was not found in the AgreementTemplate Collection"));
    }

}
