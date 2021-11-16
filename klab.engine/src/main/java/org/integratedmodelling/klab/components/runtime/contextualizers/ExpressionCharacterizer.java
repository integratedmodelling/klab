package org.integratedmodelling.klab.components.runtime.contextualizers;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.Extensions;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.extensions.ILanguageExpression;
import org.integratedmodelling.klab.api.extensions.ILanguageProcessor;
import org.integratedmodelling.klab.api.extensions.ILanguageProcessor.Descriptor;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.model.contextualization.IPredicateResolver;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;

/**
 * A classifier that defines the predicate to attribute a direct observation
 * through an expression.
 * 
 * @author Ferd
 *
 */
public class ExpressionCharacterizer implements IPredicateResolver<IDirectObservation>, IExpression {

	public final static String ID = "klab.runtime.characterizer";

	private static Set<String> prototypeParameters;
	Map<String, Object> additionalParameters = null;

	private Descriptor expressionDescriptor;
	private ILanguageExpression expression;
	private Descriptor conditionDescriptor;
	private ILanguageExpression condition;

	static {
		prototypeParameters = new HashSet<>();
		prototypeParameters.add("unlesscondition");
		prototypeParameters.add("ifcondition");
		prototypeParameters.add("code");
	}

	// leave for when used as expression
	public ExpressionCharacterizer() {
	}

	public ExpressionCharacterizer(Descriptor code, Descriptor condition, IParameters<String> parameters,
			IContextualizationScope context, Map<String, Object> additional) {
		this.expressionDescriptor = code;
		this.conditionDescriptor = condition;
		if (this.expression == null) {
			this.expression = expressionDescriptor.compile();
			if (conditionDescriptor != null) {
				this.condition = conditionDescriptor.compile();
			}
		}

	}

	@Override
	public Type getType() {
		return IArtifact.Type.CONCEPT;
	}

	@Override
	public Object eval(IParameters<String> parameters, IContextualizationScope context) {

		ILanguageProcessor processor = Extensions.INSTANCE
				.getLanguageProcessor(parameters.get("language", Extensions.DEFAULT_EXPRESSION_LANGUAGE));

		IExpression.Scope expressionContext = context.getExpressionContext();

		/*
		 * compile in scalar context as this is applied to an individual object (we want
		 * self to be a variable, not an entry in the artifact table).
		 */
		Descriptor selector = processor.describe(parameters.get("code", String.class), expressionContext, CompilerOption.ForcedScalar);
		Descriptor condition = null;
		if (parameters.get("ifcondition") != null || parameters.get("unlesscondition") != null) {
			String condCode = parameters.get("ifcondition", String.class);
			if (condCode == null) {
				condCode = processor.negate(parameters.get("unlesscondition", String.class));
			}
			condition = processor.describe(condCode, expressionContext, CompilerOption.ForcedScalar);
		}

		for (String key : parameters.keySet()) {
			if (!key.startsWith("_") && !prototypeParameters.contains(key)) {
				if (additionalParameters == null) {
					additionalParameters = new HashMap<>();
				}
				additionalParameters.put(key, parameters.get(key));
			}
		}

		return new ExpressionCharacterizer(selector, condition, context, context, additionalParameters);
	}

	@Override
	public boolean resolve(IConcept predicate, IDirectObservation observation, IContextualizationScope context) {

		/*
		 * run expression for the side effects. If it returns a boolean, take it as the
		 * return value, otherwise return true unless the condition returned false. In
		 * all cases returning false will remove the predicate.
		 */
		boolean ok = true;

		if (condition != null) {
			Object ret = condition.override("self", observation, "scale", observation.getScale(), "space",
					observation.getScale().getSpace()).eval(context, context, additionalParameters);
			ok = ret instanceof Boolean && ((Boolean) ret);
		}

		if (ok) {

			Object ret = expression.override("self", observation, "scale", observation.getScale(), "space",
					observation.getScale().getSpace()).eval(context, context, additionalParameters);

			if (ret instanceof Boolean) {
				ok = (Boolean) ret;
			}
		}

		return ok;
	}

}
