package org.integratedmodelling.klab.resolution;

import java.util.ArrayList;
import java.util.List;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.observations.scale.IExtent;
import org.integratedmodelling.klab.api.observations.scale.ITopologicallyComparable;
import org.integratedmodelling.klab.api.resolution.ICoverage;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.observation.Scale;

public class Coverage implements ICoverage {

  /*
   * do not accept a model unless its coverage is greater than this.
   */
  private static double MIN_MODEL_COVERAGE = 0.01;

  /*
   * default: we accept models if they cover at least an additional 20% of the whole context
   */
  private static double MIN_TOTAL_COVERAGE = 0.20;

  /*
   * default: we stop adding models when we cover at least 95% of the whole context.
   */
  private static double MIN_REQUIRED_COVERAGE = 0.95;

  class CExt {
    IConcept domain;
    ITopologicallyComparable<?> original;
    ITopologicallyComparable<?> current;
    double coverage;

    CExt(IConcept domain, ITopologicallyComparable<?> original, ITopologicallyComparable<?> current,
        double coverage) {
      this.domain = domain;
      this.original = original;
      this.current = current;
      this.coverage = coverage;
    }
  }


  protected void setCoverage(double d) {
    this.coverage = d;
  }

  /*
   * For each extent of the original scale this contains: 1. the original, unmodified extent which
   * was defined as covered or uncovered at the constructor; 2. the currently covered extent; 3. the
   * proportion of coverage for this extent combination. The total coverage is the product of all
   * coverages.
   */
  List<CExt> current = new ArrayList<>();
  double coverage = 0.0;
  // only kept around for the copy constructor
  Scale scale;
  // if coverage is that of an object source, isEmpty will only return true for zero
  // coverage, so that points and small objects aren't lost.
  boolean isForObjects = false;

  private boolean forceRelevant;

  /**
   * Create coverage initialized at 1
   * 
   * @param scale
   */
  public Coverage(Scale scale) {
    this(scale, 1.0);
  }

  public Coverage(Scale scale, boolean isForObjects) {
    this(scale, 1.0);
    this.isForObjects = isForObjects;
  }

  public Coverage(ICoverage coverage) {
    this(((Coverage) coverage).scale, coverage.getCoverage());
  }

  @Override
  public boolean isEmpty() {
    return (isForObjects || forceRelevant) ? coverage <= 0 : coverage < MIN_MODEL_COVERAGE;
  }

  @Override
  public boolean isRelevant() {
    return (isForObjects || forceRelevant) ? coverage > 0 : coverage > MIN_TOTAL_COVERAGE;
  }


  @Override
  public Scale getScale() {
    return scale;
  }

  @Override
  public boolean isComplete() {
    return coverage >= MIN_REQUIRED_COVERAGE;
  }

  public static void setMinimumModelCoverage(double d) {
    MIN_TOTAL_COVERAGE = d;
  }

  public static void setMinimumTotalCoverage(double d) {
    MIN_MODEL_COVERAGE = d;
  }

  public static void setSufficientTotalCoverage(double d) {
    MIN_REQUIRED_COVERAGE = d;
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
      for (IExtent e : scale) {
        ITopologicallyComparable<?> orig = e.getExtent();
        ITopologicallyComparable<?> curr = coverage > 0 ? orig : null;
        current.add(new CExt(e.getDomainConcept(), orig, curr, coverage));
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

    Coverage ret = new Coverage();
    ret.coverage = 1.0;
    ret.isForObjects = isForObjects || ((Coverage) coverage).isForObjects;

    for (CExt my : current) {
      for (CExt his : ((Coverage) coverage).current) {
        if (his.domain.equals(my.domain)) {
          /*
           * recompute current using the other's
           */
          ITopologicallyComparable<?> other = his.current == null ? his.original : his.current;
          ITopologicallyComparable<?> current = my.current.union(other);
          double ncoverage = current.getCoveredExtent() / my.original.getCoveredExtent();

          /*
           * only add it if the increment is enough to justify
           */
          if ((ncoverage - my.coverage) >= MIN_REQUIRED_COVERAGE) {
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

    Coverage ret = new Coverage();
    ret.coverage = 1.0;
    ret.isForObjects = isForObjects || ((Coverage) coverage).isForObjects;
    ret.forceRelevant = forceRelevant || ((Coverage) coverage).forceRelevant;

    for (CExt my : current) {
      for (CExt his : ((Coverage) coverage).current) {
        if (his.domain.equals(my.domain)) {
          ITopologicallyComparable<?> other = his.current == null ? his.original : his.current;
          ITopologicallyComparable<?> current = my.current.intersection(other);
          double ncoverage = current.getCoveredExtent() / my.original.getCoveredExtent();
          ret.coverage *= ncoverage;
          ret.current.add(new CExt(my.domain, my.original, current, ncoverage));
        }
      }
    }
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

  public void forceRelevant() {
    this.forceRelevant = true;
  }

}
