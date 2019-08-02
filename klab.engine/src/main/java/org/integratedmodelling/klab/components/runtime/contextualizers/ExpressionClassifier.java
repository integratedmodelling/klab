package org.integratedmodelling.klab.components.runtime.contextualizers;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.Extensions;
import org.integratedmodelling.klab.api.data.artifacts.IObjectArtifact;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.extensions.ILanguageProcessor;
import org.integratedmodelling.klab.api.extensions.ILanguageProcessor.Descriptor;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.model.contextualization.IPredicateClassifier;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.IComputationContext;

/**
 * A classifier that defines the predicate to attribute a direct observation
 * through an expression.
 * 
 * @author Ferd
 *
 */
public class ExpressionClassifier implements IPredicateClassifier<IDirectObservation>, IExpression {

	public final static String ID = "klab.runtime.classifier";

	private IConcept abstractPredicate;
	private IConcept targetPredicate;
	private static Set<String> prototypeParameters;
	Map<String, Object> additionalParameters = null;

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
			IComputationContext context, IConcept basePredicate, IConcept targetPredicate,
			Map<String, Object> additional) {

	}

	@Override
	public Type getType() {
		return IArtifact.Type.CONCEPT;
	}

	@Override
	public boolean initialize(IObjectArtifact observations, IComputationContext context) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public IConcept classify(IConcept abstractPredicate, IDirectObservation observation, IComputationContext context) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object eval(IParameters<String> parameters, IComputationContext context) {

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

		IConcept basePredicate = parameters.get("base", IConcept.class);
		IConcept targetPredicate = parameters.get("target", IConcept.class);

		return new ExpressionClassifier(selector, condition, context, context, basePredicate, targetPredicate,
				additionalParameters);
	}

}
