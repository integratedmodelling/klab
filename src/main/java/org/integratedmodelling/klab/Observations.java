package org.integratedmodelling.klab;

import java.lang.reflect.Constructor;
import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.klab.api.data.raw.IObjectData;
import org.integratedmodelling.klab.api.data.raw.IObservationData;
import org.integratedmodelling.klab.api.data.raw.IStorage;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.INamespace;
import org.integratedmodelling.klab.api.model.IObserver;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.ISubject;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.api.services.IObservationService;
import org.integratedmodelling.klab.engine.Engine.Monitor;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabInternalErrorException;
import org.integratedmodelling.klab.model.Namespace;
import org.integratedmodelling.klab.observation.DirectObservation;
import org.integratedmodelling.klab.observation.Event;
import org.integratedmodelling.klab.observation.Process;
import org.integratedmodelling.klab.observation.Relationship;
import org.integratedmodelling.klab.observation.Scale;
import org.integratedmodelling.klab.observation.State;
import org.integratedmodelling.klab.observation.Subject;
import org.integratedmodelling.klab.owl.Observable;
import org.integratedmodelling.klab.utils.IntelligentMap;

public enum Observations implements IObservationService {

  INSTANCE;

  IntelligentMap<Class<? extends ISubject>> subjectClasses = new IntelligentMap<>();

  /**
   * Record a Subject class to use to instantiate a specific type of subject.
   * 
   * @param concept
   * @param cls
   */
  public void registerSubjectClass(String concept, Class<? extends ISubject> cls) {
    subjectClasses.put(Observables.INSTANCE.declare(concept), cls);
  }

  @Override
  public void releaseNamespace(INamespace namespace, IMonitor monitor) throws KlabException {
    // TODO remove all artifacts from local kbox
  }

  @Override
  public void index(IObserver observer, IMonitor monitor) throws KlabException {
    // TODO
  }

//  @Override
//  public Subject createSubject(IObserver observer, IMonitor monitor) throws KlabException {
//
//    Subject result;
//    Constructor<?> constructor;
//
//    Class<?> agentClass = subjectClasses.get(observer.getObservable().getType());
//
//    if (agentClass != null) {
//      try {
//        constructor = agentClass.getConstructor(String.class, IObservable.class, IScale.class,
//            IMonitor.class);
//      } catch (Exception e) {
//        throw new KlabInternalErrorException(
//            "No viable constructor found for Java class '" + agentClass.getCanonicalName()
//                + "' for agent type '" + observer.getObservable().getLocalName() + "'");
//      }
//
//      try {
//        result = (Subject) constructor.newInstance(observer.getName(), observer.getObservable(),
//            Scale.create(observer.getBehavior().getExtents(monitor)), monitor);
//      } catch (Exception e) {
//        throw new KlabInternalErrorException(
//            "Unable to generate new instance of Java class '" + agentClass.getCanonicalName()
//                + "' for agent type '" + observer.getObservable().getLocalName() + "'");
//      }
//    } else {
//
//      result = Subject.create(observer.getName(), (Observable) observer.getObservable(),
//          Scale.create(observer.getBehavior().getExtents(monitor)), monitor);
//
//    }
//
//    result.setNamespace(observer.getNamespace());
//
//    return result;
//  }

  public Process createProcess(Observable observable, String name, Scale scale, IObjectData data, Namespace namespace,
      Monitor monitor, Subject context) throws KlabException {
    Process result = Process.create(name, observable, scale, data, context, monitor);
    result.setNamespace(namespace);
    return result;
  }

  public Event createEvent(Observable observable, String name, Scale scale, IObjectData data, Namespace namespace,
      Monitor monitor, Subject context) throws KlabException {
    Event result = Event.create(name, observable, scale, data, context, monitor);
    result.setNamespace(namespace);
    return result;
  }

  public State createState(Observable observable, Scale scale, IStorage<?> data, Namespace namespace, Monitor monitor,
      Subject context) throws KlabException {
    State result = State.create(observable, scale, data, context, monitor);
    result.setNamespace(namespace);
    return result;
  }

  public Configuration createConfiguration(Observable observable, String name, Scale scale,
      Namespace namespace, Monitor monitor, DirectObservation context) throws KlabException {
    return null;
  }

  public Relationship createRelationship(Observable observable, String name, Scale scale,
      Namespace namespace, Monitor monitor, DirectObservation context, Subject source,
      Subject target) throws KlabException {
    return null;
  }

  public Subject createSubject(Observable observable, String name, Scale scale, IObjectData data, INamespace namespace,
      IMonitor monitor, DirectObservation context) throws KlabException {

    Subject result;
    Constructor<?> constructor;

    Class<?> agentClass = subjectClasses.get(observable.getType());

    if (agentClass != null) {
      try {
        constructor = agentClass.getConstructor(String.class, IObservable.class, IScale.class,
            IMonitor.class);
      } catch (Exception e) {
        throw new KlabInternalErrorException(
            "No viable constructor found for Java class '" + agentClass.getCanonicalName()
                + "' for agent type '" + observable.getLocalName() + "'");
      }

      try {
        result = (Subject) constructor.newInstance(name, observable, scale, monitor);
      } catch (Exception e) {
        throw new KlabInternalErrorException(
            "Unable to generate new instance of Java class '" + agentClass.getCanonicalName()
                + "' for agent type '" + observable.getLocalName() + "'");
      }
    } else {
      result = Subject.create(name, observable, scale, data, monitor);
    }

    if (context != null) {
      result.setContextObservation(context);
    }
    result.setNamespace(namespace);

    return result;
  }

  public IObservation createObservation(IObservable observable, IScale scale, IObservationData data, INamespace namespace,
      IMonitor monitor, IDirectObservation context) throws KlabException {
    IObservation ret = null;
    if (observable.is(Type.SUBJECT)) {
      ret = createSubject((Observable) observable, observable.getLocalName(), (Scale) scale, (IObjectData)data,
          (Namespace)namespace, (Monitor) monitor, (DirectObservation) context);
    } else if (observable.is(Type.EVENT)) {
      ret = createEvent((Observable) observable, observable.getLocalName(), (Scale) scale, (IObjectData)data,
          (Namespace)namespace, (Monitor) monitor, (Subject) context);
    } else if (observable.is(Type.PROCESS)) {
      ret = createProcess((Observable) observable, observable.getLocalName(), (Scale) scale, (IObjectData)data,
          (Namespace)namespace, (Monitor) monitor, (Subject) context);
    } else if (observable.is(Type.RELATIONSHIP)) {

    } else if (observable.is(Type.QUALITY)) {
      ret = createState((Observable) observable, (Scale) scale, (IStorage<?>)data,
          (Namespace)namespace, (Monitor) monitor, (Subject) context);
    } else if (observable.is(Type.CONFIGURATION)) {

    }
    return ret;
  }

  /*
   * Non-API - sync namespace. TODO check equivalent in Models.
   */
  public void registerNamespace(Namespace ns, Monitor monitor) {
    // TODO Auto-generated method stub

  }
}
