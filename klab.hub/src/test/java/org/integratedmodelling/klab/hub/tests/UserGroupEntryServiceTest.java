package org.integratedmodelling.klab.hub.tests;

import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.integratedmodelling.klab.hub.agreements.services.AgreementService;
import org.integratedmodelling.klab.hub.api.Agreement;
import org.integratedmodelling.klab.hub.api.AgreementEntry;
import org.integratedmodelling.klab.hub.api.User;
import org.integratedmodelling.klab.hub.payload.UpdateUsersGroups;
import org.integratedmodelling.klab.hub.repository.AgreementRepository;
import org.integratedmodelling.klab.hub.repository.MongoGroupRepository;
import org.integratedmodelling.klab.hub.repository.UserRepository;
import org.integratedmodelling.klab.hub.users.services.UserGroupEntryService;
import org.integratedmodelling.klab.hub.users.services.UserGroupEntryServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@TestInstance(Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(MockitoExtension.class)
@EnableAutoConfiguration
@ActiveProfiles(profiles = "production")
public class UserGroupEntryServiceTest {

    @InjectMocks
    private UserGroupEntryServiceImpl userService;

    @Mock
    private UserRepository userRepository;
    @Mock
    private MongoGroupRepository groupRepository;
    @Mock
    private AgreementService agreementService;
    @Mock
    private AgreementRepository agreementRepository;

    private AgreementEntry mockAgreementEntry() {
        AgreementEntry agreementEntry = Mockito.mock(AgreementEntry.class);
        return agreementEntry;
    }

    private Agreement mockAgreement() {
        Agreement agreement = Mockito.mock(Agreement.class);
        return agreement;
    }

    private User mockUser() {
        User user = Mockito.mock(User.class);
        return user;
    }

    @Test
    public void addUsersGroupsByNamesTest() {
        // Mock agreement
        Agreement agreement = mockAgreement();
        AgreementEntry agreementEntry = mockAgreementEntry();
        Mockito.when(agreementEntry.getAgreement()).thenReturn(agreement);
        // Mock user
        User user = mockUser();
        Set<AgreementEntry> agreementEntries = Set.of(agreementEntry);
        Mockito.when(user.getAgreements()).thenReturn(agreementEntries);
        // Mock UserRepository
        Mockito.when(userRepository.findByNameIgnoreCase("hades")).thenReturn(Optional.of(user));

        UpdateUsersGroups updateRequest = Mockito.mock(UpdateUsersGroups.class);
        Mockito.when(updateRequest.getGroupNames()).thenReturn(Set.of("IM", "ARIES"));
        Mockito.when(updateRequest.getUsernames()).thenReturn(Set.of("hades"));

        userService.addUsersGroupsByNames(updateRequest); 
    }

}
