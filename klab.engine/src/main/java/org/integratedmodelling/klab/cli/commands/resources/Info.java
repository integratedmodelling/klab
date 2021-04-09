package org.integratedmodelling.klab.cli.commands.resources;

import java.util.List;
import java.util.Map;

import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.klab.Network;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.Urn;
import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.api.auth.INodeIdentity;
import org.integratedmodelling.klab.api.cli.ICommand;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.scale.Scale;
import org.integratedmodelling.klab.utils.JsonUtils;

public class Info implements ICommand {

	@Override
	public Object execute(IServiceCall call, ISession session) {

		String ret = "";
		String nodeId = (String) call.getParameters().get("node");

		for (Object urn : call.getParameters().get("arguments", java.util.List.class)) {
			Urn u = new Urn(urn.toString());
			String node = nodeId;
			if (node == null && !(u.isLocal() || u.isUniversal())) {
				node = u.getNodeName();
			}
			ret += (node == null ? resourceInfo(Resources.INSTANCE.getLocalResourceCatalog().get(urn))
					: remoteResourceInfo(node, urn)) + "\n";
		}
		return ret;
	}

	private String remoteResourceInfo(String nodeId, Object urn) {
		StringBuffer ret = new StringBuffer(10000);
		INodeIdentity node = Network.INSTANCE.getNode(nodeId);
		if (node != null && node.isOnline()) {
			Map<?,?> info = node.getClient().get(API.NODE.RESOURCE.INFO, Map.class, "urn", urn);
			ret.append(JsonUtils.printAsJson(info));
		} else {
			return "Node " + nodeId + " is " + (node == null ? "unknown" : "offline");
		}
		return ret.toString();
	}

	private String resourceInfo(IResource resource) {

		String ret = "";

		/*
		 * Build a sample observation for the resource scale
		 */
		Scale scale = Scale.create(resource.getGeometry());
		if (!scale.isEmpty()) {
			ret += "observe " + (scale.getSpace() != null ? "earth:Region" : "im:Thing") + " named test\n   over";
			List<IServiceCall> scaleSpecs = scale.getKimSpecification();
			for (int i = 0; i < scaleSpecs.size(); i++) {
				ret += " " + scaleSpecs.get(i).getSourceCode()
						+ ((i < scaleSpecs.size() - 1) ? (",\n" + "      ") : ";");
			}
		}

		/*
		 * TODO more
		 */

		return ret;
	}
}
