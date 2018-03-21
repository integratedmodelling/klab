package org.integratedmodelling.klab;

import org.integratedmodelling.klab.api.model.INamespace;
import org.integratedmodelling.klab.api.model.IObserver;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.api.services.IObservationService;
import org.integratedmodelling.klab.engine.Engine.Monitor;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.model.Namespace;

public enum Observations implements IObservationService {

  INSTANCE;

//  IntelligentMap<Class<? extends ISubject>> subjectClasses = new IntelligentMap<>();

//  /**
//   * Record a Subject class to use to instantiate a specific type of subject.
//   * 
//   * @param concept
//   * @param cls
//   */
//  public void registerSubjectClass(String concept, Class<? extends ISubject> cls) {
//    subjectClasses.put(Observables.INSTANCE.declare(concept), cls);
//  }

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

//  public Subject createSubject(Observable observable, String name, Scale scale, IObjectData data, INamespace namespace,
//      IMonitor monitor, DirectObservation context) throws KlabException {
//
//    Subject result;
//    Constructor<?> constructor;
//
//    Class<?> agentClass = subjectClasses.get(observable.getType());
//
//    if (agentClass != null) {
//      try {
//        constructor = agentClass.getConstructor(String.class, IObservable.class, IScale.class,
//            IMonitor.class);
//      } catch (Exception e) {
//        throw new KlabInternalErrorException(
//            "No viable constructor found for Java class '" + agentClass.getCanonicalName()
//                + "' for agent type '" + observable.getLocalName() + "'");
//      }
//
//      try {
//        result = (Subject) constructor.newInstance(name, observable, scale, monitor);
//      } catch (Exception e) {
//        throw new KlabInternalErrorException(
//            "Unable to generate new instance of Java class '" + agentClass.getCanonicalName()
//                + "' for agent type '" + observable.getLocalName() + "'");
//      }
//    } else {
//      result = Subject.create(name, observable, scale, data, monitor);
//    }
//
//    if (context != null) {
//      result.setContext(context);
//    }
//    result.setNamespace(namespace);
//
//    return result;
//  }


  /*
   * Non-API - sync namespace. TODO check equivalent in Models.
   */
  public void registerNamespace(Namespace ns, Monitor monitor) {
    // TODO Auto-generated method stub

  }
}
