package org.integratedmodelling.klab.api.lang;

import org.integratedmodelling.klab.api.collections.IParameters;

/**
 * Annotation from k.IM code. Exposed as a {@link IParameters} object with a name. Does not preserve
 * the relationship with the k.IM, k.DL or k.Actors statement after construction.
 * 
 * @author Ferd
 *
 */
public interface IAnnotation extends IParameters<String> {

    /**
     * The name of the annotation.
     * 
     * @return
     */
    String getName();

    /**
     * Specialized get() that converts IKimConcepts left over as forward references into concepts at
     * the time of use. It could also parse IKimExpressions into IExpressions but this is not done
     * at this time.
     */
    Object getDeclared(Object key);

    /**
     * Typed version of getDeclared().
     * 
     * @param name
     * @param cls
     * @return
     */
    <K> K getDeclared(String name, Class<? extends K> cls);
}
