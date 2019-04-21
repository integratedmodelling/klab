package org.integratedmodelling.klab.components.runtime.contextualizers;

import java.util.Map;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.extensions.ILanguageExpression;
import org.integratedmodelling.klab.api.extensions.ILanguageProcessor.Descriptor;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.contextualization.IStateResolver;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.IComputationContext;
import org.integratedmodelling.klab.common.Geometry;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.utils.Parameters;

public class ExpressionStateResolver implements IStateResolver {

    Descriptor                  expressionDescriptor;
    Descriptor                  conditionDescriptor;
    ILanguageExpression         expression;
    ILanguageExpression         condition;
    private Map<String, Object> additionalParameters;

    public ExpressionStateResolver(Descriptor descriptor, Descriptor condition,
            IParameters<String> parameters,
            IComputationContext context, Map<String, Object> additionalParameters) {
        this.expressionDescriptor = descriptor;
        this.conditionDescriptor = condition;
        this.additionalParameters = additionalParameters;
    }

    @Override
    public Object resolve(IObservable semantics, IComputationContext context) throws KlabException {

        boolean ok = true;
        if (this.expression == null) {
            this.expression = expressionDescriptor.compile();
            if (conditionDescriptor != null) {
                this.condition = conditionDescriptor.compile();
            }
        }
        if (condition != null) {
            Object ret = condition
                    .override("scale", context.getScale(), "space", context.getScale().getSpace())
                    .eval(context, context, additionalParameters);
            ok = ret instanceof Boolean && ((Boolean) ret);
        }
        return ok ? expression
                .override("scale", context.getScale(), "space", context.getScale().getSpace())
                .eval(context, context, additionalParameters)
                : null;
    }

    @Override
    public IGeometry getGeometry() {
        return Geometry.scalar();
    }

    @Override
    public IArtifact.Type getType() {
        return Type.VALUE;
    }

}
