package org.integratedmodelling.kim.model;

import java.util.EnumSet;

import org.integratedmodelling.kim.api.IKimConcept;
import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.kim.api.IKimConceptStatement;
import org.integratedmodelling.kim.api.IKimMacro.Field;
import org.integratedmodelling.kim.api.IKimModel;
import org.integratedmodelling.kim.api.IKimNamespace;
import org.integratedmodelling.kim.api.IKimAcknowledgement;
import org.integratedmodelling.kim.api.IKimScope;

/**
 * A do-nothing {@link IKimScope.Visitor} for less painful derivations when only
 * a few actions are needed.
 * 
 * @author ferdinando.villa
 *
 */
public class DefaultVisitor implements IKimScope.Visitor {

	@Override
	public void visitAuthority(String authority, String term) {
	}

	@Override
	public void visitDeclaration(IKimConcept declaration) {
	}

	@Override
	public void visitReference(String conceptName, EnumSet<Type> type, IKimConcept validParent) {
	}

	@Override
	public void visitNamespace(IKimNamespace kimNamespace) {
	}

	@Override
	public void visitModel(IKimModel kimNamespace) {
	}

	@Override
	public void visitObserver(IKimAcknowledgement kimNamespace) {
	}

	@Override
	public void visitConceptStatement(IKimConceptStatement kimNamespace) {
	}

    @Override
    public void visitTemplate(Field valueOf, IKimConcept validParent, boolean mandatory) {
    }

}
