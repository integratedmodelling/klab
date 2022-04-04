package org.integratedmodelling.klab.components.runtime.contextualizers;

import java.util.HashMap;
import java.util.Map;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.api.extensions.ILanguageExpression;
import org.integratedmodelling.klab.api.extensions.ILanguageProcessor.Descriptor;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.contextualization.IStateResolver;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.utils.Parameters;

public class ExpressionStateResolver extends AbstractContextualizer implements IStateResolver {

	Descriptor expressionDescriptor;
	Descriptor conditionDescriptor;
	ILanguageExpression expression;
	ILanguageExpression condition;
	private Map<String, Object> additionalParameters;
	private IParameters<String> variables = Parameters.create();

	public ExpressionStateResolver(Descriptor descriptor, Descriptor condition, IParameters<String> parameters,
			IContextualizationScope context, Map<String, Object> additionalParameters) {
		this.expressionDescriptor = descriptor;
		this.conditionDescriptor = condition;
		this.additionalParameters = additionalParameters;
	}

	@Override
	public Object resolve(IObservable semantics, IContextualizationScope context) throws KlabException {

		// if these exist, they are necessarily overridden every time.
		if (!context.getVariables().isEmpty()) {
			variables.clear();
			variables.putAll(context);
			if (additionalParameters == null) {
				additionalParameters = new HashMap<>();
			}
			// override with interactive parameters
			variables.putAll(additionalParameters);
			for (String key : context.getVariables().keySet()) {
				additionalParameters.put(key, context.getVariables().get(key).getValue(variables, context));
			}
		}

		boolean ok = true;
		if (this.expression == null) {
			this.expression = expressionDescriptor.compile();
			if (conditionDescriptor != null) {
				this.condition = conditionDescriptor.compile();
			}
		}
		if (condition != null) {
			Object ret = condition.override("scale", context.getScale(), "space", context.getScale().getSpace(), "semantics", semantics)
					.eval(context, context, additionalParameters);
			ok = ret instanceof Boolean && ((Boolean) ret);
		}
		return ok ? expression.override("scale", context.getScale(), "space", context.getScale().getSpace(), "semantics", semantics)
				.eval(context, context, additionalParameters) : null;
	}

	@Override
	public IArtifact.Type getType() {
		return Type.VALUE;
	}

}
