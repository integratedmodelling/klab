package org.integratedmodelling.klab.cli.commands;

import java.io.File;
import java.util.List;

import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.Network;
import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.api.auth.INodeIdentity;
import org.integratedmodelling.klab.api.cli.ICommand;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.api.services.IConfigurationService;
import org.integratedmodelling.klab.utils.FileUtils;

public class Log implements ICommand {

	@Override
	public Object execute(IServiceCall call, ISession session) {

		String ret = "";
		String nodeId = call.getParameters().containsKey("node") ? call.getParameters().get("node").toString() : null;
		int lines = 100;
		if (((List<?>) call.getParameters().get("arguments")).size() > 0) {
			lines = Integer.parseInt(((List<?>) call.getParameters().get("arguments")).get(0).toString());
		}

		if (nodeId != null) {
			INodeIdentity node = Network.INSTANCE.getNode(nodeId);
			if (node != null && node.isOnline()) {
				ret = node.getClient().get(API.NODE.ADMIN.GET_LOG, String.class, API.NODE.ADMIN.P_LINES, lines + "");
			} else {
				ret = "Node " + nodeId + " is " + (node == null ? "unknown" : "offline");
			}
		} else {
			ret = "No logs available or configured";
			File file = new File(Configuration.INSTANCE.getProperty(IConfigurationService.KLAB_LOG_FILE,
					Configuration.INSTANCE.getDataPath("logs") + File.separator + "klab.log"));
			if (file.canRead()) {
				StringBuffer sbuf = new StringBuffer(4096);
				for (String line : FileUtils.tailFile(file, lines)) {
					sbuf.append(line);
					sbuf.append("\n");
				}
				ret = sbuf.toString();
			}
		}

		return ret;
	}

}
