package org.integratedmodelling.tables;

import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.extensions.Component;

/**
 * The table adapter handles tabular data, scalar or distributed along a
 * geometry, by imposing a common table structure and adapter over a variety of
 * tabular data sources. These include relational database tables and joins,
 * SDMX, RDF/SPARQL, XLS/CSV files and NetCDF files. The resource parameter
 * "source" specifies the subtype of the source, which is then handled by a
 * correspondent instance of {@link ITableInterpreter}.
 * 
 * A non-scalar geometry may be handled specifically (for example in NetCDF for
 * raster data or temporal CSV timeseries), or not at all. The output type for
 * all these is a ITable, but when knowable in advance the resource will
 * automatically report its data type as map, list or table according to the
 * source and the filtering.
 * 
 * Table resources have the option of being cacheable into a persistent map to
 * speed up lookup and handling. For this set cache=true in the resource.
 * 
 * @author Ferd
 *
 */
@Component(id = "org.integratedmodelling.table", version = Version.CURRENT)
public class TablesComponent {

	public static final String ID = "org.integratedmodelling.table";

	/**
	 * Source types supported so far. Correspond to 'source' parameter.
	 * 
	 * @author ferdinando.villa
	 *
	 */
	enum Source {
		XLS, XLSX, OPENDAP, JDBC, CSV, SDMX, SPARQL
	}

}
