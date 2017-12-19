package org.integratedmodelling.klab.engine.resources;

import java.io.File;
import java.util.Collection;

import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.knowledge.IWorldview;
import org.integratedmodelling.klab.api.observations.scale.IScale;

public class Worldview extends MonitorableGitWorkspace implements IWorldview {

    public Worldview(String name, File root, Collection<String> gitUrls, File... overridingProjects) {
        super(root, gitUrls, overridingProjects);
        this.setName(name);
    }

    @Override
    public IScale getScale(IGeometry geometry) {
        // TODO Auto-generated method stub
        return null;
    }

}
