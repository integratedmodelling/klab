package org.integratedmodelling.klab.dataflow;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.integratedmodelling.klab.Observations;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.dataflow.IActuator;
import org.integratedmodelling.klab.api.runtime.dataflow.IDataflow;
import org.integratedmodelling.klab.api.runtime.dataflow.ILink;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.observation.DirectObservation;

public class Dataflow<T extends IArtifact> extends Actuator<T> implements IDataflow<T> {

  List<IActuator>   actuators = new ArrayList<>();
  DirectObservation context;

  @Override
  public Iterator<IActuator> iterator() {
    // TODO use a topological iterator; put each group of parallelizable actuators within a
    // dataflow.
    return null;
  }

  @Override
  public List<IActuator> getActuators() {
    return actuators;
  }

  @Override
  public Collection<ILink> getLinks() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public T run(IMonitor monitor) throws KlabException {
    // TODO enough?
    return compute(context, monitor);
  }
  
  @SuppressWarnings("unchecked")
  public T compute(DirectObservation context, IMonitor monitor) throws KlabException {

      // TODO
      T ret = null;
      if (this.newObservationType != null) {
          ret = (T) Observations.INSTANCE
                  .createObservation(newObservationType, this.scale, this.namespace, monitor, context);
      }

      // TODO compute children
      
      return ret;
  }

}
