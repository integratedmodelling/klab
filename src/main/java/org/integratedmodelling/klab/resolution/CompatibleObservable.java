package org.integratedmodelling.klab.resolution;

import org.integratedmodelling.klab.owl.Observable;

/**
 * A specialized observable that redefines its equality methods so that only one
 * compatible observable per type will be found in a set.
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
        return obj instanceof Observable && ((Observable)obj).getMain().equals(main);
    }

}
