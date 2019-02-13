/**
 * LikelihoodWeighting.java
 * ----------------------------------------------------------------------------------
 * 
 * Copyright (C) 2009 www.integratedmodelling.org
 * Created: Apr 28, 2009
 *
 * ----------------------------------------------------------------------------------
 * This file is part of riskwiz-cvars.
 * 
 * riskwiz-cvars is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 * 
 * riskwiz-cvars is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with the software; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 * 
 * ----------------------------------------------------------------------------------
 * 
 * @copyright 2009 www.integratedmodelling.org
 * @author    Sergey Krivov
 * @date      Apr 28, 2009
 * @license   http://www.gnu.org/licenses/gpl.txt GNU General Public License v3
 * @link      http://www.integratedmodelling.org
 **/

package org.integratedmodelling.ml.legacy.riskwiz.stochastic;


import java.util.List;
import java.util.Vector;

import org.integratedmodelling.ml.legacy.riskwiz.bn.BNNode;
import org.integratedmodelling.ml.legacy.riskwiz.bn.BeliefNetwork;
import org.integratedmodelling.ml.legacy.riskwiz.discretizer.DomainDiscretizer;
import org.integratedmodelling.ml.legacy.riskwiz.domain.DiscreteDomain;
import org.integratedmodelling.ml.legacy.riskwiz.domain.DomainFactory;
import org.integratedmodelling.ml.legacy.riskwiz.domain.IntervalDomain;
import org.integratedmodelling.ml.legacy.riskwiz.pfunction.ICondProbDistrib;
import org.integratedmodelling.ml.legacy.riskwiz.pfunction.TabularCPD;
import org.integratedmodelling.ml.legacy.riskwiz.pfunction.TabularFunction;
import org.integratedmodelling.ml.legacy.riskwiz.pt.PT;
import org.nfunk.jep.ParseException;


/**
 * @author Sergey Krivov
 * 
 */
public class LikelihoodWeightingSampler extends AbstractSampler {

    protected int runs;
    protected int precisionFactor = 1000;
    protected Vector<BNNode> orderedNodes;

    /**
     * 
     */
    public LikelihoodWeightingSampler(BeliefNetwork bn) {
        super(bn);
        orderedNodes = AbstractSampler.topologicalOrder(bn);
        createOrderedParents(bn);
        // this is temporary
        setStatespeceSize();
        ;
        runs = precisionFactor * statespaceSize;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.integratedmodelling.riskwiz.inference.IInference#run()
     */
    @Override
	public void run() {
        try {
            DomainDiscretizer.discretize(bn);
            initSamplesCounters();

            for (int i = 0; i < runs; i++) {
                sampleBN();
            }
            updateMarginals();
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void sampleBN() throws ParseException {
        double weight = 1.0;

        for (int i = 0; i < orderedNodes.size(); i++) {
            BNNode node = orderedNodes.elementAt(i);

            if (!node.hasEvidence()) {
                Object aSample = sampleNode(node);
                int iSample = mapToDiscreteDomain(node, aSample);

                node.setCurrentSample(aSample);
                node.setDiscretizedSample(iSample);
            } else {
                double w = getWeight(node);

                if (w == 0) {
                    return;
                } else {
                    weight *= w;
                }

            }

        }

        for (int i = 0; i < orderedNodes.size(); i++) {
            BNNode node = orderedNodes.elementAt(i);

            if (!node.hasEvidence()) {
                int iNodeSample = node.getDiscretizedSample();

                node.getSamplesCounter()[iNodeSample] += weight;
            }

        }

    }

    private Object sampleNode(BNNode node) throws ParseException {
        List args;

        if (node.getFunction() instanceof TabularFunction) {
            args = getDiscreteArguments(node);
        } else {
            args = getArguments(node);
        }

        if (node.isProbabilistic()) {
            return ((ICondProbDistrib) node.getFunction()).sampleVal(args);
        } else if (node.isDeterministic()) {
            return node.getFunction().getValue(args);

        } else {
            return null;
        }
    }

    private double getWeight(BNNode node) throws ParseException {
        List args;

        if (node.getFunction() instanceof TabularFunction) {
            args = getDiscreteArguments(node);
            TabularFunction func = (TabularFunction) node.getFunction();

            if (node.isProbabilistic()) {
                return ((TabularCPD) func).getProb(args, getEvidence(node));
            } else if (node.isDeterministic()) {
				 
                Object val = func.getValue(args);
                int valIndex = ((DiscreteDomain) func.getDomain()).findState(
                        (String) val);

                if (valIndex == getEvidence(node)) {
                    return 1;
                } else {
                    return 0;
                }
            } else {
                return 0;
            }

        } else {
            args = getArguments(node);
            if (node.isProbabilistic()) {
                ICondProbDistrib func = (ICondProbDistrib) node.getFunction();
                int evidence = getEvidence(node);
                double probDensity = func.getProb(args,
                        getEvidenceAvarage(node, evidence));

                return probDensity * getEvidenceWidth(node, evidence);

            } else if (node.isDeterministic()) {
                Object val = node.getFunction().getValue(args);
                int valIndex = node.getDiscretizedDomain().findState(
                        (String) val);

                if (valIndex == getEvidence(node)) {
                    return 1;
                } else {
                    return 0;
                }

            } else {
                return 0;
            }
        }  
		 
    }

    private void updateMarginals() {
        for (int i = 0; i < orderedNodes.size(); i++) {
            BNNode node = orderedNodes.elementAt(i);

            if (!node.hasEvidence()) {
                PT marginal = new PT(
                        DomainFactory.createDomainProduct(
                                node.getDiscretizedDomain()));
                double[] counter = node.getSamplesCounter();

                for (int j = 0; j < counter.length; j++) {
                    double val = counter[j];

                    marginal.setValue(j, val);
                }
                marginal.normalize();
                node.setMarginal(marginal);
            }
        }
    }

    public int getPrecisionFactor() {
        return precisionFactor;
    }

    public void setPrecisionFactor(int precisionFactor) {
        this.precisionFactor = precisionFactor;
    }

    // TODO how in the hell I get exact one and does it have to be exact?
    // what getProbability() really mean in this code?
    public int getEvidence(BNNode node) {
        PT pt = node.getEvidence();

        for (int i = 0; i < pt.size(); i++) {
            if (pt.getValue(i) == 1) {
                return i;
            }
        }
        return -1;
		
    }
	
    protected double getEvidenceAvarage(BNNode node, int evidence) {
        IntervalDomain idom = (IntervalDomain) node.getDiscretizedDomain();

        return idom.getAvarage(evidence);
    }
	
    protected double getEvidenceWidth(BNNode node, int evidence) {
        IntervalDomain idom = (IntervalDomain) node.getDiscretizedDomain();

        return idom.getWidth(evidence);
    }

}
