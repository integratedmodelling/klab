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
import org.integratedmodelling.klab.utils.MiscUtilities;
import org.integratedmodelling.klab.utils.Triple;
import org.integratedmodelling.tables.SQLTableCache;
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

    static public class Dataset {
        public Table table;
        public ITimeInstant start;
        public ITimeInstant end;
        public ITime.Resolution resolution;
        public long rows;
    }

    @Override
    public Type getType(IResource resource, IGeometry geometry) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void encode(IResource resource, Map<String, String> urnParameters, IGeometry geometry, Builder builder,
            IContextualizationScope context) {

    }

    /**
     * Creates a table from the passed dataflow and query. Returns the table along with the temporal
     * span, resolution and value statistics. The table will contain four additional columns besides
     * the ones for each dimension: <code>timestart</code> (time of earliest observation in
     * milliseconds from epoch), <code>timeend</code> (time after last observation in milliseconds
     * from epoch), <code>literaltime</code> (the original string value of the time field) and
     * <code>value</code> (the actual numeric value of each observation, a double).
     * 
     * @param provider
     * @param dataflow
     * @param query
     * @param sortedDimensions
     * @return a valid dataset or null if anything weird occurred.
     */
    public static Dataset getTimeseriesTable(String provider, String dataflow, String query, List<Dimension> sortedDimensions) {

        /**
         * Get the entire monster. Doing anything else would require knowing the years in advance
         * and/or trying all combinations of classifiers. In all cases, prohibitive. If dataset is
         * too large to fit, it will screw everything. Should only matter at import or update.
         */
        List<PortableTimeSeries<Double>> ts;
        try {
            ts = SdmxClientHandler.getTimeSeries(provider, dataflow + "/" + query, null, null);
        } catch (SdmxException e) {
            return null;
        }

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

        columns.add(LongColumn.create("starttime"));
        columns.add(LongColumn.create("endtime"));
        columns.add(StringColumn.create("literaltime"));
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
                row.setLong("starttime", time.getFirst().getMilliseconds());
                row.setLong("endtime", time.getSecond().getMilliseconds());
                row.setString("literaltime", observation.getTimeslot());
                row.setDouble("value", observation.getValueAsDouble());
            }

        }

        Dataset ret = new Dataset();

        ret.table = table;
        ret.start = allStart;
        ret.end = allEnd;
        ret.resolution = resolution;

        return ret;
    }

    /*
     * testing
     */
    public static void main(String[] args) throws Exception {

        int n = 0;
        String SEEA_CF_SUPPLY_DATASET = "DF_UNDATA_SEEA_SUPPLY";
        String ALL_QUERY = "";

        DataFlowStructure structure = SdmxClientHandler.getDataFlowStructure("UNDATA", SEEA_CF_SUPPLY_DATASET);
        List<Dimension> sortedDimensions = new ArrayList<>(structure.getDimensions());
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

        Dataset data = getTimeseriesTable("UNDATA", SEEA_CF_SUPPLY_DATASET, ALL_QUERY, sortedDimensions);
        
        data.table.write().csv(Configuration.INSTANCE.getExportFile("undata_stuff.csv"));
    }

    @Override
    public void buildResource(IParameters<String> userData, IResource.Builder builder, IMonitor monitor) {

        try {

            String providerId = userData.get("provider", String.class);
            String dataflowId = userData.get("dataflow", String.class);
            String query = userData.get("query", String.class);
            String updateQuery = userData.get("updateQuery", String.class);

            GeometryBuilder geometryBuilder = Geometry.builder();

            monitor.info("retrieving SDMX metadata....");

            DataFlowStructure structure = SdmxClientHandler.getDataFlowStructure(providerId, dataflowId);
            List<Dimension> sortedDimensions = new ArrayList<>(structure.getDimensions());
            Collections.sort(sortedDimensions, new Comparator<Dimension>(){
                @Override
                public int compare(Dimension o1, Dimension o2) {
                    return Integer.compare(o1.getPosition(), o2.getPosition());
                }
            });

            monitor.info(
                    "SDMX dataflow " + providerId + "/" + dataflowId + " contains " + sortedDimensions.size() + " dimensions");

            int n = 0;
            String allQuery = "";
            for (Dimension dimension : sortedDimensions) {
                System.out.println((++n) + ". " + dimension.getName());
                allQuery += ".";
            }

            if (updateQuery != null) {
                /*
                 * TODO issue the query and save the result hash. Any error, give up.
                 */
            }

            
            if (sortedDimensions != null && !sortedDimensions.isEmpty()) {

                monitor.info("ingesting SDMX dataflow " + providerId + "/" + dataflowId + ": please be patient...");

                Dataset data = getTimeseriesTable(providerId, dataflowId, query == null ? allQuery : query, sortedDimensions);

                if (data == null) {
                    monitor.error("cannot build resource: error retrieving SDMX data");
                    return;
                }

                geometryBuilder.time().regular().start(data.start).end(data.end).resolution(data.resolution);
                
                /*
                 * wait for the table to have worked out before building the rest of the resource
                 */

                for (Dimension dimension : sortedDimensions) {
                    Type type = dimension.getCodeList() == null ? Type.NUMBER : Type.TEXT;

                    String codeList = defineCodelist(dimension);
                    
                    builder.withAttribute(dimension.getId(), type, true, false);
                    builder.withParameter("column." + dimension.getId().toLowerCase() + ".index", dimension.getPosition() - 1);
                    builder.withParameter("column." + dimension.getId().toLowerCase() + ".mapping", codeList);
                    builder.withParameter("column." + dimension.getId().toLowerCase() + ".originalId", dimension.getId());
                    builder.withParameter("column." + dimension.getId().toLowerCase() + ".originalName", dimension.getName());
                    builder.withParameter("column." + dimension.getId().toLowerCase() + ".size",
                            dimension.getCodeList() == null ? "-1" : ("" + dimension.getCodeList().size()));
                    builder.withParameter("column." + dimension.getId().toLowerCase() + ".searchable", "true");
                    n++;
                }

                builder.withAttribute("column.timestart.index", Type.NUMBER, false, true);
                builder.withParameter("column.timestart.index", sortedDimensions.size())
                        .withParameter("column.timestart.mapping", "").withParameter("column.timestart.size", "-1")
                        .withParameter("column.timestart.searchable", "false");
                builder.withAttribute("column.timeend.index", Type.NUMBER, false, true);
                builder.withParameter("column.timeend.index", sortedDimensions.size() + 1)
                        .withParameter("column.timeend.mapping", "").withParameter("column.timeend.size", "-1")
                        .withParameter("column.timeend.searchable", "false");
                builder.withAttribute("column.literaltime.index", Type.TEXT, false, true);
                builder.withParameter("column.literaltime.index", sortedDimensions.size() + 2)
                        .withParameter("column.literaltime.mapping", "").withParameter("column.literaltime.size", "-1")
                        .withParameter("column.literaltime.searchable", "false");
                
                builder.withAttribute("column.value.index", Type.NUMBER, false, true);
                builder.withParameter("column.value.index", sortedDimensions.size() + 3)
                        .withParameter("column.value.mapping", "").withParameter("column.value.size", "-1")
                        .withParameter("column.value.searchable", "false");
                

                builder.withParameter("rows.total", data.rows);
                builder.withParameter("rows.data", data.rows);
                builder.withParameter("columns.total", sortedDimensions.size() + 4);
                builder.withParameter("columns.data", "1");
                builder.withParameter("headers.columns", false);
                builder.withParameter("headers.rows", false);
                builder.withParameter("time.encoding", "");
                builder.withParameter("space.encoding", "");
                builder.withParameter("resource.type", "sdmx");

                /*
                 * build the database by forcing the table into a SQL cache so we don't have to read this again. 
                 */
                SQLTableCache.createCache(dataflowId, data.table, monitor);
                
                
                //
                // SDMXQuery query = null;
                // if (userData.containsKey("query")) {
                // query = new SDMXQuery(userData.get("query", String.class), dimensions);
                // }
                //
                // builder.withParameter("provider", userData.get("provider",
                // String.class)).withParameter("dataflow",
                // userData.get("dataflow", String.class));
                //
                // int dataDims = 0;
                //
                // for (Dimension dimension : dimensions) {
                // Encoding descriptor =
                // TablesComponent.getEncoding(dimension.getCodeList().getFullIdentifier());
                // if (descriptor == null || !descriptor.isDimension()) {
                // // attribute dimension; can't have more than 2
                // dataDims++;
                // if (dataDims > 2) {
                // boolean locked = dimension.getCodeList().size() == 1;
                // if (!locked && query != null && query.containsKey(dimension.getName())) {
                // locked = query.getDimensionSize(dimension.getName()) == 1;
                // }
                // if (!locked) {
                // builder.addError(
                // "More than 2 non-contextual dimensions with multiple values: please restrict
                // dimensionality using a query");
                // break;
                // }
                //
                // }
                // } else {
                // // rebuild the codelist descriptor INSIDE the resource so it can be seen and
                // // edited if needed (will need actions on update)
                // String queryValue = query == null ? null : query.get(dimension.getName());
                // // may be contextual or categorical
                // descriptor.setGeometry(geometryBuilder, queryValue);
                // // TODO recover queried value for dimension, if any is passed in "query"
                // // parameter
                // Map<String, String> localizedCodes =
                // descriptor.localizeEncoding(dimension.getName());
                // for (String key : localizedCodes.keySet()) {
                // builder.withParameter(key, localizedCodes.get(key));
                // }
                // }
                // }

                monitor.info("SDMX dataflow " + providerId + "/" + dataflowId + "is available in local cache");

                builder.withGeometry(geometryBuilder.build());

            } else {
                builder.addError("Dataflow is unknown or has no recognizable dimensions");
            }

        } catch (SdmxException e) {
            builder.addError(e);
        }

    }

    private String defineCodelist(Dimension dimension) {
        // TODO Auto-generated method stub
        return "";
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
