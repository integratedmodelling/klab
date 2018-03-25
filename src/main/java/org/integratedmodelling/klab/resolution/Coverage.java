package org.integratedmodelling.klab.resolution;

import java.util.ArrayList;
import java.util.List;
import org.integratedmodelling.kim.api.data.IGeometry.Dimension;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.observations.scale.IExtent;
import org.integratedmodelling.klab.api.observations.scale.ITopologicallyComparable;
import org.integratedmodelling.klab.api.resolution.ICoverage;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.observation.AbstractExtent;
import org.integratedmodelling.klab.observation.Extent;
import org.integratedmodelling.klab.observation.Scale;

public class Coverage implements ICoverage {

  /*
   * Default - do not accept a state model unless its coverage is greater than this. Instantiator
   * models make this 0.
   * 
   * TODO make this configurable
   */
  private static double MIN_MODEL_COVERAGE    = 0.01;

  /*
   * Default - we accept models if they cover at least an additional 20% of the whole context TODO
   * make this configurable
   */
  private static double MIN_TOTAL_COVERAGE    = 0.20;

  /*
   * Default - we stop adding models when we cover at least 95% of the whole context. TODO make this
   * configurable
   */
  private static double MIN_REQUIRED_COVERAGE = 0.95;

  // make local copies that may be modified and are inherited by children
  private double        minModelCoverage      = MIN_MODEL_COVERAGE;
  private double        minTotalCoverage      = MIN_TOTAL_COVERAGE;
  private double        minRequiredCoverage   = MIN_REQUIRED_COVERAGE;

  /*
   * For each extent of the original scale this contains: 1. the original, unmodified extent which
   * was defined as covered or uncovered at the constructor; 2. the currently covered extent; 3. the
   * proportion of coverage for this extent combination. The total coverage is the product of all
   * coverages.
   */
  List<CExt>            current               = new ArrayList<>();
  double                coverage              = 0.0;
  Scale                 scale;

  // used downstream after an and() or or() to check if the operation produced a significant change
  // in
  // coverage
  private boolean       mergeSignificant      = false;

  class CExt {
    Dimension.Type              domain;
    ITopologicallyComparable<?> original;
    ITopologicallyComparable<?> current;
    double                      coverage;

    CExt(Dimension.Type domain, ITopologicallyComparable<?> original,
        ITopologicallyComparable<?> current, double coverage) {
      this.domain = domain;
      this.original = original;
      this.current = current;
      this.coverage = coverage;
    }
  }

  protected void setCoverage(double d) {
    this.coverage = d;
  }

  public void setTo(Coverage cov) {
    this.scale = cov.scale;
    this.coverage = cov.coverage;
    current.clear();
    if (scale != null) {
      for (IExtent e : scale.getExtents()) {
        ITopologicallyComparable<?> orig = ((AbstractExtent) e).getExtent();
        ITopologicallyComparable<?> curr = coverage > 0 ? orig : null;
        current.add(new CExt(e.getType(), orig, curr, coverage));
      }
    }
  }

  /**
   * Create coverage initialized at 1
   * 
   * @param scale
   */
  public Coverage(Scale scale) {
    this(scale, 1.0);
  }

  public Coverage(Coverage coverage) {
    this(coverage.scale, coverage.getCoverage());
    this.minModelCoverage = coverage.minModelCoverage;
    this.minRequiredCoverage = coverage.minRequiredCoverage;
    this.minTotalCoverage = coverage.minTotalCoverage;
  }

  @Override
  public boolean isEmpty() {
    return coverage < minModelCoverage;
  }

  @Override
  public boolean isRelevant() {
    return coverage > minTotalCoverage;
  }

  @Override
  public Scale getScale() {
    return scale;
  }

  @Override
  public boolean isComplete() {
    return coverage >= minRequiredCoverage;
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

  /**
   * Create coverage with predefined value (either 0 or 1).
   * 
   * @param scale
   * @param coverage
   */
  public Coverage(Scale scale, double coverage) {
    this.scale = scale;
    this.coverage = coverage;
    if (scale != null) {
      for (IExtent e : scale.getExtents()) {
        ITopologicallyComparable<?> orig = ((AbstractExtent) e).getExtent();
        ITopologicallyComparable<?> curr = coverage > 0 ? orig : null;
        current.add(new CExt(e.getType(), orig, curr, coverage));
      }
    }
  }

  private Coverage() {}

  @Override
  public String toString() {
    return "coverage (" + current.size() + " ext) = " + coverage;
  }

  @Override
  public Double getCoverage() {
    return coverage;
  }

  @Override
  public Coverage or(ICoverage coverage) throws KlabException {

    if (coverage.isEmpty()) {
      return new Coverage(this);
    }

    Coverage ret = new Coverage();
    ret.coverage = 1.0;

    for (CExt my : current) {
      for (CExt its : ((Coverage) coverage).current) {
        if (its.domain.equals(my.domain)) {
          /*
           * recompute current using the other's
           */
          ITopologicallyComparable<?> other = its.current == null ? its.original : its.current;
          ITopologicallyComparable<?> current =
              my.current == null ? other : my.current.union(other);
          double ncoverage = current.getCoveredExtent() / my.original.getCoveredExtent();

          /*
           * only add it if the increment is enough to justify
           */
          if ((ncoverage - my.coverage) >= minRequiredCoverage) {
            ret.mergeSignificant = true;
            ret.coverage *= ncoverage;
            ret.current.add(new CExt(my.domain, my.original, current, ncoverage));
          } else {
            ret.coverage *= my.coverage;
            ret.current.add(new CExt(my.domain, my.original, my.current, my.coverage));
          }
        }
      }
    }
    return ret;
  }

  @Override
  public Coverage and(ICoverage coverage) throws KlabException {

    if (coverage.isEmpty()) {
      return new Coverage(((Coverage) coverage).scale, 0);
    }

    Coverage ret = new Coverage();
    ret.coverage = 1.0;

    for (CExt my : current) {
      for (CExt its : ((Coverage) coverage).current) {
        if (its.domain.equals(my.domain)) {
          ITopologicallyComparable<?> other = its.current == null ? its.original : its.current;
          ITopologicallyComparable<?> current =
              my.current == null ? other : my.current.intersection(other);
          double ncoverage = current.getCoveredExtent() / my.original.getCoveredExtent();
          ret.coverage *= ncoverage;
          ret.current.add(new CExt(my.domain, my.original, current, ncoverage));
        }
      }
    }

    ret.mergeSignificant = ret.isRelevant();

    return ret;
  }

  /**
   * for debugging
   * 
   * @param c
   * @return extent
   */
  public ITopologicallyComparable<?> getCurrentExtent(IConcept c) {
    for (CExt cc : current) {
      if (cc.domain.equals(c)) {
        return cc.current;
      }
    }
    return null;
  }

  /**
   * for debugging
   * 
   * @param c
   * @return original extent
   */
  public ITopologicallyComparable<?> getOriginalExtent(IConcept c) {
    for (CExt cc : current) {
      if (cc.domain.equals(c)) {
        return cc.current;
      }
    }
    return null;
  }

  public boolean isMergeSignificant() {
    return mergeSignificant;
  }

}
