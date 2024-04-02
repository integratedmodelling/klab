package org.integratedmodelling.klab.hub.agreements.commands;

import org.integratedmodelling.klab.hub.agreements.dto.AgreementTemplate;
import org.integratedmodelling.klab.hub.repository.AgreementTemplateRepository;

public class CreateAgreementTemplate {
        
        private AgreementTemplate agreementTemplate;
        private AgreementTemplateRepository repository;        
        
        
        public CreateAgreementTemplate(AgreementTemplate agreementTemplate, AgreementTemplateRepository repository) {
            super();
            this.agreementTemplate = agreementTemplate;
            this.repository = repository;
        }


        public AgreementTemplate execute() {
            return repository.insert(agreementTemplate);
        }

    }



