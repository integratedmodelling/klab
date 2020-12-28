package org.integratedmodelling.tables.adapter.sdmx;

import java.net.URL;
import java.util.List;
import java.util.Map;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.adapters.IKlabData.Builder;
import org.integratedmodelling.klab.api.data.general.ITable;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.common.Geometry;
import org.integratedmodelling.klab.common.GeometryBuilder;
import org.integratedmodelling.tables.TableInterpreter;
import org.integratedmodelling.tables.TablesComponent;
import org.integratedmodelling.tables.TablesComponent.Encoding;

import it.bancaditalia.oss.sdmx.api.Dimension;
import it.bancaditalia.oss.sdmx.api.PortableTimeSeries;
import it.bancaditalia.oss.sdmx.client.SdmxClientHandler;
import it.bancaditalia.oss.sdmx.exceptions.SdmxException;

/**
 * Attributes: sdmx.dataflow, sdmx.provider, sdmx.query (optional: just fix
 * sdmx.dimension.XXXXX=value,value) If provider.needsCredentials(): sdmx.user,
 * sdmx.password
 * 
 * Temporal aspect and numeric character seem to be hard-coded in the standard
 * (time is not a dimension). Not sure if the codelist conventions for country
 * codes, frequencies etc can change across providers (or within) and by how
 * much. In SDMX dthese are called "Cross-domain codelists"
 * (https://sdmx.org/?page_id=3215). They come from more SDMX queries so they
 * can be cached and turned into semantics or context info once and recovered.
 * They seem to be internal to the provider.
 * 
 * So because time is implicit, there's no need for temporal mapping in
 * configuration and the geometry is always T1. Spatial mapping can be
 * configured like in any other table adapter.
 * 
 * Operations could include calling analyze() again to update time coverage.
 * 
 * Codelists will be key to join spatialization sources; may need translation to
 * ISO or others if the codes are non-conformant. For conventional codes we
 * could provide functions to call, e.g.
 * space.join=im.geo:admin:boundaries.global:country#iso2&map=iso2(GEO)
 */
public class SDMXInterpreter extends TableInterpreter {

	@Override
	public Type getType(IResource resource, IGeometry geometry) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void encode(IResource resource, Map<String, String> urnParameters, IGeometry geometry, Builder builder,
			IContextualizationScope context) {
		// TODO Auto-generated method stub

	}

	/*
	 * testing
	 */
	public static void main(String[] args) throws Exception {

		String provider = "EUROSTAT";
//		// this returns about 7000 dataflow descriptors
//		final Map<String, Dataflow> flows = SdmxClientHandler.getFlowObjects(provider, null);
//		// choose a flow ID among the results. Check out GGH emissions at sdg_13_10
//		String dataflowID = "sdg_15_50";
//		// each dimension has name, offset and codelist
//		List<Dimension> dimensions = SdmxClientHandler.getDimensions(provider, dataflowID);
//		// Build timeseries query key using the dimensions and the codelists
//		String tsQuery = "sdg_15_50/A.SEV.CLC2_3X331_332_335.KM2+PC.BE+BG+CH+AT+CY";
//		List<PortableTimeSeries<Double>> result = SdmxClientHandler.getTimeSeriesNames(provider, tsQuery);

		int n = 0;
		for (Dimension dimension : SdmxClientHandler.getDimensions(provider, "sdg_13_10")) {
			System.out.print((++n) + ". " + dimension);
		}

		n = 0;
		for (PortableTimeSeries<Double> timeseries : SdmxClientHandler.getTimeSeries(provider,
				"sdg_13_10/A.GHG_T_HAB.UK", "1990", "2020")) {
			// each timeseries has a TIMES attribute that details the temporal coverage.
			// Time is NOT a dimension!
			System.out.println((++n) + ". " + timeseries);
		}

		// key method seems to be getTimeseries(provider, query, String startTime,
		// String endTime, boolean serieskeysonly, String updatedAfter, boolean
		// includeHistory) - previous calls it
		// with default parameters, i.e. no data, just names
		// Check interface docs in GenericSDMXClient
	}

	@Override
	public void buildResource(IParameters<String> userData, IResource.Builder builder, IMonitor monitor) {

		try {

			String provider = userData.get("provider", String.class);
			String dataflow = userData.get("dataflow", String.class);

			List<Dimension> dimensions = SdmxClientHandler.getDimensions(provider, dataflow);

			GeometryBuilder geometryBuilder = Geometry.builder();

			if (dimensions != null && !dimensions.isEmpty()) {

				SDMXQuery query = null;
				if (userData.containsKey("query")) {
					query = new SDMXQuery(userData.get("query", String.class), dimensions);
				}

				builder.withParameter("provider", userData.get("provider", String.class)).withParameter("dataflow",
						userData.get("dataflow", String.class));

				int dataDims = 0;

				for (Dimension dimension : dimensions) {
					Encoding descriptor = TablesComponent.getEncoding(dimension.getCodeList().getFullIdentifier());
					if (descriptor == null || !descriptor.isDimension()) {
						// attribute dimension; can't have more than 2
						dataDims++;
						if (dataDims > 2) {
							boolean locked = dimension.getCodeList().size() == 1;
							if (!locked && query != null && query.containsKey(dimension.getName())) {
								locked = query.getDimensionSize(dimension.getName()) == 1;
							}
							if (!locked) {
								builder.addError(
										"More than 2 non-contextual dimensions with multiple values: please restrict dimensionality using a query");
								break;
							}
							
							
							
						}
					} else {
						// rebuild the codelist descriptor INSIDE the resource so it can be seen and
						// edited if needed (will need actions on update)
						String queryValue = query == null ? null : query.get(dimension.getName());
						// may be contextual or categorical
						descriptor.setGeometry(geometryBuilder, queryValue);
						// TODO recover queried value for dimension, if any is passed in "query"
						// parameter
						Map<String, String> localizedCodes = descriptor.localizeEncoding(dimension.getName());
						for (String key : localizedCodes.keySet()) {
							builder.withParameter(key, localizedCodes.get(key));
						}
					}
				}

				builder.withGeometry(geometryBuilder.build());

			} else {
				builder.addError("Dataflow is unknown or has no recognizable dimensions");
			}

		} catch (SdmxException e) {
			builder.addError(e);
		}

	}

	@Override
	public boolean canHandle(URL resource, IParameters<String> parameters) {
		// TODO Auto-generated method stub
		return parameters.contains("dataflow") && parameters.contains("provider");
	}

	@Override
	public ITable<?> getTable(IResource resource, IGeometry geometry) {
		// TODO Auto-generated method stub
		return null;
	}

}
