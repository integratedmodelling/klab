package org.integratedmodelling.klab.api.knowledge;

import java.util.Collection;

import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.exceptions.KlabIllegalStateException;

/**
 * The non-semantic version of an authority; used to match statistical codelists that haven't yet
 * made into classifications worth of being an officially endorsed authority, through a dedicated
 * authority builder. Codelists can be included in and referenced from resources. A codelist may be
 * promoted to an authority.
 * 
 * @author Ferd
 *
 */
public interface ICodelist<K, T> {

    /**
     * Name of codelist. Usually a string with agency/name/version.
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
     * maps to. If this is not null, {@link #getType()} must return CONCEPT.
     * 
     * @return
     */
    String getAuthorityId();

    /**
     * If true, the codelist is exposed as an authority, referenceable through the URN of the
     * resource containing it. There may still be an authority that this maps to without being an
     * authority itself.
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
     * Value corresponding to a key. If there are multiple values, this will throw a
     * {@link KlabIllegalStateException}.
     * 
     * @param key
     * @throws KlabIllegalStateException if key maps to multiple values.
     * @return
     */
    T value(K key);

    /**
     * Key correspondent to a value. Inverse mappings are always functional so there's no keys()
     * method.
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

}
