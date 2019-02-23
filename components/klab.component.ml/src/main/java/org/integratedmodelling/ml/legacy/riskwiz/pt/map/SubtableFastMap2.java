/**
 * SubtableFastMap2.java
 * ----------------------------------------------------------------------------------
 * 
 * Copyright (C) 2009 www.integratedmodelling.org
 * Created: Aug 24, 2009
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
 * @date      Aug 24, 2009
 * @license   http://www.gnu.org/licenses/gpl.txt GNU General Public License v3
 * @link      http://www.integratedmodelling.org
 **/

package org.integratedmodelling.ml.legacy.riskwiz.pt.map;


import java.util.Vector;

import org.integratedmodelling.ml.legacy.riskwiz.domain.DiscreteDomain;


/**
 * @author Sergey Krivov
 *
 */
public class SubtableFastMap2 extends DomainMap2 {
	
    private int[] indexMap2;

    /**
     * @param first
     * @param second
     */
    public SubtableFastMap2(Vector<DiscreteDomain> first, Vector<DiscreteDomain> second) {
        super(first, second);
        compile1(domainProduct, second);
    }
	
    public int[] getIndexMap2() {
        return indexMap2;
    }
	 
    public void compile(Vector<DiscreteDomain> out, Vector<DiscreteDomain> second) {
        PTMap ptMap = new PTMap(out);
		  
        PTMap ptMap2 = new PTMap(second);
		 
        indexMap2 = new int[ptMap.size()];
		
        int[] productStructureIterator = ptMap.index2addr(0);		 
        boolean done = false;

        while (!done) {
            setIndexMapValues(productStructureIterator, ptMap, ptMap2); 
            done = ptMap.addOne(productStructureIterator);
        }
		
    }
	
    private void setIndexMapValues(int[] productStructureIterator,
            PTMap ptOut, PTMap ptm2) {
		
        int index = ptOut.addr2index(productStructureIterator);
		 
        int index2 = ptm2.addr2index(
                this.getProjectionSecond(productStructureIterator));
		 
        indexMap2[index] = index2;
 
    }
	
    public void compile1(Vector<DiscreteDomain> out, Vector<DiscreteDomain> second) {
        PTMap ptMap = new PTMap(out);
		  
        PTMap ptMap2 = new PTMap(second);
		 
        indexMap2 = new int[ptMap.size()];
		
        for (int i = 0; i < indexMap2.length; i++) {
            indexMap2[i] = secondIndex(i, ptMap, ptMap2);
        }
		
    }
	
    private int secondIndex(int i,
            PTMap ptOut, PTMap ptm2) {
		
        return ptm2.addr2index(this.getProjectionSecond(ptOut.index2addr(i)));
		 
    }
	
}
