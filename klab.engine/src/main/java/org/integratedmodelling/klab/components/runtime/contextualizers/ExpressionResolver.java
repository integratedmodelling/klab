package org.integratedmodelling.klab.components.runtime.contextualizers;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.integratedmodelling.kim.api.IContextualizable;
import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.kim.model.KimServiceCall;
import org.integratedmodelling.klab.Extensions;
import org.integratedmodelling.klab.Observables;
import org.integratedmodelling.klab.api.data.artifacts.IDataArtifact;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.extensions.ILanguageExpression;
import org.integratedmodelling.klab.api.extensions.ILanguageProcessor;
import org.integratedmodelling.klab.api.extensions.ILanguageProcessor.Descriptor;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.contextualization.IResolver;
import org.integratedmodelling.klab.api.provenance.IActivity;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.components.runtime.observations.State;
import org.integratedmodelling.klab.engine.runtime.code.ExpressionScope;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.utils.Parameters;
import org.integratedmodelling.klab.utils.Utils;

/**
 * Scalar resolver that evaluates an expression resolving an artifact. If the
 * expression returns a different artifact, that substitutes the previous
 * artifact.
 * 
 * TODO if the expression terminates the artifact, that must be handled by the
 * caller.
 * 
 * @author Ferd
 *
 */
public class ExpressionResolver extends AbstractContextualizer implements IResolver<IArtifact>, IExpression {

	static final public String FUNCTION_ID = "klab.runtime.exec";

	Descriptor expressionDescriptor;
	Descriptor conditionDescriptor;
	ILanguageExpression expression = null;
	ILanguageExpression condition = null;
	Map<String, Object> additionalParameters = null;
	boolean isScalar;
	IContextualizable resource = null;
	private static Set<String> prototypeParameters;

	static {
		prototypeParameters = new HashSet<>();
		prototypeParameters.add("unlesscondition");
		prototypeParameters.add("ifcondition");
		prototypeParameters.add("code");
	}

	// don't remove - only used as expression
	public ExpressionResolver() {
	}

	public ExpressionResolver(Descriptor descriptor, Descriptor condition, IParameters<String> parameters,
			IContextualizationScope context, Map<String, Object> additionalParameters) {
		this.expressionDescriptor = descriptor;
		this.conditionDescriptor = condition;
		this.additionalParameters = additionalParameters;
		this.expression = descriptor.compile();
		if (condition != null) {
			this.condition = condition.compile();
		}
	}

	public static IServiceCall getServiceCall(IContextualizable resource, IObservable observable) {

		String functionId = FUNCTION_ID;
		if (observable.getDescriptionType() == IActivity.Description.CLASSIFICATION) {
			functionId = ExpressionClassifier.ID;
		} else if (observable.getDescriptionType() == IActivity.Description.CHARACTERIZATION) {
			functionId = ExpressionCharacterizer.ID;
		}

		IServiceCall ret = KimServiceCall.create(functionId);
		ret.getParameters().put("code", resource.getExpression());
		if (resource.getExpression().isForcedScalar()) {
			ret.getParameters().put("scalar", Boolean.TRUE);
		}
		if (resource.getCondition() != null) {
			ret.getParameters().put(resource.isNegated() ? "unlesscondition" : "ifcondition", resource.getCondition());
		}

		return ret;
	}

	@Override
	public Object eval(IContextualizationScope context, Object... params) throws KlabException {

		Parameters<String> parameters = Parameters.create(params);
		ILanguageProcessor processor = Extensions.INSTANCE
				.getLanguageProcessor(parameters.get("language", Extensions.DEFAULT_EXPRESSION_LANGUAGE));
		/**
		 * Determine what we are computing. It will be the target artifact but the model
		 * may be computing an action directed to a secondary observable, which we
		 * should find in the call as a hidden parameter.
		 */
		IObservable targetObservable = context.getTargetSemantics();
		if (parameters.containsKey(Extensions.TARGET_OBSERVABLE_PARAMETER)) {
			targetObservable = Observables.INSTANCE.asObservable(context.get(Extensions.TARGET_OBSERVABLE_PARAMETER));
		}

		boolean forceScalar = parameters.get("scalar", Boolean.FALSE);
		ExpressionScope expressionScope = (ExpressionScope) context.getExpressionContext(targetObservable)
				.scalar(forceScalar ? Forcing.Always : Forcing.AsNeeded);
		Set<CompilerOption> options = new HashSet<>();
		if (expressionScope.getCompilerScope() == CompilerScope.Scalar) {
			// this is for speed
			// FIXME may not be necessary
			options.add(CompilerOption.DirectQualityAccess);
		}
		Descriptor descriptor = processor.describe(parameters.get("code", String.class), expressionScope,
				options.toArray(new CompilerOption[options.size()]));
		Descriptor condition = null;
		if (parameters.get("ifcondition") != null || parameters.get("unlesscondition") != null) {
			String condCode = parameters.get("ifcondition", String.class);
			if (condCode == null) {
				condCode = processor.negate(parameters.get("unlesscondition", String.class));
			}
			condition = processor.describe(condCode, expressionScope,
					options.toArray(new CompilerOption[options.size()]));
		}

		for (String key : parameters.keySet()) {
			if (!key.startsWith("_") && !prototypeParameters.contains(key)) {
				if (additionalParameters == null) {
					additionalParameters = new HashMap<>();
				}
				additionalParameters.put(key, parameters.get(key));
			}
		}

		if (forceScalar || (descriptor.isScalar() && (targetObservable == null || targetObservable.is(Type.QUALITY)))) {
			return new ExpressionStateResolver(descriptor, condition, parameters, context, additionalParameters);
		}

		/**
		 * otherwise just a single-shot expression resolver
		 */
		return new ExpressionResolver(descriptor, condition, parameters, context, additionalParameters);
	}

	@Override
	public IArtifact resolve(IArtifact ret, IContextualizationScope scope) throws KlabException {

		IParameters<String> parameters = scope;
		if (additionalParameters != null || !scope.getVariables().isEmpty()) {
			parameters = new Parameters<String>();
			parameters.putAll(scope);
			parameters.putAll(additionalParameters);
		}

		if (this.expression == null) {
			this.expression = expressionDescriptor.compile();
			if (conditionDescriptor != null) {
				this.condition = conditionDescriptor.compile();
			}
		}

		for (String key : scope.getVariables().keySet()) {
			parameters.put(key, scope.getVariables().get(key).getValue(scope, parameters));
		}

		boolean ok = true;
		if (condition != null) {
			Object cond = condition.eval(scope, parameters, "self", ret);
			ok = cond instanceof Boolean && ((Boolean) cond);
		}
		if (ok) {
			Object o = expression.eval(scope, parameters, "self", ret);
			if (o instanceof IDataArtifact) {
				ret = (IDataArtifact) o;
			} else if (Utils.isPOD(o) && ret instanceof State) {
				((State) ret).distributeScalar(o);
			}
		}
		return ret;
	}

	@Override
	public IArtifact.Type getType() {
		// we can't really know
		return IArtifact.Type.VALUE;
	}

}
