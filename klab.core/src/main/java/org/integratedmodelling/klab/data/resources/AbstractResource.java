package org.integratedmodelling.klab.data.resources;

import java.util.ArrayList;
import java.util.List;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.api.runtime.monitoring.INotification;
import org.integratedmodelling.klab.api.services.IResourceService;

/**
 * The k.LAB resource is identified by a URN. The {@link IResourceService} provides methods to create one from
 * file names and concept declarations. A URN can be partially resolved (using the <code>resolve</code> API
 * call), i.e. it will know its geometry and metadata without actually building the IResource, or fully
 * resolved to a IResource (using the <code>get</code> API call).
 * 
 * Resources also have a (non-API) method to produce the KDL function call or literal that encodes their
 * computation or resolution.
 * 
 * @author Ferd
 *
 */
public abstract class AbstractResource implements IResource {

    private static final long   serialVersionUID = -923039635832182164L;

    Version             version;
    IMetadata           metadata;
    String              urn;
    List<INotification> history          = new ArrayList<>();

    public AbstractResource(String urn) {
        this.urn = urn;
    }

    /**
     * Available after resolution unless the version was explicitly part of the URN.
     * 
     * @return
     */
    @Override
    public Version getVersion() {
        return version;
    }

    /**
     * Metadata. Available after resolution.
     * 
     * @return
     */
    @Override
    public IMetadata getMetadata() {
        return metadata;
    }

    @Override
    public String getUrn() {
        return urn;
    }

    @Override
    public List<INotification> getHistory() {
        return history;
    }
    
}
