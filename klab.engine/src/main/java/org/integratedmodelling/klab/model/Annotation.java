package org.integratedmodelling.klab.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.integratedmodelling.kim.api.IKimAnnotation;
import org.integratedmodelling.klab.api.model.IAnnotation;
import org.integratedmodelling.klab.kim.KimValidator;
import org.integratedmodelling.klab.utils.NameGenerator;
import org.integratedmodelling.klab.utils.Parameters;

/**
 * An annotation is a simple parameter object with a unique ID so it can be recognized. 
 * This is used when interactive mode is used and parameters are changed by users.
 * 
 * @author Ferd
 *
 */
public class Annotation extends Parameters<Object> implements IAnnotation {

    String name;
    String id = "ann" + NameGenerator.shortUUID();
    Set<String> interactiveParameters = new HashSet<>();
    
    public Annotation(IKimAnnotation statement) {
        this.name = statement.getName();
        this.interactiveParameters.addAll(statement.getInteractiveParameters());
        this.putAll(KimValidator.compileMapLiteral(statement.getParameters()));
    }

    @Override
    public String getName() {
        return name;
    }

    /**
     * Return the unique ID of this annotation.
     * 
     * @return
     */
    public String getId() {
        return id;
    }

    /**
     * Any parameters that use the ?= syntax
     * @return
     */
    public Collection<String> getInteractiveParameters() {
        return interactiveParameters;
    }

}
