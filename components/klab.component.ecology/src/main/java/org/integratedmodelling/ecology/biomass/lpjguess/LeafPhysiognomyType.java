package org.integratedmodelling.ecology.biomass.lpjguess;

import org.integratedmodelling.klab.exceptions.KlabException;

public enum LeafPhysiognomyType {

    NOLEAFTYPE,
    NEEDLELEAF,
    BROADLEAF;

    public static LeafPhysiognomyType get(String s) {
        if (s.equalsIgnoreCase("BROADLEAF")) {
            return BROADLEAF;
        } else if (s.equalsIgnoreCase("NEEDLELEAF")) {
            return NEEDLELEAF;
        } else if (s.equalsIgnoreCase("NOLEAFTYPE")) {
            return NOLEAFTYPE;
        }

        throw new KlabException("unknown leaf physiognomy: " + s);

    }
}
