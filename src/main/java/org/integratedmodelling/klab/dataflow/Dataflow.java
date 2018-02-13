package org.integratedmodelling.klab.dataflow;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.dataflow.IActuator;
import org.integratedmodelling.klab.api.runtime.dataflow.IDataflow;
import org.integratedmodelling.klab.api.runtime.dataflow.ILink;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.observation.Scale;

public class Dataflow<T extends IArtifact> extends Actuator<T> implements IDataflow<T> {

  String name;
  Scale scale;
  List<IActuator> actuators = new ArrayList<>();
  
  public static BuilderImpl builder() {
      return new BuilderImpl();
  }
  
  @Override
  public String getName() {
    return name;
  }

  @Override
  public Scale getScale() {
    return scale;
  }
  
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
  
  public static class BuilderImpl implements Builder {
    @Override
    public <T extends IArtifact> IDataflow<T> build(Class<T> cls) {
      // TODO Auto-generated method stub
      return null;
    }
  }

  @Override
  public T run(IMonitor monitor) {
    // TODO Auto-generated method stub
    return null;
  }


}
