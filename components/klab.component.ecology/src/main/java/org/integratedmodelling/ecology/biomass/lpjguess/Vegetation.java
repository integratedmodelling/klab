package org.integratedmodelling.ecology.biomass.lpjguess;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Vegetation sounds nice but it's just a list of Individual, with a few
 * additional methods to ease accounting.
 *
 * @author Ferd
 *
 */
public class Vegetation extends ArrayList<Individual> {

    private static final long serialVersionUID = -8453534186219231981L;

    // running ID for individuals. I'm not sure this is what they want, but they
    // assign the
    private int  _iid    = 0;
    Set<Integer> _killed = new HashSet<Integer>();

    public Patch patch;

    public Vegetation(Patch p) {
        patch = p;
    }

    public void reap() {

    	/*
    	 * FIXME THIS IS WRONG - must copy the non-killed to a new list and reassign.
    	 */
        for (Integer i : _killed) {
            remove(i);
        }
        _killed.clear();
    }

    /**
     * Schedule the individual with the passed ID for removal. Actual removal
     * only happens at reap().
     *
     * @param ind
     */
    public void kill(int ind) {

        for (int idx = 0; idx < size(); idx++) {
            if (get(idx).id == ind) {
                get(idx).alive = false;
                _killed.add(idx);
            }
        }
    }

    @Override
    public boolean add(Individual e) {
        // DataRecorder.get().info("Adding vegetation!");
        e.id = _iid++;
        return super.add(e);
    }

    /*
     * true if the passed PFT ID is represented in any individuals.
     */
    public boolean isPFTRepresented(int id) {

        for (Individual i : this) {
            if (i.pft.id == id) {
                return true;
            }
        }
        return false;
    }

}
