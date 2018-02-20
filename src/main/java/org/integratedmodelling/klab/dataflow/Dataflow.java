package org.integratedmodelling.klab.dataflow;

import org.integratedmodelling.klab.Observations;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.dataflow.IDataflow;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.observation.DirectObservation;

public class Dataflow<T extends IArtifact> extends Actuator<T> implements IDataflow<T> {

  public Dataflow(Class<? extends T> cls) {
    super(cls);
  }

  String description;
  DirectObservation context;
  double coverage;

  @Override
  public T run(IMonitor monitor) throws KlabException {
    // TODO enough?
    return compute(context, monitor);
  }
  
  @SuppressWarnings("unchecked")
  public T compute(DirectObservation context, IMonitor monitor) throws KlabException {

      // TODO compute children in parallel; chain resulting artifacts
      
      return null;
  }

  @Override
  protected String encode(int offset) {

    String ret = "";
    
    if (offset == 0) {
      ret += "@klab " + Version.CURRENT + "\n";
      ret += "@dataflow " + name + "\n";
      ret += "@author 'k.LAB resolver " + creationTime + "'" + "\n";
      if (context != null) {
        ret += "@context " + context.getUrn() + "\n";
      }
      ret += "\n";
    }

    ret += super.encode(offset);
    
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
   * @param name
   * @param description 
   */
  public void setName(String name, String description) {
    this.name = name;
    this.description = description;
  }
  

}
