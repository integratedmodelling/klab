package org.integratedmodelling.klab.cli.commands.authority;

import java.util.LinkedHashMap;
import java.util.Map;

import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.klab.Authorities;
import org.integratedmodelling.klab.Network;
import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.api.auth.INodeIdentity;
import org.integratedmodelling.klab.api.cli.ICommand;
import org.integratedmodelling.klab.api.knowledge.IAuthority;
import org.integratedmodelling.klab.api.runtime.ISession;

public class Setup implements ICommand {

	@Override
	public Object execute(IServiceCall call, ISession session)  {

		String ret = "";
		String nodeId = call.getParameters().get("node", String.class);
		String authority = null;
		Map<String, String> options = new LinkedHashMap<>();

		if (call.getParameters().get("arguments", java.util.List.class).size() > 0) {
			String key = null;
			for (Object o : call.getParameters().get("arguments", java.util.List.class)) {
				if (authority == null) {
					authority = o.toString();
				} else if (key == null) {
					key = o.toString();
				} else {
					options.put(key, o.toString());
					key = null;
				}
			}
		}

		INodeIdentity node = null;
		if (nodeId != null) {
			node = Network.INSTANCE.getNode(nodeId);
		}
		if (node == null) {
			IAuthority a = Authorities.INSTANCE.getAuthority(authority);
			if (a != null) {
				return "Local authority " + authority + " setup " + (a.setup(options) ? "" : "in") + "successful";
			}
		} else {
			return "Local authority " + authority + " setup "
					+ (node.getClient().onBehalfOf(session.getUser()).post(API.AUTHORITY.SETUP.replace(API.AUTHORITY.P_AUTHORITY, authority), options,
							Boolean.class) ? "" : "in")
					+ "successful";
		}

		return ret;
	}

}
