package org.integratedmodelling.klab.api.model;

import org.integratedmodelling.klab.api.knowledge.IMetadata;

/**
 * An observer is the k.LAB object corresponding to an <code>observe</code> statement
 * in k.IM.
 * 
 * @author Ferd
 *
 */
public interface IObserver extends IKimObject {

    /**
     * Metadata can be associated to models in k.IM.
     * 
     * @return metadata (never null).
     */
    IMetadata getMetadata();

    
}
