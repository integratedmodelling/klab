package org.integratedmodelling.klab.common.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

/**
 * Bean that contains the entire state of a view for a context (possibly not correspondent to an observation),
 * including the observation tree and the current position and visualized element in it. A view object
 * describes each view in a viewer, and it is enough (along with the context itself when applicable) to
 * reconstruct its interactive representation completely.
 * 
 * The correspondent JSON should be the data for each view in the engine viewer.
 * 
 * @author ferdinando.villa
 *
 */
public class View implements Serializable {

    private static final long serialVersionUID = -7468152830954276455L;
    
    public static class Observation {
        String name;
        String id;
        String description;
    }
    
    /*
     * ID of all observations shown in the view
     */
    private List<String> displayed = new ArrayList<>();
    
    /*
     * Root of observation tree. May be null if we're simply moving the
     * context without making observations. May not be complete as some
     * observation may be loaded on demand.
     */
    private @Nullable Observation root;
    
    /*
     * Type of view. Defines strategy to build interactive view. Do not use an enum for now, maybe later.
     */
    private String type;

    /*
     * ID of the linked context. If the view is persisted, the context should also
     * be.
     */
    private @Nullable String contextId;

    /*
     * TODO space and time beans, serializable, to hold ROI if the context is
     * not set.
     * 
     * TODO metadata for creation, serialization, storage, user etc
     */
}
