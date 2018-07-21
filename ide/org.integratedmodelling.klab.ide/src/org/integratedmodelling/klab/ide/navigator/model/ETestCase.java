package org.integratedmodelling.klab.ide.navigator.model;

import org.integratedmodelling.kim.api.IKimNamespace;

public class ETestCase extends ENamespace {

    private static final long serialVersionUID = 208774724246345701L;

    ETestCase(IKimNamespace statement, ENavigatorItem parent) {
        super(statement, parent);
        this.id = statement.getTestCaseId();
    }

}
