package org.integratedmodelling.klab.api.data.adapters;

import org.integratedmodelling.kim.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.IResource;

public interface IResourceEncoder {

    IKlabData getEncodedData(IResource resource, IGeometry geometry);
}
