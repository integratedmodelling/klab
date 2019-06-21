package org.integratedmodelling.klab.utils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.klab.api.data.Aggregation;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;

public class AggregationUtils {
    
    public static Object aggregate(List<Object> values, Aggregation aggregation, IMonitor monitor) {

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
            return values.size() > 0;
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

    public static Object sum(List<Object> values, IMonitor monitor) {
        boolean dataWarning = false;
        double sum = 0;
        for (Object value : values) {
            if (value instanceof Number) {
                sum += ((Number) value).doubleValue();
            } else if (!dataWarning) {
                dataWarning = true;
                monitor.warn(
                        "neighborhood analysis: one or more values found to be of incompatible type during aggregation");
            }
        }
        return sum;
    }

    public static Object std(List<Object> values, IMonitor monitor) {
        boolean dataWarning = false;
        double sum = 0;
        int n = 0;
        for (Object value : values) {
            if (value instanceof Number) {
                sum += ((Number) value).doubleValue();
                n++;
            } else if (!dataWarning) {
                dataWarning = true;
                monitor.warn(
                        "neighborhood analysis: one or more values found to be of incompatible type during aggregation");
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

    public static Object min(List<Object> values, IMonitor monitor) {
        boolean dataWarning = false;
        Double min = null;
        for (Object value : values) {
            if (value instanceof Number) {
                if (min == null || min > ((Number) value).doubleValue()) {
                    min = ((Number) value).doubleValue();
                }
            } else if (!dataWarning) {
                dataWarning = true;
                monitor.warn(
                        "neighborhood analysis: one or more values found to be of incompatible type during aggregation");
            }
        }
        return min;
    }

    public static Object mean(List<Object> values, IMonitor monitor) {
        boolean dataWarning = false;
        double sum = 0;
        int n = 0;
        for (Object value : values) {
            if (value instanceof Number) {
                sum += ((Number) value).doubleValue();
                n++;
            } else if (!dataWarning) {
                dataWarning = true;
                monitor.warn(
                        "neighborhood analysis: one or more values found to be of incompatible type during aggregation");
            }
        }
        return sum / (double) n;
    }

    public static Object max(List<Object> values, IMonitor monitor) {
        boolean dataWarning = false;
        Double max = null;
        for (Object value : values) {
            if (value instanceof Number) {
                if (max == null || max < ((Number) value).doubleValue()) {
                    max = ((Number) value).doubleValue();
                }
            } else if (!dataWarning) {
                dataWarning = true;
                monitor.warn(
                        "neighborhood analysis: one or more values found to be of incompatible type during aggregation");
            }
        }
        return max;
    }

    public static Object dominant(List<Object> values, IMonitor monitor) {

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

    public static Object count(List<Object> values, IMonitor monitor) {
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

    public static Aggregation getAggregation(IObservable observable) {
        switch (observable.getObservationType()) {
        case CLASSIFICATION:
        case VERIFICATION:
            return Aggregation.MAJORITY;
        case QUANTIFICATION:
            return observable.getType().is(Type.EXTENSIVE_PROPERTY) ? Aggregation.SUM : Aggregation.MEAN;
        case DETECTION:
        case INSTANTIATION:
        case SIMULATION:
            break;
        }
        return null;
    }

}
