package org.integratedmodelling.klab.api.lang.kim.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.integratedmodelling.klab.api.lang.kim.KKimMacro;

/**
 * A macro carries around the macro definition as a concept statement, acting as a delegate for it,
 * in addition to the results of validation; an instance of a macro is passed to the generator of
 * IKimConcept to turn a declaration into its substituted form.
 * 
 * @author ferdinando.villa
 */
public class KimMacro extends KimConceptStatement implements KKimMacro {

    private static final long serialVersionUID = 1961293561311544502L;
    private boolean empty;
    private List<Field> fields = new ArrayList<>();

    @Override
    public boolean isEmpty() {
        return empty;
    }

    @Override
    public Collection<Field> getFields() {
        return fields;
    }

    @Override
    public FieldType typeOf(Field field) {
        // TODO
        return null;
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
    }

    public void setFields(List<Field> fields) {
        this.fields = fields;
    }

}
