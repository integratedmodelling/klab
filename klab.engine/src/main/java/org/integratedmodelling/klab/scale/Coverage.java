package org.integratedmodelling.klab.scale;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.integratedmodelling.klab.api.data.IGeometry.Dimension.Type;
import org.integratedmodelling.klab.api.observations.scale.IExtent;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.observations.scale.ITopologicallyComparable;
import org.integratedmodelling.klab.api.observations.scale.time.ITime;
import org.integratedmodelling.klab.api.resolution.ICoverage;
import org.integratedmodelling.klab.common.LogicalConnector;
import org.integratedmodelling.klab.utils.NumberUtils;
import org.integratedmodelling.klab.utils.Pair;

/**
 * The implementation of Coverage extends Scale and reimplements
 * {@link #merge(ITopologicallyComparable, LogicalConnector)} according to
 * coverage semantics. If the coverage is used as a Scale, it is important that
 * a {@link Scale#copy() copy} is made so that any calls to merge() will perform
 * as expected.
 * 
 * @author ferdinando.villa
 *
 */
public class Coverage extends Scale implements ICoverage {

	private static final long serialVersionUID = 7035851173441618273L;

	/*
	 * Default - do not accept a state model unless its coverage is greater than
	 * this. Instantiator models make this 0.
	 * 
	 * TODO make this configurable
	 */
	private static double MIN_MODEL_COVERAGE = 0.01;

	/*
	 * Default - we accept models if they cover at least an additional 20% of the
	 * whole context TODO make this configurable
	 */
	private static double MIN_TOTAL_COVERAGE = 0.20;

	/*
	 * Default - we stop adding models when we cover at least 95% of the whole
	 * context. TODO make this configurable
	 */
	private static double MIN_REQUIRED_COVERAGE = 0.95;

	// make local copies that may be modified and are inherited by children
	private double minModelCoverage = MIN_MODEL_COVERAGE;
	private double minTotalCoverage = MIN_TOTAL_COVERAGE;
	private double minRequiredCoverage = MIN_REQUIRED_COVERAGE;

	List<Pair<IExtent, Double>> coverages = new ArrayList<>();
	double coverage;
	double gain = 0;

	/*
	 * Keep all the (collapsed) merge history in subextents in their current
	 * situation. At each merge, all the extents are combined again, any resulting
	 * empty extents eliminated.
	 */
	Map<Dimension.Type, List<Pair<LogicalConnector, IExtent>>> merged = new HashMap<>();

	/**
	 * Create a coverage with full coverage, which can be reduced by successive AND
	 * merges.
	 * 
	 * @param original
	 * @return a full coverage for the passed scale.
	 */
	public static Coverage full(IScale original) {
		return new Coverage((Scale) original, 1.0);
	}

	protected void setTo(Coverage other) {
		extents.clear();
		extents.addAll(other.extents);
		sort();
		coverages.clear();
		for (Pair<IExtent, Double> pair : other.coverages) {
			coverages.add(new Pair<>(pair.getFirst(), pair.getSecond()));
		}
		coverage = other.coverage;
	}

	/**
	 * Create a coverage with full coverage, which can be increased by successive OR
	 * merges.
	 * 
	 * @param original
	 * @return a new empty coverage of this scale
	 */
	public static Coverage empty(IScale original) {
		return new Coverage((Scale) original, 0.0);
	}

	/**
	 * Use this when we need the IScale semantics on our same extents.
	 * 
	 * @return
	 */
	public Scale asScale() {
		return Scale.create(extents);
	}

	protected Coverage(Scale original, double initialCoverage) {
		super(original.extents);
		this.coverage = initialCoverage;
		for (IExtent extent : extents) {
			coverages.add(new Pair<>(initialCoverage > 0 ? extent : null, initialCoverage));
		}
	}

	private Coverage(Coverage original, List<Pair<IExtent, Double>> newcoverages, double gain) {
		super(original.extents);
		this.coverage = Double.NaN;
		this.gain = gain;
		for (Pair<IExtent, Double> cov : newcoverages) {
			coverages.add(new Pair<>(cov.getFirst(), cov.getSecond()));
			this.coverage = Double.isNaN(this.coverage) ? cov.getSecond() : (this.coverage * cov.getSecond());
		}
		if (Double.isNaN(this.coverage)) {
			this.coverage = 0;
		}
		assert (this.coverage >= 0 && this.coverage <= 1);
	}

	public Coverage(Coverage other) {
		this(other, other.coverages, other.gain);
	}

	public void setCoverage(double c) {
		if (!(c == 0 || c == 1)) {
			throw new IllegalArgumentException("a coverage can only be explicitly set to 0 or 1");
		}
		this.coverage = c;
		for (int i = 0; i < coverages.size(); i++) {
			Pair<IExtent, Double> cov = coverages.get(i);
			cov.setSecond(c);
			if (c == 0) {
				cov.setFirst(null);
			} else if (cov.getFirst() == null) {
				cov.setFirst(extents.get(i));
			}
		}
	}

	@Override
	public boolean isEmpty() {
		return coverage == 0;
	}

	@Override
	public double getCoverage() {
		return coverage;
	}

	@Override
	public double getCoverage(Type dimension) {
		for (Pair<IExtent, Double> cov : coverages) {
			if (cov.getFirst().getType() == dimension) {
				return cov.getSecond();
			}
		}
		throw new IllegalArgumentException("this coverage does not contain the dimension " + dimension);
	}

	@Override
	public Coverage merge(ITopologicallyComparable<?> other, LogicalConnector how) {

		if (!(other instanceof Scale)) {
			throw new IllegalArgumentException("a coverage can only merge another scale");
		}

		// no need for suffering if either is 0 and we're intersecting
		if (how == LogicalConnector.INTERSECTION
				&& ((other instanceof Coverage && NumberUtils.equal(((Coverage) other).getCoverage(), 0))
						|| NumberUtils.equal(this.getCoverage(), 0))) {
			return empty(this.asScale());
		}

		Scale coverage = (Scale) other;
		List<Pair<IExtent, Double>> newcoverages = new ArrayList<>();
		if (coverage.getExtentCount() != getExtentCount()) {
			throw new IllegalArgumentException("cannot merge a coverage with a scale with different dimensions");
		}

		// flag gain for extents to recompute it; save previous and put it back after
		double pgain = this.gain;
		this.gain = Double.NaN;
		for (int i = 0; i < coverage.getExtentCount(); i++) {
			if (extents.get(i).getType() != coverage.getExtents().get(i).getType()) {
				throw new IllegalArgumentException("cannot merge a coverage with a scale with different dimensions");
			}
			// FIXME must use the MERGED extent - which are not kept. The extents array contains the full area to cover. 
			newcoverages.add(mergeExtent(i, getCurrentExtent(coverage, i), how));
		}

		double gain = this.gain;
		this.gain = pgain;

		// if nothing happened, reset gain to 0
		if (Double.isNaN(gain)) {
			gain = 0;
		}

		return new Coverage(this, newcoverages, gain);
	}

	/*
	 * Get the currently merged extent
	 */
	private IExtent getCurrentExtent(Scale coverage, int i) {
		if (coverage instanceof Coverage) {
			return ((Coverage)coverage).coverages.get(i).getFirst();
		} 
		return coverage.getExtents().get(i);
	}

	@Override
	public double getGain() {
		return gain;
	}

	/**
	 * Merging logics - not the simplest, so summarized here:
	 * <p>
	 * 
	 * <pre>
	 * Given
	 * 
	 *    orig  = the original extent (extents.get(i))
	 *    other = the passed extent of same type
	 *    curr  = the current extent at coverages.get(i).getFirst() (possibly null)
	 *    
	 * if UNION:
	 *    set X to orig.equals(other) ? other : (orig INTERSECTION other);
	 *    determine benefit of swapping curr with X:
	 *       if   (curr == null)
	 *         ok = X.extent > relevant
	 *       else (
	 *        set U = X UNION curr
	 *        ok = (U.extent - curr.extent) > relevant
	 *        
	 *    if (ok)
	 *        set prev to curr == null ? 0 : coverages.get(i).second
	 *        set curr to curr == null ? X else (X UNION curr)
	 *        set gain to curr.extent - prev
	 *        set coverage to curr.extent
	 * 
	 * if INTERSECTION:
	 *    if (curr == null) return previous;
	 *    else 
	 *        set prev to curr == null ? 0 : coverages.get(i).second
	 *        set curr to curr INTERSECTION other
	 *        set gain to prev - curr.extent (negative)
	 *        set coverage to curr.extent
	 * </pre>
	 * 
	 * @param i
	 * @param other
	 * @param how
	 * @return
	 */
	private Pair<IExtent, Double> mergeExtent(int i, IExtent other, LogicalConnector how) {

		IExtent orig = extents.get(i);
		
		if (orig instanceof ITime && ((ITime)orig).is(ITime.Type.INITIALIZATION)) {
			return new Pair<>(orig, 1.0);
		}
		
		IExtent current = coverages.get(i).getFirst();
		double ccover = coverages.get(i).getSecond();
		double newcover = 0;
		double gain = 0;
		double previouscoverage = current == null ? 0 : ccover;

		if (how == LogicalConnector.UNION) {

			double origcover = ((AbstractExtent)orig).getCoveredExtent();

			// guarantee that we don't union with anything larger. Use outer extent.
			IExtent x = orig.equals(other) ? other
					: ((AbstractExtent) ((AbstractExtent) orig).getExtent())
							.mergeCoverage(((AbstractExtent) other).getExtent(), LogicalConnector.INTERSECTION);

			IExtent union = null;
			if (current == null) {
				newcover = ((AbstractExtent)x).getCoveredExtent();
			} else {
				union = x.equals(current) ? x : ((AbstractExtent) x).mergeCoverage(current, LogicalConnector.UNION);
				newcover = ((AbstractExtent)union).getCoveredExtent();
			}

			// happens with non-dimensional extents
			if (!((AbstractExtent)x).isEmpty() && newcover == 0 && origcover == 0) {
				newcover = origcover = 1;
			}
			
			boolean proceed = ((newcover / origcover) - ccover) > minModelCoverage;
			if (proceed) {
				gain = (newcover / origcover) - previouscoverage;
				this.gain = Double.isNaN(this.gain) ? gain : this.gain * gain;
				return new Pair<>(newcover == 0 ? null : (current == null ? x : union), newcover / origcover);
			}

		} else if (how == LogicalConnector.INTERSECTION) {

			// if intersecting nothing with X, leave it at nothing
			if (current != null) {
				double origcover = ((AbstractExtent)orig).getCoveredExtent();
				IExtent x = ((AbstractExtent) current).mergeCoverage(((AbstractExtent) other).getExtent(),
						LogicalConnector.INTERSECTION);
				newcover = ((AbstractExtent)x).getCoveredExtent();

				// happens with non-dimensional extents
				if (!((AbstractExtent)x).isEmpty() && newcover == 0 && origcover == 0) {
					newcover = origcover = 1;
				}
				
				gain = (newcover / origcover) - previouscoverage; 
				this.gain = Double.isNaN(this.gain) ? gain : this.gain * gain;
				return new Pair<>(newcover == 0 ? null : x, newcover / origcover);
			}
			
		} else {
			// throw new IllegalArgumentException("cannot merge a coverage with another using operation: " + how);
		}

		// return the original, let gain untouched
		return new Pair<>(coverages.get(i).getFirst(), coverages.get(i).getSecond());
	}

	@Override
	public boolean isComplete() {
		return coverage >= minRequiredCoverage;
	}

	@Override
	public boolean isRelevant() {
		return coverage > minTotalCoverage;
	}

	public void setMinimumModelCoverage(double d) {
		this.minModelCoverage = d;
	}

	public void setMinimumTotalCoverage(double d) {
		this.minTotalCoverage = d;
	}

	public void setSufficientTotalCoverage(double d) {
		this.minRequiredCoverage = d;
	}

    public boolean coversBoundaries(Scale scale) {
        for (IExtent extent : scale.getExtents()) {
            for (Pair<IExtent, Double> cov : coverages) {
                if (cov.getFirst().getType() == extent.getType()) {
                    if (!extent.getBoundingExtent().contains(cov.getFirst().getBoundingExtent())) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
