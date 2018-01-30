package org.integratedmodelling.klab.engine.annotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.integratedmodelling.klab.Annotations;
import org.integratedmodelling.klab.api.auth.IIdentity.Type;
import org.integratedmodelling.klab.api.model.IKimObject;
import org.integratedmodelling.klab.api.model.IObserver;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.ISubject;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.engine.runtime.Session;

public class TestHandler implements Annotations.Handler {

    @Override
    public Object process(IKimObject target, Map<String, Object> arguments, IMonitor monitor) throws Exception {
        
      String id = arguments.containsKey("id") ? arguments.get("id").toString() : "unnamed test";

      if (!(arguments.get("observations") instanceof List)) {
          monitor.warn("run rannotation does not specify observations");
          return null;
      }
      
      List<IObservation> result = new ArrayList<>();
      
      List<?> observations = (List<?>)arguments.get("observations");
      /* only run the context if we are in a script */
      if (monitor.getIdentity().is(Type.SCRIPT)) {

        // safe cast as the annotation is limited to observers
        IObserver observer = (IObserver) target;
        Session session = monitor.getIdentity().getParent(Session.class);

        if (session != null && observer != null) {
          ISubject subject = session.observe(observer.getName()).get();
          if (subject != null) {
            for (Object o : observations) {
              IObservation ret = subject.observe(o.toString()).get();
              if (ret == null) {
                monitor.warn(id + ": observation of " + o + " in context " + subject.getName() + " was unsuccessful");
              } else {
                /*
                 * TODO run any assertion indicated for the observations
                 */
                result.add(ret);
              }
            }
            /*
             * TODO run any assertion indicated for the subject
             */
          } else {
            monitor.warn(id + ": observation of " + observer.getName() + " was unsuccessful");
          }
        } else {
          monitor.error(id + ": errors in retrieving observer or session");
        }
      }

      return result;
    }

}
