package org.integratedmodelling.adapter.copernicus;

import org.integratedmodelling.adapter.datacube.ChunkedDatacubeRepository;
import org.integratedmodelling.adapter.datacube.GenericDatacubeAdapter;
import org.integratedmodelling.klab.Extensions;
import org.integratedmodelling.klab.Urn;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.extensions.UrnAdapter;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.exceptions.KlabIllegalStateException;

@UrnAdapter(type = CopernicusStaticAdapter.ID, version = Version.CURRENT)
public class CopernicusStaticAdapter extends GenericDatacubeAdapter {

    public static final String ID = "copernicus.static";

    public CopernicusStaticAdapter() {
        super(ID, false);
    }

    @Override
    public ChunkedDatacubeRepository getDatacube(Urn urn) {
        CopernicusComponent copernicus = Extensions.INSTANCE.getComponentImplementation(CopernicusComponent.ID,
                CopernicusComponent.class);
        ChunkedDatacubeRepository ret = copernicus.getRepository(urn.getNamespace());
        if (ret == null) {
            throw new KlabIllegalStateException("Copernicus repository " + urn.getNamespace() + " is unknown or unregistered");
        }
        return ret;
    }

    @Override
    public String getDescription() {
        return "ESA Copernicus EO API service adapter for static quality output";
    }

    @Override
    public Type getType(Urn urn) {
        return Type.NUMBER;
    }

}
