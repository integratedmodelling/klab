package org.integratedmodelling.klab.cli.commands.component;

import java.util.ArrayList;
import java.util.List;

import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.klab.Extensions;
import org.integratedmodelling.klab.Network;
import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.api.auth.INodeIdentity;
import org.integratedmodelling.klab.api.cli.ICommand;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.api.runtime.ITicket;
import org.integratedmodelling.klab.api.runtime.ITicket.Status;
import org.integratedmodelling.klab.engine.extensions.Component;
import org.integratedmodelling.klab.exceptions.KlabResourceNotFoundException;
import org.integratedmodelling.klab.rest.TicketResponse;
import org.integratedmodelling.klab.rest.TicketResponse.Ticket;

public class Setup implements ICommand {

    @Override
    public Object execute(IServiceCall call, ISession session) {

        String ret = "";
        String nodeId = call.getParameters().get("node", String.class);
        List<String> components = new ArrayList<>();
        if (call.getParameters().get("arguments", java.util.List.class).size() > 0) {
            for (Object o : call.getParameters().get("arguments", java.util.List.class)) {
                components.add(o.toString());
            }
        }

        INodeIdentity node = null;
        if (nodeId != null) {
            node = Network.INSTANCE.getNode(nodeId);
        }

        for (String component : components) {

            if (node == null) {

                Component c = Extensions.INSTANCE.getComponent(component);
                if (c == null) {
                    throw new KlabResourceNotFoundException("component " + component + " is not installed in engine");
                }

                ITicket ticket = c.setup();

                if (ticket.getStatus() == Status.ERROR) {
                    ret += "\n   Component " + component + " setup failed: component reported '" + ticket.getStatusMessage()
                            + "'";
                } else if (ticket.getStatus() == Status.RESOLVED) {
                    ret += "\n   Component " + component + " setup finished";
                } else {
                    ret += "\n   Component " + component + " setup requested: follow ticket " + ticket.getId();
                }

            } else {

                Ticket ticket = node.getClient().onBehalfOf(session.getUser()).get(API.NODE.ADMIN.COMPONENT_SETUP,
                        TicketResponse.Ticket.class, API.NODE.ADMIN.P_COMPONENT, component);

                if (ticket.getStatus() == Status.ERROR) {
                    ret += "\n   Component " + component + " setup failed: node reported '" + ticket.getStatusMessage() + "'";
                } else if (ticket.getStatus() == Status.RESOLVED) {
                    ret += "\n   Component " + component + " setup finished";
                } else {
                    ret += "\n   Component " + component + " setup requested: follow ticket " + ticket.getId();
                }
            }
        }

        return ret;
    }

}
