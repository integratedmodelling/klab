package org.integratedmodelling.klab.services.reasoner.owl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Currency;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.codehaus.groovy.transform.trait.Traits;
import org.integratedmodelling.kim.api.IKimConcept;
import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.kim.api.IKimExpression;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.kim.api.IValueMediator;
import org.integratedmodelling.kim.api.ValueOperator;
import org.integratedmodelling.klab.Concepts;
import org.integratedmodelling.klab.Observables;
import org.integratedmodelling.klab.api.auth.Roles;
import org.integratedmodelling.klab.api.data.mediation.ICurrency;
import org.integratedmodelling.klab.api.data.mediation.IUnit;
import org.integratedmodelling.klab.api.data.mediation.KCurrency;
import org.integratedmodelling.klab.api.data.mediation.KUnit;
import org.integratedmodelling.klab.api.knowledge.ISemantic;
import org.integratedmodelling.klab.api.knowledge.IViewModel;
import org.integratedmodelling.klab.api.knowledge.KConcept;
import org.integratedmodelling.klab.api.knowledge.KModel;
import org.integratedmodelling.klab.api.knowledge.KObservable;
import org.integratedmodelling.klab.api.knowledge.SemanticRole;
import org.integratedmodelling.klab.api.knowledge.observation.scope.KScope;
import org.integratedmodelling.klab.api.model.IAnnotation;
import org.integratedmodelling.klab.api.model.IKimObject;
import org.integratedmodelling.klab.api.model.KConceptDefinition;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.observations.ISubject;
import org.integratedmodelling.klab.api.provenance.IActivity;
import org.integratedmodelling.klab.api.provenance.KArtifact;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.common.mediation.Unit;
import org.integratedmodelling.klab.dataflow.Dataflow;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
import org.integratedmodelling.klab.exceptions.KlabUnimplementedException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.services.reasoner.internal.CoreOntology.NS;
import org.integratedmodelling.klab.services.reasoner.owl.adapter.KimKnowledgeProcessor;
import org.integratedmodelling.klab.services.reasoner.owl.adapter.ObservableBuilder;
import org.integratedmodelling.klab.utils.Pair;
import org.integratedmodelling.klab.utils.Range;
import org.integratedmodelling.klab.utils.Utils.Kim;

import groovy.lang.GroovyObjectSupport;
import groovy.transform.CompileStatic;

/**
 * Equality ignores differences of name, value, optional and generic status.
 * 
 * @author ferdinando.villa
 */
@CompileStatic
public class Observable extends GroovyObjectSupport implements KObservable {

    private KObservable incarnatedAbstract;
    protected KConcept observable;
    private String definition;
    private boolean isAbstract;
    private Range range;
    private KUnit unit;
    private KCurrency currency;
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
    private KConcept temporalInherent;
    private Resolution resolution;
    private boolean specialized = false;
    private Set<KConcept> contextualRoles = new HashSet<>();
    private Object defaultValue = null;
    private Set<SemanticRole> resolutionExceptions = EnumSet.noneOf(SemanticRole.class);

    /**
     * The "kosher" name of the observable, possibly ambiguous w.r.t. the semantics and possibly
     * overridden by the stated name ('named').
     */
    private String name;
    /**
     * The stated name in the 'named' clause, or null if no name was supplied.
     */
    private String statedName;
    /**
     * The reference name, unambiguous w.r.t. the semantics and not for human consumption, suitable
     * as an identifier when an absolute reference to the observable is needed.
     */
    private String referenceName;

    /*
     * Target predicate is a concrete predicate that may be added to the observable that classifies
     * its abstract base predicate, so that any outputs that do not have the exact target predicate
     * after classification can be marked as irrelevant to the observation and hidden.
     */
    private KConcept targetPredicate;

    /**
     * This and the next support situations in which the observable contains a pre-resolved model,
     * such as when models (including non-semantic ones) are used as dependencies. It's a convenient
     * implementation trick for now, so it does not affect the public API.
     */
    transient KModel resolvedModel;
    private String modelReference;

    // only used to resolve the subject observable if it has to be marshalled across
    // network boundaries
    transient String sessionId;
    private List<IAnnotation> annotations = new ArrayList<>();
    private String url;

    // added to carry the attribute if the resolution of the observable comes from
    // an instantiator dereification
    private String dereifiedAttribute;

    // kept in the observables generated after resolution of abstract predicates to
    // remember the lineage and
    // enable scoping for downstream resolutions.
    private Map<KConcept, KConcept> resolvedPredicates = new HashMap<>();
    // also keep the full set of the resolved predicates of which we resolve just
    // one, in stable
    // order so that the reporting
    // system can use it.
    private Map<KConcept, Set<KConcept>> resolvedPredicatesContext = new HashMap<>();
    // and the original abstract one so we can compare two observables to see if
    // they incarnate the same.
    public Observable incarnatedAbstractObservable;

    // keep these here for speed after computing them in case of repeated
    // resolutions.
    private Set<KConcept> abstractPredicates_;

    /*
     * this is only for debugging
     */
    transient String originatingModelId;
    private boolean mustContextualize;
    private boolean global;

    // explicitly set in the builder, used to avoid scheduling for now
    private boolean dereified;
    private Observable deferredTarget;
    // TODO check if the above fills the scope of this one.
    List<Observable> deferredObservables = new ArrayList<>();

    Observable(KConcept concept) {
        this.observable = concept;
        this.name = concept.getCodeName();
    }

    public static Observable promote(KConceptDefinition concept) {
        return promote(concept.getConcept());
    }

    public static Observable promote(KModel model) {
        Observable ret = new Observable(model.getObservables().get(0));
        ret.resolvedModel = model;
        return ret;
    }

    /**
     * The observable for a view is for now the only instance of a void observable as the view is a
     * non-semantic artifact. The observable's reference name is derived from the fully qualified
     * view name.
     * 
     * @param view
     * @return
     */
    public static Observable promote(IViewModel view) {
        Observable ret = new Observable(Concepts.c(NS.CORE_VOID));
        ret.generic = false;
        ret.definition = NS.CORE_VOID;
        ret.referenceName = ret.name = view.getName().replaceAll("\\.", "_");
        ret.resolvedModel = new Model(view);
        return ret;
    }

    public static Observable promote(KConcept concept) {

        Observable ret = new Observable((Concept) concept);

        ret.observable = (Concept) concept;
        ret.definition = concept.getDefinition().trim();
        ret.isAbstract = concept.isAbstract();
        ret.generic = concept.is(Type.ROLE);
        ret.referenceName = concept.getReferenceName();
        ret.name = Concepts.INSTANCE.getCodeName(concept);
        if (ret.referenceName == null) {
            // only happens with non-standard observables from system ontologies
            ret.referenceName = KimKnowledgeProcessor.getCleanFullId(concept.getNamespace(),
                    concept.getName());
        }

        return ret;
    }

    /**
     * Return an observable that has all the abstract predicates contained in the passed map
     * substituted by the correspondent concrete ones.
     * 
     * @param observable
     * @param resolved
     * @param incarnated
     * @return
     */
    public static KObservable concretize(KObservable observable, Map<KConcept, KConcept> resolved,
            Map<KConcept, Set<KConcept>> incarnated) {

        if (resolved.isEmpty()) {
            return observable;
        }

        Collection<KConcept> abs = observable.getAbstractPredicates();
        if (abs.isEmpty()) {
            return observable;
        }

        KObservable ret = replaceComponent((Observable) observable, resolved);

        for (KConcept key : resolved.keySet()) {
            if (abs.contains(key)) {
                ((Observable) ret).resolvedPredicates.put(key, resolved.get(key));
                ((Observable) ret).resolvedPredicatesContext.put(key,
                        new LinkedHashSet<>(incarnated.get(key)));
                ((Observable) ret).incarnatedAbstractObservable = (Observable) observable;
            }
            if (key.is(Type.ROLE)) {
                ret.getContextualRoles().add(key);
            }
        }

        /**
         * Concretized observables must keep the stated name if any, so that no models are broken.
         */
        ((Observable) ret).setStatedName(observable.getStatedName());

        ((Observable) ret).unit = ((Observable) observable).unit;
        ((Observable) ret).currency = ((Observable) observable).currency;
        ((Observable) ret).range = ((Observable) observable).range;
        ((Observable) ret).valueOperators.addAll(((Observable) observable).valueOperators);

        return ret;
    }

    public Observable(Observable observable) {
        this.observable = observable.observable;
        this.name = observable.name;
        this.referenceName = observable.referenceName;
        this.definition = observable.definition;
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
        this.resolution = observable.resolution;
        this.statedName = observable.statedName;
        this.contextualRoles.addAll(observable.contextualRoles);
        this.dereifiedAttribute = observable.dereifiedAttribute;
        this.resolvedPredicates.putAll(observable.resolvedPredicates);
        this.resolvedPredicatesContext.putAll(observable.resolvedPredicatesContext);
        this.dereified = observable.dereified;
        this.specialized = observable.specialized;
        this.deferredTarget = observable.deferredTarget;
        this.defaultValue = observable.defaultValue;
        this.resolutionExceptions = observable.resolutionExceptions;
    }

    public static KObservable replaceComponent(Observable original, Map<KConcept, KConcept> replacements) {

        if (replacements.isEmpty()) {
            return original;
        }

        String declaration = original.getDefinition();
        for (KConcept key : replacements.keySet()) {
            String rep = replacements.get(key).getDefinition();
            if (rep.contains(" ")) {
                rep = "(" + rep + ")";
            }
            declaration = declaration.replace(key.toString(), rep);
        }

        return Observables.INSTANCE.declare(declaration);
    }

    public Observable withoutModel() {
        this.originatingModelId = null;
        this.modelReference = null;
        this.resolvedModel = null;
        return this;
    }

    @Override
    public KConcept getType() {
        return observable;
    }

    @Override
    public String getName() {
        return statedName == null ? name : statedName;
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
        return isAbstract || !getAbstractPredicates().isEmpty();
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * "Fluent" setName for special circumstances. Use with caution and only OUTSIDE of resolution.
     * Does not substitute the "named" clause in k.IM - that is {@link #setStatedName(String)}.
     * 
     * @param name
     * @return
     */
    public Observable named(String name) {
        this.name = name;
        return this;
    }

    public void setDefinition(String definition) {
        this.definition = definition.trim();
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

    public Observable withResolvedModel(KModel model) {
        Observable ret = new Observable(this);
        ret.resolvedModel = model;
        return ret;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    @Override
    public String getDefinition() {
        return definition;
    }

    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public Collection<KConcept> getContextualRoles() {
        return this.contextualRoles;
    }

    @Override
    public String getDeclaration() {
        return (value == null ? "" : (Kim.INSTANCE.encodeValue(value) + " as ")) + definition
                + (unit == null ? "" : (" in " + unit)) + (currency == null ? "" : (" in " + currency))
                + (range == null ? "" : (" " + Kim.INSTANCE.encodeValue(range)))
                + (statedName == null ? "" : (" named " + statedName));
    }

    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    public IActivity.Description getDescriptionType() {
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
                observationType = distributed
                        ? IActivity.Description.CLASSIFICATION
                        : IActivity.Description.CHARACTERIZATION;
            } else {
                // void observable: just do it, no semantics.
                observationType = IActivity.Description.COMPILATION;
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
        return "[" + getName() + " = " + this.definition + "]";
    }

    /**
     * Checks for equality of 'actual' meaning, i.e. equal observables and observers besides name
     * and mediators.
     * 
     * @param obj
     * @return
     */
    public boolean resolvesStrictly(Observable obj) {

        if (observer == null) {
            if (obj.observer != null) {
                return false;
            }
        } else if (!observer.equals(obj.observer)) {
            return false;
        }

        boolean conceptsAreEqual = this.observable.getDefinition().equals(obj.observable.getDefinition());

        /*
         * TODO check: operators are only allowed at the receiving end. We should also allow the
         * same operators and operands as us in the provider.
         */
        return conceptsAreEqual && CollectionUtils.isEqualCollection(this.valueOperators, obj.valueOperators);
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
        } else if (!Observables.INSTANCE.compareOperators(valueOperators, other.valueOperators))
            return false;
        return true;
    }

    public void setModelReference(String modelReference) {
        this.modelReference = modelReference;
    }

    public KModel getReferencedModel(KScope scope) {
        if (this.resolvedModel == null && this.modelReference != null) {
            IKimObject model = Resources.INSTANCE.getModelObject(modelReference);
            if (model == null && scope != null) {
                model = scope.findResolvedModel(modelReference);
            }
            if (!(model instanceof KModel)) {
                throw new KlabValidationException(
                        "referenced object " + modelReference + " does not exist or is not a model");
            }
            this.resolvedModel = (KModel) model;
        }
        return this.resolvedModel;
    }

    @Override
    public KArtifact.Type getArtifactType() {

        if (observable != null) {
            if (observable.is(Type.CLASS)) {
                return KArtifact.Type.CONCEPT;
            } else if (observable.is(Type.PRESENCE)) {
                return KArtifact.Type.BOOLEAN;
            } else if (observable.is(Type.QUALITY)) { // don't reorder these!
                return KArtifact.Type.NUMBER;
            } else if (observable.is(Type.EVENT)) {
                return KArtifact.Type.EVENT;
            } else if (observable.is(Type.COUNTABLE)) {
                return KArtifact.Type.OBJECT;
            } else if (observable.is(Type.CONFIGURATION)) {
                return KArtifact.Type.OBJECT;
            } else if (observable.is(Type.PROCESS)) {
                return KArtifact.Type.PROCESS;
            } else if (observable.is(Type.TRAIT) || observable.is(Type.ROLE)) {
                return KArtifact.Type.VALUE;
            }
        }
        // trait and role observers specify filters, which produce void.
        return KArtifact.Type.VOID;
    }

    @Override
    public Builder getBuilder(IMonitor monitor) {
        return new ObservableBuilder(this, monitor);
    }

    public static Observable promote(KConcept operand, Observable observable2) {
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
    public KObservable subjectify(IDirectObservation observer) {
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
     * Return this same observable after assigning the passed pre-observed value to it. Used to
     * create states:
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
     * Annotations that may have interactive parameters changed can be retrieved using this one,
     * which will find the modifications in the context and produce copies with modified values.
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
        return value instanceof Number || value instanceof Boolean || value instanceof KConcept
                || value instanceof IKimExpression
                || value instanceof IServiceCall;
    }

    public boolean leftShift(ISemantic semantics) {
        return Reasoner.INSTANCE.is(getType(), semantics);
    }

    public boolean rightShift(ISemantic semantics) {
        return Reasoner.INSTANCE.isRelated(getType(), semantics);
    }

    @Override
    public boolean is(ISemantic semantics) {
        KConcept c = semantics.getType();
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
     * An observable with fluid units is one that needs units where the user has chosen to not
     * declare them. These are treated specially during resolution, where units will be attributed
     * based on the dependencies but will also be matched across sibling dependencies to ensure
     * comparability. Observables not coming from a declaration may still have no units (with
     * semantics that require them) and fluidUnits = false.
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
         * only resolve rescaling attributes for qualities, where attributes transform values. For
         * direct observations, resolve everything.
         */
        if (observable.is(Type.OBSERVABLE)) {
            for (KConcept c : Traits.INSTANCE.getDirectTraits(observable)) {
                if (!c.is(Type.ABSTRACT)) {
                    if (is(Type.QUALITY) && !c.is(Type.RESCALING)) {
                        continue;
                    }
                    return true;
                }
            }
            if (!is(Type.QUALITY)) {
                for (KConcept c : Roles.INSTANCE.getDirectRoles(observable)) {
                    if (!c.is(Type.ABSTRACT)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Find the first resolvable trait and return it along with a new observable without it. Get
     * traits first so that roles can be resolved with the full trait set resolved.
     * 
     * @return
     */
    public Pair<KConcept, Observable> popResolvableTrait(IMonitor monitor) {
        KConcept resolvable = null;
        if (observable.is(Type.OBSERVABLE)) {
            for (KConcept c : Traits.INSTANCE.getDirectTraits(observable)) {
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
                    for (KConcept c : Roles.INSTANCE.getDirectRoles(observable)) {
                        if (!c.is(Type.ABSTRACT)) {
                            resolvable = c;
                            break;
                        }
                    }
                }
            }
        }

        return resolvable == null
                ? null
                : new Pair<>(resolvable,
                        (Observable) getBuilder(monitor).without(resolvable).buildObservable());
    }

    /*
     * package private after removing the disambiguator in ResolutionScope
     */
    void setReferenceName(String name) {
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

    public void setTargetPredicate(KConcept targetPredicate) {
        this.targetPredicate = targetPredicate;
    }

    public KConcept getTargetPredicate() {
        return this.targetPredicate;
    }

    /**
     * Used within Groovy and k.Actors to extract the actual predicate that resolves the passed one.
     * 
     * @param abstractPredicate anything referring to a concept - string, concept or Groovy peer for
     *        now.
     * @return
     */
    public KConcept getPredicate(Object abstractPredicate) {

        KConcept predicate = Concepts.INSTANCE.asConcept(abstractPredicate);
        if (predicate == null) {
            return null;
        }

        for (KConcept c : Traits.INSTANCE.getTraits(getType())) {
            if (c.is(predicate)) {
                return c;
            }
        }

        /*
         * Predicates within the scope of a first-level operator are also returned. TODO should we
         * recurse? probably not - but keep this in mind.
         */
        KConcept described = Observables.INSTANCE.getDescribedType(getType());
        if (described != null) {
            for (KConcept c : Traits.INSTANCE.getTraits(described)) {
                if (c.is(predicate)) {
                    return c;
                }
            }
        }
        return predicate;
    }

    public void setMustContextualizeAtResolution(boolean b) {
        this.mustContextualize = b;
    }

    /**
     * Return true only when the observable is a dependency of an instantiator, which is resolved
     * within the context of resolution. In these situations the context for the observable cannot
     * be resolved at model parsing, so it must be done when used.
     * 
     * @return true if observable needs context but the model cannot establish which context at
     *         declaration time.
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
    public KConcept context() {
        return Observables.INSTANCE.getContext(this.getType());
    }

    @Override
    public KConcept inherent() {
        return Observables.INSTANCE.getInherency(this.getType());
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public KConcept getTemporalInherent() {
        return temporalInherent;
    }

    public void setTemporalInherent(KConcept temporalInherent) {
        this.temporalInherent = temporalInherent;
    }

    @Override
    public boolean isGlobal() {
        return this.global;
    }

    public void setGlobal(boolean global) {
        this.global = global;
    }

    @Override
    public Resolution getResolution() {
        return this.resolution;
    }

    public void setResolution(Resolution resolution) {
        this.resolution = resolution;
    }

    public KObservable withRole(KConcept role) {
        this.contextualRoles.add(role);
        return this;
    }

    public String getDereifiedAttribute() {
        return this.dereifiedAttribute;
    }

    public void setDereifiedAttribute(String dereifiedAttribute) {
        this.dereifiedAttribute = dereifiedAttribute;
    }

    @Override
    public boolean resolves(KObservable other, KConcept context) {
        return ((Concept) getType()).resolves(other.getType(), context,
                ((Observable) other).resolvedPredicates)
                && CollectionUtils.isEqualCollection(this.valueOperators,
                        ((Observable) other).valueOperators);
    }

    @Override
    public Collection<KConcept> getAbstractPredicates() {

        if (this.abstractPredicates_ == null) {

            this.abstractPredicates_ = new HashSet<>();

            if (getType() != null && !isGeneric()) {

                /*
                 * remove operators if any
                 */
                KConcept target = getType();
                KConcept defined = Observables.INSTANCE.getDescribedType(target);
                if (defined != null) {
                    target = defined;
                }

                for (KConcept c : Concepts.INSTANCE.collectComponents(target,
                        EnumSet.of(IKimConcept.Type.ABSTRACT))) {
                    if (c.is(IKimConcept.Type.ROLE)) {
                        this.abstractPredicates_.add(c);
                    } else if (c.is(IKimConcept.Type.IDENTITY)) {
                        if (Observables.INSTANCE.getRequiredIdentities(target).contains(c)) {
                            this.abstractPredicates_.add(c);
                        }
                    }
                }
            }
        }

        return this.abstractPredicates_;
    }

    @Override
    public Map<KConcept, KConcept> getResolvedPredicates() {
        return resolvedPredicates;
    }

    @Override
    public String getStatedName() {
        return statedName;
    }

    public void setStatedName(String statedName) {
        this.statedName = statedName;
    }

    public Map<KConcept, Set<KConcept>> getResolvedPredicatesContext() {
        return this.resolvedPredicatesContext;
    }

    @Override
    public Object getProperty(String property) {
        switch(property) {
        case "displayLabel":
            return Concepts.INSTANCE.getDisplayLabel(this);
        case "codeName":
            return Concepts.INSTANCE.getCodeName(this.getType());
        }
        return super.getProperty(property);
    }

    public void setDereified(boolean dereified) {
        this.dereified = dereified;
    }

    @Override
    public boolean isDereified() {
        return this.dereified;
    }

    /**
     * True if two observables incarnate the same abstract observable, even if they're different.
     * 
     * @param obs
     * @return
     */
    public boolean incarnatesSame(KObservable obs) {
        return this.incarnatedAbstractObservable != null
                && ((Observable) obs).incarnatedAbstractObservable != null
                && this.incarnatedAbstractObservable.equals(((Observable) obs).incarnatedAbstractObservable);
    }

    public void setIncarnatedAbstractObservable(Observable incarnatedAbstractObservable) {
        this.incarnatedAbstractObservable = incarnatedAbstractObservable;
    }

    public KObservable getIncarnatedAbstract() {
        return incarnatedAbstract;
    }

    public void setIncarnatedAbstract(KObservable incarnatedAbstract) {
        this.incarnatedAbstract = incarnatedAbstract;
    }

    @Override
    public boolean isSpecialized() {
        return specialized;
    }

    public void setSpecialized(boolean specialized) {
        this.specialized = specialized;
    }

    public String getModelReference() {
        return this.modelReference;
    }

    public void setDeferredTarget(Observable observable) {
        this.deferredTarget = observable;
    }

    /**
     * The actual target of a deferred observation. May differ from the incarnated type if the
     * deferring model is resolving through one of its secondary outputs (e.g. this observable is a
     * process and the deferred target is one of the qualities it affects).
     * 
     * @return
     */
    public Observable getDeferredTarget() {
        return this.deferredTarget;
    }

    /**
     * FIXME this may need to be integrated with the previous, needs to be checked.
     * 
     * @return
     */
    public Collection<Observable> getDeferredObservables() {
        return this.deferredObservables;
    }

    public IValueMediator getMediator() {
        if (this.unit != null) {
            return this.unit;
        }
        if (this.currency != null) {
            return this.currency;
        }
        if (this.range != null) {
            return this.range;
        }
        return null;
    }

    public Observable withMediator(IValueMediator mediator) {

        if (mediator instanceof IUnit) {
            this.unit = (Unit) mediator;
        } else if (mediator instanceof ICurrency) {
            this.currency = (Currency) mediator;
        } else if (mediator instanceof Range) {
            this.range = (Range) mediator;
        }

        return this;
    }

    @Override
    public Object getDefaultValue() {
        return defaultValue;
    }

    @Override
    public Collection<ResolutionException> getResolutionExceptions() {
        return resolutionExceptions;
    }

}
