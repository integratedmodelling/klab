package org.integratedmodelling.random.adapters;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
import org.integratedmodelling.klab.common.Geometry;
import org.integratedmodelling.klab.data.resources.Resource;
import org.integratedmodelling.klab.rest.ResourceReference;

@UrnAdapter(type = "soilgrids", version = Version.CURRENT)
public class SoilgridsAdapter implements IUrnAdapter{

    public static final String NAME = "soilgrids";
    
    public static final String CLASSIFICATION_QUERY = "/classification/query";
    public static final String PROPERTIES_LAYER = "/properties/layers";
    public static final String PROPERTIES_QUERY = "/properties/query";

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public IResource getResource(String urn) {
        Urn kurn = new Urn(urn);
        ResourceReference ref = new ResourceReference();
        ref.setUrn(urn.toString());
        ref.setAdapterType(getName());
        ref.setLocalName(kurn.getResourceId());
        //ref.setGeometry("#S2"); //TODO check geometry
        ref.setVersion(Version.CURRENT);
        ref.setType(getType(kurn));
        return new Resource(ref);
    }

    @Override
    public IResource contextualize(IResource resource, IGeometry scale, IGeometry overallScale, IObservable semantics) {
        return resource;
    }

    @Override
    public boolean isOnline(Urn urn) {
        // TODO
        return true;
    }

    @Override
    public void encodeData(Urn urn, Builder builder, IGeometry geometry, IContextualizationScope scope) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public Type getType(Urn urn) {
        // TODO
        return Type.OBJECT;
    }

    @Override
    public IGeometry getGeometry(Urn urn) {
        // TODO for now, return S2 geometry
        return Geometry.create("#S2");
    }

    @Override
    public String getDescription() {
        return "A system for digital soil mapping based on global compilation of soil profile data and environmental layers";
    }

    @Override
    public Collection<String> getResourceUrns() {
        List<String> ret = new ArrayList<>();
        // TODO
        return ret;
    }

}
