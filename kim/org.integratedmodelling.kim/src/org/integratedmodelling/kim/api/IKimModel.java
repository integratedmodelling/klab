package org.integratedmodelling.kim.api;

import java.util.List;
import java.util.Optional;

import org.integratedmodelling.kim.model.KimObservable;

public interface IKimModel extends IKimActiveStatement {

    public static enum Type {
        SEMANTIC,
        NUMBER,
        TEXT,
        BOOLEAN
    }

    Optional<IKimConcept> getReinterpretingRole();

    List<KimObservable> getDependencies();

    List<KimObservable> getObservables();

    boolean isPrivate();

    Type getType();

    Optional<String> getResourceUrn();

    boolean isAssessmentModel();

    boolean isLearningModel();

    boolean isInterpreter();

    boolean isAbstract();

    boolean isInactive();

    boolean isInstantiator();

    String getName();

    /**
     * 
     * @return
     */
    Optional<Object> getInlineValue();

    /**
     * 
     * @return
     */
    Optional<IServiceCall> getResourceFunction();
    
    /**
     * Contextualizer or processor(s) given after 'using'
     * @return computables or an empty list
     */
    List<IComputableResource> getContextualization();
}
