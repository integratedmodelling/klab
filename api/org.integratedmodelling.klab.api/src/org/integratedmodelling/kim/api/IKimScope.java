package org.integratedmodelling.kim.api;

import java.io.Serializable;
import java.util.EnumSet;
import java.util.List;

import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.kim.api.IKimMacro.Field;

public interface IKimScope extends Serializable {

	/**
	 * Visitor allows traversing all concept declarations and references.
	 * 
	 */
	public static interface Visitor {

		void visitAuthority(String authority, String term);

		void visitDeclaration(IKimConcept declaration);

		void visitReference(String conceptName, EnumSet<Type> type, IKimConcept validParent);

		void visitNamespace(IKimNamespace kimNamespace);

		void visitModel(IKimModel kimNamespace);

		void visitObserver(IKimAcknowledgement kimNamespace);

		void visitConceptStatement(IKimConceptStatement kimNamespace);

        void visitTemplate(Field valueOf, IKimConcept validParent, boolean mandatory);

	}

	List<IKimScope> getChildren();

	/**
	 * Return a parseable string that describes the location of this code scope.
	 * 
	 * @return the location
	 */
	String getLocationDescriptor();

	String getURI();

	void visit(Visitor visitor);

}
