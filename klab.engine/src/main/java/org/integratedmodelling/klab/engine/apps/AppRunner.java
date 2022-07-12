package org.integratedmodelling.klab.engine.apps;

import java.util.ArrayList;
import java.util.List;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.Annotations;
import org.integratedmodelling.klab.api.auth.IIdentity.Type;
import org.integratedmodelling.klab.api.model.IKimObject;
import org.integratedmodelling.klab.api.model.IAcknowledgement;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.ISubject;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.components.runtime.observations.Subject;
import org.integratedmodelling.klab.engine.Engine.Monitor;
import org.integratedmodelling.klab.engine.runtime.Session;

/**
 * The handler for the 'run' annotation makes observations in the target context and returns them.
 * 
 * @author ferdinando.villa
 *
 */
public class AppRunner implements Annotations.Handler {

  @Override
  public Object process(IKimObject target, IParameters<String> arguments, IMonitor monitor) throws Exception {

    if (!(arguments.get("observations") instanceof List)) {
        monitor.warn("run annotation does not specify observations");
        return null;
    }
    
    List<IObservation> result = new ArrayList<>();
    
    List<?> observations = arguments.get("observations", List.class);
    /* only run the context if we are in a script */
    if (monitor.getIdentity().is(Type.SCRIPT)) {

      // safe cast as the annotation is limited to observers
      IAcknowledgement observer = (IAcknowledgement) target;
      Session session = monitor.getIdentity().getParentIdentity(Session.class);

      if (session != null && observer != null) {
        ISubject subject = (ISubject) session.getState().submit(observer.getName()).get();
        if (subject != null) {
          for (Object o : observations) {
            IArtifact ret = ((Subject)subject).observe(o.toString()).get();
            if (ret == null) {
              monitor.warn("observation of " + o + " in context " + subject.getName() + " was unsuccessful");
            } else {
              result.add((IObservation)ret);
            }
          }
        } else {
          monitor.warn("observation of " + observer.getName() + " was unsuccessful");
        }
        
        if (subject != null && arguments.get("visualize", false)) {
          if (subject.getScale().isSpatiallyDistributed()) {
//            SpatialDisplay display = new SpatialDisplay(subject.getScale().getSpace());
//            for (IState state : subject.getStates()) {
//              display.add(state);
//            }
//            display.show();
          }
        }
        
        if (subject != null && monitor instanceof Monitor) {
        	for (Monitor.Listener listener : ((Monitor)monitor).getListeners()) {
        		listener.notifyRootContext(subject);
        	}
        }
        
      } else {
        monitor.error("errors in retrieving observer or session");
      }
    }

    return result;
  }

}
