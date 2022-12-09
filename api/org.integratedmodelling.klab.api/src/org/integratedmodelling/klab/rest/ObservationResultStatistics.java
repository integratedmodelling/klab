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
     * Although presented here in a flat list, assets are nested, so times of execution only
     * compound among assets of the same type. The nesting structure is not kept in the statistics
     * although it appears in the ActivityBuilder that generated it.
     */
    private List<ObservationAssetStatistics> assets = new ArrayList<>();

}
