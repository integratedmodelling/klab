package org.integratedmodelling.weather.adapters.agera;

import org.integratedmodelling.adapter.datacube.Datacube;
import org.integratedmodelling.adapter.datacube.Datacube.MaintenanceService;
import org.integratedmodelling.klab.ogc.integration.Geoserver;

public class AgERAMaintenanceService implements MaintenanceService {

	@Override
	public void setup(Datacube datacube) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initialize(Datacube datacube) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void maintain(Datacube datacube) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean checkService(Datacube datacube) {
		if (!Geoserver.isEnabled()) {
			return false;
		}
		return true;
	}

	@Override
	public void cleanupBefore(Datacube datacube) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cleanupAfter(Datacube datacube) {
		// TODO Auto-generated method stub
		
	}

}
