package org.integratedmodelling.klab.observation;

import java.util.Iterator;
import org.integratedmodelling.kim.api.IKimFunctionCall;
import org.integratedmodelling.klab.api.observations.scale.IExtent;

public abstract class Extent implements IExtent {

    public static int INAPPROPRIATE_LOCATOR = -2;
    public static int GENERIC_LOCATOR       = -1;
    
    @Override
    public Iterator<IExtent> iterator() {
        return new Iterator<IExtent>() {

            int i = 0;
            
            @Override
            public boolean hasNext() {
                return i < (getMultiplicity() - 1);
            }

            @Override
            public IExtent next() {
                return getExtent(i++);
            }
            
        };
    }
    
    /**
     * All extents must be able to produce a deep copy of themselves.
     * 
     * @return a new extent identical to this.
     */
    public abstract Extent copy();

    /**
     * All extents must have a two-way street between k.IM code functions and themselves.
     * 
     * @return the k.IM function call specifying this extent.
     */
    public abstract IKimFunctionCall getKimSpecification();
   
}
