package org.integratedmodelling.klab.hub.tasks.controllers;

import java.util.Collection;
import javax.annotation.security.RolesAllowed;
import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.hub.api.Agreement;
import org.integratedmodelling.klab.hub.exception.BadRequestException;
import org.integratedmodelling.klab.hub.users.services.UserAgreementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AgreementController {

    @Autowired
    UserAgreementService service;

    @GetMapping(value = API.HUB.USER_AGREEMENTS, produces = "application/json")
    @RolesAllowed({"ROLE_ADMINISTRATOR", "ROLE_SYSTEM"})
    public ResponseEntity< ? > revokeAgreementOfUser(
            @PathVariable("id") String username) {
        Collection<Agreement> agreements;
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

    @DeleteMapping(value = API.HUB.USER_AGREEMENT_ID, produces = "application/json")
    @RolesAllowed({"ROLE_ADMINISTRATOR", "ROLE_SYSTEM"})
    public ResponseEntity< ? > revokeAgreementOfUser(
            @PathVariable("id") String username,
            @PathVariable("agreement-id") String agreementId) {
        try {
            service.revokeAgreementFromUser(username, agreementId);
        } catch (BadRequestException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }

        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(String.format("Agreement %s of user %s has been revoked.", agreementId, username));
    }

}
