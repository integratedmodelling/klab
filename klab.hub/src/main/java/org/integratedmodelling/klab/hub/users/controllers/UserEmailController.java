package org.integratedmodelling.klab.hub.users.controllers;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.hub.config.EmailConfig;
import org.integratedmodelling.klab.hub.emails.services.EmailManager;
import org.integratedmodelling.klab.hub.exception.MailAddressNotAllowedException;
import org.integratedmodelling.klab.rest.KlabEmail;
import org.integratedmodelling.klab.rest.KlabEmail.EmailType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for email related actions
 * Include template management
 * @author Enrico Girotto
 *
 */
@RestController
public class UserEmailController {

    @Autowired
    EmailManager emailManager;

    @Autowired
    private EmailConfig emailConfig;

    /**
     * Send an email. Used by apps in engines.
     * @param sendEmail
     * @return confirmation or error
     */
    @PostMapping(value = API.HUB.USER_SEND_EMAIL, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity< ? > sendEmail(@RequestBody KlabEmail email) {

        // / Currently, the only email that can be used is the one used for SMTP. Therefore no
        // customization is available
        email.from = emailConfig.senderEmail();

        Set<String> recipients = new HashSet<String>();
        Set<String> replayToRecipients = new HashSet<String>();
        if (email.to != null && email.to.size() > 0) {
            for(String userEmail : email.to) {
                if (userEmail.length() > 0) {
                    if (!userEmail.contains("@")) {
                        userEmail = userEmail + "@" + emailConfig.defaultDomain();
                    }
                    if (Arrays.asList(emailConfig.getAllowedEmailAddresses()).contains(userEmail)) {
                        recipients.add(userEmail);
                    } else {
                        throw new MailAddressNotAllowedException(userEmail);
                    }
                }
            }
            if (recipients.size() == 0) {
                recipients.add(emailConfig.defaultRecipient());
            }
        } else {
            recipients.add(emailConfig.defaultRecipient());
        }
        if (email.replayTo != null && email.replayTo.size() > 0) {
            for(String replayTo : email.replayTo) {
                replayToRecipients.add(replayTo);
            }
        }

        emailManager.send(email.from, recipients, replayToRecipients, email.subject, email.content, email.type != EmailType.TEXT);
        return new ResponseEntity<HttpStatus>(HttpStatus.OK);

    }
}
