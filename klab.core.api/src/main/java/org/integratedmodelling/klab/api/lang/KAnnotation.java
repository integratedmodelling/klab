package org.integratedmodelling.klab.api.lang;

import org.integratedmodelling.klab.api.collections.KParameters;

/**
 * Annotation from code, normally starting out as a @xxx annotation in a parsed language (all three
 * k.LAB languages support Java-style annotations) but it can also be used for other purposes. It's
 * essentially a named {@link KParameters} object with a name; a content class attribute is also
 * added to capture typed objects like in k.IM's <code>define</code> statements. Does not preserve
 * the relationship with the k.IM, k.DL or k.Actors statement after construction.
 * <p>
 * Serializes correctly only if with a Jackson object mapper instrumented with specialized
 * serializers.
 * 
 * @author Ferd
 *
 */
public interface KAnnotation extends KParameters<String> {

    public static final String VALUE_PARAMETER_KEY = "value";
    
    /**
     * The name of the annotation.
     * 
     * @return
     */
    String getName();

    String getContentClass();

    //
    // /**
    // * Specialized get() that converts IKimConcepts left over as forward references into concepts
    // at
    // * the time of use. It could also parse KExpressions into IExpressions but this is not done at
    // * this time.
    // */
    // Object getDeclared(Object key);
    //
    // /**
    // * Typed version of getDeclared().
    // *
    // * @param name
    // * @param cls
    // * @return
    // */
    // <K> K getDeclared(String name, Class<? extends K> cls);
}
