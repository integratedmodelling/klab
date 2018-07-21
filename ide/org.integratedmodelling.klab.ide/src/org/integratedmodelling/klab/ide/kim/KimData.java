package org.integratedmodelling.klab.ide.kim;

import java.io.File;

import org.eclipse.core.resources.IFile;
import org.integratedmodelling.kim.api.IKimNamespace;
import org.integratedmodelling.kim.api.IPrototype;
import org.integratedmodelling.kim.model.Kim;
import org.integratedmodelling.klab.client.utils.FileCatalog;
import org.integratedmodelling.klab.common.Prototype;
import org.integratedmodelling.klab.ide.Activator;
import org.integratedmodelling.klab.ide.utils.Eclipse;

/**
 * A singleton holding all synchronized k.IM-relevant data that come from the engine, such as
 * function prototypes and URN resolution data. These can be read with Jackson as synchronized file
 * catalogs.
 * 
 * @author ferdinando.villa
 *
 */
public enum KimData {

    INSTANCE;

    FileCatalog<IPrototype> prototypes;
    FileCatalog<IPrototype> annotations;

    KimData() {
        File protoFile = new File(System.getProperty("user.home") + File.separator + ".klab" + File.separator
                + "language" + File.separator + "prototypes.json");
        File annotFile = new File(System.getProperty("user.home") + File.separator + ".klab" + File.separator
                + "language" + File.separator + "annotations.json");
        prototypes = new FileCatalog<IPrototype>(protoFile, IPrototype.class, Prototype.class);
        annotations = new FileCatalog<IPrototype>(annotFile, IPrototype.class, Prototype.class);
    }

    public IPrototype getFunctionPrototype(String name) {
        return prototypes.get(name);
    }

    public IPrototype getAnnotationPrototype(String name) {
        return annotations.get(name);
    }

    public IKimNamespace getNamespace(IFile file) {
        String nsId = Eclipse.INSTANCE.getNamespaceIdFromIFile(file);
        return nsId == null ? null : Kim.INSTANCE.getNamespace(nsId);
    }

    public Object findObjectAt(int caret, IKimNamespace namespace) {
        // TODO Auto-generated method stub
        return null;
    }

}
