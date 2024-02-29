package org.integratedmodelling.klab.hub.agreements.controllers;

import java.util.List;

import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.hub.agreements.dto.AgreementTemplate;
import org.integratedmodelling.klab.hub.agreements.payload.RequestAgreementTemplate;
import org.integratedmodelling.klab.hub.agreements.services.AgreementTemplateService;
import org.integratedmodelling.klab.hub.enums.AgreementLevel;
import org.integratedmodelling.klab.hub.enums.AgreementType;
import org.integratedmodelling.klab.hub.exception.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nimbusds.jose.shaded.json.JSONObject;

@RestController
public class AgreementTemplateController {
    
    @Autowired
    private AgreementTemplateService agreementTemplateService;
    
    @GetMapping(API.HUB.AGREEMENT_TEMPLATE)
    public ResponseEntity<?> getAgreementTemplates(@RequestBody(required=false) RequestAgreementTemplate requestAgreementTemplate) {
        JSONObject agreementTemplates = new JSONObject().appendField("agreementTemplates", agreementTemplateService.getAgreementTemplates(requestAgreementTemplate));
        return new ResponseEntity<>(agreementTemplates, HttpStatus.OK);        
    }
    
    @GetMapping(API.HUB.AGREEMENT_TEMPLATE_TYPE_LEVEL)
    public ResponseEntity<?> getAgreementTemplate(@RequestParam(required=false) String agreementType, @RequestParam(required=false) String agreementLevel) {
        JSONObject agreementTemplate = new JSONObject().appendField("agreementTemplate", agreementTemplateService.getAgreementTemplate(AgreementType.valueOf(agreementType), AgreementLevel.valueOf(agreementLevel)));
        return new ResponseEntity<>(agreementTemplate, HttpStatus.OK);        
    }
    
    @GetMapping(API.HUB.AGREEMENT_TEMPLATE_FILTER)
    public ResponseEntity<?> getAgreementTemplate(RequestAgreementTemplate requestAgreementTemplate) {
        AgreementTemplate agreementTemplate = null;
        
        try {
             agreementTemplate = agreementTemplateService.getAgreementTemplate(requestAgreementTemplate);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(e.getMessage());
        }
        JSONObject agreementTemplateJson = new JSONObject().appendField("agreementTemplate", agreementTemplate);
        return new ResponseEntity<>(agreementTemplateJson, HttpStatus.OK);        
    }
    
    @PostMapping(value = API.HUB.AGREEMENT_TEMPLATE)
    @PreAuthorize("hasRole('ROLE_SYSTEM') or hasRole('ROLE_ADMINISTRATOR')")
    public ResponseEntity<Object> createAgreementTemplate(@RequestBody AgreementTemplate agreementTemplate) {
        AgreementTemplate agreementTemplateCreated = agreementTemplateService.create(agreementTemplate);
        return new ResponseEntity<>(agreementTemplateCreated, HttpStatus.CREATED);
    }
    
    @PutMapping(value = API.HUB.AGREEMENT_TEMPLATE_ID)
    @PreAuthorize("hasRole('ROLE_SYSTEM') or hasRole('ROLE_ADMINISTRATOR')")
    public ResponseEntity<Object> updateAgreementTemplatep(@PathVariable("id") String id, @RequestBody AgreementTemplate agreementTemplate) {
        if (id.equals(agreementTemplate.getId())) {
            agreementTemplateService.update(agreementTemplate);
        } else {
            throw new BadRequestException("Agreement Id does not match url Id");
        }
        return new ResponseEntity<>("The agreement has been updated successsfully", HttpStatus.OK);
    }
    
    @DeleteMapping(value = API.HUB.AGREEMENT_TEMPLATE_ID)
    @PreAuthorize("hasRole('ROLE_SYSTEM')")
    public ResponseEntity<Object> delete(@PathVariable("id") String id) {
        agreementTemplateService.delete(id);
        return new ResponseEntity<>("The agreement template has been deleted successsfully", HttpStatus.OK);
    }
    
    @PostMapping(value = API.HUB.AGREEMENT_TEMPLATE_DELETE)
    @PreAuthorize("hasRole('ROLE_SYSTEM')")
    public ResponseEntity<Object> delete(@RequestBody List<AgreementTemplate> requestAgreementTemplates) {
        agreementTemplateService.delete(requestAgreementTemplates);
        return new ResponseEntity<>("The agreement templates has been deleted successsfully", HttpStatus.OK);
    }
    
    

}
