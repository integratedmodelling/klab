package org.integratedmodelling.klab.components.geospace.processing;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.integratedmodelling.klab.Observations;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.classification.IDataKey;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IKnowledge;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.IComputationContext;
import org.integratedmodelling.klab.utils.StringUtils;

/**
 * Classifies a set of states into one map of classes corresponding to different
 * configurations, discretizing each state if necessary.
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

		public double getValueOf(IConcept k) {
			MapDescriptor mds = stateIndex.get(k);
			if (mds == null) {
				return 0;
			}
			return 0
					
					; // TODO! ((Classification)
						// mds.discretization).getNumericCode(classifiers.get(mds.index));
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
			} else if (s.getObservable().getArtifactType() == IArtifact.Type.NUMBER
					&& s.getObservable().getClassifier() == null) {
				this.discretization = Observations.INSTANCE.discretize(s, locator, maxBinsPerState);
			}
		}

		@Override
		public String toString() {
			return "D/" + index + "/" + state.getObservable();
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
	// private ILocator locator;
	Map<String, MapClass> classCatalog = new HashMap<>();
	List<MapClass> classes = new ArrayList<>();
	Map<IKnowledge, MapDescriptor> stateIndex = new HashMap<>();
	private IComputationContext context;

	/**
	 * Build the necessary discretizations. Check getStatesCount() before calling
	 * classify() to ensure we have at least the desired number of states.
	 * 
	 * @param states
	 * @param maxBinsPerState
	 * @param monitor
	 * @param locators
	 */
	public MapClassifier(Collection<IState> states, int maxBinsPerState, IComputationContext context,
			ILocator locator) {

		// this.locator = locator;
		this.maxBinsPerState = maxBinsPerState;
		this.context = context;

		int i = 0;
		for (IState s : states) {
			MapDescriptor md = new MapDescriptor(s, locator);
			if (md.discretization != null || md.useRanks) {
				this.states.add(md);
				stateIndex.put(s.getObservable().getType(), md);
				md.index = i++;
			} else {
				context.getMonitor()
						.warn("discretizer: state " + s.getObservable().getType() + " could not be discretized");
			}
		}

		if (states.size() > 0) {
			this.scale = states.iterator().next().getScale();
		}
	}

	public int getStatesCount() {
		return this.states.size();
	}

	public int getClass(int offset) {
		return index[offset];
	}

	public int classify() {

		// IScale.Index index = scale.getIndex(locators);
		// this.index = new int[index.size()];

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
	 * Redistribute values inside a passed state. Pass the values in the same order
	 * as the classes.
	 * 
	 * @param state
	 * @param values
	 * 
	 */
	public void distributeResults(IState state, double[] values) {

		int n = 0;
		for (ILocator i : scale) {
			state.set(i, values[classes.get(index[n]).index]);
			n++;
		}
	}

}
