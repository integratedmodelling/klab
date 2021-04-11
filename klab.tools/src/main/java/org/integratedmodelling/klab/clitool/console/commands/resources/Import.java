package org.integratedmodelling.klab.clitool.console.commands.resources;

import java.io.File;
import java.util.List;

import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.klab.Klab;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.api.cli.ICommand;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.knowledge.IProject;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.utils.MiscUtilities;
import org.integratedmodelling.klab.utils.Parameters;

public class Import implements ICommand {

	@Override
	public Object execute(IServiceCall call, ISession session) {

		String adapter = call.getParameters().get("adapter", String.class);
		String projectId = call.getParameters().get("project", String.class);
		Parameters<String> parameters = new Parameters<>();
		IProject project = Resources.INSTANCE.getLocalWorkspace().getProject(projectId);

		if (project == null) {
			throw new KlabValidationException(
					"resource::create: project " + projectId + " does not exist in local workspace");
		}

		File file = null;
		for (Object arg : (List<?>) call.getParameters().get("arguments")) {
			String argument = arg.toString();
			if (argument.contains("=")) {
				String[] ss = argument.split("\\=");
				if (ss.length != 2) {
					throw new KlabValidationException("arguments following the URN must be in the form key=value");
				}
				parameters.put(ss[0], ss[1]);
			} else {
				if (file != null) {
					throw new KlabValidationException(
							"the resource::create command only supports one file argument: " + argument);
				}
				file = Klab.INSTANCE.resolveFile(argument);
				if (file == null || !file.exists()) {
					throw new KlabValidationException("resource::create: file " + argument + " can't be read");
				}
			}
		}

		String id = parameters.get("id", String.class);
		if (id == null && file != null) {
			id = MiscUtilities.getFileBaseName(file);
		}
		if (id == null) {
			throw new KlabValidationException(
					"resource::create: file is null and the parameters do not contain an 'id' field");
		}

		IResource ret = Resources.INSTANCE.createLocalResource(id, file, parameters, project, adapter, true, false,
				session.getMonitor());
		
		return ret.getUrn() + " [v" + ret.getVersion() + "; adapter=" + ret.getAdapterType() + "]\n   Geometry: " + ret.getGeometry();
	}

}
