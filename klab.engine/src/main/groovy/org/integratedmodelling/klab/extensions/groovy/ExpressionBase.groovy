package org.integratedmodelling.klab.extensions.groovy

import org.apache.commons.math3.special.Erf
import org.codehaus.groovy.runtime.NullObject
import org.integratedmodelling.klab.Concepts
import org.integratedmodelling.klab.Extensions
import org.integratedmodelling.klab.Reasoner
import org.integratedmodelling.klab.api.knowledge.IConcept
import org.integratedmodelling.klab.api.knowledge.ISemantic
import org.integratedmodelling.klab.api.observations.IDirectObservation
import org.integratedmodelling.klab.api.observations.IEvent
import org.integratedmodelling.klab.api.observations.IObservation
import org.integratedmodelling.klab.api.observations.IProcess
import org.integratedmodelling.klab.api.observations.IRelationship
import org.integratedmodelling.klab.api.observations.IState
import org.integratedmodelling.klab.api.observations.ISubject
import org.integratedmodelling.klab.api.provenance.IArtifact
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor
import org.integratedmodelling.klab.contrib.math.ExponentialIntegrals
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope
import org.integratedmodelling.klab.exceptions.KlabException
import org.integratedmodelling.klab.extensions.groovy.model.Concept
import org.integratedmodelling.klab.extensions.groovy.model.DirectObservation
import org.integratedmodelling.klab.extensions.groovy.model.Relationship
import org.integratedmodelling.klab.extensions.groovy.model.Scale
import org.integratedmodelling.klab.extensions.groovy.model.Space
import org.integratedmodelling.klab.extensions.groovy.model.State
import org.integratedmodelling.klab.extensions.groovy.model.Time
import org.integratedmodelling.klab.utils.NumberUtils
import org.integratedmodelling.klab.utils.Pair
import org.integratedmodelling.klab.utils.Utils

/**
 * The base class for any k.LAB action or script.
 * 
 * @author Ferd
 *
 */
abstract class ExpressionBase extends Script {

    static inited = false;
    private Random rgen = new Random();

    ExpressionBase() {
        if (!inited) {

            ExpandoMetaClass.enableGlobally();

            /*
             * enable <n>.<unit> notation to return a number with units.
             */
            //            Number.metaClass.getProperty = { String symbol ->
            //                Amount.valueOf(delegate, Unit.valueOf(symbol))
            //            }

            // Allows sensible math results when the first op is null. Ensure that all
            // input null numbers are actually NaN and no more null-proofing should be
            // required.
            NullObject.metaClass.multiply = { Object n -> Double.NaN }
            NullObject.metaClass.div = { Object n -> Double.NaN }
            NullObject.metaClass.plus = { Object n -> Double.NaN }
            NullObject.metaClass.minus = { Object n -> Double.NaN }

            // allow 'is' operator to return on categories with nodata
            NullObject.metaClass.leftShift = { Object n -> null }
            NullObject.metaClass.rightShift = { Object n -> null }
            
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
            //            Amount.metaClass.multiply = { Number factor -> delegate.times(factor) }
            //            Number.metaClass.multiply = { Amount amount -> amount.times(delegate) }
            //            Number.metaClass.div = { Amount amount -> amount.inverse().times(delegate) }
            //            Amount.metaClass.div = { Number factor -> delegate.divide(factor) }
            //            Amount.metaClass.div = { Amount factor -> delegate.divide(factor) }
            //            Amount.metaClass.power = { Number factor -> delegate.pow(factor) }
            //            Amount.metaClass.negative = { -> delegate.opposite() }
        }
    }

    ExpressionBase(Binding binding) {
        
        super(binding)
        ExpandoMetaClass.enableGlobally();

        /*
         * enable <n>.<unit> notation to return a number with units.
         */
        //            Number.metaClass.getProperty = { String symbol ->
        //                Amount.valueOf(delegate, Unit.valueOf(symbol))
        //            }

        // Allows sensible math results when the first op is null. Ensure that all
        // input null numbers are actually NaN and no more null-proofing should be
        // required.
        NullObject.metaClass.multiply = { Object n -> Double.NaN }
        NullObject.metaClass.div = { Object n -> Double.NaN }
        NullObject.metaClass.plus = { Object n -> Double.NaN }
        NullObject.metaClass.minus = { Object n -> Double.NaN }
        // without this, null concepts are everything
        NullObject.metaClass.leftShift = { Object n -> null }
        NullObject.metaClass.rightShift = { Object n -> null }

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

    def _recontextualize(String targetId, String contextId) {
        Extensions.INSTANCE.recontextualizeIdentifier(targetId, contextId, binding.getVariable("_c"), binding.getVariable("_exp"), binding.variables)
    }

    /**
     * Exponential integral
     * @param d
     * @return
     */
    def exponentialIntegral(double d) {
        ExponentialIntegrals.exponentialIntegralEi(d);
    }
	
	/**
	 * Exponential integral
	 * @param d
	 * @return
	 */
	def enx(double d) {
		ExponentialIntegrals.enx(d);
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

    def info(String text) {
        Object o = binding.getVariable("_monitor");
        if (o instanceof IMonitor) {
            ((IMonitor)o).info(text);
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

}
