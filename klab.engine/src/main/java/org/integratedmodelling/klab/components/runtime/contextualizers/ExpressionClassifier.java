package org.integratedmodelling.klab.components.runtime.contextualizers;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.Extensions;
import org.integratedmodelling.klab.api.data.artifacts.IObjectArtifact;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.extensions.ILanguageExpression;
import org.integratedmodelling.klab.api.extensions.ILanguageProcessor;
import org.integratedmodelling.klab.api.extensions.ILanguageProcessor.Descriptor;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.model.contextualization.IPredicateClassifier;
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
public class ExpressionClassifier implements IPredicateClassifier<IDirectObservation>, IExpression {

	public final static String ID = "klab.runtime.classifier";

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
	public ExpressionClassifier() {
	}

	public ExpressionClassifier(Descriptor code, Descriptor condition, IParameters<String> parameters,
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
	public IConcept classify(IConcept abstractPredicate, IDirectObservation observation, IContextualizationScope context) {

		/*
		 * run expression, ensure it returns an OK concept, if so return it.
		 */
		boolean ok = true;
		if (condition != null) {
			Object ret = condition.override("self", observation, "scale", observation.getScale(), "space",
					observation.getScale().getSpace()).eval(context, context, additionalParameters);
			ok = ret instanceof Boolean && ((Boolean) ret);
		}

		Object ret = ok ? expression.override("self", observation, "scale", observation.getScale(), "space",
				observation.getScale().getSpace()).eval(context, context, additionalParameters) : null;

		if (ret == null) {
			return null;
		}

		if (ret instanceof IConcept && ((IConcept) ret).is(abstractPredicate)) {
			return (IConcept) ret;
		}

		throw new IllegalStateException(
				"classification expression does not return a concept or a subtype of " + abstractPredicate);
	}

	@Override
	public Object eval(IParameters<String> parameters, IContextualizationScope context) {

		ILanguageProcessor processor = Extensions.INSTANCE
				.getLanguageProcessor(parameters.get("language", Extensions.DEFAULT_EXPRESSION_LANGUAGE));

		IExpression.Context expressionContext = context.getExpressionContext();
		Descriptor selector = processor.describe(parameters.get("code", String.class), expressionContext, false);
		Descriptor condition = null;
		if (parameters.get("ifcondition") != null || parameters.get("unlesscondition") != null) {
			String condCode = parameters.get("ifcondition", String.class);
			if (condCode == null) {
				condCode = processor.negate(parameters.get("unlesscondition", String.class));
			}
			condition = processor.describe(condCode, expressionContext, false);
		}

		for (String key : parameters.keySet()) {
			if (!key.startsWith("_") && !prototypeParameters.contains(key)) {
				if (additionalParameters == null) {
					additionalParameters = new HashMap<>();
				}
				additionalParameters.put(key, parameters.get(key));
			}
		}

		return new ExpressionClassifier(selector, condition, context, context, additionalParameters);
	}

	@Override
	public boolean initialize(IObjectArtifact observations, IConcept abstractPredicate, IConcept targetPredicate,
			IContextualizationScope context) {
		return true;
	}

}
