package org.integratedmodelling.klab.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.integratedmodelling.kim.api.IKimBehavior;
import org.integratedmodelling.klab.api.model.IAction;
import org.integratedmodelling.klab.api.model.IActiveKimObject;
import org.integratedmodelling.klab.api.model.IBehavior;
import org.integratedmodelling.klab.api.observations.scale.IExtent;

public class Behavior implements IBehavior {

    List<IAction> actions = new ArrayList<>();
    List<IExtent> extents;
    IKimBehavior  statement;

    class Action implements IAction {

    }

    public Behavior(IKimBehavior behavior, IActiveKimObject model) {
        // TODO Auto-generated constructor stub
        this.statement = behavior;
    }

    @Override
    public Iterator<IAction> iterator() {
        return actions.iterator();
    }

    @Override
    public Collection<IExtent> getExtents() {
        if (this.extents == null) {
            this.extents = new ArrayList<>();
            /*
             * TODO exec all the functions in the statement
             */
        }
        return this.extents;
    }

}
