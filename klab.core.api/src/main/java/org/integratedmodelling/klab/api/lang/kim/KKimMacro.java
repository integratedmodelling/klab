package org.integratedmodelling.klab.api.lang.kim;

import java.util.Collection;
import java.util.Set;

import org.integratedmodelling.klab.api.knowledge.SemanticType;

/**
 * A macro carries around the macro definition as a concept statement, acting as a 
 * delegate for it, in addition to the results of validation; an instance of a 
 * macro is passed to the generator of IKimConcept to turn a declaration into 
 * its substituted form.
 * 
 * @author ferdinando.villa
 */
public interface KKimMacro extends KKimConceptStatement {

	public static enum Field {
		
		CONTEXT("context"),
		INHERENT("inherent"),
		COMPRESENT("compresent"),
		GOAL("goal"),
		CAUSANT("causant"),
		CAUSED("caused"),
		COOCCURRENT("cooccurrent"),
		ADJACENT("adjacent");

		String declaredName;
		
		Field(String name) {
			this.declaredName = name;
		}
				
	}
	
	interface FieldType {

        Set<SemanticType> getType();

//        IConceptDescriptor getDescriptor();

        boolean isOptional();
	    
	}
	
	/**
	 * Macro can be empty
	 * @return true if empty
	 */
	boolean isEmpty();
	
	/**
	 * All the fields mentioned in the macro for substitution.
	 * 
	 * @return all fields
	 */
	Collection<Field> getFields();

	/**
	 * Get the type and, if any, the concept descriptor to which the passed field is 
	 * constrained. The concept descriptor may be null, and the type may be empty.
	 * 
	 * @param field
	 * @return the field type
	 */
	FieldType typeOf(Field field);

}
