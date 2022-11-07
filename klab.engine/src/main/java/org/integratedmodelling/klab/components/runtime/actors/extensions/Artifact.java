package org.integratedmodelling.klab.components.runtime.actors.extensions;

import org.integratedmodelling.klab.api.data.artifacts.IDataArtifact;
import org.integratedmodelling.klab.api.data.artifacts.IObjectArtifact;
import org.integratedmodelling.klab.api.provenance.IArtifact;

import groovy.lang.GroovyObjectSupport;

/**
 * Wrapper for artifacts
 * 
 * @author Ferd
 *
 */
public class Artifact extends GroovyObjectSupport {

    IArtifact artifact;

    public Artifact(IArtifact artifact) {
        this.artifact = artifact;
    }

    @Override
    public void setProperty(String key, Object value) {
        this.artifact.getMetadata().put(key, value);
    }

    @Override
    public Object getProperty(String key) {
        switch(key) {
        case "name":
            return artifact instanceof IObjectArtifact ? ((IObjectArtifact) artifact).getName() : artifact.getId();
        default:
            break;
        }
        return artifact.getMetadata().get(key);
    }

    public IObjectArtifact getObjectArtifact() {
        return artifact instanceof IObjectArtifact ? (IObjectArtifact) artifact : null;
    }

    public IDataArtifact getDataArtifact() {
        return artifact instanceof IDataArtifact ? (IDataArtifact) artifact : null;
    }

}
