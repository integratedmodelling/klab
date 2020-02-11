package org.integratedmodelling.klab.components.runtime.contextualizers;

import java.util.Collection;
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
import org.integratedmodelling.klab.api.extensions.ILanguageProcessor;
import org.integratedmodelling.klab.api.extensions.ILanguageProcessor.Descriptor;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.contextualization.IResolver;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.provenance.IActivity;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.components.runtime.observations.State;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.utils.Pair;
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
public class ExpressionResolver implements IResolver<IArtifact>, IExpression {

	static final public String FUNCTION_ID = "klab.runtime.exec";

	Descriptor expressionDescriptor;
	Descriptor conditionDescriptor;
	IExpression expression = null;
	IExpression condition = null;
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
		if (observable.getDescription() == IActivity.Description.CLASSIFICATION) {
			functionId = ExpressionClassifier.ID;
		} else if (observable.getDescription() == IActivity.Description.CHARACTERIZATION) {
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
	public Object eval(IParameters<String> parameters, IContextualizationScope context) throws KlabException {

		ILanguageProcessor processor = Extensions.INSTANCE
				.getLanguageProcessor(parameters.get("language", Extensions.DEFAULT_EXPRESSION_LANGUAGE));

		IExpression.Context expressionContext = context.getExpressionContext();
		Boolean forceScalar = parameters.get("scalar", Boolean.FALSE);
		Descriptor descriptor = processor.describe(parameters.get("code", String.class), expressionContext, false);
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

		/**
		 * Determine what we are computing. It will be the target artifact but the model
		 * may be computing an action directed to a secondary observable, which we
		 * should find in the call as a hidden parameter.
		 */
		Type targetType = context.getArtifactType();
		if (parameters.containsKey(Extensions.TARGET_OBSERVABLE_PARAMETER)) {
			targetType = Observables.INSTANCE
					.getObservableType(parameters.get(Extensions.TARGET_OBSERVABLE_PARAMETER, IObservable.class), true);
		}

		/**
		 * If we're computing a quality and there is any scalar usage of the known
		 * non-scalar quantities, create a distributed state resolver. Do the analysis
		 * even if scalar evaluation has been forced.
		 */
		boolean scalar = false;
		if (targetType == Type.QUALITY) {
			Collection<String> distributedStateIds = getDistributedStateIds(context);
			distributedStateIds.add("self");
			scalar = descriptor.isScalar(distributedStateIds);
			if (!scalar && condition != null) {
				scalar = condition.isScalar(distributedStateIds);
			}
		}

		if (scalar || forceScalar) {
			return new ExpressionStateResolver(descriptor, condition, parameters, context, additionalParameters);
		}

		/**
		 * otherwise just a single-shot expression resolver
		 */
		return new ExpressionResolver(descriptor, condition, parameters, context, additionalParameters);
	}

	private Set<String> getDistributedStateIds(IContextualizationScope context) {
		Set<String> ret = new HashSet<>();
		for (Pair<String, IState> state : context.getArtifacts(IState.class)) {
			ret.add(state.getFirst());
		}
		return ret;
	}

	@Override
	public IArtifact resolve(IArtifact ret, IContextualizationScope context) throws KlabException {

		IParameters<String> parameters = context;
		if (additionalParameters != null || !context.getVariables().isEmpty()) {
			parameters = new Parameters<String>();
			parameters.putAll(context);
			parameters.putAll(additionalParameters);
		}

		if (this.expression == null) {
			this.expression = expressionDescriptor.compile();
			if (conditionDescriptor != null) {
				this.condition = conditionDescriptor.compile();
			}
		}
		
		for (String key : context.getVariables().keySet()) {
			parameters.put(key, context.getVariables().get(key).getValue(parameters, context));
		}
		
		boolean ok = true;
		if (condition != null) {
			Object cond = condition.eval(parameters, context);
			ok = cond instanceof Boolean && ((Boolean) cond);
		}
		if (ok) {
			Object o = expression.eval(parameters, context);
			if (o instanceof IDataArtifact) {
				ret = (IDataArtifact) o;
			} else if (Utils.isPOD(o) && ret instanceof State) {
				((State) ret).distributeScalar(o);
			}
		}
		return ret;
	}

//	@Override
//	public IGeometry getGeometry() {
//		return Geometry.scalar();
//	}

	@Override
	public IArtifact.Type getType() {
		// we can't really know
		return IArtifact.Type.VALUE;
	}

}
