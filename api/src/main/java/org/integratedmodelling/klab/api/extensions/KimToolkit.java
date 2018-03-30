package org.integratedmodelling.klab.api.extensions;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Use to tag a class that gets automatically imported in a Kim script. If the class exposes a 
 * static setBinding() method, that will be called with the current binding. Only static methods
 * will be supported.
 * 
 * @author Ferd
 *
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface KimToolkit {
    
}
