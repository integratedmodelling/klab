package org.integratedmodelling.klab.extensions.groovy.model

import org.integratedmodelling.klab.Observations
import org.integratedmodelling.klab.api.data.ILocator
import org.integratedmodelling.klab.api.knowledge.IConcept
import org.integratedmodelling.klab.api.observations.IState
import org.integratedmodelling.klab.api.provenance.IArtifact
import org.integratedmodelling.klab.exceptions.KlabUnimplementedException
import org.integratedmodelling.klab.exceptions.KlabValidationException
import org.integratedmodelling.klab.rest.StateSummary
import org.integratedmodelling.klab.engine.debugger.Statistics

class State extends Observation<IState> {

	// only used to tag a state for reduction when transformed
	IConcept dataReduction = null;
	Map<String, Double> reductions = new HashMap<>();

	State(IState obs, Binding binding) {
		super(obs, binding);
	}

    State(String id, Binding binding) {
        super(id, binding)
    }
    
	String toString() {
		return unwrap().toString();
	}
	
	private StateSummary getStateSummary() {
		return Observations.INSTANCE.getStateSummary(unwrap(), getRuntimeScope().getScale());
	}

	public Collection<IConcept> getCategories() {
		return ((org.integratedmodelling.klab.components.runtime.observations.State)unwrap()).getCategories();
	}

	public double getArea(Object value, String unit) {
		return ((org.integratedmodelling.klab.components.runtime.observations.State)unwrap()).getArea(value, unit);
	}

    public Statistics computeStatistics(Object locator) {
        return ((org.integratedmodelling.klab.components.runtime.observations.State)unwrap()).computeStatistics(locator);
    }
    
	/**
	 * Invert the state values if the state is numeric and has values.
	 * 
	 * @return
	 */
	public State invert() {
		if (unwrap().type == IArtifact.Type.NUMBER) {
			def summary = getStateSummary();
			if (!summary.isDegenerate()) {
				for (ILocator locator : getRuntimeScope().getScale()) {
					Double d = unwrap().get(locator, Double.class);
					if (d != null && !Double.isNaN(d)) {
						d = summary.getRange().get(1) - d + summary.getRange().get(0);
						unwrap().set(locator, d);
					}
				}
			}
		}
		return this;
	}

	public State normalize() {
		if (unwrap().type == IArtifact.Type.NUMBER) {
			def summary = getStateSummary();
			if (!summary.isDegenerate()) {
				for (ILocator locator : getRuntimeScope().getScale()) {
					Double d = unwrap().get(locator, Double.class);
					if (d != null && !Double.isNaN(d)) {
						d = (d - summary.getRange().get(0)) / (summary.getRange().get(1) - summary.getRange().get(0));
						unwrap().set(locator, d);
					}
				}
			}
		}
		return this;
	}

	
	/**
	 * Return the state with the appropriate type, aggregating as necessary. If we
	 * have a time pointer, use that.
	 * 
	 * @param cls
	 * @return
	 */
	Object asType(Class cls) {

		Number nret = null;

		setTimePointer();

		if (cls == Number) {
			return aggregateNumbers();
		} else if (cls == Double || cls == double) {
			nret = aggregateNumbers();
			return nret ? nret.doubleValue() : (double)0.0;
		} else if (cls == Long || cls == long) {
			nret = aggregateNumbers();
			return nret ? nret.longValue() : (long)0;
		} else if (cls == Integer || cls == int) {
			nret = aggregateNumbers();
			return nret ? nret.intValue() : 0;
		} else if (cls == Float || cls == float) {
			nret = aggregateNumbers();
			return nret ? nret.floatValue() : (float)0.0;
		} else if (cls == Boolean || cls == boolean) {
			Object zio = aggregateMajority(Boolean);
			return (zio instanceof Boolean) ? (Boolean)zio : false;
		} else if (cls == IConcept) {
			Object zio = aggregateMajority(IConcept);
			return (zio instanceof IConcept) ? (IConcept)zio : null;
		} else if (cls == double[]) {
			//            return getDoubleData();
		} else if (cls == long[]) {
			//			return (long)aggregate();
		} else if (cls == int[]) {
			//			return (int)aggregate();
		} else if (cls == float[]) {
			//			return (float)aggregate();
		} else if (cls == boolean[]) {
			// TODO
		} else if (cls == IConcept[]) {
			// TODO
		}
		return null;
	}

	//    double[] getDoubleData() {
	//        VisualizationFactory.get().getStateDataAsNumbers((IState)obs,
	//                Collections.singleton(timePointer));
	//    }

	/**
	 * Assign to state as a whole using state << value. If we are in a 
	 * transition handler, assign to the value AFTER the current transition and
	 * set the state's time pointer to the transition just seen.
	 * 
	 * @param value
	 * @return
	 */
	def leftShift(Object value) {
		throw new KlabUnimplementedException("groovy.State:leftShift");
		setTimePointer();
		States.set(unwrap(), value, timePointer);
	}

	def getAreaHistogram(String unit) {
		return ((org.integratedmodelling.klab.components.runtime.observations.State)unwrap()).getAreaHistogram(unit);
	}

	def setTimePointer() {
		// reset this to force recompute
		summary = null;
		throw new KlabUnimplementedException("groovy.State:setTimePointer");
		//        ITransition tr = getTransition();
		//        if (tr != null) {
		//            if (timePointer == null || tr.getTimeIndex() > timePointer.getTimeIndex()) {
		////                if (((org.integratedmodelling.common.states.State)obs).isCopyStateAtTransitions()) {
		////                    States.copyStateToNextTransition((IState)obs, timePointer);
		////                }
		//                timePointer = tr;
		//            }
		//        }
	}

	/**
	 * Support for state[n]
	 * 
	 * @param offset
	 * @return
	 */
	def getAt(int offset) {
		throw new KlabUnimplementedException("groovy.State:getAt");
		//        States.get((IState)obs, offset, getTransition());
	}

	def at(Observation o) {
		throw new KlabUnimplementedException("groovy.State:at");
		//        return new State(States.getView((IState)obs, o.obs, dataReduction), binding);
	}

	/**
	 * Support for state[n] = value
	 * 
	 * @param offset
	 * @param value
	 * @return
	 */
	def putAt(int offset, Object value) {
		throw new KlabUnimplementedException("groovy.State:putAt");
		//        States.set(((IState)obs), value, offset);
	}

	/**
	 * Support for (state * observation) -> returning state view
	 * 
	 * @param o
	 * @return
	 */
	def multiply(Object o) {
		throw new KlabUnimplementedException("groovy.State:multiply");
		//        if (o instanceof Observation) {
		//            return new State(States.getView(obs, ((Observation)o).obs), binding);
		//        }
		//        if (o instanceof Scale) {
		//            return new State(States.getView(obs, ((Scale)o).scale), binding);
		//        }
		//        if (o instanceof IScale) {
		//            return new State(States.getView(obs, (IScale)o), binding);
		//        }
	}

	def div(Object o) {
		/*
		 * must be a data reduction trait 
		 * TODO implement (in aggregation strategy)
		 */
		if (!(o instanceof Concept)) {
			throw new KlabValidationException("the division operator on a state can only be used with a data reduction trait");
		}
		State ret = new State(unwrap(), binding);
		ret.dataReduction = ((Concept)o).concept;
		return ret;
	}

	/**
	 * Iterable offsets over the current transition: foreach (n : state.offsets) {}
	 * 
	 * @return
	 */
	def getOffsets() {
		throw new KlabUnimplementedException("groovy.State:getOffsets");
		//        def locs = new ILocator[1];
		//        locs[0] = getTransition();
		//        return obs.getScale().getIndex(locs);
	}

	/**
	 * TODO revise; use native States functions.
	 * @return
	 */

	Number aggregateNumbers() {

		throw new KlabUnimplementedException("groovy.State:aggregateNumbers");

		//        double t = Double.NaN;
		//        if (((IState)obs).getObserver() instanceof INumericObserver) {
		//            double[] d = VisualizationFactory.getStateDataAsNumbers((IState)obs, Collections.singleton(timePointer));
		//            int n = 0;
		//            for (double v in d) {
		//                if (!Double.isNaN(v)) {
		//                    if (Double.isNaN(t)) {
		//                        t = v;
		//                    } else {
		//                        t += v;
		//                    }
		//                    n ++
		//                }
		//            }
		//
		//            // FIXME reintegrate
		//            //            if (n > 0 && !((IState)obs).getObserver().isExtensive()) {
		//            t = t/(double)n;
		//            //            }
		//        }
		//        return t;
	}

	Object aggregateMajority(Class cls) {
		Object ret = null;
		return ret;
	}

	def getSum() {
		long idx = getTimeIndex();
		if (reductions.containsKey("sum_" + idx)) {
			return reductions.get("sum_" + idx);
		}
		double t = Double.NaN;
		if (unwrap().getType() == IArtifact.Type.NUMBER) {
			t = getStateSummary().getSum()
		}
		reductions.put("sum_" + idx, t);
		return t;
	}

	def getAvg() {
		long idx = getTimeIndex();
		if (reductions.containsKey("avg_" + idx)) {
			return reductions.get("avg_" + idx);
		}
		double t = Double.NaN;
		if (unwrap().getType() == IArtifact.Type.NUMBER) {
			t = getStateSummary().getMean()
		}
		reductions.put("avg_" + idx, t);
		return t;
	}

	def getMin() {
		long idx = getTimeIndex();
		if (reductions.containsKey("min_" + idx)) {
			return reductions.get("min_" + idx);
		}
		double t = Double.NaN;
		if (unwrap().getType() == IArtifact.Type.NUMBER) {
			t = getStateSummary().getRange().get(0)
		}
		reductions.put("min_" + idx, t);
		return t;
	}

	def getMax() {
		long idx = getTimeIndex();
		if (reductions.containsKey("max_" + idx)) {
			return reductions.get("max_" + idx);
		}
		double t = Double.NaN;
		if (unwrap().getType() == IArtifact.Type.NUMBER) {
			t = getStateSummary().getRange().get(1)
		}
		reductions.put("max_" + idx, t);
		return t;
	}

	def covers(Observation observation) {
		return getCoverageOf(observation) > 0;
	}
    
	def getCoverageOf(Observation observation) {

		throw new KlabUnimplementedException("groovy.State:aggregateNumbers");

		// TODO return the proportion of coverage that intersects an ACTIVE state
		// of ours in the current transition and at matching other extents
		//        if (((IState)obs).getObserver() instanceof IPresenceObserver) {
		//
		//            IState view = States.getView((IState)obs, observation.obs);
		//            // TODO this shouldn't be necessary
		//            boolean inView = (Boolean) view.getValue(0);
		//            if (!inView) {
		//                // to fool the optimizer; no worries for the useless code.
		//                inView = true;
		//            }
		//            double percentInView = 1.0;
		//            if (inView && view.getMetadata().contains(IState.Mediator.SPACE_TOTAL_VALUES)) {
		//                percentInView = (double) view.getMetadata().getInt(IState.Mediator.SPACE_VALUE_SUM) / (double) view.getMetadata().getInt(IState.Mediator.SPACE_TOTAL_VALUES);
		//            }
		//            return percentInView;
		//
		//        } else {
		//            // TODO
		//            throw new KlabRuntimeException("getCoverageOf is implemented only for boolean states at the moment", DefaultAction.getArtifact(binding));
		//        }
		//
		//        return 1.0;
	}
}
