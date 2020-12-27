package org.integratedmodelling.tables;

import java.net.URL;
import java.util.Map;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.adapters.IKlabData.Builder;
import org.integratedmodelling.klab.api.data.general.ITable;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.utils.Pair;

public interface ITableInterpreter {

	IArtifact.Type getType(IResource resource, IGeometry geometry);
	
	/**
	 * Analyze the resource and return the 
	 * @param resource
	 * @return
	 */
	Pair<ITable.Structure, IGeometry> analyze(IResource resource); 

	void encode(IResource resource, Map<String, String> urnParameters, IGeometry geometry, Builder builder,
			IContextualizationScope context);

	void buildResource(IParameters<String> userData, IResource.Builder ret, IMonitor monitor);

	boolean canHandle(URL resource, IParameters<String> parameters);
}
