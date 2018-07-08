package org.integratedmodelling.klab.clitool.console.commands.project;

import java.util.List;

import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.clitool.api.ICommand;
import org.integratedmodelling.klab.exceptions.KlabInternalErrorException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;

public class Create implements ICommand {

    @Override
    public Object execute(IServiceCall call, ISession session) throws Exception {

        if (((List<?>) call.getParameters().get("arguments")).size() < 1) {
            throw new KlabValidationException("project::create requires one or more project names as arguments");
        }

        String ret = "";
        for (Object projectId : ((List<?>) call.getParameters().get("arguments"))) {
            if (Resources.INSTANCE.getLocalWorkspace().createProject(projectId.toString()) == null) {
                throw new KlabInternalErrorException("unknown error creating project " + projectId);
            }
            ret += (ret.isEmpty() ? "" : "\n") + "Empty project '" + projectId + "' created in local workspace";
        }

        return ret;
    }

}
