package org.integratedmodelling.klab.ide.navigator.model;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.URI;
import org.integratedmodelling.kim.api.IKimNamespace;
import org.integratedmodelling.kim.model.KimStatement;

public class EScript extends ENamespace {

    private static final long serialVersionUID = 208774724246345701L;

	EScript(IKimNamespace statement, ENavigatorItem parent) {
        super(statement, parent);
        this.id = statement.getScriptId();
    }
        
    public IFile getIFile() {
        URI uri = ((KimStatement)getKimStatement()).getEObject().eResource().getURI();
        System.out.println("URI is " + uri);
        return null;
    }

}
