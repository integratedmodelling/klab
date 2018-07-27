package org.integratedmodelling.kim.api;

import java.io.Serializable;
import java.net.URI;
import java.util.EnumSet;
import java.util.List;

import org.integratedmodelling.kim.api.IKimConcept.Type;

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

		void visitObserver(IKimObserver kimNamespace);

		void visitConceptStatement(IKimConceptStatement kimNamespace);

	}

	List<IKimScope> getChildren();

	/**
	 * Return a parseable string that describes the location of this code scope.
	 * 
	 * @return the location
	 */
	String getLocationDescriptor();

	URI getURI();

	void visit(Visitor visitor);

}
