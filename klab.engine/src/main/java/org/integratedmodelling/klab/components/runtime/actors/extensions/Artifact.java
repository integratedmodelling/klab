package org.integratedmodelling.klab.components.runtime.actors.extensions;

import org.integratedmodelling.klab.api.provenance.IArtifact;

/**
 * Wrapper for artifacts 
 * 
 * @author Ferd
 *
 */
public class Artifact {

    IArtifact artifact;
    
    public Artifact(IArtifact artifact) {
        this.artifact = artifact;
    }

    public void setProperty(String key, Object value) {
        this.artifact.getMetadata().put(key, value);
    }

    public Object getProperty(String key) {
       switch (key) {
           default:
               break;
       }
       return artifact.getMetadata().get(key);
    }
}
