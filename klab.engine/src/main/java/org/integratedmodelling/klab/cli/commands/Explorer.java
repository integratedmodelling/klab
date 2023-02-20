package org.integratedmodelling.klab.cli.commands;

import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.klab.api.cli.ICommand;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.utils.BrowserUtils;

public class Explorer implements ICommand {

    @Override
    public Object execute(IServiceCall call, ISession session) {
        BrowserUtils.startBrowser("http://localhost:8283/modeler/ui/viewer?session=" + session.getId());
        return "Browser launched on " + "http://localhost:8283/modeler/ui/viewer?session=" + session.getId();
    }

}
