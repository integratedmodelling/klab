package org.integratedmodelling.klab.engine.runtime.code;

import org.integratedmodelling.kim.api.IKimExpression;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.Extensions;
import org.integratedmodelling.klab.api.auth.IIdentity;
import org.integratedmodelling.klab.api.data.general.IExpression.CompilerOption;
import org.integratedmodelling.klab.api.data.general.IExpression.Forcing;
import org.integratedmodelling.klab.api.extensions.ILanguageExpression;
import org.integratedmodelling.klab.api.extensions.ILanguageProcessor;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
import org.integratedmodelling.klab.utils.Parameters;
import org.integratedmodelling.klab.utils.Utils;

/**
 * A specialized expression that is initialized with a runtime scope and evaluated in the context of
 * a passed direct observation, known as "self" in it. The expression is aware of all the details of
 * the context and sets up proxies and variables according to what is requested and available. Meant
 * mainly for use by k.LAB actors.
 * <p>
 * TODO collect all the expressions used in contextualizers and refactor to this.
 * 
 * @author Ferd
 *
 */
public class ObjectExpression {

    private ILanguageProcessor.Descriptor descriptor;
    private IParameters<String> parameters = Parameters.create();
    private ILanguageExpression expression = null;
    CompilerOption[] compilerOptions;

    public ObjectExpression(IKimExpression expression, IRuntimeScope scope, CompilerOption... options) {
        this(expression, scope, false, options);
    }

    public ObjectExpression(IKimExpression expression, IRuntimeScope overallScope, boolean forceScalar,
            CompilerOption... options) {
        this.descriptor = Extensions.INSTANCE
                .getLanguageProcessor(
                        expression.getLanguage() == null ? Extensions.DEFAULT_EXPRESSION_LANGUAGE : expression.getLanguage())
                .describe(expression.getCode(),
                        forceScalar
                                ? overallScope.getExpressionContext().scalar(Forcing.AsNeeded)
                                : overallScope.getExpressionContext(),
                        Extensions.options(false, options));
        this.expression = this.descriptor.compile();
    }

    public Object eval(IRuntimeScope scope, IIdentity identity) {
        return eval(scope, identity, null, Object.class);
    }

    public Object eval(IRuntimeScope scope, IIdentity identity, IParameters<String> additionalParameters) {
        return eval(scope, identity, additionalParameters, Object.class);
    }

    public <T> T eval(IRuntimeScope scope, IIdentity identity, Class<? extends T> cls) {
        return eval(scope, identity, null, cls);
    }

    public <T> T eval(IRuntimeScope scope, IIdentity identity, IParameters<String> additionalParameters, Class<? extends T> cls) {

        this.parameters.clear();
        if (additionalParameters != null) {
            this.parameters.putAll(additionalParameters);
        }

//        IScale scale = identity instanceof IObservation ? ((IObservation) identity).getScale() : null;
//
//        Map<String, IObservation> artifacts = scope.getLocalCatalog(IObservation.class);
//        if (artifacts != null) {
//            for (String id : descriptor.getIdentifiersInScalarScope()) {
//                IObservation artifact = artifacts.get(id);
//                if (artifact instanceof IState && scale != null) {
//                    parameters.put(id, ((IState) artifact).get(scale));
//                }
//            }
//        }

        return Utils.asType(this.expression.eval(scope, parameters, "self", identity), cls);
    }
}
