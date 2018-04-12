package org.integratedmodelling.kim.api;

import java.util.Collection;
import java.util.EnumSet;
import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.kim.kim.ConceptDeclaration;
import org.integratedmodelling.kim.model.Kim;
import org.integratedmodelling.kim.model.Kim.ConceptDescriptor;

/**
 * A macro carries around the macro definition as a concept statement, acting as a 
 * delegate for it, in addition to the results of validation; an instance of a 
 * macro is passed to the generator of IKimConcept to turn a declaration into 
 * its substituted form.
 * 
 * @author ferdinando.villa
 */
public interface IKimMacro extends IKimConceptStatement {

	public static enum Field {
		
		CONTEXT("context"),
		INHERENT("inherent"),
		COMPRESENT("compresent"),
		GOAL("goal"),
		CAUSANT("causant"),
		CAUSED("caused");

		String declaredName;
		
		Field(String name) {
			this.declaredName = name;
		}
	}
	
	interface FieldType {

        EnumSet<Type> getType();

        ConceptDescriptor getDescriptor();

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
	FieldType getType(Field field);
	
	/**
	 * Get the parsed declaration for the passed field, which must be one of those returned
	 * by {@link #getFields()}.
	 * 
	 * @param field
	 * @return the parsed declaration
	 */
	ConceptDeclaration getDeclaration(Field field);
}
