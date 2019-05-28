package org.integratedmodelling.ecology.biomass.lpjguess.common;

import java.util.Collection;
import java.util.LinkedList;

/// Keeps track of historic values of some variable
/** Useful for calculating running means etc.
*
*  The class behaves like a queue with a fixed size,
*  when a new value is added, and the queue is full,
*  the oldest value is overwritten.
*/

public class Historic extends LinkedList <Double> {

	private int capacity;

	public Historic(int cap) {
		// TODO Auto-generated constructor stub
		this.capacity = cap;
	}
	
	public boolean add(double e) {
		super.add(e);
		
		while (this.size() > this.capacity) {
			this.remove(0);
		}
		return true;
	}
	
	public double mean() {
		return sum() / this.size();
	}
	
	public double sum() {
		double total = 0;
		for (double item : this) {
			total += item;
		}
		
		return total;
	}
	
	public double lastadd() {
		return this.getLast();
	}
	
	public double max() {
		double result = -9999999999.0;
		
		for (double item : this) {
			if (item > result) {
				result = item;
			}
		}
		
		return result;
	}

	public double min() {
		double result = 9999999999.0;
		
		for (double item : this) {
			if (item < result) {
				result = item;
			}
		}
		
		return result;
	}
	
	public double periodicmean(int nsteps) {
		if (nsteps >= this.size()) {
			return mean();
		} else {
			return periodicsum(nsteps) / nsteps;
		}
	}
	
	public double periodicsum(int nsteps) {
		double result = 0.0;
		
		if (nsteps >= this.size()) {
			return sum();
		} else {
			for (int i = size() - 1; i >= size()-nsteps; i--) {
				result += this.get(i);
			}
		}
		
		return result;
	}
	
	
}
