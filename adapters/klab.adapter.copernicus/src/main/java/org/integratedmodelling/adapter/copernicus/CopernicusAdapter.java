package org.integratedmodelling.adapter.copernicus;

import java.util.HashMap;
import java.util.Map;

import org.integratedmodelling.adapter.copernicus.repositories.AgERA5Repository;
import org.integratedmodelling.adapter.datacube.ChunkedDatacubeRepository;
import org.integratedmodelling.adapter.datacube.GenericDatacubeAdapter;
import org.integratedmodelling.klab.Urn;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.extensions.UrnAdapter;
import org.integratedmodelling.klab.exceptions.KlabIllegalStateException;

/**
 * Handles URNs:
 * 
 * <ul>
 * <li>klab:weather:agera5:{variable} uses the AgERA5 dataset (credentials needed in configuration)
 * <li>klab:weather:data:{all|catalog} (handles primary output as parameter, e.g. #precipitation,
 * and others as additional attributes)</li>
 * <li>klab:weather:stations:{all|catalog}</li>
 * <li>klab:weather:storms:{all|catalog}</li>
 * </ul>
 * <p>
 * "Catalog" may depend on the setup, should be ghdnc (or noaa), ....
 * 
 * @author Ferd
 *
 */
@UrnAdapter(type = CopernicusAdapter.ID, version = Version.CURRENT)
public class CopernicusAdapter extends GenericDatacubeAdapter {

	public static final String ID = "copernicus";
	
	Map<String, ChunkedDatacubeRepository> repositories = new HashMap<>();
	
	public CopernicusAdapter() {

		super(ID);
		
		/*
		 * the repos
		 */
		ChunkedDatacubeRepository agera5 = new AgERA5Repository();
		
		/*
		 * add them all with the names they advertise
		 */
		repositories.put(agera5.getName(), agera5);
	}

	@Override
	public ChunkedDatacubeRepository getDatacube(Urn urn) {
		ChunkedDatacubeRepository ret = repositories.get(urn.getNamespace());
		if (ret == null) {
			throw new KlabIllegalStateException("Copernicus repository " + urn.getNamespace() + " is unknown or unregistered");
		}
		return ret;
	}
	
	@Override
	public String getDescription() {
		return "ESA Copernicus EO API service adapter";
	}

}
