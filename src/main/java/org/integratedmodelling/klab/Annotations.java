package org.integratedmodelling.klab;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.integratedmodelling.kdl.api.IKdlActuator;
import org.integratedmodelling.kdl.api.IKdlDataflow;
import org.integratedmodelling.kim.api.IKimAnnotation;
import org.integratedmodelling.kim.api.IPrototype;
import org.integratedmodelling.kim.api.IPrototype.Type;
import org.integratedmodelling.kim.utils.Parameters;
import org.integratedmodelling.klab.api.model.IKimObject;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.api.services.IAnnotationService;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabInternalErrorException;
import org.integratedmodelling.klab.kim.Prototype;
import org.integratedmodelling.klab.utils.StringUtils;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

public enum Annotations implements IAnnotationService {

    INSTANCE;

    /**
     * To be implemented by the handlers of annotations mentioned in the corresponding prototypes. These are
     * executed on the corresponding objects after the entire namespace is loaded, in order of declaration.
     * 
     * @author ferdinando.villa
     *
     */
    public interface Handler {

        /**
         * Handle the passed object with the passed annotation arguments. Do not throw exceptions but use the
         * monitor for notifications.
         * 
         * @param target
         * @param arguments
         * @param monitor
         * @return any value deemed necessary. The return value is ignored for now, reserved for future
         *         applications.
         * @throws Exception 
         */
        Object process(IKimObject target, Parameters arguments, IMonitor monitor) throws Exception;
    }

    Map<String, Handler>    handlers   = Collections.synchronizedMap(new HashMap<>());
    Map<String, IPrototype> prototypes = Collections.synchronizedMap(new HashMap<>());

    @Override
    public IPrototype getPrototype(String annotation) {
        return prototypes.get(annotation);
    }

    @Override
    public Object process(IKimAnnotation annotation, IKimObject object, IMonitor monitor) {

        Handler handler = handlers.get(annotation.getName());
        if (handler != null) {
            try {
              return handler.process(object, annotation.getParameters(), monitor);
            } catch (Exception e) {
              monitor.error(e);
            }
        }
        return null;
    }

    public void declareServices(URL manifest) throws KlabException {

        IKdlDataflow declaration = Dataflows.INSTANCE.declare(manifest);

        String namespace = declaration.getPackageName();
        for (IKdlActuator actuator : declaration.getActuators()) {
            IPrototype prototype = new Prototype(actuator, namespace);
            prototypes.put(prototype.getName(), prototype);
            if (prototype.getType() != Type.ANNOTATION) {
                throw new KlabInternalErrorException("annotation prototype for "
                        + prototype.getName()
                        + " does not specify an annotation");
            } else if (prototype.getExecutorClass() != null) {
                try {
                    Object handler = prototype.getExecutorClass().getDeclaredConstructor().newInstance();
                    if (handler instanceof Handler) {
                        handlers.put(prototype.getName(), (Handler)handler);
                    } else {
                        throw new KlabInternalErrorException("error creating handler for "
                                + prototype.getName()
                                + ": handler is not an instance of Annotations.Handler");
                    }
                } catch (Exception e) {
                    throw new KlabInternalErrorException(e);
                }
            }

            System.out.println(StringUtils.repeat('-', 80));
            System.out.println(prototype.getSynopsis());
        }
    }
    
    public void exportPrototypes(File file) {
      try {
        ObjectMapper mapper = new ObjectMapper();
        JavaType type = mapper.getTypeFactory().constructMapLikeType(Map.class, String.class, Prototype.class);
        mapper.writerFor(type).writeValue(file, this.prototypes);
      } catch (IOException e) {
        Klab.INSTANCE.error(e);
      }
    }
    
}
