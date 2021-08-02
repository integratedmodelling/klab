package org.integratedmodelling.adapter.datacube;

import java.util.Collection;

import org.integratedmodelling.klab.Urn;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.adapters.IKlabData.Builder;
import org.integratedmodelling.klab.api.data.adapters.IUrnAdapter;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;

public class GenericDatacubeAdapter<T extends Datacube> implements IUrnAdapter {

    private String name;
    private T datacube;
    
    protected GenericDatacubeAdapter(String name, T datacube) {
        this.name = name;
        this.datacube = datacube;
    }
    
    @Override
    public IResource getResource(String urn) {
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

    @Override
    public String getName() {
        return name;
    }

}
