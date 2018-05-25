package org.integratedmodelling.kim.model;

import org.integratedmodelling.kim.api.IKimClassifier;
import org.integratedmodelling.kim.api.IKimStatement;
import org.integratedmodelling.kim.kim.ClassifierRHS;

public class KimClassifier extends KimStatement implements IKimClassifier {

    private static final long serialVersionUID = 2634906673844696880L;

    public KimClassifier(ClassifierRHS statement, IKimStatement parent) {
        super(statement, parent);
        // TODO Auto-generated constructor stub
    }
}
