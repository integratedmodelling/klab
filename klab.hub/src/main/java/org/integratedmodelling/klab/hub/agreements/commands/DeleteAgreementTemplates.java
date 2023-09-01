package org.integratedmodelling.klab.hub.agreements.commands;

import java.util.List;

import org.integratedmodelling.klab.hub.api.AgreementTemplate;
import org.integratedmodelling.klab.hub.repository.AgreementTemplateRepository;

public class DeleteAgreementTemplates {
        
        private List<AgreementTemplate> agreementTemplates;
        private AgreementTemplateRepository repository;        
        
        
        public DeleteAgreementTemplates(List<AgreementTemplate> agreementTemplates, AgreementTemplateRepository repository) {
            super();
            this.agreementTemplates = agreementTemplates;
            this.repository = repository;
        }


        public List<AgreementTemplate> execute() {
            repository.deleteAll(agreementTemplates);
            return agreementTemplates;
        }

    }



