package org.integratedmodelling.klab.cli.commands.resources;

import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.klab.Network;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.api.auth.INodeIdentity;
import org.integratedmodelling.klab.api.cli.ICommand;
import org.integratedmodelling.klab.api.runtime.ISession;

public class Clear implements ICommand {

	@Override
	public Object execute(IServiceCall call, ISession session) {

		String nodeId = (String)call.getParameters().get("node");

		if (nodeId == null) {
			if (call.getParameters().get("arguments", java.util.List.class).size() > 0) {
				for (Object urn : call.getParameters().get("arguments", java.util.List.class)) {
					Resources.INSTANCE.getLocalResourceCatalog().remove(urn.toString());
				}
			} else {
				Resources.INSTANCE.getLocalResourceCatalog().clear();
			}
		} else {
			INodeIdentity node = Network.INSTANCE.getNode(nodeId);
			if (node == null || !node.isOnline()) {
				return "Node " + nodeId + (node == null ? "does not exist" : "is offline");
			}
			int n = 0, t = 0;
			String ret = "";
			for (Object urn : call.getParameters().get("arguments", java.util.List.class)) {
				Object o = node.getClient().onBehalfOf(session.getUser()).remove(API.NODE.RESOURCE.DELETE_URN, API.P_URN, urn);
				ret += "\n" + urn;
				if (o instanceof Boolean && (Boolean)o) {
					n++;
					ret += " deleted on " + nodeId;
				} else {
					ret += " not found on " + nodeId;
				}
				t++;
			}
			return ret + "\n\n" + n + "/" + t + " resources deleted";
		}
		return null;
	}
}
