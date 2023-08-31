package org.integratedmodelling.klab.hub.agreements.services;

import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
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

import com.google.common.collect.Sets;

@Service
public class AgreementServiceImpl implements AgreementService {

    AgreementRepository agreementRepository;
    AgreementTemplateService agreementTemplateService;
    GroupService groupService;

    @Autowired
    public AgreementServiceImpl(AgreementRepository agreementRepository, AgreementTemplateService agreementTemplateService,
            GroupService groupService) {
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
    public List<Agreement> createAgreement(AgreementType agreementType, AgreementLevel agreementLevel) {
        AgreementTemplate agreementTemplate = agreementTemplateService.getAgreementTemplate(agreementType, agreementLevel);
        return createAgreementByAgreementTemplate(agreementTemplate);
        
    }

    /**
     * Create agreement from Agreement Template
     * 
     * @param agreementTemplate
     * @return
     */
    private List<Agreement> createAgreementByAgreementTemplate(AgreementTemplate agreementTemplate) {
        Date now = new Date();
        Agreement agreement = new Agreement();
        agreement.setAgreementLevel(agreementTemplate.getAgreementLevel());
        agreement.setAgreementType(agreementTemplate.getAgreementType());

        agreement.addGroupEntries(getAgreementDefault(agreementTemplate));
        agreement.setValidDate(null);
        agreement.setTransactionDate(now);
        agreement.setExpirationDate(agreementTemplate.getDefaultDuration() == 0
                ? null
                : new Date(System.currentTimeMillis() + agreementTemplate.getDefaultDuration()));

        return new CreateAgreement(Sets.newHashSet(agreement), agreementRepository).execute();
    }

    /**
     * Get default GroupEntry
     * @param agreementTemplate
     * @return
     */
    private Set<GroupEntry> getAgreementDefault(AgreementTemplate agreementTemplate) {
        /* Get agreement template's default groups */
        Set<GroupEntry> groups = agreementTemplate.getDefaultGroups();

        /* Merge with optIn groups */
        groupService.getGroupsDefault().forEach((group) -> extracted(groups, group, agreementTemplate));

        /* Set expiration date of groups */
        groups.forEach((group) -> setMinDateByGroupsDepengingOnGroupsAndAgreementTemplate(group, agreementTemplate));

        return groups;
    }

    /**
     * Create GroupEntry with different groups of agreement template and optIn with their expiration date
     * @param groups
     * @param group
     * @param agreementTemplate
     */
    private void extracted(Set<GroupEntry> groups, MongoGroup group, AgreementTemplate agreementTemplate) {
        if (!groups.contains(group)) {
            groups.add(new GroupEntry(group));
        }
    }

    /**
     * Set to groupEntry minimum date between group, dependents groups and agreement template expiration date
     * @param group
     * @param agreementTemplate
     * @return
     */
    private void setMinDateByGroupsDepengingOnGroupsAndAgreementTemplate(GroupEntry groupEntry,
            AgreementTemplate agreementTemplate) {
        List<Date> dates = new ArrayList<>();

        /* Get dependents groups */
        if (groupEntry.getGroup().getDependsOn() != null) {
            List<MongoGroup> dependingGroups = groupEntry.getGroup().getDependsOn().stream().map(g -> groupService.getByName(g))
                    .toList();

            /* Get default expiration of dependents groups */
            if (dependingGroups != null)
                dates.addAll(dependingGroups.stream().filter(mongoGroup -> mongoGroup.getDefaultExpirationTime() != 0)
                        .map((mongoGroup) -> new Date(System.currentTimeMillis() + mongoGroup.getDefaultExpirationTime()))
                        .toList());
        }

        /* Get default expiration of group */
        if (groupEntry.getGroup().getDefaultExpirationTime() != 0)
            dates.add(new Date(System.currentTimeMillis() + groupEntry.getGroup().getDefaultExpirationTime()));

        /* Get default expiration of agreementTemplate */
        if (agreementTemplate.getDefaultDuration() != 0)
            dates.add(new Date(System.currentTimeMillis() + agreementTemplate.getDefaultDuration()));

        /* If dates isn't empty get minimun date of them */
        groupEntry.setExpiration(dates.isEmpty()
                ? null
                : Instant.ofEpochMilli(Collections.min(dates).getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime());
    }

    @Override
    public Set<Agreement> updateAgreementValidDate(Set<Agreement> agreements, Date validDate) {
        agreements.stream().forEach(agreement -> {
            agreement.setValidDate(validDate);
        });
        new UpdateAgreement(agreements, agreementRepository).execute();
        return agreements;
    }

    @Override
    public List<Agreement> updateAgreement(Agreement agreement) {
        return new UpdateAgreement(Sets.newHashSet(agreement), agreementRepository).execute();
    }
}
