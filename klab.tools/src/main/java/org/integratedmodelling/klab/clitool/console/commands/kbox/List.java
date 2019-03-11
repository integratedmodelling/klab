package org.integratedmodelling.klab.clitool.console.commands.kbox;

import java.util.ArrayList;

import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.klab.Models;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.clitool.api.ICommand;
import org.integratedmodelling.klab.data.resources.Resource;
import org.integratedmodelling.klab.rest.ModelReference;
import org.integratedmodelling.klab.utils.JsonUtils;
import org.integratedmodelling.klab.utils.StringUtils;

public class List implements ICommand {

	@Override
	public Object execute(IServiceCall call, ISession session) throws Exception {

		String ret = "";
		boolean verbose = call.getParameters().get("verbose", false);
		for (ModelReference model : Models.INSTANCE.listModels(true)) {
			ret += (ret.isEmpty() ? "" : "\n") + describe(model, verbose);
		}
		return ret;
	}

	private String describe(ModelReference urn, boolean verbose) {
		String ret = urn.getUrn();
		if (verbose) {
			ret += ":";
			IResource resource = Resources.INSTANCE.getLocalResourceCatalog().get(urn);
			if (resource == null) {
				ret += " Error retrieving resource!";
			} else {
				ret += "\n   " + StringUtils.leftIndent(JsonUtils.printAsJson(((Resource)resource).getReference()), 3);
			}
		} else {
		    ret += "\n";
		}
		return ret;
	}

}
