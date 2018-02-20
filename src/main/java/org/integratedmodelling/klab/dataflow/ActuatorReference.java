package org.integratedmodelling.klab.dataflow;

import org.apache.commons.lang.StringUtils;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.dataflow.IActuator;
import org.integratedmodelling.klab.api.runtime.dataflow.IActuatorReference;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.observation.DirectObservation;

public class ActuatorReference<T extends IArtifact> extends Actuator<T> implements IActuatorReference {

  public static <T extends IArtifact> ActuatorReference<T> create(Actuator<T> actuator, Class<? extends T> cls) {
    return new ActuatorReference<T>(actuator, cls);
  }
  
  private ActuatorReference(Actuator<T> original, Class<? extends T> cls) {
    super(cls);
    this.original = original;
  }

  Actuator<T> original;
  
  @Override
  public IActuator getOriginalActuator() {
    return original;
  }

  @Override
  public T compute(DirectObservation context, IMonitor monitor) throws KlabException {
    return null;
  }

  @Override
  protected String encode(int offset) {
    
    String ofs = StringUtils.repeat(" ", offset);
    String ret = ofs + "import " + original.type.name().toLowerCase() + " " + name;

    if (!computation.isEmpty()) {
      ret += encodeBody(offset, ofs); 
    }
    
    return ret;
  }
  
}
