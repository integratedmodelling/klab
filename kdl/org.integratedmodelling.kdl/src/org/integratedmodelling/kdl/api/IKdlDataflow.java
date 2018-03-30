package org.integratedmodelling.kdl.api;

import java.util.Collection;
import java.util.List;

/**
 * A dataflow contains zero or more actuator specifications and some 
 * specified metadata.
 * 
 * @author Ferd
 *
 */
public interface IKdlDataflow extends IKdlStatement {

    /**
     * All actuators in the dataflow. If > 1, each can be computed in parallel. 
     * They may be resolved (@link {@link #getComputation()} != null) or not, in
     * which case they specify interfaces and possibly adapter operations to ensure
     * correct representation. 
     * 
     * @return
     */
    Collection<IKdlActuator> getActuators();

    /**
     * Endpoint for this computation, if any. Implementations must be able to understand how to
     * use it based on other metadata.
     * 
     * @return
     */
    String getEndpoint();

    /**
     * Version of this dataflow.
     * 
     * @return
     */
    String getVersion();

    /**
     * Minimum version of k.LAB needed to run.
     * 
     * @return
     */
    String getKlabVersion();

    /**
     * Worldview to interpret the semantics in actors, if any.
     * 
     * @return
     */
    String getWorldview();

    /**
     * Contextualizers to build the scale for the dataflow.
     * 
     * @return
     */
    List<IKdlContextualizer> getScale();

    /**
     * Package name, if any, for declaration of functions in k.IM.
     * 
     * @return
     */
    String getPackageName();
}
