package org.integratedmodelling.klab.components.runtime.contextualizers;

import java.util.Map;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.kim.api.ValueOperator;
import org.integratedmodelling.kim.model.KimServiceCall;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.contextualization.IProcessor;
import org.integratedmodelling.klab.api.model.contextualization.IResolver;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.components.runtime.RuntimeScope;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;

public class ValueOperatorResolver implements IResolver<IState>, IProcessor, IExpression {

	static final public String FUNCTION_ID = "klab.runtime.transform";

	IArtifact classified;
	IArtifact stateOperand;
	ValueOperator operator;
	Object valueOperand;

	protected ThreadLocal<Map<String, Boolean>> _reasonCache = new ThreadLocal<>();

	public ValueOperatorResolver() {
	}

	public ValueOperatorResolver(IArtifact classified, ValueOperator operator, Object valueOperand,
			IArtifact stateOperand) {
		this.classified = classified;
		this.stateOperand = stateOperand;
		this.valueOperand = valueOperand;
		this.operator = operator;
	}

	public static IServiceCall getServiceCall(IObservable classified, ValueOperator operator, Object operand)
			throws KlabValidationException {
		return KimServiceCall.create(FUNCTION_ID, "artifact", classified.getName(), "operator", operator.name(),
				"value", operand);
	}

	@Override
	public Object eval(IParameters<String> parameters, IContextualizationScope context) throws KlabException {

		IArtifact classified = context.getArtifact(parameters.get("artifact", String.class));
		IArtifact stateOperand = parameters.containsKey("state")
				? context.getArtifact(parameters.get("state", String.class))
				: null;
		ValueOperator operator = ValueOperator.valueOf(parameters.get("operator", String.class));
		Object valueOperand = parameters.get("value");

		return new ValueOperatorResolver(classified, operator, valueOperand, stateOperand);
	}

//	/*
//	 * TODO extract this mechanism into a caching class which also handles AND and
//	 * OR as needed.
//	 */
//	private boolean is(IConcept c1, IConcept c2) {
//
//		if (c1 == null || c2 == null) {
//			return false;
//		}
//
//		String key = c1 + "#" + c2;
//		Boolean ret = null;
//		if (_reasonCache.get() != null)
//			ret = _reasonCache.get().get(key);
//		if (ret == null) {
//			if (_reasonCache.get() == null) {
//				_reasonCache.set(new HashMap<String, Boolean>());
//			}
//			ret = c1.is(c2);
//			_reasonCache.get().put(key, ret);
//		}
//		return ret;
//	}

	@Override
	public IState resolve(IState ret, IContextualizationScope context) throws KlabException {

		// Map<Object, Double> cache = new HashMap<>();
		// Map<Object, Long> count = new HashMap<>();

		if (!(classified instanceof IState)) {
			throw new IllegalArgumentException("Operations are not possible unless the primary argument is a state");
		}
		if (stateOperand != null && !(stateOperand instanceof IState)) {
			throw new IllegalArgumentException(
					"Operations are not possible if the second argument is not a literal or a state");
		}

		IState values = (IState) classified;
		IState others = (IState) stateOperand;

		Object total = null;
		if (operator == ValueOperator.TOTAL) {
			total = others == null ? values.aggregate(context.getScale()) : others.aggregate(context.getScale());
		}

		/*
		 * TODO some values are extensive. Others aren't. Put this check under
		 * Observables after it's all understood.
		 */
		// boolean isExtensive = values.getObservable().is(Type.EXTENSIVE_PROPERTY)
		// || values.getObservable().is(Type.VALUE) ||
		// values.getObservable().is(Type.MONEY);
		//
		// IUnit propagateSpace = values.getObservable().getUnit() != null &&
		// (ret.getScale().getSpace() != null
		// && Units.INSTANCE.isArealDensity(values.getObservable().getUnit()))
		// ? Units.INSTANCE.getArealExtentUnit(values.getObservable().getUnit())
		// : null;
		// IUnit propagateTime = values.getObservable().getUnit() != null
		// && (ret.getScale().getTime() != null &&
		// Units.INSTANCE.isRate(values.getObservable().getUnit()))
		// ? Units.INSTANCE.getTimeExtentUnit(values.getObservable().getUnit())
		// : null;

		for (ILocator locator : context.getScale()) {

			Object value = values.get(locator);
			Object other = valueOperand;
			if (others != null) {
				other = others.get(locator);
			}

			if (value == null || (value instanceof Number && Double.isNaN(((Number) value).byteValue()))) {
				continue;
			}

			switch (operator) {
			case GREATER:
				if (!(value instanceof Number && other instanceof Number
						&& ((Number) value).doubleValue() > ((Number) other).doubleValue())) {
					value = null;
				}
				break;
			case GREATEREQUAL:
				if (!(value instanceof Number && other instanceof Number
						&& ((Number) value).doubleValue() >= ((Number) other).doubleValue())) {
					value = null;
				}
				break;
			case IS:
				if (value instanceof Number && other instanceof Number) {
					if (!(((Number) value).doubleValue() == ((Number) other).doubleValue())) {
						value = null;
					}
				} else if (value instanceof IConcept && other instanceof IConcept) {
					if (!((RuntimeScope)context).cached_is((IConcept) value, ((IConcept) other))) {
						value = null;
					}
				} else if (value != null && other != null) {
					if (!value.equals(other)) {
						value = null;
					}
				} else {
					value = null;
				}
				break;
			case LESS:
				if (!(value instanceof Number && other instanceof Number
						&& ((Number) value).doubleValue() < ((Number) other).doubleValue())) {
					value = null;
				}
				break;
			case LESSEQUAL:
				if (!(value instanceof Number && other instanceof Number
						&& ((Number) value).doubleValue() <= ((Number) other).doubleValue())) {
					value = null;
				}
				break;
			case MINUS:
				if (value instanceof Number && other instanceof Number) {
					value = ((Number) value).doubleValue() - ((Number) other).doubleValue();
				} else {
					value = null;
				}
				break;
			case OVER:
				if (value instanceof Number && other instanceof Number) {
					value = ((Number) value).doubleValue() / ((Number) other).doubleValue();
				} else {
					value = null;
				}
				break;
			case PLUS:
				if (value instanceof Number && other instanceof Number) {
					value = ((Number) value).doubleValue() + ((Number) other).doubleValue();
				} else {
					value = null;
				}
				break;
			case SAMEAS:
				if (value instanceof Number && other instanceof Number) {
					if (!(((Number) value).doubleValue() == ((Number) other).doubleValue())) {
						value = null;
					}
				} else if (value != null && other != null) {
					if (!value.equals(other)) {
						value = null;
					}
				} else {
					value = null;
				}
				break;
			case TIMES:
				if (value instanceof Number && other instanceof Number) {
					value = ((Number) value).doubleValue() * ((Number) other).doubleValue();
				} else {
					value = null;
				}
				break;
			case TOTAL:
				value = total;
				break;
			case WHERE:
				if (other == null || (other instanceof Number && Double.isNaN(((Number) other).doubleValue()))) {
					value = null;
				}
				break;
			case WITHOUT:
				if (value instanceof IConcept && other instanceof IConcept) {
					if (((RuntimeScope)context).cached_is((IConcept)value, (IConcept)other)) {
						value = null;
					}
				} else if (other != null && value.equals(other)) {
					value = null;
				}
				break;
			default:
				break;

			}

			ret.set(locator, value);

			// double aggregated = cache.containsKey(sclas) ? cache.get(sclas).doubleValue()
			// : 0;
			// aggregated += aggregateValue(value, values.getObservable(), ret.getScale());
			// cache.put(sclas, aggregated);
			// long cnt = count.containsKey(sclas) ? count.get(sclas) : 0;
			// count.put(sclas, cnt + 1);
		}

//		// if (!isExtensive) {
//		// for (Object key : cache.keySet()) {
//		// cache.put(key, cache.get(key) / count.get(key));
//		// }
//		// }
//
//		for (ILocator locator : ret.getScale()) {
//
//			// Object sclas = classf.get(locator);
//			// if (cache.containsKey(sclas)) {
//			// ret.set(locator, cache.get(sclas));
//			// }
//		}

		return ret;
	}

	@Override
	public IArtifact.Type getType() {
		return classified.getType();
	}

}
