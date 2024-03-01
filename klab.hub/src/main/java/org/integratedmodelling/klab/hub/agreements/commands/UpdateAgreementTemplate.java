package org.integratedmodelling.klab.hub.agreements.commands;

import org.integratedmodelling.klab.hub.api.AgreementTemplate;
import org.integratedmodelling.klab.hub.repository.AgreementTemplateRepository;

public class UpdateAgreementTemplate {
        
        private AgreementTemplate agreementTemplate;
        private AgreementTemplateRepository repository;        
        
        
        public UpdateAgreementTemplate(AgreementTemplate agreementTemplate, AgreementTemplateRepository repository) {
            super();
            this.agreementTemplate = agreementTemplate;
            this.repository = repository;
        }


        public AgreementTemplate execute() {
            return repository.save(agreementTemplate);
        }

    }



