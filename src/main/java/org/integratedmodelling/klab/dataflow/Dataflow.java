package org.integratedmodelling.klab.dataflow;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.integratedmodelling.kdl.api.IKdlActuator;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.dataflow.IActuator;
import org.integratedmodelling.klab.api.runtime.dataflow.IDataflow;
import org.integratedmodelling.klab.api.runtime.dataflow.ILink;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.observation.Scale;

public class Dataflow<T extends IArtifact> extends Actuator<T> implements IDataflow<T> {

  List<IActuator> actuators = new ArrayList<>();
  
  @Override
  public Iterator<IActuator> iterator() {
    // TODO use a topological iterator; put each group of parallelizable actuators within a dataflow.
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
    // TODO
    return super.compute(monitor);
  }

}
