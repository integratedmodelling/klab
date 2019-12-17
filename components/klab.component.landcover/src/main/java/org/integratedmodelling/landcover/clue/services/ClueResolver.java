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
import org.integratedmodelling.klab.scale.AbstractExtent;
import org.integratedmodelling.klab.utils.Parameters;
import org.integratedmodelling.landcover.clue.KlabCLUEParameters;

import nl.alterra.shared.utils.log.Log;
import nl.wur.iclue.model.CLUEModel;

/**
 * Needed improvements:
 * 
 * 1. Determine the max level <n> at which classes are mentioned in transition,
 * use that level as the aggregation level for the whole model, and match using
 * the reasoner. 
 * 2. When a lower-level class changes, allow a function to determine the actual class it changes 
 *    into.
 * 
 * 3. Enable annotation-driven links for elasticity, demand AND suitability (all for one or
 *    more LCTs).
 * 
 * @author ferdinando.villa
 *
 */
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

		/*
		 * index of timeslice. Should start at 1 at the first call.
		 */
		int targetTime = (int) ((AbstractExtent)context.getScale().getTime()).getLocatedOffset();
		
		if (this.clue == null) {

			Log.setMonitor(context.getMonitor());
			
			/*
			 * if a duration is required as output, we have the storage for the age layer;
			 * otherwise we will create it as storage inside the parameters.
			 */
			IState ageState = null;

			/*
			 * First time call: set the target time to 1 (from 0)
			 */
			this.clue = new CLUEModel(new KlabCLUEParameters(parameters, (IRuntimeScope) context, ret, ageState),
					context.getMonitor());
			
			this.clue.initializeSuitabilities();
		}

		/*
		 * run a cycle, update process data. TODO must set the beginning and end for this
		 * timestep (0-1 after configuration).
		 */
		this.clue.run(targetTime - 1, targetTime);

		return ret;
	}

	@Override
	public Object eval(IParameters<String> parameters, IContextualizationScope context) {
		return new ClueResolver(parameters, context);
	}

}
