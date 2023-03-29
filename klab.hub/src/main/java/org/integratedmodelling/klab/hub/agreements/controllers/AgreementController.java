package org.integratedmodelling.klab.hub.agreements.controllers;

import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.hub.agreements.services.AgreementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nimbusds.jose.shaded.json.JSONObject;

@RestController
public class AgreementController {
    
    @Autowired
    private AgreementService agreementService;
    
    @GetMapping(API.HUB.GET_AGREEMENT)
    public ResponseEntity<?> getAgreement(@RequestParam(required=false) String id) {
        JSONObject agreement = new JSONObject().appendField("agreement", agreementService.getAgreement(id));
        return new ResponseEntity<>(agreement, HttpStatus.OK);        
    }
}
