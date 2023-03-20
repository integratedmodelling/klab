package org.integratedmodelling.klab.hub.agreements.services;

import java.util.Date;
import java.util.Set;

import org.integratedmodelling.klab.hub.api.Agreement;
import org.integratedmodelling.klab.hub.api.AgreementTemplate;
import org.integratedmodelling.klab.hub.api.GroupEntry;
import org.integratedmodelling.klab.hub.api.MongoGroup;
import org.integratedmodelling.klab.hub.commands.CreateAgreement;
import org.integratedmodelling.klab.hub.commands.UpdateAgreement;
import org.integratedmodelling.klab.hub.enums.AgreementLevel;
import org.integratedmodelling.klab.hub.enums.AgreementType;
import org.integratedmodelling.klab.hub.groups.services.GroupService;
import org.integratedmodelling.klab.hub.repository.AgreementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AgreementServiceImpl implements AgreementService{
    
    
    AgreementRepository agreementRepository;
    AgreementTemplateService agreementTemplateService;
    GroupService groupService;
    
    @Autowired
    public AgreementServiceImpl(AgreementRepository agreementRepository, AgreementTemplateService agreementTemplateService, GroupService groupService) {
        super();
        this.agreementRepository = agreementRepository;
        this.agreementTemplateService = agreementTemplateService;
        this.groupService = groupService;
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
        
        agreement.addGroupEntries(getAgreementDefault(agreementTemplate));
        agreement.setTransactionDate(new Date());        
        return new CreateAgreement(agreement, agreementRepository).execute();
    }
    
    private Set<GroupEntry> getAgreementDefault(AgreementTemplate agreementTemplate) {
        Set<GroupEntry> groups = agreementTemplate.getDefaultGroups();
        groupService.getGroupsDefault().forEach((group) -> extracted(groups, group));

        return groups;
    }

    private void extracted(Set<GroupEntry> groups, MongoGroup group) {
        if (!groups.contains(group)) groups.add(new GroupEntry(group));
    }

    @Override
    public Set<Agreement> updateAgreementValidDate(Set<Agreement> agreements, Date validDate) {
        agreements.stream().forEach(agreement -> {
            agreement.setValidDate(validDate);
            new UpdateAgreement(agreement, agreementRepository).execute();
        });
        
        return agreements;
    }
}
