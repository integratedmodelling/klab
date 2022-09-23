package org.integratedmodelling.klab.cli.commands.resources;

import java.util.ArrayList;
import java.util.Collections;

import org.h2.util.StringUtils;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.klab.Network;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.api.auth.INodeIdentity;
import org.integratedmodelling.klab.api.cli.ICommand;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.data.resources.Resource;
import org.integratedmodelling.klab.rest.ResourceReference;
import org.integratedmodelling.klab.utils.JsonUtils;
import org.integratedmodelling.klab.utils.StringUtil;

public class List implements ICommand {

	@Override
	public Object execute(IServiceCall call, ISession session) {

		String ret = "";
		boolean verbose = call.getParameters().get("verbose", false);
		boolean online = call.getParameters().get("online", false);
		boolean forceUpdate = call.getParameters().get("force", false);
		String nodeId = (String)call.getParameters().get("node");

		ArrayList<String> resourceIds = new ArrayList<>();
		if (call.getParameters().get("arguments", java.util.List.class).size() > 0) {
			for (Object o : call.getParameters().get("arguments", java.util.List.class)) {
				resourceIds.add(o.toString());
			}
			verbose = true;
		} else if (nodeId == null) {
			resourceIds.addAll(Resources.INSTANCE.getLocalResourceCatalog().keySet());
		}
		if (nodeId != null) {
			return describeRemote(nodeId, verbose, resourceIds);
		}

		Collections.sort(resourceIds);
		for (String urn : resourceIds) {
			ret += (ret.isEmpty() ? "" : "\n") + describe(urn, verbose, online, forceUpdate);
		}
		return ret;
	}

	private String describeRemote(String nodeId, boolean verbose, java.util.List<String> resourceIds) {
		StringBuilder ret = new StringBuilder(10000);
		INodeIdentity node = Network.INSTANCE.getNode(nodeId);
		if (node != null && node.isOnline()) {

			if (resourceIds.isEmpty()) {
				int n = 0;
				for (ResourceReference resource : node.getClient().get(API.NODE.RESOURCE.LIST,
						ResourceReference[].class)) {
					ret.append(resource.getUrn() + (verbose ? ":\n" : "\n"));
					if (verbose) {
						StringUtils.indent(ret, JsonUtils.printAsJson(resource), 4, true);
					}
					n++;
				}
				ret.append("\n" + n + " resources retrieved\n");
			} else {
				for (String urn : resourceIds) {
					ResourceReference resource = node.getClient().get(API.NODE.RESOURCE.RESOLVE_URN, ResourceReference.class, "urn", urn);
					ret.append(resource.getUrn() + (verbose ? ":\n" : "\n"));
					if (verbose) {
						StringUtils.indent(ret, JsonUtils.printAsJson(resource), 4, true);
					}
				}
			}
		} else {
			return "Node " + nodeId + (node == null ? " does not exist" : " is offline");
		}
		return ret.toString();
	}

	private String describe(String urn, boolean verbose, boolean online, boolean forceUpdate) {
		String ret = urn;
		IResource resource = null;
		if (online) {
			resource = Resources.INSTANCE.resolveResource(urn);
			ret += " [" + (Resources.INSTANCE.isResourceOnline(resource, forceUpdate) ? "ONLINE" : "OFFLINE") + "]";
		}
		if (verbose) {
			ret += ":";
			if (resource == null) {
				resource = Resources.INSTANCE.resolveResource(urn);
			}
			if (resource == null) {
				ret += " Error retrieving resource!";
			} else {
				ret += "\n   " + StringUtil.leftIndent(JsonUtils.printAsJson(((Resource) resource).getReference()), 3);
			}
		}
		return ret;
	}

}
