package org.integratedmodelling.klab.hub.tasks.controllers;

import java.util.Collection;
import javax.annotation.security.RolesAllowed;
import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.hub.api.Agreement;
import org.integratedmodelling.klab.hub.api.AgreementEntry;
import org.integratedmodelling.klab.hub.exception.BadRequestException;
import org.integratedmodelling.klab.hub.users.services.UserAgreementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AgreementController {

    @Autowired
    UserAgreementService service;

    @GetMapping(value = API.HUB.USER_AGREEMENTS)
    @RolesAllowed({"ROLE_ADMINISTRATOR", "ROLE_SYSTEM"})
    public ResponseEntity< ? > getAgreementsOfUser(
            @PathVariable("id") String username) {
        Collection<AgreementEntry> agreements;
        try {
            agreements = service.getAgreementsFromUser(username);
        } catch (BadRequestException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }

        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(agreements);
    }

    @PostMapping(value = API.HUB.USER_REVOKE_AGREEMENT_ID)
    @RolesAllowed({"ROLE_ADMINISTRATOR", "ROLE_SYSTEM"})
    public ResponseEntity< ? > revokeAgreementOfUser(
            @PathVariable("id") String username,
            @PathVariable("agreement-id") String agreementId) {
        try {
            service.revokeAgreement(username, agreementId);
        } catch (BadRequestException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }

        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(String.format("Agreement %s of user %s has been revoked.", agreementId, username));
    }

    private boolean validateAgreementId(Agreement agreement, String agreementId) {
        if (agreement.getId() == null) {
            agreement.setId(agreementId);
            return true;
        }
        return agreement.getId().equals(agreementId);
    }
    
    @PutMapping(value = API.HUB.USER_AGREEMENT_ID, consumes = "application/json")
    @RolesAllowed({"ROLE_ADMINISTRATOR", "ROLE_SYSTEM"})
    public ResponseEntity< ? > updateAgreementOfUser(
            @PathVariable("id") String username,
            @PathVariable("agreement-id") String agreementId,
            @RequestBody Agreement agreement) {
        if(!validateAgreementId(agreement, agreementId)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Cannot modify agreement. Unclear id reference.");
        }
        try {
            service.updateAgreement(username, agreement);
        } catch (BadRequestException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }

        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(String.format("Agreement %s of user %s has been modified.", agreementId, username));
    }

}
