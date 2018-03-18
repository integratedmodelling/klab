package org.integratedmodelling.klab.observation;

import java.util.Collection;
import java.util.Map;
import org.integratedmodelling.kim.utils.CollectionUtils;
import org.integratedmodelling.klab.api.data.raw.IObjectData;
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
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.engine.runtime.ObserveInContextTask;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeContext;
import org.integratedmodelling.klab.owl.Observable;

public class Subject extends CountableObservation implements ISubject {

  private IRuntimeContext runtimeContext;

  private Subject(String name, Observable observable, Scale scale, IObjectData data, IMonitor monitor) {
    super(name, observable, scale, data, monitor);
  }

  private static final long serialVersionUID = 2466999232658613114L;

  /**
   * 
   * @param name
   * @param observable
   * @param scale
   * @param data 
   * @param monitor 
   * @return
   */
  public static Subject create(String name, Observable observable, Scale scale, IObjectData data, IMonitor monitor) {
    return new Subject(name, observable, scale, data, monitor);
  }
  
  /**
   * 
   * @param name
   * @param observable
   * @param scale
   * @param data 
   * @param context
   * @param monitor 
   * @return
   */
  public static Subject create(String name, Observable observable, Scale scale, IObjectData data, Subject context, IMonitor monitor) {
    Subject ret = new Subject(name, observable, scale, data, monitor);
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
  public ITask<IObservation> observe(String urn, String... scenarios) {
    return new ObserveInContextTask(this, urn, CollectionUtils.arrayToList(scenarios));
  }

  /**
   * Return the runtime context where all the 'live' bookkeeping is kept.
   * 
   * @return
   */
  public IRuntimeContext getRuntimeContext() {
    return runtimeContext;
  }

  public void setRuntimeContext(IRuntimeContext runtimeContext) {
    this.runtimeContext = runtimeContext;
  }

  @Override
  public Subject next() {
    return (Subject)getNext();
  }
}
