package org.integratedmodelling.klab.clitool.console.commands.visualization;

import java.util.List;

import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.clitool.api.ICommand;
import org.integratedmodelling.klab.components.runtime.observations.Observation;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.utils.StringUtil;

public class PrintStructure implements ICommand {

	@Override
	public Object execute(IServiceCall call, ISession session) throws Exception {

		IObservation obs = null;
		if (call.getParameters().get("arguments", List.class).size() == 1) {
			obs = session.getObservation(call.getParameters().get("arguments", List.class).get(0).toString());
		}
		if (obs == null) {
			throw new KlabValidationException("show::structure requires a valid observation ID as argument");
		}
		
		return  "Session " + session.getId() + ":\n" + printStructure(obs, 0);
	}

	private String printStructure(IObservation obs, int level) {

		IRuntimeScope context = ((Observation)obs).getRuntimeContext();
		
		String ret = StringUtil.repeat(' ', level) + obs;
		for (IObservation child : context.getChildrenOf(obs)) {
			ret += "\n" + printStructure(child, level + 3);
		}
		
		return ret;
	}

}
