package org.integratedmodelling.klab.engine.runtime.code;

import java.util.Map;

import org.integratedmodelling.kim.api.IKimExpression;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.Extensions;
import org.integratedmodelling.klab.api.auth.IIdentity;
import org.integratedmodelling.klab.api.extensions.ILanguageExpression;
import org.integratedmodelling.klab.api.extensions.ILanguageProcessor;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
import org.integratedmodelling.klab.utils.Parameters;
import org.integratedmodelling.klab.utils.Utils;

/**
 * A specialized expression that is initialized with a runtime scope and
 * evaluated in the context of a passed direct observation, known as "self" in
 * it. The expression is aware of all the details of the context and sets up
 * proxies and variables according to what is requested and available. Meant
 * mainly for use by k.LAB actors.
 * <p>
 * TODO collect all the expressions used in contextualizers and refactor to
 * this.
 * 
 * @author Ferd
 *
 */
public class ObjectExpression {

	private ILanguageProcessor.Descriptor descriptor;
	private ThreadLocal<IParameters<String>> parameters = new ThreadLocal<>();
	private ThreadLocal<ILanguageExpression> expression = new ThreadLocal<>();
	private boolean first = false;
	
	public ObjectExpression(IKimExpression expression, IRuntimeScope scope) {
		this(expression, scope, false);
		this.parameters.set(Parameters.create());
	}

	public ObjectExpression(IKimExpression expression, IRuntimeScope overallScope, boolean forceScalar) {
		boolean scalar = forceScalar || expression.isForcedScalar();
		this.descriptor = Extensions.INSTANCE
				.getLanguageProcessor(expression.getLanguage() == null ? Extensions.DEFAULT_EXPRESSION_LANGUAGE
						: expression.getLanguage())
				.describe(expression.getCode(), overallScope.getExpressionContext(), scalar);
		this.expression.set(this.descriptor.compile());
		this.parameters.set(Parameters.create());
	}

	public Object eval(IRuntimeScope scope, IIdentity identity) {
		return eval(scope, identity, null, Object.class);
	}

	public Object eval(IRuntimeScope scope, IIdentity identity, IParameters<String> additionalParameters) {
		return eval(scope, identity, null, Object.class);
	}

	public <T> T eval(IRuntimeScope scope, IIdentity identity, Class<? extends T> cls) {
		return eval(scope, identity, null, cls);
	}

	public <T> T eval(IRuntimeScope scope, IIdentity identity, IParameters<String> additionalParameters,
			Class<? extends T> cls) {

		this.parameters.get().clear();
		if (additionalParameters != null) {
			this.parameters.get().putAll(additionalParameters);
		}

		if (first) {
			parameters.get().put("self", identity);
			first = true;
		} else {
			this.expression.get().override("self", identity);
		}
		
		IScale scale = identity instanceof IObservation ? ((IObservation) identity).getScale() : null;

		Map<String, IObservation> artifacts = scope.getLocalCatalog(IObservation.class);
		for (String id : descriptor.getIdentifiersInScalarScope()) {
			IObservation artifact = artifacts.get(id);
			if (artifact instanceof IState && scale != null) {
				parameters.get().put(id, ((IState) artifact).get(scale));
			}
		}

		return Utils.asType(this.expression.get().eval(parameters.get(), scope), cls);
	}
}
