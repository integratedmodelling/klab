package org.integratedmodelling.klab.cli.commands.credentials;

import java.util.List;

import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.klab.Authentication;
import org.integratedmodelling.klab.api.cli.ICommand;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.rest.ExternalAuthenticationCredentials;

public class Set implements ICommand {

	@Override
	public Object execute(IServiceCall call, ISession session) {		
		
		List<?> args = (List<?>) call.getParameters().get("arguments");
		if (args.size() != 3) {
			throw new KlabValidationException("expecting <host[:port]> <username> <password>");
		}

		ExternalAuthenticationCredentials credentials = new ExternalAuthenticationCredentials();
		
		credentials.setScheme("basic");
		credentials.getCredentials().add(args.get(1).toString());
		credentials.getCredentials().add(args.get(2).toString());

		Authentication.INSTANCE.addExternalCredentials(args.get(0).toString(), credentials);
		
		return "Credentials for " + args.get(1) + "@" + args.get(0) + " added";
	
	}

}
