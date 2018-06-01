package org.integratedmodelling.kim.api;

import java.io.File;
import java.util.List;
import java.util.Properties;

public interface IKimProject {

    static final String KLAB_CONFIGURATION_SOURCE_FOLDER        = "klab.source.folder";
    static final String KLAB_CONFIGURATION_DEFINED_WORLDVIEW_ID = "klab.defined.worldview";
    static final String KLAB_CONFIGURATION_WORLDVIEW_ID         = "klab.worldview";

    String getDefinedWorldview();

    List<File> getSourceFiles();

    IKimNamespace getNamespace(String id);

    IKimWorkspace getWorkspace();

    String getName();

    String getWorldview();

    File getRoot();

    Properties getProperties();

    List<IKimNamespace> getNamespaces();

}
