package org.integratedmodelling.klab.clitool.console.commands.visualization;

import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.clitool.api.ICommand;
import org.integratedmodelling.klab.utils.graph.Graphs;

public class ShowDependencies implements ICommand {

	@Override
	public Object execute(IServiceCall call, ISession session) throws Exception {
		Graphs.showDependencies();
		return null;
	}

}
