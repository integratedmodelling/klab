package org.integratedmodelling.landcover.clue.services;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.model.contextualization.IResolver;
import org.integratedmodelling.klab.api.observations.IProcess;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.utils.Parameters;
import org.integratedmodelling.landcover.clue.KlabCLUEParameters;

import nl.wur.iclue.model.CLUEModel;

public class ClueResolver implements IResolver<IProcess>, IExpression {

	private CLUEModel clue = null;
	private IParameters<String> parameters = Parameters.create();

	public ClueResolver() {
	}

	public ClueResolver(IParameters<String> parameters, IContextualizationScope context) {
		/*
		 * TODO validate parameters one by one, log info
		 */
		this.parameters.putAll(parameters);
	}

	@Override
	public Type getType() {
		return Type.CONCEPT;
	}

	@Override
	public IProcess resolve(IProcess ret, IContextualizationScope context) throws KlabException {

		if (this.clue == null) {

			/*
			 * if a duration is required as output, we have the storage for the age layer;
			 * otherwise we will create it as storage inside the parameters.
			 */
			IState ageState = null;

			// first call; create CLUE model.
			this.clue = new CLUEModel(new KlabCLUEParameters(parameters, (IRuntimeScope) context, ret, ageState),
					context.getMonitor());
		}

		/*
		 * set the time to the current
		 */

		return ret;
	}

	@Override
	public Object eval(IParameters<String> parameters, IContextualizationScope context) {
		return new ClueResolver(parameters, context);
	}

}
