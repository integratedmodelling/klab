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
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.rest.StateSummary;

public class NormalizingResolver implements IResolver<IState>, IExpression, IProcessor {

    boolean invert;

    public NormalizingResolver() {
    }

    public NormalizingResolver(IParameters<String> parameters, IContextualizationScope context) {
        this.invert = parameters.get("invert", Boolean.FALSE);
    }

    @Override
    public Type getType() {
        return Type.NUMBER;
    }

    @Override
    public Object eval(IParameters<String> parameters, IContextualizationScope context) throws KlabException {
        return new NormalizingResolver(parameters, context);
    }

    @Override
    public IState resolve(IState ret, IContextualizationScope scope) throws KlabException {

        IState target = scope.get("self", IState.class);
        if (scope.get("artifact") != null) {
            target = scope.getArtifact(scope.get("artifact", String.class), IState.class);
            if (target instanceof IState && (target.getType() != Type.NUMBER && target.getType() != Type.VALUE)) {
                throw new IllegalArgumentException("normalization operations can only be performed on numeric states");
            }
        }

        double min = Double.NaN;
        double max = Double.NaN;

        StateSummary summary = Observations.INSTANCE.getStateSummary(target, scope.getScale());
        if (!summary.isDegenerate()) {

            for (ILocator locator : scope.getScale()) {
                Object value = target.get(locator);
                if (value instanceof Number && !Double.isNaN(((Number) value).doubleValue())) {
                    double nval = ((Number) value).doubleValue();
                    nval = (nval - summary.getRange().get(0)) / (summary.getRange().get(1) - summary.getRange().get(0));
                    ret.set(locator, nval);
                    if (invert && Observations.INSTANCE.isData(nval)) {
                        max = max < nval ? nval : max;
                        min = min > nval ? nval : min;
                    }
                }
            }
        }

        if (invert && !Double.isNaN(min) && !Double.isNaN(max)) {
            for (ILocator locator : scope.getScale()) {
                Object value = ret.get(locator);
                if (value instanceof Number && !Double.isNaN(((Number) value).doubleValue())) {
                    ret.set(locator, max - ((Number) value).doubleValue() + min);
                }
            }
        }
        return ret;
    }

}
