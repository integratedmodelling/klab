package org.integratedmodelling.klab.resolution;

import org.integratedmodelling.klab.owl.Observable;

/**
 * A specialized observable that redefines its equality methods so that only one compatible
 * observable per type will be found in a set. Equality is assessed based only on the equality of
 * the observable concept, independent of any mediations, units etc (which are guaranteed compatible
 * by the semantics).
 * 
 * A set of compatible observables can be further reduced using
 * {@link Observable#canResolve(Observable)} to optimize when classifications can be encoded
 * directly instead of calling other models.
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
    result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Observable) {
      return this.getType().equals(((Observable) obj).getType());
    }
    return false;
  }

}
