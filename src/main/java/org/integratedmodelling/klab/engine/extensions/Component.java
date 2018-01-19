package org.integratedmodelling.klab.engine.extensions;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.extensions.IPrototype;
import org.integratedmodelling.klab.api.extensions.component.IComponent;
import org.integratedmodelling.klab.api.knowledge.IProject;
import org.integratedmodelling.klab.api.model.INamespace;

public class Component implements IComponent {

    String name;
    boolean active;
    Version version;
    Map<String, IPrototype> services = new HashMap<>();
    
    public Component() {
        // TODO Auto-generated constructor stub
    }

    public Component(org.integratedmodelling.klab.api.extensions.Component annotation, Class<?> cls) {
        
        this.name = annotation.id();
        this.version = Version.parse(annotation.version());

        // TODO scan methods and exec/store setup and initialization
    }

    @Override
    public Collection<File> getBinaryAssets() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<IPrototype> getAPI() {
        return services.values();
    }

    @Override
    public boolean isActive() {
        return active;
    }

    public void addService(IPrototype prototype) {
        services.put(prototype.getName(), prototype);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public File getRoot() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<INamespace> getNamespaces() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<IProject> getPrerequisites() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Version getVersion() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public INamespace getUserKnowledge() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isCanonical() {
        // TODO Auto-generated method stub
        return false;
    }

}
