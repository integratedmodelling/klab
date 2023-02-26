package org.integratedmodelling.klab.api.knowledge;

import java.io.Serializable;

/**
 * All knowledge in k.LAB has a URN and is serializable. Methods in derived classes only use the
 * <code>getXxxx</code> naming pattern for serializable fields, to ensure easy serialization to JSON
 * and the like; everything else is expected to be handled through the reasoner service, with optional
 * caching if latency is significant.
 * 
 * @author ferd
 *
 */
public interface KKnowledge extends Serializable {

    /**
     * Anything that represents knowledge must return a stable, unique identifier that can be
     * resolved back to the original or to an identical object. Only {@link KResource} must use
     * proper URN syntax; for other types of knowledge may use expressions or paths.
     * 
     * @return the unique identifier that specifies this.
     */
    public String getUrn();
}
