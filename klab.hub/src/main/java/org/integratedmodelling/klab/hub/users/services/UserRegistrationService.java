package org.integratedmodelling.klab.hub.users.services;

import org.integratedmodelling.klab.hub.agreements.dto.Agreement;
import org.integratedmodelling.klab.hub.enums.AgreementLevel;
import org.integratedmodelling.klab.hub.enums.AgreementType;
import org.integratedmodelling.klab.hub.users.dto.User;
import org.integratedmodelling.klab.hub.users.exceptions.UserEmailExistsException;
import org.integratedmodelling.klab.hub.users.exceptions.UserExistsException;
import org.springframework.stereotype.Service;

@Service
public interface UserRegistrationService {
	public abstract User registerNewUser(String username, String email)
			throws UserExistsException, UserEmailExistsException;
	public abstract User verifyNewUser(String username);
	public abstract User setPassword(String username, String password, String confirm);
	public abstract User registerUser(User user);
	public abstract User addAgreement(User user, Agreement agreement);
    public abstract User createAndAddAgreement(User user, AgreementType agreementType, AgreementLevel agreementLevel, String identity_provider);
}
