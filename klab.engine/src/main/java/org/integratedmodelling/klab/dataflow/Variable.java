package org.integratedmodelling.klab.dataflow;

import java.util.Collection;

import org.integratedmodelling.kim.api.IContextualizable;
import org.integratedmodelling.kim.api.IKimExpression;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.Extensions;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.extensions.ILanguageProcessor;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.api.runtime.IVariable;

public class Variable implements IVariable {

    private Object literal;
    private IKimExpression expression;
    private IExpression compiled;

    private Variable() {
    }

    public static Variable create(IContextualizable contextualizable, Collection<String> knownVariables, Actuator actuator) {

        Variable ret = new Variable();

        if (contextualizable.getLiteral() != null) {
            ret.literal = contextualizable.getLiteral();
        } else if (contextualizable.getExpression() != null) {
            ret.expression = contextualizable.getExpression();
        } else {
            throw new IllegalStateException("only literals and expressions are allowed for auxiliary model variables");
        }

        return ret;
    }

    @Override
    public Object getValue(IContextualizationScope scope, IParameters<String> parameters) {
        if (this.literal != null) {
            return this.literal;
        }
        if (this.expression != null) {
            if (this.compiled == null) {

                ILanguageProcessor processor = Extensions.INSTANCE.getLanguageProcessor(this.expression.getLanguage() == null
                        ? Extensions.DEFAULT_EXPRESSION_LANGUAGE
                        : this.expression.getLanguage());

                ILanguageProcessor.Descriptor descriptor = processor.describe(this.expression.getCode(),
                        scope.getExpressionContext(null).scalar(this.expression.isForcedScalar()), Extensions.options(false));

                boolean contextual = false;
                for (String id : parameters.keySet()) {
                    if (descriptor.getIdentifiers().contains(id)) {
                        contextual = true;
                        break;
                    }
                }
                if (!contextual) {
                    for (String id : scope.getVariables().keySet()) {
                        if (descriptor.getIdentifiers().contains(id)) {
                            contextual = true;
                            break;
                        }
                    }
                }

                this.compiled = descriptor.compile();

                if (!contextual) {
                    // from now on, just return this.
                    this.literal = this.compiled.eval(scope, parameters);
                    return this.literal;
                }
            }

            return this.compiled.eval(scope, parameters);
        }
        return null;
    }

}
