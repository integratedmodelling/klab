package org.integratedmodelling.tables.adapter;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.integratedmodelling.klab.Urn;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.IResource.Attribute;
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
import org.integratedmodelling.tables.AbstractTable;
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

//		System.out.println("ENCODING " + resource.getUrn());
		boolean ignoreTime = false;
		boolean ignoreSpace = false;
		if (urnParameters.containsKey("ignore")) {
			String[] ss = urnParameters.get("ignore").split(",");
			for (String s : ss) {
				if ("time".equals(s.toLowerCase())) {
					ignoreTime = true;
				} else if ("space".equals(s.toLowerCase())) {
					ignoreSpace = true;
				}
			}
		}

		if (ignoreTime && scope.getScale().getTime() != null && scope.getScale().getTime().getTimeType() != ITime.Type.INITIALIZATION) {
			// just don't move.
			return;
		}
		
		ITable<?> table = (ITable<?>) ((Resource) resource).getRuntimeData().get("table");
		if (table != null) {

			DimensionScanner<ITime> time = (DimensionScanner<ITime>) ((Resource) resource).getRuntimeData().get("time");
			DimensionScanner<ISpace> space = (DimensionScanner<ISpace>) ((Resource) resource).getRuntimeData()
					.get("space");

			if (time != null && !ignoreTime) {
				Filter timeFilter = time.locate(table, geometry);
				if (timeFilter != null) {
					table = table.filter(timeFilter);
				}
			}

			Map<Filter, Object> valueCache = new HashMap<>();
			Aggregator aggregator = new Aggregator(scope.getTargetSemantics(), scope.getMonitor(), true);

			for (Filter filter : table.getFilters()) {
				System.out.println("FILTER " + filter);
			}
			
			for (ILocator locator : scope.getScale()) {

				if (scope.getMonitor().isInterrupted()) {
					return;
				}
				
				Object value = null;
				if (!table.isEmpty()) {

					ITable<?> t = table;

					if (space != null && !ignoreSpace) {

						Filter filter = space.locate(table, locator);

						/*
						 * cache the spatial filter only as the others don't change
						 */
						if (valueCache.containsKey(filter)) {
							value = valueCache.get(filter);
						} else {
							if (filter != null) {
								System.out.println("   NEW SPATIAL FILTER " + filter);
								t = t.filter(filter);
							}
							value = ((AbstractTable<?>) t).get(aggregator);
							System.out.println("       aggregated value = " + value);
							valueCache.put(filter, value);
						}
					}
				}

				builder.add(value, locator);
			}

		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public IResource contextualize(IResource resource, IScale scale, IArtifact targetObservation,
			Map<String, String> urnParameters, IContextualizationScope scope) {

		boolean ignoreTime = false;
		boolean ignoreSpace = false;
		if (urnParameters.containsKey("ignore")) {
			String[] ss = urnParameters.get("ignore").split(",");
			for (String s : ss) {
				if ("time".equals(s.toLowerCase())) {
					ignoreTime = true;
				} else if ("space".equals(s.toLowerCase())) {
					ignoreSpace = true;
				}
			}
		}
		
		ITable<?> table = setFilters(resource, TableAdapter.getOriginalTable(resource, true), urnParameters);
		DimensionScanner<IExtent> space = TableAdapter.runtimeData.get(resource.getUrn() + "_space",
				DimensionScanner.class);
		DimensionScanner<IExtent> time = TableAdapter.runtimeData.get(resource.getUrn() + "_time",
				DimensionScanner.class);

		if (time != null && !ignoreTime) {
			time = time.contextualize(table, scale.getTime(), scope);
		}
		if (space != null && !ignoreSpace) {
			space = space.contextualize(table, scale.getSpace(), scope);
		}

		IResource ret = ((Resource) resource).copy();

		((Resource) ret).getRuntimeData().put("table", table);
		((Resource) ret).getRuntimeData().put("space", space);
		((Resource) ret).getRuntimeData().put("time", time);

		return ret;
	}

	private ITable<?> setFilters(IResource resource, ITable<?> originalTable, Map<String, String> urnParameters) {

		ITable<?> ret = originalTable;
		/*
		 * The filter is an expression
		 */
		if (resource.getParameters().containsKey("filter")) {
			ret = ret.filter(Filter.Type.COLUMN_EXPRESSION, resource.getParameters().get("filter").toString());
		}
		for (String parm : urnParameters.keySet()) {
			if (Urn.SINGLE_PARAMETER_KEY.equals(parm)) {
				Attribute attribute = originalTable.getColumnDescriptor(urnParameters.get(parm));
				if (attribute != null) {
					ret = ret.filter(Filter.Type.INCLUDE_COLUMNS, Collections.singleton(attribute.getIndex()));
					// if not there, continue on to filtering
					continue;
				}
			} 
			if (originalTable.getColumnDescriptor(parm) != null) {
				ret = ret.filter(Filter.Type.ATTRIBUTE_VALUE, parm, urnParameters.get(parm));
			}
		}

		return ret;
	}

}
