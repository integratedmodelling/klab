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
		result = prime * result + ((getBy() == null) ? 0 : getBy().hashCode());
		result = prime * result + ((getDownTo() == null) ? 0 : getDownTo().hashCode());
		result = prime * result + ((getMain() == null) ? 0 : getMain().hashCode());
		result = prime * result + ((getObservable() == null) ? 0 : getObservable().hashCode());
		return result;
  }

  @Override
  public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Observable other = (Observable) obj;
		if (getBy() == null) {
			if (other.getBy() != null) {
				return false;
			}
		} else if (!getBy().equals(other.getBy())) {
			return false;
		}
		if (getDownTo() == null) {
			if (other.getDownTo() != null) {
				return false;
			}
		} else if (!getDownTo().equals(other.getDownTo())) {
			return false;
		}
		if (getMain() == null) {
			if (other.getMain() != null) {
				return false;
			}
		} else if (!getMain().equals(other.getMain())) {
			return false;
		}
		if (getObservable() == null) {
			if (other.getObservable() != null) {
				return false;
			}
		} else if (!getObservable().equals(other.getObservable())) {
			return false;
		}
		return true;
  }

}
