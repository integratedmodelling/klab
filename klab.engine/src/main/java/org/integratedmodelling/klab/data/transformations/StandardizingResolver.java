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
import org.integratedmodelling.klab.components.runtime.contextualizers.AbstractContextualizer;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.rest.StateSummary;
import org.integratedmodelling.klab.utils.Parameters;

public class StandardizingResolver extends AbstractContextualizer implements IResolver<IState>, IProcessor, IExpression {

	boolean invert;

	public StandardizingResolver() {}
	
	public StandardizingResolver(IParameters<String> parameters, IContextualizationScope context) {
		IArtifact artifact = context.getArtifact(parameters.get("artifact", String.class));
		this.invert = parameters.get("invert", Boolean.FALSE);
	}
	
	@Override
	public Type getType() {
		return Type.NUMBER;
	}

	@Override
	public Object eval(IContextualizationScope context, Object...parameters) throws KlabException {
		return new StandardizingResolver(Parameters.create(parameters), context);
	}

	@Override
	public IState resolve(IState ret, IContextualizationScope scope) throws KlabException {
        
	    IState target = scope.get("self", IState.class);
        if (scope.get("artifact") != null) {
            target = scope.getArtifact(scope.get("artifact", String.class), IState.class);
            if (target instanceof IState && (target.getType() != Type.NUMBER && target.getType() != Type.VALUE)) {
                throw new IllegalArgumentException("standardization operations can only be performed on numeric states");
            }
        }
		
		StateSummary summary = Observations.INSTANCE.getStateSummary(target, scope.getScale());
		double min = Double.NaN;
		double max = Double.NaN;
		
		if (!summary.isDegenerate()) {
			for (ILocator locator : scope.getScale()) {
				Object value = target.get(locator);
				if (value instanceof Number && !Double.isNaN(((Number) value).doubleValue())) {
					double nval = ((Number) value).doubleValue();
					nval = (nval - summary.getMean()) / summary.getStandardDeviation();
					ret.set(locator, nval);
					if (invert && Observations.INSTANCE.isData(nval)) {
						max = max < nval ? nval : max;
						min = min > nval ? nval : min;
					}
				}
			}
			
			if (invert && !Double.isNaN(min) && !Double.isNaN(max)) {
				for (ILocator locator : scope.getScale()) {
					Object value = ret.get(locator);
					if (value instanceof Number && !Double.isNaN(((Number) value).doubleValue())) {
						ret.set(locator, max - ((Number)value).doubleValue() + min);
					}
				}
			}
		}
		return ret;
	}

}
