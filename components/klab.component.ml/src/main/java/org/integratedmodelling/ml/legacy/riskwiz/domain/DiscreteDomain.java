/**
 * DiscreteDomain.java
 * ----------------------------------------------------------------------------------
 * 
 * Copyright (C) 2008 www.integratedmodelling.org
 * Created: Feb 6, 2008
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
 * @date      Feb 6, 2008
 * @license   http://www.gnu.org/licenses/gpl.txt GNU General Public License v3
 * @link      http://www.integratedmodelling.org
 **/

package org.integratedmodelling.ml.legacy.riskwiz.domain;


import java.util.Vector;


/**
 * @author Sergey Krivov
 *
 */
public class DiscreteDomain extends Domain {
	
    public DiscreteDomain(String name) {
        super(name);
    }
	
    public DiscreteDomain(String name, DiscreteType type) {
        super(name, type);
    }

    /**
     * @deprecated
     */
    @Deprecated
	public DiscreteDomain(String name, int order) {
        super(name);
        this.type = new DiscreteType(name, order);
    }

    /* (non-Javadoc)
     * @see org.integratedmodelling.riskwiz.pt.Domain#getOrder()
     */
	 
    public int getOrder() {		 
        return ((DiscreteType) getType()).getOrder();
    }
	
    // public void setOrder(int order) {
    // ((DiscreteType)getType()).setOrder(order);
    // }
	
    public Vector<String> getStates() {
		 
        return ((DiscreteType) getType()).getStates();
    }
	
    public String getState(int i) {
        return ((DiscreteType) getType()).getState(i);
    }
	
    public int findState(String val) {	
        return ((DiscreteType) getType()).findState(val);
    }

}
