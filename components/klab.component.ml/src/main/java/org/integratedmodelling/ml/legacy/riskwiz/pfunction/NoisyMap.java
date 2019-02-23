/**
 * NoisyMap.java
 * ----------------------------------------------------------------------------------
 * 
 * Copyright (C) 2009 www.integratedmodelling.org
 * Created: Mar 4, 2009
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
 * @date      Mar 4, 2009
 * @license   http://www.gnu.org/licenses/gpl.txt GNU General Public License v3
 * @link      http://www.integratedmodelling.org
 **/

package org.integratedmodelling.ml.legacy.riskwiz.pfunction;


import java.util.Vector;

import org.integratedmodelling.ml.legacy.riskwiz.domain.DiscreteDomain;
import org.integratedmodelling.ml.legacy.riskwiz.pt.map.PTMap;


/**
 * @author Sergey Krivov
 *
 */
public class NoisyMap extends PTMap {

    /**
     * 
     */
    public NoisyMap() {// TODO Auto-generated constructor stub
    }

    /**
     * @param domainProduct
     */
    public NoisyMap(Vector< DiscreteDomain> domainProduct) {
        super(domainProduct);
        // TODO Auto-generated constructor stub
    }
	
    public int[] getCPTAddress(int domIndex, int[] parentAddress) {
        int[] address = new int[parentAddress.length + 1];
		
        address[0] = domIndex;
        for (int i = 0; i < parentAddress.length; i++) {
            address[i + 1] = parentAddress[i];
        }
		
        return address;
    }
	
    public int[] getLast() {
        return index2addr(this.size - 1);
    }

}
