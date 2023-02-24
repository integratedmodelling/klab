package org.integratedmodelling.klab.api.collections.impl;

import java.util.Date;
import java.util.List;

import org.integratedmodelling.klab.api.data.mediation.KValueMediator;
import org.integratedmodelling.klab.api.geometry.KGeometry;
import org.integratedmodelling.klab.api.geometry.KLocator;
import org.integratedmodelling.klab.api.knowledge.KObservable;
import org.integratedmodelling.klab.api.knowledge.observation.scale.time.KTimeInstant;

public class Range implements KValueMediator {

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

	public boolean isInfinite() {
		return lowerBound == Double.NEGATIVE_INFINITY || upperBound == Double.POSITIVE_INFINITY;
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

		if (lowerBound > upperBound) {
			double s = lowerBound;
			lowerBound = upperBound;
			upperBound = s;
		}

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
		 * OK, can't do it with StreamTokenizer as the silly thing does not read
		 * scientific notation.
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
			s = s.substring(0, s.length() - 1);
		} else if (s.endsWith("]")) {
			upperExclusive = false;
			s = s.substring(0, s.length() - 1);
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
			lower = s.substring(0, s.length() - 1).trim();
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
		lowerInfinite = lowerBound == null || lowerBound == Double.NEGATIVE_INFINITY;
		if (lowerBound != null) {
			this.lowerBound = lowerBound;
		}
	}

	public void setUpperBound(Double upperBound) {
		upperInfinite = upperBound == null || upperBound == Double.POSITIVE_INFINITY;
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
		return isBounded() ? upperBound - lowerBound : Double.NaN;
	}

	public double getMidpoint() {
		return isBounded() ? (lowerBound + (upperBound - lowerBound) / 2) : Double.NaN;
	}

	@Override
	public boolean isCompatible(KValueMediator other) {
		return other instanceof Range && isBounded() && ((Range) other).isBounded();
	}

	@Override
	public Number convert(Number d, KValueMediator other) {

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
//
//	@Override
//	public Number backConvert(Number d, IValueMediator other) {
//
//		if (!isBounded()) {
//			throw new IllegalArgumentException(
//					"range " + this + " cannot convert value " + d + " to " + other + " because it is unbound");
//		}
//		if (!(other instanceof Range || ((Range) other).isBounded())) {
//			throw new IllegalArgumentException("range " + this + " cannot convert value " + d + " to " + other
//					+ " because the target is not a range or is unbound");
//		}
//
//		return ((Range) other).convert(d, this);
//	}

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
	 * This form admits Number, ITimeInstant and Date. Also admits nulls to mean
	 * infinite in the corresponding direction.
	 * 
	 * @param from
	 * @param to
	 * @return
	 */
	public static Range create(Object from, Object to) {

		boolean leftInfinite = from == null;
		boolean rightInfinite = to == null;
		double a = Double.NaN;
		double b = Double.NaN;

		if (!leftInfinite) {
			if (from instanceof Number) {
				a = ((Number) from).doubleValue();
			} else if (from instanceof KTimeInstant) {
				a = ((KTimeInstant) from).getMilliseconds();
			} else if (from instanceof Date) {
				a = ((Date) from).getTime();
			} else {
				throw new IllegalArgumentException("Cannot make a range: left limit unrecognized: " + from);
			}
		}
		if (!rightInfinite) {
			if (to instanceof Number) {
				b = ((Number) to).doubleValue();
			} else if (to instanceof KTimeInstant) {
				b = ((KTimeInstant) to).getMilliseconds();
			} else if (to instanceof Date) {
				b = ((Date) to).getTime();
			} else {
				throw new IllegalArgumentException("Cannot make a range: right limit unrecognized: " + to);
			}
		}

		return new Range(leftInfinite ? null : a, rightInfinite ? null : b, false, true);
	}

	public Range intersection(Range other) {
		int lowerCmp = Double.compare(lowerBound, other.lowerBound);
		int upperCmp = Double.compare(upperBound, other.upperBound);
		if (lowerCmp >= 0 && upperCmp <= 0) {
			return this;
		} else if (lowerCmp <= 0 && upperCmp >= 0) {
			return other;
		} else {
			double newLower = (lowerCmp >= 0) ? lowerBound : other.lowerBound;
			double newUpper = (upperCmp <= 0) ? upperBound : other.upperBound;
			return create(newLower, newUpper);
		}
	}

	public boolean overlaps(Range other) {
		return this.lowerBound <= other.upperBound && other.lowerBound <= this.upperBound;
	}

	/**
	 * Returns the minimal range that {@linkplain #encloses encloses} both this
	 * range and {@code
	 * other}. For example, the span of {@code [1..3]} and {@code (5..7)} is
	 * {@code [1..7)}.
	 *
	 * <p>
	 * <i>If</i> the input ranges are {@linkplain #isConnected connected}, the
	 * returned range can also be called their <i>union</i>. If they are not, note
	 * that the span might contain values that are not contained in either input
	 * range.
	 *
	 * <p>
	 * Like {@link #intersection(Range) intersection}, this operation is
	 * commutative, associative and idempotent. Unlike it, it is always well-defined
	 * for any two input ranges.
	 */
	public Range span(Range other) {
		int lowerCmp = Double.compare(lowerBound, other.lowerBound);
		int upperCmp = Double.compare(upperBound, other.upperBound);
		if (lowerCmp <= 0 && upperCmp >= 0) {
			return this;
		} else if (lowerCmp >= 0 && upperCmp <= 0) {
			return other;
		} else {
			double newLower = (lowerCmp <= 0) ? lowerBound : other.lowerBound;
			double newUpper = (upperCmp >= 0) ? upperBound : other.upperBound;
			return create(newLower, newUpper);
		}
	}

	/**
	 * Return a range that contains as much as possible of the span of the second
	 * argument constrained to the span of this, changing the values so that the
	 * boundaries may change with the least possible error, and keeping the span as
	 * much as possible. The output may be different from both but will never be
	 * outside this, or span larger than the argument.
	 * 
	 * @param constraint
	 * @param other
	 * @return
	 */
	public Range match(Range other) {
		return null;
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

		if (this.equals(other)) {
			return true;
		}

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

	/**
	 * Return a [0-1] double representing how much this interval excludes of the
	 * other. Will compute the missing parts on each side, normalize to the extent
	 * of the range, and add them in the output, dealing with infinity
	 * appropriately.
	 * 
	 * @param other
	 * @return
	 */
	public double exclusionOf(Range other) {

		double leftExclusion = 0;
		if (lowerBound != Double.NEGATIVE_INFINITY && lowerBound > other.lowerBound) {
			leftExclusion = other.lowerBound - lowerBound;
		}
		double rightExclusion = 0;
		if (upperBound != Double.POSITIVE_INFINITY && upperBound < other.upperBound) {
			rightExclusion = upperBound - other.upperBound;
		}

		double size = other.isBounded() ? other.getWidth() : (leftExclusion + rightExclusion);
		if (size == 0) {
			return 0;
		}

		return Math.abs(leftExclusion / size) + Math.abs(rightExclusion / size);

	}

	/**
	 * Return another range that includes the passed one and aligns with this when
	 * divided by the passed number of cells, which is expected to divide our width
	 * exactly. Also return a pair of doubles representing the amount of coverage of
	 * the left and right cells in the original range, in [0, 1) with 0,0 if they
	 * originally aligned exactly.
	 * 
	 * @param original the range to align
	 * @param nCells   the number of subdivisions in this range
	 * @return 1) a new range that includes original and aligns with the grid we
	 *         represent at the passed resolution. If original contains this on
	 *         either side, cut it to align. 2) two doubles for the left and right
	 *         percentage of original error (amount of cell covered in the original
	 *         range).
	 */
	public Pair<Range, Pair<Double, Double>> snap(Range original, long nCells) {

		if (!this.overlaps(original)) {
			return null;
		}

		double olower = original.getLowerBound() < getLowerBound() ? getLowerBound() : original.getLowerBound();
		double oupper = original.getUpperBound() > getUpperBound() ? getUpperBound() : original.getUpperBound();

		double cellWidth = getWidth() / nCells;
		double leftGap = olower - getLowerBound();
		double leftCells = (long) Math.floor(leftGap / cellWidth);
		double leftError = leftGap - (leftCells * cellWidth);
		double rightGap = getUpperBound() - oupper;
		double rightCells = (long) Math.floor(rightGap / cellWidth);
		double rightError = rightGap - (rightCells * cellWidth);

		return new Pair<>(
				create(this.getLowerBound() + (leftGap > 0 ? (leftCells * cellWidth) : 0),
						this.getUpperBound() - (rightGap > 0 ? (rightCells * cellWidth) : 0)),
				new Pair<>(leftError, rightError));
	}

	public boolean isWithin(double n) {
		boolean left = lowerExclusive ? n > lowerBound : n >= lowerBound;
		boolean right = upperExclusive ? n < upperBound : n <= upperBound;
		return left && right;
	}

	public static void main(String[] args) {

		Range cock = create(-10, 0);
		Pair<Range, Pair<Double, Double>> snapped = cock.snap(create(-8.7, -3.9), 10);

		System.out.println("FIXED RANGE: " + snapped.getFirst());
		System.out.println("ERRORS: " + snapped.getSecond());

		System.out.println("OVERLAP TRUE: " + cock.overlaps(create(1.7, 1.9)));
		System.out.println("OVERLAP FALSE: " + cock.overlaps(create(11, 19)));

		// System.out.println(Range.create("[1100000.0,7.148E7]").toString());
		// System.out.println(Range.create("[0,1]"));
		// System.out.println(Range.create("[12.33, 3222]"));
		// System.out.println(Range.create("[,1]"));
		// System.out.println(Range.create("[0,]"));
		// System.out.println(Range.create("[,]"));
	}

	public void include(double d) {

		if (lowerBound == Double.NEGATIVE_INFINITY || lowerBound > d) {
			lowerBound = d;
			lowerInfinite = false;
		}
		if (upperBound == Double.POSITIVE_INFINITY || upperBound < d) {
			upperBound = d;
			upperInfinite = false;
		}
	}

	/**
	 * A reference point in the interval, i.e. the midpoint if bounded, any boundary
	 * point that is not infinity if not, and NaN if infinite.
	 * 
	 * @return
	 */
	public double getFocalPoint() {

		return isBounded() ? getMidpoint()
				: lowerBound != Double.NEGATIVE_INFINITY ? lowerBound
						: (upperBound == Double.POSITIVE_INFINITY ? Double.NaN : upperBound);
	}

	public String getDisplayLabel() {
		if (!isBounded()) {
			if (lowerInfinite) {
				return "< " + upperBound;
			} else if (upperInfinite) {
				return "> " + lowerBound;
			}
		} else if (lowerBound == upperBound) {
			if (upperExclusive && lowerExclusive) {
				return "!= " + lowerBound;
			} else if (!upperExclusive && !lowerExclusive) {
				return "= " + lowerBound;
			}
		}
		return lowerBound + " - " + upperBound;
	}

	@Override
	public KValueMediator contextualize(KObservable observable, KGeometry scale) {
		return this;
	}

	public String getKimCode() {
		/*
		 * for now k.IM does not allow much specification in observables
		 */
		return getLowerBound() + " to " + getUpperBound();
	}

    @Override
    public Number convert(Number value, KLocator locator) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isContextual() {
        return false;
    }

}
