package org.integratedmodelling.klab.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.integratedmodelling.kim.api.IKimBehavior;
import org.integratedmodelling.kim.api.IKimFunctionCall;
import org.integratedmodelling.klab.Extensions;
import org.integratedmodelling.klab.api.model.IAction;
import org.integratedmodelling.klab.api.model.IActiveKimObject;
import org.integratedmodelling.klab.api.model.IBehavior;
import org.integratedmodelling.klab.api.observations.scale.IExtent;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;

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
    public Collection<IExtent> getExtents(IMonitor monitor) throws KlabException {
        if (this.extents == null) {
            this.extents = new ArrayList<>();
            for (IKimFunctionCall extentFunction : statement.getExtentFunctions()) {
              Object extent = Extensions.INSTANCE.callFunction(extentFunction, monitor);
              if (!(extent instanceof IExtent)) {
                throw new KlabValidationException("function " + extentFunction + " does not produce a valid extent");
              }
              this.extents.add((IExtent) extent);
            }
        }
        return this.extents;
    }

}
