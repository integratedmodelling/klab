package org.integratedmodelling.klab.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.integratedmodelling.kim.api.IKimAction;
import org.integratedmodelling.kim.api.IKimAction.Trigger;
import org.integratedmodelling.kim.api.IKimBehavior;
import org.integratedmodelling.kim.api.IServiceCall;
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

    public Behavior(IKimBehavior behavior, IActiveKimObject model) {
        // TODO Auto-generated constructor stub
        this.statement = behavior;
        for (IKimAction action : behavior) {
          actions.add(new Action(action));
        }
    }

    @Override
    public Iterator<IAction> iterator() {
        return actions.iterator();
    }

    /**
     * True if the behavior defines a scale for the containing object. Used during resolution.
     * 
     * @return true if scale is defined
     */
    public boolean hasScale() {
      return this.extents != null && !this.extents.isEmpty();
    }
    
    @Override
    public Collection<IExtent> getExtents(IMonitor monitor) throws KlabException {
        if (this.extents == null) {
            this.extents = new ArrayList<>();
            for (IServiceCall extentFunction : statement.getExtentFunctions()) {
              Object extent = Extensions.INSTANCE.callFunction(extentFunction, monitor);
              if (!(extent instanceof IExtent)) {
                throw new KlabValidationException("function " + extentFunction + " does not produce a valid extent");
              }
              this.extents.add((IExtent) extent);
            }
        }
        return this.extents;
    }

    @Override
    public List<IAction> getActions(Trigger trigger) {
      // TODO Auto-generated method stub
      return null;
    }

}
