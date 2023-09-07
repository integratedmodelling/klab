package org.integratedmodelling.klab.hub.agreements.commands;

import org.integratedmodelling.klab.hub.repository.AgreementTemplateRepository;

public class ExistsAgreementTemplate {
        
        private String id;
        private AgreementTemplateRepository repository;        
        
        
        public ExistsAgreementTemplate(String id, AgreementTemplateRepository repository) {
            super();
            this.id = id;
            this.repository = repository;
        }


        public boolean execute() {
            return repository.findById(this.id).isPresent();
        }

    }



