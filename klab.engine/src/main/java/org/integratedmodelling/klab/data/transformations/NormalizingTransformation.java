package org.integratedmodelling.klab.data.transformations;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.Observations;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.model.contextualization.IProcessor;
import org.integratedmodelling.klab.api.model.contextualization.IResolver;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.components.time.extents.Time;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.rest.StateSummary;

public class NormalizingTransformation implements IResolver<IState>, IExpression, IProcessor {

	IState state;

	public NormalizingTransformation() {
	}

	public NormalizingTransformation(IParameters<String> parameters, IContextualizationScope context) {

		IArtifact artifact = context.getArtifact(parameters.get("artifact", String.class));
		if (artifact instanceof IState && (artifact.getType() != Type.NUMBER && artifact.getType() != Type.VALUE)) {
			throw new IllegalArgumentException("normalization operations can only be performed on numeric states");
		}
		this.state = (IState) artifact;
	}

	@Override
	public Type getType() {
		return Type.NUMBER;
	}

	@Override
	public Object eval(IParameters<String> parameters, IContextualizationScope context) throws KlabException {
		return new NormalizingTransformation(parameters, context);
	}

	@Override
	public IState resolve(IState ret, IContextualizationScope context) throws KlabException {
		/*
		 * this is for when the contextualizer is used directly without arguments in a
		 * 'using' clause. In that circumstance, it means 'contextualize myself'.
		 */
		if (state == null) {
			state = context.get("self", IState.class);
		}
		StateSummary summary = Observations.INSTANCE.getStateSummary(state, Time.INITIALIZATION);
		if (!summary.isDegenerate()) {
			for (ILocator locator : context.getScale()) {
				Object value = state.get(locator);
				if (value instanceof Number && !Double.isNaN(((Number) value).doubleValue())) {
					double nval = ((Number) value).doubleValue();
					nval = (nval - summary.getRange().get(0)) / (summary.getRange().get(1) - summary.getRange().get(0));
					ret.set(locator, nval);
				}
			}
		}
		return ret;
	}

}
