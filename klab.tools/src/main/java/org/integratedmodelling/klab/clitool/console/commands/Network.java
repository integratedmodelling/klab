package org.integratedmodelling.klab.clitool.console.commands;

import java.util.List;

import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.clitool.CliRuntime;
import org.integratedmodelling.klab.clitool.api.ICommand;

public class Network implements ICommand {

    @Override
    public Object execute(IServiceCall call, ISession session) throws Exception {

        if (((List<?>) call.getParameters().get("arguments")).size() > 0) {
            String arg = ((List<?>) call.getParameters().get("arguments")).get(0).toString();
            if ("on".equals(arg)) {
                CliRuntime.INSTANCE.startNetwork();
            } else if ("off".equals(arg)) {
                CliRuntime.INSTANCE.stopNetwork();
            } else {
                session.getMonitor().error("Network services may only be turned on or off");
            }
        }
        return null;
    }

}
