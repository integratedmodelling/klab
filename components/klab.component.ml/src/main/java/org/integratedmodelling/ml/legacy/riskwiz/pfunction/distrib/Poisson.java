/*
 * Copyright (c) 2005, Regents of the University of California
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * * Redistributions of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in
 *   the documentation and/or other materials provided with the
 *   distribution.  
 *
 * * Neither the name of the University of California, Berkeley nor
 *   the names of its contributors may be used to endorse or promote
 *   products derived from this software without specific prior 
 *   written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS
 * FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE
 * COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.integratedmodelling.ml.legacy.riskwiz.pfunction.distrib;


import java.io.Serializable;
import java.util.List;

import org.integratedmodelling.ml.legacy.riskwiz.Util;
import org.integratedmodelling.ml.legacy.riskwiz.pfunction.JavaCondProbDistrib;


/**
 * A Poisson distribution with mean and variance lambda.  This is a 
 * distribution over non-negative integers.  The probability of n is 
 * exp(-lambda) lambda^n / n!.  
 *
 * This is a slightly modified version of Poisson.java in the common directory,
 * tailored to implement the CondProbDistrib interface.
 */
public class Poisson extends JavaCondProbDistrib implements Serializable {

    /**
     * Creates a new Poisson distribution with the specifies lambda parameter.
     */
    public Poisson(List params) {
        if (!(params.get(0) instanceof Number)) {
            throw new IllegalArgumentException(
                    "The first parameter to Poisson " 
                            + "distribution must be of class Number, " + "not "
                            + params.get(0).getClass() + ".");
        }

        lambda = ((Number) params.get(0)).doubleValue();
    }

    /**
     * Returns the probability of the integer n under this distribution.   
     */
    @Override
	public double getProb(List args, Object value) {
        // Work in log domain to avoid overflow for large values of n
        return Math.exp(getLogProb(args, value)); 
    }

    /**
     * Returns the log probability of the integer n under this distribution.
     */
    @Override
	public double getLogProb(List args, Object value) {
        int n = ((Number) value).intValue(); 

        return (-lambda + (n * Math.log(lambda)) - Util.logFactorial(n));
    }

    /**
     * Returns an integer sampled according to this distribution.  This 
     * implementation takes time proportional to the magnitude of the integer 
     * returned.  I got the algorithm from Anuj Kumar's course page for 
     * IEOR E4404 at Columbia University, specifically the file:
     * <blockquote>
     * http://www.columbia.edu/~ak2108/ta/summer2003/poisson1.c
     * </blockquote>
     */
    @Override
	public Object sampleVal(List args) {
        int n = 0;
        double probOfN = Math.exp(-lambda); // start with prob of 0
        double cumProb = probOfN;

        double u = Util.random();

        while (cumProb < u) {
            n++;
            // ratio between P(n) and P(n-1) is lambda / n
            probOfN *= (lambda / n);
            cumProb += probOfN;
        }

        return new Integer(n);
    }

    @Override
	public String toString() {
        return getClass().getName();
    }

    private double lambda;
}
