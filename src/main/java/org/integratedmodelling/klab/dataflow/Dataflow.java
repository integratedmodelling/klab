package org.integratedmodelling.klab.dataflow;

import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.dataflow.IActuator;
import org.integratedmodelling.klab.api.runtime.dataflow.IDataflow;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.observation.DirectObservation;

public class Dataflow<T extends IArtifact> extends Actuator<T> implements IDataflow<T> {

  public Dataflow(IMonitor monitor, Class<? extends T> cls) {
    super(monitor, cls);
  }

  String            description;
  private DirectObservation context;
  private double            coverage;

  @Override
  public T run(IMonitor monitor) throws KlabException {
    // TODO enough?
    return compute(getContext(), monitor);
  }

  @SuppressWarnings("unchecked")
  public T compute(DirectObservation context, IMonitor monitor) throws KlabException {

    // TODO use futures and compute all children in parallel; chain resulting artifacts. Not really
    // important at the moment.
    T ret = null;

    for (IActuator actuator : actuators) {
      // TODO this is the top-level execution so it should order by dependency and run the children
      // appropriately.
      Object o = ((Actuator<?>) actuator).compute(context, monitor);
      if (o != null) {
        if (ret == null) {
          ret = (T) o;
        } else {
          // chain it
        }
      }
    }
    return ret;
  }

  @Override
  protected String encode(int offset) {

    String ret = "";

    if (offset == 0) {
      ret += "@klab " + Version.CURRENT + "\n";
      ret += "@dataflow " + getName() + "\n";
      ret += "@author 'k.LAB resolver " + creationTime + "'" + "\n";
      if (getContext() != null) {
        ret += "@context " + getContext().getUrn() + "\n";
      }
      ret += "\n";
    }

    for (IActuator actuator : actuators) {
      ret += ((Actuator<?>) actuator).encode(offset) + "\n";
    }

    return ret;
  }

  /**
   * Return the source code of the dataflow.
   * 
   * @return the source code as a string.
   */
  @Override
  public String getKdlCode() {
    return encode(0);
  }

  /**
   * Called by tasks
   * 
   * @param name
   * @param description
   */
  public void setName(String name, String description) {
    this.name = name;
    this.description = description;
  }

public double getCoverage() {
    return coverage;
}

public void setCoverage(double coverage) {
    this.coverage = coverage;
}

public DirectObservation getContext() {
    return context;
}

public void setContext(DirectObservation context) {
    this.context = context;
}


}
