package org.integratedmodelling.ecology.biomass.lpjguess;

import org.integratedmodelling.klab.exceptions.KlabException;

public enum ForceAutumnSowing {

    NOFORCING,
    AUTUMNSOWING,
    SPRINGSOWING;

    public static ForceAutumnSowing get(String s) {

        switch (s.toUpperCase()) {
        case "0":
        case "NOFORCING":
            return NOFORCING;
        case "AUTUMNSOWING":
            return AUTUMNSOWING;
        case "SPRINGSOWING":
            return SPRINGSOWING;
        }

        throw new KlabException("unknown autumn sowing mode: " + s);
    }
}
