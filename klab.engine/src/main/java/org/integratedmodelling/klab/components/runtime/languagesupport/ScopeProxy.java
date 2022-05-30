package org.integratedmodelling.klab.components.runtime.languagesupport;

import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.components.runtime.RuntimeScope;

import groovy.lang.GroovyObjectSupport;

/**
 * The scope returned as observation.scope in Groovy expressions. Facilitates access to the runtime
 * scope. Currently making RuntimeScope a groovy object is not an option due to inheritance, and
 * this is slightly cleaner although it's only returned through an observation proxy that should be
 * eliminated.
 * 
 * @author Ferd
 *
 */
public class ScopeProxy extends GroovyObjectSupport {

    RuntimeScope scope;

    public ScopeProxy(IContextualizationScope scope) {
        this.scope = (RuntimeScope)scope;
    }

    @Override
    public Object getProperty(String propertyName) {
        switch (propertyName) {
        case "catalog":
            return scope.getCatalog();
        }
        return super.getProperty(propertyName);
    }

    
    
}
