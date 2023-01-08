package org.integratedmodelling.stats.adapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.integratedmodelling.klab.Extensions;
import org.integratedmodelling.klab.Klab;
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
import org.integratedmodelling.klab.engine.extensions.Component;
import org.integratedmodelling.stats.StatsComponent;

/**
 * Provides a URN pattern to access usage/observation events, heatmaps, contexts
 * and other queries to the stats system.
 * 
 * 
 * 
 * @author Ferd
 *
 */
@UrnAdapter(type = StatsAdapter.ID, version = Version.CURRENT)
public class StatsAdapter implements IUrnAdapter {

	/**
	 * ACTHUNG - if this is changed, the STATS_SERVICE_ADAPTER_ID constant in the
	 * Klab service also must reflect the change or the service won't be updated.
	 */
	public final static String ID = "stats";
	StatsComponent _stats = null;

	private StatsComponent getStats() {
		if (_stats == null) {
			Component stc = Extensions.INSTANCE.getComponent(StatsComponent.ID);
			if (stc != null && stc.isActive()) {
				_stats = stc.getImplementation(StatsComponent.class);
			}
		}
		return _stats;
	}

	public StatsAdapter() {
	}

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
	public IResource contextualize(IResource resource, IGeometry scale, IGeometry overallScale, IObservable semantics) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isOnline(Urn urn) {
		/*
		 * disabled locally if we have stats gathering enabled, i.e. only works on
		 * nodes.
		 */
		return Klab.INSTANCE.getStatisticsLocalHandler() == null && getStats() != null;
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
		List<String> ret = new ArrayList<>();
		return ret;

	}

}
