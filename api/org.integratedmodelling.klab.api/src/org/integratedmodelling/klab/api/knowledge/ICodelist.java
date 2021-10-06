package org.integratedmodelling.klab.api.knowledge;

import java.util.Collection;

import org.integratedmodelling.klab.api.provenance.IArtifact;

/**
 * The non-semantic version of an authority; used to match statistical codelists that haven't yet
 * made into classifications worth of being an officially endorsed authority, through a dedicated
 * authority builder. Codelists can be included in and referenced from resources.
 * 
 * @author Ferd
 *
 */
public interface ICodelist<K, T> {

    /**
     * 
     * @return
     */
    String getName();

    /**
     * 
     * @return
     */
    String getDescription();

    /**
     * If not null, specifies the authority this either incarnates (if {@link #isAuthority()}) or
     * maps to.
     * 
     * @return
     */
    String getAuthorityId();

    /**
     * If true, the codelist is exposed as an authority, referenceable through the URN of the
     * resource containing it. 
     * 
     * @return
     */
    boolean isAuthority();

    /**
     * If the codelist depends on a worldview, return true. Worldview-dependent codelists should be
     * as few as possible and ideally should not exist.
     * 
     * @return
     */
    String getWorldview();

    /**
     * The type corresponding to the mapped value. Must agree with T.
     * 
     * @return
     */
    IArtifact.Type getType();

    /**
     * 
     * @param key
     * @return
     */
    T value(K key);

    /**
     * 
     * @param value
     * @return
     */
    K key(T value);
    
    /**
     * 
     * @param key
     * @return
     */
    Collection<T> values(K key);

    /**
     * 
     * @param value
     * @return
     */
    Collection<K> keys(T value);

}
