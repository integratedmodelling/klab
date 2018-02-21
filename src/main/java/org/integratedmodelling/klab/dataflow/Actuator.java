package org.integratedmodelling.klab.dataflow;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.integratedmodelling.kdl.api.IKdlActuator;
import org.integratedmodelling.kim.api.IKimFunctionCall;
import org.integratedmodelling.klab.Observations;
import org.integratedmodelling.klab.api.model.INamespace;
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
  INamespace                 namespace;
  Observable                 observable;
  Scale                      scale;
  IKdlActuator.Type          type;
  List<IActuator>            actuators           = new ArrayList<>();
  IMonitor                   monitor;
  Date                       creationTime        = new Date();
  boolean                    createObservation;

  /*
   * these are the specs from which the contextualizers are built: first the computation, then the
   * mediation. We keep them separated because the compiler needs to rearrange mediators and
   * references as needed. Then both get executed to produce the final list of contextualizers.
   */
  List<IKimFunctionCall>     computationStrategy = new ArrayList<>();
  List<IKimFunctionCall>     mediationStrategy   = new ArrayList<>();


  private Class<? extends T> cls;

  @Override
  public String getName() {
    return name;
  }

  public Actuator(IMonitor monitor, Class<? extends T> cls) {
    this.cls = cls;
    this.monitor = monitor;
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
    if (this.createObservation) {
      ret = (T) Observations.INSTANCE.createObservation(observable, this.scale, this.namespace,
          monitor, context);
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
        + (observable == null ? name : observable.getLocalName());

    boolean hasBody = actuators.size() > 0 || computationStrategy.size() > 0
        || mediationStrategy.size() > 0 || observable != null;

    if (hasBody) {
      ret += encodeBody(offset, ofs);
    }

    return ret;

  }

  protected String encodeBody(int offset, String ofs) {
    String ret = " {\n";

    if (createObservation) {
      ret += ofs + "   " + "observe new " + observable.getDeclaration() + "\n";
    }

    for (IActuator actuator : actuators) {
      ret += ((Actuator<?>) actuator).encode(offset + 3) + "\n";
    }

    List<IKimFunctionCall> computation = new ArrayList<>();
    computation.addAll(computationStrategy);
    computation.addAll(mediationStrategy);

    for (int i = 0; i < computation.size(); i++) {
      ret += (i == 0 ? (ofs + "   compute") : ofs + "     ") + computation.get(i).getSourceCode()
          + (i < computation.size() - 1 ? "," : "") + "\n";
    }
    ret += ofs + "}";

    if (alias != null) {
      ret += " as " + alias;
    }

    if (scale != null && !scale.isEmpty()) {
      List<IKimFunctionCall> scaleSpecs = scale.getKimSpecification();
      if (!scaleSpecs.isEmpty()) {
        ret += " over";
        for (int i = 0; i < scaleSpecs.size(); i++) {
          ret += " " + scaleSpecs.get(i).getSourceCode() + ((i < scaleSpecs.size() - 1) ? (",\n" + ofs + "      ") : "");
        }
      }
    }

    return ret;
  }

  public static <T extends IArtifact> Actuator<T> create(IMonitor monitor,
      Class<T> observationClass) {
    return new Actuator<T>(monitor, observationClass);
  }

  public Actuator<T> getReference() {
    return ActuatorReference.create(this, cls);
  }


}
