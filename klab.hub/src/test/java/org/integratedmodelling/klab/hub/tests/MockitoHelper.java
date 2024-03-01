package org.integratedmodelling.klab.hub.tests;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Set;

import org.integratedmodelling.klab.hub.agreements.dto.Agreement;
import org.integratedmodelling.klab.hub.agreements.dto.AgreementEntry;
import org.integratedmodelling.klab.hub.users.dto.GroupEntry;
import org.integratedmodelling.klab.hub.users.dto.MongoGroup;
import org.integratedmodelling.klab.hub.users.dto.User;
import org.mockito.Mockito;

public class MockitoHelper {
    public static Agreement mockAgreement() {
        Agreement agreement = Mockito.mock(Agreement.class);
        return agreement;
    }

    public static Agreement mockAgreement(Set<GroupEntry> groupEntries) {
        Agreement agreement = Mockito.mock(Agreement.class);
        Mockito.when(agreement.getGroupEntries()).thenReturn(groupEntries);
        return agreement;
    }

    public static Agreement mockNonExpiredAgreement(Set<GroupEntry> groupEntries) {
        Agreement agreement = Mockito.mock(Agreement.class);
        Mockito.when(agreement.getGroupEntries()).thenReturn(groupEntries);
        Mockito.when(agreement.getExpirationDate()).thenReturn(Date.from(Instant.now().plus(2, ChronoUnit.YEARS)));
        return agreement;
    }

    public static Agreement mockExpiredAgreement(Set<GroupEntry> groupEntries) {
        Agreement agreement = Mockito.mock(Agreement.class);
        Mockito.when(agreement.getGroupEntries()).thenReturn(groupEntries);
        Mockito.when(agreement.getExpirationDate()).thenReturn(Date.from(Instant.now().minus(2, ChronoUnit.YEARS)));
        return agreement;
    }

    public static AgreementEntry mockAgreementEntry(Agreement agreement) {
        AgreementEntry agreementEntry = Mockito.mock(AgreementEntry.class);
        Mockito.when(agreementEntry.getAgreement()).thenReturn(agreement);
        return agreementEntry;
    }

    public static User mockUser() {
        User user = Mockito.mock(User.class);
        return user;
    }

    public static User mockUser(Set<AgreementEntry> agreementEntries) {
        User user = Mockito.mock(User.class);
        Mockito.when(user.getAgreements()).thenReturn(agreementEntries);
        return user;
    }


    public static User mockUserWithAgreements() {
        Agreement agreement = mockAgreement();
        AgreementEntry agreementEntry = mockAgreementEntry(agreement);

        User user = mockUser();
        Set<AgreementEntry> agreementEntries = Set.of(agreementEntry);
        Mockito.when(user.getAgreements()).thenReturn(agreementEntries);
        return user;
    }

    public static MongoGroup mockMongoGroup() {
        MongoGroup mongoGroup = Mockito.mock(MongoGroup.class);
        return mongoGroup;
    }

    public static MongoGroup mockMongoGroup(String groupName) {
        MongoGroup mongoGroup = Mockito.mock(MongoGroup.class);
        Mockito.when(mongoGroup.getName()).thenReturn(groupName);
        return mongoGroup;
    }

    public static MongoGroup mockMongoGroup(String groupName, long defaultExpirationTime) {
        MongoGroup mongoGroup = Mockito.mock(MongoGroup.class);
        Mockito.when(mongoGroup.getName()).thenReturn(groupName);
        Mockito.when(mongoGroup.getDefaultExpirationTime()).thenReturn(defaultExpirationTime);
        return mongoGroup;
    }

    public static GroupEntry mockGroupEntry() {
        GroupEntry groupEntry = Mockito.mock(GroupEntry.class);
        return groupEntry;
    }

    public static GroupEntry mockGroupEntry(MongoGroup mongoGroup, String groupName) {
        GroupEntry groupEntry = Mockito.mock(GroupEntry.class);
        Mockito.when(groupEntry.getGroup()).thenReturn(mongoGroup);
        Mockito.when(groupEntry.getGroupName()).thenReturn(groupName);
        return groupEntry;
    }

    public static GroupEntry mockGroupEntry(MongoGroup mongoGroup, LocalDateTime expiration) {
        GroupEntry groupEntry = Mockito.mock(GroupEntry.class);
        Mockito.when(groupEntry.getGroup()).thenReturn(mongoGroup);
        Mockito.when(groupEntry.getGroupName()).thenReturn(mongoGroup.getName());
        Mockito.when(groupEntry.getExpiration()).thenReturn(expiration);
        return groupEntry;
    }
}
