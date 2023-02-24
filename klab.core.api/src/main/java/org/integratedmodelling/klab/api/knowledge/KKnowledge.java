package org.integratedmodelling.klab.api.knowledge;

public interface KKnowledge {

    /**
     * Anything that represents knowledge must return a stable, unique identifier that can be
     * resolved back to the original or to an identical object. Only {@link KResource} must use
     * proper URN syntax; for other types of knowledge may use expressions or paths.
     * 
     * @return the unique identifier that specifies this.
     */
    public String getUrn();
}
