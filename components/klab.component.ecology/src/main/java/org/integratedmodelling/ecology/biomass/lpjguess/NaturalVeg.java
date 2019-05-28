package org.integratedmodelling.ecology.biomass.lpjguess;

import org.integratedmodelling.klab.exceptions.KlabException;

public enum NaturalVeg {

    NONE,
    GRASSONLY,
    ALL;

    static public NaturalVeg get(String s) {
        switch (s.toUpperCase()) {
        case "NONE":
            return NONE;
        case "GRASSONLY":
            return GRASSONLY;
        case "ALL":
            return ALL;
        }

        throw new KlabException("unknown natural vegetation statement: " + s);
    }
}
