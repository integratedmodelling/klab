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
public class LabelDomain extends DiscreteDomain {

    public LabelDomain(String name, LabelType ltype) {
        super(name);
        this.type = ltype;
    }
	
    /**
     * @param name
     * @param vals
     *  
     */
    public LabelDomain(String name, String[] vals) {
        super(name);
        this.type = new LabelType(name, vals);
    }
	 
    @Override
	public int findState(String val) {
        return ((LabelType) getType()).findState(val);
    }
	
    // public void addState(String val)
    // {
    // ((LabelType)getType()).addState(val);
    // }
    //
    // public void removeState(String val)
    // {
    // ((LabelType)getType()).removeState(val);
    // }
	 
	 

    @Override
	public Vector<String> getStates() {
        return ((LabelType) getType()).getStates();
    }

    // public void setStates(Vector<String> values) {
    // ((LabelType)getType()).setStates(values);  
    // }
	
    @Override
	public String getState(int i) {
        return ((LabelType) getType()).getState(i);
    }
	
}
