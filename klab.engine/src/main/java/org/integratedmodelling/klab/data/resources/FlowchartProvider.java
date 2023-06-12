package org.integratedmodelling.klab.data.resources;

import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.adapters.IResourceEncoder;
import org.integratedmodelling.klab.dataflow.Flowchart;

/**
 * If the resource encoder is a flowchart provider, it can create internal process structure and
 * documentation from the URN.
 * 
 * TODO expose this through the node resource API for publicly hosted resources. This requires API
 * to send resource adapter capabilities as well as the needed endpoint.
 * 
 * @author mario
 *
 */
public interface FlowchartProvider extends IResourceEncoder {

    /**
     * Create the internal structure that documents the passed resource within the passed element.
     * 
     * @param resource
     * @param parentElement
     */
    void createwFLowchart(IResource resource, Flowchart.Element parentElement);
}
