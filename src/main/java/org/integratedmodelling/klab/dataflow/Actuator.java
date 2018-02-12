package org.integratedmodelling.klab.dataflow;

import java.util.List;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.dataflow.IActuator;
import org.integratedmodelling.klab.api.runtime.dataflow.IPort;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.owl.Observable;

public abstract class Actuator<T extends IArtifact<?>> implements IActuator {

  String     name;

  Observable newObservationType;
  String     newObservationUrn;

  @Override
  public String getName() {
    return name;
  }

  public Actuator() {
    // TODO Auto-generated constructor stub
  }

  @Override
  public List<IPort> getInputs() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<IPort> getOutputs() {
    // TODO Auto-generated method stub
    return null;
  }

  public abstract T run(IMonitor monitor);

}
