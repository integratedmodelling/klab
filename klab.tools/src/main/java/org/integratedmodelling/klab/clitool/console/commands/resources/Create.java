package org.integratedmodelling.klab.clitool.console.commands.resources;

import java.io.File;
import java.util.List;

import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.klab.Klab;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.api.knowledge.IProject;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.clitool.api.ICommand;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.utils.Parameters;

public class Create implements ICommand {

	@Override
	public Object execute(IServiceCall call, ISession session) throws Exception {

		String adapter = call.getParameters().get("adapter", String.class);
		String projectId = call.getParameters().get("project", String.class);
		Parameters parameters = new Parameters();
		IProject project = Resources.INSTANCE.getLocalWorkspace().getProject(projectId);

		if (project == null) {
			throw new KlabValidationException("resource::create: project " + projectId + " does not exist in local workspace");
		}

		File file = null;
		for (Object arg : (List<?>) call.getParameters().get("arguments")) {
			String argument = arg.toString();
			if (argument.contains("=")) {
				String[] ss = argument.split("=");
				parameters.put(ss[0], ss[1]);
			} else {
				if (file != null) {
					throw new KlabValidationException("the resource::create command only supports one file argument: " + argument);
				}
				file = Klab.INSTANCE.resolveFile(argument);
				if (!file.exists()) {
					throw new KlabValidationException("resource::create: file " + argument + " can't be read");
				}
			}
		}

		return Resources.INSTANCE.getLocalResource(file, parameters, project, adapter, session.getMonitor());
	}

}
