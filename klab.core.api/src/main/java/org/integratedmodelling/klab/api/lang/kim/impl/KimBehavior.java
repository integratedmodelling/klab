package org.integratedmodelling.klab.api.lang.kim.impl;

import java.util.Collection;
import java.util.Iterator;

import org.integratedmodelling.klab.api.lang.KServiceCall;
import org.integratedmodelling.klab.api.lang.kim.KKimAction;
import org.integratedmodelling.klab.api.lang.kim.KKimBehavior;

/**
 * A IKimBehavior is the statement of the contextualization strategy
 * for a model or an observation, consisting of a list of action and
 * a set of general methods for convenience.
 * 
 * @author fvilla
 *
 */
public class KimBehavior extends KimStatement implements KKimBehavior {

    private static final long serialVersionUID = 2701074196387350255L;

    @Override
    public Iterator<KKimAction> iterator() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isEmpty() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isDynamic() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Collection<KServiceCall> getExtentFunctions() {
        // TODO Auto-generated method stub
        return null;
    }

    
}
