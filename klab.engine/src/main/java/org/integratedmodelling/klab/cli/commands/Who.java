package org.integratedmodelling.klab.cli.commands;

import java.util.Map;

import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.klab.Network;
import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.api.auth.INodeIdentity;
import org.integratedmodelling.klab.api.cli.ICommand;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.utils.JsonUtils;

public class Who implements ICommand {

	@Override
	public Object execute(IServiceCall call, ISession session) throws Exception {

		String ret = "";
		String nodeId = call.getParameters().containsKey("node") ? call.getParameters().get("node").toString() : null;

		if (nodeId != null) {
			INodeIdentity node = Network.INSTANCE.getNode(nodeId);
			if (node != null && node.isOnline()) {
				Map<?,?> result = node.getClient().get(API.NODE.WHO, Map.class);	
				ret = JsonUtils.printAsJson(result);
			} else {
				ret = "Node " + nodeId + " is " + (node == null ? "unknown" : "offline");
			}
		} else {
			// TODO
		}

		return ret;
	}

}
