package org.integratedmodelling.klab.api.services;

import java.lang.annotation.Annotation;
import java.util.List;

import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.utils.Pair;

/**
 * FIXME review API - this is exposing all the wrong things.
 * 
 * @author ferdinando.villa
 *
 */
public interface IRuntimeService {

    /**
     * Handler to process classes with k.LAB annotations. Register using
     * {@link #registerAnnotationHandler(Class, AnnotationHandler)}; the handlers will
     * be called for each matching class when {@link #scanPackage(String)} is called.
     * 
     * @author ferdinando.villa
     *
     */
    public interface AnnotationHandler {
        void processAnnotatedClass(Annotation annotation, Class<?> cls) throws KlabException;
    }

    void info(Object... o);

    void warn(Object... o);

    void error(Object... o);

    void debug(Object... o);

}
