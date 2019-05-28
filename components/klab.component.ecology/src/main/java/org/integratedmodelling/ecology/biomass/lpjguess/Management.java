package org.integratedmodelling.ecology.biomass.lpjguess;

import org.integratedmodelling.klab.Logging;

public class Management {

    private boolean isValid;

    public String        pftname;
    /// hydrology (RAINFED,IRRIGATED)
    public HydrologyType hydrology;
    /// irrigation efficiency
    // double firr;
    public int           sdate;
    public int           hdate;
    /// Nitrogen fertilisation amount
    public double        nfert;

    public Boolean fallow;

    public Management() {
        hydrology = HydrologyType.RAINFED;
        // firr = 0.0;
        sdate = -1;
        hdate = -1;
        nfert = 0.0;
        fallow = false;
    }

    public boolean isValid() {
        return isValid;
    }

    public void validate() {

        isValid = true;

        if (pftname == null) {
            Logging.INSTANCE.error("no PFT for management");
            isValid = false;
        }
    }
}
