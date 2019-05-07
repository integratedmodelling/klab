package org.integratedmodelling.klab.api.runtime;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Tag a state contextualizer with this to ensure it does not get parallelized.
 * 
 * @author ferdinando.villa
 *
 */
@Retention(RUNTIME)
@Target(TYPE)
public @interface NonReentrant {

}
