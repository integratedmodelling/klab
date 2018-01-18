package org.integratedmodelling.klab.api.extensions.component;

import java.io.File;
import java.util.Collection;

import org.integratedmodelling.klab.api.extensions.IPrototype;
import org.integratedmodelling.klab.api.knowledge.IProject;

public interface IComponent extends IProject {

    /**
     * Return any binary assets included in the component that must be
     * loaded explicitly after the component is created.
     * 
     * @return all loadable binary files.
     */
    Collection<File> getBinaryAssets();
    
    /**
     * The API, as a set of prototypes for all the services provided by this component.
     * 
     * @return all prototypes provided
     */
    Collection<IPrototype> getAPI();

    /**
     * Called to establish if the component has been properly initialized and is ready to be used.
     * 
     * @return true if usable.
     */
    boolean isActive();
}
