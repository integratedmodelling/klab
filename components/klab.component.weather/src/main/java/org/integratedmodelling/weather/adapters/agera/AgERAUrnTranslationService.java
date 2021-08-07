package org.integratedmodelling.weather.adapters.agera;

import org.integratedmodelling.adapter.datacube.Datacube.UrnTranslationService;
import org.integratedmodelling.klab.Urn;
import org.integratedmodelling.klab.api.data.IGeometry;

public class AgERAUrnTranslationService implements UrnTranslationService {

    private AgERADatacube cube;

    @Override
    public Object getVariable(Urn urn, IGeometry geometry) {
        return cube.getVariable(urn.getResourceId());
    }

    public void setDatacube(AgERADatacube cube) {
        this.cube = cube;
    }

}
