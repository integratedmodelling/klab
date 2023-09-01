package org.integratedmodelling.klab.hub.agreements.commands;

import org.integratedmodelling.klab.hub.api.AgreementTemplate;
import org.integratedmodelling.klab.hub.repository.AgreementTemplateRepository;

public class DeleteAgreementTemplate {
        
        private AgreementTemplate agreementTemplate;
        private AgreementTemplateRepository repository;        
        
        
        public DeleteAgreementTemplate(AgreementTemplate agreementTemplate, AgreementTemplateRepository repository) {
            super();
            this.agreementTemplate = agreementTemplate;
            this.repository = repository;
        }


        public AgreementTemplate execute() {
            repository.delete(agreementTemplate);
            return agreementTemplate;
        }

    }



