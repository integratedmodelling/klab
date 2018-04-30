package org.integratedmodelling.kim.model;

import org.eclipse.emf.ecore.EObject;
import org.integratedmodelling.kim.api.IKimClassifier;
import org.integratedmodelling.kim.api.IKimStatement;

public class KimClassifier extends KimStatement implements IKimClassifier {

    private static final long serialVersionUID = 2634906673844696880L;

    public KimClassifier(EObject statement, IKimStatement parent) {
        super(statement, parent);
        // TODO Auto-generated constructor stub
    }
}
