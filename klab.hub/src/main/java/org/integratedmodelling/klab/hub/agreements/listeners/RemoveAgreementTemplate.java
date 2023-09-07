package org.integratedmodelling.klab.hub.agreements.listeners;

import org.springframework.context.ApplicationEvent;

public class RemoveAgreementTemplate extends ApplicationEvent{
    
    /**
     * 
     */
    private static final long serialVersionUID = 2137361508052117106L;
    
    private String agreementTemplateId;

    public RemoveAgreementTemplate(Object source, String agreementTemplateId) {
        super(source);
        this.agreementTemplateId = agreementTemplateId;
    }

    public String getAgreementTemplateId() {
        return agreementTemplateId;
    }

}
