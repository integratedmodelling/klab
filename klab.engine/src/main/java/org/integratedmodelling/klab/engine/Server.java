package org.integratedmodelling.klab.engine;

import java.lang.annotation.Annotation;

import org.integratedmodelling.klab.Extensions;
import org.integratedmodelling.klab.Klab;
import org.integratedmodelling.klab.Klab.AnnotationHandler;
import org.integratedmodelling.klab.api.auth.IServerIdentity;
import org.integratedmodelling.klab.api.extensions.Component;
import org.integratedmodelling.klab.api.extensions.ResourceAdapter;
import org.integratedmodelling.klab.exceptions.KlabException;

public abstract class Server implements IServerIdentity {

    protected void registerCommonAnnotations() {

        Klab.INSTANCE.registerAnnotationHandler(ResourceAdapter.class, new AnnotationHandler() {

            @Override
            public void processAnnotatedClass(Annotation annotation, Class<?> cls) throws KlabException {
                Extensions.INSTANCE.registerResourceAdapter((ResourceAdapter) annotation, cls);
            }
        });

        Klab.INSTANCE.registerAnnotationHandler(Component.class, new AnnotationHandler() {

            @Override
            public void processAnnotatedClass(Annotation annotation, Class<?> cls) throws KlabException {
                Extensions.INSTANCE.registerComponent((Component) annotation, cls);
            }
        });
    }
}
