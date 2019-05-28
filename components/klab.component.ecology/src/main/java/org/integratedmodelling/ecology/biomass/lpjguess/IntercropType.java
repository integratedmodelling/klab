package org.integratedmodelling.ecology.biomass.lpjguess;

import org.integratedmodelling.klab.exceptions.KlabException;

public enum IntercropType {
    NOINTERCROP,
    NATURALGRASS;

    public static IntercropType get(String s) {
        switch (s.toUpperCase()) {
        case "NOINTERCROP":
            return NOINTERCROP;
        case "NATURALGRASS":
            return NATURALGRASS;
        }

        throw new KlabException("invalid intercrop type: " + s);
    }
}
