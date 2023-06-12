package org.integratedmodelling.klab.cli.commands.credentials;

import java.util.List;

import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.klab.Authentication;
import org.integratedmodelling.klab.api.cli.ICommand;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.exceptions.KlabIllegalArgumentException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.rest.ExternalAuthenticationCredentials;

public class Set implements ICommand {

    /**
     * Start with an (optional) scheme; if not given, "basic" is assumed. Then first argument is
     * always the site to get access to, then depends on the scheme.
     */

    @Override
    public Object execute(IServiceCall call, ISession session) {

        String ret = "";
        List<?> args = (List<?>) call.getParameters().get("arguments");
        String scheme = "basic";
        int nargs = 3;
        int astart = 0;
        if (args.size() > 0) {
            if (ExternalAuthenticationCredentials.parameterKeys.containsKey(args.get(0).toString())) {
                scheme = args.get(0).toString();
                astart = 1;
            }
        }

        String[] params = ExternalAuthenticationCredentials.parameterKeys.get(scheme);
        if (params == null) {
            throw new KlabIllegalArgumentException("unrecognized authorization scheme");
        }

        nargs = params.length + 1; // the URL
        if (args.size() != (nargs + astart)) {
            throw new KlabValidationException("expecting " + nargs + " arguments for scheme " + scheme);
        }

        ret = scheme + " credentials:";

        ExternalAuthenticationCredentials credentials = new ExternalAuthenticationCredentials();

        credentials.setScheme(scheme);
        for (int i = astart + 1; i < (astart + nargs); i++) {
            credentials.getCredentials().add(args.get(i).toString());
            ret += "\n   " + params[i - astart - 1] + ": " + args.get(i).toString();

        }

        Authentication.INSTANCE.addExternalCredentials(args.get(astart).toString(), credentials);

        return ret;

    }

}
