package org.integratedmodelling.klab.owl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.kim.api.IKimExpression;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.kim.api.ValueOperator;
import org.integratedmodelling.klab.Authentication;
import org.integratedmodelling.klab.Concepts;
import org.integratedmodelling.klab.Observables;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.Roles;
import org.integratedmodelling.klab.Traits;
import org.integratedmodelling.klab.api.data.mediation.ICurrency;
import org.integratedmodelling.klab.api.data.mediation.IUnit;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.knowledge.ISemantic;
import org.integratedmodelling.klab.api.model.IAnnotation;
import org.integratedmodelling.klab.api.model.IConceptDefinition;
import org.integratedmodelling.klab.api.model.IKimObject;
import org.integratedmodelling.klab.api.model.IModel;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.observations.ISubject;
import org.integratedmodelling.klab.api.provenance.IActivity;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.common.mediation.Currency;
import org.integratedmodelling.klab.common.mediation.Unit;
import org.integratedmodelling.klab.dataflow.Dataflow;
import org.integratedmodelling.klab.engine.runtime.Session;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
import org.integratedmodelling.klab.exceptions.KlabUnimplementedException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.model.Annotation;
import org.integratedmodelling.klab.utils.CamelCase;
import org.integratedmodelling.klab.utils.Pair;
import org.integratedmodelling.klab.utils.Range;

/**
 * Equality ignores differences of name, value, optional and generic status.
 * 
 * @author ferdinando.villa
 *
 */
public class Observable implements IObservable {

	protected Concept observable;
	private String name;
	private String declaration;
	private boolean isAbstract;
	private Range range;
	private Unit unit;
	private Currency currency;
	private Object value;
	private IActivity.Description observationType;
	private boolean optional;
	private boolean generic;
	private String observerId;
	private IDirectObservation observer;
	private List<Pair<ValueOperator, Object>> valueOperators = new ArrayList<>();
	private boolean fluidUnits;
	private boolean distributedInherency;
	private boolean active = true;
	private IConcept temporalInherent;

	/*
	 * Target predicate is a concrete predicate that may be added to the observable
	 * that classifies its abstract base predicate, so that any outputs that do not
	 * have the exact target predicate after classification can be marked as
	 * irrelevant to the observation and hidden.
	 */
	private IConcept targetPredicate;

	/**
	 * This and the next support situations in which the observable contains a
	 * pre-resolved model, such as when models (including non-semantic ones) are
	 * used as dependencies. It's a convenient implementation trick for now, so it
	 * does not affect the public API.
	 */
	transient IModel resolvedModel;
	private String modelReference;

	// only used to resolve the subject observable if it has to be marshalled across
	// network boundaries
	transient String sessionId;
	private List<IAnnotation> annotations = new ArrayList<>();
	private String referenceName;
	private String url;

	/*
	 * this is only for debugging
	 */
	transient String originatingModelId;
	private boolean mustContextualize;

	Observable(Concept concept) {
		this.observable = concept;
	}

	public static Observable promote(IConceptDefinition concept) {
		return promote(concept.getConcept());
	}

	public static Observable promote(IModel model) {
		Observable ret = new Observable((Observable) model.getObservables().get(0));
		ret.resolvedModel = model;
		return ret;
	}

	public static Observable promote(IConcept concept) {

		Observable ret = new Observable((Concept) concept);

		ret.observable = (Concept) concept;
		ret.declaration = concept.getDefinition().trim();
		ret.isAbstract = concept.isAbstract();
		ret.generic = concept.isAbstract();
		ret.referenceName = ret.name = Concepts.INSTANCE.getCodeName(ret.observable);

		return ret;
	}

	public Observable(Observable observable) {
		this.observable = observable.observable;
		this.name = observable.name;
		this.referenceName = observable.referenceName;
		this.declaration = observable.declaration;
		this.isAbstract = observable.isAbstract;
		this.range = observable.range;
		this.unit = observable.unit;
		this.currency = observable.currency;
		this.targetPredicate = observable.targetPredicate;
		this.value = observable.value;
		this.observationType = observable.observationType;
		this.optional = observable.optional;
		this.generic = observable.generic;
		this.annotations.addAll(observable.getAnnotations());
		this.valueOperators.addAll(observable.valueOperators);
		this.fluidUnits = observable.fluidUnits;
		this.originatingModelId = observable.originatingModelId;
		this.mustContextualize = observable.mustContextualize;
		this.distributedInherency = observable.distributedInherency;
		this.active = observable.active;
		this.temporalInherent = observable.temporalInherent;
	}

	
	public Observable withoutModel() {
		this.originatingModelId = null;
		this.modelReference = null;
		this.resolvedModel = null;
		return this;
	}
	
	
	@Override
	public IConcept getType() {
		return observable;
	}

	@Override
	public String getName() {
		if (name == null) {
			name = CamelCase.toLowerCase(Concepts.INSTANCE.getDisplayName(observable/* .getName() */), '_');
		}
		return name;
	}

	@Override
	public Range getRange() {
		return range;
	}

	@Override
	public IUnit getUnit() {
		return currency != null ? currency.getUnit() : unit;
	}

	@Override
	public ICurrency getCurrency() {
		return currency;
	}

	@Override
	public boolean isAbstract() {
		return isAbstract;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * "Fluent" setName for special circumstances. Use with caution and only OUTSIDE
	 * of resolution.
	 * 
	 * @param name
	 * @return
	 */
	public Observable named(String name) {
		this.name = name;
		return this;
	}

	public void setDeclaration(String declaration) {
		this.declaration = declaration.trim();
	}

	public void setAbstract(boolean isAbstract) {
		this.isAbstract = isAbstract;
	}

	public void setRange(Range range) {
		this.range = range;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
		if (this.unit != null && this.is(Type.NUMEROSITY)) {
			this.unit = (Unit) Unit.unitless().divide(this.unit);
		}
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	@Override
	public String getDefinition() {
		return declaration;
	}

	@Override
	public Object getValue() {
		return value;
	}

	public String getDeclaration() {
		return declaration;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	@Override
	public IActivity.Description getDescription() {
		if (observationType == null && observable != null) {
			if (observable.is(Type.CLASS)) {
				observationType = IActivity.Description.CATEGORIZATION;
			} else if (observable.is(Type.PRESENCE)) {
				observationType = IActivity.Description.VERIFICATION;
			} else if (observable.is(Type.QUALITY)) { // don't reorder these!
				observationType = IActivity.Description.QUANTIFICATION;
			} else if (observable.is(Type.COUNTABLE)) {
				observationType = IActivity.Description.INSTANTIATION;
			} else if (observable.is(Type.CONFIGURATION)) {
				observationType = IActivity.Description.DETECTION;
			} else if (observable.is(Type.PROCESS)) {
				observationType = IActivity.Description.SIMULATION;
			} else if (observable.is(Type.TRAIT) || observable.is(Type.ROLE)) {
				boolean distributed = Observables.INSTANCE.hasDistributedInherency(observable);
				observationType = distributed ? IActivity.Description.CLASSIFICATION
						: IActivity.Description.CHARACTERIZATION;
			}
		}
		return observationType;
	}

	public boolean isOptional() {
		return optional;
	}

	public void setOptional(boolean optional) {
		this.optional = optional;
	}

	public void setObservationType(IActivity.Description observationType) {
		this.observationType = observationType;
	}

	public boolean isGeneric() {
		return generic;
	}

	public void setGeneric(boolean generic) {
		this.generic = generic;
	}

	public String toString() {
		return "[" + getName() + " = " + this.declaration + "]";
	}

	/**
	 * Checks for equality of 'actual' meaning, i.e. equal observables and observers
	 * besides name and mediators.
	 * 
	 * @param obj
	 * @return
	 */
	public boolean canResolve(Observable obj) {

		if (observer == null) {
			if (obj.observer != null) {
				return false;
			}
		} else if (!observer.equals(obj.observer)) {
			return false;
		}

		/*
		 * TODO check: operators are only allowed at the receiving end. We should also
		 * allow the same operators and operands as us in the provider.
		 */
		return this.observable.equals(obj.observable) && (obj.valueOperators.isEmpty()
				|| CollectionUtils.isEqualCollection(this.valueOperators, obj.valueOperators));
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((currency == null) ? 0 : currency.hashCode());
		result = prime * result + ((observable == null) ? 0 : observable.hashCode());
		result = prime * result + ((observer == null) ? 0 : observer.hashCode());
		result = prime * result + ((range == null) ? 0 : range.hashCode());
		result = prime * result + ((unit == null) ? 0 : unit.hashCode());
		result = prime * result + ((valueOperators == null) ? 0 : valueOperators.hashCode());
		return result;
	}

	/**
	 * Equality checks semantics, mediators and operators but not names.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Observable other = (Observable) obj;
		if (currency == null) {
			if (other.currency != null)
				return false;
		} else if (!currency.equals(other.currency))
			return false;
		if (observable == null) {
			if (other.observable != null)
				return false;
		} else if (!observable.equals(other.observable))
			return false;
		if (observer == null) {
			if (other.observer != null)
				return false;
		} else if (!observer.equals(other.observer))
			return false;
		if (range == null) {
			if (other.range != null)
				return false;
		} else if (!range.equals(other.range))
			return false;
		if (unit == null) {
			if (other.unit != null)
				return false;
		} else if (!unit.equals(other.unit))
			return false;
		if (valueOperators == null) {
			if (other.valueOperators != null)
				return false;
		} else if (!valueOperators.equals(other.valueOperators))
			return false;
		return true;
	}

	public void setModelReference(String modelReference) {
		this.modelReference = modelReference;
	}

	public IModel getReferencedModel() {
		if (this.resolvedModel == null && this.modelReference != null) {
			IKimObject model = Resources.INSTANCE.getModelObject(modelReference);
			if (!(model instanceof IModel)) {
				throw new KlabValidationException(
						"referenced object " + modelReference + " does not exist or is not a model");
			}
			this.resolvedModel = (IModel) model;
		}
		return this.resolvedModel;
	}

	@Override
	public IArtifact.Type getArtifactType() {

		if (observable != null) {
			if (observable.is(Type.CLASS)) {
				return IArtifact.Type.CONCEPT;
			} else if (observable.is(Type.PRESENCE)) {
				return IArtifact.Type.BOOLEAN;
			} else if (observable.is(Type.QUALITY)) { // don't reorder these!
				return IArtifact.Type.NUMBER;
			} else if (observable.is(Type.EVENT)) {
				return IArtifact.Type.EVENT;
			} else if (observable.is(Type.COUNTABLE)) {
				return IArtifact.Type.OBJECT;
			} else if (observable.is(Type.CONFIGURATION)) {
				return IArtifact.Type.OBJECT;
			} else if (observable.is(Type.PROCESS)) {
				return IArtifact.Type.PROCESS;
			} else if (observable.is(Type.TRAIT) || observable.is(Type.ROLE)) {
				return IArtifact.Type.VALUE;
			}
		}
		// trait and role observers specify filters, which produce void.
		return IArtifact.Type.VOID;
	}

	@Override
	public Builder getBuilder(IMonitor monitor) {
		return new ObservableBuilder(this, monitor);
	}

	public static Observable promote(IConcept operand, Observable observable2) {
		// TODO promote, then copy units and other observation semantics from the passed
		// observable
		throw new KlabUnimplementedException("copy semantics from other observable");
	}

	@Override
	public IDirectObservation getObserver() {
		if (observer == null && observerId != null && sessionId != null) {
			Session session = Authentication.INSTANCE.getIdentity(sessionId, Session.class);
			if (session != null) {
				observer = (IDirectObservation) session.getObservation(observerId);
			}
		}
		return observer;
	}

	public void setObserver(ISubject observer) {
		this.observer = observer;
	}

	/**
	 * Return a subjective observable with the passed observer as observer.
	 * 
	 * @param observer
	 * @return
	 */
	public IObservable subjectify(IDirectObservation observer) {
		Observable ret = new Observable(this);
		ret.observer = observer;
		ret.observerId = observer.getId();
		return ret;
	}

	public String getObserverId() {
		return observerId;
	}

	public void setObserverId(String observerId, ISession session) {
		this.observerId = observerId;
		this.sessionId = session.getId();
	}

	/**
	 * Return this same observable after assigning the passed pre-observed value to
	 * it. Used to create states:
	 * 
	 * <pre>
	 * state = subject.observe(Observable.promote(concept).withValue(3));
	 * </pre>
	 * 
	 * @param value
	 * @return
	 */
	public Observable withValue(Object value) {
		this.value = value;
		return this;
	}

	@Override
	public List<IAnnotation> getAnnotations() {
		return this.annotations;
	}

	/**
	 * Annotations that may have interactive parameters changed can be retrieved
	 * using this one, which will find the modifications in the context and produce
	 * copies with modified values.
	 * 
	 * @param context
	 * @return
	 */
	public List<IAnnotation> getAnnotations(IRuntimeScope context) {
		Dataflow dataflow = (Dataflow) context.getDataflow();
		if (dataflow != null) {
			List<IAnnotation> ret = new ArrayList<>();
			for (IAnnotation annotation : this.annotations) {
				ret.add(dataflow.parameterizeAnnotation(annotation));
			}
			return ret;
		}
		return this.annotations;
	}

	public void addAnnotation(Annotation annotation) {
		this.annotations.add(annotation);
	}

	public boolean isResolved() {
		return value instanceof Number || value instanceof Boolean || value instanceof IConcept
				|| value instanceof IKimExpression || value instanceof IServiceCall;
	}

	@Override
	public boolean is(ISemantic semantics) {
		IConcept c = semantics.getType();
		return getType() == null ? false : getType().is(c);
	}

	public String getNamespace() {
		// TODO if we come from a declaration in a given namespace, use that
		return getType().getNamespace();
		
		
	}

	@Override
	public boolean is(Type type) {
		return getType() == null ? false : getType().is(type);
	}

	public void setUrl(String uri) {
		this.url = uri;
	}

	public String getUrl() {
		return this.url;
	}

	/**
	 * Sets the unit without conversions or adjustments, return this.
	 * 
	 * @param chosenUnit
	 * @return
	 */
	public Observable withUnit(IUnit unit) {
		this.unit = (Unit) unit;
		return this;
	}

	/**
	 * An observable with fluid units is one that needs units where the user has
	 * chosen to not declare them. These are treated specially during resolution,
	 * where units will be attributed based on the dependencies but will also be
	 * matched across sibling dependencies to ensure comparability. Observables not
	 * coming from a declaration may still have no units (with semantics that
	 * require them) and fluidUnits = false.
	 * 
	 * @return
	 */
	public boolean isFluidUnits() {
		return fluidUnits;
	}

	public void setFluidUnits(boolean fluidUnits) {
		this.fluidUnits = fluidUnits;
	}

	public void setOriginatingModelId(String modelId) {
		this.originatingModelId = modelId;
	}

	public String getOriginatingModelId() {
		return this.originatingModelId;
	}

	public Observable withCurrency(ICurrency currency) {
		this.currency = (Currency) currency;
		return this;
	}

	public boolean hasResolvableTraits() {

		/*
		 * only resolve rescaling attributes for qualities, where attributes transform
		 * values. For direct observations, resolve everything.
		 */
		if (observable.is(Type.OBSERVABLE)) {
			for (IConcept c : Traits.INSTANCE.getDirectTraits(observable)) {
				if (!c.is(Type.ABSTRACT)) {
					if (is(Type.QUALITY) && !c.is(Type.RESCALING)) {
						continue;
					}
					return true;
				}
			}
			if (!is(Type.QUALITY)) {
				for (IConcept c : Roles.INSTANCE.getDirectRoles(observable)) {
					if (!c.is(Type.ABSTRACT)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * Find the first resolvable trait and return it along with a new observable
	 * without it. Get traits first so that roles can be resolved with the full
	 * trait set resolved.
	 * 
	 * @return
	 */
	public Pair<IConcept, Observable> popResolvableTrait(IMonitor monitor) {
		IConcept resolvable = null;
		if (observable.is(Type.OBSERVABLE)) {
			for (IConcept c : Traits.INSTANCE.getDirectTraits(observable)) {
				if (!c.is(Type.ABSTRACT)) {
					if (is(Type.QUALITY) && !c.is(Type.RESCALING)) {
						continue;
					}
					resolvable = c;
					break;
				}
			}
			if (!is(Type.QUALITY)) {
				if (resolvable /* still */ == null) {
					for (IConcept c : Roles.INSTANCE.getDirectRoles(observable)) {
						if (!c.is(Type.ABSTRACT)) {
							resolvable = c;
							break;
						}
					}
				}
			}
		}

		return resolvable == null ? null
				: new Pair<>(resolvable, (Observable) getBuilder(monitor).without(resolvable).buildObservable());
	}
	
	public void setReferenceName(String name) {
		this.referenceName = name;
	}

	@Override
	public String getReferenceName() {
		return this.referenceName;
	}

	@Override
	public List<Pair<ValueOperator, Object>> getValueOperators() {
		return this.valueOperators;
	}

	public void setTargetPredicate(IConcept targetPredicate) {
		this.targetPredicate = targetPredicate;
	}

	public IConcept getTargetPredicate() {
		return this.targetPredicate;
	}

	public void setMustContextualizeAtResolution(boolean b) {
		this.mustContextualize = b;
	}

	/**
	 * Return true only when the observable is a dependency of an instantiator,
	 * which is resolved within the context of resolution. In these situations the
	 * context for the observable cannot be resolved at model parsing, so it must be
	 * done when used.
	 * 
	 * @return true if observable needs context but the model cannot establish which
	 *         context at declaration time.
	 */
	public boolean mustContextualizeAtResolution() {
		return mustContextualize;
	}

	public void setAnnotations(List<IAnnotation> list) {
		this.annotations = list;
	}

	public void setDistributedInherency(boolean b) {
		this.distributedInherency = b;
	}

	public boolean isDistributedInherency() {
		return this.distributedInherency;
	}

	@Override
	public IConcept getContext() {
		return Observables.INSTANCE.getContext(this.getType());
	}

	@Override
	public IConcept getInherent() {
		return Observables.INSTANCE.getInherency(this.getType());
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public IConcept getTemporalInherent() {
		return temporalInherent;
	}

	public void setTemporalInherent(IConcept temporalInherent) {
		this.temporalInherent = temporalInherent;
	}
	
}
