package org.integratedmodelling.klab.api.services;

import org.integratedmodelling.klab.api.extensions.Component;
import org.integratedmodelling.klab.api.extensions.Prototype;

public interface IExtensionService {

    void registerPrototype(Prototype annotation, Class<?> cls);

    void registerComponent(Component annotation, Class<?> cls);

}
