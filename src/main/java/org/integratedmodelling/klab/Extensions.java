package org.integratedmodelling.klab;

import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.integratedmodelling.kdl.api.IKdlActuator;
import org.integratedmodelling.kdl.api.IKdlDataflow;
import org.integratedmodelling.kim.api.IKimFunctionCall;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.extensions.Component;
import org.integratedmodelling.klab.api.extensions.ResourceAdapter;
import org.integratedmodelling.klab.api.extensions.component.IComponent;
import org.integratedmodelling.klab.api.model.contextualization.IContextualizer;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.api.services.IExtensionService;
import org.integratedmodelling.klab.common.services.Prototype;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabInternalErrorException;
import org.integratedmodelling.klab.exceptions.KlabResourceNotFoundException;
import org.integratedmodelling.klab.exceptions.KlabRuntimeException;
import org.integratedmodelling.klab.utils.StringUtils;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;

public enum Extensions implements IExtensionService {

  INSTANCE;

  Map<String, IComponent> components = new HashMap<>();
  Map<String, Prototype>  prototypes = new HashMap<>();

  public void registerPrototype(org.integratedmodelling.klab.api.extensions.Prototype annotation,
      Class<?> cls) {

    // TODO Auto-generated method stub
    /*
     * Class must be a 'callable' object - contextualizer etc
     */
  }

  @Override
  public Collection<IComponent> getComponents() {
    return components.values();
  }

  @Override
  public org.integratedmodelling.klab.engine.extensions.Component getComponent(String componentId) {
    return (org.integratedmodelling.klab.engine.extensions.Component) components.get(componentId);
  }

  @Override
  public Prototype getServicePrototype(String service) {
    return prototypes.get(service);
  }

  public org.integratedmodelling.klab.engine.extensions.Component registerComponent(
      Component annotation, Class<?> cls) {

    org.integratedmodelling.klab.engine.extensions.Component ret =
        new org.integratedmodelling.klab.engine.extensions.Component(annotation, cls);

    System.out.println(StringUtils.repeat('-', 80));
    System.out.println("* COMPONENT " + ret.getName());
    System.out.println(StringUtils.repeat('-', 80) + "\n");
    System.out.println("* Services");
    System.out.println(StringUtils.repeat('-', 80));

    /*
     * TODO store knowledge for later processing
     */

    /*
     * ingest all .kdl files in the component's path
     */
    for (String kdl : new Reflections(cls.getPackage().getName(), new ResourcesScanner())
        .getResources(Pattern.compile(".*\\.kdl"))) {
      try (InputStream input = cls.getClassLoader().getResourceAsStream(kdl)) {
        declareServices(ret, Dataflows.INSTANCE.declare(input));
      } catch (Exception e) {
        throw new KlabRuntimeException(e);
      }
    }

    this.components.put(annotation.id(), ret);
    
    return ret;
  }

  @Override
  public Object callFunction(IKimFunctionCall functionCall, IMonitor monitor) throws KlabException {

    Object ret = null;

    Prototype prototype = getServicePrototype(functionCall.getName());
    if (prototype == null) {
      throw new KlabResourceNotFoundException(
          "cannot find function implementation for " + functionCall.getName());
    }
    
    Class<?> cls = prototype.getExecutorClass();
    
    if (cls != null) {
      if (IExpression.class.isAssignableFrom(cls)) {
        try {
          IExpression expr = (IExpression)cls.getDeclaredConstructor().newInstance();
          ret = expr.eval(functionCall.getParameters(), monitor);
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
            | InvocationTargetException | NoSuchMethodException | SecurityException e) {
          throw new KlabInternalErrorException(e);
        }
      } else if (IContextualizer.class.isAssignableFrom(cls)) {
        try {
          ret = cls.getDeclaredConstructor().newInstance();
          // TODO initialize with the parameters and monitor
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
            | InvocationTargetException | NoSuchMethodException | SecurityException e) {
          throw new KlabInternalErrorException(e);
        }
      }
    }
    
    return ret;
  }

  private void declareServices(org.integratedmodelling.klab.engine.extensions.Component component,
      IKdlDataflow declaration) {

    String namespace = declaration.getPackageName();
    for (IKdlActuator actuator : declaration.getActuators()) {
      Prototype prototype = new Prototype(actuator, namespace);
      component.addService(prototype);
      prototypes.put(prototype.getName(), prototype);

      System.out.println(StringUtils.repeat('-', 80));
      System.out.println(prototype.getSynopsis());
    }
  }

  public void registerResourceAdapter(ResourceAdapter annotation, Class<?> cls) {
    // TODO Auto-generated method stub
    /*
     * class must be a IResourceAdapter
     */
  }

}
