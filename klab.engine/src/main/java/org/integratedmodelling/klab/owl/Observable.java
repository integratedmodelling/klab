package org.integratedmodelling.klab.owl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.kim.api.IKimExpression;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.kim.api.ValueOperator;
import org.integratedmodelling.klab.Authentication;
import org.integratedmodelling.klab.Concepts;
import org.integratedmodelling.klab.Observables;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.Units;
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
import org.integratedmodelling.klab.api.observations.scale.ExtentDimension;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.common.mediation.Currency;
import org.integratedmodelling.klab.common.mediation.Unit;
import org.integratedmodelling.klab.dataflow.Dataflow;
import org.integratedmodelling.klab.engine.runtime.Session;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeContext;
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
public class Observable implements IObservable {

	protected Concept observable;
	protected Concept main;
	private String name;
	private String declaration;
	private boolean isAbstract;
	private Range range;
	private Unit unit;
	private Currency currency;
	private Concept classifier;
	private Concept downTo;
	private Object value;
	private ObservationType observationType;
	private boolean optional;
	private boolean generic;
	private String observerId;
	private IDirectObservation observer;
	private ValueOperator valueOperator;
	private Object valueOperand;

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
	private boolean givenName;
    private String url;

	Observable(Concept concept) {
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

		if (this.getType().is(Type.EXTENSIVE_PROPERTY)) {
			IUnit unit = this.getUnit();
			if (unit != null) {
				Set<ExtentDimension> toAdd = new HashSet<>();
				if (scale.isSpatiallyDistributed() && !Units.INSTANCE.isSpatialDensity(unit, scale.getSpace())
						&& !Units.INSTANCE.isArea(unit)) {
					toAdd.add(Units.INSTANCE.getExtentDimension(scale.getSpace()));
				}
				if (scale.isTemporallyDistributed() && !Units.INSTANCE.isRate(unit) /* TODO add isTime condition */) {
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
		return promote(concept, true);
	}

	public static Observable promote(IConcept concept, boolean setUnits) {
		if (concept instanceof Observable) {
			return (Observable) concept;
		}
		Observable ret = new Observable((Concept) concept);
		ret.observable = (Concept) concept;
		ret.main = (Concept) concept;
		ret.declaration = concept.getDefinition().trim();
		ret.isAbstract = concept.isAbstract();
		ret.generic = concept.isAbstract();

		if (setUnits) {
			ret.unit = (Unit) Units.INSTANCE.getDefaultUnitFor(concept);
			if (ret.unit != null) {
				ret.declaration += " in " + ret.unit;
			}
		}
		return ret;
	}

	public Observable(Observable observable) {
		this.observable = observable.observable;
		this.main = observable.main;
		this.name = observable.name;
		this.declaration = observable.declaration;
		this.isAbstract = observable.isAbstract;
		this.range = observable.range;
		this.unit = observable.unit;
		this.currency = observable.currency;
		this.classifier = observable.classifier;
		this.downTo = observable.downTo;
		this.value = observable.value;
		this.observationType = observable.observationType;
		this.optional = observable.optional;
		this.generic = observable.generic;
		this.annotations.addAll(observable.getAnnotations());
		this.valueOperand = observable.valueOperand;
		this.valueOperator = observable.valueOperator;
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
	public ObservationType getObservationType() {
		if (observationType == null && observable != null) {
			if (observable.is(Type.CLASS) || observable.is(Type.TRAIT)) {
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
			} else if (observable.is(Type.TRAIT) || observable.is(Type.ROLE)) {
				observationType = ObservationType.ATTRIBUTION;
				System.out.println("OSTIA un attributo nel posto sbagliato: " + observable);
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
		return "[" + getName() + " = " + this.declaration + "]";
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
		} else if (other.main == null || !main.equals(other.main)) {
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
		if (valueOperator == null) {
			if (other.valueOperator != null) {
				return false;
			}
		} else if (!valueOperator.equals(other.valueOperator) || !valueOperand.equals(other.valueOperand)) {
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

		boolean ok = false;

		if (this.main.equals(obj.main)) {

			ok = true;
			if (this.observable.equals(obj.observable)) {
				
				ok = obj.classifier == null && this.classifier == null || (this.classifier != null
						&& obj.classifier != null && obj.classifier.equals(this.classifier));
				
				if (ok) {
					ok = ((this.downTo == null && obj.downTo == null) || ((this.downTo != null && obj.downTo != null
							&& Concepts.INSTANCE.compareSpecificity(this.downTo, obj.downTo, true) >= 0)));
				}
				if (ok) {
					/*
					 * the one without the operator can resolve the one with the operator.
					 */
					ok = obj.valueOperator == null || (obj.valueOperator.equals(this.valueOperator)
							&& ((obj.valueOperand == null && this.valueOperand == null)
									|| (obj.valueOperand != null && obj.valueOperand.equals(this.valueOperand))));
				}
			}
		}
		
		return ok;

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
			if (/* classifier != null || */ observable.is(Type.CLASS) || observable.is(Type.TRAIT)) {
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
	public List<IAnnotation> getAnnotations(IRuntimeContext context) {
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
		boolean ret = getType() == null ? false : getType().is(c);
		if (ret && semantics instanceof IObservable) {
			if (((IObservable) semantics).getClassifier() != null) {
				ret = getClassifier() == null || getClassifier().is(((IObservable) semantics).getClassifier());
			}
			if (ret && ((IObservable) semantics).getDownTo() != null) {
				ret = getDownTo() == null || getDownTo().is(((IObservable) semantics).getDownTo());
			}
		}
		return ret;
	}

	public String getNamespace() {
		// TODO if we come from a declaration in a given namespace, use that
		return getType().getNamespace();
	}

	@Override
	public boolean is(Type type) {
		return getType() == null ? false : getType().is(type);
	}

	@Override
	public ValueOperator getValueOperator() {
		return valueOperator;
	}

	@Override
	public Object getValueOperand() {
		return valueOperand;
	}

	public void setValueOperator(ValueOperator valueOperator) {
		this.valueOperator = valueOperator;
	}

	public void setValueOperand(Object valueOperand) {
		this.valueOperand = valueOperand;
	}

	public void setGivenName(boolean b) {
		this.givenName = b;
	}
	
	/**
	 * If true, the name is locked in by the user with a 'named' clause and shouldn't be changed.
	 * 
	 * @return
	 */
	public boolean isGivenName() {
		return this.givenName;
	}

    public void setUrl(String uri) {
        this.url = uri;
    }
    
    public String getUrl() {
        return this.url;
    }

}
