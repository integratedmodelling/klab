package org.integratedmodelling.klab.hub.agreements.services;

import java.time.Instant;
import java.util.Date;
import java.util.Set;

import org.integratedmodelling.klab.hub.api.Agreement;
import org.integratedmodelling.klab.hub.api.AgreementTemplate;
import org.integratedmodelling.klab.hub.commands.CreateAgreement;
import org.integratedmodelling.klab.hub.commands.UpdateAgreement;
import org.integratedmodelling.klab.hub.enums.AgreementLevel;
import org.integratedmodelling.klab.hub.enums.AgreementType;
import org.integratedmodelling.klab.hub.repository.AgreementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AgreementServiceImpl implements AgreementService{
    
    
    AgreementRepository agreementRepository;
    AgreementTemplateService agreementTemplateService;
    
    @Autowired
    public AgreementServiceImpl(AgreementRepository agreementRepository, AgreementTemplateService agreementTemplateService) {
        super();
        this.agreementRepository = agreementRepository;
        this.agreementTemplateService = agreementTemplateService;
    }

    @Override
    public Agreement getAgreement(String id) {
        return agreementRepository.findById(id).get();
    }
    
    @Override
    public Agreement createAgreement(AgreementType agreementType, AgreementLevel agreementLevel) {
        AgreementTemplate agreementTemplate =  agreementTemplateService.getAgreementTemplate(agreementType, agreementLevel);
        return createAgreementByAgreementTemplate(agreementTemplate);
    }
    
    
    
    private Agreement createAgreementByAgreementTemplate(AgreementTemplate agreementTemplate) {        
        Agreement agreement = new Agreement();
        agreement.setAgreementLevel(agreementTemplate.getAgreementLevel());
        agreement.setAgreementType(agreementTemplate.getAgreementType());
        agreement.addGroupEntries(agreementTemplate.getDefaultGroups());
        agreement.setTransactionDate(new Date());        
        return new CreateAgreement(agreement, agreementRepository).execute();
    }
    
    @Override
    public Set<Agreement> updateAgreementValidDate(Set<Agreement> agreements, Date validDate) {
        agreements.stream().forEach(agreement -> {
            agreement.setValidDate(validDate);
            new UpdateAgreement(agreement, agreementRepository);
        });
        
        return agreements;
    }
}
