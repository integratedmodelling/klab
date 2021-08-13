package org.integratedmodelling.adapter.datacube.copernicus;

import java.util.Map;

import org.integratedmodelling.klab.components.time.extents.TimeInstant;

public class AgERA5Repository extends CopernicusCDSDatacube {

    public AgERA5Repository() {
        super("", TimeInstant.create(1979, 1, 1));
    }

    @Override
    protected void configureRequest(String variable, Map<String, Object> payload) {
        // TODO Auto-generated method stub
        
    }

    @Override
    protected boolean checkRemoteAvailability(int chunk, String variable) {
        // TODO Auto-generated method stub
        return false;
    }

}
