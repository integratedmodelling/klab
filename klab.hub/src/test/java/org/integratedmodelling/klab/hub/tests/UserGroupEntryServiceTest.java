package org.integratedmodelling.klab.hub.tests;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.integratedmodelling.klab.hub.agreements.dto.Agreement;
import org.integratedmodelling.klab.hub.agreements.dto.AgreementEntry;
import org.integratedmodelling.klab.hub.agreements.services.AgreementService;
import org.integratedmodelling.klab.hub.repository.AgreementRepository;
import org.integratedmodelling.klab.hub.repository.MongoGroupRepository;
import org.integratedmodelling.klab.hub.repository.UserRepository;
import org.integratedmodelling.klab.hub.users.dto.GroupEntry;
import org.integratedmodelling.klab.hub.users.dto.MongoGroup;
import org.integratedmodelling.klab.hub.users.dto.User;
import org.integratedmodelling.klab.hub.users.exceptions.UserDoesNotExistException;
import org.integratedmodelling.klab.hub.users.payload.UpdateUsersGroups;
import org.integratedmodelling.klab.hub.users.services.UserGroupEntryServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
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

    private final String USER_NAME = "olentzero";
    private final String GROUP_NAME = "SCP";
    private final String DEPENDED_ON_GROUP_NAME = "TIA";
    public final long EPOCH_YEAR_IN_MILLIS = 31532400000L;

    @Test
    public void addUsersGroupsByNames_noDependantGroupsNoExpirationDate() {
        User user = MockitoHelper.mockUserWithAgreements();
        Mockito.when(userRepository.findByNameIgnoreCase(USER_NAME)).thenReturn(Optional.of(user));
        UpdateUsersGroups updateRequest = Mockito.mock(UpdateUsersGroups.class);
        Mockito.when(updateRequest.getGroupNames()).thenReturn(Set.of(GROUP_NAME));
        Mockito.when(updateRequest.getUsernames()).thenReturn(Set.of(USER_NAME));
        MongoGroup mongoGroup = MockitoHelper.mockMongoGroup(GROUP_NAME);
        Mockito.when(groupRepository.findByNameIgnoreCase(GROUP_NAME)).thenReturn(Optional.of(mongoGroup));

        userService.addUsersGroupsByNames(updateRequest); 
    }

    @Test
    public void addUsersGroupsByNames_nonExistingUser() {
        // Do not add a mocked return for userRepository.findByNameIgnoreCase()
        UpdateUsersGroups updateRequest = Mockito.mock(UpdateUsersGroups.class);
        Mockito.when(updateRequest.getGroupNames()).thenReturn(Set.of(GROUP_NAME));
        Mockito.when(updateRequest.getUsernames()).thenReturn(Set.of(USER_NAME));
        MongoGroup mongoGroup = MockitoHelper.mockMongoGroup();
        Mockito.when(groupRepository.findByNameIgnoreCase(GROUP_NAME)).thenReturn(Optional.of(mongoGroup));

        Assertions.assertThrows(UserDoesNotExistException.class, () -> {
            userService.addUsersGroupsByNames(updateRequest); 
        });
    }

    @Test
    public void addUsersGroupsByNames_DependantGroupsNoExpirationDate() {
        User user = MockitoHelper.mockUserWithAgreements();
        Mockito.when(userRepository.findByNameIgnoreCase(USER_NAME)).thenReturn(Optional.of(user));
        UpdateUsersGroups updateRequest = Mockito.mock(UpdateUsersGroups.class);
        Mockito.when(updateRequest.getGroupNames()).thenReturn(Set.of(GROUP_NAME));
        Mockito.when(updateRequest.getUsernames()).thenReturn(Set.of(USER_NAME));
        MongoGroup mongoGroup = MockitoHelper.mockMongoGroup(GROUP_NAME);
        MongoGroup mongoGroupDependedOn = MockitoHelper.mockMongoGroup(GROUP_NAME);
        Mockito.when(mongoGroup.getDependsOn()).thenReturn(List.of(DEPENDED_ON_GROUP_NAME));
        Mockito.when(groupRepository.findByNameIgnoreCase(GROUP_NAME)).thenReturn(Optional.of(mongoGroup));
        Mockito.when(groupRepository.findByNameIn(List.of(DEPENDED_ON_GROUP_NAME))).thenReturn(List.of(mongoGroupDependedOn));
        Agreement agreement = MockitoHelper.mockAgreement();
//        AgreementEntry agreementEntry = MockitoHelper.mockAgreementEntry(agreement);

        userService.addUsersGroupsByNames(updateRequest); 
    }

    @Test
    public void addUsersGroupsByNames_OnlyDependantGroupsHaveExpirationDate() {
//        MongoGroup mongoGroupDependedOn = MockitoHelper.mockMongoGroup(DEPENDED_ON_GROUP_NAME, EPOCH_YEAR_IN_MILLIS * 2);
        MongoGroup mongoGroupDependedOn = MockitoHelper.mockMongoGroup(DEPENDED_ON_GROUP_NAME);
//        GroupEntry groupEntryDependedOn = MockitoHelper.mockGroupEntry(mongoGroupDependedOn, DEPENDED_ON_GROUP_NAME);
        GroupEntry groupEntryDependedOn = MockitoHelper.mockGroupEntry();
//      Agreement agreement = MockitoHelper.mockAgreement(Set.of(groupEntryDependedOn));
        Agreement agreement = MockitoHelper.mockAgreement();
        AgreementEntry agreementEntry = MockitoHelper.mockAgreementEntry(agreement);
        User user = MockitoHelper.mockUser(Set.of(agreementEntry));
        Mockito.when(userRepository.findByNameIgnoreCase(USER_NAME)).thenReturn(Optional.of(user));

        MongoGroup mongoGroup = MockitoHelper.mockMongoGroup(GROUP_NAME);
        Mockito.when(mongoGroup.getDependsOn()).thenReturn(List.of(DEPENDED_ON_GROUP_NAME));
        Mockito.when(groupRepository.findByNameIgnoreCase(GROUP_NAME)).thenReturn(Optional.of(mongoGroup));
        Mockito.when(groupRepository.findByNameIn(List.of(DEPENDED_ON_GROUP_NAME))).thenReturn(List.of(mongoGroupDependedOn));

        UpdateUsersGroups updateRequest = Mockito.mock(UpdateUsersGroups.class);
        Mockito.when(updateRequest.getGroupNames()).thenReturn(Set.of(GROUP_NAME));
        Mockito.when(updateRequest.getUsernames()).thenReturn(Set.of(USER_NAME));
        userService.addUsersGroupsByNames(updateRequest); 
    }
}
