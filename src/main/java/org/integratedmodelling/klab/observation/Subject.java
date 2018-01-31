package org.integratedmodelling.klab.observation;

import java.util.Collection;
import java.util.Map;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IIndividual;
import org.integratedmodelling.klab.api.knowledge.IOntology;
import org.integratedmodelling.klab.api.observations.IConfiguration;
import org.integratedmodelling.klab.api.observations.IEvent;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.IProcess;
import org.integratedmodelling.klab.api.observations.IRelationship;
import org.integratedmodelling.klab.api.observations.ISubject;
import org.integratedmodelling.klab.api.runtime.ITask;
import org.integratedmodelling.klab.engine.runtime.ObserveInContextTask;
import org.integratedmodelling.klab.owl.Observable;
import org.integratedmodelling.klab.resolution.RuntimeContext;

public class Subject extends DirectObservation implements ISubject {

  private RuntimeContext runtimeContext;

  private Subject(String name, Observable observable, Scale scale) {
    super(name, observable, scale);
    // TODO Auto-generated constructor stub
  }

  private static final long serialVersionUID = 2466999232658613114L;

  /**
   * 
   * @param name
   * @param observable
   * @param scale
   * @return
   */
  public static Subject create(String name, Observable observable, Scale scale) {
    return new Subject(name, observable, scale);
  }
  
  /**
   * 
   * @param name
   * @param observable
   * @param scale
   * @param context
   * @return
   */
  public static Subject create(String name, Observable observable, Scale scale, Subject context) {
    Subject ret = new Subject(name, observable, scale);
    ret.setContextObservation(context);
    return ret;
  }

  @Override
  public Collection<IEvent> getEvents() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Collection<IProcess> getProcesses() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Collection<ISubject> getSubjects() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Collection<IRelationship> getRelationships() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Collection<IRelationship> getIncomingRelationships(ISubject subject) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Collection<IRelationship> getOutgoingRelationships(ISubject subject) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Map<IConcept, IConfiguration> getConfigurations() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public IIndividual instantiate(IOntology ontology) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ITask<IObservation> observe(String urn) {
    return new ObserveInContextTask(this, urn);
  }

  /**
   * Return the runtime context where all the 'live' bookkeeping is kept.
   * 
   * @return
   */
  public RuntimeContext getRuntimeContext() {
    return runtimeContext;
  }
  
}
