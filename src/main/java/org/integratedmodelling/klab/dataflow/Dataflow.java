package org.integratedmodelling.klab.dataflow;

import java.util.concurrent.ExecutionException;
import org.integratedmodelling.klab.Klab;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.Workspaces;
import org.integratedmodelling.klab.api.data.raw.IObservationData;
import org.integratedmodelling.klab.api.runtime.IComputationContext;
import org.integratedmodelling.klab.api.runtime.dataflow.IActuator;
import org.integratedmodelling.klab.api.runtime.dataflow.IDataflow;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.components.runtime.observations.DirectObservation;
import org.integratedmodelling.klab.components.runtime.observations.ObservationData;
import org.integratedmodelling.klab.components.runtime.observations.Subject;
import org.integratedmodelling.klab.exceptions.KlabContextualizationException;
import org.integratedmodelling.klab.exceptions.KlabException;

public class Dataflow extends Actuator implements IDataflow<IObservationData> {

  public Dataflow(IMonitor monitor) {
    super(monitor);
  }

  String                    description;
  private DirectObservation context;
  private double            coverage;

  @Override
  public IObservationData run(IMonitor monitor) throws KlabException {


    /*
     * Children at the dataflow level run in parallel, so have the runtime start futures for each
     * child and chain the results when they come.
     */
    IObservationData ret = null;
    for (IActuator actuator : actuators) {
      try {

        IObservationData data = Klab.INSTANCE.getRuntimeProvider().compute(actuator,
            context == null
                ? Klab.INSTANCE.getRuntimeProvider().createRuntimeContext(((Actuator)actuator).getObservable(), monitor)
                : ((Subject) context).getRuntimeContext().getChild(((Actuator)actuator).getObservable()))
            .get();

        if (ret == null) {
          ret = data;
        } else {
          ((ObservationData) ret).chain(data);
        }
      } catch (InterruptedException e) {
        return null;
      } catch (ExecutionException e) {
        throw new KlabContextualizationException(e);
      }
    }
    return ret;
  }

  @Override
  protected String encode(int offset) {

    String ret = "";

    if (offset == 0) {
      ret += "@klab " + Version.CURRENT + "\n";
      // UNCOMMENT IF SEMANTICS MUST BE OUTPUT BY ACTUATOR
      // ret += "@worldview " + Workspaces.INSTANCE.getWorldview().getName() + "\n";
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
