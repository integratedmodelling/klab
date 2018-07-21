package org.integratedmodelling.klab.ide.kim;

import java.io.File;

import org.eclipse.core.resources.IFile;
import org.integratedmodelling.kim.api.IKimConceptStatement;
import org.integratedmodelling.kim.api.IKimModel;
import org.integratedmodelling.kim.api.IKimNamespace;
import org.integratedmodelling.kim.api.IKimObserver;
import org.integratedmodelling.kim.api.IKimScope;
import org.integratedmodelling.kim.api.IKimStatement;
import org.integratedmodelling.kim.api.IPrototype;
import org.integratedmodelling.kim.model.Kim;
import org.integratedmodelling.klab.client.utils.FileCatalog;
import org.integratedmodelling.klab.common.Prototype;
import org.integratedmodelling.klab.ide.navigator.model.EConcept;
import org.integratedmodelling.klab.ide.navigator.model.EModel;
import org.integratedmodelling.klab.ide.navigator.model.EObserver;
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

        IKimScope focus = namespace;

        for (IKimStatement child : namespace.getAllStatements()) {
            if (child.getFirstCharOffset() > caret) {
                break;
            }
            focus = child;
        }

        /*
         * build screwed-up objects without parent that hopefully will only be used to search for their match in the tree.
         */
        if (focus != null) {
            if (focus instanceof IKimConceptStatement) {
                return new EConcept(namespace.getName() + ":" + ((IKimConceptStatement) focus).getName(),
                        ((IKimConceptStatement) focus), null, null);
            } else if (focus instanceof IKimModel) {
                return new EModel(namespace.getName() + "." + ((IKimModel) focus).getName(), ((IKimModel) focus), null);
            } else if (focus instanceof IKimObserver) {
                return new EObserver(namespace.getName() + "." + ((IKimObserver) focus).getName(),
                        ((IKimObserver) focus), null, null);
            }
        }
        return null;
    }

}
