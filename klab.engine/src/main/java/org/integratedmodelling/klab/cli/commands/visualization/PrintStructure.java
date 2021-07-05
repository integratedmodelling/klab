package org.integratedmodelling.klab.cli.commands.visualization;

import java.util.List;

import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.klab.api.cli.ICommand;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.components.runtime.observations.Observation;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.utils.StringUtil;

public class PrintStructure implements ICommand {

	@Override
	public Object execute(IServiceCall call, ISession session) {

		IObservation obs = null;
		if (call.getParameters().get("arguments", List.class).size() == 1) {
			obs = session.getObservation(call.getParameters().get("arguments", List.class).get(0).toString());
		}
		if (obs == null) {
			throw new KlabValidationException("show::structure requires a valid observation ID as argument");
		}

		return "Session " + session.getId() + ":\n"
				+ printStructure(obs, 0, call.getParameters().get("artifacts", false));
	}

	private String printStructure(IArtifact obs, int level, boolean artifacts) {

		IRuntimeScope context = ((Observation) obs).getScope();

		String ret = StringUtil.repeat(' ', level) + obs;
		for (IArtifact child : (artifacts ? context.getChildArtifactsOf(obs)
				: context.getChildrenOf((IObservation) obs))) {
			ret += "\n" + printStructure(child, level + 3, artifacts);
		}

		return ret;
	}

}
