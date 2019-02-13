/**
 * DistTable.java
 * ----------------------------------------------------------------------------------
 * 
 * Copyright (C) 2008 www.integratedmodelling.org
 * Created: May 15, 2008
 *
 * ----------------------------------------------------------------------------------
 * This file is part of RiskWiz.
 * 
 * RiskWiz is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 * 
 * RiskWiz is distributed in the hope that it will be useful,
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
 * @copyright 2008 www.integratedmodelling.org
 * @author    Sergey Krivov
 * @date      May 15, 2008
 * @license   http://www.gnu.org/licenses/gpl.txt GNU General Public License v3
 * @link      http://www.integratedmodelling.org
 **/

package org.integratedmodelling.ml.legacy.riskwiz.learning.dtable;


import java.util.Vector;

import org.integratedmodelling.ml.legacy.riskwiz.domain.DiscreteDomain;
import org.integratedmodelling.ml.legacy.riskwiz.interpreter.RT;
import org.integratedmodelling.ml.legacy.riskwiz.pfunction.TabularCPD;
import org.integratedmodelling.ml.legacy.riskwiz.pt.CPT;
import org.integratedmodelling.ml.legacy.riskwiz.pt.TableFactory;
import org.integratedmodelling.ml.legacy.riskwiz.pt.map.PTMap;


/**
 * @author Sergey Krivov
 * 
 */
public class DistTable extends PTMap {

    private Dirichlet[] multiarray;

    private DiscreteDomain domain;

    private boolean isScalar = false;

    /**
     * @param domainProduct
     */
    public DistTable(DiscreteDomain domain, Vector<DiscreteDomain> domainProduct) {
        super(domainProduct);
        this.domain = domain;
        resetMultiarray();
        if (domainProduct.size() == 0) {
            isScalar = true;
        }
    }

    // TODO
    public DistTable(CPT cpt, int virtualSamples) {
        this(cpt.getDomain(), cpt.getParentsDomains());
        // create Dirichlet distributions corresponding to exixsting CPT
        createDistributionsFromCPT(cpt, virtualSamples);
    }
	
    public DistTable(TabularCPD cpf, int virtualSamples) {
        this((DiscreteDomain) cpf.getDomain(), cpf.getParentsDomains());		
        createDistributionsFromTabularCPD(cpf, virtualSamples);
    }

    protected void resetMultiarray() {

        super.structure = new int[super.domainProduct.size()];
        int s = 1;

        for (int i = 0; i < structure.length; i++) {
            super.structure[i] = super.domainProduct.elementAt(i).getOrder();
            s *= super.domainProduct.elementAt(i).getOrder();
        }
        multiarray = new Dirichlet[s];
        size = s;

    }

    public void setUniformDistributions() {
        for (int i = 0; i < size; i++) {
            multiarray[i] = new Dirichlet(domain.getOrder());
        }
    }
	
    public void createDistributionsFromCPT(CPT cpt, int virtualSamples) {
        if (!isScalar) {
            int[] structureIterator = this.index2addr(0);
            boolean done = false;

            while (!done) {
                double[] params = new double[domain.getOrder()];
				
                for (int domIndex = 0; domIndex < domain.getOrder(); domIndex++) {
                    params[domIndex] = (virtualSamples
                            * cpt.getValue(
                                    TableFactory.getCptReference(domIndex,
                                    structureIterator)));
					 
                }
                multiarray[addr2index(structureIterator)] = new Dirichlet(
                        domain.getOrder(), params); 
                done = this.addOne(structureIterator);
            }
        } else {
            double[] params = new double[domain.getOrder()];
			
            for (int domIndex = 0; domIndex < domain.getOrder(); domIndex++) {
                params[domIndex] = cpt.getValue(domIndex) * virtualSamples;
				 
            }
            multiarray[0] = new Dirichlet(domain.getOrder(), params); 
        }

    }

    public CPT createCPT() {
        CPT cpt = new CPT(domain, domainProduct);

        if (!isScalar) {
            int[] structureIterator = this.index2addr(0);
            boolean done = false;

            while (!done) {
                Dirichlet dist = getValue(structureIterator);

                for (int domIndex = 0; domIndex < domain.getOrder(); domIndex++) {
                    cpt.setValue(
                            TableFactory.getCptReference(domIndex,
                            structureIterator),
                            dist.getExpectedVal(domIndex));
                }
                done = this.addOne(structureIterator);
            }
        } else {
            Dirichlet dist = multiarray[0];

            for (int domIndex = 0; domIndex < domain.getOrder(); domIndex++) {
                cpt.setValue(domIndex, dist.getExpectedVal(domIndex));
            }
        }

        return cpt;
    }

    public void createDistributionsFromTabularCPD(TabularCPD cpf, int virtualSamples)  {
		
        // XJep jep = PF.getJep();
        try {
            if (!isScalar) {
                int[] structureIterator = this.index2addr(0);
                boolean done = false;

                while (!done) {
                    double[] params = new double[domain.getOrder()];
					
                    for (int domIndex = 0; domIndex < domain.getOrder(); domIndex++) {
                        Object expression = cpf.getValue(
                                TableFactory.getCptReference(domIndex,
                                structureIterator));
                        double value = RT.eval(expression);

                        params[domIndex] = virtualSamples * value;
						 
                    }
                    multiarray[addr2index(structureIterator)] = new Dirichlet(
                            domain.getOrder(), params); 
                    done = this.addOne(structureIterator);
                }
            } else {
                double[] params = new double[domain.getOrder()];
				
                for (int domIndex = 0; domIndex < domain.getOrder(); domIndex++) {
                    Object expression = cpf.getValue(domIndex);
                    double value = RT.eval(expression);

                    params[domIndex] = value * virtualSamples;
					 
                }
                multiarray[0] = new Dirichlet(domain.getOrder(), params); 
            }
        } catch (NumberFormatException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }  

    }

    public TabularCPD createCPF() {
        Vector<DiscreteDomain> pDomains = new Vector<DiscreteDomain>();

        pDomains.addAll(super.domainProduct);
        TabularCPD cpf = new TabularCPD(domain, pDomains);

        if (!isScalar) {
            int[] structureIterator = this.index2addr(0);
            boolean done = false;

            while (!done) {
                Dirichlet dist = getValue(structureIterator);

                for (int domIndex = 0; domIndex < domain.getOrder(); domIndex++) {
                    cpf.setValue(
                            TableFactory.getCptReference(domIndex,
                            structureIterator),
                            dist.getExpectedVal(domIndex));
                }
                done = this.addOne(structureIterator);
            }
        } else {
            Dirichlet dist = multiarray[0];

            for (int domIndex = 0; domIndex < domain.getOrder(); domIndex++) {
                cpf.setValue(domIndex, dist.getExpectedVal(domIndex));
            }
        }

        return cpf;
    }

    // this should be a bit faster....no it is wrong
    // TODO check if this works
    // public CPT createCPTfast() {
    // CPT cpt = new CPT(domain, domainProduct);
    // if (!isScalar) {
    // int[] structureIterator = this.index2addr(0);
    // boolean done = false;
    // while (!done) {
    //
    // Dirichlet dist = getValue(structureIterator);
    // int index = cpt
    // .addr2index(TableFactory.getCptReference(0, structureIterator));
    // int step = cpt.step(0);
    // for (int domIndex = 0; domIndex < domain.getOrder(); domIndex++) {
    // cpt.setValue(index, dist.getExpectedVal(domIndex));
    // index += step;
    // }
    //
    // done = this.addOne(structureIterator);
    // }
    // } else {
    // Dirichlet dist = multiarray[0];
    // for (int domIndex = 0; domIndex < domain.getOrder(); domIndex++) {
    // cpt.setValue(domIndex, dist.getExpectedVal(domIndex));
    // }
    // }
    //
    // return cpt;
    // }
	
    // public IFunction createCPFfast() {
    // CPF cpf = new CPF(domain, domainProduct);
    // if (!isScalar) {
    // int[] structureIterator = this.index2addr(0);
    // boolean done = false;
    // while (!done) {
    //
    // Dirichlet dist = getValue(structureIterator);
    // int index = cpf
    // .addr2index(TableFactory.getCptReference(0, structureIterator));
    // int step = cpf.step(0);
    // for (int domIndex = 0; domIndex < domain.getOrder(); domIndex++) {
    // cpf.setValue(index, dist.getExpectedVal(domIndex));
    // index += step;
    // }
    //
    // done = this.addOne(structureIterator);
    // }
    // } else {
    // Dirichlet dist = multiarray[0];
    // for (int domIndex = 0; domIndex < domain.getOrder(); domIndex++) {
    // cpf.setValue(domIndex, dist.getExpectedVal(domIndex));
    // }
    // }
    //
    // return cpf;
    // }

	

    public Dirichlet getValue(int[] query) {

        return multiarray[addr2index(query)];

    }

    public Dirichlet getValue(int index) {

        return multiarray[index];

    }

    public Dirichlet getValue1(int[] cptquery) {
        int index = 0;

        for (int i = 1; i < cptquery.length; i++) {
            index *= structure[i - 1];
            index += cptquery[i];
        }

        return multiarray[index];

    }

}
