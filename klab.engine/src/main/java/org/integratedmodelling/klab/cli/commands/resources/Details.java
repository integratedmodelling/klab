package org.integratedmodelling.klab.cli.commands.resources;

import java.io.ByteArrayOutputStream;
import java.util.Map;

import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.klab.Network;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.Urn;
import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.api.auth.INodeIdentity;
import org.integratedmodelling.klab.api.cli.ICommand;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.adapters.IResourceAdapter;
import org.integratedmodelling.klab.api.data.adapters.IResourceEncoder;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.utils.JsonUtils;

public class Details implements ICommand {

	@Override
	public Object execute(IServiceCall call, ISession session) {

		String ret = "";
		String nodeId = (String) call.getParameters().get("node");
		boolean verbose = call.getParameters().get("verbose", false);

		for (Object urn : call.getParameters().get("arguments", java.util.List.class)) {
			Urn u = new Urn(urn.toString());
			String node = nodeId;
			if (node == null && !(u.isLocal() || u.isUniversal())) {
				node = u.getNodeName();
			}
			ret += (node == null
					? resourceDetails(Resources.INSTANCE.getLocalResourceCatalog().get(urn), verbose,
							session.getMonitor())
					: remoteResourceDetails(node, urn)) + "\n";
		}
		return ret;
	}

	/**
	 * FIXME TODO this is a copy of Info
	 * 
	 * @param nodeId
	 * @param urn
	 * @return
	 */
	private String remoteResourceDetails(String nodeId, Object urn) {
		StringBuffer ret = new StringBuffer(10000);
		INodeIdentity node = Network.INSTANCE.getNode(nodeId);
		if (node != null && node.isOnline()) {
			Map<?, ?> info = node.getClient().get(API.NODE.RESOURCE.INFO, Map.class, "urn", urn);
			ret.append(JsonUtils.printAsJson(info));
		} else {
			return "Node " + nodeId + " is " + (node == null ? "unknown" : "offline");
		}
		return ret.toString();
	}

	private String resourceDetails(IResource resource, boolean verbose, IMonitor monitor) {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		IResourceAdapter adapter = Resources.INSTANCE.getResourceAdapter(resource.getAdapterType());
		IResourceEncoder encoder = adapter.getEncoder();
		encoder.listDetail(resource, output, verbose, monitor);
		return output.toString();
	}
}
