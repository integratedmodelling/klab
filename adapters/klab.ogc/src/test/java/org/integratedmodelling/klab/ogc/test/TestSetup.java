package org.integratedmodelling.klab.ogc.test;

import java.net.URL;

import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.knowledge.IProject;

public class TestSetup {

    public IResource importResource(String resource, IProject project, Object... parameterKVPs) {
        URL resourceUrl = getClass().getClassLoader().getResource(resource);
        return Resources.INSTANCE.importResource(resourceUrl, project, parameterKVPs);
    }

}
