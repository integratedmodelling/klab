package org.integratedmodelling.klab.hub.licenses.controllers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.NoSuchProviderException;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.bouncycastle.openpgp.PGPException;
import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.auth.KlabCertificate;
import org.integratedmodelling.klab.hub.agreements.dto.Agreement;
import org.integratedmodelling.klab.hub.agreements.services.AgreementService;
import org.integratedmodelling.klab.hub.emails.services.EmailManager;
import org.integratedmodelling.klab.hub.licenses.dto.EngineAuthResponeFactory;
import org.integratedmodelling.klab.hub.licenses.dto.LicenseGenerator;
import org.integratedmodelling.klab.hub.licenses.exceptions.LicenseExpiredException;
import org.integratedmodelling.klab.hub.licenses.exceptions.LicenseGenerationError;
import org.integratedmodelling.klab.hub.licenses.services.LicenseConfigService;
import org.integratedmodelling.klab.hub.repository.MongoGroupRepository;
import org.integratedmodelling.klab.hub.tags.enums.TagNameEnum;
import org.integratedmodelling.klab.hub.tags.services.TagNotificationService;
import org.integratedmodelling.klab.hub.tokens.services.UserAuthTokenService;
import org.integratedmodelling.klab.hub.users.dto.ProfileResource;
import org.integratedmodelling.klab.hub.users.services.UserProfileService;
import org.integratedmodelling.klab.rest.EngineAuthenticationRequest;
import org.integratedmodelling.klab.rest.EngineAuthenticationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EngineLicenseController extends LicenseController<EngineAuthenticationRequest> {

    private EmailManager emailManager;

    private EngineAuthResponeFactory authFactory;

    private UserProfileService userProfileService;

    private AgreementService agreementService;

    private LicenseGenerator licenseGenerator;

    private TagNotificationService tagNotificationService;

//    @Autowired
//    EngineLicenseController(UserProfileService userProfileService, LicenseConfigService configService,
//            MongoGroupRepository groupRepository, EmailManager emailManager, UserAuthTokenService authTokenService,
//            AgreementService agreementService, TagNotificationService tagNotificationService) {
//        this.authFactory = new EngineAuthResponeFactory(userProfileService, groupRepository, configService, authTokenService,
//                agreementService);
//        this.licenseGenerator = new LicenseGenerator(configService);
//        this.userProfileService = userProfileService;
//        this.emailManager = emailManager;
//        this.agreementService = agreementService;
//        this.tagNotificationService = tagNotificationService;
//    }

    @Autowired
    EngineLicenseController(UserProfileService userProfileService, LicenseConfigService configService,
            MongoGroupRepository groupRepository, EmailManager emailManager, AgreementService agreementService,
            TagNotificationService tagNotificationService) {
        this.authFactory = new EngineAuthResponeFactory(userProfileService, groupRepository, configService, agreementService);
        this.licenseGenerator = new LicenseGenerator(configService);
        this.userProfileService = userProfileService;
        this.emailManager = emailManager;
        this.agreementService = agreementService;
        this.tagNotificationService = tagNotificationService;
    }

    @GetMapping(value = API.HUB.USER_AGREEMENT_BASE_ID, params = "certificate")
    @PreAuthorize("@securityService.isUser(#id)")
    public void generateCertFile(@PathVariable("id") String id, @PathVariable("agreementId") String agreementId,
            HttpServletResponse response) throws IOException {

        ProfileResource profile = userProfileService.getCurrentUserProfile(false);
        Agreement agreement = agreementService.getAgreement(agreementId);
        byte[] certFileContent = licenseGenerator.generate(profile, agreement, null);
        String certFileString = String.format("attachment; filename=%s", KlabCertificate.DEFAULT_ENGINE_CERTIFICATE_FILENAME);
        response.setHeader("Content-disposition", certFileString);
        response.setContentType("text/plain;charset=utf-8");
        response.setContentLength(certFileContent.length);
        try {
            IOUtils.copy(new ByteArrayInputStream(certFileContent), response.getOutputStream());
            response.flushBuffer();
        } finally {
            response.getOutputStream().close();
        }
        /* Delete warning to download kLabCertificate is exists */
        tagNotificationService.deleteTagNotification(userProfileService.getUser(id), TagNameEnum.downloadCertificateChangeEmail);

    }

    @PostMapping(value = API.HUB.AUTHENTICATE_ENGINE)
    public ResponseEntity<EngineAuthenticationResponse> processCertificate(@RequestBody EngineAuthenticationRequest request,
            HttpServletRequest httpRequest) throws MessagingException {
        String remoteAddr = "";

        if (httpRequest != null) {
            remoteAddr = httpRequest.getHeader("X-FORWARDED-FOR");
            if (remoteAddr == null || "".equals(remoteAddr)) {
                remoteAddr = httpRequest.getRemoteAddr();
            }
        }

        if (httpRequest.getHeader("test") != null) {
            remoteAddr = "128.0.0.1";
        }

        EngineAuthenticationResponse response = null;

        try {
            response = authFactory.getRespone(request, remoteAddr);
        } catch (NoSuchProviderException | IOException | PGPException e) {
            throw new LicenseGenerationError("Issue in authenticating certificate.");
        } catch (LicenseExpiredException e) {
            emailManager.expiredLicenseEmail(request.getEmail());
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
