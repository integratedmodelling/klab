package org.integratedmodelling.klab.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.integratedmodelling.kim.api.IKimBehavior;
import org.integratedmodelling.klab.api.model.IAction;
import org.integratedmodelling.klab.api.model.IActiveKimObject;
import org.integratedmodelling.klab.api.model.IBehavior;

public class Behavior implements IBehavior {

    List<IAction> actions = new ArrayList<>();
    
    class Action implements IAction {
        
    }
    
    public Behavior(IKimBehavior behavior, IActiveKimObject model) {
        // TODO Auto-generated constructor stub
    }

    @Override
    public Iterator<IAction> iterator() {
        return actions.iterator();
    }

}
