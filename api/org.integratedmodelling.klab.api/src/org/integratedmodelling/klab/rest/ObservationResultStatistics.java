package org.integratedmodelling.klab.rest;

import java.util.ArrayList;
import java.util.List;

/**
 * Package sent to a stats service to document the result of an observation query. Each "user-level"
 * query (including those for models) can produce one of these.
 * 
 * @author Ferd
 *
 */
public class ObservationResultStatistics {

    /**
     * List of assets produced, including resolved observables, models, resources and exports.
     * Although presented here in a flat list, assets are born nested, so times of execution only
     * compound among assets of the same type. The nesting structure is not kept in the statistics
     * although it appears in the ActivityBuilder that generated it.
     */
    private List<ObservationAssetStatistics> assets = new ArrayList<>();
    /**
     * Context ID. Never null. If root == true, this describes the first context observation with
     * this ID. Otherwise it must refer to a previously notified one.
     */
    private String contextId;
    
    /**
     * Not null only in contexts and observations done with partial coverage.
     */
    private ScaleStatistics scaleStatistics;
    
    /**
     * Start and end time are in milliseconds from epoch, zulu time
     */
    private long startTime;
    private long endTime;
    
    /**
     * Total duration is start to end, redundant with the above
     */
    private double durationSeconds;
    
    /**
     * The engine this comes from. May be CLI or API if not an authorized remote engine.
     */
    private String engineName;
    
    /**
     * Observable or model/view URN.
     */
    private String observable;
    
    /**
     * Observation name, not null only in contexts.
     */
    private String observationName;

    
}
