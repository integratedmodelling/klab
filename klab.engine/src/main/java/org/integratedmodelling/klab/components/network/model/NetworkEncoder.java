package org.integratedmodelling.klab.components.network.model;

import java.io.OutputStream;
import java.util.Map;

import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.adapters.IKlabData.Builder;
import org.integratedmodelling.klab.api.data.adapters.IResourceEncoder;
import org.integratedmodelling.klab.api.knowledge.ICodelist;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;

public class NetworkEncoder implements IResourceEncoder {

	@Override
	public boolean isOnline(IResource resource, IMonitor monitor) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public IResource contextualize(IResource resource, IScale scale, IArtifact targetObservation,
			Map<String, String> urnParameters, IContextualizationScope scope) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ICodelist categorize(IResource resource, String attribute, IMonitor monitor) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void getEncodedData(IResource resource, Map<String, String> urnParameters, IGeometry geometry,
			Builder builder, IContextualizationScope scope) {
		// TODO Auto-generated method stub

	}

	@Override
	public void listDetail(IResource resource, OutputStream stream, boolean verbose, IMonitor monitor) {
		// TODO Auto-generated method stub

	}

}
