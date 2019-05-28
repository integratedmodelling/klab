package org.integratedmodelling.ecology.biomass.lpjguess;

import org.integratedmodelling.klab.exceptions.KlabException;

public enum LandcoverType {

    URBAN, // 0
    CROP, // 1
    PASTURE, // 2
    FOREST, // 3
    NATURAL, // 4
    PEATLAND, // 5
    BARREN; // 6

    public static LandcoverType get(String s) {
        switch (s.toUpperCase()) {
        case "URBAN":
            return URBAN;
        // jesus
        case "CROP":
        case "CROPLAND":
            return CROP;
        case "PASTURE":
            return URBAN;
        case "FOREST":
            return FOREST;
        case "NATURAL":
            return NATURAL;
        case "PEATLAND":
            return PEATLAND;
        case "BARREN":
            return BARREN;
        }
        throw new KlabException("invalid land cover type specification: " + s);
    }

}
