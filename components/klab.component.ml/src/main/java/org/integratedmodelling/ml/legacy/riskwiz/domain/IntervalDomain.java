/**
 * IntervalDomain.java
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
public class IntervalDomain extends DiscreteDomain {

    /**
     * @param name
     */
    public IntervalDomain(String name) {
        super(name);
        // TODO Auto-generated constructor stub
    }
	
    public IntervalDomain(String name, IntervalType type) {
        super(name);
        this.type = type;
    }

    /**
     * @param name
     * @param vals
     *  
     */
    public IntervalDomain(String name, double from, double to, int numberOfIntervals) {
        super(name);
        this.type = new IntervalType(name, from, to, numberOfIntervals);
		 
    }
	
    /**
     * @param name
     * @param ints
     *  
     */
	
    public IntervalDomain(String name, Vector<Double> ints) {
        super(name);
        this.type = new IntervalType(name, ints);
		 
    }
	
    public Vector<Interval> getIntervalStates() {
        return ((IntervalType) getType()).getIntervalStates();
    }
	
    @Override
	public String getState(int i) {
        return ((IntervalType) getType()).getState(i);
    }
	
    public int getStateIndex(double val) {		 
        return ((IntervalType) getType()).getStateIndex(val);
    }
	
    public double getUpperBoundary(int i) {
        return ((IntervalType) getType()).getUpperBoundary(i);
    }
	
    public double getLowerBoundary(int i) {
        return ((IntervalType) getType()).getLowerBoundary(i);
    }
	
    public double getAvarage(int i) {		 
        return ((IntervalType) getType()).getAvarage(i);
    }
	
    public double getWidth(int i) {
        return ((IntervalType) getType()).getWidth(i);
    }
	
    @Override
	public int findState(String val) {
        return ((IntervalType) getType()).findState(val);
    }
	
    @Override
	public Vector<String> getStates() {
        return ((IntervalType) getType()).getStates();
    }
	
    public Vector<String> getStates(String format) {
		 
        return ((IntervalType) getType()).getStates(format);
    }

    // public void setStates(Vector<Interval> states) {
    // ((IntervalType)getType()).setStates(states);
    // }
	
    public Vector<Double> getStateBorders() {
        return ((IntervalType) getType()).getStateBorders();
    }

    public double getMax() {
        return ((IntervalType) getType()).getMax();
    }

    public double getMin() {
        return ((IntervalType) getType()).getMin();
    }
	
    public boolean hasEverPartition() {
        return ((IntervalType) getType()).hasEverPartition();
    }
}
