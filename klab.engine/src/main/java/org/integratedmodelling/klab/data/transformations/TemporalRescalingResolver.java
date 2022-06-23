package org.integratedmodelling.klab.data.transformations;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.Observations;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.model.contextualization.IProcessor;
import org.integratedmodelling.klab.api.model.contextualization.IResolver;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.components.runtime.contextualizers.AbstractContextualizer;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.rest.StateSummary;
import org.integratedmodelling.klab.utils.Parameters;

public class TemporalRescalingResolver extends AbstractContextualizer implements IResolver<IState>, IExpression, IProcessor {


	public TemporalRescalingResolver() {
	}

	public TemporalRescalingResolver(IParameters<String> parameters, IContextualizationScope context) {
	}

	@Override
	public Type getType() {
		return Type.EXTENT;
	}

	@Override
	public Object eval(IContextualizationScope context, Object...parameters) throws KlabException {
		return new TemporalRescalingResolver(Parameters.create(parameters), context);
	}

	@Override
	public IState resolve(IState ret, IContextualizationScope scope) throws KlabException {
		/*
		 * this is for when the contextualizer is used directly without arguments in a
		 * 'using' clause. In that circumstance, it means 'contextualize myself'.
		 */
        IState target = scope.get("self", IState.class);
        if (scope.get("artifact") != null) {
            target = scope.getArtifact(scope.get("artifact", String.class), IState.class);
            if (target instanceof IState && (target.getType() != Type.NUMBER && target.getType() != Type.VALUE)) {
                throw new IllegalArgumentException("temporal rescaling operations can only be performed on numeric states");
            }
        }
        
		StateSummary summary = Observations.INSTANCE.getStateSummary(target, scope.getScale());
		if (!summary.isDegenerate()) {
			for (ILocator locator : scope.getScale()) {
				Object value = target.get(locator);
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
