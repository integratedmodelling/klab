package org.integratedmodelling.klab.extensions.groovy.model

import org.integratedmodelling.kim.api.IKimConcept
import org.integratedmodelling.klab.api.knowledge.IConcept
import org.integratedmodelling.klab.api.observations.IDirectObservation
import org.integratedmodelling.klab.api.observations.IObservation
import org.integratedmodelling.klab.api.observations.IState
import org.integratedmodelling.klab.api.observations.ISubject
import org.integratedmodelling.klab.api.observations.scale.IExtent
import org.integratedmodelling.klab.api.observations.scale.IScale
import org.integratedmodelling.klab.exceptions.KlabUnimplementedException
import org.integratedmodelling.klab.exceptions.KlabValidationException
import org.integratedmodelling.klab.extensions.groovy.ActionBase
import org.integratedmodelling.klab.extensions.groovy.Utils
import org.integratedmodelling.klab.extensions.groovy.sets.ObservationSet
import org.integratedmodelling.klab.extensions.groovy.sets.RoledObservationSet

class DirectObservation<T extends IDirectObservation> extends Observation<IDirectObservation> {

	DirectObservation(String id, Binding binding) {
		super(id, binding);
		name = unwrap().name;
	}

	DirectObservation(IDirectObservation obs, Binding binding) {
		super(obs, binding);
		name = obs.name;
	}

	def deactivate() {
		unwrap().setActive(false);
	}

	private void setVar(String s, Object o) {
		if (o instanceof ObservationSet) {
			o = ((ObservationSet)o).resolve();
			if (o instanceof List) {
				if (((List)o).size() == 1) {
					o = ((List)o).get(0);
				}
			}
		}
		((org.integratedmodelling.klab.components.runtime.observations.DirectObservation)obs).setVar(s,o);
	}

	//    private Object getVar(String s) {
	//
	//        def var = ((org.integratedmodelling.klab.components.runtime.observations.DirectObservation)obs).getVar(s);
	//        // reset the transition if we have just retrieved an observation, possibly saved at an earlier one.
	//        // TODO check if we should transfer (or merge?) all the bindings instead and anyway (for anything
	//        // that has bindings, using an interface).
	//        if (var instanceof Observation && getTransition() != null) {
	//			throw new KlabUnimplementedException("groovy.DirectObservation:getVar");
	//            var.binding.setVariable("_transition", getTransition());
	////            var.binding.setVariable("now", new Transition(getTransition()));
	//        }
	//        return var;
	//    }

	public String toString() {
		return unwrap() == null ? "[null observation]" : unwrap().toString();
	}

	//    /**
	//     * Set user data.
	//     * Change names with a prefix to avoid potential conflicts.
	//     *
	//     * @param s
	//     * @param o
	//     * @return
	//     */
	//    def set(String s, Object o) {
	//        setVar("___u_" + s, o);
	//    }
	//
	//    /**
	//     * Retrieve user data.
	//     * Change names with a prefix to avoid potential conflicts.
	//     *
	//     * @param s
	//     * @return
	//     */
	//    def get(String s) {
	//        def ret = getVar("___u_" + s);
	//        if (ret == null) {
	//            ret = getVar(s);
	//        }
	//        return ret;
	//    }

	//    def rename(String name) {
	//        ((org.integratedmodelling.klab.components.runtime.observations.DirectObservation)obs).setName(name);
	//    }

	/**
	 * Select child objects by observable(s) and/or name(s)
	 * 
	 * @param args
	 * @return
	 */
	def select(Object[] args) {

		Concept roleSelector = null;
		// ensure observations were made, and allow rescale operations
		if (args == null) {
			throw new KlabValidationException("observation selection on " + unwrap() + " must be called with non-null arguments");
		} else {
			for (Object o : args) {
				if (o instanceof Concept) {
					if (o.concept.is(IKimConcept.Type.ROLE) || ((Concept)o).roleContext != null) {
						if (roleSelector != null || args.length > 1) {
							throw new KlabValidationException("wrong role selection syntax: only one role is admitted", ActionBase.getArtifact(binding));
						}
						roleSelector = o;
					} else {
						this << o;
					}
				}
			}
		}
		if (roleSelector != null) {
			return new RoledObservationSet(context: this, recursive: false, children: true, role: roleSelector.concept, roleContext: roleSelector.roleContext);
		}

		return new ObservationSet(context: this, recursive: false, children: true, args: args);
	}

	def has(Object[] args) {

		def concept = null;
		for (arg in args) {
			if (arg instanceof Concept) {
				concept = ((Concept)arg).concept;
			}
		}

		if (concept == null) {
			return false;
		}

		if (concept.is(IKimConcept.Type.QUALITY)) {
			return ((org.integratedmodelling.klab.components.runtime.observations.DirectObservation)unwrap()).getExistingState(concept);
		}

		/*
		 * TODO other situations
		 */

		return false;
	}

	/**
	 * Find states to aggregate of stateConcept (or having that role) in all the sub-agents of
	 * agentConcept and create one state in self with the same semantics, containing the 
	 * aggregated value of all agents. If the state already exists, simply update the value
	 * of the state with any new values if a transition is occurring.
	 * 
	 * Just call the version with options.
	 * 
	 * @param stateConcept
	 * @param agentConcept
	 * @return the aggregating state, or null if nothing is available or the concept is null
	 */
	def aggregateStates(Concept stateConcept, ObservationSet agents) {
		return aggregateStates(null, stateConcept, agents);
	}

	def aggregateStates(Map options, Concept stateConcept, ObservationSet agents) {

		//        if (getTransition() != null) {
		//            throw new KlabValidationException("cannot define aggregated states during a transition");
		//        }

		if (stateConcept == null) {
			return null;
		}

		def ags = agents.resolveToObservationCollection();
		if (!ags) {
			return null;
		}

		def ret = ((org.integratedmodelling.klab.components.runtime.observations.DirectObservation)unwrap()).
				createAggregatedState(stateConcept.getConcept(), ags);

		return ret instanceof IState ? new State(ret, binding) : null;
	}

	def isSubject() {
		return unwrap().observable.is(IKimConcept.Type.SUBJECT);
	}

	def isProcess() {
		return unwrap().observable.is(IKimConcept.Type.PROCESS);
	}

	def isEvent() {
		return unwrap().observable.is(IKimConcept.Type.EVENT);
	}

	def isCountable() {
		return unwrap().observable.is(IKimConcept.Type.COUNTABLE);
	}

	/**
	 * Select sibling objects. For now does not make observations.
	 * @param args
	 * @return
	 */
	def siblings(Object[] args) {
		return new ObservationSet(context: this, recursive: false, children: false, args: args);
	}

	def color(String c) {
		throw new KlabUnimplementedException("groovy.DirectObservation:color");
		//        obs.getMetadata().put(IMetadata.KLAB_LINE_COLOR, ColorUtils.getHexFromColorName(c));
		//        obs.getMetadata().put(IMetadata.KLAB_FILL_COLOR, ColorUtils.getHexFromColorName(c));
		return this;
	}

	def color(String line, String fill) {
		throw new KlabUnimplementedException("groovy.DirectObservation:color");
		//        obs.getMetadata().put(IMetadata.KLAB_LINE_COLOR, ColorUtils.getHexFromColorName(line));
		//        obs.getMetadata().put(IMetadata.KLAB_FILL_COLOR, ColorUtils.getHexFromColorName(fill));
		return this;
	}

	def color(String line, String fill, float opacity) {
		throw new KlabUnimplementedException("groovy.DirectObservation:color");
		//        obs.getMetadata().put(IMetadata.KLAB_LINE_COLOR, ColorUtils.getHexFromColorName(line));
		//        obs.getMetadata().put(IMetadata.KLAB_FILL_COLOR, ColorUtils.getHexFromColorName(fill));
		//        obs.getMetadata().put(IMetadata.KLAB_OPACITY, opacity);
		return this;
	}

	def opacity(float opacity) {
		throw new KlabUnimplementedException("groovy.DirectObservation:opacity");
		//        obs.getMetadata().put(IMetadata.KLAB_OPACITY, opacity);
		return this;
	}

	/**
	 * Create a relationship of the passed type between self and the 
	 * target. Pass extents, scales, other objects to contextualize it.
	 * Return the relationship so it can be named, colored or anything.
	 * 
	 * @param concept
	 * @param target
	 * @param args
	 * @return
	 */
	def connect(Concept concept, DirectObservation target, Object ... args) {

		throw new KlabUnimplementedException("groovy.DirectObservation:connect");

		if (!concept.isRelationship()) {
			throw new KlabValidationException("can only create connections with relationship concepts: " + concept, ActionBase.getArtifact(binding));
		}

		if (!isSubject() || !target.isSubject()) {
			throw new KlabValidationException("cannot connect non-subjects in a relationship", ActionBase.getArtifact(binding));
		}

		//        if (!isSibling(target)) {
		//            throw new KlabRuntimeException("cannot connect two non-sibling subjects in a relationship");
		//        }

		IScale scale = Utils.extractScale(args);

		ISubject ctx = ((ISubject) unwrap().getContext());

		// functional relationships get time from source by default
		//        if (scale.getTime() == null && obs.getScale().getTime() != null && NS.isFunctionalRelationship(concept.concept)) {
		//            ((org.integratedmodelling.engine.modelling.runtime.Scale)scale).mergeExtent(obs.getScale().getTime(), true);
		//        }
		//
		//        IRelationship ret = ctx.connect((IActiveSubject)obs, (IActiveSubject)target.unwrap(), concept.concept, scale);
		//        ((org.integratedmodelling.engine.modelling.runtime.DirectObservation)ctx).addActionGeneratedObservation(ret);

		return new Relationship(ret, binding);
	}


	/**
	 * Select child objects by observable(s) and/or name(s), recursing 
	 * within children. Doesn't make observations for now.
	 *
	 * @param args
	 * @return
	 */
	def selectAll(Object[] args) {
		return new ObservationSet(context: this, recursive: true, children: true, args: args);
	}

	//    def wrap(IDirectObservation obs) {
	//
	//        for (state in obs.getStates()) {
	//            setVar(getFormalName(state), new State(state, binding));
	//        }
	//
	//        setVar("space", obs.getScale().getSpace() == null ? null : new Space(obs.getScale().getSpace(), binding));
	//        setVar("time", obs.getScale().getTime() == null ? null : new Time(obs.getScale().getTime(), binding));
	//        setVar("scale", new Scale(obs.getScale(), binding));
	//    }

	def requireState(IConcept concept) {
		throw new KlabUnimplementedException("groovy.DirectObservation:requireState");

		//        ITask task = obs.observe(concept);
		//        IContext context = task.finish();
	}

	def getFormalName(IObservation observation) {
		//        if (model != null) {
		//            return model.getNameFor(observation.getObservable().getLo);
		//        }
		return observation.getObservable().getName();
	}

	def distanceTo (Observation observation) {
		unwrap().space.getStandardizedDistance(observation.unwrap().space)
	}

	def leftShift(observation) {

		throw new KlabUnimplementedException("groovy.DirectObservation:leftShift");

		Collection<IExtent> alternativeReality = null;
		Collection<String> scenarios = null;

		if (observation instanceof ObservationSet) {
			observation = ((ObservationSet)observation).resolve();
		}

		//        if (observation instanceof Concept) {
		//            // ACHTUNG may contain a different scale
		//            if (!((Concept)observation).searchExtents.isEmpty()) {
		//                alternativeReality = ((Concept)observation).searchExtents;
		//            }
		//            if (!((Concept)observation).scenarios.isEmpty()) {
		//                scenarios = ((Concept)observation).scenarios;
		//            }
		//            observation = Roles.assignRoles(((Concept)observation).concept, obs);
		//        }
		//
		//        if (observation instanceof StateInfo) {
		//
		//            String id = ((StateInfo)observation).name;
		//
		//            /*
		//             * TODO must quickly return the state if it's there, without re-observing it, and if
		//             * necessary mediating it to the required unit/scale etc.
		//             *
		//             * if ((..)obs).findState(observationSemantics) { return new State (States.mediate(state, getObserver()), binding) }
		//             */
		//
		//            if (((StateInfo)observation).getValue() != null) {
		//
		//                IState state = ((IActiveDirectObservation)obs).getState(((StateInfo)observation).getObservable(obs));
		//                // ensure we remain in charge of the temporal evolution by copying the previous state at new transition
		//                ((org.integratedmodelling.common.states.State)state).setCopyStateAtTransitions(true);
		//                States.set(state, ((StateInfo)observation).getValue());
		//                State wrapped = new State(state, binding);
		//                setVar(id, wrapped);
		//                return wrapped;
		//
		//            } else {
		//
		//                /*
		//                 * TODO observe, then wrap in mediator if necessary
		//                 */
		//                IObservableSemantics observable = ((StateInfo)observation).getObservable();
		//                for (o in _observe(observable.getType(), alternativeReality, scenarios)) {
		//                    if (o instanceof IState) {
		//
		//                        IState state = (IState)o;
		//
		//                        /*
		//                         * TODO mediate! tedious....
		//                         */
		//
		//                        State wrapped = new State(state, binding);
		//                        setVar(id, wrapped);
		//                        return wrapped;
		//                    } else if (o instanceof State) {
		//                        setVar(id, o);
		//                        return o;
		//                    }
		//                }
		//            }
		//        } else if (observation instanceof SubjectInfo) {
		//
		//            if (obs instanceof ISubject) {
		//
		//                IConcept cc = Roles.assignRoles(observation.getConcept().getConcept(), obs);
		//                IScale scl = observation.makeScale();
		//                ISubject subject = ((Subject)obs).newSubject(new ObservableSemantics(cc), scl, observation.getName(), null);
		//                ((org.integratedmodelling.engine.modelling.runtime.DirectObservation)obs).addActionGeneratedObservation(subject);
		//                return new DirectObservation(subject, binding);
		//
		//            } else {
		//                throw new KlabValidationException("cannot add a subject to anything but another subject", DefaultAction.getArtifact(binding));
		//            }
		//
		//        } else if (observation instanceof EventInfo) {
		//
		//            if (obs instanceof ISubject) {
		//
		//                IConcept cc = Roles.assignRoles(observation.getConcept().getConcept(), obs);
		//                IScale scl = observation.makeScale();
		//                IEvent event = ((Subject)obs).newEvent(new ObservableSemantics(cc), scl, observation.getName());
		//                ((org.integratedmodelling.engine.modelling.runtime.DirectObservation)obs).addActionGeneratedObservation(event);
		//                return new DirectObservation(event, binding);
		//
		//            } else {
		//                throw new KlabValidationException("cannot add an event to anything but a subject", DefaultAction.getArtifact(binding));
		//            }
		//
		//        } else if (observation instanceof ProcessInfo) {
		//        } else if (observation instanceof IConcept) {
		//            return _observe(observation, alternativeReality, scenarios);
		//        } else if (observation instanceof KlabUrn) {
		//            // TODO retrieve and insert
		//        } else if (observation instanceof State) {
		//
		//            State mediated = ((State)observation).at(this);
		//            ((org.integratedmodelling.engine.modelling.runtime.DirectObservation)obs).addState(mediated.obs);
		//            return mediated;
		//
		//        } else if (observation instanceof Observation) {
		//            // TODO add to context if freestanding;
		//            // if State, use to create own state by sampling adequately, or
		//            // observe the same observable if not covering.
		//        }
		null;
	}

	private def _observe(Object observation, Collection<IExtent> altExtents, Collection<String> altScenarios) {

		//        /*
		//         * TODO check if this is OK: will only observe things of which no subclass was already observed. Not
		//         * strictly correct but seems to reflect better what API users could expect. If not OK pass false to
		//         * wasContextualized.
		//         */
		//        if (((org.integratedmodelling.engine.modelling.runtime.DirectObservation)obs).wasContextualized(observation, true)) {
		//            if (altExtents != null && !altExtents.isEmpty()) {
		//                getMonitor().warn("recontextualization ignored for already observed " + observation, DefaultAction.getArtifact(binding));
		//            }
		//            return select(observation);
		//        }
		//
		//        // pass the parent task to delay sending objects
		//        ITask parentTask = getMonitor() == null ? null : getMonitor().getTask();
		//        IContext ctx = ((org.integratedmodelling.common.knowledge.Observation)obs).getContext().focus(obs);
		//        if (altExtents != null && !altExtents.isEmpty()) {
		//            ctx = ctx.rescale(altExtents);
		//        }
		//        if (altScenarios != null && !altScenarios.isEmpty()) {
		//            ctx = ctx.inScenario(altScenarios.toArray(new String[altScenarios.size()]));
		//        }
		//        ITask task = ctx.observe(observation, parentTask);
		//        IContext context = task.finish();
		//        return wrapAll(context.getNewObservations());
	}

	//    def wrapAll(Collection obs) {
	//        List<Observation> ret = new ArrayList<>();
	//        for (o in obs) {
	//            if (o instanceof IRelationship) {
	//                ret.add(new Relationship(o, binding));
	//            } else if (o instanceof IDirectObservation) {
	//                ret.add(new DirectObservation(o, binding));
	//            } else if (o instanceof IState) {
	//                ret.add(new State(o, binding));
	//            }
	//        }
	//        return ret;
	//    }

	def propertyMissing(String id) {
		
		def artifact = ((org.integratedmodelling.klab.components.runtime.observations.DirectObservation)unwrap())
			.getChildArtifact(id);
			
		if (artifact instanceof IState) {
			return new State(artifact, binding);
		} else if (artifact instanceof IDirectObservation) {
			// TODO special treatment for groups
			return new DirectObservation(artifact, binding);
		}
		
		return null;
	}

	//    def tag(TagProvider tag) {
	//
	//        /*
	//         * resolve the tag provider
	//         */
	//
	//        /*
	//         * apply metadata and extents
	//         */
	//    }

	/*
	 * movement and growth
	 */
	def move(Object...args) {

	}

	def moveTo(Object...args) {

	}

	def moveIn(Object...args) {

	}

	def grow(Object...args) {
	}

}
