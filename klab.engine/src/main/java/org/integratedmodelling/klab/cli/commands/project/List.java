package org.integratedmodelling.klab.cli.commands.project;

import java.util.Collection;

import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.api.cli.ICommand;
import org.integratedmodelling.klab.api.knowledge.IProject;
import org.integratedmodelling.klab.api.model.INamespace;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.utils.Parameters;

public class List implements ICommand {

    @Override
    public Object execute(IServiceCall call, ISession session) {
        Parameters<String> parameters = call.getParameters();
        boolean verbose = parameters.get("verbose", false);

        String ret = "";
        for(IProject project : Resources.INSTANCE.getProjects()) {
            ret += (ret.isEmpty() ? "   " : "\n   ") + project.getName();

            if (verbose) {
                ret += "\n";
                java.util.List<INamespace> namespaces = project.getNamespaces();
                if (!namespaces.isEmpty()) {
                    ret += "     Namespaces:\n";
                    for(INamespace iNamespace : namespaces) {
                        ret += "       " + iNamespace.getName() + "\n";
                    }
                }
                Collection<String> localResourceUrns = project.getLocalResourceUrns();
                if (!localResourceUrns.isEmpty()) {
                    ret += "     Local resource URNs:\n";
                    for(String localResourceUrn : localResourceUrns) {
                        ret += "       " + localResourceUrn + "\n";
                    }
                }
            }
        }
        return "Local workspace (" + Resources.INSTANCE.getLocalWorkspace().getRoot() + "):\n" + ret;
    }

}
