package org.integratedmodelling.klab.clitool.console.commands.resources;

import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.clitool.api.ICommand;

public class Clear implements ICommand {

    @Override
    public Object execute(IServiceCall call, ISession session) throws Exception {

        // TODO ask for confirmation

        if (call.getParameters().get("arguments", java.util.List.class).size() > 0) {
            for (Object urn : call.getParameters().get("arguments", java.util.List.class)) {
                Resources.INSTANCE.getLocalResourceCatalog().remove(urn.toString());
            }
        } else {
            Resources.INSTANCE.getLocalResourceCatalog().clear();
        }
        return null;
    }
}
