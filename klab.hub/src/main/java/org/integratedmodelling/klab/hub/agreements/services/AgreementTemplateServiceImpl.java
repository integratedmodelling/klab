package org.integratedmodelling.klab.hub.agreements.services;

import java.util.List;
import java.util.Optional;

import org.integratedmodelling.klab.hub.agreements.commands.CreateAgreementTemplate;
import org.integratedmodelling.klab.hub.agreements.commands.DeleteAgreementTemplate;
import org.integratedmodelling.klab.hub.agreements.commands.DeleteAgreementTemplates;
import org.integratedmodelling.klab.hub.agreements.commands.ExistsAgreementTemplate;
import org.integratedmodelling.klab.hub.agreements.commands.GetAgreementTemplateById;
import org.integratedmodelling.klab.hub.agreements.commands.UpdateAgreementTemplate;
import org.integratedmodelling.klab.hub.agreements.exceptions.AgreementTemplateDoesNotExistException;
import org.integratedmodelling.klab.hub.agreements.exceptions.AgreementTemplateExistException;
import org.integratedmodelling.klab.hub.agreements.listeners.RemoveAgreementTemplate;
import org.integratedmodelling.klab.hub.agreements.payload.RequestAgreementTemplate;
import org.integratedmodelling.klab.hub.api.AgreementTemplate;
import org.integratedmodelling.klab.hub.enums.AgreementLevel;
import org.integratedmodelling.klab.hub.enums.AgreementType;
import org.integratedmodelling.klab.hub.listeners.HubEventPublisher;
import org.integratedmodelling.klab.hub.repository.AgreementTemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AgreementTemplateServiceImpl implements AgreementTemplateService {

    private AgreementTemplateRepository agreementTemplateRepository;
    private HubEventPublisher<RemoveAgreementTemplate> publisher;    
    
    @Autowired
    public AgreementTemplateServiceImpl(AgreementTemplateRepository agreementTemplateRepository,
            HubEventPublisher<RemoveAgreementTemplate> publisher) {
        super();
        this.agreementTemplateRepository = agreementTemplateRepository;
        this.publisher = publisher;
    }

    @Override
    public List<AgreementTemplate> getAgreementTemplates(RequestAgreementTemplate requestAgreementTemplate) {
        return agreementTemplateRepository.findAll();
    }

    @Override
    public AgreementTemplate getAgreementTemplate(AgreementType agreementType, AgreementLevel agreementLevel) {
        return agreementTemplateRepository
                .findByAgreementTypeAndAgreementLevelAndDefaultTemplate(agreementType, agreementLevel, true).get();
    }

    @Override
    public AgreementTemplate getAgreementTemplate(RequestAgreementTemplate requestAgreementTemplate) throws Exception {
        return getById(requestAgreementTemplate.getId());
    }

    @Override
    public AgreementTemplate create(AgreementTemplate agreementTemplate) {
        if (agreementTemplateRepository.findById(agreementTemplate.getId()).isPresent()) {
            throw new AgreementTemplateExistException(
                    "AgreementTemplate by the name: " + agreementTemplate.getId() + " already exists");
        }
        /*
         * Only can exist one agreement default template to true by agreement type and level.
         * If default template is true : Check and update to false if is necessary the others agreement templates with the same agreement type and level.
         * If default template is false: Check if is the only one agreement template with this agreement type and level, in that case set default template to true.
         * */
        checkDefaultTemplate(agreementTemplate);

        return new CreateAgreementTemplate(agreementTemplate, agreementTemplateRepository).execute();
    }

    /**
     * If the input agreement template is the only that exists with this agreement type and level. Set to true the default template attribute.
     * 
     * @param agreementTemplate
     */
    private AgreementTemplate checkAndUpdateDefaultTemplateIfOnlyAgreementTypeAndLevel(AgreementTemplate agreementTemplate) {
        Optional<AgreementTemplate> agreementTemplateDefaultTemplate = agreementTemplateRepository
                .findByAgreementTypeAndAgreementLevelAndDefaultTemplate(agreementTemplate.getAgreementType(),
                        agreementTemplate.getAgreementLevel(), true);
        if (!agreementTemplateDefaultTemplate.isPresent()) {
            agreementTemplate.setDefaultTemplate(true);
        }
        return agreementTemplate;
    }

    /**
     * Find agreement template with the input agreement template's level and type and default template to true, and update to false if exists.
     * 
     * @param agreementTemplate
     */
    private void checkAndUpdateDefaultTemplateInSameAgreementTypeAndLevel(AgreementTemplate agreementTemplate) {
        Optional<AgreementTemplate> agreementTemplateDefaultTemplate = agreementTemplateRepository
                .findByAgreementTypeAndAgreementLevelAndDefaultTemplate(agreementTemplate.getAgreementType(),
                        agreementTemplate.getAgreementLevel(), true);
        if (agreementTemplateDefaultTemplate.isPresent() && agreementTemplateDefaultTemplate.get().getDefaultTemplate()) {
            agreementTemplateDefaultTemplate.get().setDefaultTemplate(false);
            new UpdateAgreementTemplate(agreementTemplateDefaultTemplate.get(), agreementTemplateRepository).execute();
        }

    }

    @Override
    public AgreementTemplate update(AgreementTemplate agreementTemplate) {
        if(exists(agreementTemplate.getId())) {
            AgreementTemplate agreementTemplateBBDD = agreementTemplateRepository.findById(agreementTemplate.getId()).get();
            if (agreementTemplateBBDD.getDefaultTemplate() != agreementTemplate.getDefaultTemplate()) {
                checkDefaultTemplate(agreementTemplate);
            }
            return new UpdateAgreementTemplate(agreementTemplate, agreementTemplateRepository).execute();
        } else {
            throw new AgreementTemplateDoesNotExistException("No agreement template by the id: " + agreementTemplate.getId() + " was found.");
        }
    }

    /**
     * Only can exist one agreement default template to true by agreement type and level.
         * If default template is true : Check and update to false if is necessary the others agreement templates with the same agreement type and level.
         * If default template is false: Check if is the only one agreement template with this agreement type and level, in that case set default template to true.
     * 
     * @param agreementTemplate
     */
    private void checkDefaultTemplate(AgreementTemplate agreementTemplate) {
        if (agreementTemplate.getDefaultTemplate()) {
            checkAndUpdateDefaultTemplateInSameAgreementTypeAndLevel(agreementTemplate);
        } else {
            checkAndUpdateDefaultTemplateIfOnlyAgreementTypeAndLevel(agreementTemplate);
        }
    }

    @Override
    public boolean exists(String id) {
        return new ExistsAgreementTemplate(id, agreementTemplateRepository).execute();
    }  


    @Override
    public void delete(String id) {
        if(exists(id)) {
            AgreementTemplate agreementTemplate = getById(id);
            //this needs to get called first, safer to remove the agreement template only after it has been cascaded
            this.publisher.publish(new RemoveAgreementTemplate(new Object(), agreementTemplate.getId()));
            new DeleteAgreementTemplate(agreementTemplate, agreementTemplateRepository).execute();
        } else {
            throw new AgreementTemplateDoesNotExistException("No agreement template by the id: " + id + " was found.");
        }   
        
    }
    
    @Override
    public void delete(List<AgreementTemplate> requestAgreementTemplates) {
        for(AgreementTemplate agreementTemplate : requestAgreementTemplates) {
            this.publisher.publish(new RemoveAgreementTemplate(new Object(), agreementTemplate.getId()));
        }                
        new DeleteAgreementTemplates(requestAgreementTemplates, agreementTemplateRepository).execute();
        
    }

    private AgreementTemplate getById(String id) {
        AgreementTemplate agreementTemplate = null;
        agreementTemplate =  new GetAgreementTemplateById(agreementTemplateRepository, id).execute();
        if(agreementTemplate != null) {
            return agreementTemplate;
        } else {
            throw new AgreementTemplateDoesNotExistException("No agreement template by the id: " + id + " was found.");
        }
    }


}
