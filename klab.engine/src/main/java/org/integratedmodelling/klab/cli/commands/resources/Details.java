package org.integratedmodelling.klab.cli.commands.resources;

import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.klab.Network;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.Urn;
import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.api.auth.INodeIdentity;
import org.integratedmodelling.klab.api.cli.ICommand;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.adapters.IUrnAdapter;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.data.resources.Resource;
import org.integratedmodelling.klab.rest.ResourceReference;
import org.integratedmodelling.klab.utils.JsonUtils;

public class Details implements ICommand {

    @Override
    public Object execute(IServiceCall call, ISession session) {

        String ret = "";
        boolean verbose = call.getParameters().get("verbose", false);

        for (Object urn : call.getParameters().get("arguments", java.util.List.class)) {
            ret += resourceDetails(urn.toString(), verbose, session.getMonitor());
        }
        return ret;
    }

    private String resourceDetails(String urns, boolean verbose, IMonitor monitor) {

        Urn urn = new Urn(urns);
        String ret = urns + ":\n";
        IResource resource = null;
        
        if (urn.isLocal()) {
            resource = Resources.INSTANCE.getLocalResourceCatalog().get(urn.getUrn());
            ret += "  Local resource " + (resource == null ? "NOT available" : "available") + "\n";
            ret += "  Status: " + (Resources.INSTANCE.isResourceOnline(resource) ? "ONLINE" : "OFFLINE") + "\n";
        } else if (urn.isUniversal()) {

            IUrnAdapter adapter = Resources.INSTANCE.getUrnAdapter(urn.getCatalog());
            if (adapter != null) {
                resource = adapter.getResource(urns);
                ret += "  Generic resource served locally: " + (resource == null ? "NOT available" : "available") + "\n";
                ret += "  Status: " + (Resources.INSTANCE.isResourceOnline(resource) ? "ONLINE" : "OFFLINE") + "\n";
            } else {
                INodeIdentity node = Network.INSTANCE.getNodeForResource(urn);
                if (node != null) {

                    ret += "  Available through node " + node.getName() + "\n";
                    
                    ResourceReference reference = node.getClient().get(API.NODE.RESOURCE.RESOLVE_URN, ResourceReference.class,
                            "urn", urn.getUrn());
                    resource = new Resource(reference);
                    ret += "  Remote resource " + (resource == null ? "NOT available" : "available") + "\n";
                    ret += "  Status: " + (Resources.INSTANCE.isResourceOnline(resource) ? "ONLINE" : "OFFLINE") + "\n";
                } else {
                    ret += "  Status: UNRESOLVED (not served by any node)\n";
                }
            }
        }
        
        if (verbose) {
            ret += "----\n" + JsonUtils.printAsJson(((Resource)resource).getReference()) + "\n----\n";
        }

        return ret;
    }
}
