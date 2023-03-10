package org.integratedmodelling.klab.hub.agreements.controllers;

import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.hub.agreements.services.AgreementTemplateService;
import org.integratedmodelling.klab.hub.enums.AgreementLevel;
import org.integratedmodelling.klab.hub.enums.AgreementType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nimbusds.jose.shaded.json.JSONObject;

@RestController
public class AgreementTemplateController {
    
    @Autowired
    private AgreementTemplateService agreementTemplateService;
    
    @GetMapping(API.HUB.GET_AGREEMENT_TEMPLATE)
    public ResponseEntity<?> getAgreementTemplate(@RequestParam(required=false) String agreementType, @RequestParam(required=false) String agreementLevel) {
        JSONObject agreementTemplate = new JSONObject().appendField("agreementTemplate", agreementTemplateService.getAgreementTemplate(AgreementType.valueOf(agreementType), AgreementLevel.valueOf(agreementLevel)));
        return new ResponseEntity<>(agreementTemplate, HttpStatus.OK);        
    }
    
    

}
