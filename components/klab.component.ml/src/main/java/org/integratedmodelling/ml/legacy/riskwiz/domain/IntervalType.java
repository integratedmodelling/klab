/**
 * IntervalType.java
 * ----------------------------------------------------------------------------------
 * 
 * Copyright (C) 2009 www.integratedmodelling.org
 * Created: May 13, 2009
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
 * @date      May 13, 2009
 * @license   http://www.gnu.org/licenses/gpl.txt GNU General Public License v3
 * @link      http://www.integratedmodelling.org
 **/

package org.integratedmodelling.ml.legacy.riskwiz.domain;


import java.util.Vector;
 

/**
 * @author Sergey Krivov
 *
 */
public class IntervalType extends DiscreteType {
	
    private Vector<Interval> states;
    private double min;
    private double max;
    private boolean evenPartition = true;

    /**
     * 
     */
    public IntervalType(String name, int numberOfIntervals) {
        super(name, numberOfIntervals);
    }
	
    /**
     * @param name
     * @param vals
     *  
     */
    public IntervalType(String name, double from, double to, int numberOfIntervals) {
        super(name, numberOfIntervals);
        min = from;
        max = to;
        order = numberOfIntervals;
        evenPartition = true;
        generateIntervals(from, to, numberOfIntervals);
		 
    }
	
    public IntervalType(String name, Vector<Double> ints) {
        super(name);
        min = ints.firstElement();
        max = ints.lastElement();
        order = ints.size() - 1;
        evenPartition = false;
        generateIntervals(ints);
		 
    }
	
    public void generateIntervals(double from, double to, int numberOfIntervals) {
        states = new Vector<Interval>();
        double temp = from;
        double delta = (to - from) / numberOfIntervals;

        for (int i = 0; i < numberOfIntervals; i++) {
            states.add(new Interval(temp, temp + delta));
            temp += delta;
        }
    }
	
    public void generateIntervals(Vector<Double> ints) {
        states = new Vector<Interval>();
		  
        for (int i = 0; i < ints.size() - 1; i++) {
            states.add(new Interval(ints.elementAt(i), ints.elementAt(i + 1)));
			
        }
    }
	
    public Vector<Interval> getIntervalStates() {
        return states;
    }
	
    @Override
	public String getState(int i) {
        return states.elementAt(i).toString();
    }
	
    public int getStateIndex(double val) {		 
		 
        for (int i = 0; i < states.size(); i++) {
            Interval intl = states.elementAt(i);

            if (intl.from <= val && intl.to >= val) {
                return i;
            }
			
        }
        return -1;
    }
	
    public double getUpperBoundary(int i) {
        return states.elementAt(i).to;
    }
	
    public double getLowerBoundary(int i) {
        return states.elementAt(i).from;
    }
	
    public double getAvarage(int i) {
        Interval intl = states.elementAt(i);

        return (intl.to + intl.from) / 2;
    }
	
    public double getWidth(int i) {
        Interval intl = states.elementAt(i);

        return (intl.to - intl.from);
    }
	
    @Override
	public int findState(String val) {
        return getStates().indexOf(val);
    }
	
    @Override
	public Vector<String> getStates() {
        Vector<String> sts = new Vector<String>();		 

        for (Interval interval : states) {
            sts.add(interval.toString());
        } 
        return sts;
    }
	
    public Vector<String> getStates(String format) {
        Vector<String> sts = new Vector<String>();		 

        for (Interval interval : states) {
            sts.add(interval.toString(format));
        } 
        return sts;
    }

    public void setStates(Vector<Interval> states) {
        this.states = states;
    }
	
    public Vector<Double> getStateBorders() {
        Vector<Double>  borders = new Vector<Double>();

        for (Interval interval : states) {
            borders.add(interval.from);
        } 
        borders.add(states.lastElement().to);
		
        return borders;
    }

    public double getMax() {
        return max;
    }

    public double getMin() {
        return min;
    }
	
    public boolean hasEverPartition() {
        return evenPartition;
    }

}
