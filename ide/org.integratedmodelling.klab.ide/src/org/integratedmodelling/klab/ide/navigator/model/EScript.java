package org.integratedmodelling.klab.ide.navigator.model;

import java.io.File;

import org.integratedmodelling.kim.api.IKimNamespace;

public class EScript extends ENamespace {

    private static final long serialVersionUID = 208774724246345701L;

    EScript(IKimNamespace statement, ENavigatorItem parent) {
        super(statement, parent);
        this.id = statement.getScriptId();
    }
        
    public File getScriptFile() {
        getKimStatement().getURI();
        return null;
    }

}
