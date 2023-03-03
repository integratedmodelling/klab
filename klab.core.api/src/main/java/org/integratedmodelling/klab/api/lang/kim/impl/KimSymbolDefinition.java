package org.integratedmodelling.klab.api.lang.kim.impl;

import org.integratedmodelling.klab.api.collections.KLiteral;
import org.integratedmodelling.klab.api.lang.kim.KKimSymbolDefinition;

/**
 * The syntactic peer of a k.IM 'define' statement.
 * 
 * @author ferdinando.villa
 *
 */
public class KimSymbolDefinition extends KimStatement implements KKimSymbolDefinition {

    private static final long serialVersionUID = -3605295215543099841L;

    private String name;
    private String defineClass;
    private KLiteral value;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDefineClass() {
        return defineClass;
    }

    @Override
    public KLiteral getValue() {
        return value;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDefineClass(String defineClass) {
        this.defineClass = defineClass;
    }

    public void setValue(KLiteral value) {
        this.value = value;
    }

}
