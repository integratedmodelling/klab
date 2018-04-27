package org.integratedmodelling.klab.engine.annotations;

import java.util.ArrayList;
import java.util.List;

import org.integratedmodelling.klab.Annotations;
import org.integratedmodelling.klab.api.auth.IIdentity.Type;
import org.integratedmodelling.klab.api.model.IKimObject;
import org.integratedmodelling.klab.api.model.IObserver;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.ISubject;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.components.geospace.utils.SpatialDisplay;
import org.integratedmodelling.klab.engine.runtime.Session;
import org.integratedmodelling.klab.utils.Parameters;

/**
 * The handler for the 'run' annotation makes observations in the target context and returns them.
 * 
 * @author ferdinando.villa
 *
 */
public class RunHandler implements Annotations.Handler {

  @Override
  public Object process(IKimObject target, Parameters arguments, IMonitor monitor) throws Exception {

    if (!(arguments.get("observations") instanceof List)) {
        monitor.warn("run rannotation does not specify observations");
        return null;
    }
    
    List<IObservation> result = new ArrayList<>();
    
    List<?> observations = arguments.get("observations", List.class);
    /* only run the context if we are in a script */
    if (monitor.getIdentity().is(Type.SCRIPT)) {

      // safe cast as the annotation is limited to observers
      IObserver observer = (IObserver) target;
      Session session = monitor.getIdentity().getParentIdentity(Session.class);

      if (session != null && observer != null) {
        ISubject subject = session.observe(observer.getName()).get();
        if (subject != null) {
          for (Object o : observations) {
            IObservation ret = subject.observe(o.toString()).get();
            if (ret == null) {
              monitor.warn("observation of " + o + " in context " + subject.getName() + " was unsuccessful");
            } else {
              result.add(ret);
            }
          }
        } else {
          monitor.warn("observation of " + observer.getName() + " was unsuccessful");
        }
        
        if (subject != null && arguments.get("visualize", false)) {
          if (subject.getScale().isSpatiallyDistributed()) {
            SpatialDisplay display = new SpatialDisplay(subject.getScale().getSpace());
            for (IState state : subject.getStates()) {
              display.add(state);
            }
            display.show();
          }
        }
        
      } else {
        monitor.error("errors in retrieving observer or session");
      }
    }

    return result;
  }

}
