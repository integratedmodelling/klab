package org.integratedmodelling.weather.adapters.agera;

import org.integratedmodelling.adapter.datacube.Datacube.EncodingService;
import org.integratedmodelling.klab.Urn;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.adapters.IKlabData.Builder;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;

public class AgERAEncodingService implements EncodingService {

    private AgERADatacube cube;
    
    @Override
    public void encodeData(Urn urn, Builder builder, IGeometry geometry, IContextualizationScope scope) {
    	
    }

    public void setDatacube(AgERADatacube cube) {
        this.cube = cube;
    }

}
