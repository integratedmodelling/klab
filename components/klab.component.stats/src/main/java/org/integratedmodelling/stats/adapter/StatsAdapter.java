package org.integratedmodelling.stats.adapter;

import java.util.Collection;

import org.integratedmodelling.klab.Urn;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.adapters.IKlabData.Builder;
import org.integratedmodelling.klab.api.extensions.UrnAdapter;
import org.integratedmodelling.klab.api.data.adapters.IUrnAdapter;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;

/**
 * Provides a URN pattern to access usage/observation events, heatmaps, and other queries to the
 * stats system.
 * 
 * @author Ferd
 *
 */
@UrnAdapter(type = StatsAdapter.ID, version = Version.CURRENT)
public class StatsAdapter implements IUrnAdapter {

    /**
     * ACTHUNG - if this is changed, the STATS_SERVICE_ADAPTER_ID constant in the Klab service also
     * must reflect the change or the service won't be updated.
     */
    public final static String ID = "stats";

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
