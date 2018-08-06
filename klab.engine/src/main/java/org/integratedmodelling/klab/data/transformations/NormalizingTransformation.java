package org.integratedmodelling.klab.data.transformations;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.Observations;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.model.contextualization.IResolver;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.IComputationContext;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.rest.StateSummary;

public class NormalizingTransformation implements IResolver<IState>, IExpression {

	IState state;

	public NormalizingTransformation() {}
	
	public NormalizingTransformation(IParameters<String> parameters, IComputationContext context) {
		
		if (!(context.getTargetArtifact() instanceof IState) || context.getTargetArtifact().getType() != Type.NUMBER) {
			throw new IllegalArgumentException("normalization operations can only be performed on numeric states");
		}
		this.state = (IState) context.getTargetArtifact();
	}

	@Override
	public IGeometry getGeometry() {
		return state.getScale();
	}

	@Override
	public Type getType() {
		return Type.NUMBER;
	}

	@Override
	public Object eval(IParameters<String> parameters, IComputationContext context) throws KlabException {
		return new NormalizingTransformation(parameters, context);
	}

	@Override
	public IState resolve(IState ret, IComputationContext context) throws KlabException {
		StateSummary summary = Observations.INSTANCE.getStateSummary(ret, context.getScale());
		if (!summary.isDegenerate()) {
			for (ILocator locator : context.getScale()) {
				Object value = ret.get(locator);
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
