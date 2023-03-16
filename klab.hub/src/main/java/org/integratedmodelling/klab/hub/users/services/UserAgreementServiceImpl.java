package org.integratedmodelling.klab.hub.users.services;

import java.util.Collection;
import java.util.Date;
import org.integratedmodelling.klab.hub.api.Agreement;
import org.integratedmodelling.klab.hub.api.User;
import org.integratedmodelling.klab.hub.exception.BadRequestException;
import org.integratedmodelling.klab.hub.repository.AgreementRepository;
import org.integratedmodelling.klab.hub.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserAgreementServiceImpl implements UserAgreementService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AgreementRepository agreementRepository;

    private User getUserByUsername(String username) throws BadRequestException {
        return userRepository.findByNameIgnoreCase(username)
                .orElseThrow(() -> new BadRequestException(String.format("User %s is not present in the database", username)));
    }

    @Override
    public Collection<Agreement> getAgreementsFromUser(String username) {
        return getUserByUsername(username).getAgreements();
    }

    @Override
    public void revokeAgreementFromUser(String username, String agreementId) {
        User user = getUserByUsername(username);

        user.getAgreements().stream()
            .filter(a -> a.getId().equals(agreementId))
            .forEach(a -> {
                a.setExpiredDate(new Date());
                agreementRepository.save(a);
            });
    }

    private boolean checkIfUserHasThatAgreementId(String agreementId, User user) {
        return user.getAgreements().stream().anyMatch(a -> a.getId().equals(agreementId));
    }

    @Override
    public void patchAgreementOfUser(String username, Agreement agreement) throws BadRequestException {
        User user = getUserByUsername(username);

        if(!checkIfUserHasThatAgreementId(agreement.getId(), user)) {
            throw new BadRequestException(String.format("User %s does not have an agreement with id %s", username, agreement.getId()));
        }

        agreementRepository.save(agreement);
    }

}
