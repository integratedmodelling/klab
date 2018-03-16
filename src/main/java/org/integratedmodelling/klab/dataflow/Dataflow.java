package org.integratedmodelling.klab.dataflow;

import java.util.concurrent.ExecutionException;
import org.integratedmodelling.klab.Klab;
import org.integratedmodelling.klab.Observations;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.data.raw.IObservationData;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.IComputationContext;
import org.integratedmodelling.klab.api.runtime.dataflow.IActuator;
import org.integratedmodelling.klab.api.runtime.dataflow.IDataflow;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.exceptions.KlabContextualizationException;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.observation.DirectObservation;
import org.integratedmodelling.klab.observation.Subject;
import org.integratedmodelling.klab.provenance.Artifact;

public class Dataflow<T extends IArtifact> extends Actuator implements IDataflow<T> {

  public Dataflow(IMonitor monitor, Class<? extends T> cls) {
    super(monitor);
  }

  String                    description;
  private DirectObservation context;
  private double            coverage;

  @SuppressWarnings("unchecked")
  @Override
  public T run(IMonitor monitor) throws KlabException {

    // ret = (T) Observations.INSTANCE.createObservation(getObservable(), this.getScale(),
    // this.getNamespace(), monitor, context);

    /*
     * 1. establish the computation context: if we have a runtime context, take it from it,
     * otherwise make one.
     */
    IComputationContext ctx =
        context == null ? Klab.INSTANCE.getRuntimeProvider().createRuntimeContext()
            : ((Subject) context).getRoot().getRuntimeContext();

    /*
     * 2. Children at the dataflow level run in parallel, so have the runtime start futures for each
     * child and chain the results when they come.
     */
    IArtifact ret = null;
    for (IActuator actuator : actuators) {
      try {

        IObservationData data =
            Klab.INSTANCE.getRuntimeProvider().compute(actuator, ctx, monitor).get();
        
        IObservation artifact = Observations.INSTANCE.createObservation(((Actuator) actuator).getObservable(),
            actuator.getScale(), data, ((Actuator) actuator).getNamespace(), monitor, context);

        if (ret == null) {
          ret = artifact;
        } else {
          ((Artifact) ret).chain(ret);
        }
      } catch (InterruptedException e) {
        return null;
      } catch (ExecutionException e) {
        throw new KlabContextualizationException(e);
      }
    }
    return (T) ret;
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
      ret += ((Actuator) actuator).encode(offset) + "\n";
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
