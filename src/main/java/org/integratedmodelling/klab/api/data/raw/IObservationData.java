package org.integratedmodelling.klab.api.data.raw;

import java.util.Iterator;

import org.integratedmodelling.kim.api.data.IGeometry;
import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.observations.IObservation;

/**
 * The generic API for the data behind any observational artifacts. These objects are produced by
 * contextualizers called through service calls. The objects implement Iterator in order to also
 * represent groups of objects when appropriate. 
 * 
 * IObservationData objects are received by the dataflow runtime and used to define {@link IObservation informational
 * artifacts}.
 * 
 * @author Ferd
 *
 */
public interface IObservationData extends Iterator<IObservationData> {

    /**
     * Semantics is passed down to contextualizers to support any reasoning they want to do, but is
     * not essential to the contract of a "raw" observation, which is by definition stripped of all
     * semantics except for reporting it to applications that wish to know.
     * 
     * @return the semantics for the observation. Never null in the k.LAB engine runtime, but possibly
     *         null if this API is used outside of it.
     */
    IObservable getSemantics();

    /**
     * The geometry linked to the observation. In states, this is supplemented by the corresponding
     * contextualized datashape.
     * 
     * @return the geometry
     */
    IGeometry getGeometry();

    /**
     * Metadata. Never null, possibly empty. They are passed to the semantic artifact and may
     * be used for visualization, provenance etc.
     * 
     * @return the metadata
     */
    IMetadata getMetadata();

}
