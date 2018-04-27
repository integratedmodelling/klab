package org.integratedmodelling.klab.extensions.groovy

import org.apache.commons.math3.special.Erf
import org.codehaus.groovy.runtime.NullObject
import org.integratedmodelling.klab.utils.NumberUtils
import org.integratedmodelling.klab.api.observations.IDirectObservation
import org.integratedmodelling.klab.api.observations.IEvent
import org.integratedmodelling.klab.api.observations.IObservation
import org.integratedmodelling.klab.api.observations.IProcess
import org.integratedmodelling.klab.api.observations.IRelationship
import org.integratedmodelling.klab.api.observations.IState
import org.integratedmodelling.klab.api.observations.ISubject
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor
import org.integratedmodelling.klab.common.mediation.Unit
import org.integratedmodelling.klab.exceptions.KlabException
import org.integratedmodelling.klab.utils.Pair
import org.jscience.physics.amount.Amount

/**
 * The base class for any k.LAB action or script.
 * 
 * @author Ferd
 *
 */
abstract class ActionBase extends Script {

    static inited = false;
    private Random rgen = new Random();

    ActionBase() {
        if (!inited) {
            /*
             * enable <n>.<unit> notation to return a number with units.
             */
            ExpandoMetaClass.enableGlobally();
            Number.metaClass.getProperty = { String symbol ->
                Amount.valueOf(delegate, Unit.valueOf(symbol))
            }

            /*
             * enable arithmetics and comparisons with units:
             *         println( 18.4.kg * 2 )
             *         println( 1800000.kg / 3 )
             *         println( 1.kg * 2 + 3.kg / 4 )
             *         println( 3.cm + 12.m * 3 - 1.km )
             *         println( 1.5.h + 33.s - 12.min )
             *         println( 30.m**2 - 100.ft**2 )
             *         println( -3.h )
             *         println( 3.h < 4.h )
             */
            Amount.metaClass.multiply = { Number factor -> delegate.times(factor) }
            Number.metaClass.multiply = { Amount amount -> amount.times(delegate) }
            Number.metaClass.div = { Amount amount -> amount.inverse().times(delegate) }
            Amount.metaClass.div = { Number factor -> delegate.divide(factor) }
            Amount.metaClass.div = { Amount factor -> delegate.divide(factor) }
            Amount.metaClass.power = { Number factor -> delegate.pow(factor) }
            Amount.metaClass.negative = { -> delegate.opposite() }

            // allows null-proof multiplications and division
            NullObject.metaClass.multiply = { Number n -> null }
            NullObject.metaClass.div = { Number n -> null }
            NullObject.metaClass.plus = { Number n -> null }
            NullObject.metaClass.minus = { Number n -> null }
        }
    }

    /**
     * Compiled in before anything happens; will check for context and target and
     * set up any proxies.
     */
    def wrap() {

        // do this or we get a concurrent modification exception
        Map<?,?> vars = new HashMap<Object,Object>(binding.getVariables());
        for (vname in vars.keySet()) {
//            if (vname.equals("_context")) {
//                binding.setVariable("context", wrapObject(binding.getVariable("_context"), true));
//            } else if (vname.equals("_self")) {
//                binding.setVariable("self", wrapObject(binding.getVariable("_self"), false));
//            } else if (vname.equals("_transition") && binding.getVariable("_transition") != null) {
//                binding.setVariable("now", new Transition(((ITransition)binding.getVariable('_transition'))));
//            } else if (vname.equals("_p")) {
//                Map<?,?> parms = binding.getVariable(vname);
//                for (o in parms.keySet()) {
//                    if (parms.get(o) instanceof IConcept) {
//                        parms.put(o, new Concept(parms.get(o), binding));
//                    }
//                }
//            } else if (vars.get(vname) instanceof IConcept) {
//                binding.setVariable(vname, new Concept(vars.get(vname), binding));
//            }
        }
    }

    /**
     * Random function is always available. TODO establish a mechanism to
     * set the seed. 
     * @return
     */
    def random() {
        rgen.nextDouble();
    }

    /**
     * Missing math functions, using Apache math
     * 
     * @param observation
     * @param isContext
     * @return
     */
    def erf(double d) {
        Erf.erf(d);
    }
    
    /**
     * Reliably check for nodata
     * @param o
     * @return
     */
    def nodata(Object o) {
        return o == null || (o instanceof Double && Double.isNaN(o)) || (o instanceof Float && Float.isNaN(o));
    }

    /**
     * Missing math functions, using Apache math
     *
     * @param observation
     * @param isContext
     * @return
     */
    def erfc(double d) {
        Erf.erfc(d);
    }

    /**
     * Missing math functions, using Apache math
     *
     * @param observation
     * @param isContext
     * @return
     */
    def erf(double d1, double d2) {
        Erf.erf(d1, d2);
    }

    /**
     * Missing math functions, using Apache math
     *
     * @param observation
     * @param isContext
     * @return
     */
    def erfInv(double d) {
        Erf.erfInv(d);
    }

    /**
     * Missing math functions, using Apache math
     *
     * @param observation
     * @param isContext
     * @return
     */
    def erfcInv(double d) {
        Erf.erfcInv(d);
    }

    def wrapObject(IObservation observation, boolean isContext) {

        def ret = null;

        if (observation instanceof IRelationship) {
//            ret = new Relationship(observation, binding);
        } else if (observation instanceof IDirectObservation) {
//            ret = new DirectObservation(observation, binding);
        } else if (observation instanceof IState) {
//            ret = new State(observation, binding);
        }

        if (!isContext) {
//            binding.setVariable('scale', new Scale(observation.getScale(), binding));
//            binding.setVariable('space', new Space(observation.getScale().getSpace(), binding));
//            binding.setVariable('time', new Time(observation.getScale().getTime(), binding));
        }

        return ret;
    }

    String getObservationId(IObservation obs) {
        if (obs instanceof ISubject) {
            return "subject"
        }
        if (obs instanceof IEvent) {
            return "event"
        }
        if (obs instanceof IProcess) {
            return "process"
        }
        if (obs instanceof IRelationship) {
            return "relationship"
        }
        return "nothing";
    }

    def info(String text) {
        Object o = binding.getVariable("_monitor");
        if (o instanceof IMonitor) {
//            ((IMonitor)o).info(text, Messages.INFOCLASS_USER_OWN);
        }
    }
    
    def logenv() {
        def map = binding.getVariables();
        for (var in map.keySet()) {
            info (var + " = " + map.get(var));
        }
    }

    def warn(String text) {
        Object o = binding.getVariable("_monitor");
        if (o instanceof IMonitor) {
            ((IMonitor)o).warn(text);
        }
    }

    def error(String text) {
        Object o = binding.getVariable("_monitor");
        if (o instanceof IMonitor) {
            ((IMonitor)o).error(text);
        }
    }

    def fail(String text) {
        Object o = binding.getVariable("_monitor");
        if (o instanceof IMonitor) {
            ((IMonitor)o).error(text);
        }
        throw new KlabException(text);
    }

    /*
     * window into the knowledge manager
     * TODO use a wrapper that caches is() operations - also when
     * passing to script.
     */
//    Concept _getConcept(String string) {
//        return new Concept(KLAB.c(string), binding);
//    }

//    IProperty _getProperty(String string) {
//        return KLAB.p(string);
//    }

//    IKnowledge _getKnowledge(String string) {
//        return KLAB.k(string);
//    }

//    KlabUrn _getUrn(String string) {
//        return new KlabUrn(string);
//    }
//
//
//    SubjectInfo subject(Concept observable, Object... args) {
//        return new SubjectInfo(observable, args);
//    }
//
//    ProcessInfo process(Concept observable, Object... args) {
//        return new ProcessInfo(observable, args);
//    }
//
//    EventInfo event(Concept observable, Object... args) {
//
//        if (!getBinding().hasVariable("now")) {
//            throw new KlabValidationException("cannot instantiate events in non-temporal actions", getArtifact(getBinding()));
//        }
//        def aargs = [];
//        if (args != null) {
//            for (a in args) {
//                aargs.add(a);
//            }
//        }
//        def transition = getBinding().getVariable('now');
//        aargs = aargs.add(transition);
//        return new EventInfo(observable, aargs as Object[]);
//    }
//
//    StateInfo measure(Concept observable, String unit, Object value) {
//        return new StateInfo(new ObservableSemantics(ModelFactory.measureObserver(observable.concept, unit), null, observable.concept.getLocalName()), value);
//    }
//
//    /**
//     * TODO leave this only and allow value: 
//     * @param observable
//     * @param unit
//     * @return
//     */
//    StateInfo measure(Concept observable, String unit) {
//        return new StateInfo(new ObservableSemantics(ModelFactory.measureObserver(observable.concept, unit), null, observable.concept.getLocalName()), null);
//    }
//    
//    /*
//     * TODO harmonize API and use keys for value
//     */
//    StateInfo proportion(Concept observable) {
//        def self = binding.getVariable("self");
//        def comparison = self == null ? null : self.obs.getObservable().getSemantics().getType();
//        return new StateInfo(new ObservableSemantics(ModelFactory.proportionObserver(observable.concept, comparison, false), null, observable.concept.getLocalName()), null);
//    }
//    
//    StateInfo percentage(Concept observable) {
//        def self = binding.getVariable("self");
//        def comparison = self == null ? null : self.obs.getObservable().getSemantics().getType();
//        return new StateInfo(new ObservableSemantics(ModelFactory.proportionObserver(observable.concept, comparison, true), null, observable.concept.getLocalName()), null);
//    }
//
//    StateInfo proportion(Map opts, Concept observable) {
//        if (!observable.concept.is(KLAB.c(NS.CORE_PROPORTION))) {
//            observable = proportionOf(observable);
//        }
//        def value = null;
//        def comparison = null;
//        if (opts.containsKey('in')) {
//            comparison = opts.get('in');
//        } else {
//            def self = binding.getVariable("self");
//            if (self != null) {
//                comparison = new Concept(self.obs.getObservable().getSemantics().getType(), binding);
//            }
//        }
//        if (opts.containsKey('value')) {
//            value = opts.get('value');
//        }
//        return new StateInfo(new ObservableSemantics(ModelFactory.proportionObserver(observable.concept, (comparison == null ? null : comparison.concept), false), null, observable.concept.getLocalName()), value);
//    }
//    
//    StateInfo percentage(Map opts, Concept observable) {
//        def value = null;
//        def comparison = null;
//        if (opts.containsKey('in')) {
//            comparison = opts.get('in');
//        } else {
//            def self = binding.getVariable("self");
//            if (self != null) {
//                comparison = new Concept(self.obs.getObservable().getSemantics().getType(), binding);
//            }
//        }
//        if (opts.containsKey('value')) {
//            value = opts.get('value');
//        }
//        return new StateInfo(new ObservableSemantics(ModelFactory.proportionObserver(observable.concept, (comparison == null ? null : comparison.concept), true), null, observable.concept.getLocalName()), value);
//    }
//
//
//    /**
//     * TODO allow map syntax for value:, min:, max:, options
//     * @param observable
//     * @param value
//     * @return
//     */
//    StateInfo rank(Map opts, Concept observable) {
//
//        List<Integer> range = null;
//        def value = null;
//        if (opts.containsKey('min') && opts.containsKey('max')) {
//            range = new ArrayList<>();
//            range.add(opts.get('min') as Integer);
//            range.add(opts.get('max') as Integer);
//        }
//        if (opts.containsKey('value')) {
//            value = opts.get('value');
//        }
//        return new StateInfo(new ObservableSemantics(ModelFactory.rankObserver(observable.concept, range), null, observable.concept.getLocalName()), value);
//    }
//
//    StateInfo rank(Concept observable) {
//        return new StateInfo(new ObservableSemantics(ModelFactory.rankObserver(observable.concept, null), null, observable.concept.getLocalName()), null);
//    }
//
//    /**
//     * TODO allow map syntax for currency: value:, min:, max:, options
//     * @param observable
//     * @param value
//     * @return
//     */
//    StateInfo value(Map opts, Concept observable) {
//
//        def value = null;
//        def currency = new Currency();
//        def observ = Observables.makeValue(observable.concept, null);
//
//        if (opts.containsKey('currency')) {
//            currency.parse(opts.get('currency').toString());
//        } else if (opts.containsKey('min') && opts.containsKey('max')) {
//            currency.setConcept(observable.concept, opts.get('min') as Integer, opts.get('max') as Integer);
//        }
//        if (opts.containsKey('value')) {
//            value = opts.get('value');
//        }
//        return new StateInfo(new ObservableSemantics(ModelFactory.valueObserver(observ, currency), null, observ.getLocalName()), value);
//    }
//
//    /**
//     * TODO allow map syntax for currency: value:, min:, max:, options
//     * @param observable
//     * @param value
//     * @return
//     */
//    StateInfo count(Map opts, Concept observable, String unit) {
//
//        def value = null;
//        def observ = Observables.makeCount(observable.concept);
//        if (opts.containsKey('value')) {
//            value = opts.get('value');
//        }
//        return new StateInfo(new ObservableSemantics(ModelFactory.countObserver(observ, unit), null, observ.getLocalName()), value);
//    }
//    
//    /**
//     * TODO allow map syntax for currency: value:, min:, max:, options
//     * @param observable
//     * @param value
//     * @return
//     */
//    StateInfo count(Concept observable, String unit) {
//        def observ = Observables.makeCount(observable.concept);
//        return new StateInfo(new ObservableSemantics(ModelFactory.countObserver(observ, unit), null, observ.getLocalName()), null);
//    }
//
//    StateInfo count(Map opts, Concept observable) {
//
//        def value = null;
//        def unit = null;
//        def observ = Observables.makeCount(observable.concept);
//        if (opts.containsKey('value')) {
//            value = opts.get('value');
//        }
//        if (opts.containsKey('unit')) {
//            unit = opts.get('unit');
//        }
//        return new StateInfo(new ObservableSemantics(ModelFactory.countObserver(observ, unit), null, observ.getLocalName()), value);
//    }
//
//    StateInfo count(Concept observable) {
//        def observ = Observables.makeCount(observable.concept);
//        return new StateInfo(new ObservableSemantics(ModelFactory.countObserver(observ, null), null, observ.getLocalName()), null);
//    }


//    def getScope() {
//        return getScope(binding);
//    }
//
//    static def getScope(Binding binding) {
//        Object o = binding.getVariable("_provenance");
//        if (o instanceof IProvenance.Artifact) {
//            return new Artifact(artifact: o, binding: binding);
//        }
//        // TODO return empty provenance - if that is at all possible - so that users can do stuff anyway.
//        return null;
//    }
//
//    static def getArtifact(Binding binding) {
//        Object o = binding.getVariable("_provenance");
//        if (o instanceof IProvenance.Artifact) {
//            return o;
//        }
//        // TODO return empty provenance - if that is at all possible - so that users can do stuff anyway.
//        return null;
//    }

    /**
     * Used only to break during debugging of k.IM code.
     * @return
     */
    def breakpoint(Object... args) {
        def ret =  "reached breakpoint with " + (args == null ? " no args " : args);
        println(ret);
    }

    /**
     * Pass an object (string or double) and a unit through which we want to interpret the
     * object as a numeric value. If the object is a number or a string parseable as a number,
     * return its double value. If the object is a string and it contains a unit after a number,
     * parse the unit and ensure it's compatible with the one we ask for. If so, return the
     * numeric value after converting if necessary. In any other case, return null.
     *  
     * @param value
     * @param defaultUnit
     * @return
     */
    def parseWithUnit(value, defaultUnit) {

        if (value instanceof Number) {
            return ((Number)value).doubleValue();
        }
        Pair<Double, String> pu = NumberUtils.separateUnit(value);
        if (!Double.isNaN(pu.getFirst()) && !pu.getSecond().isEmpty()) {
//            pu.setFirst(Units.INSTANCE.convert(pu.getFirst(), pu.getSecond(), defaultUnit));
        }
        return pu.getFirst();
    }

//    /**
//     * Mix together concepts ensuring that one and only one is an observable.
//     * 
//     * @param concepts
//     * @return
//     */
//    def c(Concept... concepts) {
//
//        List<Concept> traits = new ArrayList<>();
//        List<Concept> roles = new ArrayList<>();
//        Concept observable;
//
//        for (Concept c : concepts) {
//            if (NS.isTrait(c.concept)) {
//                traits.add(c);
//            } else if (NS.isObservable(c.concept) || NS.isConfiguration(c.concept)) {
//                if (observable != null) {
//                    throw new KlabRuntimeException(c.concept + ": cannot combine more than one observable");
//                }
//                observable = c;
//            } else if (NS.isRole(c.concept)) {
//                roles.add(c);
//            } else {
//                throw new KlabRuntimeException(c.concept + ": only observables, configurations, traits and roles admitted in a c() expression");
//            }
//        }
//
//        if (observable == null) {
//            throw new KlabRuntimeException("a c() expression must have one observable");
//        }
//
//        return new Concept(observable, (traits.empty ? null : traits), (roles.empty ? null : roles), binding);
//
//    }

//    /*
//     * semantic operators
//     */
//
//    def valueOf(Concept c) {
//        return new Concept(Observables.makeValue(c.concept, null), binding);
//    }
//
//    def valueOf(Concept c1, Concept c2) {
//        return new Concept(Observables.makeValue(c1.concept, c2.concept), binding);
//    }
//
//    def assessmentOf(Concept c) {
//        return new Concept(Observables.makeAssessment(c.concept), binding);
//    }
//
//    def presenceOf(Concept c) {
//        return new Concept(Observables.makePresence(c.concept), binding);
//    }
//
//    def distanceTo(Concept c) {
//        return new Concept(Observables.makeDistance(c.concept), binding);
//    }
//
//    def distanceFrom(Concept c) {
//        return distanceTo(c);
//    }
//
//    def proportionOf(Concept c) {
//        return new Concept(Observables.makeProportion(c.concept, null), binding);
//    }
//
//    def proportionOf(Concept c1, Concept c2) {
//        return new Concept(Observables.makeProportion(c1.concept, c2.concept), binding);
//    }
//
//    def occurrenceOf(Concept c) {
//        return new Concept(Observables.makeOccurrence(c.concept), binding);
//    }
//
//    def uncertaintyOf(Concept c) {
//        return new Concept(Observables.makeUncertainty(c.concept), binding);
//    }
//
//    def probabilityOf(Concept c) {
//        return new Concept(Observables.makeProbability(c.concept), binding);
//    }
//
//    def countOf(Concept c) {
//        return new Concept(Observables.makeCount(c.concept), binding);
//    }
//
//    def ratioOf(Concept c1, Concept c2) {
//        return new Concept(Observables.makeRatio(c1.concept, c2.concept), binding);
//    }

    static def _wrapIfNecessary(Object obj, Binding binding) {
//        if (obj instanceof Observation) {
//            return obj;
//        }
//        if (obj instanceof IRelationship) {
//            obj = new Relationship(obj, binding);
//        } else if (obj instanceof IDirectObservation) {
//            obj = new DirectObservation(obj, binding);
//        } else if (obj instanceof IState) {
//            obj = new State((IState)obj, binding);
//        }

        return obj;
    }

}
