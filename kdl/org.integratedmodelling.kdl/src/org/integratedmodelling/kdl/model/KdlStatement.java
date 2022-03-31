package org.integratedmodelling.kdl.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.integratedmodelling.kdl.api.IKdlStatement;

public class KdlStatement implements IKdlStatement, Serializable {

	private static final long serialVersionUID = 8163017113324256385L;

	List<String> errors = new ArrayList<>();

	KdlStatement(EObject o) {

	}

	@Override
	public boolean isErrors() {
		return errors.size() > 0;
	}

	@Override
	public Collection<String> getErrors() {
		return errors;
	}
}
