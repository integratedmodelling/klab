package org.integratedmodelling.klab.api.data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.data.adapters.IResourceEncoder;
import org.integratedmodelling.klab.api.data.adapters.IResourcePublisher;
import org.integratedmodelling.klab.api.data.adapters.IResourceValidator;
import org.integratedmodelling.klab.api.data.raw.IRawObject;
import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.api.knowledge.IWorldview;
import org.integratedmodelling.klab.api.model.IModel;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.api.runtime.monitoring.INotification;
import org.integratedmodelling.klab.api.services.IResourceService;

/**
 * A IResource represents any information content that is identified by a URN and can be inspected and
 * processed semantically through a worldview. k.LAB provides methods to resolve a URN into a IResource and to
 * retrieve the data content, in a separate operation to optimize speed. The services available on a k.LAB
 * node allow to upload resources in the form of files, literals, or URLs for services, and make their content
 * available under a URN that becomes a secure endpoint for their use in semantic engines.
 * 
 * Data services implemented in a k.LAB node allow bridging to multiple types of resources, providing plug-ins
 * that expose a validator ({@link IResourceValidator}), a publisher/unpublisher ({@link IResourcePublisher}) and an
 * encoder to IResource ({@link IResourceEncoder}) for each new resource type supported. IResources have a
 * {@link IGeometry} that can be turned into a semantic {@link IScale} through a {@link IWorldview}. This way,
 * engines do not need to know the details of any specific data protocol as the contents are returned from the
 * engine in encoded form upon a request for a URN's contents in a compatible scale.
 * 
 * Resolution of a URN (operated by the configured {@link IResourceService} returns a IResource, whose
 * {@link #get(IScale, IMonitor)} method will yield an immutable, non-empty list of IRawObjects for a semantic
 * {@link IModel} to process into IObservations.
 * 
 * @author Ferd
 *
 */
public interface IResource extends Serializable {

    /**
     * The URN that identifies this resource.
     * 
     * @return the resource's URN.
     */
    String getUrn();

    /**
     * Get the geometry associated with the resource, without fetching the entire data content.
     * 
     * @return the resource's geometry
     */
    IGeometry getGeometry();

    /**
     * Get the version associated with the resource.
     * 
     * @return the resource's version.
     */
    Version getVersion();
    
    /**
     * The data adapter type that published this resource and will be used to
     * encode it.
     * 
     * @return the ID specifying which adapter should be used to encode/decode the resource's contents.
     */
    String getAdapterType();

    /**
     * Fetch (if necessary) and return the root raw object represented by this resource. Use the monitor for
     * any reporting and to manage asynchronous requests.
     * 
     * @param scale
     * @param monitor
     * @return the result of decoding the resource.
     */
    IRawObject get(IScale scale, IMonitor monitor);
    
    /**
     * Resources come with both system-defined and user-defined metadata. User metadata will be
     * indexed by Dublin Core properties. Other metadata fields will depend on the adapter used
     * (for example, no-data values or metadata attributes such as name).
     * 
     * @return any metadata associated with the resource. Never null.
     */
    IMetadata getMetadata();
    
    /**
     * Get the history of changes affecting this resources.
     * 
     * @return the list of changes in order of time (oldest first).
     */
    List<INotification> getHistory();
    
    /**
     * URNs coming with parameters will list them here.
     * 
     * @return parameter map, possibly empty, never null.
     */
    Map<String, Object> getParameters();
}
