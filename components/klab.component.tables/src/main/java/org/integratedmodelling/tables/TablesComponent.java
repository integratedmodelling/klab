package org.integratedmodelling.tables;

import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.function.Function;

import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.IGeometry.Dimension;
import org.integratedmodelling.klab.api.extensions.Component;
import org.integratedmodelling.klab.api.knowledge.IAuthority;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.common.GeometryBuilder;
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

	public static final String[] adapters = { CDMAdapter.ID, JDBCAdapter.ID, RDFAdapter.ID, SDMXAdapter.ID,
			XLSAdapter.ID };

	/**
	 * Encoding descriptors built from resources (and potentially plug-ins) that
	 * attribute to the values of a dimension that adopts the codelist a specific
	 * role in contextualization. Encodings may be tied to worldviews.
	 * 
	 * Encodings may bridge to context dimensions or help decode content. The key
	 * fields are:
	 * <ul>
	 * <li>T domain, S domain, (dimensionType != null);</li>
	 * <li>worldview-bound classification (conceptSpace != null);</li>
	 * <li>authority identifiers (authority != null);</li>
	 * </ul>
	 * 
	 * In all situations, a transformation may be encoded to turn the value into
	 * whatever is needed to match the code.
	 * 
	 * @author Ferd
	 *
	 */
	public static class Encoding {

		private Properties properties;
		private Map<String, String> fields = new HashMap<>();
		private IAuthority authority = null;
		private IConcept conceptSpace;
		private Function<String, String> transformation = null;
		private String worldview = null;

		public Encoding(Properties props) {
			this.properties = props;
			parseDimension(props.getProperty("dimension.domain"));
		}

		private void parseDimension(String property) {
			if (property != null) {
				String[] pp = property.split(";");
				switch (pp[0]) {
				case "TIME":
					this.dimensionType = Dimension.Type.TIME;
					break;
				case "SPACE":
					this.dimensionType = Dimension.Type.SPACE;
					break;
				case "NUMEROSITY":
					this.dimensionType = Dimension.Type.NUMEROSITY;
					break;
				}
				for (int i = 1; i < pp.length; i++) {
					if (pp[i].contains("=")) {
						String[] ss = pp[i].split("=");
						this.fields.put(ss[0], ss[1]);
					} else {
						this.fields.put(pp[i], "TRUE");
					}
				}
			}
		}

		// if not null, the dimension with this codelist defines a property of the
		// context
		IGeometry.Dimension.Type dimensionType;

		public void setGeometry(GeometryBuilder builder, String queriedValue) {
			switch (this.dimensionType) {
			case NUMEROSITY:
				break;
			case SPACE:
				break;
			case TIME:
				break;
			}
		}

		// if not null, the codelist descriptor maps to semantics within the worldview
		// we represent
		IConcept concept;

		public IGeometry.Dimension.Type getExtent() {
			return dimensionType;
		}

		public Map<String, String> localizeEncoding(String name) {
			Map<String, String> ret = new HashMap<>();
			ret.put("dimension." + name + ".value", ".");
			for (Object property : properties.keySet()) {
				if (!"dimension.name".equals(property)) {
					String rn = Path.getRemainder(property.toString(), ".");
					ret.put(Path.getFirst(property.toString(), ".") + "." + name + "." + rn,
							properties.getProperty(property.toString()));
				}
			}
			return ret;
		}

		public boolean isDimension() {
			return dimensionType != null;
		}

	}

	static Map<String, Encoding> encodingDescriptors = null;

	public TablesComponent() {
		if (encodingDescriptors == null) {
			encodingDescriptors = Collections.synchronizedMap(new HashMap<>());
			ResourcePatternResolver patternResolver = new PathMatchingResourcePatternResolver();
			try {
				for (Resource res : patternResolver.getResources("components/" + ID + "/codes/*.properties")) {
					try (InputStream input = res.getInputStream()) {
						Properties props = new Properties();
						props.load(input);
						if (props.containsKey("dimension.name")) {
							encodingDescriptors.put(props.getProperty("dimension.name"), new Encoding(props));
						}
					}
				}
			} catch (Throwable t) {
				Logging.INSTANCE.error(t);
			}
		}
	}

	public static Encoding getEncoding(String name) {
		return encodingDescriptors.get(name);
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
