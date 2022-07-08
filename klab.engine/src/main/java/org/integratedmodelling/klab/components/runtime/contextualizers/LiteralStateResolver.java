package org.integratedmodelling.klab.components.runtime.contextualizers;

import org.eclipse.emf.ecore.EObject;
import org.integratedmodelling.kim.api.IContextualizable;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.kim.model.KimServiceCall;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.contextualization.IStateResolver;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.utils.Parameters;

public class LiteralStateResolver extends AbstractContextualizer implements IStateResolver, IExpression {

    static final public String FUNCTION_ID = "klab.runtime.literal";

    Object value;

    // don't remove - only used as expression
    public LiteralStateResolver() {
    }

    public LiteralStateResolver(Object value) {
        this.value = value;
    }

    public static IServiceCall getServiceCall(Object literal, IContextualizable condition, boolean conditionNegated) {
        // TODO handle condition
        return new LiteralFunction(literal);
    }

    /**
     * A literal function produces a literal and takes no arguments. It is used to simplify the
     * encoding in dataflows, and its KDL code equivalent is the literal itself.
     * 
     * @author ferdinando.villa
     *
     */
    public static class LiteralFunction extends KimServiceCall {

        static final public String FUNCTION_ID = "klab.runtime.literal";

        Object value;

        public LiteralFunction(Object value) {
            super((EObject) null, null);
            this.value = value;
            setName(FUNCTION_ID);
            getParameters().put("val", value);
        }

        private static final long serialVersionUID = -5190145577904822153L;

        @Override
        public String getSourceCode() {
            return value == null ? "unknown" : value.toString().trim();
        }

    }

    @Override
    public Object eval(IContextualizationScope context, Object... parameters) throws KlabException {
        return new LiteralStateResolver(Parameters.create(parameters).get("val"));
    }

    @Override
    public Object resolve(IObservable semantics, IContextualizationScope context, ILocator locator) {
        return value;
    }

    @Override
    public IArtifact.Type getType() {
        return IArtifact.Type.classify(this.value);
    }
}
