package org.integratedmodelling.klab.owl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.kim.api.IKimExpression;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.klab.Authentication;
import org.integratedmodelling.klab.Concepts;
import org.integratedmodelling.klab.Observables;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.Units;
import org.integratedmodelling.klab.api.data.mediation.ICurrency;
import org.integratedmodelling.klab.api.data.mediation.IUnit;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.knowledge.IProperty;
import org.integratedmodelling.klab.api.knowledge.ISemantic;
import org.integratedmodelling.klab.api.model.IAnnotation;
import org.integratedmodelling.klab.api.model.IConceptDefinition;
import org.integratedmodelling.klab.api.model.IKimObject;
import org.integratedmodelling.klab.api.model.IModel;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.observations.ISubject;
import org.integratedmodelling.klab.api.observations.scale.ExtentDimension;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.common.mediation.Currency;
import org.integratedmodelling.klab.common.mediation.Unit;
import org.integratedmodelling.klab.engine.runtime.Session;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabUnimplementedException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.model.Annotation;
import org.integratedmodelling.klab.utils.CamelCase;
import org.integratedmodelling.klab.utils.Range;

/**
 * Equality ignores differences of name, value, optional and generic status.
 * 
 * @author ferdinando.villa
 *
 */
public class Observable extends Concept implements IObservable {

	protected Concept observable;
	protected Concept main;
	private String name;
	private String declaration;
	private boolean isAbstract;
	private Range range;
	private Unit unit;
	private Currency currency;
	private Concept classifier;
	private Concept aggregator;
	private Concept downTo;
	private Object value;
	private ObservationType observationType;
	private boolean optional;
	private boolean generic;
	private String observerId;
	private IDirectObservation observer;
	private Set<IConcept> assignedRoles = new HashSet<>();

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

	Observable(Concept concept) {
		super(concept);
		this.observable = this.main = concept;
	}

	public static Observable promote(IConceptDefinition concept, IScale scale) {

		Observable ret = promote(concept.getConcept());
		return ret.contextualizeUnits(scale);
	}

	/**
	 * Ensure the unit is appropriate for the scale if the observable is an
	 * extensive property. Modifies the unit - call on a copy.
	 * 
	 * @param scale
	 * @return this observable (not a copy)
	 */
	public Observable contextualizeUnits(IScale scale) {

		if (this.is(Type.EXTENSIVE_PROPERTY)) {
			IUnit unit = this.getUnit();
			if (unit != null) {
				Set<ExtentDimension> toAdd = new HashSet<>();
				if (scale.isSpatiallyDistributed() && !Units.INSTANCE.isSpatialDensity(unit, scale.getSpace())) {
					toAdd.add(Units.INSTANCE.getExtentDimension(scale.getSpace()));
				}
				if (scale.isTemporallyDistributed() && !Units.INSTANCE.isRate(unit)) {
					toAdd.add(ExtentDimension.TEMPORAL);
				}
				if (toAdd != null) {
					unit = Units.INSTANCE.addExtents(unit, toAdd);
					this.unit = (Unit) unit;
					this.declaration += " in " + this.unit;
				}
			}
		}
		return this;

	}

	public static Observable promote(IModel model) {
		Observable ret = new Observable((Observable) model.getObservables().get(0));
		ret.resolvedModel = model;
		return ret;
	}

	public static Observable promote(IConcept concept) {
		Observable ret = new Observable((Concept) concept);
		ret.observable = (Concept) concept;
		ret.main = (Concept) concept;
		ret.declaration = concept.getDefinition().trim();
		ret.isAbstract = concept.isAbstract();
		ret.generic = concept.isAbstract();
		ret.unit = (Unit) Units.INSTANCE.getDefaultUnitFor(concept);
		if (ret.unit != null) {
			ret.declaration += " in " + ret.unit;
		}
		return ret;
	}

	public Observable(Observable observable) {
		super(observable);
		this.observable = observable.observable;
		this.main = observable.main;
		this.name = observable.name;
		this.declaration = observable.declaration;
		this.isAbstract = observable.isAbstract;
		this.range = observable.range;
		this.unit = observable.unit;
		this.currency = observable.currency;
		this.classifier = observable.classifier;
		this.aggregator = observable.aggregator;
		this.downTo = observable.downTo;
		this.value = observable.value;
		this.observationType = observable.observationType;
		this.optional = observable.optional;
		this.generic = observable.generic;
		this.assignedRoles.addAll(observable.assignedRoles);
		this.annotations.addAll(observable.getAnnotations());
	}

	@Override
	public IConcept getType() {
		return observable;
	}

	@Override
	public String getLocalName() {
		if (name == null) {
			name = CamelCase.toLowerCase(observable.getName(), '_');
		}
		return name;
	}

	@Override
	public IConcept getMain() {
		return main;
	}

	@Override
	public IConcept getDownTo() {
		return downTo;
	}

	@Override
	public IConcept getClassifier() {
		return classifier;
	}

	@Override
	public IConcept getAggregator() {
		return aggregator;
	}

	@Override
	public Range getRange() {
		return range;
	}

	@Override
	public IUnit getUnit() {
		return unit;
	}

	@Override
	public ICurrency getCurrency() {
		return currency;
	}

	@Override
	public boolean isAbstract() {
		return isAbstract;
	}

	public IConcept getObservable() {
		return observable;
	}

	public void setObservable(Concept observable) {
		this.observable = observable;
	}

	public void setMain(Concept main) {
		this.main = main;
	}

	public void setName(String name) {
		this.name = name;
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
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public void setClassifier(Concept by) {
		this.classifier = by;
	}

	public void setDownTo(Concept downTo) {
		this.downTo = downTo;
	}

	@Override
	public boolean is(Type type) {
		return observable.is(type);
	}

	@Override
	public Collection<IConcept> getParents() {
		return observable.getParents();
	}

	@Override
	public Collection<IConcept> getAllParents() {
		return observable.getAllParents();
	}

	@Override
	public Collection<IConcept> getChildren() {
		return observable.getChildren();
	}

	@Override
	public Collection<IProperty> getProperties() {
		return observable.getProperties();
	}

	@Override
	public Collection<IProperty> getAllProperties() {
		return observable.getAllProperties();
	}

	@Override
	public Collection<IConcept> getPropertyRange(IProperty property) throws KlabException {
		return observable.getPropertyRange(property);
	}

	@Override
	public Object getValueOf(IProperty property) throws KlabException {
		return observable.getValueOf(property);
	}

	@Override
	public IConcept getParent() {
		return observable.getParent();
	}

	@Override
	public int getPropertiesCount(String property) {
		return observable.getPropertiesCount(property);
	}

	@Override
	public IConcept getLeastGeneralCommonConcept(IConcept c) {
		return observable.getLeastGeneralCommonConcept(c);
	}

	@Override
	public Set<IConcept> getSemanticClosure() {
		return observable.getSemanticClosure();
	}

	@Override
	public int[] getCardinality(IProperty property) {
		return observable.getCardinality(property);
	}

	@Override
	public Collection<IConcept> getDisjointConcreteChildren() {
		return observable.getDisjointConcreteChildren();
	}

	@Override
	public Collection<IProperty> findRestrictingProperty(IConcept target) {
		return observable.findRestrictingProperty(target);
	}

	@Override
	public String getDefinition() {
		return declaration;
	}

	@Override
	public String getUrn() {
		return observable.getUrn();
	}

	@Override
	public String getURI() {
		return observable.getURI();
	}

	@Override
	public String getNamespace() {
		return observable.getNamespace();
	}

	@Override
	public boolean is(ISemantic concept) {
		return observable.is(concept);
	}

	@Override
	public IConcept getDomain() {
		return observable.getDomain();
	}

	@Override
	public Ontology getOntology() {
		return observable.getOntology();
	}

	@Override
	public IMetadata getMetadata() {
		return observable.getMetadata();
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
	public String getName() {
		return observable.getName();
	}

	@Override
	public ObservationType getObservationType() {
		if (observationType == null && observable != null) {
			if (classifier != null || observable.is(Type.CLASS) || observable.is(Type.TRAIT)) {
				observationType = ObservationType.CLASSIFICATION;
			} else if (observable.is(Type.PRESENCE)) {
				observationType = ObservationType.VERIFICATION;
			} else if (observable.is(Type.QUALITY)) { // don't reorder these!
				observationType = ObservationType.QUANTIFICATION;
			} else if (observable.is(Type.COUNTABLE)) {
				observationType = ObservationType.INSTANTIATION;
			} else if (observable.is(Type.CONFIGURATION)) {
				observationType = ObservationType.DETECTION;
			} else if (observable.is(Type.PROCESS)) {
				observationType = ObservationType.SIMULATION;
			}
		}
		return observationType;
	}

	@Override
	public boolean isExtensive(IConcept extent) {
		return observable != null && unit != null && observable.is(Type.EXTENSIVE_PROPERTY)
				&& Units.INSTANCE.isDensity(unit, extent);
	}

	public boolean isOptional() {
		return optional;
	}

	public void setOptional(boolean optional) {
		this.optional = optional;
	}

	public void setObservationType(ObservationType observationType) {
		this.observationType = observationType;
	}

	public boolean isGeneric() {
		return generic;
	}

	public void setGeneric(boolean generic) {
		this.generic = generic;
	}

	public String toString() {
		return "[" + getLocalName() + " = " + this.declaration + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((classifier == null) ? 0 : classifier.hashCode());
		result = prime * result + ((currency == null) ? 0 : currency.hashCode());
		result = prime * result + ((downTo == null) ? 0 : downTo.hashCode());
		result = prime * result + ((main == null) ? 0 : main.hashCode());
		// result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((observable == null) ? 0 : observable.hashCode());
		// result = prime * result + (optional ? 1231 : 1237);
		result = prime * result + ((range == null) ? 0 : range.hashCode());
		result = prime * result + ((unit == null) ? 0 : unit.hashCode());
		// result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Observable other = (Observable) obj;
		if (classifier == null) {
			if (other.classifier != null) {
				return false;
			}
		} else if (!classifier.equals(other.classifier)) {
			return false;
		}
		if (currency == null) {
			if (other.currency != null) {
				return false;
			}
		} else if (!currency.equals(other.currency)) {
			return false;
		}
		if (downTo == null) {
			if (other.downTo != null) {
				return false;
			}
		} else if (!downTo.equals(other.downTo)) {
			return false;
		}
		if (main == null) {
			if (other.main != null) {
				return false;
			}
		} else if (!main.equals(other.main)) {
			return false;
		}
		// if (name == null) {
		// if (other.name != null) {
		// return false;
		// }
		// } else if (!getName().equals(other.getName())) {
		// return false;
		// }
		if (observable == null) {
			if (other.observable != null) {
				return false;
			}
		} else if (!observable.equals(other.observable)) {
			return false;
		}
		// if (optional != other.optional) {
		// return false;
		// }
		if (range == null) {
			if (other.range != null) {
				return false;
			}
		} else if (!range.equals(other.range)) {
			return false;
		}
		if (unit == null) {
			if (other.unit != null) {
				return false;
			}
		} else if (!unit.equals(other.unit)) {
			return false;
		}
		// if (value == null) {
		// if (other.value != null) {
		// return false;
		// }
		// } else if (!value.equals(other.value)) {
		// return false;
		// }
		return true;
	}

	public boolean canResolve(Observable obj) {
		if (this.main.equals(obj.main)) {
			if (this.observable.equals(obj.observable)) {
				// this takes care of this.by == obj.by
				return true;
			}
			if (obj.classifier != null && this.classifier == null) {
				return true;
			}
			if (this.classifier != null && obj.classifier != null && obj.classifier.equals(this.classifier)) {
				return ((this.downTo == null && obj.downTo == null) || ((this.downTo != null && obj.downTo != null
						&& Concepts.INSTANCE.compareSpecificity(this.downTo, obj.downTo, true) >= 0)));
			}
		}
		return false;
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
	public IConcept getContext() {
		return Observables.INSTANCE.getContextType(getType());
	}

	@Override
	public IConcept getInherentType() {
		return Observables.INSTANCE.getInherentType(getType());
	}

	@Override
	public IConcept getComparisonType() {
		return Observables.INSTANCE.getComparisonType(getType());
	}

	@Override
	public IConcept getCaused() {
		return Observables.INSTANCE.getCausedType(getType());
	}

	@Override
	public IConcept getCausant() {
		return Observables.INSTANCE.getCausantType(getType());
	}

	@Override
	public IConcept getCompresent() {
		return Observables.INSTANCE.getCompresentType(getType());
	}

	@Override
	public IConcept getPurpose() {
		return Observables.INSTANCE.getGoalType(getType());
	}

	@Override
	public IArtifact.Type getArtifactType() {

		if (observable != null) {
			if (classifier != null || observable.is(Type.CLASS) || observable.is(Type.TRAIT)) {
				return IArtifact.Type.CONCEPT;
			} else if (observable.is(Type.PRESENCE)) {
				return IArtifact.Type.BOOLEAN;
			} else if (observable.is(Type.QUALITY)) { // don't reorder these!
				return IArtifact.Type.NUMBER;
			} else if (observable.is(Type.COUNTABLE)) {
				return IArtifact.Type.OBJECT;
			} else if (observable.is(Type.CONFIGURATION)) {
				return IArtifact.Type.OBJECT;
			} else if (observable.is(Type.PROCESS)) {
				return IArtifact.Type.OBJECT;
			}
		}
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

	public void setAggregator(Concept by) {
		this.aggregator = by;
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
	 * Return this same observable after assigning the passed pre-observed value to it.
	 * Used to create states:
	 * <pre>
	 *     state = subject.observe(Observable.promote(concept).withValue(3));
	 * </pre>
	 * @param value
	 * @return
	 */
	public Observable withValue(Object value) {
	    this.value = value;
	    return this;
	}
	
	@Override
	public Set<IConcept> getAssignedRoles() {
		return assignedRoles;
	}

	public List<IAnnotation> getAnnotations() {
		return this.annotations;
	}

	public void addAnnotation(Annotation annotation) {
		this.annotations.add(annotation);
	}

	public boolean isResolved() {
		return value instanceof Number || value instanceof Boolean || value instanceof IConcept
				|| value instanceof IKimExpression || value instanceof IServiceCall;
	}
}
