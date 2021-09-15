package org.integratedmodelling.tables.adapter.sdmx;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.Time;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.adapters.IKlabData.Builder;
import org.integratedmodelling.klab.api.data.general.ITable;
import org.integratedmodelling.klab.api.observations.scale.time.ITime;
import org.integratedmodelling.klab.api.observations.scale.time.ITime.Resolution;
import org.integratedmodelling.klab.api.observations.scale.time.ITimeInstant;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.common.Geometry;
import org.integratedmodelling.klab.common.GeometryBuilder;
import org.integratedmodelling.klab.exceptions.KlabUnimplementedException;
import org.integratedmodelling.klab.utils.Triple;
import org.integratedmodelling.tables.TableInterpreter;
import org.integratedmodelling.tables.TablesComponent;
import org.integratedmodelling.tables.TablesComponent.Encoding;

import it.bancaditalia.oss.sdmx.api.BaseObservation;
import it.bancaditalia.oss.sdmx.api.DataFlowStructure;
import it.bancaditalia.oss.sdmx.api.Dataflow;
import it.bancaditalia.oss.sdmx.api.Dimension;
import it.bancaditalia.oss.sdmx.api.PortableTimeSeries;
import it.bancaditalia.oss.sdmx.client.SdmxClientHandler;
import it.bancaditalia.oss.sdmx.exceptions.SdmxException;
import tech.tablesaw.api.DoubleColumn;
import tech.tablesaw.api.LongColumn;
import tech.tablesaw.api.Row;
import tech.tablesaw.api.StringColumn;
import tech.tablesaw.api.Table;
import tech.tablesaw.columns.Column;

/**
 * Attributes: sdmx.dataflow, sdmx.provider, sdmx.query (optional: just fix
 * sdmx.dimension.XXXXX=value,value) If provider.needsCredentials(): sdmx.user, sdmx.password
 * 
 * Temporal aspect and numeric character seem to be hard-coded in the standard (time is not a
 * dimension). Not sure if the codelist conventions for country codes, frequencies etc can change
 * across providers (or within) and by how much. In SDMX dthese are called "Cross-domain codelists"
 * (https://sdmx.org/?page_id=3215). They come from more SDMX queries so they can be cached and
 * turned into semantics or context info once and recovered. They seem to be internal to the
 * provider.
 * 
 * So because time is implicit, there's no need for temporal mapping in configuration and the
 * geometry is always T1. Spatial mapping can be configured like in any other table adapter.
 * 
 * Operations could include calling analyze() again to update time coverage.
 * 
 * Codelists will be key to join spatialization sources; may need translation to ISO or others if
 * the codes are non-conformant. For conventional codes we could provide functions to call, e.g.
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
        // // this returns about 7000 dataflow descriptors
        // final Map<String, Dataflow> flows = SdmxClientHandler.getFlowObjects(provider, null);
        // // choose a flow ID among the results. Check out GGH emissions at sdg_13_10
        // String dataflowID = "sdg_15_50";
        // // each dimension has name, offset and codelist
        // List<Dimension> dimensions = SdmxClientHandler.getDimensions(provider, dataflowID);
        // // Build timeseries query key using the dimensions and the codelists
        // String tsQuery = "sdg_15_50/A.SEV.CLC2_3X331_332_335.KM2+PC.BE+BG+CH+AT+CY";
        // List<PortableTimeSeries<Double>> result = SdmxClientHandler.getTimeSeriesNames(provider,
        // tsQuery);

        int n = 0;
        // for (Dimension dimension : SdmxClientHandler.getDimensions(provider, "sdg_13_10")) {
        // System.out.print((++n) + ". " + dimension);
        // }

        String SEEA_CF_SUPPLY_DATASET = "DF_UNDATA_SEEA_SUPPLY";
        String ALL_QUERY = "";

        DataFlowStructure structure = SdmxClientHandler.getDataFlowStructure("UNDATA", SEEA_CF_SUPPLY_DATASET);
        Dataflow dataflow = SdmxClientHandler.getFlow("UNDATA", SEEA_CF_SUPPLY_DATASET);

        List<Dimension> sortedDimensions = new ArrayList<>(SdmxClientHandler.getDimensions("UNDATA", SEEA_CF_SUPPLY_DATASET));

        Collections.sort(sortedDimensions, new Comparator<Dimension>(){
            @Override
            public int compare(Dimension o1, Dimension o2) {
                return Integer.compare(o1.getPosition(), o2.getPosition());
            }
        });

        for (Dimension dimension : sortedDimensions) {
            System.out.println((++n) + ". " + dimension.getName());
            ALL_QUERY += ".";
        }

        List<PortableTimeSeries<Double>> ts = SdmxClientHandler.getTimeSeries("UNDATA", SEEA_CF_SUPPLY_DATASET + "/" + ALL_QUERY,
                null, null);

        Map<String, Dimension> dims = new HashMap<>();

        List<Column<?>> columns = new ArrayList<>();

        for (Dimension dim : sortedDimensions) {
            dims.put(dim.getId(), dim);
            columns.add(dim.getCodeList() == null ? DoubleColumn.create(dim.getId()) : StringColumn.create(dim.getId()));

            /*
             * TODO make dimension attributes and metadata, including fixedValue=XXX if there is a
             * codelist with only one value
             */

        }

        columns.add(LongColumn.create("start_time"));
        columns.add(LongColumn.create("end_time"));
        columns.add(StringColumn.create("literal_time"));
        columns.add(DoubleColumn.create("value"));

        Table table = Table.create(columns.toArray(new Column[columns.size()]));

        // TODO resolution should go in the resource metadata
        ITime.Resolution resolution = null;
        ITimeInstant allStart = null;
        ITimeInstant allEnd = null;

        for (PortableTimeSeries<Double> t : ts) {

            for (BaseObservation<? extends Double> observation : t) {

                Triple<ITimeInstant, ITimeInstant, Resolution> time = Time.INSTANCE.analyzeTimepoint(observation.getTimeslot());

                if (time == null) {
                    throw new KlabUnimplementedException("cannot understand time slot " + observation.getTimeslot());
                }

                if (resolution == null) {
                    resolution = time.getThird();
                } else if (!resolution.equals(time.getThird())) {
                    throw new KlabUnimplementedException(
                            "inconsistent time slot " + observation.getTimeslot() + "  for inferred resolution " + resolution);
                }

                if (allStart == null) {
                    allStart = time.getFirst();
                } else if (allStart.isAfter(time.getFirst())) {
                    allStart = time.getFirst();
                }
                if (allEnd == null) {
                    allEnd = time.getSecond();
                } else if (allEnd.isBefore(time.getSecond())) {
                    allEnd = time.getSecond();
                }

                Row row = table.appendRow();
                for (String attribute : t.getDimensionsMap().keySet()) {
                    String value = t.getDimension(attribute);
                    row.setString(attribute, value);
                }
                row.setLong("start_time", time.getFirst().getMilliseconds());
                row.setLong("end_time", time.getSecond().getMilliseconds());
                row.setString("literal_time", observation.getTimeslot());
                row.setDouble("value", observation.getValueAsDouble());
            }

        }

        table.write().csv(Configuration.INSTANCE.getExportFile("myass.csv"));

        // TODO this goes in the geometry with the resolution
        System.out.println("Temporal coverage: " + allStart + " to " + allEnd);

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
    public ITable<?> getTable(IResource resource, IGeometry geometry, IMonitor monitor) {
        // TODO Auto-generated method stub
        return new SDMXTable(resource, monitor);
    }

    @Override
    public IGeometry recomputeGeometry(IResource resource, Map<String, String> parameters, IMonitor monitor) {

        IGeometry ret = resource.getGeometry();

        return ret;
    }
}
