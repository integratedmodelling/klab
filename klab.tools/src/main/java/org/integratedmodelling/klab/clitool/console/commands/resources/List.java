package org.integratedmodelling.klab.clitool.console.commands.resources;

import java.util.ArrayList;
import java.util.Collections;

import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.clitool.api.ICommand;
import org.integratedmodelling.klab.utils.JsonUtils;
import org.integratedmodelling.klab.utils.StringUtils;

public class List implements ICommand {

	@Override
	public Object execute(IServiceCall call, ISession session) throws Exception {

		String ret = "";
		boolean verbose = call.getParameters().get("verbose", false);

		ArrayList<String> resourceIds = new ArrayList<>();
		if (call.getParameters().get("arguments", java.util.List.class).size() > 0) {
			for (Object o : call.getParameters().get("arguments", java.util.List.class)) {
				resourceIds.add(o.toString());
			}
			verbose = true;
		} else {
			resourceIds.addAll(Resources.INSTANCE.getLocalResourceCatalog().keySet());
		}

		Collections.sort(resourceIds);
		for (String urn : resourceIds) {
			ret += (ret.isEmpty() ? "" : "\n") + describe(urn, verbose);
		}
		return ret;
	}

	private String describe(String urn, boolean verbose) {
		String ret = urn;
		if (verbose) {
			ret += ":";
			IResource resource = Resources.INSTANCE.getLocalResourceCatalog().get(urn);
			if (resource == null) {
				ret += " Error retrieving resource!";
			} else {
				ret += "\n   " + StringUtils.leftIndent(JsonUtils.printAsJson(resource), 3);
			}
		}
		return ret;
	}

}
