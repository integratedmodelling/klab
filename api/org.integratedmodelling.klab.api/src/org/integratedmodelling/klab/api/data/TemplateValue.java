package org.integratedmodelling.klab.api.data;

import org.integratedmodelling.kim.api.IParameters;

/**
 * Incarnation of any %id% used in k.IM. Can be used automatically through the Parameters object
 * after reconfiguring it to use a map of template values.
 * 
 * @author Ferd
 *
 */
public class TemplateValue {

    String name;

    public TemplateValue(String name) {
        this.name = name;
        if (name.startsWith("%") && name.endsWith("%")) {
            this.name = this.name.substring(1, this.name.length() - 1);
        }
    }

    public String getName() {
        return name;
    }

    public Object getValue(IParameters<?> bindings) {
        if (bindings.containsKey(name)) {
            return bindings.get(name);
        }
        return name;
    }
}
