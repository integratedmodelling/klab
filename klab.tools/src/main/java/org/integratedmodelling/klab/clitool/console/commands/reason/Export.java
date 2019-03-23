package org.integratedmodelling.klab.clitool.console.commands.reason;

import java.io.File;
import java.util.List;

import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.Namespaces;
import org.integratedmodelling.klab.api.model.INamespace;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.clitool.api.ICommand;
import org.integratedmodelling.klab.exceptions.KlabResourceNotFoundException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;

public class Export implements ICommand {

    @Override
    public Object execute(IServiceCall call, ISession session) throws KlabValidationException {

        boolean writeImported = true;

        String ret = "";
        for (Object o : call.getParameters().get("arguments", List.class)) {
            INamespace namespace = Namespaces.INSTANCE.getNamespace(o.toString());
            if (namespace != null) {
                File output = Configuration.INSTANCE.getExportFile(namespace.getId() + ".owl");
                namespace.getOntology().write(output, writeImported);
                ret += "Namespace " + namespace.getName() + " written to " + output
                        + (writeImported ? " along with all dependencies" : "") + "\n";
            } else {
                throw new KlabResourceNotFoundException("namespace " + o + " does not exist");
            }
        }
        return ret;
    }

}
