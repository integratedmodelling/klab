package org.integratedmodelling.klab.cli.commands.message;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.klab.api.cli.ICommand;
import org.integratedmodelling.klab.api.runtime.ISession;

public class Logging implements ICommand {

    @Override
    public Object execute(IServiceCall call, ISession session) {
        String message = StringUtils.join((List<?>) call.getParameters().get("arguments"), ' ').trim();
        switch(call.getName()) {
        case "info":
            session.getMonitor().info(message);
            break;
        case "error":
            session.getMonitor().error(message);
            break;
        case "warning":
            session.getMonitor().warn(message);
            break;
        case "debug":
            session.getMonitor().debug(message);
            break;
        }
        return null;
    }

}
