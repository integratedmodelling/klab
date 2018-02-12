package org.integratedmodelling.klab.api.runtime.dataflow;

import java.util.List;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;

public interface IActuator {

  String getName();

  List<IPort> getInputs();

  List<IPort> getOutputs();

  public IArtifact<?> run(IMonitor monitor);
}
