package org.integratedmodelling.klab.engine.resources;

import java.io.File;
import java.util.Collection;

import org.integratedmodelling.klab.Concepts;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IWorldview;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.scale.Scale;

public class Worldview extends MonitorableGitWorkspace implements IWorldview {

    public Worldview(String name, File root, Collection<String> gitUrls, File... overridingProjects) {
        super(root, gitUrls, overridingProjects);
        this.setName(name);
        this.setSkipSync(System.getProperty("skipWorldviewSync") != null);
    }

    @Override
    public IScale getScale(IGeometry geometry) {
        return Scale.create(geometry);
    }
    
    public static IConcept getGeoregionConcept() {
    	/**
    	 * TODO! Tie to annotation in worldview AND configured parameter to override
    	 */
    	return Concepts.c("earth:Region");
    }

}
