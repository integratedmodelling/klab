package org.integratedmodelling.ecology.biomass.lpjguess;

import org.integratedmodelling.klab.exceptions.KlabException;

public enum HydrologyType {

    RAINFED,
    IRRIGATED;

    public static HydrologyType get(String s) {

        switch (s.toUpperCase()) {
        case "RAINFED":
            return RAINFED;
        case "IRRIGATED":
            return IRRIGATED;
        }

        throw new KlabException("unknown irrigation type: " + s);
    }
}
