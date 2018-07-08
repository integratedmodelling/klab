package org.integratedmodelling.klab.components.geospace.extents;

import org.integratedmodelling.klab.api.observations.scale.space.ISpace;

/**
 * Helper class to handler merging of extents.
 * 
 * @author Ferd
 *
 */
public class ExtentMerger {

    /**
     * Merge the passed extents.
     * 
     * Scenarios: Add grid from other to extent in main Substitute grid in main with
     * that of other Intersect extent of main with that of other, revise grid if
     * required
     * 
     * @param main
     *            the stated extent that we merge into, possibly incomplete (e.g.
     *            grid specs w/o extent) but authoritative.
     * @param other
     *            the actual extent we merge into main, should NOT override existing
     *            specs, only add extents main does not have 
     * @return
     */
    ISpace merge(ISpace main, ISpace other) {
        return null;
    }

}
