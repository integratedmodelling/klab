package org.integratedmodelling.kim.api;

import java.util.EnumSet;

import org.integratedmodelling.kim.api.IKimConcept.Type;

public interface IConceptDescriptor {

    String getName();

    String getDocumentation();

    boolean isUndefined();

    IKimConceptStatement getMacro();

    boolean is(Type type);

    EnumSet<Type> getFlags();

}
