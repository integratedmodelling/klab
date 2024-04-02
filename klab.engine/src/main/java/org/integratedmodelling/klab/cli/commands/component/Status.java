package org.integratedmodelling.klab.cli.commands.component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.klab.Extensions;
import org.integratedmodelling.klab.Network;
import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.api.auth.INodeIdentity;
import org.integratedmodelling.klab.api.cli.ICommand;
import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.data.Metadata;
import org.integratedmodelling.klab.engine.extensions.Component;
import org.integratedmodelling.klab.exceptions.KlabResourceNotFoundException;
import org.integratedmodelling.klab.utils.JsonUtils;
import org.integratedmodelling.klab.utils.MapUtils;

public class Status implements ICommand {

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

                IMetadata data = c.getStatus();

                ret += JsonUtils.printAsJson(((Metadata) data).getData());

            } else {

                Map<?, ?> data = node.getClient().onBehalfOf(session.getUser()).get(API.NODE.ADMIN.COMPONENT_GET_STATUS, Map.class, API.NODE.ADMIN.P_COMPONENT,
                        component);

                ret += JsonUtils.printAsJson(data);

            }
        }

        return ret;
    }

}
