/**
 * TabularCPD.java
 * ----------------------------------------------------------------------------------
 * 
 * Copyright (C) 2009 www.integratedmodelling.org
 * Created: Apr 30, 2009
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
 * @date      Apr 30, 2009
 * @license   http://www.gnu.org/licenses/gpl.txt GNU General Public License v3
 * @link      http://www.integratedmodelling.org
 **/

package org.integratedmodelling.ml.legacy.riskwiz.pfunction;


import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.integratedmodelling.ml.legacy.riskwiz.Util;
import org.integratedmodelling.ml.legacy.riskwiz.domain.DiscreteDomain;


/**
 * @author Sergey Krivov
 *
 */
public class TabularCPD extends TabularFunction implements ICondProbDistrib {

    /**
     * 
     */
    public TabularCPD() {// TODO Auto-generated constructor stub
    }

    /**
     * @param domain
     * @param parentDomains
     */
    public TabularCPD(DiscreteDomain domain,
            Vector<DiscreteDomain> parentDomains) {
        super(domain, parentDomains);
        // TODO Auto-generated constructor stub
    }
	
    // public TabularCPD(DiscreteDomain domain,
    // Vector<DiscreteDomain> parentDomains) {
    // super(domain, parentDomains);
    // // TODO Auto-generated constructor stub
    // }

    /* (non-Javadoc)
     * @see org.integratedmodelling.riskwiz.pfunction.ICondProbDistrib#getLogProb(java.util.List, java.lang.Object)
     */
    @Override
	public double getLogProb(List args, Object childValue)  {
        return Math.log(getProb(args, childValue));
    }

    /* (non-Javadoc)
     * @see org.integratedmodelling.riskwiz.pfunction.ICondProbDistrib#getProb(java.util.List, java.lang.Object)
     */
    @Override
	public double getProb(List args, Object value)  {
        if (getDomainProduct().size() != args.size() + 1
                || !(value instanceof Integer)) {
            throw new IllegalArgumentException(
                    "TabulaCDP called with wrong number of arguments or wrong argument types. node:"
                            + this.getDomain().getName());
        } 
		
        int[] query = new int[args.size() + 1];

        query[0] = (Integer) value; 
        int i = 1;

        for (Iterator iterator = args.iterator(); iterator.hasNext();) {
            Integer v = (Integer) iterator.next();

            query[i] = v;
            i++;
			
        }
        double prob = -1;
		
        prob = (Double) getValue(query);
		 
        return prob;
    }

    /* (non-Javadoc)
     * @see org.integratedmodelling.riskwiz.pfunction.ICondProbDistrib#sampleVal(java.util.List)
     */
    @Override
	public Object sampleVal(List args)  {
		
        int[] query = new int[args.size() + 1];
        int i = 1;

        for (Iterator iterator = args.iterator(); iterator.hasNext();) {
            Integer v = (Integer) iterator.next();

            query[i] = v;
            i++;
			
        }
        double[] probs = new double[((DiscreteDomain) getDomain()).getOrder()];
		 
        for (int j = 0; j < probs.length; j++) {
            query[0] = j;
            probs[j] = (Double) getValue(query);
        }
		 
        return   Util.sampleWithProbs(probs); 
    }
	
}
