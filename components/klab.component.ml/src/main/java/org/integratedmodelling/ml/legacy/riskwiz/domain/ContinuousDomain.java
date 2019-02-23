/**
 * ContinuousDomain.java
 * ----------------------------------------------------------------------------------
 * 
 * Copyright (C) 2008 www.integratedmodelling.org
 * Created: Sep 15, 2008
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
 * @copyright 2008 www.integratedmodelling.org
 * @author    Sergey Krivov
 * @date      Sep 15, 2008
 * @license   http://www.gnu.org/licenses/gpl.txt GNU General Public License v3
 * @link      http://www.integratedmodelling.org
 **/

package org.integratedmodelling.ml.legacy.riskwiz.domain;




/**
 * @author Sergey Krivov
 *
 */
public class ContinuousDomain extends Domain {
	
    private double max = 0;
    private double min = 0;
	
    private int discretizationOrder = -1;

    /**
     * @param name
     * @param order
     */
    public ContinuousDomain(String name) {
        super(name);
        // TODO Auto-generated constructor stub
    }
	
    public ContinuousDomain(String name, ContinuousType type) {
        super(name);
        super.type = type;
		
    }

    /**
     * @param name
     * @deprecated
     */
    @Deprecated
	public ContinuousDomain(String name, double min, double max, int dorder) {
        super(name);
        super.type = new ContinuousType(name, min, max);
        this.discretizationOrder = dorder;
    }
	
    public int getDiscretizationOrder() {
        return discretizationOrder;
    }

    public void setDiscretizationOrder(int discretizationOrder) {
        this.discretizationOrder = discretizationOrder;
    }

    // these are wrong methods to call with ContinuousDomain
    // public Vector<String> getStates() {
    //
    // return null;
    // }
    //
    // public String getState(int i){
    // return "contDomState";
    // }
    //
    // public int findState(String val)
    // {	 
    // return -1;
    // }





    public double getMin() {
        return min;
    }

    public void setMin(double lowerBound) {
        this.min = lowerBound;
    }
	
    public double getMax() {
        return max;
    }

    public void setMax(double upperBound) {
        this.max = upperBound;
    }
}
