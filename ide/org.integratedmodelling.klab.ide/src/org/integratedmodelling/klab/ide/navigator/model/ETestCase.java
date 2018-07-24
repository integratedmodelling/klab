package org.integratedmodelling.klab.ide.navigator.model;

import java.io.File;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.emf.common.util.URI;
import org.integratedmodelling.kim.api.IKimNamespace;
import org.integratedmodelling.kim.model.KimStatement;

public class ETestCase extends ENamespace {

    private static final long serialVersionUID = 208774724246345701L;

    ETestCase(IKimNamespace statement, ENavigatorItem parent) {
        super(statement, parent);
        this.id = statement.getTestCaseId();
    }
    
}
