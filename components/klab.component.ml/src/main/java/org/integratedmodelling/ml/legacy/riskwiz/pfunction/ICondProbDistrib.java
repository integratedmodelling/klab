
package org.integratedmodelling.ml.legacy.riskwiz.pfunction;


import java.util.List;



/**
 * An interface which all user-defined conditional probability
 * distributions (CPDs) 
 * are expected to implement.
 */
public interface ICondProbDistrib {

    /**
     * For a discrete distribution, returns the conditional probability of 
     * <code>childValue</code> given the argument values <code>args</code>.  
     * For a continuous distribution, returns the conditional probability 
     * density at <code>childValue</code>.   
     */
    double getProb(List args, Object childValue) ;

    /**
     * Returns the natural log of the value returned by getProb.
     */
    double getLogProb(List args, Object childValue) ;

    /**
     * Samples a value according to this CPD given the <code> args
     * </code>.   
     */
    Object sampleVal(List args) ; 
}

