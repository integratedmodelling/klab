package org.integratedmodelling.klab.utils;

import java.util.List;

import org.integratedmodelling.kim.api.IValueMediator;

public class Range implements IValueMediator {

	double lowerBound = Double.NEGATIVE_INFINITY;
	double upperBound = Double.POSITIVE_INFINITY;
	boolean lowerExclusive = false;
	boolean upperExclusive = false;
	boolean lowerInfinite = true;
	boolean upperInfinite = true;

	/**
	 * Create a [-Inf, +Inf] range.
	 */
	public Range() {
	}

	/**
	 * Parse a range from the string representation.
	 * 
	 * @param intvs
	 */
	public Range(String intvs) {
		parse(intvs);
	}

	public boolean isBounded() {
		return !isLeftInfinite() && !isRightInfinite();
	}

	/**
	 * Create a range.
	 * 
	 * @param left
	 * @param right
	 * @param leftExclusive
	 * @param rightExclusive
	 */
	public Range(Double left, Double right, boolean leftExclusive, boolean rightExclusive) {

		if (!(lowerInfinite = (left == null)))
			lowerBound = left;

		if (!(upperInfinite = (right == null)))
			upperBound = right;

		lowerExclusive = leftExclusive;
		upperExclusive = rightExclusive;
	}

	public Range(Range range) {
		this.lowerBound = range.lowerBound;
		this.upperBound = range.upperBound;
		this.lowerExclusive = range.lowerExclusive;
		this.upperExclusive = range.upperExclusive;
		this.lowerInfinite = range.lowerInfinite;
		this.upperInfinite = range.upperInfinite;
	}

	public boolean isLowerExclusive() {
		return lowerExclusive;
	}

	public void setLowerExclusive(boolean b) {
		this.lowerExclusive = b;
	}

	public void parse(String s) {

		/*
		 * OK, can't do it with StreamTokenizer as the silly thing does not read scientific notation.
		 */
		
		lowerInfinite = false;
		upperInfinite = false;
		
		s = s.trim();
		if (s.startsWith("(")) {
			lowerExclusive = true;
			s = s.substring(1);
		} else if (s.startsWith("[")) {
			lowerExclusive = false;
			s = s.substring(1);
		}

		if (s.endsWith(")")) {
			upperExclusive = true;
			s = s.substring(0, s.length()-1);
		} else if (s.endsWith("]")) {
			upperExclusive = false;
			s = s.substring(0, s.length()-1);
		}

		String upper = null;
		String lower = null;
		s = s.trim();
		if (s.startsWith(",")) {
			lowerInfinite = true;
			upper = s.substring(1).trim();
		}
		if (s.endsWith(",")) {
			upperInfinite = true;
			lower = s.substring(0,s.length()-1).trim();
		}
		if (!s.startsWith(",") && !s.endsWith(",")) {
			
			if (!s.contains(",")) {
				throw new IllegalArgumentException("invalid interval syntax: " + s);
			}
			
			String[] ss = s.split(",");
			lowerBound = Double.valueOf(ss[0].trim());
			upperBound = Double.valueOf(ss[1].trim());
		} else {
		
			if (upper != null && !upper.isEmpty()) {
				upperBound = Double.valueOf(upper);
			}
			if (lower != null && !lower.isEmpty()) {
				lowerBound = Double.valueOf(lower);
			}
		}
	}

	public int compare(Range i) {

		if (lowerInfinite == i.lowerInfinite && lowerExclusive == i.lowerExclusive && upperInfinite == i.upperInfinite
				&& upperExclusive == i.upperExclusive && lowerBound == i.lowerBound && upperBound == i.upperBound)
			return 0;

		if (this.upperBound <= i.lowerBound)
			return -1;

		if (this.lowerBound >= i.upperBound)
			return 1;

		throw new IllegalArgumentException("error: trying to sort overlapping numeric intervals");

	}

	public boolean isUpperOpen() {
		return upperExclusive;
	}

	public void setUpperOpen(boolean upperOpen) {
		this.upperExclusive = upperOpen;
	}

	public void setLowerBound(Double lowerBound) {
		lowerInfinite = lowerBound == null;
		if (lowerBound != null) {
			this.lowerBound = lowerBound;
		}
	}

	public void setUpperBound(Double upperBound) {
		upperInfinite = upperBound == null;
		if (upperBound != null) {
			this.upperBound = upperBound;
		}
	}

	public void setLeftInfinite(boolean leftInfinite) {
		this.lowerInfinite = leftInfinite;
		this.lowerBound = Double.NEGATIVE_INFINITY;
	}

	public void setRightInfinite(boolean rightInfinite) {
		this.upperInfinite = rightInfinite;
		this.upperBound = Double.POSITIVE_INFINITY;
	}

	public boolean isRightInfinite() {
		return upperInfinite;
	}

	public boolean isLeftInfinite() {
		return lowerInfinite;
	}

	/**
	 * true if the upper boundary is closed, i.e. includes the limit
	 * 
	 * @return true if upper boundary is closed
	 */
	public boolean isRightBounded() {
		return !upperExclusive;
	}

	/**
	 * true if the lower boundary is closed, i.e. includes the limit
	 * 
	 * @return true if lower bounday is closed
	 */
	public boolean isLeftBounded() {
		return !lowerExclusive;
	}

	public double getLowerBound() {
		return lowerBound;
	}

	public double getUpperBound() {
		return upperBound;
	}

	public boolean contains(double d) {

		if (lowerInfinite)
			return (upperExclusive ? d < upperBound : d <= upperBound);
		else if (upperInfinite)
			return (lowerExclusive ? d > lowerBound : d >= lowerBound);
		else
			return (upperExclusive ? d < upperBound : d <= upperBound)
					&& (lowerExclusive ? d > lowerBound : d >= lowerBound);
	}

	@Override
	public String toString() {

		String ret = "";

		if (!lowerInfinite) {
			ret += lowerExclusive ? "(" : "[";
			ret += lowerBound;
		}
		if (!upperInfinite) {
			if (!lowerInfinite)
				ret += " ";
			ret += upperBound;
			ret += upperExclusive ? ")" : "]";
		}

		return ret;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (lowerInfinite ? 1231 : 1237);
		long temp;
		temp = Double.doubleToLongBits(lowerBound);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + (lowerExclusive ? 1231 : 1237);
		result = prime * result + (upperInfinite ? 1231 : 1237);
		temp = Double.doubleToLongBits(upperBound);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + (upperExclusive ? 1231 : 1237);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Range other = (Range) obj;
		if (lowerInfinite != other.lowerInfinite)
			return false;
		if (Double.doubleToLongBits(lowerBound) != Double.doubleToLongBits(other.lowerBound))
			return false;
		if (lowerExclusive != other.lowerExclusive)
			return false;
		if (upperInfinite != other.upperInfinite)
			return false;
		if (Double.doubleToLongBits(upperBound) != Double.doubleToLongBits(other.upperBound))
			return false;
		if (upperExclusive != other.upperExclusive)
			return false;
		return true;
	}

	/**
	 * Record the passed value as a part of the range, adjusting boundaries as
	 * needed.
	 * 
	 * @param value
	 */
	public void adapt(double value) {

		if (Double.isNaN(value)) {
			return;
		}
		if (Double.isInfinite(lowerBound) || lowerBound > value) {
			setLowerBound(value);
		}
		if (Double.isInfinite(upperBound) || upperBound < value) {
			setUpperBound(value);
		}
	}

	/**
	 * Normalize the passed value to this range, which must include it.
	 * 
	 * @param value
	 * @return the normalized value (0-1)
	 */
	public double normalize(double value) {

		if (!isBounded()) {
			return value;
		}
		return (value - lowerBound) / (upperBound - lowerBound);
	}

	public double getWidth() {
		return upperBound - lowerBound;
	}

	public double getMidpoint() {
		return lowerBound + (upperBound - lowerBound) / 2;
	}

	@Override
	public boolean isCompatible(IValueMediator other) {
		return other instanceof Range && isBounded() && ((Range) other).isBounded();
	}

	@Override
	public Number convert(Number d, IValueMediator other) {

		if (!isBounded()) {
			throw new IllegalArgumentException(
					"range " + this + " cannot convert value " + d + " to " + other + " because it is unbound");
		}
		if (!(other instanceof Range || ((Range) other).isBounded())) {
			throw new IllegalArgumentException("range " + this + " cannot convert value " + d + " to " + other
					+ " because the target is not a range or is unbound");
		}
		if (!((Range) other).contains(d.doubleValue())) {
			throw new IllegalArgumentException("range " + other + " cannot convert value " + d + " to range " + this
					+ " because it does not contain it");
		}

		return this.lowerBound + (this.getWidth() * ((Range) other).normalize(d.doubleValue()));
	}

	/**
	 * Create a range from a list of doubles
	 * 
	 * @param range
	 * @return
	 */
	public static Range create(List<Double> range) {
		return new Range(range.get(0), range.get(1), false, false);
	}

	public static Range create(double start, double end, boolean rightOpen) {
		return new Range(start, end, false, rightOpen);
	}

	public static Range create(double start, double end) {
		return new Range(start, end, false, false);
	}

	public static Range create(String string) {
		return new Range(string);
	}

	/**
	 * Stretch one of the ends so that the passed value is the midpoint. If the
	 * midpoint isn't in the range, return self.
	 * 
	 * @param midpoint
	 * @return
	 */
	public Range stretchForMidpoint(double midpoint) {

		if (!isBounded()) {
			throw new IllegalArgumentException(
					"range " + this + " cannot be stretched for midpoint because it is unbound");
		}

		if (!contains(midpoint)) {
			return this;
		}

		double left = midpoint - getLowerBound();
		double right = getUpperBound() - midpoint;
		Range ret = new Range(this);
		if (Math.abs(left) > Math.abs(right)) {
			ret.upperBound = midpoint + Math.abs(left);
		} else if (Math.abs(right) > Math.abs(left)) {
			ret.lowerBound = midpoint - Math.abs(right);
		}

		return ret;
	}

	public boolean contains(Range other) {
		if (!lowerInfinite && !other.lowerInfinite
				&& (lowerExclusive ? lowerBound >= other.lowerBound : lowerBound > other.lowerBound)) {
			return false;
		}
		if (!upperInfinite && !other.upperInfinite
				&& (upperExclusive ? upperBound <= other.upperBound : upperBound < other.upperBound)) {
			return false;
		}
		if (!upperInfinite && other.upperInfinite) {
			return false;
		}
		if (!lowerInfinite && other.lowerInfinite) {
			return false;
		}
		return true;
	}

	public boolean isWithin(double n) {
		boolean left = lowerExclusive ? n > lowerBound : n >= lowerBound;
		boolean right = upperExclusive ? n < upperBound : n <= upperBound;
		return left && right;
	}

	public static void main(String[] args) {
		System.out.println(Range.create("[1100000.0,7.148E7]").toString());
		System.out.println(Range.create("[0,1]"));
		System.out.println(Range.create("[12.33, 3222]"));
		System.out.println(Range.create("[,1]"));
		System.out.println(Range.create("[0,]"));
		System.out.println(Range.create("[,]"));
	}
	
}
