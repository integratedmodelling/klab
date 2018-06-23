package org.integratedmodelling.klab.components.runtime.contextualizers;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.kim.api.IPrototype.Type;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.extensions.ILanguageProcessor.Descriptor;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.contextualization.IStateResolver;
import org.integratedmodelling.klab.api.runtime.IComputationContext;
import org.integratedmodelling.klab.common.Geometry;
import org.integratedmodelling.klab.exceptions.KlabException;

public class ExpressionStateResolver implements IStateResolver {

	IExpression expression;
	IExpression condition;

	public ExpressionStateResolver(Descriptor descriptor, Descriptor condition, IParameters parameters,
			IComputationContext context) {
		this.expression = descriptor.compile();
		if (condition != null) {
			this.condition = condition.compile();
		}
	}

	@Override
	public Object resolve(IObservable semantics, IComputationContext context) throws KlabException {
		boolean ok = true;
		if (condition != null) {
			Object ret = condition.eval(context, context);
			ok = ret instanceof Boolean && ((Boolean) ret);
		}
		return ok ? expression.eval(context, context) : null;
	}

	@Override
	public IGeometry getGeometry() {
		return Geometry.scalar();
	}

	@Override
	public Type getType() {
		return Type.VALUE;
	}

}
