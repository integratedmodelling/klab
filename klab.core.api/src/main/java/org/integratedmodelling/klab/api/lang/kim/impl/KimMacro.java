package org.integratedmodelling.klab.api.lang.kim.impl;

import java.util.Collection;

import org.integratedmodelling.klab.api.lang.kim.KKimMacro;

/**
 * A macro carries around the macro definition as a concept statement, acting as a 
 * delegate for it, in addition to the results of validation; an instance of a 
 * macro is passed to the generator of IKimConcept to turn a declaration into 
 * its substituted form.
 * 
 * @author ferdinando.villa
 */
public class KimMacro extends KimConceptStatement implements KKimMacro {

    private static final long serialVersionUID = 1961293561311544502L;

    @Override
    public boolean isEmpty() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Collection<Field> getFields() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public FieldType getType(Field field) {
        // TODO Auto-generated method stub
        return null;
    }

}
