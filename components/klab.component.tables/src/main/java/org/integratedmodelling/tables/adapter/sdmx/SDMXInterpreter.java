package org.integratedmodelling.tables.adapter.sdmx;

import java.util.List;
import java.util.Map;

import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.adapters.IKlabData.Builder;
import org.integratedmodelling.klab.api.data.general.ITable.Structure;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.utils.Pair;
import org.integratedmodelling.tables.TableInterpreter;

import it.bancaditalia.oss.sdmx.api.Dataflow;
import it.bancaditalia.oss.sdmx.api.Dimension;
import it.bancaditalia.oss.sdmx.api.PortableTimeSeries;
import it.bancaditalia.oss.sdmx.client.SdmxClientHandler;

/*
 * Attributes: sdmx.dataflow, sdmx.provider, sdmx.query
 * If provider.needsCredentials(): sdmx.user, sdmx.password
 */
public class SDMXInterpreter extends TableInterpreter {

	
	
	@Override
	public Type getType(IResource resource, IGeometry geometry) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Pair<Structure, IGeometry> analyze(IResource resource) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void encode(IResource resource, Map<String, String> urnParameters, IGeometry geometry, Builder builder,
			IContextualizationScope context) {
		// TODO Auto-generated method stub

	}

	public static void main(String[] args) throws Exception {
		String provider = "EUROSTAT";
		// this returns about 7000 dataflow descriptors
		final Map<String, Dataflow> flows = SdmxClientHandler.getFlowObjects(provider, null);
		// choose a flow ID among the results. Check out GGH emissions at sdg_13_10
		String dataflowID = "sdg_15_50";
		// each dimension has name, offset and codelist
		List<Dimension> dimensions = SdmxClientHandler.getDimensions(provider, dataflowID);
		// Build timeseries query key using the dimensions and the codelists
		String tsQuery = "sdg_15_50/A.SEV.CLC2_3X331_332_335.KM2+PC.BE+BG+CH+AT+CY";
		List<PortableTimeSeries<Double>> result = SdmxClientHandler.getTimeSeriesNames(provider, tsQuery);
		// key method seems to be getTimeseries(provider, query, String startTime,
		// String endTime, boolean serieskeysonly, String updatedAfter, boolean includeHistory) - previous calls it
		// with default parameters, i.e. no data, just names
		// Check interface docs in GenericSDMXClient
	}

}
