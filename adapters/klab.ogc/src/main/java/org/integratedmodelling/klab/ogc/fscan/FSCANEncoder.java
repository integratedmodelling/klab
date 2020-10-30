package org.integratedmodelling.klab.ogc.fscan;

import java.io.File;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.Urn;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.adapters.IKlabData.Builder;
import org.integratedmodelling.klab.api.data.adapters.IResourceEncoder;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.data.resources.Resource;
import org.integratedmodelling.klab.ogc.integration.Postgis;
import org.integratedmodelling.klab.utils.Path;

public class FSCANEncoder implements IResourceEncoder {

	private Postgis postgis = null;

	private boolean isOnline() {
		if (Postgis.isEnabled()) {
			this.postgis = Postgis.create();
			return this.postgis.isOnline();
		}
		return false;
	}
	
	@Override
	public boolean isOnline(IResource resource, IMonitor monitor) {
		if (isOnline()) {
			// TODO check for data in resource
			return true;
		}
		return false;
	}

	@Override
	public void getEncodedData(IResource resource, Map<String, String> urnParameters, IGeometry geometry,
			Builder builder, IContextualizationScope context) {
		
		if (!resource.getParameters().containsKey("totalshapes")) {
			long n = indexShapes(resource);
			if (n >= 0) {
				resource.getParameters().put("totalshapes", n);
				Resources.INSTANCE.getCatalog(resource).update(resource, "spatial indices generated for " + n + " polygons");
			}
		}
		
	}

	public long indexShapes(IResource resource) {
		
		if (!isOnline()) {
			return -1;
		}
		
		Urn urn = new Urn(resource.getUrn());
		
		postgis.resetBoundaries(urn);
		
		long ret = 0;
		Set<File> files = new HashSet<>();
		for (String key : resource.getParameters().keySet()) {
			if (key.startsWith("filesource.import")) {
				File file = ((Resource)resource).getLocalFile(Path.getLeading(key, '.') + '.' + "name");
				if (file != null && !files.contains(file)) {
					int level = resource.getParameters().get(Path.getLeading(key, '.') + '.' + "level", Integer.class);
					String nameExpression = resource.getParameters().get(Path.getLeading(key, '.') + '.' + "nameExpression", String.class);
					files.add(file);
					postgis.indexBoundaries(file, urn, nameExpression, level);
				}
			}
		}

		// TODO
		
		return ret;
	}

}
