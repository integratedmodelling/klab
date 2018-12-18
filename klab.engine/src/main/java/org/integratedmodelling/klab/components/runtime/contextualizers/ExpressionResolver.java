package org.integratedmodelling.klab.components.runtime.contextualizers;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.integratedmodelling.kim.api.IComputableResource;
import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.kim.model.KimServiceCall;
import org.integratedmodelling.klab.Extensions;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.artifacts.IDataArtifact;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.extensions.ILanguageProcessor;
import org.integratedmodelling.klab.api.extensions.ILanguageProcessor.Descriptor;
import org.integratedmodelling.klab.api.model.contextualization.IResolver;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.IComputationContext;
import org.integratedmodelling.klab.common.Geometry;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.utils.Pair;

/**
 * Scalar resolver that evaluates an expression resolving an artifact. If the
 * expression returns a different artifact, that substitutes the previous
 * artifact.
 * 
 * TODO if the expression terminates the artifact, that must be handled by 
 * the caller.
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
//	IGeometry geometry = null;
	boolean isScalar;
	IComputableResource resource = null;

	// don't remove - only used as expression
	public ExpressionResolver() {
	}

	public ExpressionResolver(Descriptor descriptor, Descriptor condition, IParameters<String> parameters,
			IComputationContext context) {
		this.expressionDescriptor = descriptor;
		this.conditionDescriptor = condition;
		this.expression = descriptor.compile();
		if (condition != null) {
			this.condition = condition.compile();
		}
	}

	public static IServiceCall getServiceCall(IComputableResource resource) {
		
		IServiceCall ret = KimServiceCall.create(FUNCTION_ID);
		ret.getParameters().put("code", resource.getExpression());
		if (resource.getCondition() != null) {
			ret.getParameters().put(resource.isNegated() ? "unlesscondition" : "ifcondition", resource.getCondition());
		}
		return ret;
	}

	@Override
	public Object eval(IParameters<String> parameters, IComputationContext context) throws KlabException {

		ILanguageProcessor processor = Extensions.INSTANCE
				.getLanguageProcessor(parameters.get("language", Extensions.DEFAULT_EXPRESSION_LANGUAGE));

		Descriptor descriptor = processor.describe(parameters.get("code", String.class), context);
		Descriptor condition = null;
		if (parameters.get("ifcondition") != null || parameters.get("unlesscondition") != null) {
			String condCode = parameters.get("ifcondition", String.class);
			if (condCode == null) {
				condCode = processor.negate(parameters.get("unlesscondition", String.class));
			}
			condition = processor.describe(condCode, context);
		}

		/**
		 * If we're computing a quality and there is any scalar usage of the known
		 * non-scalar quantities, create a distributed state resolver.
		 */
		boolean scalar = false;
		if (context.getArtifactType() == Type.QUALITY) {
			Collection<String> distributedStateIds = getDistributedStateIds(context);
			distributedStateIds.add("self");
			scalar = descriptor.isScalar(distributedStateIds);
			if (!scalar && condition != null) {
				scalar = condition.isScalar(distributedStateIds);
			}
		}

		if (scalar) {
			return new ExpressionStateResolver(descriptor, condition, parameters, context);
		}

		/**
		 * otherwise just a single-shot expression resolver
		 */
		return new ExpressionResolver(descriptor, condition, parameters, context);
	}

	private Set<String> getDistributedStateIds(IComputationContext context) {
		Set<String> ret = new HashSet<>();
		for (Pair<String, IState> state : context.getArtifacts(IState.class)) {
			if (!state.getSecond().isConstant()) {
				ret.add(state.getFirst());
			}
		}
		return ret;
	}

	@Override
	public IArtifact resolve(IArtifact ret, IComputationContext context) throws KlabException {
		
		if (this.expression == null) {
			this.expression = expressionDescriptor.compile();
			if (conditionDescriptor != null) {
				this.condition = conditionDescriptor.compile();
			}
		}
		boolean ok = true;
		if (condition != null) {
			Object cond = condition.eval(context, context);
			ok = cond instanceof Boolean && ((Boolean) cond);
		}
		if (ok) {
			Object o = expression.eval(context, context);
			if (o instanceof IDataArtifact) {
				ret = (IDataArtifact) o;
			}
		}
		return ret;
	}

	@Override
	public IGeometry getGeometry() {
		return Geometry.scalar();
	}

	@Override
	public IArtifact.Type getType() {
		// we can't really know
		return IArtifact.Type.VALUE;
	}

}
