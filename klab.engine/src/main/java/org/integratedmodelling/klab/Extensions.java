package org.integratedmodelling.klab;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import org.integratedmodelling.kdl.api.IKdlActuator;
import org.integratedmodelling.kdl.api.IKdlDataflow;
import org.integratedmodelling.kim.api.IPrototype;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.klab.api.data.adapters.IResourceAdapter;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.extensions.Component;
import org.integratedmodelling.klab.api.extensions.ILanguageProcessor;
import org.integratedmodelling.klab.api.extensions.ResourceAdapter;
import org.integratedmodelling.klab.api.extensions.component.IComponent;
import org.integratedmodelling.klab.api.model.contextualization.IContextualizer;
import org.integratedmodelling.klab.api.runtime.IComputationContext;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.api.services.IExtensionService;
import org.integratedmodelling.klab.engine.runtime.code.Expression;
import org.integratedmodelling.klab.engine.runtime.code.groovy.GroovyProcessor;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabInternalErrorException;
import org.integratedmodelling.klab.exceptions.KlabResourceNotFoundException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.kim.Prototype;
import org.integratedmodelling.klab.utils.Path;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

public enum Extensions implements IExtensionService {

    INSTANCE;

    public static final String DEFAULT_EXPRESSION_LANGUAGE = "groovy";

    Map<String, IComponent> components = Collections.synchronizedMap(new HashMap<>());
    Map<String, Prototype> prototypes = Collections.synchronizedMap(new HashMap<>());

    @Override
    public Collection<IComponent> getComponents() {
        return components.values();
    }

    @Override
    public org.integratedmodelling.klab.engine.extensions.Component getComponent(String componentId) {
        return (org.integratedmodelling.klab.engine.extensions.Component) components.get(componentId);
    }

    @Override
    public Prototype getPrototype(String service) {
        return prototypes.get(service);
    }

    public org.integratedmodelling.klab.engine.extensions.Component registerComponent(Component annotation,
            Class<?> cls) {

        org.integratedmodelling.klab.engine.extensions.Component ret = new org.integratedmodelling.klab.engine.extensions.Component(
                annotation, cls);

        Logging.INSTANCE.info("Registering component " + ret.getName() + " version " + ret.getVersion());

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
            } catch (Throwable e) {
                throw new KlabValidationException(e);
            }
        }

        this.components.put(annotation.id(), ret);

        return ret;
    }

    @Override
    public Object callFunction(IServiceCall functionCall, IMonitor monitor) throws KlabException {
        return callFunction(functionCall, Expression.emptyContext(monitor));
    }

    public Object callFunction(IServiceCall functionCall, IComputationContext context) throws KlabException {

        Object ret = null;

        Prototype prototype = getPrototype(functionCall.getName());
        if (prototype == null) {
            throw new KlabResourceNotFoundException(
                    "cannot find function implementation for " + functionCall.getName());
        }

        Class<?> cls = prototype.getExecutorClass();

        if (cls != null) {
            if (IExpression.class.isAssignableFrom(cls)) {
                try {
                    IExpression expr = (IExpression) cls.getDeclaredConstructor().newInstance();
                    ret = expr.eval(functionCall.getParameters(), context);
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
        }
    }

    public void registerResourceAdapter(ResourceAdapter annotation, Class<?> cls) throws KlabException {
        /*
         * class must be a IResourceAdapter
         */
        if (IResourceAdapter.class.isAssignableFrom(cls)) {
            try {
                IResourceAdapter adapter = (IResourceAdapter) cls.getDeclaredConstructor().newInstance();
                Resources.INSTANCE.registerResourceAdapter(annotation.type(), adapter);
            } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
                    | InvocationTargetException | NoSuchMethodException | SecurityException e) {
                throw new KlabInternalErrorException(e);
            }
            Logging.INSTANCE
                    .info("Registered resource adapter " + cls.getCanonicalName() + " for '" + annotation.type() + "'");
        } else {
            throw new KlabValidationException(
                    annotation.type() + ": resource adapter annotations must be used on IResourceAdapter classes");
        }
    }

    public void validateArguments(IPrototype prototype, Map<String, Object> arguments) {
        // TODO
    }

    public void exportPrototypes(File file) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JavaType type = mapper.getTypeFactory().constructMapLikeType(Map.class, String.class, Prototype.class);
            mapper.writerFor(type).writeValue(file, this.prototypes);
        } catch (IOException e) {
            Logging.INSTANCE.error(e);
        }
    }

    /**
     * TODO! These are the Groovy/expr extension packages.
     * 
     * @return all the Kim imports
     */
    public Collection<Class<?>> getKimImports() {
        List<Class<?>> ret = new ArrayList<>();
        return ret;
    }

    public IExpression compileExpression(String expressionCode, String language) throws KlabValidationException {
        return getLanguageProcessor(language).compile(expressionCode, null);
    }

    public IExpression compileExpression(String expressionCode, IComputationContext context, String language)
            throws KlabValidationException {
        return getLanguageProcessor(language).compile(expressionCode, context);
    }

    public ILanguageProcessor getLanguageProcessor(String language) {
        // TODO
        return (language == null || language.equals(DEFAULT_EXPRESSION_LANGUAGE)) ? GroovyProcessor.INSTANCE : null;
    }

}
