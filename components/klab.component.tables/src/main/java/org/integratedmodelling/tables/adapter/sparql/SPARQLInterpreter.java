package org.integratedmodelling.tables.adapter.sparql;

import java.util.Map;

import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.adapters.IKlabData.Builder;
import org.integratedmodelling.klab.api.data.general.ITable.Structure;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.utils.Pair;
import org.integratedmodelling.tables.TableInterpreter;

public class SPARQLInterpreter extends TableInterpreter {

	@Override
	public Type getType(IResource resource, IGeometry geometry) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Pair<Structure, IGeometry> analyze(IResource resource) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void encode(IResource resource, Map<String, String> urnParameters, IGeometry geometry, Builder builder,
			IContextualizationScope context) {
		// TODO Auto-generated method stub
		
	}

}
