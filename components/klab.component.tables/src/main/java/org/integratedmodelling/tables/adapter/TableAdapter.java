package org.integratedmodelling.tables.adapter;

import java.util.Map;
import java.util.regex.Pattern;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.IResourceCalculator;
import org.integratedmodelling.klab.api.data.adapters.IResourceAdapter;
import org.integratedmodelling.klab.api.data.adapters.IResourceEncoder;
import org.integratedmodelling.klab.api.data.adapters.IResourceImporter;
import org.integratedmodelling.klab.api.data.adapters.IResourcePublisher;
import org.integratedmodelling.klab.api.data.general.ITable;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.observations.scale.space.IShape;
import org.integratedmodelling.klab.api.observations.scale.time.ITime;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.utils.Parameters;
import org.integratedmodelling.tables.DimensionScanner;
import org.integratedmodelling.tables.TablesComponent;

public abstract class TableAdapter implements IResourceAdapter {

    public static final String COLUMN_HEADER_CATEGORIZABLE = "Column header";
    
	/**
	 * Storage for stuff that is too expensive to recompute at each resource use.
	 * Should be linked to resource URN and URN only. Individual resources can store
	 * context-dependent data in their own runtime storage at
	 * {@link IResourceEncoder#contextualize(IResource, IScale, IArtifact, Map, IContextualizationScope)}.
	 */
	public static IParameters<String> runtimeData = Parameters.createSynchronized();

	protected TableAdapter() {
	}

	@Override
	public IResourcePublisher getPublisher() {
		return new TablePublisher();
	}

	@Override
	public IResourceEncoder getEncoder() {
		return new TableEncoder();
	}

	@Override
	public IResourceImporter getImporter() {
		return new TableImporter();
	}

	@Override
	public IResourceCalculator getCalculator(IResource resource) {
		// TODO Auto-generated method stub
		return null;
	}

	public static ITable<?> getOriginalTable(IResource resource, boolean recomputeExtents, IMonitor monitor) {
		ITable<?> ret = runtimeData.get(resource.getUrn() + "_table", ITable.class);
		if (ret == null) {
			ret = TablesComponent.getTableInterpreter(resource.getAdapterType()).getTable(resource, null, monitor);
			if (recomputeExtents) {
				if (resource.getParameters().containsKey("space.encoding")
						&& !runtimeData.containsKey(resource.getUrn() + "_space")) {
					String[] parts = resource.getParameters().get("space.encoding").toString()
							.split(Pattern.quote("->"));
					runtimeData.put(resource.getUrn() + "_space",
							new DimensionScanner<IShape>(resource, parts, IShape.class));
				}
				if (resource.getParameters().containsKey("time.encoding")
						&& !runtimeData.containsKey(resource.getUrn() + "_time")) {
					String[] parts = resource.getParameters().get("time.encoding").toString()
							.split(Pattern.quote("->"));
					runtimeData.put(resource.getUrn() + "_time",
							new DimensionScanner<ITime>(resource, parts, ITime.class));
				}
			}
			TableAdapter.runtimeData.put(resource.getUrn() + "_table", ret);
		}
		return ret;
	}

}
