package org.integratedmodelling.tables;

import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.extensions.Component;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.utils.Path;
import org.integratedmodelling.tables.adapter.cdm.CDMAdapter;
import org.integratedmodelling.tables.adapter.cdm.CDMInterpreter;
import org.integratedmodelling.tables.adapter.jdbc.JDBCAdapter;
import org.integratedmodelling.tables.adapter.jdbc.JDBCInterpreter;
import org.integratedmodelling.tables.adapter.rdf.RDFAdapter;
import org.integratedmodelling.tables.adapter.rdf.RDFInterpreter;
import org.integratedmodelling.tables.adapter.sdmx.SDMXAdapter;
import org.integratedmodelling.tables.adapter.sdmx.SDMXInterpreter;
import org.integratedmodelling.tables.adapter.xls.XLSAdapter;
import org.integratedmodelling.tables.adapter.xls.XLSInterpreter;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

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
 * Code lists not linked to authorities can be translateable using code files in
 * the component's local space, which are initialized from the contents in
 * resources.
 * 
 * @author Ferd
 *
 */
@Component(id = "org.integratedmodelling.table", version = Version.CURRENT)
public class TablesComponent {

	public static final String ID = "org.integratedmodelling.table";

	/**
	 * Codelist descriptors built from resources (and potentially plug-ins) that
	 * attribute to the values of a dimension that adopts the codelist a specific
	 * role in contextualization.
	 * 
	 * @author Ferd
	 *
	 */
	public static class CodelistDescriptor {

		private Properties properties;

		public CodelistDescriptor(Properties props) {
			this.properties = props;
		}

		// if not null, the dimension with this codelist defines a property of the
		// context
		org.integratedmodelling.klab.api.data.IGeometry.Dimension.Type dimensionType;

		// if not null, the codelist descriptor maps to semantics within the worldview
		// we represent
		IConcept concept;

		public Map<String, String> localizeCodes(String name) {
			Map<String, String> ret = new HashMap<>();
			ret.put("codelist." + name + ".value", ".");
			for (Object property : properties.keySet()) {
				if (!"codelist.name".equals(property)) {
					String rn = Path.getRemainder(property.toString(), ".");
					ret.put(Path.getFirst(property.toString(), ".") + "." + name + "." + rn,
							properties.getProperty(property.toString()));
				}
			}
			return ret;
		}

	}

	static Map<String, CodelistDescriptor> codelistDescriptors = null;

	public TablesComponent() {
		if (codelistDescriptors == null) {
			codelistDescriptors = Collections.synchronizedMap(new HashMap<>());
			ResourcePatternResolver patternResolver = new PathMatchingResourcePatternResolver();
			try {
				for (Resource res : patternResolver.getResources("components/" + ID + "/codes/*.properties")) {
					try (InputStream input = res.getInputStream()) {
						Properties props = new Properties();
						props.load(input);
						if (props.containsKey("codelist.name")) {
							codelistDescriptors.put(props.getProperty("codelist.name"), new CodelistDescriptor(props));
						}
					}
				}
			} catch (Throwable t) {
				Logging.INSTANCE.error(t);
			}
		}
	}

	public static CodelistDescriptor getCodelistDescriptor(String name) {
		return codelistDescriptors.get(name);
	}

	public static ITableInterpreter getTableInterpreter(String type) {

		switch (type) {
		case CDMAdapter.ID:
			return new CDMInterpreter();
		case SDMXAdapter.ID:
			return new SDMXInterpreter();
		case XLSAdapter.ID:
			return new XLSInterpreter();
		case RDFAdapter.ID:
			return new RDFInterpreter();
		case JDBCAdapter.ID:
			return new JDBCInterpreter();
		}
		return null;
	}

}
