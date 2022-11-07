package org.integratedmodelling.klab.engine.runtime.code;

import org.integratedmodelling.kim.api.IKimExpression;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.Extensions;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.data.general.IExpression.Forcing;
import org.integratedmodelling.klab.api.extensions.ILanguageProcessor;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.observations.scale.space.ISpace;
import org.integratedmodelling.klab.api.observations.scale.time.ITime;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
import org.integratedmodelling.klab.utils.Parameters;
import org.integratedmodelling.klab.utils.Utils;

/**
 * A specialized expression that is initialized with a runtime scope and evaluated in a context at a
 * given locator. The expression is aware of all the details of the context and sets up proxies and
 * variables according to what is requested and available.
 * <p>
 * TODO collect all the expressions used in contextualizers and refactor to this.
 * 
 * @author Ferd
 *
 */
public class LocatedExpression {

    private ILanguageProcessor.Descriptor descriptor;
    private ThreadLocal<IParameters<String>> parameters = new ThreadLocal<>();
    private ThreadLocal<IExpression> expression = new ThreadLocal<>();

    public LocatedExpression(IKimExpression expression, IRuntimeScope scope) {
        this(expression, scope, false);
        this.parameters.set(Parameters.create());
    }

    public LocatedExpression(IKimExpression expression, IRuntimeScope overallScope, boolean forceScalar) {
        boolean scalar = forceScalar || expression.isForcedScalar();
        this.descriptor = Extensions.INSTANCE
                .getLanguageProcessor(
                        expression.getLanguage() == null ? Extensions.DEFAULT_EXPRESSION_LANGUAGE : expression.getLanguage())
                .describe(expression.getCode(),
                        overallScope.getExpressionContext().scalar(scalar ? Forcing.Always : Forcing.AsNeeded),
                        Extensions.options(false));
        this.expression.set(this.descriptor.compile()); 
        this.parameters.set(Parameters.create());
    }

    public Object eval(IRuntimeScope scope, ILocator locator) {
        return eval(scope, locator, null, Object.class);
    }

    public Object eval(IRuntimeScope scope, ILocator locator, IParameters<String> additionalParameters) {
        return eval(scope, locator, null, Object.class);
    }

    public <T> T eval(IRuntimeScope scope, ILocator locator, Class<? extends T> cls) {
        return eval(scope, locator, null, cls);
    }

    public <T> T eval(IRuntimeScope scope, ILocator locator, IParameters<String> additionalParameters, Class<? extends T> cls) {

        this.parameters.get().clear();
        if (additionalParameters != null) {
            this.parameters.get().putAll(additionalParameters);
        }

        boolean wantsCells = descriptor.getIdentifiers().contains("cell");

        if (locator instanceof IScale) {

        } else if (locator instanceof ISpace) {

        } else if (locator instanceof ITime) {

        }

//        Map<String, IObservation> artifacts = scope.getLocalCatalog(IObservation.class);
//        for (String id : descriptor.getIdentifiersInScalarScope()) {
//            IObservation artifact = artifacts.get(id);
//            if (artifact instanceof IState) {
//                parameters.get().put(id, ((IState) artifact).get(locator));
//            }
//        }

        return Utils.asType(this.expression.get().eval(scope, parameters.get(), "scale", locator), cls);
    }
}
