package org.integratedmodelling.klab.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.klab.Observations;
import org.integratedmodelling.klab.Units;
import org.integratedmodelling.klab.api.data.Aggregation;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.mediation.IUnit;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.observations.scale.ExtentDimension;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.exceptions.KlabUnimplementedException;
import org.integratedmodelling.klab.exceptions.KlabUnsupportedFeatureException;
import org.integratedmodelling.klab.utils.Triple;

/**
 * Class facilitating "intelligent" aggregation across multiple scales and units
 * of measurement, handling the translation of context in case of extensive
 * units with differently scaled inputs and outputs.
 * 
 * @author Ferd
 *
 */
public class Aggregator {

	private List<Triple<Object, IObservable, ILocator>> addenda = new ArrayList<>();
	private IObservable observable;
	private Aggregation aggregation;
	private IMonitor monitor;
	boolean dataWarning = false;
	int temporalDimensionality = 0;
	int spatialDimensionality = 0;

	/*
	 * if true, we don't expect different observables or units, and we just
	 * accumulate values right away without mediation. Set to false by the default
	 * semantic aggregator. For now values with mediation require an enormity of RAM
	 * and GC on large extents.
	 */
	boolean stable = true;

	/*
	 * the next three are to support stable aggregation.
	 */
	double sum = 0;
	long count = 0;
	Map<Object, Integer> counts = new HashMap<>();

	IUnit unit; // destination unit

	public Aggregator(IObservable destinationObservable, IMonitor monitor) {
		this(destinationObservable, monitor, false);
	}

	public Aggregator(IObservable destinationObservable, IMonitor monitor, boolean stable) {
		this.observable = destinationObservable;
		this.aggregation = getAggregation(destinationObservable);
		this.monitor = monitor;
		this.unit = this.observable.getUnit();
		this.stable = stable;
		if (this.unit == null && this.observable.getCurrency() != null) {
			this.unit = this.observable.getCurrency().getUnit();
			this.temporalDimensionality = Units.INSTANCE.getTemporalDimensionality(this.unit);
			this.spatialDimensionality = Units.INSTANCE.getSpatialDimensionality(this.unit);
		}
	}

	/**
	 * Use this constructor when the semantics is no concern.
	 * 
	 * @param aggregation
	 */
	public Aggregator(Aggregation aggregation) {
		this.aggregation = aggregation;
	}

	public void add(Object value) {
		addenda.add(new Triple<>(value, null, null));
	}

	public void add(Object value, IObservable observable, ILocator locator) {

		if (Observations.INSTANCE.isData(value)) {

			if (stable) {
				if (value instanceof Number) {
					sum += ((Number) value).doubleValue();
					count++;
				} else {
					Integer cnt = counts.get(value);
					if (cnt == null) {
						counts.put(value, 1);
					} else {
						counts.put(value, cnt + 1);
					}
				}
				return;
			}

			if (this.aggregation == Aggregation.SUM && value instanceof Number && locator instanceof IScale) {

				double nval = ((Number) value).doubleValue();
				IScale incomingScale = (IScale) locator;

				/*
				 * adapt value
				 */
				if (observable.getUnit() != null && this.unit != null) {

					int incomingSpatialDimensionality = Units.INSTANCE.getSpatialDimensionality(observable.getUnit());
					int incomingTemporalDimensionality = Units.INSTANCE.getTemporalDimensionality(observable.getUnit());

					List<ExtentDimension> distribution = new ArrayList<>();
					if (incomingSpatialDimensionality >= 2 && spatialDimensionality == 0
							&& incomingScale.getSpace() != null) {

						/*
						 * aggregate over the spatial coverage of original locator
						 */
						if (incomingSpatialDimensionality == 2) {
							IUnit arealUnit = Units.INSTANCE.getArealExtentUnit(observable.getUnit());
							nval *= incomingScale.getSpace().getShape().getArea(arealUnit);
						} else if (incomingSpatialDimensionality == 3) {
							// TODO not equipped yet
							throw new KlabUnsupportedFeatureException("volumetric extents are still unsupported");
							// IUnit volumeUnit = Units.INSTANCE.getVolumeExtentUnit(observable.getUnit());
							// double factor = incomingScale.getSpace().getShape().getVolume(volumeUnit);
							// nval *= factor;
						}

						distribution.add(ExtentDimension.spatial(incomingSpatialDimensionality));
					}
					if (incomingTemporalDimensionality >= 2 && temporalDimensionality == 0
							&& incomingScale.getTime() != null) {

						/*
						 * adjust for temporal coverage
						 */
						IUnit temporalUnit = Units.INSTANCE.getTimeExtentUnit(observable.getUnit());
						nval *= incomingScale.getTime().getLength(temporalUnit);
						distribution.add(ExtentDimension.TEMPORAL);
					}

					/*
					 * transform value w.r.t. base units if needed
					 */
					IUnit baseUnit = observable.getUnit();
					if (distribution.size() > 0) {
						baseUnit = Units.INSTANCE.removeExtents(baseUnit, distribution);
					}

					value = this.unit.convert(nval, baseUnit);

				} else if (observable.getCurrency() != null && this.unit != null) {

				}
			}

			addenda.add(new Triple<>(value, observable, locator));
		}
	}

	/**
	 * Perform the final aggregation.
	 * 
	 * @param iMonitor
	 * @return
	 */
	public Object aggregate() {

		if (stable) {
			return aggregateStable();
		}

		Object ret = null;
		Object[] rets = null;
		int n = 0;

		for (Triple<Object, IObservable, ILocator> triple : addenda) {
			if (ret == null) {
				ret = triple.getFirst();
			} else {
				if (rets == null) {
					rets = new Object[addenda.size()];
					rets[0] = ret;
				}
				rets[++n] = triple.getFirst();
			}
		}
		return rets != null ? aggregate(rets, this.aggregation, monitor) : ret;
	}

	private Object aggregateStable() {

		if (aggregation == null) {
			return null;
		}

		switch (aggregation) {
		case ANY_PRESENT:
			return counts.size() > 1 || count > 0;
		case COUNT:
			throw new KlabUnimplementedException("count aggregation still unsupported on stable aggregator");
		case MAJORITY:
			throw new KlabUnimplementedException("majority aggregation still unsupported on stable aggregator");
		case MAX:
			throw new KlabUnimplementedException("max aggregation still unsupported on stable aggregator");
		case MEAN:
			return count > 0 ? (sum / count) : null;
		case MIN:
			throw new KlabUnimplementedException("min aggregation still unsupported on stable aggregator");
		case STD:
			throw new KlabUnimplementedException("std aggregation still unsupported on stable aggregator");
		case SUM:
			return count == 0 ? null : sum;
		default:
			break;
		}

		return null;
	}

	/**
	 * Reentrant. Use with caution.
	 * 
	 * @param objects
	 * @return
	 */
	public Object aggregate(Collection<?> objects) {
		addenda.clear();
		counts.clear();
		sum = 0;
		count = 0;
		for (Object o : objects) {
			add(o);
		}
		return aggregate();
	}
	
	public Object aggregate(Object[] values, Aggregation aggregation, IMonitor monitor) {

		if (aggregation == null) {
			for (Object value : values) {
				if (value instanceof Boolean) {
					aggregation = Aggregation.COUNT;
					break;
				} else if (value instanceof Number) {
					aggregation = Aggregation.MEAN;
					break;
				} else if (value != null) {
					aggregation = Aggregation.MAJORITY;
					break;
				}
			}
		}

		if (aggregation == null) {
			return null;
		}

		switch (aggregation) {
		case ANY_PRESENT:
			return values.length > 0;
		case COUNT:
			return count(values, monitor);
		case MAJORITY:
			return dominant(values, monitor);
		case MAX:
			return max(values, monitor);
		case MEAN:
			return mean(values, monitor);
		case MIN:
			return min(values, monitor);
		case STD:
			return std(values, monitor);
		case SUM:
			return sum(values, monitor);
		default:
			break;
		}

		return null;
	}

	public Object sum(Object[] values, IMonitor monitor) {
		double sum = 0;
		for (Object value : values) {
			if (value instanceof Number) {
				sum += ((Number) value).doubleValue();
			} else if (!dataWarning) {
				dataWarning = true;
				monitor.warn("one or more values found to be of incompatible type during aggregation");
			}
		}
		return sum;
	}

	public Object std(Object[] values, IMonitor monitor) {
		double sum = 0;
		int n = 0;
		for (Object value : values) {
			if (value instanceof Number) {
				sum += ((Number) value).doubleValue();
				n++;
			} else if (!dataWarning) {
				dataWarning = true;
				monitor.warn("one or more values found to be of incompatible type during aggregation");
			}
		}
		double mean = sum / (double) n;
		double sd = 0;
		for (Object value : values) {
			if (value instanceof Number) {
				sd += Math.pow(((Number) value).doubleValue() - mean, 2);
			}
		}
		return Math.sqrt(sd / (double) n);
	}

	public Object min(Object[] values, IMonitor monitor) {
		Double min = null;
		for (Object value : values) {
			if (value instanceof Number) {
				if (min == null || min > ((Number) value).doubleValue()) {
					min = ((Number) value).doubleValue();
				}
			} else if (!dataWarning) {
				dataWarning = true;
				monitor.warn("one or more values found to be of incompatible type during aggregation");
			}
		}
		return min;
	}

	public Object mean(Object[] values, IMonitor monitor) {
		double sum = 0;
		int n = 0;
		for (Object value : values) {
			if (value instanceof Number) {
				sum += ((Number) value).doubleValue();
				n++;
			} else if (!dataWarning) {
				dataWarning = true;
				monitor.warn("one or more values found to be of incompatible type during aggregation");
			}
		}
		return sum / (double) n;
	}

	public Object max(Object[] values, IMonitor monitor) {

		Double max = null;
		for (Object value : values) {
			if (value instanceof Number) {
				if (max == null || max < ((Number) value).doubleValue()) {
					max = ((Number) value).doubleValue();
				}
			} else if (!dataWarning) {
				dataWarning = true;
				monitor.warn("one or more values found to be of incompatible type during aggregation");
			}
		}
		return max;
	}

	public Object dominant(Object[] values, IMonitor monitor) {

		Map<Object, Integer> vals = new HashMap<>();
		for (Object value : values) {
			if (vals.containsKey(value)) {
				vals.put(value, vals.get(value) + 1);
			} else {
				vals.put(value, 1);
			}
		}
		Object val = null;
		int n = 0;
		for (Object o : vals.keySet()) {
			if (val == null || vals.get(val) > n) {
				val = o;
				n = vals.get(val);
			}
		}
		return val;
	}

	public Object count(Object[] values, IMonitor monitor) {
		int n = 0;
		Set<Object> set = new HashSet<>();
		for (Object value : values) {
			if (value instanceof Boolean) {
				if ((Boolean) value) {
					n++;
				}
			} else if (value instanceof Number) {
				if (((Number) value).doubleValue() != 0) {
					n++;
				}
			} else if (value instanceof IConcept) {
				if (!set.contains(value)) {
					n++;
					set.add(value);
				}
			} else if (value != null) {
				n++;
			}
		}
		return n;
	}

	public Aggregation getAggregation(IObservable observable) {
		switch (observable.getDescriptionType()) {
		case CATEGORIZATION:
		case VERIFICATION:
			return Aggregation.MAJORITY;
		case QUANTIFICATION:
			// NO - depends on whether the unit is extensive too, and there may be a
			// conversion factor
//			if (observable.getType().is(Type.EXTENSIVE_PROPERTY) && this.unit)
			return (observable.getType().is(Type.EXTENSIVE_PROPERTY) || observable.getType().is(Type.MONEY))
					? Aggregation.SUM
					: Aggregation.MEAN;
		default:
			break;
		}
		return null;
	}

	/**
	 * If the value requires a specific adjustment based on a locator, do that and
	 * return the result.
	 * 
	 * @param value
	 * @param locator
	 * @return
	 */
	public Object adjust(Object value, ILocator locator) {
		// TODO Auto-generated method stub
		return value;
	}

}
