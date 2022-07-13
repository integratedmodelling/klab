package org.integratedmodelling.klab.data;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.integratedmodelling.kim.api.IValueMediator;
import org.integratedmodelling.klab.Observations;
import org.integratedmodelling.klab.Units;
import org.integratedmodelling.klab.api.data.Aggregation;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.exceptions.KlabIllegalArgumentException;
import org.integratedmodelling.klab.exceptions.KlabUnimplementedException;
import org.integratedmodelling.klab.utils.Pair;

import groovy.lang.GroovyObjectSupport;

/**
 * Class facilitating "intelligent" aggregation across multiple scales and units of measurement,
 * handling the translation of context in case of extensive units with differently scaled inputs and
 * outputs.
 * 
 * An aggregator object must only be used within <em>one</em> contextualization: it will cache
 * conversion factors if the target is extensive and the aggregation dimensions are regular
 * topologies, so switching the same aggregator to a different scale will silently produce wrong
 * results. It is not thread safe.
 * 
 * @author Ferd
 */
public class Aggregator extends GroovyObjectSupport {

    private Aggregation aggregation;
    private IValueMediator unit; // destination unit or currency
    boolean dataWarning = false;
    /*
     * these enable passing null as locator to add() as long as the space is regular. The aggregator
     * should not be used across time for time-varying extents in that case.
     */
    IScale scale = null;

    /*
     * the next three are to support stable aggregation. TODO use summary stats
     */
    double sum = 0;
    long count = 0;
    double min = Double.NaN;
    double max = Double.NaN;
    Map<Object, Integer> counts = new HashMap<>();

    public Aggregator(IObservable destinationObservable, IScale scale) {
        Pair<IValueMediator, Aggregation> aggregationStrategy = Units.INSTANCE.getAggregationStrategy(destinationObservable, scale);
        this.unit = aggregationStrategy.getFirst();
        this.aggregation = aggregationStrategy.getSecond();
    }

    /**
     * Use this constructor when the semantics is no concern. A scale must be passed and the
     * aggregator will reject {@link #add(Object, ILocator)} if the scale is not regular and the
     * locator is null. The aggregation can also be null, in which case the appropriate one for the
     * observable will be used.
     * 
     * @param aggregation
     * @param scale
     */
    public Aggregator(Aggregation aggregation, IScale scale) {
        this.aggregation = aggregation;
        this.scale = scale;
    }

    /**
     * Add a single value. If the aggregator has been created with a scale and the scale has a
     * regular space, the locator can be null and the first subdivision, or the entire scale, will
     * be used as a reference.
     * 
     * @param value
     * @param locator
     */
    public void add(Object value, ILocator locator) {
        
        if (Observations.INSTANCE.isData(value)) {

            if (value instanceof Collection) {
                for (Object o : ((Collection<?>) value)) {
                    add(o, locator);
                }
            } else if (value instanceof Number) {

                double dval = (this.unit == null || (!this.unit.isContextual() || locator == null))
                        ? ((Number) value).doubleValue()
                        : this.unit.convert((Number) value, locator).doubleValue();

                sum += dval;
                if (Double.isNaN(min) || min > dval) {
                    min = dval;
                }
                if (Double.isNaN(max) || max < dval) {
                    max = dval;
                }
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
    }

    public void reset() {
        // addenda.clear();
        sum = 0;
        count = 0;
        counts.clear();
    }

    /**
     * Perform the final aggregation.
     * 
     * @param iMonitor
     * @return
     */
    public Object aggregate() {

        if (aggregation == null) {
            return null;
        }

        switch(aggregation) {
        case ANY_PRESENT:
            return counts.size() > 1 || count > 0;
        case COUNT:
            return (double)count;
        case MAJORITY:
            Object ret = null;
            int cnt = -1;
            for (Object o : counts.keySet()) {
               if (counts.get(o) > cnt) {
                   cnt = counts.get(o);
                   ret = o;
               }
            }
            return ret;
        case MAX:
            return max;
        case MEAN:
            return count > 0 ? (sum / count) : null;
        case MIN:
            return min;
        case STD:
            throw new KlabUnimplementedException("std aggregation still unsupported on stable aggregator");
        case SUM:
            return count == 0 ? null : sum;
        default:
            break;
        }

        return null;
    }

    /*
     * Reentrant. Use with caution.
     * 
     * @param objects
     * @return
     */
    public Object aggregate(Collection<?> objects, ILocator locator) {
        counts.clear();
        sum = 0;
        count = 0;
        for (Object o : objects) {
            add(o, locator);
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

        switch(aggregation) {
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
            if (Observations.INSTANCE.isNodata(value)) {
                return null;
            }
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
            if (Observations.INSTANCE.isNodata(value)) {
                return null;
            }
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
            if (Observations.INSTANCE.isNodata(value)) {
                return null;
            }
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
            if (Observations.INSTANCE.isNodata(value)) {
                return null;
            }
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
            if (Observations.INSTANCE.isNodata(value)) {
                return null;
            }
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

}
