package org.integratedmodelling.klab.hub.api;

import org.springframework.data.mongodb.core.mapping.DBRef;

public class AgreementEntry {
    
    @DBRef
    private Agreement agreement;    

    public AgreementEntry(Agreement agreement) {
        super();
        this.agreement = agreement;
    }

    public Agreement getAgreement() {
        return agreement;
    }

    public void setAgreement(Agreement agreement) {
        this.agreement = agreement;
    }   

}
