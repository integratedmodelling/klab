package org.integratedmodelling.klab.components.localstorage.impl;

import java.util.Iterator;

import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.ILocator;

/**
 * A special-purpose locator that will point to one specific timeslice in this specific
 * implementation of State. Used to quickly download the different temporal states of a state.
 * 
 * @author Ferd
 *
 */
public class TimesliceLocator implements ILocator {

    int sliceIndex;
    String label;
    String codeName;

    TimesliceLocator(AbstractAdaptiveStorage<?> storage, int slice) {
        this.sliceIndex = slice;
    }

    @Override
    public Iterator<ILocator> iterator() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IGeometry getGeometry() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public double getCoverage() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public <T extends ILocator> T as(Class<T> cls) {
        // TODO Auto-generated method stub
        return null;
    }

}
