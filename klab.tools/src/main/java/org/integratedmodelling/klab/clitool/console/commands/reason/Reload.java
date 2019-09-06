package org.integratedmodelling.klab.clitool.console.commands.reason;

import java.util.List;

import org.integratedmodelling.kim.api.IKimNamespace;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.kim.model.Kim;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.api.cli.ICommand;
import org.integratedmodelling.klab.api.errormanagement.ICompileNotification;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.exceptions.KlabResourceNotFoundException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;

public class Reload implements ICommand {

    @Override
    public Object execute(IServiceCall call, ISession session) throws KlabValidationException {

        String ret = "";
        for (Object o : call.getParameters().get("arguments", List.class)) {
            IKimNamespace namespace = Kim.INSTANCE.getNamespace(o.toString());
            if (namespace != null) {
                Resources.INSTANCE.getLoader().touch(namespace.getFile());
                for (ICompileNotification issue : Resources.INSTANCE.getLoader().getIssues(namespace.getFile())) {
                    ret += issue + "\n";
                }
            } else {
                throw new KlabResourceNotFoundException("namespace " + o + " does not exist");
            }
        }
        return ret;
    }


}
