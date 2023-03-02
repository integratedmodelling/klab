package org.integratedmodelling.klab.api.lang.kim;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import org.integratedmodelling.klab.api.knowledge.SemanticType;
import org.integratedmodelling.klab.api.lang.kim.KKimMacro.Field;

public interface KKimScope extends Serializable {

	/**
	 * Visitor allows traversing all concept declarations and references.
	 * 
	 */
	public static interface Visitor {

		void visitAuthority(String authority, String term);

		void visitDeclaration(KKimConcept declaration);

		void visitReference(String conceptName, Set<SemanticType> type, KKimConcept validParent);

		void visitNamespace(KKimNamespace kimNamespace);

		void visitModel(KKimModelStatement kimNamespace);

		void visitObserver(KKimAcknowledgement kimNamespace);

		void visitConceptStatement(KKimConceptStatement kimNamespace);

        void visitTemplate(Field valueOf, KKimConcept validParent, boolean mandatory);

	}

	List<KKimScope> getChildren();

	/**
	 * Return a parseable string that describes the location of this code scope.
	 * 
	 * @return the location
	 */
	String getLocationDescriptor();

	String getURI();

	void visit(Visitor visitor);

}
