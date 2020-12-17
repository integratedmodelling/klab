package org.integratedmodelling.tables;

import java.util.Map;

import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.adapters.IKlabData.Builder;
import org.integratedmodelling.klab.api.data.general.ITable;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
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
}
