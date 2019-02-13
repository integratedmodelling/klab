/**
 * PTMap.java
 * ----------------------------------------------------------------------------------
 * 
 * Copyright (C) 2008 www.integratedmodelling.org
 * Created: Feb 14, 2008
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
 * @date      Feb 14, 2008
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
public class PTMap {

    protected int[] structure;

    protected int size;

    protected Vector<DiscreteDomain> domainProduct;
	
    public PTMap() {}

    /**
     * 
     */
    public PTMap(Vector<DiscreteDomain> domainProduct) {
        this.domainProduct = domainProduct;
        resetSize();
    }
	
    protected void resetSize() {
        structure = new int[domainProduct.size()];
        int s = 1;

        for (int i = 0; i < structure.length; i++) {
            structure[i] = domainProduct.elementAt(i).getOrder();
            s *= domainProduct.elementAt(i).getOrder();
        }

        size = s;
    }

    public int[] getStructure() {
        return structure;
    }

    public int addr2index(int[] addr) {
        int index = 0;

        for (int i = 0; i < addr.length; i++) {
            index *= structure[i];
            index += addr[i];
        }
        return index;
    }

    /*
     * ! Convert between a real address and a logical address [Div-modul] [very
     * expensive] \param[in] realaddr the absolute/real address \return the
     * logical address
     */
    public int[] index2addr(int index) {
        int[] addr = new int[domainProduct.size()];
        int run = index;

        for (int i = domainProduct.size() - 1; i >= 0; i--) {
            addr[i] = run % structure[i];
            run = (run - addr[i]) / structure[i];
        }
        return addr;
    }

    /*
     * ! Add one to an addr isomorphic to addOne(q) => index(addr2index(q)+1),
     * but FASTER \param[in,out] addr the address
     */
    public boolean addOne(int addr[]) {
        for (int k = addr.length - 1; k >= 0; k--) {
            addr[k]++;
            if (addr[k] >= structure[k]) {
                addr[k] = 0;
                if (k == 0) {
                    return true;
                }
            } else {
                return false;
            }
        }
        return false;
    }

    public int size() {
        return size;
    }

}
