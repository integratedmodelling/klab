package org.integratedmodelling.klab.ide.kim;

import java.io.File;

import org.integratedmodelling.kim.api.IPrototype;
import org.integratedmodelling.kim.model.Prototype;
import org.integratedmodelling.klab.utils.FileCatalog;

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
    File protoFile = new File(System.getProperty("user.home") + File.separator + ".klab"
        + File.separator + "language" + File.separator + "prototypes.json");
    File annotFile = new File(System.getProperty("user.home") + File.separator + ".klab"
        + File.separator + "language" + File.separator + "annotations.json");
    prototypes = new FileCatalog<IPrototype>(protoFile, Prototype.class, IPrototype.class);
    annotations = new FileCatalog<IPrototype>(annotFile, Prototype.class, IPrototype.class);
  }

  public IPrototype getFunctionPrototype(String name) {
    return prototypes.get(name);
  }

  public IPrototype getAnnotationPrototype(String name) {
    return annotations.get(name);
  }

}
