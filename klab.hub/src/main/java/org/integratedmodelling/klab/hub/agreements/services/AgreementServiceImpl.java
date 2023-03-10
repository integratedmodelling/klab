package org.integratedmodelling.klab.hub.agreements.services;

import org.integratedmodelling.klab.hub.api.Agreement;
import org.integratedmodelling.klab.hub.repository.AgreementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AgreementServiceImpl implements AgreementService{
    
    @Autowired
    AgreementRepository agreementRepository;

    @Override
    public Agreement getAgreement(String id) {
        return agreementRepository.findById(id).get();
    }
 

    
}
