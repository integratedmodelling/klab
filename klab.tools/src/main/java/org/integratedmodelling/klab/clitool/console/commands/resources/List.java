package org.integratedmodelling.klab.clitool.console.commands.resources;

import java.util.ArrayList;
import java.util.Collections;

import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.api.cli.ICommand;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.data.resources.Resource;
import org.integratedmodelling.klab.utils.JsonUtils;
import org.integratedmodelling.klab.utils.StringUtil;

public class List implements ICommand {

    @Override
    public Object execute(IServiceCall call, ISession session) throws Exception {

        String ret = "";
        boolean verbose = call.getParameters().get("verbose", false);
        boolean online = call.getParameters().get("online", false);
        boolean forceUpdate = call.getParameters().get("force", false);
        String nodeId = call.getParameters().get("node", (String)null);

        ArrayList<String> resourceIds = new ArrayList<>();
        if (call.getParameters().get("arguments", java.util.List.class).size() > 0) {
            for (Object o : call.getParameters().get("arguments", java.util.List.class)) {
                resourceIds.add(o.toString());
            }
            verbose = true;
        } else {
            resourceIds.addAll(Resources.INSTANCE.getLocalResourceCatalog().keySet());
        }

        Collections.sort(resourceIds);
        for (String urn : resourceIds) {
            ret += (ret.isEmpty() ? "" : "\n") + describe(urn, verbose, online, forceUpdate);
        }
        return ret;
    }

    private String describe(String urn, boolean verbose, boolean online, boolean forceUpdate) {
        String ret = urn;
        IResource resource = null;
        if (online) {
            resource = Resources.INSTANCE.getLocalResourceCatalog().get(urn);
            ret += " [" + (Resources.INSTANCE.isResourceOnline(resource, forceUpdate) ? "ONLINE" : "OFFLINE")
                    + "]";
        }
        if (verbose) {
            ret += ":";
            if (resource == null) {
                resource = Resources.INSTANCE.getLocalResourceCatalog().get(urn);
            }
            if (resource == null) {
                ret += " Error retrieving resource!";
            } else {
                ret += "\n   " + StringUtil
                        .leftIndent(JsonUtils.printAsJson(((Resource) resource).getReference()), 3);
            }
        }
        return ret;
    }

}
