package org.integratedmodelling.kim.api;

import java.util.List;
import java.util.Optional;

public interface IKimModel extends IKimActiveStatement {

	public static enum Type {
		SEMANTIC, NUMBER, TEXT, BOOLEAN
	}

	Optional<IKimConcept> getReinterpretingRole();

	List<IKimObservable> getDependencies();

	List<IKimObservable> getObservables();

	Scope getScope();

	Type getType();

	List<String> getResourceUrns();

	boolean isLearningModel();

	boolean isInterpreter();

	boolean isAbstract();

	boolean isInactive();

	boolean isInstantiator();

	String getName();

	Optional<Object> getInlineValue();

	/**
	 * Contextualizer or processor(s) given after 'using'
	 * 
	 * @return computables or an empty list
	 */
	List<IContextualizable> getContextualization();

	String getDocstring();

	/**
	 * Normally true, it will return false in models that were expressed as
	 * non-semantic operations, using the 'number', 'text', etc. keywords. These are
	 * also, by default, private and are used only directly by name.
	 * 
	 * @return
	 */
	boolean isSemantic();
}
