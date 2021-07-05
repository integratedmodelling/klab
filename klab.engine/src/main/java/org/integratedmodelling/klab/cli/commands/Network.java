package org.integratedmodelling.klab.cli.commands;

import java.util.List;

import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.klab.api.auth.INetworkSessionIdentity;
import org.integratedmodelling.klab.api.auth.INodeIdentity;
import org.integratedmodelling.klab.api.cli.ICommand;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.joda.time.Duration;
import org.joda.time.format.PeriodFormat;

public class Network implements ICommand {

    @Override
    public Object execute(IServiceCall call, ISession session) {

        if (((List<?>) call.getParameters().get("arguments")).size() > 0) {
            String arg = ((List<?>) call.getParameters().get("arguments")).get(0).toString();
            /*
             * if ("on".equals(arg)) { if (!CliRuntime.INSTANCE .startNetwork(
             * call.getParameters().get("browser", false) ? () -> BrowserUtils
             * .startBrowser("http://localhost:8283/modeler/ui/viewer?session=" +
             * CliRuntime.INSTANCE.getSession().getId()) : null)) {
             * session.getMonitor().error("Please wait until engine is active"); } } else if
             * ("off".equals(arg)) { CliRuntime.INSTANCE.stopNetwork(); } else
             */if ("nodes".equals(arg)) {
                INetworkSessionIdentity network = session.getParentIdentity(INetworkSessionIdentity.class);
                String ret = "";
                if (network != null) {
                    for (INodeIdentity node : network.getNodes()) {
                        Duration uptime = new Duration(node.getUptime());
                        ret += (ret.isEmpty() ? "" : "\n") + "   " + node.getName() + " at " + node.getUrls()
                                + node.getAdapters().toString() + " (up " + PeriodFormat.getDefault().print(uptime.toPeriod()) + ")";
                    }
                }
                return ret;
            } else {
                session.getMonitor().error("Network services may only be turned on or off");
            }
        }
        return null;
    }

}
