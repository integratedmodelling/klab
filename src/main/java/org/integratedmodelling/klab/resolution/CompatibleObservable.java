package org.integratedmodelling.klab.resolution;

import org.integratedmodelling.klab.owl.Observable;

/**
 * A specialized observable that redefines its equality methods so that only one compatible
 * observable per type will be found in a set. Equality is assessed based on the main observable
 * only, and
 * 
 * Use with great care as this breaks the simmetry contract of equality. A classified observable
 * does not equal() an unclassified one (or one classified at a lower detail) but the opposite is
 * true.
 * 
 * @author Ferd
 *
 */
public class CompatibleObservable extends Observable {

  public CompatibleObservable(Observable observable) {
    super(observable);
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((main == null) ? 0 : main.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Observable) {
      return this.canResolve((Observable)obj);
    }
    return false;
  }

}
