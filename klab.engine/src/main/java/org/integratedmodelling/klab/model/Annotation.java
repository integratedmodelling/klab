package org.integratedmodelling.klab.model;

import org.integratedmodelling.kim.api.IKimAnnotation;
import org.integratedmodelling.klab.api.model.IAnnotation;
import org.integratedmodelling.klab.kim.KimValidator;
import org.integratedmodelling.klab.utils.Parameters;

public class Annotation extends Parameters<Object> implements IAnnotation {

    String name;

    public Annotation(IKimAnnotation statement) {
        this.name = statement.getName();
        this.putAll(KimValidator.compileMapLiteral(statement.getParameters()));
    }

    @Override
    public String getName() {
        return name;
    }

}
