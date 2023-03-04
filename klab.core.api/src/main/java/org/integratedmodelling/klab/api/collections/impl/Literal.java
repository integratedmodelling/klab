package org.integratedmodelling.klab.api.collections.impl;

import org.integratedmodelling.klab.api.collections.KLiteral;
import org.integratedmodelling.klab.api.data.ValueType;
import org.integratedmodelling.klab.api.lang.kim.KKimConcept;
import org.integratedmodelling.klab.api.lang.kim.KKimObservable;

public class Literal implements KLiteral {

    private static final long serialVersionUID = -4099902319334878080L;

    private ValueType valueType;
    private Object value;

    /**
     * Classifies and returns a literal for the passed object.
     * 
     * @param o
     * @return
     */
    public static Literal of(Object o) {
        Literal ret = new Literal();
        ret.value = o;
        ret.valueType = classifyLiteral(o);
        return ret;
    }

    private static ValueType classifyLiteral(Object o) {
        if (o instanceof Number) {
            return ValueType.NUMBER;
        } else if (o instanceof String) {
            return ValueType.STRING;
        } else if (o instanceof Boolean) {
            return ValueType.BOOLEAN;
        } else if (o instanceof Range) {
            return ValueType.RANGE;
        } else if (o instanceof KKimObservable) {
            return ValueType.OBSERVABLE;
        } else if (o instanceof KKimConcept) {
            return ValueType.CONCEPT;
        } // TODO continue
        return null;
    }

    @Override
    public ValueType getValueType() {
        return valueType;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T get(Class<? extends T> valueClass) {
        return (T) value;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public void setValueType(ValueType valueType) {
        this.valueType = valueType;
    }

}
