package org.integratedmodelling.klab.clitool.console.commands.ticket;

import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.klab.Klab;
import org.integratedmodelling.klab.Network;
import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.api.auth.INodeIdentity;
import org.integratedmodelling.klab.api.cli.ICommand;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.api.runtime.ITicket;
import org.integratedmodelling.klab.common.monitoring.TicketManager;
import org.integratedmodelling.klab.rest.TicketResponse;
import org.integratedmodelling.klab.utils.JsonUtils;

public class List implements ICommand {

	@Override
	public Object execute(IServiceCall call, ISession session) {

		String ret = "";
		String nodeId = call.getParameters().get("node", String.class);

		INodeIdentity node = null;
		if (nodeId != null) {
			node = Network.INSTANCE.getNode(nodeId);
		}

		if (node == null) {
			for (ITicket ticket : Klab.INSTANCE.getTicketManager().getTickets()) {
				ret += "\n" + JsonUtils.printAsJson(TicketManager.encode(ticket)) + "\n";
			}
		} else {
			for (TicketResponse.Ticket ticket : node.getClient().get(API.TICKET.LIST, TicketResponse.Ticket[].class)) {
				ret += "\n" + JsonUtils.printAsJson(ticket) + "\n";
			}
		}

		return ret;
	}

}
