package org.integratedmodelling.klab.clitool.console.commands.resources;

import java.util.ArrayList;
import java.util.List;

import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.api.cli.ICommand;
import org.integratedmodelling.klab.api.knowledge.IProject;
import org.integratedmodelling.klab.api.runtime.ISession;

public class Move implements ICommand {

	@Override
	public Object execute(IServiceCall call, ISession session) throws Exception {

		List<String> projectNames = new ArrayList<>();
		List<String> urns = new ArrayList<>();
		String ret = "";
		
		for (Object arg : call.getParameters().get("arguments", java.util.List.class)) {
			if (Resources.INSTANCE.getProject(arg.toString()) != null) {
				projectNames.add(arg.toString());
			} else if (Resources.INSTANCE.getLocalResourceCatalog().containsKey(arg.toString())) {
				urns.add(arg.toString());
			} else {
				throw new IllegalArgumentException("argument " + arg + "is neither a project or a resource URN");
			}
		}
		
		if (projectNames.size() > 1 && urns.size() > 0) {
			throw new IllegalArgumentException("more than one projects can only be mentioned if no URNs are specified");
		}
		if (projectNames.size() < 2 && urns.size() == 0) {
			throw new IllegalArgumentException("if no URNs are specified two projects must be mentioned");
		}
		
		if (projectNames.size() == 2) {
			IProject destination = Resources.INSTANCE.getProject(projectNames.get(1));
			for (String urn : Resources.INSTANCE.getProject(projectNames.get(0)).getLocalResourceUrns()) {
				Resources.INSTANCE.moveLocalResource(Resources.INSTANCE.getLocalResourceCatalog().get(urn), destination);
			}
		} else if (projectNames.size() == 1 && urns.size() > 0) {
			IProject destination = Resources.INSTANCE.getProject(projectNames.get(0));
			for (String urn : urns) {
				Resources.INSTANCE.moveLocalResource(Resources.INSTANCE.getLocalResourceCatalog().get(urn), destination);
			}
		} else {
			throw new IllegalArgumentException("illegal call: either specify two projects or one project and 1+ URNs");
		}
		
		return ret;
	}
}
