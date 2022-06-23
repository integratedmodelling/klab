package org.integratedmodelling.klab.components.runtime.contextualizers;

import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.kim.model.KimServiceCall;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.contextualization.IProcessor;
import org.integratedmodelling.klab.api.model.contextualization.IStateResolver;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.utils.NumberUtils;
import org.integratedmodelling.klab.utils.Parameters;

public class CastingStateResolver extends AbstractContextualizer implements IStateResolver, IProcessor, IExpression {

    static final public String FUNCTION_ID = "klab.runtime.cast";

    private Type from;
    private Type to;

    // don't remove - only used as expression
    public CastingStateResolver() {
    }

    public CastingStateResolver(IArtifact.Type from, IArtifact.Type to) {
        this.from = from;
        this.to = to;
    }

    public static IServiceCall getServiceCall(IArtifact.Type from, IArtifact.Type to) throws KlabValidationException {
        return KimServiceCall.create(FUNCTION_ID, "source", from.name(), "destination", to.name());
    }

    @Override
    public Object eval(IContextualizationScope context, Object...params) throws KlabException {
        Parameters<String> parameters = Parameters.create(params);
        return new CastingStateResolver(IArtifact.Type.valueOf(parameters.get("source", String.class)),
                IArtifact.Type.valueOf(parameters.get("destination", String.class)));
    }

    @Override
    public Object resolve(IObservable observable, IContextualizationScope context, ILocator locator) throws KlabException {

        Object value = context.get("self") instanceof IState ? ((IState) context.get("self")).get(locator) : context.get("self");

        switch(to) {
        case BOOLEAN:
            if (value != null) {
                switch(from) {
                case NUMBER:
                    if (value instanceof Number) {
                        double d = ((Number) value).doubleValue();
                        value = Double.isNaN(d) ? null : !NumberUtils.equal(d, 0);
                    } else {
                        value = Boolean.FALSE;
                    }
                    break;
                case VALUE:
                    value = value instanceof Number && Double.isNaN(((Number) value).doubleValue())
                            ? Boolean.FALSE
                            : Boolean.TRUE;
                    break;
                default:
                    break;
                }
                break;
            }
        case NUMBER:
            break;
        case TEXT:
            value = value == null ? null : value.toString();
            break;
        default:
            break;
        }
        return value;
    }

    @Override
    public IArtifact.Type getType() {
        return to;
    }
}
