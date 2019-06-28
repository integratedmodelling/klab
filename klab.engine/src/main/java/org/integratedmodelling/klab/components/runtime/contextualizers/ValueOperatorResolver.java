package org.integratedmodelling.klab.components.runtime.contextualizers;

import java.util.HashMap;
import java.util.Map;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.kim.api.ValueOperator;
import org.integratedmodelling.kim.model.KimServiceCall;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.contextualization.IProcessor;
import org.integratedmodelling.klab.api.model.contextualization.IResolver;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.IComputationContext;
import org.integratedmodelling.klab.common.Geometry;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;

public class ValueOperatorResolver implements IResolver<IState>, IProcessor, IExpression {

	static final public String FUNCTION_ID = "klab.runtime.transform";

	IArtifact classified;
	IArtifact stateOperand;
	ValueOperator operator;
	Object valueOperand;

	public ValueOperatorResolver() {
	}

	public ValueOperatorResolver(IArtifact classified, ValueOperator operator, Object valueOperand,
			IArtifact stateOperand) {
		this.classified = classified;
		this.stateOperand = stateOperand;
		this.valueOperand = valueOperand;
		this.operator = operator;
	}

	public static IServiceCall getServiceCall(IObservable classified, IObservable classifier)
			throws KlabValidationException {
		return KimServiceCall.create(FUNCTION_ID, "artifact", classified.getName(), "classifier", classifier.getName());
	}

	@Override
	public Object eval(IParameters<String> parameters, IComputationContext context) throws KlabException {

		IArtifact classified = context.getArtifact(parameters.get("artifact", String.class));
		IArtifact stateOperand = parameters.containsKey("state")
				? context.getArtifact(parameters.get("state", String.class))
				: null;
		ValueOperator operator = ValueOperator.valueOf(parameters.get("operator", String.class));
		Object valueOperand = parameters.get("value");

		return new ValueOperatorResolver(classified, operator, valueOperand, stateOperand);
	}

	@Override
	public IGeometry getGeometry() {
		return Geometry.scalar();
	}

	@Override
	public IState resolve(IState ret, IComputationContext context) throws KlabException {

		Map<Object, Double> cache = new HashMap<>();
		Map<Object, Long> count = new HashMap<>();

		if (!(classified instanceof IState)) {
			throw new IllegalArgumentException("Operations are not possible unless the primary argument is a state");
		}

		IState values = (IState) classified;

		/*
		 * TODO some values are extensive. Others aren't. Put this check under
		 * Observables after it's all understood.
		 */
//		boolean isExtensive = values.getObservable().is(Type.EXTENSIVE_PROPERTY)
//				|| values.getObservable().is(Type.VALUE) || values.getObservable().is(Type.MONEY);
//
//		IUnit propagateSpace = values.getObservable().getUnit() != null && (ret.getScale().getSpace() != null
//				&& Units.INSTANCE.isArealDensity(values.getObservable().getUnit()))
//						? Units.INSTANCE.getArealExtentUnit(values.getObservable().getUnit())
//						: null;
//		IUnit propagateTime = values.getObservable().getUnit() != null
//				&& (ret.getScale().getTime() != null && Units.INSTANCE.isRate(values.getObservable().getUnit()))
//						? Units.INSTANCE.getTimeExtentUnit(values.getObservable().getUnit())
//						: null;

		for (ILocator locator : ret.getScale()) {

			Object value = values.get(locator, Number.class);

			// if (propagateSpace || propagateTime) {
			// // Observations.INSTANCE.getSpaceAndTimeExtents(locator);
			// }

			if (value == null || (value instanceof Number && Double.isNaN(((Number) value).byteValue()))) {
				continue;
			}

			// double aggregated = cache.containsKey(sclas) ? cache.get(sclas).doubleValue()
			// : 0;
			// aggregated += aggregateValue(value, values.getObservable(), ret.getScale());
			// cache.put(sclas, aggregated);
			// long cnt = count.containsKey(sclas) ? count.get(sclas) : 0;
			// count.put(sclas, cnt + 1);
		}

		// if (!isExtensive) {
		// for (Object key : cache.keySet()) {
		// cache.put(key, cache.get(key) / count.get(key));
		// }
		// }

		for (ILocator locator : ret.getScale()) {

			// Object sclas = classf.get(locator);
			// if (cache.containsKey(sclas)) {
			// ret.set(locator, cache.get(sclas));
			// }
		}

		return ret;
	}

	@Override
	public IArtifact.Type getType() {
		return classified.getType();
	}

}
