package org.integratedmodelling.adapter.copernicus;

import org.integratedmodelling.adapter.datacube.ChunkedDatacubeRepository;
import org.integratedmodelling.adapter.datacube.GenericDatacubeAdapter;
import org.integratedmodelling.klab.Extensions;
import org.integratedmodelling.klab.Urn;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.extensions.UrnAdapter;
import org.integratedmodelling.klab.exceptions.KlabIllegalStateException;

@UrnAdapter(type = CopernicusAdapter.ID, version = Version.CURRENT)
public class CopernicusAdapter extends GenericDatacubeAdapter {

	public static final String ID = "copernicus";
	
	public CopernicusAdapter() {

		super(ID, true);
		
	}

	@Override
	public ChunkedDatacubeRepository getDatacube(Urn urn) {
	    CopernicusComponent copernicus = Extensions.INSTANCE.getComponentImplementation(CopernicusComponent.ID, CopernicusComponent.class);
		ChunkedDatacubeRepository ret = copernicus.getRepository(urn.getNamespace());
		if (ret == null) {
			throw new KlabIllegalStateException("Copernicus repository " + urn.getNamespace() + " is unknown or unregistered");
		}
		return ret;
	}
	
	@Override
	public String getDescription() {
		return "ESA Copernicus EO API service adapter for dynamic output";
	}

}
