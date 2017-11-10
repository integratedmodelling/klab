package org.integratedmodelling.klab.engine;

import java.lang.annotation.Annotation;

import org.integratedmodelling.klab.Extensions;
import org.integratedmodelling.klab.Klab;
import org.integratedmodelling.klab.api.auth.IServerIdentity;
import org.integratedmodelling.klab.api.extensions.Component;
import org.integratedmodelling.klab.api.extensions.Prototype;
import org.integratedmodelling.klab.api.services.IRuntimeService.AnnotationHandler;
import org.integratedmodelling.klab.exceptions.KlabException;

public abstract class Server implements IServerIdentity {

    protected void registerCommonAnnotations() {

        Klab.INSTANCE.registerAnnotationHandler(Prototype.class, new AnnotationHandler() {
            @Override
            public void processAnnotatedClass(Annotation annotation, Class<?> cls) {
                Extensions.INSTANCE.registerPrototype((Prototype) annotation, cls);
            }
        });

        Klab.INSTANCE.registerAnnotationHandler(Component.class, new AnnotationHandler() {
            @Override
            public void processAnnotatedClass(Annotation annotation, Class<?> cls) throws KlabException {
                Extensions.INSTANCE.registerComponent((Component) annotation, cls);
                // do nothing but do record the class and its occurrence.
                // if (KLAB.CMANAGER.getComponent(((Component) annotation).id())
                // == null)
                // {
                // KLAB.CMANAGER.register((Component) annotation, cls);
                //
                // /*
                // * in personal engines, no need to keep reallocating
                // connections in what
                // can
                // * potentially be a slow operation.
                // */
                // if (Engine.this instanceof ModelingEngine) {
                // ModelKbox.get().getDatabase().preallocateConnection();
                // ObservationKbox.get().getDatabase().preallocateConnection();
                // }
                //
                // KLAB.CMANAGER.link();
                //
                // /*
                // * in personal engines, no need to keep reallocating
                // connections in what
                // can
                // * potentially be a slow operation.
                // */
                // if (Engine.this instanceof ModelingEngine) {
                // ModelKbox.get().getDatabase().deallocateConnection();
                // ObservationKbox.get().getDatabase().deallocateConnection();
                // }
                // }
            }
        });
    }
}
