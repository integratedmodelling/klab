package org.integratedmodelling.klab.cli.commands.ticket;

import java.util.ArrayList;

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

public class Status implements ICommand {

	@Override
	public Object execute(IServiceCall call, ISession session) {

		String ret = "";
		String nodeId = call.getParameters().get("node", String.class);
		java.util.List<String> tickets = new ArrayList<>();
		if (call.getParameters().get("arguments", java.util.List.class).size() > 0) {
			for (Object o : call.getParameters().get("arguments", java.util.List.class)) {
				tickets.add(o.toString());
			}
		}

		INodeIdentity node = null;
		if (nodeId != null) {
			node = Network.INSTANCE.getNode(nodeId);
		}

		for (String ticketId : tickets) {

			if (node == null) {

				ITicket ticket = Klab.INSTANCE.getTicketManager().getTicket(ticketId);
				
				if (ticket != null) {
					ret += "\n" + JsonUtils.printAsJson(TicketManager.encode(ticket));
				} else {
					ret += "\n   Ticket " + ticketId + " not found.";
				}
				
			} else {

				TicketResponse.Ticket data = node.getClient().get(API.TICKET.INFO, TicketResponse.Ticket.class,
						API.NODE.ADMIN.P_COMPONENT, ticketId);
				
				ret += "\n" + JsonUtils.printAsJson(data);
				
			}
		}

		return ret;
	}

}
