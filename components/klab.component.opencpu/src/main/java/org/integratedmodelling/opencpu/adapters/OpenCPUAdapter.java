package org.integratedmodelling.opencpu.adapters;

import java.util.Collection;

import org.integratedmodelling.klab.Urn;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.adapters.IKlabData.Builder;
import org.integratedmodelling.klab.api.data.adapters.IUrnAdapter;
import org.integratedmodelling.klab.api.extensions.UrnAdapter;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;

@UrnAdapter(type = OpenCPUAdapter.ID, version = Version.CURRENT)
public class OpenCPUAdapter implements IUrnAdapter {

    public static final String ID = "opencpu";
    
    @Override
    public String getName() {
        return ID;
    }

    @Override
    public IResource getResource(String urn) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IResource contextualize(IResource resource, IGeometry scale, IGeometry overallScale,
            IObservable semantics) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isOnline(Urn urn) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void encodeData(Urn urn, Builder builder, IGeometry geometry, IContextualizationScope scope) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public Type getType(Urn urn) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IGeometry getGeometry(Urn urn) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getDescription() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<String> getResourceUrns() {
        // TODO Auto-generated method stub
        return null;
    }

}
