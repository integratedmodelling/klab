package org.integratedmodelling.klab.engine.annotations;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.eclipse.xtext.util.Exceptions;
import org.integratedmodelling.klab.Annotations;
import org.integratedmodelling.klab.Klab;
import org.integratedmodelling.klab.api.auth.IIdentity.Type;
import org.integratedmodelling.klab.api.model.IKimObject;
import org.integratedmodelling.klab.api.model.IObserver;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.ISubject;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.engine.runtime.Session;

public class TestHandler implements Annotations.Handler {

  @Override
  public Object process(IKimObject target, Map<String, Object> arguments, IMonitor monitor)
      throws Exception {

    String id = arguments.containsKey("name") ? arguments.get("name").toString() : "unnamed test";
    int repetitions = arguments.containsKey("repeat") ? (Integer) arguments.get("repeat") : 1;

    if (!(arguments.get("observations") instanceof List)) {
      monitor.warn("test annotation does not specify observations");
      return null;
    }

    List<IObservation> result = new ArrayList<>();
    List<Throwable> exceptions = new ArrayList<>();
    List<?> observations = (List<?>) arguments.get("observations");
    /* only run the context if we are in a script */
    if (monitor.getIdentity().is(Type.SCRIPT)) {

      for (int i = 0; i < repetitions; i++) {

        try {
          Klab.INSTANCE
              .info("----------------------------------------------------------------------");
          Klab.INSTANCE.info("Running test " + id + " on " + new Date()
              + (repetitions == 1 ? "" : (" [#" + i + "]")));
          Klab.INSTANCE
              .info("----------------------------------------------------------------------");

          // safe cast as the annotation is limited to observers
          IObserver observer = (IObserver) target;
          Session session = monitor.getIdentity().getParent(Session.class);

          if (session != null && observer != null) {
            ISubject subject = session.observe(observer.getName()).get();
            if (subject != null) {
              for (Object o : observations) {
                IObservation ret = subject.observe(o.toString()).get();
                if (ret == null) {
                  monitor.warn(id + ": observation of " + o + " in context " + subject.getName()
                      + " was unsuccessful");
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
        } catch (Throwable t) {
          exceptions.add(t);
        }
      }

      Klab.INSTANCE.info("Finished test " + id + " on " + new Date() + " with "
          + ((monitor.hasErrors() || exceptions.size() > 0) ? "errors" : "no errors"));
      
      for (Throwable t : exceptions) {
        Klab.INSTANCE.info("   EXCEPTION: " + t.getMessage());
      }

    }

    return result;
  }

}
