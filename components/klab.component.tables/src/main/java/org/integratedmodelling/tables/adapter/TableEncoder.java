package org.integratedmodelling.tables.adapter;

import java.util.HashMap;
import java.util.Map;

import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.adapters.IKlabData.Builder;
import org.integratedmodelling.klab.api.data.adapters.IResourceEncoder;
import org.integratedmodelling.klab.api.data.general.ITable;
import org.integratedmodelling.klab.api.data.general.ITable.Filter;
import org.integratedmodelling.klab.api.observations.scale.IExtent;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.common.Urns;
import org.integratedmodelling.klab.data.resources.Resource;
import org.integratedmodelling.tables.DimensionScanner;

public class TableEncoder implements IResourceEncoder {

	public TableEncoder() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isOnline(IResource resource, IMonitor monitor) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void getEncodedData(IResource resource, Map<String, String> urnParameters, IGeometry geometry,
			Builder builder, IContextualizationScope context) {
		// TODO Auto-generated method stub
		System.out.println("ZIO POP");
	}

	@SuppressWarnings("unchecked")
	@Override
	public IResource contextualize(IResource resource, IScale scale, IArtifact targetObservation,
			Map<String, String> urnParameters, IContextualizationScope scope) {

		ITable<?> table = setFilters(resource, TableAdapter.getOriginalTable(resource, true), urnParameters);
		DimensionScanner<IExtent> space = TableAdapter.runtimeData.get(resource.getUrn() + "_space",
				DimensionScanner.class);
		DimensionScanner<IExtent> time = TableAdapter.runtimeData.get(resource.getUrn() + "_time",
				DimensionScanner.class);

		if (time != null) {
			table = time.contextualize(table, scale.getTime(), scope);
		}
		if (space != null) {
			table = space.contextualize(table, scale.getSpace(), scope);
		}

		((Resource) resource).getRuntimeData().put("table", table);
		((Resource) resource).getRuntimeData().put("space", space);
		((Resource) resource).getRuntimeData().put("time", time);

		return resource;
	}

	private ITable<?> setFilters(IResource resource, ITable<?> originalTable, Map<String, String> urnParameters) {

		ITable<?> ret = originalTable;
		if (resource.getParameters().containsKey("filter")) {
			ret = ret.filter(Filter.COLUMN_EXPRESSION, resource.getParameters().get("filter").toString());
		}
		for (String parm : urnParameters.keySet()) {
			if (originalTable.getColumnDescriptor(parm) != null) {
				ret = ret.filter(Filter.COLUMN_MATCH, parm, urnParameters.get(parm));
			}
		}

		return ret;
	}

}
