package org.integratedmodelling.adapter.datacube;

import java.util.Collection;

import org.integratedmodelling.klab.Urn;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.adapters.IKlabData.Builder;
import org.integratedmodelling.klab.api.data.adapters.IUrnAdapter;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.data.resources.Resource;
import org.integratedmodelling.klab.rest.ResourceReference;

public class GenericDatacubeAdapter implements IUrnAdapter {

    private String name;
    private Datacube datacube;
    
    protected GenericDatacubeAdapter(String name, Datacube datacube) {
        this.name = name;
        this.datacube = datacube;
    }
    
    @Override
    public IResource getResource(String urn) {
    	
        Urn kurn = new Urn(urn);
        ResourceReference ref = new ResourceReference();
        ref.setUrn(kurn.getUrn());
        ref.setAdapterType(getName());
        ref.setLocalName(kurn.getResourceId());
        ref.setGeometry(getGeometry(kurn).encode());
        ref.setVersion(Version.CURRENT);
        ref.setType(getType(kurn));

        return new Resource(ref);
    }

    @Override
    public boolean isOnline(Urn urn) {
        return datacube.isOnline();
    }

    @Override
    public void encodeData(Urn urn, Builder builder, IGeometry geometry, IContextualizationScope scope) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public Type getType(Urn urn) {
        return datacube.getResourceType(urn);
    }

    @Override
    public IGeometry getGeometry(Urn urn) {
        return datacube.getResourceGeometry(urn);
    }

    @Override
    public String getDescription() {
        return datacube.getDescription();
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
