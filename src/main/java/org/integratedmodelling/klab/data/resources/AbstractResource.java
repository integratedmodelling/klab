package org.integratedmodelling.klab.data.resources;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.api.runtime.monitoring.INotification;
import org.integratedmodelling.klab.api.services.IResourceService;
import org.integratedmodelling.klab.dataflow.Dataflow;

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
     * Pattern to validate a RFC 2141-compliant URN, just to be on the right side of things.
     */
    public final static Pattern URN_PATTERN = Pattern
            .compile("^urn:[a-z0-9][a-z0-9-]{0,31}:([a-z0-9()+,\\-.:=@;$_!*']|%[0-9a-f]{2})+$", Pattern.CASE_INSENSITIVE);

    public static boolean isCompliant(String urn) {
        return URN_PATTERN.matcher(urn).matches();
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

    /**
     * Return a function call that will produce the correspondent value or 
     * contextualizer when called through a {@link Dataflow}.
     * 
     * @return
     */
    public abstract IServiceCall getComputation();
    
}
