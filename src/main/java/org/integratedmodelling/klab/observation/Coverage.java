package org.integratedmodelling.klab.observation;

import java.util.ArrayList;
import java.util.List;
import org.integratedmodelling.kim.api.data.IGeometry.Dimension.Type;
import org.integratedmodelling.klab.api.observations.scale.IExtent;
import org.integratedmodelling.klab.api.observations.scale.ITopologicallyComparable;
import org.integratedmodelling.klab.api.resolution.ICoverage;
import org.integratedmodelling.klab.common.LogicalConnector;
import org.integratedmodelling.klab.utils.Pair;

public class Coverage extends Scale implements ICoverage {

  private static final long serialVersionUID = 7035851173441618273L;

  /*
   * Default - do not accept a state model unless its coverage is greater than this. Instantiator
   * models make this 0.
   * 
   * TODO make this configurable
   */
  private static double MIN_MODEL_COVERAGE = 0.01;

  /*
   * Default - we accept models if they cover at least an additional 20% of the whole context TODO
   * make this configurable
   */
  private static double MIN_TOTAL_COVERAGE = 0.20;

  /*
   * Default - we stop adding models when we cover at least 95% of the whole context. TODO make this
   * configurable
   */
  private static double MIN_REQUIRED_COVERAGE = 0.95;

  // make local copies that may be modified and are inherited by children
  private double minModelCoverage = MIN_MODEL_COVERAGE;
  private double minTotalCoverage = MIN_TOTAL_COVERAGE;
  private double minRequiredCoverage = MIN_REQUIRED_COVERAGE;

  List<Pair<IExtent, Double>> coverages = new ArrayList<>();
  double coverage;
  double gain = 0;

  /**
   * Create a coverage with full coverage, which can be reduced by successive AND merges.
   * 
   * @param original
   * @return a full coverage for the passed scale.
   */
  public static Coverage full(Scale original) {
    return new Coverage(original, 1.0);
  }

  protected void setTo(Coverage other) {
    extents.clear();
    extents.addAll(other.extents);
    sort();
    coverages.clear();
    coverages.addAll(other.coverages);
    coverage = other.coverage;
  }

  /**
   * Create a coverage with full coverage, which can be increased by successive OR merges.
   * 
   * @param original
   * @return
   */
  public static Coverage empty(Scale original) {
    return new Coverage(original, 0.0);
  }

  protected Coverage(Scale original, double initialCoverage) {
    super(original.extents);
    this.coverage = initialCoverage;
    for (IExtent extent : extents) {
      coverages.add(new Pair<>(initialCoverage > 0 ? extent : null, initialCoverage));
    }
  }

  private Coverage(Coverage original, List<Pair<IExtent, Double>> newcoverages) {
    super(original.extents);
    this.coverage = Double.NaN;
    for (Pair<IExtent, Double> cov : newcoverages) {
      coverages.add(cov);
      this.coverage =
          Double.isNaN(this.coverage) ? cov.getSecond() : (this.coverage * cov.getSecond());
    }
  }

  public Coverage(Coverage other) {
    this(other, other.coverages);
  }

  protected void setCoverage(double c) {
    if (!(c == 0 || c == 1)) {
      throw new IllegalArgumentException("a coverage can only be explicitly set to 0 or 1");
    }
    this.coverage = c;
    for (Pair<IExtent, Double> cov : coverages) {
      cov.setSecond(c);
      if (c == 0) {
        cov.setFirst(null);
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
  public ICoverage merge(ITopologicallyComparable<?> other, LogicalConnector how) {

    if (!(other instanceof Coverage)) {
      throw new IllegalArgumentException("a coverage can only merge another coverage");
    }

    Coverage coverage = (Coverage) other;
    List<Pair<IExtent, Double>> newcoverages = new ArrayList<>();

    if (coverage.getExtentCount() != getExtentCount()) {
      throw new IllegalArgumentException(
          "cannot merge a coverage with a scale with different dimensions");
    }
    for (int i = 0; i < coverage.getExtentCount(); i++) {
      if (extents.get(i).getType() != coverage.getExtents().get(i).getType()) {
        throw new IllegalArgumentException(
            "cannot merge a coverage with a scale with different dimensions");
      }
      newcoverages.add(mergeExtent(i, coverage.getExtents().get(i), how));
    }

    return new Coverage(this, newcoverages);
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
   *    set X to orig.equals(other) ? other : orig INTERSECTION other;
   *    determine benefit of swapping curr with X
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

    IExtent current = coverages.get(i).getFirst().merge(other, how);

    if (how == LogicalConnector.UNION) {

      double ncoverage = current.getCoveredExtent() / extents.get(i).getCoveredExtent();

      /*
       * only add it if the increment is enough to justify
       */
      if ((ncoverage - coverages.get(i).getSecond()) >= minRequiredCoverage) {
        return new Pair<>(current, ncoverage);
      } else {
        return coverages.get(i);
      }
    } else if (how == LogicalConnector.INTERSECTION) {

      double ncoverage = current.getCoveredExtent() / extents.get(i).getCoveredExtent();
      return new Pair<>(current, ncoverage);

    } else {
      throw new IllegalArgumentException(
          "cannot merge a coverage with another using operation: " + how);
    }
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
}
