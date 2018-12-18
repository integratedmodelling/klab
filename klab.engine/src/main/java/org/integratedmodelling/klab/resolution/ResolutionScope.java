package org.integratedmodelling.klab.resolution;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.integratedmodelling.kim.api.IComputableResource;
import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.klab.Models;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.knowledge.IProject;
import org.integratedmodelling.klab.api.model.IModel;
import org.integratedmodelling.klab.api.model.INamespace;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.ISubject;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.resolution.IResolutionScope;
import org.integratedmodelling.klab.api.resolution.IResolvable;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.api.services.IModelService.IRankedModel;
import org.integratedmodelling.klab.common.LogicalConnector;
import org.integratedmodelling.klab.components.runtime.observations.DirectObservation;
import org.integratedmodelling.klab.components.runtime.observations.Subject;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabInternalErrorException;
import org.integratedmodelling.klab.model.Model;
import org.integratedmodelling.klab.model.Namespace;
import org.integratedmodelling.klab.model.Observer;
import org.integratedmodelling.klab.owl.Observable;
import org.integratedmodelling.klab.resolution.ObservableReasoner.CandidateObservable;
import org.integratedmodelling.klab.scale.Coverage;
import org.integratedmodelling.klab.scale.Scale;
import org.integratedmodelling.klab.utils.Pair;

public class ResolutionScope implements IResolutionScope {

	/**
	 * Each successful merge creates a link that is saved in the scope. At the end
	 * of the resolution, all link are used to build the final dependency graph.
	 * 
	 * Class is used by the dataflow compiler, so it's package private.
	 */
	public class Link {

		ResolutionScope target;
		List<IComputableResource> computation;

		Link(ResolutionScope target) {
			this.target = target;
		}

		Link(ResolutionScope target, List<IComputableResource> computation) {
			this.target = target;
			this.computation = computation;
		}

		public ResolutionScope getSource() {
			return ResolutionScope.this;
		}

		public ResolutionScope getTarget() {
			return target;
		}

		public List<IComputableResource> getComputation() {
			return computation;
		}

		@Override
		public String toString() {
			return getSource() + " <- " + target;
		}

		@Override
		public boolean equals(Object object) {
			return object == this || (object instanceof Link && ((Link) object).getSource().equals(getSource())
					&& target.equals(((Link) object).target));
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getSource().hashCode();
			result = prime * result + target.hashCode();
			return result;
		}

	}

	/*
	 * The three main pieces of info in each scope: only the root node has none of
	 * these, other nodes have one and only one of these.
	 */
	private Observable observable;
	private Model model;
	private Observer observer;

	/*
	 * When an artifact is here, it's from the context and will be turned into an
	 * 'input' actuator in the dataflow. Scopes resolved with an artifact have a
	 * coverage of 1.
	 */
	private ResolvedArtifact resolvedArtifact;

	/*
	 * When resolving relationships, we carry around their source and target
	 * subjects to inform resolution and enable the runtime to create their
	 * observations.
	 */
	private Subject relationshipSource;
	private Subject relationshipTarget;

	/*
	 * These two are built at merge() and thrown away if a resolution ends up empty.
	 */
	Set<Link> links = new HashSet<>();
	Set<ResolutionScope> resolvedObservables = new HashSet<>();

	/**
	 * If not null, this is a scope for a logical combination of resolutions.
	 */
	LogicalConnector connector;
	
	/*
	 * Cache of names of models being resolved upstream, to avoid recursive resolutions 
	 */
	Set<String> beingResolved = new HashSet<>();
	/*
	 * These change during resolution and influence the choice of models
	 */
	private Mode mode = Mode.RESOLUTION;
	private Namespace resolutionNamespace;

	/*
	 * these do not change during an individual resolution.
	 */
	private DirectObservation context;
	private Collection<String> scenarios = new ArrayList<>();
	private boolean interactive;
	private IMonitor monitor;

	/*
	 * The parent is only used during model ranking, to establish project and
	 * namespace nesting and distance
	 */
	private ResolutionScope parent;

	/*
	 * this is only used to correctly merge in dependencies.
	 */
	int mergedObservables = 0;

	/*
	 * the coverage for this scope.
	 */
	private Coverage coverage;

	/*
	 * Cached resolvers for specific observables in the current scale. Will be
	 * reassessed (by the kbox) to match them to the scale of an instance created by
	 * an instantiator for our scale, moving on to a full search only if resolvers
	 * are available but they don't cover the scale of the instance.
	 * 
	 * This is a set because the kbox will reassess all models based on the specific
	 * instance being resolved.
	 */
	private Map<IObservable, Set<IRankedModel>> resolverCache = new HashMap<>();
	private boolean caching;

	/**
	 * Get a root scope based on the definition of an observation.
	 * 
	 * @param observer
	 * @param monitor
	 * @param scenarios
	 * @return a root scope
	 * @throws KlabException
	 */
	public static ResolutionScope create(Observer observer, IMonitor monitor, Collection<String> scenarios)
			throws KlabException {
		return new ResolutionScope(observer, monitor, scenarios);
	}

	/**
	 * Get a root scope with the scale of an existing subject used as a context for
	 * the next observations.
	 * 
	 * @param observer
	 * @param monitor
	 * @param scenarios
	 * @return a root scope
	 * @throws KlabException
	 */
	public static ResolutionScope create(Subject observer, IMonitor monitor, Collection<String> scenarios)
			throws KlabException {
		return new ResolutionScope(observer, monitor, scenarios);
	}

	private ResolutionScope(Subject observer, IMonitor monitor, Collection<String> scenarios) throws KlabException {
		this.coverage = Coverage.full(observer.getScale());
		this.context = observer;
		this.scenarios.addAll(scenarios);
		this.resolutionNamespace = observer.getNamespace();
		this.monitor = monitor;

		/*
		 * TODO pre-existing observables do not need any resolution: they can be just
		 * referred to by name, so they don't go in resolvedObservables, which is a
		 * cache for the current resolution only. At worst they may be called a
		 * different name, so we should preset paths for naming them differently in the
		 * dataflow. Those are simply INPUT PORTS - 'import' statements - with 'as' if
		 * necessary.
		 */
	}

	private ResolutionScope(Observer observer, IMonitor monitor, Collection<String> scenarios) throws KlabException {
		this.coverage = Coverage.full(Scale.create(observer.getBehavior().getExtents(monitor)));
		this.scenarios.addAll(scenarios);
		this.resolutionNamespace = observer.getNamespace();
		this.observer = observer;
		this.monitor = monitor;
		/*
		 * TODO instantiate all pre-existing states mentioned in the observer and any
		 * metadata.
		 */
	}

	/**
	 * Copy the passed scope without copying the results of resolution.
	 * 
	 * @param other
	 */
	private ResolutionScope(ResolutionScope other) {
		this(other, false);
	}

	private ResolutionScope(ResolutionScope other, double coverage) {
		this(other);
		this.coverage = new Coverage(this.coverage);
		this.coverage.setCoverage(coverage);
	}

	private ResolutionScope(IScale scale, double coverage, ResolutionScope other, boolean copyResolution) {
		copy(other, copyResolution);
		this.coverage = coverage == 1 ? Coverage.full(scale) : Coverage.empty(scale);
	}

	private ResolutionScope(Coverage coverage, ResolutionScope other, boolean copyResolution) {
		copy(other, copyResolution);
		this.coverage = coverage;
	}

	private ResolutionScope(ResolutionScope other, boolean copyResolution) {
		copy(other, copyResolution);
	}

	private void copy(ResolutionScope other, boolean copyResolution) {
		this.scenarios.addAll(other.scenarios);
		this.resolutionNamespace = other.resolutionNamespace;
		this.mode = other.mode;
		this.interactive = other.interactive;
		this.monitor = other.monitor;
		this.parent = other;
		this.context = other.context;
		this.coverage = other.coverage;
		this.beingResolved.addAll(other.beingResolved);
		if (copyResolution) {
			this.observable = other.observable;
			this.model = other.model;
			this.observer = other.observer;
			this.links.addAll(other.links);
			this.resolvedObservables.addAll(other.resolvedObservables);
		}
	}

	public final ResolutionScope empty() {
		return new ResolutionScope(this, 0.0);
	}
	
	public boolean isResolving(String modelName) {
		return beingResolved.contains(modelName);
	}

	public Collection<Link> getLinks() {
		return links;
	}

	/**
	 * Create a child coverage for a passed observable with the same scale but
	 * initial coverage set at 0.
	 * 
	 * @param observable
	 * @param mode
	 * @return a new scope for the passed observable
	 */
	public ResolutionScope getChildScope(Observable observable, Mode mode) {

		ResolutionScope ret = new ResolutionScope(this);
		ret.observable = observable;
		ret.mode = mode;

		/*
		 * check if we already can resolve this (directly or indirectly), and if so, set
		 * coverage so that it can be accepted as is. This should be a model; we should
		 * make the link, increment the use count for the observable, and return
		 * coverage.
		 */
		ResolutionScope previous = getObservable(observable, mode, false);
		if (previous != null) {
			ret.coverage = previous.coverage;
		} else {
			ret.coverage = Coverage.empty(ret.coverage);
		}

		return ret;
	}
	
	public ResolutionScope getChildScope(LogicalConnector connector) {
	    ResolutionScope ret = new ResolutionScope(this);
	    ret.connector = connector;
	    ret.coverage = connector == LogicalConnector.UNION ? Coverage.full(ret.coverage) : Coverage.empty(ret.coverage);
	    return ret;
	}

	public ResolutionScope getChildScope(Observable observable, Mode mode, Scale scale) {

		ResolutionScope ret = new ResolutionScope(scale, 1.0, this, false);
		ret.observable = observable;
		ret.mode = mode;

		/*
		 * check if we already can resolve this (directly or indirectly), and if so, set
		 * coverage so that it can be accepted as is. This should be a model; we should
		 * make the link, increment the use count for the observable, and return
		 * coverage.
		 */
		ResolutionScope previous = getObservable(observable, mode, false);
		if (previous != null) {
			ret.coverage = previous.coverage;
		}

		return ret;
	}

	/**
	 * Return a scope to resolve a relationship that will link the passed subjects.
	 * 
	 * @param observable
	 *            must be a relationship observable
	 * @param scale
	 *            scale of the relationship
	 * @param source
	 *            the source subject
	 * @param target
	 *            the target subject
	 * @return a new scope
	 */
	public ResolutionScope getChildScope(Observable observable, Scale scale, Subject source, Subject target) {

		if (!observable.is(Type.RELATIONSHIP)) {
			throw new KlabInternalErrorException(
					"cannot create scope for non-relationships with a source and target subject");
		}

		ResolutionScope ret = getChildScope(observable, Mode.RESOLUTION, scale);

		ret.relationshipSource = source;
		ret.relationshipTarget = target;

		return ret;
	}

	/**
	 * 
	 * @param model
	 * @return a scope to resolve the passed model
	 * @throws KlabException
	 */
	public ResolutionScope getChildScope(Model model) throws KlabException {

		ResolutionScope ret = new ResolutionScope(this);

		ret.model = model;
		ret.resolutionNamespace = (Namespace) model.getNamespace();
		ret.beingResolved.add(model.getName());

		/*
		 * models start with full coverage...
		 */
		ret.coverage = new Coverage(this.coverage);
		ret.coverage.setCoverage(1.0);

		if (model.getBehavior().hasScale()) {
			/*
			 * ...and redefine it based on their own coverage if they have any.
			 */
			ret.coverage = Coverage.full(Scale.createLike(ret.coverage, model.getBehavior().getExtents(this.monitor)));
		}
		return ret;
	}

	/**
	 * 
	 * @param observer
	 * @return a scope to resolve the passed observer
	 * @throws KlabException
	 */
	public ResolutionScope getChildScope(Observer observer) throws KlabException {

		ResolutionScope ret = new ResolutionScope(this);
		ret.observer = observer;
		ret.resolutionNamespace = (Namespace) observer.getNamespace();

		if (observer.getBehavior().hasScale()) {
			ret = new ResolutionScope(Scale.create(observer.getBehavior().getExtents(monitor)), 1.0, ret, false);
		} else {
			ret.coverage = new Coverage(this.coverage);
			ret.coverage.setCoverage(1.0);
		}

		return ret;
	}

	@Override
	public Collection<String> getScenarios() {
		return scenarios;
	}

	public Model getModel() {
		return model;
	}

	@Override
	public Namespace getResolutionNamespace() {
		return resolutionNamespace;
	}

	@Override
	public Mode getMode() {
		return mode;
	}

	@Override
	public boolean isInteractive() {
		return interactive;
	}

	public Observable getObservable() {
		return observable;
	}

	public ResolvedArtifact getResolvedArtifact() {
		return resolvedArtifact;
	}

	// for the dataflow compiler
	public IResolvable getResolvable() {
		
		// lovin'it
		return resolvedArtifact != null 
				? resolvedArtifact
				: (observable != null 
					? observable 
					: (model != null 
						? model 
						: (observer != null 
							? observer 
							: null)));
	}

	@Override
	public IMonitor getMonitor() {
		return monitor;
	}

	public String toString() {
		String ret = "<" + (observable == null ? "" : ("OBS " + observable))
				+ (model == null ? "" : ("MOD " + model.getName()))
				+ (observer == null ? "" : ("SUB " + observer.getName()));
		return ret + (ret.length() == 1 ? "ROOT " : " ") + getCoverage().getCoverage() + ">";
	}

	/**
	 * Link a scope but leave the definition of the resolution (including the
	 * coverage and the catalog update) to an upstream resolver. Used when models
	 * are accepted but we still don't know if their contribution finalizes the
	 * needed coverage.
	 * 
	 * @param childScope
	 * @param computation
	 */
	void link(ResolutionScope childScope, List<IComputableResource> computation) {
		links.addAll(childScope.links);
		links.add(new Link(childScope, computation));
	}

	/**
	 * Merge an accepted child scope (which has, in turn, been merged before this is
	 * called). The scope must represent the entire result of a downstream
	 * resolution, not just partially - if not, use the other version
	 * ({@link #merge(ResolutionScope, LogicalConnector)}).
	 * 
	 * @param childScope
	 * @return true if the merge did anything significant
	 */
	boolean merge(ResolutionScope childScope) {

		boolean successful = childScope.coverage.isRelevant();

		/*
		 * Accept the observation and merge in the model if any
		 */

		/*
		 * my coverage becomes the child's
		 */
		this.coverage = childScope.coverage;

		if (successful) {

			// when I am OBS and the child is MOD, make links
			if (this.observable != null && childScope.getModel() != null) {

				// TODO merge in new scopes for the other observables provided and link them to
				// the child
				// scope
				for (IObservable o : childScope.getResolvedObservables(this.observable)) {
					resolvedObservables.add(childScope.getAdditionalScope(o));
				}
			}

			// when the child is OBS, update all resolution records with the new observable
			if (childScope.getObservable() != null) {
				// usage count goes up every time an observable is explicitly merged.
				resolvedObservables.add(childScope);
			}

			links.addAll(childScope.links);
			links.add(new Link(childScope));

			/*
			 * Record any observables already resolved in the dependency graph so far. Must
			 * have the model's scale if not existing already.
			 */
			resolvedObservables.addAll(childScope.resolvedObservables);
		}

		return successful;
	}

	/**
	 * Return a new scope for the passed observable, with same scale and coverage as
	 * ours. Used for yet unused observables provided by models besides the focal
	 * one.
	 * 
	 * @param o
	 * @return
	 */
	private ResolutionScope getAdditionalScope(IObservable o) {
		ResolutionScope ret = new ResolutionScope(this);
		ret.observable = (Observable) o;
		ret.mode = o.getType().is(Type.COUNTABLE) ? Mode.INSTANTIATION : Mode.RESOLUTION;
		return ret;
	}

	/*
	 * observables are actually resolved only if this is used within merge()
	 */
	private Collection<IObservable> getResolvedObservables(IObservable toSkip) {
		if (this.model != null) {
			List<IObservable> ret = new ArrayList<>();
			for (IObservable obs : this.model.getObservables()) {
				if (!obs.equals(toSkip)) {
					ret.add(obs);
				}
			}
			return ret;
		} else if (this.observable != null && !this.observable.equals(toSkip)) {
			return Collections.singleton(this.observable);
		}
		return Collections.emptyList();
	}

	public Observer getObserver() {
		return observer;
	}

	/**
	 * OR the passed scope, already established as relevant, and return whether it
	 * causes enough coverage gain to bother. If so, the model should be linked to
	 * the scope.
	 * 
	 * @param child
	 * @return true if gain > 0, meaning the child coverage was merged into us.
	 */
	public boolean or(ResolutionScope child) {
		this.coverage = (Coverage) this.coverage.merge(child.coverage, LogicalConnector.UNION);
		return this.coverage.getGain() > 0;
	}

	/**
	 * AND the passed child scope coverage.
	 * 
	 * @param child
	 */
	public void and(ResolutionScope child) {
		this.coverage = (Coverage) this.coverage.merge(child.coverage, LogicalConnector.INTERSECTION);
	}

	public Observable findObservable() {
		if (observable != null) {
			return observable;
		} else if (observer != null) {
			return observer.getObservable();
		} else if (model != null) {
			return (Observable) model.getObservables().get(0);
		} else if (context != null) {
			return context.getObservable();
		}
		return null;
	}

	/**
	 * Return how far away in the resolution is the passed namespace; -1 if not
	 * found.
	 * 
	 * @param ns
	 * @return namespace distance
	 */
	public int getNamespaceDistance(INamespace ns) {

		if (ns == null) {
			return -1;
		}

		int ret = 0;
		if (resolutionNamespace != null && !this.resolutionNamespace.getId().equals(ns.getId())) {
			ResolutionScope rc = parent;
			while (rc != null) {
				ret++;
				if (rc.resolutionNamespace != null && rc.resolutionNamespace.getId().equals(ns.getId())) {
					return ret;
				}
				rc = rc.parent;
			}
		}

		return -1;
	}

	public int getProjectDistance(IProject ns) {

		if (ns == null || this.resolutionNamespace == null) {
			return -1;
		}

		if (this.resolutionNamespace.getProject() != null
				&& this.resolutionNamespace.getProject().getName().equals(ns.getName())) {
			return 0;
		}

		int ret = 0;
		if (this.resolutionNamespace.getProject() == null
				|| !this.resolutionNamespace.getProject().getName().equals(ns.getName())) {
			ResolutionScope rc = parent;
			while (rc != null) {
				ret++;
				if (rc.resolutionNamespace != null && rc.resolutionNamespace.getProject() != null
						&& rc.resolutionNamespace.getProject().getName().equals(ns.getName())) {
					return ret;
				}
				rc = rc.parent;
			}
		}

		return -1;
	}

	@Override
	public IDirectObservation getContext() {
		return context;
	}

	/**
	 * Get the existing dependency for a passed observable and mode, or null if none
	 * exists.
	 * 
	 * @param observable
	 * @param mode
	 * @param allowIndirectObservation
	 * @return the existing scope for the passed parameters or null
	 */
	public ResolutionScope getObservable(Observable observable, Mode mode, boolean allowIndirectObservation) {
		for (ResolutionScope o : resolvedObservables) {
			if (o.observable.equals(observable) && o.mode == mode) {
				return o;
			}
		}
		if (allowIndirectObservation) {
			ObservableReasoner reasoner = new ObservableReasoner(observable, mode, this);
			for (CandidateObservable indirect : reasoner.getAlternatives()) {
				// ACHTUNG observable(0)
				ResolutionScope alternative = getObservable(indirect.observables.get(0), indirect.mode, true);
				if (alternative != null) {
					return alternative;
				}
			}
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((mode == null) ? 0 : mode.hashCode());
		result = prime * result + ((model == null) ? 0 : model.hashCode());
		result = prime * result + ((observable == null) ? 0 : observable.hashCode());
		result = prime * result + ((observer == null) ? 0 : observer.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		ResolutionScope other = (ResolutionScope) obj;
		if (mode != other.mode) {
			return false;
		}
		if (model == null) {
			if (other.model != null) {
				return false;
			}
		} else if (!model.equals(other.model)) {
			return false;
		}
		if (observable == null) {
			if (other.observable != null) {
				return false;
			}
		} else if (!observable.equals(other.observable)) {
			return false;
		}
		if (observer == null) {
			if (other.observer != null) {
				return false;
			}
		} else if (!observer.equals(other.observer)) {
			return false;
		}
		return true;
	}

	public void setContext(IDirectObservation target) {
		this.context = (DirectObservation) target;
	}

	/**
	 * Called on an empty coverage to accept anyway, setting coverage to 1.0 and
	 * removing any remnants from a possibly failed resolution.
	 */
	public void acceptEmpty() {
		this.coverage.setCoverage(1.0);
		this.model = null;
		this.links.clear();
		this.resolvedObservables.clear();
	}

	public void acceptArtifact(Observable observable, IObservation artifact, String artifactId) {
		this.resolvedArtifact = new ResolvedArtifact(observable, artifact, artifactId);
		this.coverage.setCoverage(1.0);
	}

	@Override
	public Coverage getCoverage() {
		return this.coverage;
	}

	/**
	 * Called before each instance resolution when the passed observable is
	 * instantiated in our context. Should fill in the resolver set in our scale
	 * only once, so the same resolvers can be used above. The models will then be
	 * ranked in the scale of each instance.
	 * 
	 * @param observable
	 */
	public void preloadResolvers(IObservable observable) {

		/**
		 * only search for resolvers if we haven't already
		 */
		if (this.resolverCache.get(observable) != null) {
			return;
		}

		Set<IRankedModel> resolvers = new HashSet<>();

		/*
		 * preload and cache resolvers to explain the observable in the current scale.
		 * Called after instantiation when the first instance is resolved. The resolvers
		 * are used by the kbox if a cache for this observable and scale is present
		 * (including if empty). The kbox will return any model within our scale,
		 * independent of how much of the context they cover; they will be ranked in the
		 * scale of the instance, not ours.
		 */
		ResolutionScope scope = this.getChildScope((Observable) observable, Mode.RESOLUTION);
		// ensure we don't try to find the cache for the cache
		scope.caching = true;
		resolvers.addAll(Models.INSTANCE.resolve(observable, scope));

		/*
		 * TODO this may include the existing states in the context, with enough
		 * metadata to choose wisely on their use or not according to the subscales.
		 * Order does not matter as they should be reassessed by
		 */

		/*
		 * add the possibly empty set even if no resolvers are found, to prevent
		 * additional search.
		 */
		resolverCache.put(observable, resolvers);
	}

	/**
	 * True if this scope is being used to build a cache, indicating that we
	 * shouldn't try to use the cache when resolving.
	 * 
	 * @return
	 */
	public boolean isCaching() {
		return this.caching;
	}

	/**
	 * Return the (possibly empty) set of resolver models that was cached after the
	 * first instantiation, along with the scale of the instantiator.
	 * 
	 * @param observable
	 * @return
	 */
	public Pair<Scale, Set<IRankedModel>> getPreresolvedModels(IObservable observable) {

		if (mode != Mode.RESOLUTION) {
			return null;
		}

		/*
		 * look up in parents until the observable is the same and the mode is
		 * instantiation.
		 */
		ResolutionScope target = this;
		while (target != null && target.observable != null && target.observable.equals(observable)) {
			if (target.mode == Mode.INSTANTIATION) {
				break;
			}
			target = target.parent;
		}
		return (target == null || target.resolverCache.get(observable) == null) ? null
				: new Pair<>(target.coverage.asScale(), target.resolverCache.get(observable));
	}

	@Override
	public ISubject getRelationshipSource() {
		return relationshipSource;
	}

	@Override
	public ISubject getRelationshipTarget() {
		return relationshipTarget;
	}

	public boolean isBeingResolved(IConcept observable, Mode mode) {
		if (this.observable != null && this.observable.getType().equals(observable) && this.mode == mode) {
			return true;
		}
		return parent == null ? false : parent.isBeingResolved(observable, mode);
	}

	public ResolutionScope getChildScope(Observable observable, Mode mode, IObservation artifact, String artifactId) {
		ResolutionScope ret = getChildScope(observable, mode);
		ret.acceptArtifact(observable, artifact, artifactId);
		return ret;
	}

}
