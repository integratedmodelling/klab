package org.integratedmodelling.klab.api.services;

import java.lang.annotation.Annotation;
import java.util.List;

import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.utils.Pair;

public interface IRuntimeService {

    /**
     * Handler to process classes with k.LAB annotations. Register using
     * {@link #registerAnnotationHandler(Class<? extends Annotation>, AnnotationHandler)}; the handlers will
     * be called for each matching class when {@link scanPackage(String)} is called.
     * 
     * @author ferdinando.villa
     *
     */
    public interface AnnotationHandler {
        void processAnnotatedClass(Annotation annotation, Class<?> cls) throws KlabException;
    }

    void info(Object o);

    void warn(Object o);

    void error(Object o);

    void debug(Object o);

    void warn(String string, Throwable e);

    void error(String string, Throwable e);

    /**
     * Scan a package and its subpackages for any class annotations whose handlers have been registered.
     * 
     * @param packageId
     * @return
     * @throws KlabException
     */
    List<Pair<Annotation, Class<?>>> scanPackage(String packageId) throws KlabException;

    /**
     * Register a class annotation and its handler for processing when {@link #scanPackage(String)} is called.
     * 
     * @param annotationClass
     * @param handler
     */
    void registerAnnotationHandler(Class<? extends Annotation> annotationClass, AnnotationHandler handler);

    /**
     * Register a class as a k.IM toolkit, providing extensions usable from Groovy or other expression
     * language.
     * 
     * @param cls
     */
    void registerKimToolkit(Class<?> cls);

}
