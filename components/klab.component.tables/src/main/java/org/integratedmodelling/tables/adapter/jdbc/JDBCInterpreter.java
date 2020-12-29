package org.integratedmodelling.tables.adapter.jdbc;

import java.net.URL;
import java.util.Map;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.adapters.IKlabData.Builder;
import org.integratedmodelling.klab.api.data.general.ITable;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.tables.TableInterpreter;

public class JDBCInterpreter extends TableInterpreter {

	@Override
	public Type getType(IResource resource, IGeometry geometry) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void encode(IResource resource, Map<String, String> urnParameters, IGeometry geometry, Builder builder,
			IContextualizationScope context) {
		// TODO Auto-generated method stub

	}

	@Override
	public void buildResource(IParameters<String> userData, org.integratedmodelling.klab.api.data.IResource.Builder ret,
			IMonitor monitor) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean canHandle(URL resource, IParameters<String> parameters) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ITable<?> getTable(IResource resource, IGeometry geometry) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public IGeometry recomputeGeometry(IResource resource, Map<String, String> parameters) {
		IGeometry ret = resource.getGeometry();
		return ret;
	}
}
