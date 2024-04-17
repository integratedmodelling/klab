package org.integratedmodelling.klab.cli.commands.credentials;

import java.net.MalformedURLException;
import java.net.URL;
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
        String id = (String) call.getParameters().get("id");
        String url = (String) call.getParameters().get("url");
        String scheme = (String) call.getParameters().get("scheme");
        int astart = 0;

        if (scheme == null) {
            scheme = args.get(astart).toString();
            astart++;
        }
        List<String> params = List.of(ExternalAuthenticationCredentials.parameterKeys.get(scheme));
        int nargs = params.size() + astart;

        if (url == null) {
            url = args.get(astart).toString();
            astart++;
            nargs++;
        }
        if (id == null) {
            try {
                id = new URL(url).getHost();
            } catch (MalformedURLException e) {
               throw new KlabValidationException("Cannot generate credential ID from URL " + url);
            }
        }

        if (params.isEmpty()) {
            throw new KlabIllegalArgumentException("unrecognized authorization scheme");
        }

        if (args.size() != nargs) {
            throw new KlabValidationException("expecting " + nargs + " arguments for scheme " + scheme);
        }

        ret = scheme + " credentials:";

        ExternalAuthenticationCredentials credentials = new ExternalAuthenticationCredentials();

        credentials.setScheme(scheme);
        credentials.setURL(url);
        for (int i = astart; i < nargs; i++) {
            credentials.getCredentials().add(args.get(i).toString());
            ret += "\n   " + params.get(i - astart) + ": " + args.get(i).toString();
        };

        Authentication.INSTANCE.addExternalCredentials(id, credentials);

        return ret;

    }

}
