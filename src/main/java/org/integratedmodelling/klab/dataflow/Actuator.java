package org.integratedmodelling.klab.dataflow;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.integratedmodelling.kdl.api.IKdlActuator;
import org.integratedmodelling.kim.api.IKimFunctionCall;
import org.integratedmodelling.klab.Observations;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.dataflow.IActuator;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.observation.DirectObservation;
import org.integratedmodelling.klab.observation.Scale;
import org.integratedmodelling.klab.owl.Observable;

public class Actuator<T extends IArtifact> implements IActuator {

  String                     name;
  String                     alias;
  String                     namespace;
  Observable                 newObservationType;
  String                     newObservationUrn;
  Scale                      scale;
  IKdlActuator.Type          type;
  List<IActuator>            actuators    = new ArrayList<>();
  List<IKimFunctionCall>     computation  = new ArrayList<>();
  Date                       creationTime = new Date();
  private Class<? extends T> cls;

  @Override
  public String getName() {
    return name;
  }

  public Actuator(Class<? extends T> cls) {
    this.cls = cls;
  }

  @Override
  public Scale getScale() {
    return scale;
  }

  @Override
  public List<IActuator> getActuators() {
    return actuators;
  }

  @Override
  public List<IActuator> getInputs() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<IActuator> getOutputs() {
    // TODO Auto-generated method stub
    return null;
  }

  /**
   * 
   * @param context
   * @param monitor
   * @return
   * @throws KlabException
   */
  @SuppressWarnings("unchecked")
  public T compute(DirectObservation context, IMonitor monitor) throws KlabException {

    // TODO
    T ret = null;
    if (this.newObservationType != null) {
        ret = (T) Observations.INSTANCE
                .createObservation(newObservationType, this.scale, this.namespace, monitor, context);
    }

    return ret;
  }

  /**
   * Reconstruct or return the source code for this actuator.
   * 
   * @param offset
   * @return
   */
  protected String encode(int offset) {

    String ofs = StringUtils.repeat(" ", offset);
    String ret = ofs + type.name().toLowerCase() + " "
        + (newObservationType == null ? name : newObservationType.getLocalName());

    boolean hasBody = actuators.size() > 0 || computation.size() > 0 || newObservationType != null
        || newObservationUrn != null;

    if (hasBody) {
      ret += encodeBody(offset, ofs);
    }

    return ret;

  }

  protected String encodeBody(int offset, String ofs) {
    String ret = " {\n";

    if (newObservationType != null) {
      ret += ofs + "   " + "observe new " + newObservationType.getDeclaration() + "\n";
    }
    if (newObservationUrn != null) {
      ret += ofs + "   " + "observe " + newObservationUrn + "\n";
    }

    for (IActuator actuator : actuators) {
      ret += ((Actuator<?>) actuator).encode(offset + 3) + "\n";
    }

    for (int i = 0; i < computation.size(); i++) {
      ret += (i == 0 ? (ofs + "compute") : ofs + "  ") + computation.get(i).getSourceCode()
          + (i < computation.size() - 1 ? "," : "") + "\n";
    }
    ret += ofs + "}";

    if (scale != null && !scale.isEmpty()) {
      // add 'over ' + scale specs
    }

    if (alias != null) {
      ret += " as " + alias;
    }

    return ret;
  }

  public static <T extends IArtifact> Actuator<T> create(Class<T> observationClass) {
    return new Actuator<T>(observationClass);
  }

  public Actuator<T> getReference() {
    return ActuatorReference.create(this, cls);
  }


}
