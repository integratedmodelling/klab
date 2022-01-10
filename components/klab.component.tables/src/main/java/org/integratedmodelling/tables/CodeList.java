package org.integratedmodelling.tables;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Pattern;

import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.bidimap.DualTreeBidiMap;
import org.integratedmodelling.klab.Authorities;
import org.integratedmodelling.klab.Concepts;
import org.integratedmodelling.klab.api.knowledge.IAuthority;
import org.integratedmodelling.klab.api.knowledge.ICodelist;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.components.time.extents.Time;
import org.integratedmodelling.klab.exceptions.KlabIOException;
import org.integratedmodelling.klab.exceptions.KlabIllegalStateException;
import org.integratedmodelling.klab.utils.NumberUtils;
import org.integratedmodelling.klab.utils.Utils;

/**
 * Maps the values in a table (as strings) to other values, in either direction.
 * Can be initialized parametrically (as a function), with conventional mapping
 * types, or initialized from a property file that besides the category mappings
 * (expressed as category.nn=X->Y) may contain a type for the values, a
 * worldview identifier and a root concept if the type is concept and the
 * worldview is set.
 * 
 * This may be a front to an official codelist (such as those retrieved from
 * SDMX). The {@link #getCodelistBackend()} method returns it if it's there.
 * Otherwise it allows more sophisticated types of mapping.
 * 
 * TODO must become more sophisticated and enable 1) separate, optional
 * bi-directional specifications and 2) many-to-many mapping. The current
 * implementation uses a bidimap which only handles functional mappings.
 * 
 * TODO must enable descriptions and other metadata for each code - a
 * Map<String, IMetadata>
 * 
 * @author Ferd
 *
 */
public class CodeList implements ICodelist {

	public enum Mapping {
		CODELIST, YEAR, DATE_PATTERN, PERIOD
		// ....
	}

	private Properties properties = new Properties();
	private BidiMap<String, Object> mappings = new DualTreeBidiMap<>();
	private IArtifact.Type type = null;
	private String worldview = null;
	private IConcept rootConcept = null;
	private IAuthority authority;
	private Map<String, IMetadata> metadata = new HashMap<>();

	// we either encode the mapping in a file or use the logic encoded in the next
	// fields
	private Mapping mapping = Mapping.CODELIST;
	private String mappingKey;
	private String name;
	private ICodelist codelist;

	public CodeList(Mapping type, String value) {
		this.mapping = type;
		this.mappingKey = value;
		this.name = type + ": " + value;
	}

	public CodeList(String name, File propertiesFile) {
		this.name = name;
		try (InputStream input = new FileInputStream(propertiesFile)) {
			properties.load(input);
			this.worldview = properties.getProperty("worldview");
			if (properties.containsKey("authority")) {
				this.authority = Authorities.INSTANCE.getAuthority(properties.getProperty("authority"));
			}
			String t = properties.getProperty("type");
			if (t != null) {
				this.type = IArtifact.Type.valueOf(t.toUpperCase());
			}
			if (this.worldview != null && this.type == IArtifact.Type.CONCEPT) {
				/*
				 * check for root concept
				 */
				String rc = properties.getProperty("root.concept");
				if (rc != null) {
					this.rootConcept = Concepts.INSTANCE.declare(Concepts.INSTANCE.declare(rc));
				}
			}
			String pattern = Pattern.quote("->");
			for (Object key : properties.keySet()) {
				if (key.toString().startsWith("category.")) {
					String[] pp = properties.getProperty(key.toString()).split(pattern);
					if (pp.length > 1 && !pp[1].isEmpty()) {
						mappings.put(pp[0], pp[1]);
					}
				}
			}
		} catch (Exception e) {
			throw new KlabIOException(e);
		}
	}

	/**
	 * If this is based on an official codelist, return it
	 * 
	 * @return
	 */
	public ICodelist getCodelistBackend() {
		return codelist;
	}

	public CodeList(ICodelist codelist) {
		this.codelist = codelist;
	}

	public Object value(Object value) {

		if (this.codelist != null) {
			return this.codelist.value(value.toString());
		}

		if (this.mapping == Mapping.YEAR) {

			if (NumberUtils.encodesInteger(value.toString())) {
				int year = Integer.parseInt(value.toString());
				Time ret = Time.create(year);
				mappings.put(value.toString(), ret.encode());
				return ret;
			}

		} else if (this.mapping == Mapping.DATE_PATTERN) {

			// TODO

		} else if (this.mapping == Mapping.PERIOD) {

			// value must be two numbers
			if (!value.getClass().isArray() || Array.getLength(value) != 2) {
				throw new KlabIllegalStateException("period code mapping requires two numbers as mapped value");
			}

			Object d1 = Array.get(value, 0);
			Object d2 = Array.get(value, 1);

			if (!(d1 instanceof Number && d2 instanceof Number)) {
				throw new KlabIllegalStateException("period code mapping requires two numbers as mapped value");
			}

			long start = ((Number) d1).longValue();
			long end = ((Number) d2).longValue();

			Time ret = Time.create(start > end ? end : start, start > end ? start : end);
			mappings.put(value.toString(), ret.encode());
			return ret;

		} else {

			Object ret = value == null ? null : mappings.get(value.toString());
			if (ret != null) {
				if (this.authority != null) {
					ret = this.authority.getIdentity(ret.toString(), null);
				} else if (rootConcept != null && this.type == IArtifact.Type.CONCEPT) {
					ret = Concepts.c(ret.toString());
					// TODO error if unknown?
				} else if (this.type != null) {
					ret = Utils.asType(ret, Utils.getClassForType(this.type));
				}
			}
			return ret;
		}

		return null;
	}

	public Object key(Object value) {

		if (this.codelist != null) {
			return this.codelist.key(value);
		}

		if (this.mapping == Mapping.YEAR) {

			if (value instanceof Time) {
				return mappings.inverseBidiMap().get(((Time) value).encode());
			}

		} else if (this.mapping == Mapping.DATE_PATTERN) {

			// TODO

		}

		return value == null ? null : mappings.inverseBidiMap().get(value.toString());
	}

	public IConcept getRootConcept() {
		return codelist == null ? rootConcept
				: (codelist.getRootConceptId() == null ? null : Concepts.c(codelist.getRootConceptId()));
	}

	public String getWorldview() {
		return codelist == null ? worldview : codelist.getWorldview();
	}

	public IArtifact.Type getType() {
		return codelist == null ? type : codelist.getType();
	}

	public String getDescription() {
		return codelist == null ? this.name : codelist.getDescription();
	}

	@Override
	public String getName() {
		return codelist == null ? name : codelist.getName();
	}

	@Override
	public String getAuthorityId() {
		return codelist == null ? null : codelist.getAuthorityId();
	}

	@Override
	public boolean isAuthority() {
		return codelist == null ? false : codelist.isAuthority();
	}

	@Override
	public Collection<Object> keys(Object value) {
		return codelist == null ? Collections.singleton(mappings.getKey(value)) : codelist.keys(value);
	}

	@Override
	public String getPattern() {
		return codelist == null ? null : codelist.getPattern();
	}

	@Override
	public String getRootConceptId() {
		return codelist == null ? (rootConcept == null ? null : rootConcept.getDefinition())
				: codelist.getRootConceptId();
	}

	@Override
	public int size() {
		return codelist == null ? mappings.size() : codelist.size();
	}

	@Override
	public String getDescription(Object code) {
		// TODO Auto-generated method stub
		return codelist != null ? codelist.getDescription(code) : null;
	}

}
