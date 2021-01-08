package org.integratedmodelling.tables.adapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.adapters.IKlabData.Builder;
import org.integratedmodelling.klab.api.data.adapters.IResourceEncoder;
import org.integratedmodelling.klab.api.data.general.ITable;
import org.integratedmodelling.klab.api.data.general.ITable.Filter;
import org.integratedmodelling.klab.api.observations.scale.IExtent;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.observations.scale.space.ISpace;
import org.integratedmodelling.klab.api.observations.scale.time.ITime;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.data.Aggregator;
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

	@SuppressWarnings("unchecked")
	@Override
	public void getEncodedData(IResource resource, Map<String, String> urnParameters, IGeometry geometry,
			Builder builder, IContextualizationScope scope) {

		ITable<?> table = (ITable<?>) ((Resource) resource).getRuntimeData().get("table");
		if (table != null) {

			DimensionScanner<ITime> time = (DimensionScanner<ITime>) ((Resource) resource).getRuntimeData()
					.get("time");
			DimensionScanner<ISpace> space = (DimensionScanner<ISpace>) ((Resource) resource).getRuntimeData()
					.get("space");

			if (time != null) {
				// TODO implement this
				Filter timeFilter = time.locate(table, geometry);
				if (timeFilter != null) {
					table = table.filter(timeFilter);
				}
			}
			
			Map<Filter, Object> valueCache = new HashMap<>();
			Aggregator aggregator = new Aggregator(scope.getTargetSemantics(), scope.getMonitor(), true);

			for (ILocator locator : scope.getScale()) {

				Object value = null;
				if (!table.isEmpty()) {

					ITable<?> t = table;
					
					if (space != null) {
						
						Filter filter = space.locate(table, locator);

						if (valueCache.containsKey(filter)) {

						} else {

							if (filter != null) {
								t = t.filter(filter);
								List<?> lval = t.asList();
								if (!lval.isEmpty()) {
									value = lval.size() == 1 ? lval.get(0) : aggregator.aggregate(lval);
								}
							}

							valueCache.put(filter, value);

						}
					}

				}

				builder.add(value, locator);
			}

		}

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

		IResource ret = ((Resource) resource).copy();

		((Resource) ret).getRuntimeData().put("table", table);
		((Resource) ret).getRuntimeData().put("space", space);
		((Resource) ret).getRuntimeData().put("time", time);

		return ret;
	}

	private ITable<?> setFilters(IResource resource, ITable<?> originalTable, Map<String, String> urnParameters) {

		ITable<?> ret = originalTable;
		if (resource.getParameters().containsKey("filter")) {
			ret = ret.filter(Filter.Type.COLUMN_EXPRESSION, resource.getParameters().get("filter").toString());
		}
		for (String parm : urnParameters.keySet()) {
			if (originalTable.getColumnDescriptor(parm) != null) {
				ret = ret.filter(Filter.Type.COLUMN_MATCH, parm, urnParameters.get(parm));
			}
		}

		return ret;
	}

}
