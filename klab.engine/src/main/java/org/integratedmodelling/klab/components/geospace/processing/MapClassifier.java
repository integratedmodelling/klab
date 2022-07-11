package org.integratedmodelling.klab.components.geospace.processing;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.integratedmodelling.klab.Observations;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.classification.IDataKey;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.data.classification.Discretization;
import org.integratedmodelling.klab.exceptions.KlabUnimplementedException;
import org.integratedmodelling.klab.utils.StringUtils;

/**
 * Classifies a set of states into one map of classes corresponding to different configurations,
 * discretizing each state if necessary.
 * 
 * @author Ferd
 *
 */
public class MapClassifier {

    public class MapClass {

        /*
         * classifier N per descriptor
         */
        List<Integer> classifiers;
        int index;
        String name;

        public int getIndex() {
            return index;
        }

        @Override
        public String toString() {
            return index + ":" + classifiers;
        }

        public double getValueOf(String k) {
            MapDescriptor mds = stateIndex.get(k);
            if (mds == null) {
                return 0;
            }
            return mds.getValue(classifiers.get(mds.index));
        }
    }

    class MapDescriptor {

        IState state;
        IDataKey discretization = null;
        int index;
        boolean useRanks = false;
        Map<Integer, Integer> rankMap;

        public MapDescriptor(IState s, ILocator locator) {

            this.state = s;

            if (s.getDataKey() != null) {
                this.discretization = s.getDataKey();
            } else if (s.getObservable().getArtifactType() == IArtifact.Type.NUMBER) {
                this.discretization = Observations.INSTANCE.discretize(s, locator, maxBinsPerState);
            } else {
                // I guess boolean is left
                throw new KlabUnimplementedException("map classification not implemented for type "
                        + s.getObservable().getArtifactType() + ": please notify developers");
            }
        }

        @Override
        public String toString() {
            return "D/" + index + "/" + state.getObservable();
        }

        public double getValue(int n) {
            if (discretization instanceof Discretization) {
                return ((Discretization) discretization).getRange(n).getMidpoint();
            }
            return n;
        }

        public int getClass(ILocator offset) {

            Object o = state.get(offset);
            if (o == null) {
                return -1;
            }
            if (useRanks) {
                Integer n = o instanceof Number ? ((Number) o).intValue() : null;
                if (n != null) {
                    if (rankMap.containsKey(n)) {
                        return rankMap.get(n);
                    } else {
                        int newv = rankMap.size();
                        rankMap.put(n, newv);
                        return newv;
                    }
                } else {
                    return -1;
                }
            }
            return this.discretization.reverseLookup(o);
        }
    }

    IScale scale;
    private List<MapDescriptor> states = new ArrayList<>();
    private int maxBinsPerState;
    int[] index;
    Map<String, MapClass> classCatalog = new HashMap<>();
    List<MapClass> classes = new ArrayList<>();
    Map<String, MapDescriptor> stateIndex = new HashMap<>();

    /**
     * Build the necessary discretizations. Check getStatesCount() before calling classify() to
     * ensure we have at least the desired number of states.
     * 
     * @param states
     * @param maxBinsPerState
     * @param monitor
     * @param locators
     */
    public MapClassifier(Collection<IState> states, int maxBinsPerState, IContextualizationScope scope) {

        this.maxBinsPerState = maxBinsPerState;
        int i = 0;
        for (IState s : states) {
            MapDescriptor md = new MapDescriptor(s, scope.getScale());
            if (md.discretization != null || md.useRanks) {
                this.states.add(md);
                stateIndex.put(s.getObservable().getName(), md);
                md.index = i++;
            } else {
                scope.getMonitor().warn("discretizer: state " + s.getObservable().getType() + " could not be discretized");
            }
        }
        this.scale = scope.getScale();
        this.index = new int[(int) scope.getScale().size()];
    }

    public int getStatesCount() {
        return this.states.size();
    }

    public int getClass(int offset) {
        return index[offset];
    }

    public int classify() {

        // prepare the fastest int array we can use as key
        List<Integer> mc = new ArrayList<>();
        for (int i = 0; i < states.size(); i++) {
            mc.add(-1);
        }

        int idx = 0;
        for (ILocator offset : scale) {

            int i = 0;
            boolean nodata = false;
            for (MapDescriptor md : states) {
                int n = md.getClass(offset);
                if (n < 0) {
                    nodata = true;
                    break;
                }
                mc.set(i++, n);
            }

            this.index[idx++] = nodata ? 0 : getMapClass(mc);
        }

        return classes.size();
    }

    private int getMapClass(List<Integer> mc) {

        String nnc = StringUtils.join(mc, '|');

        MapClass clas = classCatalog.get(nnc);
        if (clas == null) {
            clas = new MapClass();
            clas.classifiers = new ArrayList<>(mc);
            classes.add(clas);

            clas.index = classes.size() - 1;
            classCatalog.put(nnc, clas);
        }

        return clas.index;
    }

    public Collection<MapClass> getClasses() {
        return classes;
    }

    /**
     * Redistribute values inside a passed state. Pass the values in the same order as the classes.
     * 
     * @param state
     * @param values
     * 
     */
    public void distributeResults(IState state, double[] values) {

        int n = 0;
        for (ILocator i : scale) {
            if (index[n] == 0) {
                state.set(i, null);
            } else {
                state.set(i, values[classes.get(index[n]).index]);
            }
            n++;
        }
    }

}
