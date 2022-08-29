package org.integratedmodelling.klab;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.knowledge.IObservedConcept;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.resolution.IResolutionScope.Mode;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.api.services.IReasonerService;
import org.integratedmodelling.klab.dataflow.ObservedConcept;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
import org.integratedmodelling.klab.owl.IntelligentMap;
import org.integratedmodelling.klab.owl.KlabReasoner;
import org.integratedmodelling.klab.owl.Ontology;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

public enum Reasoner implements IReasonerService {

	INSTANCE;

	private KlabReasoner reasoner;
	protected LoadingCache<String, Boolean> reasonerCache;
	protected LoadingCache<String, Boolean> relatedReasonerCache;
	Map<IConcept, Emergence> emergent = new HashMap<>();
	IntelligentMap<Set<Emergence>> emergence = new IntelligentMap<>();

	/**
	 * An emergence is the appearance of an observation triggered by another, under
	 * the assumptions stated in the worldview. It applies to processes and
	 * relationships and its emergent observable can be a configuration, subject or
	 * process.
	 * 
	 * @author Ferd
	 *
	 */
	public class Emergence {

		public Set<IConcept> triggerObservables = new LinkedHashSet<>();
		public IConcept emergentObservable;
		public String namespaceId;

		public Set<IObservation> matches(IConcept relationship, IRuntimeScope scope) {

			for (IConcept trigger : triggerObservables) {
				Set<IObservation> ret = new HashSet<>();
				checkScope(trigger, scope.getCatalog(), relationship, ret);
				if (!ret.isEmpty()) {
					return ret;
				}
			}

			return Collections.emptySet();
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getEnclosingInstance().hashCode();
			result = prime * result + Objects.hash(emergentObservable, namespaceId, triggerObservables);
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Emergence other = (Emergence) obj;
			if (!getEnclosingInstance().equals(other.getEnclosingInstance()))
				return false;
			return Objects.equals(emergentObservable, other.emergentObservable)
					&& Objects.equals(namespaceId, other.namespaceId)
					&& Objects.equals(triggerObservables, other.triggerObservables);
		}

		/*
		 * current observable must be one of the triggers, any others need to be in
		 * scope
		 */
		private void checkScope(IConcept trigger, Map<IObservedConcept, IObservation> map, IConcept relationship,
				Set<IObservation> obs) {
			if (trigger.is(Type.UNION)) {
				for (IConcept trig : trigger.getOperands()) {
					checkScope(trig, map, relationship, obs);
				}
			} else if (trigger.is(Type.INTERSECTION)) {
				for (IConcept trig : trigger.getOperands()) {
					Set<IObservation> oobs = new HashSet<>();
					checkScope(trig, map, relationship, oobs);
					if (oobs.isEmpty()) {
						obs = oobs;
					}
				}
			} else {
				IObservation a = map.get(new ObservedConcept(trigger));
				if (a != null) {
					obs.add(a);
				}
			}
		}

		private Reasoner getEnclosingInstance() {
			return Reasoner.this;
		}
	}

	class AttributeDescription {
		IObservable observable;
		Object value; // range, concept or number. Could be a complex value eventually.
	}

	private Reasoner() {
		Services.INSTANCE.registerService(this, IReasonerService.class);
		this.reasonerCache = CacheBuilder.newBuilder().maximumSize(2048).build(new CacheLoader<String, Boolean>() {
			@Override
			public Boolean load(String key) throws Exception {
				String[] split = key.split(";");
				IConcept a = Concepts.c(split[0]);
				IConcept b = Concepts.c(split[1]);
				return a.is(b);
			}
		});
		this.relatedReasonerCache = CacheBuilder.newBuilder().maximumSize(2048)
				.build(new CacheLoader<String, Boolean>() {
					@Override
					public Boolean load(String key) throws Exception {
						String[] split = key.split(";");

						IConcept a = Concepts.c(split[0]);
						IConcept b = Concepts.c(split[1]);

						boolean ret = a.is(b);
						if (!ret && (b.is(Type.PREDICATE))) {
							// TODO check for adoption
						}
						return ret;
					}
				});
	}

	public void setReasoner(KlabReasoner klabReasoner) {
		this.reasoner = klabReasoner;
	}

	public void addOntology(Ontology ontology) {
		this.reasoner.addOntology(ontology);
	}

	public Ontology getOntology() {
		return (Ontology) reasoner.getOntology();
	}

	@Override
	public boolean isSatisfiable(IConcept concept) {
		return reasoner.isSatisfiable(concept);
	}

	@Override
	public Set<IConcept> getParentClosure(IConcept main) {
		return reasoner.getParentClosure(main);
	}

	@Override
	public Set<IConcept> getSemanticClosure(IConcept main) {
		return reasoner.getSemanticClosure(main);
	}

	@Override
	public Map<IConcept, Collection<IObservation>> getEmergentResolvables(IObservation trigger,
			IContextualizationScope scope) {

		if (!(scope instanceof IRuntimeScope) || ((IRuntimeScope) scope).getActuator() == null) {
			return Collections.emptyMap();
		}

		Mode mode = ((IRuntimeScope) scope).getActuator().getMode();

		/*
		 * Skip a search in the map if we can't trigger anything.
		 */
		if (!trigger.getObservable().is(Type.QUALITY)
				&& !(trigger.getObservable().is(Type.RELATIONSHIP) && mode == Mode.INSTANTIATION)) {
			return Collections.emptyMap();
		}

		Map<IConcept, Collection<IObservation>> ret = new HashMap<>();
		Collection<Emergence> emergents = this.emergence.get(trigger.getObservable().getType());

		if (emergents != null) {

			for (Emergence emergent : emergents) {

				Collection<IObservation> match = emergent.matches(trigger.getObservable().getType(),
						(IRuntimeScope) scope);

				/*
				 * if process or configuration, update and skip if the scope already contains
				 * the emergent observation
				 */
				if (emergent.emergentObservable.is(Type.PROCESS)
						|| emergent.emergentObservable.is(Type.CONFIGURATION)) {
					if (((IRuntimeScope) scope).getCatalog()
							.get(new ObservedConcept(emergent.emergentObservable)) != null) {
						/*
						 * TODO update with the new observation(s)! API to be defined
						 */
						if (((IDirectObservation) trigger).getOriginatingPattern() != null) {
							((IDirectObservation) trigger).getOriginatingPattern().update(trigger);
							return ret;
						}
					}
				}

				ret.put(emergent.emergentObservable, match);
			}
		}
		return ret;
	}

	@Override
	public boolean implies(IConcept target, IConcept implied) {
		// TODO - and CACHE
		return false;
	}

	@Override
	public boolean implies(IConcept target, IConcept role, IObservation context) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean is(Object c1, Object c2) {

		if (c2 == null || c1 == null) {
			return false;
		}
		if (c1.equals(c2)) {
			return true;
		}
		try {
			return reasonerCache.get(c1.toString() + ";" + c2.toString());
		} catch (ExecutionException e) {
			return false;
		}
	}

	public boolean isRelated(Object c1, Object c2) {
		if (c2 == null || c1 == null) {
			return false;
		}
		try {
			return relatedReasonerCache.get(c1.toString() + ";" + c2.toString());
		} catch (ExecutionException e) {
			return false;
		}
	}

	/*
	 * Register the triggers and each triggering concept in the emergence map.
	 */
	public boolean registerEmergent(IConcept configuration, Collection<IConcept> triggers) {

		if (!configuration.isAbstract()) {

			if (this.emergent.containsKey(configuration)) {
				return true;
			}

			Emergence descriptor = new Emergence();
			descriptor.emergentObservable = configuration;
			descriptor.triggerObservables.addAll(triggers);
			descriptor.namespaceId = configuration.getNamespace();
			this.emergent.put(configuration, descriptor);

			for (IConcept trigger : triggers) {
				for (IConcept tr : Concepts.INSTANCE.flattenOperands(trigger)) {
					Set<Emergence> es = emergence.get(tr);
					if (es == null) {
						es = new HashSet<>();
						emergence.put(tr, es);
					}
					es.add(descriptor);
				}
			}

			return true;
		}

		return false;
	}

	void releaseNamespace(String namespaceId) {
		for (IConcept c : emergence.keySet()) {
			Set<Emergence> es = emergence.getValue(c);
			Set<Emergence> nes = new HashSet<>();
			for (Emergence e : es) {
				if (e.namespaceId.equals(namespaceId)) {
					emergent.remove(c);
				} else {
					nes.add(e);
				}
			}
			if (es.size() != nes.size()) {
				emergence.put(c, nes);
			}
		}
		for (IConcept c : emergent.keySet()) {
			if (c.getNamespace().equals(namespaceId)) {
				emergent.remove(c);
			}
		}
	}

}