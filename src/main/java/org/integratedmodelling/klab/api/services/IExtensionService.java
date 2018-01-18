package org.integratedmodelling.klab.api.services;

import java.util.Collection;

import org.integratedmodelling.klab.api.extensions.IPrototype;
import org.integratedmodelling.klab.api.extensions.component.IComponent;

public interface IExtensionService {

    Collection<IComponent> getComponents();

    IComponent getComponent(String componentId);

    IPrototype getServicePrototype(String service);

}
